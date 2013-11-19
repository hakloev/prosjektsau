package serverconnection;

import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import characters.Area;
import characters.Sheep;

// Oppgavekrav til tid:
//  Responstid på forespørsler fra bønder skal maksimalt ta 2 sekunder.
//  Responstid på innleggelse av lokaliseringsinformasjon om en sau skal maksimalt ta 0,5 sekund.

/* *** M A N U A L ***
 * GET requests are used with the combination of ID
 * server will give you the user/sheep/log from the ID specified
 * use -1 for ALL.
 * 
 * Primary:
 * 0 = get system variables
 * 1 = get user
 * 2 = get sheep
 * 3 = get sheep log
 * 4 = get alarm
 * 5 = get map
 * 6 = get sheep position only
 * 7 = get simple sheep list [position, wool color, nickname]
 * 8 = get sheep's farm and area
 */
/**
 * @author Mads Midtlyng
 * <b>Sends and receives data over a network.</b>
 * <b>All data received is given as Response objects.</b>
 */
public class NetHandler {
	private final 	String 		USER_AGENT 		= "Mozilla/5.0";	
	private final	String		PING_URL		= "http://org.ntnu.no/it1901h13g11/public_html/";
	private static 	String 		GET_URL 		= null;
	private static 	String 		POST_URL 		= null;
	private 		String 		m_lastError   	= null;
	private 		boolean 	m_isLoggedIn  	= false;
	private			boolean 	m_isDebugging 	= false;
	private			boolean		m_hasConnection = false;
	private			HttpClient	m_client;
	private			HttpPost 	m_post;
	private 		String 		m_userCode	  	= null;
	private 		String 		m_farmCode	  	= null;
	private			int			m_farmID		= -1;
	public final	String		SMS_CARRIER_NETCOM  = "@sms.netcom.no";
	public final	String		SMS_CARRIER_TELENOR = "@mobilpost.no";
	private	static	int			m_retryCounter	= 0;
	private static final int	m_retryMax		= 5;
	
	/**
	 * <b>Creates HTTP object and checks if there is a connection to the server.</b>
	 */
	public NetHandler() {
		//m_client    = HttpClientBuilder.create().build();  (causes Content-Length=-1)
		m_client		= new DefaultHttpClient();
		m_post 			= new HttpPost();
		m_hasConnection = ping(null) ? true : false;
	}

	public int getFarmID() { return m_farmID; }
	
	public enum MailType { SHEEP_ESCAPE, SHEEP_HIGH_PULSE, 	SHEEP_LOW_PULSE, SHEEP_DEAD }
	public enum MailTo   { USER_ID, 	 FARM_ID, 			SHEEP_ID   }
	
	public void isDebugging(boolean b) { m_isDebugging = b; }
	
