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
import org.apache.http.impl.client.HttpClientBuilder;
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
	
	// Constructor initiates the httpclient.
	public NetHandler() {
		//m_client    = HttpClientBuilder.create().build();  (causes Content-Length=-1)
		m_client		= new DefaultHttpClient();
		m_post 			= new HttpPost();
		m_hasConnection = ping() ? true : false;
	}

	public int getFarmID() { return m_farmID; }
	
	public enum MailType { SHEEP_ESCAPE, SHEEP_HIGH_PULSE, 	SHEEP_LOW_PULSE, SHEEP_DEAD }
	public enum MailTo   { USER_ID, 	 FARM_ID, 			SHEEP_ID   }
	
	public void isDebugging(boolean b) { m_isDebugging = b; }
	
	// Temp method? Use to search JSON string.
	private String searchJSON(String findField, String json) throws JsonProcessingException, IOException {
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
	// Login to server.
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
	
	// Logout.
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

	// Check if this response message is an error, determined by its characters.
	public boolean isError(String response) {
		if(!_contains(response, "{\"") && !_contains(response, "OK")) 
		return true;
		return false;
	}

	//=========================================//
	// POST DATA TO SERVER			          //
	//=======================================//
	// Send SMS through mail testing.
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
	
	// Send mail to everyone affilicated with this farmID.
	public Response sendMailToFarm(MailType type, String farmID) throws IOException {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("SEND_MAIL_TO_FARM", farmID));
	    parameters.add(new BasicNameValuePair("mailtype", type.toString()));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;
	}
	
	// Send a mail of type mailType to everyone related to sendToID with subject regardingIDs.
	// Example: Send a mail regarding escaped sheep to everyone with farmID ABC, sheeps with id 1,2,3,4.
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
	
	// Send escape mail to farm
	public Response sendEscapeMailToFarm(String[] escapedSheepIDs, String altEmail) {		
	return sendMail(MailType.SHEEP_ESCAPE, MailTo.FARM_ID, m_farmCode, escapedSheepIDs, altEmail);
	}
	
	// Send dead mail to farm
	public Response sendDeadMailToFarm(String[] deadSheepIDs, String altEmail) {		
	return sendMail(MailType.SHEEP_DEAD, MailTo.FARM_ID, m_farmCode, deadSheepIDs, altEmail);
	}
	
	// Send high pulse mail to farm
	public Response sendHighPulseMailToFarm(String[] deadSheepIDs, String altEmail) {		
	return sendMail(MailType.SHEEP_HIGH_PULSE, MailTo.FARM_ID, m_farmCode, deadSheepIDs, altEmail);
	}
	
	// Send low pulse mail to farm
	public Response sendLowPulseMailToFarm(String[] deadSheepIDs, String altEmail) {		
	return sendMail(MailType.SHEEP_LOW_PULSE, MailTo.FARM_ID, m_farmCode, deadSheepIDs, altEmail);
	}
	
	// Sends POST data to update a user from usercode.
	public Response updateUser(String jsontext) {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("UPDATE_USER", m_userCode));
	    parameters.add(new BasicNameValuePair("jsonstring", jsontext));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke oppdatere."; e.printStackTrace(); }
	    return null;
	}
	
	// Sends POST data to update a user from usercode.
	public Response updateFarm(String farmname, String address) {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("UPDATE_USER", m_userCode));
	    parameters.add(new BasicNameValuePair("farmname", farmname));
	    parameters.add(new BasicNameValuePair("address", address));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke oppdatere."; e.printStackTrace(); }
	    return null;
	}
	
	public Response deleteFarm() {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("FARM_DELETE", m_userCode));
		parameters.add(new BasicNameValuePair("farm_id", ""+m_farmID));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}
	
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
	
	// AREAS
	// Delete an area.
	public Response deleteArea(int id) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("AREA_DELETE", m_userCode));
		parameters.add(new BasicNameValuePair("farm_id", ""+m_farmID));
		parameters.add(new BasicNameValuePair("id", ""+id));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}
	
	// Update arraylist of sheep.
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
			longitudes += ","+a.getPosition().get(i).getLatitude();
		}
		
		parameters.add(new BasicNameValuePair("area_latitude", ""+latitudes));
		parameters.add(new BasicNameValuePair("area_longitude", ""+longitudes));
		parameters.add(new BasicNameValuePair("area_name", ""+a.getName()));
		parameters.add(new BasicNameValuePair("list_position", ""+a.getList_pos()));
		
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}	
	
	// Create an area.
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
			longitudes += ","+a.getPosition().get(i).getLatitude();
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
	
	// Update arraylist of sheep.
	public Response updateSheep(ArrayList<Sheep> als) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("SHEEP_UPDATE_MANY", m_userCode));
		parameters.add(new BasicNameValuePair("count", ""+als.size()));
		
		for(int i = 0; i < als.size(); i++) {
			parameters.add(new BasicNameValuePair("number", ""+i));
			parameters = _getSheepPostParameters(parameters, als.get(i));
		}
		
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle foresp�rselen."; e.printStackTrace(); }
	    return null;	
	}
	
	// Create a sheep.
	public Response createSheep(Sheep s) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("SHEEP_CREATE", m_userCode));
		parameters.add(new BasicNameValuePair("farm_id", "" + m_farmID));
		parameters = _getSheepPostParameters(parameters, s);
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}
	
	// Delete a single sheep.
	public Response deleteSheep(int id) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("SHEEP_DELETE", m_userCode));
		parameters.add(new BasicNameValuePair("id", ""+id));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}
	
	// Update a single sheep.
	public Response updateSheep(Sheep s) {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
		parameters.add(new BasicNameValuePair("SHEEP_UPDATE", m_userCode));
		parameters = _getSheepPostParameters(parameters, s);
		
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;	
	}
	
	// Create a farm instance.
	public Response makeDebugSheep(String farmcode, int amount) {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("DEBUG_SHEEP_CREATE", m_userCode));
	    parameters.add(new BasicNameValuePair("farmcode", ""+farmcode));
	    parameters.add(new BasicNameValuePair("amount", ""+amount));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle foresp���rselen."; e.printStackTrace(); }
	    return null;
	}
	
	// Create a farm instance.
	public Response newFarm() {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("CREATE_FARM", m_userCode));
	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
	    return null;
	}
	
	// Make a new share farm code (returns null if the user doesn't 'own' a farm)
	public Response newFarmShareCode() {
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    parameters.add(new BasicNameValuePair("NEW_FARM_CODE", m_userCode));

	    try { return _post(parameters);
		} catch (IOException e) { m_lastError = "Kunne ikke behandle foresp���rselen."; e.printStackTrace(); }
	    return null;
	}
	
	// Use this to post an array of [fieldname]+[value] data to the server.
	public Response postArrays(String[] fieldNames, String[] fieldValues) throws IOException {
	    if(fieldNames.length != fieldValues.length) {
	    m_lastError = "fieldNames.length != fieldData.length";
	    return null;
	    }
	    List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
	    for(int i = 0; i < fieldNames.length; i++) 
	    	parameters.add(new BasicNameValuePair(fieldNames[i], fieldValues[i]));

	    return _post(parameters);
	}
	
	// Put that an alarm was read.
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
	
	// Tell server to check alarms.
		public Response requestAlarmCheck(int sheep_id, boolean isOutside, String altEmail) {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>(1);
			parameters.add(new BasicNameValuePair("ALARM_NOTIFY", m_userCode));
			parameters.add(new BasicNameValuePair("farm_share_code", ""+m_farmCode));
			parameters.add(new BasicNameValuePair("sheep_id", ""+sheep_id));
			if(altEmail != null) {
			parameters.add(new BasicNameValuePair("altEmail", ""+altEmail));
			}
			if(isOutside) {
				parameters.add(new BasicNameValuePair("outside", "1"));
			} else {
				parameters.add(new BasicNameValuePair("outside", "0"));
			}
		    try { return _post(parameters);
			} catch (IOException e) { m_lastError = "Kunne ikke behandle forespørselen."; e.printStackTrace(); }
		    return null;	
		}
	
	// Actual POSTing method over a connection. 
	// Post a list over parameters to the server.
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
		} while(retryconnection(250));
	}

	//=========================================//
	// GET JSON STRING FROM REQUEST			  //
	//=======================================//
	
	// Tells the server to check alarms if it wants to.
	public Response checkAlarms() {
		if(m_isDebugging) { System.out.println("[GET] Alarm status check"); }
		try { return _get("&rid=123", null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	// Get system variables.
	public Response getSystem() {
		if(m_isDebugging) { System.out.println("[GET] system"); }
		try { return _get("&rid=0", null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	// Get user from ID.
	public Response getUser() {
		if(m_isDebugging) { System.out.println("[GET] user"); }
		try { return _get("&rid=1", null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	// Get sheep from ID.
	// -1 means all sheep.
	public Response getSheep(int id) {
		if(m_isDebugging) { System.out.println("[GET] sheep " + id); }
		try { return _get("&rid=2&f="+id, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}

	public Response getFarm(String sharecode) {
		if(m_isDebugging) { System.out.println("[GET] farm " + sharecode); }
		if(sharecode.equals("SIMULATOR")) { sharecode = "-1"; }
		try { return _get("&rid=10&=f"+sharecode, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	// Get sheep from ID.
	// -1 means all sheep.
	public Response getFarmFromSheepID(int id) {
		if(m_isDebugging) { System.out.println("[GET] farm from sheep " + id); }
		try { return _get("&rid=8&f="+id, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	// -1 means all sheep.
	public Response getSheepB(int id) {
		if(m_isDebugging) { System.out.println("[GET] sheep " + id); }
		try { return _get("&rid=22&f="+id, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	// -1 means all sheep.
	public Response getSimulatorSheep(int id) {
		if(m_isDebugging) { System.out.println("[GET] sheep " + id); }
		try { return _get("&rid="+_sha1("allsheep")+"&f="+id, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	// Get sheep log from sheepID.
	// -1 means all logs.
	public Response getSheepLog(int sheepID) {
		if(m_isDebugging) { System.out.println("[GET] sheep["+sheepID+"] log"); }
		try { return _get("&rid=3&f="+sheepID, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	// Get alarm log. No need to specify ID.
	// User can only access his own alarms. use [amount] to decide how many.
	// -1 means all alarms.
	public Response getAlarm(int sheepID) {
		if(m_isDebugging) { System.out.println("[GET] alarms for sheep id "+sheepID); }
		try { return _get("&rid=4&f="+sheepID, null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}

	// Get user's plotting of the map area.
	public Response getAreas() {
		if(m_isDebugging) { System.out.println("[GET] get all areas"); }
		try { return _get("&rid=5", null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	// Get sheep positions only.
	public Response getSheepPositions() {
		if(m_isDebugging) { System.out.println("[GET] sheep positions"); }
		try { return _get("&rid=6", null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	// Get the entire list of sheep, but only with nickname, position, wool color.
	public Response getSheepList() throws IOException {
		if(m_isDebugging) { System.out.println("[GET] simple sheep list"); }
		try { return _get("&rid=7", null);
		} catch (IOException e) { m_lastError = "Kunne ikke hente informasjon."; e.printStackTrace(); }
		return null;
	}
	
	// This is the internal method use to send GET requests.
	// If you want to get something, make a new method that calls this. See above.
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
	// Decode a response to string.
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
	public boolean isLoggedIn() { return m_isLoggedIn; }
	
	public String getLastError() { return m_lastError; }
	
	// Use this for setting post headers and URL. DRY.
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
	
	// Searches the response JSON array for the response message field that the server set.
	public String getResponseMessage(String json) {
		try { return searchJSON("request_response_message", json); }
		catch (JsonProcessingException e) 	{ e.printStackTrace(); } 
		catch (IOException e) 				{ e.printStackTrace(); }
		return null;
	}
	
	// Creates a sha1 hash string 40 characters long.
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
	
    // Searches for a needle in a haystack.
    private boolean _contains(String haystack, String needle) {
	  haystack = haystack == null ? "" : haystack;
	  needle = needle == null ? "" : needle;
	  return haystack.toLowerCase().contains( needle.toLowerCase() );
    }
    
    // Sets the usercode unless it is null. check boolean is optional.
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
	
	public boolean ping() {
		  HttpURLConnection connection = null;
		    try {
		        URL u = new URL(PING_URL);
		        connection = (HttpURLConnection) u.openConnection();
		        connection.setRequestMethod("HEAD");
		        int code = connection.getResponseCode();

		        if(code == 200) connection.disconnect(); return true;
		        
		    } 	catch (UnknownHostException e)  { e.printStackTrace(); } 
		    	catch (MalformedURLException e) { e.printStackTrace(); } 
		    	catch (IOException e) 			{ e.printStackTrace(); } 
	return false;
	}
	
	private boolean retryconnection(int sec) {
		if(!m_hasConnection) {
			while((m_retryCounter < m_retryMax) || !m_hasConnection) {
				try {
					m_retryCounter++;
					Thread.sleep(sec);
					m_hasConnection = ping();
				} catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
		return m_hasConnection;
	}
	
	private void _warp() {
			
	}
	
}