	// Temp method? Use to search JSON string.
	/**
	 * @param findField Field to find
	 * @param json String to search in
	 */
	public String searchJSON(String findField, String json) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(json);
		JsonNode field = root.get(findField);
		return field.asText();
	}
	
	public String getFarmCode() { return m_farmCode; }
	
	public void setFarmCode(String c) { m_farmCode = c; }
	
	//=======================================================//
	// A U T H E N T I C A T I O N			  				//
	//=====================================================//
	/**
	 * @param username param is obvious
	 * @param password param is obvious
	 * @return Response object from request result and time of request.
	 */
	public Response login(String username, String password) {
		Response serverResponse = new Response();
		serverResponse.start();
		String url = "http://org.ntnu.no/it1901h13g11/public_html/client_services.php?pid="
						+_sha1("clientlogin");
		
		if(m_isDebugging) { System.out.println("Ber om innlogging for bruker " + username + " med passord " + password); }

		// Prepare POST data.
		try { _prepare(url);
		} catch (URISyntaxException e1) { m_lastError = "Feilformert URL"; e1.printStackTrace(); }

		// Set parameters.
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("username", username));
	    parameters.add(new BasicNameValuePair("password", _sha1(password)));

	    // Add to post.
	    try {
			m_post.setEntity(new UrlEncodedFormEntity(parameters));
		} catch (UnsupportedEncodingException e) { m_lastError = "Feil med parameter"; e.printStackTrace(); }

	    // SEND POSTDATA TO URL.
	    try {
			HttpResponse response 	= m_client.execute(m_post);
			int statuscode = response.getStatusLine().getStatusCode();
			if(statuscode != 200) { System.out.println("Svar: " + response.toString() );
			m_lastError = "Server kunne ikke ta i mot forespørselen akkurat nå"; }
			
			// Get the server response message.
			serverResponse.msg = _getStringFromResponse(response);
			if(serverResponse.msg == null || serverResponse.msg.trim().equals("")) {
				m_lastError = "Fikk ikke svar fra server";
			}

			System.out.println();
			System.out.println(serverResponse.msg);
			System.out.println();
			
			// If not error, get the data we need.
			if(!isError(serverResponse.msg)) {
				if(_setUserCode(searchJSON("usercode", serverResponse.msg))) {
				m_farmCode = searchJSON("farm_share_code", serverResponse.msg);
				m_farmID = Integer.parseInt( searchJSON("farm_id", serverResponse.msg) );
				m_isLoggedIn = true;
				_setURLs();
				if(m_isDebugging) { System.out.println( searchJSON("request_response_message", serverResponse.msg) ); }
				}
			}
		} 
	    catch (ClientProtocolException e) 	{ e.printStackTrace(); } 
	    catch (IOException e) 				{ e.printStackTrace(); }
	    serverResponse.stop();
		return serverResponse;
	}
	
	/**
	 * <b>Tells the server that this user wants to log out</b>
	 * @return Response object from request result and time of request.
	 */
	public Response logout() {
		if(m_userCode != null && m_isLoggedIn) {
			Response serverResponse = new Response();
			serverResponse.start();
			String url = "http://org.ntnu.no/it1901h13g11/public_html/client_services.php?pid="
							+_sha1("clientlogout");
			
			// Prepare POST data.
			try { _prepare(url);
			} catch (URISyntaxException e1) { m_lastError = "Feilformert URL"; e1.printStackTrace(); }

			// Set parameters.
		    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		    parameters.add(new BasicNameValuePair("usercode", m_userCode));
	
		    // Add to post.
		    try {
				m_post.setEntity(new UrlEncodedFormEntity(parameters));
			} catch (UnsupportedEncodingException e) { 
				m_lastError = "Feil med parameter"; e.printStackTrace(); }
	
		    // SEND POSTDATA TO URL.
		    try {
				HttpResponse response = m_client.execute(m_post);
				int statuscode = response.getStatusLine().getStatusCode();
				if(statuscode != 200) { System.out.println("Svar: " + response.toString() ); 
				m_lastError = "Server kunne ikke ta i mot forespørselen akkurat nå"; }
				serverResponse.msg = _getStringFromResponse(response);
				m_isLoggedIn = false;
				m_userCode = null;
			} 
		    catch (ClientProtocolException e) 	{ e.printStackTrace(); } 
		    catch (IOException e) 				{ e.printStackTrace(); }
		    serverResponse.stop();
		    return serverResponse;  
		} else { m_lastError = "Er ikke logget inn"; return null; }
	}

	/**
	 * @param response Checks if this string contains error message characteristics.
	 * @return true or false.
	 */
	public boolean isError(String response) {
		if(!_contains(response, "{\"") && !_contains(response, "OK")) 
		return true;
		return false;
	}

	//===============================================================//
	// 	P	O	S	T			          							//
	//=============================================================//
	
	//============================================//
	// S M S	&	M A I L						 //
	//==========================================//
	/**
	 * <b>Send SMS through the web server</b>
	 * @param phoneNumber Number to send SMS to.
	 * @param carrier Service operator API email.
	 * @param message Text message to send.
	 * @return Response object from request result and time of request.
	 */
	public Response sendSMS(String phoneNumber, String carrier, String message) {
		System.out.println("MERK: Funksjonen ��� videresende e-post som SMS sl���s p��� fra operat���rens nettsider.");
		System.out.println("Pr���ver ��� sende SMS til " + phoneNumber+""+carrier);
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("SEND_SMS", m_userCode));
	    parameters.add(new BasicNameValuePair("phonenumber", phoneNumber));
	    parameters.add(new BasicNameValuePair("carrier", carrier));
	    parameters.add(new BasicNameValuePair("message", message));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle foresp���rselen."; e.printStackTrace(); }
	    return null;
	}
	
	/**
	 * @return Response object from request result and time of request.
	 */
	public Response sendMailToFarm(MailType type, String farmID) throws IOException {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("SEND_MAIL_TO_FARM", farmID));
	    parameters.add(new BasicNameValuePair("mailtype", type.toString()));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;
	}
	
	/**
	 * <b>You don't even want to know.</b>
	 * @return Response object from request result and time of request.
	 */
	public Response sendMail(MailType mailType, MailTo sendToID, 
						   String sendID, String[] regardingIDs, String altEmail) {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("SEND_MAIL", m_userCode));
	    if(altEmail != null && !altEmail.equals("")) { 
	    	parameters.add(new BasicNameValuePair("altemail", altEmail)); 
	    }
	    parameters.add(new BasicNameValuePair("mailtype", mailType.toString()));
	    parameters.add(new BasicNameValuePair("sendtoid", sendToID.toString()));
	    parameters.add(new BasicNameValuePair("sendID", sendID));
	    for(int i = 0; i < regardingIDs.length; i++) 
	    	parameters.add(new BasicNameValuePair("regardingIDs", regardingIDs[i]));
	    
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;
	}
	
	/**
	 * @param sheepIDs  IDs of sheep to mail about.
	 * @param altEmail Send mail to extra email (outside farm).
	 * @return Response object from request result and time of request.
	 */
	public Response sendEscapeMailToFarm(String[] sheepIDs, String altEmail) {		
	return sendMail(MailType.SHEEP_ESCAPE, MailTo.FARM_ID, m_farmCode, sheepIDs, altEmail);
	}
	
	/**
	 * @param sheepIDs  IDs of sheep to mail about.
	 * @param altEmail Send mail to extra email (outside farm).
	 * @return Response object from request result and time of request.
	 */
	public Response sendDeadMailToFarm(String[] sheepIDs, String altEmail) {		
	return sendMail(MailType.SHEEP_DEAD, MailTo.FARM_ID, m_farmCode, sheepIDs, altEmail);
	}
	
	/**
	 * @param sheepIDs  IDs of sheep to mail about.
	 * @param altEmail Send mail to extra email (outside farm).
	 * @return Response object from request result and time of request.
	 */
	public Response sendHighPulseMailToFarm(String[] sheepIDs, String altEmail) {		
	return sendMail(MailType.SHEEP_HIGH_PULSE, MailTo.FARM_ID, m_farmCode, sheepIDs, altEmail);
	}
	
	/**
	 * @param sheepIDs  IDs of sheep to mail about.
	 * @param altEmail Send mail to extra email (outside farm).
	 * @return Response object from request result and time of request.
	 */
	public Response sendLowPulseMailToFarm(String[] sheepIDs, String altEmail) {		
	return sendMail(MailType.SHEEP_LOW_PULSE, MailTo.FARM_ID, m_farmCode, sheepIDs, altEmail);
	}
	
	//===============================================//
	// [POST] U S E R  								//
	//=============================================//
	
	//============================================//
	// P O S T	  M E T H O D S					 //
	//==========================================//
	
	/**
	 * @param email new email
	 * @param firstname new first name
	 * @param lastname new last name
	 * @return Response object from request result and time of request.
	 */
	public Response updateUser(String email, String firstname, String lastname) {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("UPDATE_USER", m_userCode));
	    parameters.add(new BasicNameValuePair("email", email));
	    parameters.add(new BasicNameValuePair("firstname", firstname));
	    parameters.add(new BasicNameValuePair("lastname", lastname));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke oppdatere."; e.printStackTrace(); }
	    return null;
	}
	
	//===============================================//
	// [POST] F A R M								//
	//=============================================//
	
	/**
	 * <b>Create a new FARM and get the share code as result.</b>
	 * @return Response object from request result and time of request.
	 */
	public Response newFarm() {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("CREATE_FARM", m_userCode));
	    try {
		    Response r = _post(parameters);
		    System.out.println(r.msg);
		    if(!(isError(r.msg)) && m_isLoggedIn) {
			    m_farmID = Integer.parseInt( searchJSON("id", r.msg) );
		    }
		    return r;
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;
	}

	/**
	 * @param code set a farm share code for this farm.
	 * @return Response object from request result and time of request.
	 */
	public Response useFarmShareCode(String code) {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("USE_FARM", m_userCode));
	    parameters.add(new BasicNameValuePair("farm_share_code", code));  
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;
	}
	
	/**
	 * @param shareCodeWithFarmers if the new code is to be shared with everyone on the farm.
	 * <b>Creates a new farm share code for the farm owner and the farm.</b>
	 * @return Response object from request result and time of request.
	 */
	public Response newFarmShareCode(boolean shareCodeWithFarmers) {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("NEW_FARM_CODE", m_userCode));
	    if(shareCodeWithFarmers) { parameters.add(new BasicNameValuePair("sharethecode", "1")); } 
	    else { parameters.add(new BasicNameValuePair("sharethecode", "0")); } 
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;
	}
	
	/**
	 * @param farmname set a new farm name.
	 * @param address set a new farm address.
	 * @return Response object from request result and time of request.
	 */
	public Response updateFarm(String farmname, String address) {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("UPDATE_FARM", m_userCode));
	    parameters.add(new BasicNameValuePair("farm_name", farmname));
	    parameters.add(new BasicNameValuePair("farm_address", address));
	    parameters.add(new BasicNameValuePair("id", ""+m_farmID));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke oppdatere."; e.printStackTrace(); }
	    return null;
	}
	
	/**
	 * <b>Delete YOUR farm (if you have one).</b>
	 * @return Response object from request result and time of request.
	 */
	public Response deleteFarm() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("FARM_DELETE", m_userCode));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}
	
	//===============================================//
	// [POST] A R E A  								//
	//=============================================//

	/**
	 * @param a an Area object.
	 * @return Response object from request result and time of request.
	 */
	public Response createArea(Area a) {
		if(a != null)
		{
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("AREA_CREATE", m_userCode));
		parameters.add(new BasicNameValuePair("farm_id", ""+a.getFarmID()));
		
		// Create position
		String latitudes = "";
		String longitudes = "";
		for(int i = 0; i < a.getPosition().size(); i++) {
			latitudes += ","+a.getPosition().get(i).getLatitude();
			longitudes += ","+a.getPosition().get(i).getLongitude();
		}
		
		parameters.add(new BasicNameValuePair("area_latitude", ""+latitudes));
		parameters.add(new BasicNameValuePair("area_longitude", ""+longitudes));
		parameters.add(new BasicNameValuePair("area_name", ""+a.getName()));
		parameters.add(new BasicNameValuePair("list_position", ""+a.getList_pos()));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
		} else return null;
	}
	
	/**
	 * @param a an Area object.
	 * @return Response object from request result and time of request.
	 */
	public Response updateArea(Area a) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("AREA_UPDATE", m_userCode));
		parameters.add(new BasicNameValuePair("id", ""+a.getId()));
		parameters.add(new BasicNameValuePair("farm_id", ""+a.getFarmID()));
		// Create position
		String latitudes = "";
		String longitudes = "";
		for(int i = 0; i < a.getPosition().size(); i++) {
			latitudes += ","+a.getPosition().get(i).getLatitude();
			longitudes += ","+a.getPosition().get(i).getLongitude();
		}
		
		parameters.add(new BasicNameValuePair("area_latitude", ""+latitudes));
		parameters.add(new BasicNameValuePair("area_longitude", ""+longitudes));
		parameters.add(new BasicNameValuePair("area_name", ""+a.getName()));
		parameters.add(new BasicNameValuePair("list_position", ""+a.getList_pos()));
		
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}	
	
	/**
	 * @param id ID of the area you wish to delete.
	 * @return Response object from request result and time of request.
	 */
	public Response deleteArea(int id) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("AREA_DELETE", m_userCode));
		parameters.add(new BasicNameValuePair("farm_id", ""+m_farmID));
		parameters.add(new BasicNameValuePair("id", ""+id));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}
	
	//===============================================//
	// [POST] S H E E P								//
	//=============================================//

	/**
	 * @param parameters Current parameters to apply to.
	 * @param s Sheep object to set parameters from.
	 * @return List<NameValuePair> new parameters.
	 */
	private List<NameValuePair> _getSheepPostParameters(List<NameValuePair> parameters, Sheep s) {
	    parameters.add(new BasicNameValuePair("id", ""+s.getIdNr() ) );
	    //parameters.add(new BasicNameValuePair("farm_id", ""+s.getFarmID() ) );
	    parameters.add(new BasicNameValuePair("farm_id", ""+m_farmID ) );
	    parameters.add(new BasicNameValuePair("current_pulse", ""+s.getPulse() ) );
	    parameters.add(new BasicNameValuePair("nickname", ""+s.getNick() ) );
		parameters.add(new BasicNameValuePair("birthdate", ""+s.getBirthYear() ) );
		parameters.add(new BasicNameValuePair("gender", ""+s.getGender() ) );
	    parameters.add(new BasicNameValuePair("latitude", ""+s.getLocation().getLatitude() ) ); 
	    parameters.add(new BasicNameValuePair("longitude", ""+s.getLocation().getLongitude() ) );
	    parameters.add(new BasicNameValuePair("weight_grams", ""+s.getWeight() ) );    
	    parameters.add(new BasicNameValuePair("description", ""+s.getDescription() ) );    
	    parameters.add(new BasicNameValuePair("wool_color", ""+s.getWoolColor() ) );  
	    parameters.add(new BasicNameValuePair("birthdate", ""+s.getBirthYear() ) );  
	    parameters.add(new BasicNameValuePair("wool_color", ""+s.getWoolColor() ) );
		if (s.isInfected()) {
			parameters.add(new BasicNameValuePair("is_infected", "1") );
		} else {
			parameters.add(new BasicNameValuePair("is_infected", "0") );
		}
	    return parameters;
	}
	
	/**
	 * @param s Sheep object to create on serverside.
	 * @return Response object from request result and time of request.
	 */
	public Response createSheep(Sheep s) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("SHEEP_CREATE", m_userCode));
		parameters.add(new BasicNameValuePair("farm_id", "" + m_farmID));
		parameters = _getSheepPostParameters(parameters, s);
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}

	/**
	 * @param s Sheep object to update on serverside.
	 * @return Response object from request result and time of request.
	 */
	public Response updateSheep(Sheep s) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("SHEEP_UPDATE", m_userCode));
		parameters = _getSheepPostParameters(parameters, s);
		
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}
	
	/**
	 * @param id Sheep object to create on serverside.
	 * @return Response object from request result and time of request.
	 */
	public Response deleteSheep(int id) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("SHEEP_DELETE", m_userCode));
		parameters.add(new BasicNameValuePair("id", ""+id));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}
	
	/**
	 * @param farmcode Farm to add these sheep to.
	 * @param amount Amount of sheep to create.
	 * @return Response object from request result and time of request.
	 */
	public Response makeDebugSheep(String farmcode, int amount) {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("DEBUG_SHEEP_CREATE", m_userCode));
	    parameters.add(new BasicNameValuePair("farmcode", ""+farmcode));
	    parameters.add(new BasicNameValuePair("amount", ""+amount));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle foresp���rselen."; e.printStackTrace(); }
	    return null;
	}

	//===============================================//
	// [POST] A L A R M								//
	//=============================================//
	
	/**
	 * @param alarmID alarm object to update on serverside.
	 * @return Response object from request result and time of request.
	 */
	public Response updateAlarm(int alarmID, boolean isRead, boolean deleteAlarm) {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("UPDATE_ALARM", m_userCode));
	    parameters.add(new BasicNameValuePair("id", ""+alarmID));
	    if(isRead) 	{ parameters.add(new BasicNameValuePair("isread", "1")); } 
	    else 		{ parameters.add(new BasicNameValuePair("isread", "0"));  }
	    
	    if(deleteAlarm) { parameters.add(new BasicNameValuePair("delete", "1")); } 
	    else 			{ parameters.add(new BasicNameValuePair("delete", "0")); }
	    
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;
	}
	
	/**
	 * @param sheep_id SheepID to check alarms for.
	 * @param isOutside tells the server if this sheep is outside farm areas.
	 * @param altEmail email to send alarm to which is not a farm user.
	 * @return Response object from request result and time of request.
	 */
	public Response requestAlarmCheck(int sheep_id, boolean isOutside, String altEmail) { 
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1); 
		parameters.add(new BasicNameValuePair("ALARM_NOTIFY", m_userCode)); 
		parameters.add(new BasicNameValuePair("farm_share_code", ""+m_farmCode)); 
		parameters.add(new BasicNameValuePair("sheep_id", ""+sheep_id)); 
		
		if(altEmail != null) { parameters.add(new BasicNameValuePair("altemail", ""+altEmail)); } 
		
		if(isOutside) 	{ parameters.add(new BasicNameValuePair("outside", "1")); } 
		else  			{ parameters.add(new BasicNameValuePair("outside", "0")); } 
		
		try { return _post(parameters); } catch (IOException e) 
		{ m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); } 
	return null; 
	}
	
	//===============================================//
	// [POST] D A T A  S E N D  					//
	//=============================================//

	/**
	 * @param parameters POSTs these arguments to the server.
	 * @return Response object from request result and time of request.
	 */
	private Response _post(List<NameValuePair> parameters) throws IOException {		
		do {
			Response serverResponse = new Response();
			serverResponse.start();
			// Prepare POST data.
			try { _prepare(POST_URL);
			} catch (URISyntaxException e1) { e1.printStackTrace(); System.out.println("Malformed URL");}
	
		    // Add to post.
		    try {
				m_post.setEntity(new UrlEncodedFormEntity(parameters));
			} catch (UnsupportedEncodingException e) { e.printStackTrace(); }
	
		    // SEND POSTDATA TO URL.
		    try {
				HttpResponse response 	= m_client.execute(m_post);
				int statuscode = response.getStatusLine().getStatusCode();
				if(statuscode != 200) { System.out.println("Svar: " + response.toString() ); }
				
				// Here is the data the server responds with as send the POST form.
				serverResponse.msg = _getStringFromResponse(response);
				
				if(isError(serverResponse.msg)) { m_lastError = serverResponse.msg; }
			} 
		    catch (ClientProtocolException e) 	{ m_lastError="Protokollfeil."; e.printStackTrace(); } 
		    catch (IOException e) 				{ m_lastError="Ingen tilkobling."; e.printStackTrace(); }
		   m_retryCounter = 0;
		   serverResponse.stop();
		   return serverResponse;
		
		 // Retry connection 
		} while(retryconnection(250, POST_URL));
	}

	//===============================================================//
	// 	G	E	T		J	S	O	N	          					//
	//=============================================================//
	
	//===============================================//
	// [GET] M I X E D 								//
	//=============================================//

	/**
	 * <b>Tells the server to check logs</b>
	 * @return Response object from request result and time of request.
	 */
	public Response checkLogs() {
		if(m_isDebugging) { System.out.println("[GET] Check log count"); }
		try { return _get("&rid=106", null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	/**
	 * <b>Tells the server to check alarms for sheeps.</b>
	 * @return Response object from request result and time of request.
	 */
	public Response checkAlarms() {
		if(m_isDebugging) { System.out.println("[GET] Alarm status check"); }
		try { return _get("&rid=123", null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	/**
	 * <b>Get system variables.</b>
	 * @return Response object from request result and time of request.
	 */
	public Response getSystem() {
		if(m_isDebugging) { System.out.println("[GET] system"); }
		try { return _get("&rid=0", null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	/**
	 * <b>Get user data</b>
	 * @return Response object from request result and time of request.
	 */
	public Response getUser() {
		if(m_isDebugging) { System.out.println("[GET] user"); }
		try { 
			Response r = _get("&rid=1", null);
			if(!(isError(r.msg)) && m_isLoggedIn) {
				m_farmID = Integer.parseInt( searchJSON("farm_id", r.msg) );
			}
			return r;
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	//===============================================//
	// [GET] S H E E P	LOG & ALARM					//
	//=============================================//
	
	/**
	 * <b>Get sheep from ID (-1 means all sheep)</b>
	 * @return Response object from request result and time of request.
	 */
	public Response getSheep(int id) {
		if(m_isDebugging) { System.out.println("[GET] sheep " + id); }
		try { return _get("&rid=2&f="+id, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	/**
	 * <b>FOR SIMULATOR ONLY, get ALL sheep existing.</b>
	 * @return Response object from request result and time of request.
	 */
	public Response getSimulatorSheep(int id) {
		if(m_isDebugging) { System.out.println("[GET] sheep " + id); }
		try { return _get("&rid="+_sha1("allsheep")+"&f="+id, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
		
	/**
	 * <b>Get sheep's log from sheep ID (-1 means all sheeplogs)</b>
	 * @return Response object from request result and time of request.
	 */
	public Response getSheepLog(int sheepID) {
		if(m_isDebugging) { System.out.println("[GET] sheep["+sheepID+"] log"); }
		try { return _get("&rid=3&f="+sheepID, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	/**
	 * <b>Get alarm from sheep's ID (-1 means all alarms)</b>
	 * @return Response object from request result and time of request.
	 */
	public Response getAlarm(int sheepID) {
		if(m_isDebugging) { System.out.println("[GET] alarms for sheep id "+sheepID); }
		try { return _get("&rid=4&f="+sheepID, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	//===============================================//
	// [GET] F A R M	& AREA						//
	//=============================================//
	
	/**
	 * @param sharecode get farm from this share code.
	 * @return Response object from request result and time of request.
	 */
	public Response getFarm(String sharecode) {
		if(m_isDebugging) { System.out.println("[GET] farm " + sharecode); }
		if(sharecode.equals("SIMULATOR")) { sharecode = "-1"; }
		try {
			Response r = _get("&rid=10&f="+sharecode, null);
			m_farmID = Integer.parseInt(searchJSON("id", r.msg));
			return r;
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	/**
	 * @param id Get the farm belonging to this sheep's ID.
	 * @return Response object from request result and time of request.
	 */
	public Response getFarmFromSheepID(int id) {
		if(m_isDebugging) { System.out.println("[GET] farm from sheep " + id); }
		try { return _get("&rid=8&f="+id, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	/**
	 * <b>Returns all areas belonging to your farm (if any).</b>
	 * @return Response object from request result and time of request.
	 */
	public Response getAreas() {
		if(m_isDebugging) { System.out.println("[GET] get all areas"); }
		try { return _get("&rid=5&f="+m_farmID, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	//===============================================//
	// [GET] D A T A  R E C E I V E					//
	//=============================================//
	
	/**
	 * @param query URL arguments sent to the server.
	 * @return Response object from request result and time of request.
	 */
	private Response _get(String query, String altURL) throws IOException {
		if(isLoggedIn()) {
		Response response = new Response();
		response.start();
		// Set URL to get JSON from.
		URL url = null;
		if(altURL != null) 	{ url = new URL(altURL + query);  } 
		else 				{ url = new URL(GET_URL + query); }
		//if(m_isDebugging) 	{ System.out.println("[GET] " + url); }
		
        // Start download stream.
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));

        // Fill data from stream.
        String data = null;
        if(in.ready()) { data = in.readLine(); }
        in.close(); 
        if(isError(data)) {
        	m_lastError = data;
        }
        response.stop();
        response.set(data);
        return response;
        
		} else { System.out.println("[GET ERROR] Not logged in"); return null; }
	}
	
	//=========================================//
	// GET FEEDBACK FROM POST DATA			  //
	//=======================================//
	/**
	 * @param r Decodes a HttpResponse into readable string.
	 * @return Response object from request result and time of request.
	 */
	private String _getStringFromResponse(HttpResponse r) throws IOException {
		byte[] bytes 		= null;
		InputStream is 		= r.getEntity().getContent();
		int contentSize 	= (int) r.getEntity().getContentLength();
		
		if(contentSize > 0) {
			BufferedInputStream bis = new BufferedInputStream(is, 512);
			bytes 					= new byte[contentSize];
			int bytesRead 			= 0;
			int offset 				= 0;
			
			while (bytesRead != -1 && offset < contentSize) {
			    bytesRead = bis.read(bytes, offset, contentSize - offset);
			    offset += bytesRead;
			} 
		return new String(bytes, "UTF-8");
		} else { System.out.println("Content size received: " + contentSize + ", aborting.."); return null; }
	}
	
	//=========================================//
	// MISC. FUNCTIONS						  //
	//=======================================//
	/**
	 * <b>Checks if the user is logged in to server.</b>
	 * @return true or false.
	 */
	public boolean isLoggedIn() { return m_isLoggedIn; }
	
	public String getLastError() { return m_lastError; }
	
	/**
	 * @param url URL used for the next request.
	 * <b>Prepares the HTTP Client for the next request.</b>
	 * @return Response object from request result and time of request.
	 */
	private void _prepare(String url) throws URISyntaxException {
		m_client = new DefaultHttpClient();	
		if(m_isDebugging) { System.out.println("Bruker url: " + url); }
		m_post.setURI( new URI(url) );
		m_post.setHeader("Host", "org.ntnu.no");
		m_post.setHeader("charset", "utf-8");
		m_post.setHeader("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		m_post.setHeader("User-Agent", USER_AGENT);
		m_post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		m_post.setHeader("Accept-Language", "en-US,en;q=0.5");
		m_post.setHeader("Connection", "keep-alive");
		m_post.setHeader("Content-Type", "application/x-www-form-urlencoded");	
	}
	
	/**
	 * @param json JSON string to search in.
	 * <b>Returns the server's response message from last Response</b>
	 * @return Response object from request result and time of request.
	 */
	public String getResponseMessage(String json) {
		try { return searchJSON("request_response_message", json); }
		catch (JsonProcessingException e) 	{ e.printStackTrace(); } 
		catch (IOException e) 				{ e.printStackTrace(); }
		return null;
	}
	
	/**
	 * @param toHash Encrypts a string into SHA-1 hash.
	 * @return Response object from request result and time of request.
	 */
    private String _sha1(String toHash)
    {
        String hash = null;
    	if(!toHash.equals("") || toHash != null) {
	        try {
	            MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
	            byte[] bytes = toHash.getBytes("UTF-8");
	            digest.update(bytes, 0, bytes.length);
	            bytes = digest.digest();
	            StringBuilder sb = new StringBuilder();
	            for( byte b : bytes ) { sb.append( String.format("%02x", b) ); }
	            hash = sb.toString();
	        	}
	        	catch( NoSuchAlgorithmException e ) 	{ e.printStackTrace(); }
	        	catch( UnsupportedEncodingException e ) { e.printStackTrace(); }
    	}
        return hash;
    }
	
	/**
	 * @param haystack String to search in.
	 * @param needle String to search for in haystack.
	 * @return Response object from request result and time of request.
	 */
    private boolean _contains(String haystack, String needle) {
	  haystack = haystack == null ? "" : haystack;
	  needle = needle == null ? "" : needle;
	  return haystack.toLowerCase().contains( needle.toLowerCase() );
    }
    
	/**
	 * @param code Sets the usercode for the client.
	 * @return Response object from request result and time of request.
	 */
	private boolean _setUserCode(String code) 	{ 
		if(code!=null) {
			this.m_userCode = code;
			return true; 
			}
		else return false;
	}
	
	// Sets the URLs used for the different requests. Is called once userCode is available.
	private void _setURLs() {
		GET_URL = "http://org.ntnu.no/it1901h13g11/public_html/client_services.php?uc="+m_userCode+"&m="+_sha1("get");
		POST_URL = "http://org.ntnu.no/it1901h13g11/public_html/client_services.php?uc="+m_userCode+"&m="+_sha1("post");
	}
	
	/**
	 * @param url URL to ping, check if the server can respond.
	 * @return true or false.
	 */
	public boolean ping(String url) {
		String thisURL = PING_URL;
		if(url != null && !url.equals("")) { thisURL = url; }
		  HttpURLConnection connection = null;
		    try {
		        URL u = new URL(thisURL);
		        connection = (HttpURLConnection) u.openConnection();
		        connection.setRequestMethod("HEAD");
		        int code = connection.getResponseCode();
		        if(code == 200) { connection.disconnect(); return true; }
		    } 	catch (UnknownHostException e)  { e.printStackTrace(); } 
		    	catch (MalformedURLException e) { e.printStackTrace(); } 
		    	catch (IOException e) 			{ e.printStackTrace(); } 
	return false;
	}
	
	/**
	 * @param sec Seconds to retry the connection.
	 * @return true or false.
	 */
	private boolean retryconnection(int sec, String url) {
		if(!m_hasConnection) {
			String thisURL = PING_URL;
			if(url != null && !url.equals("")) { thisURL = url; }
			while((m_retryCounter < m_retryMax) || !m_hasConnection) {
				try {
					m_retryCounter++;
					Thread.sleep(sec);
					m_hasConnection = ping(thisURL);
				} catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
		return m_hasConnection;
	}
	
	/**
	 * <b>You need to go deeper</b>
	 */
	private void _warp() {
			
	}
	
}
