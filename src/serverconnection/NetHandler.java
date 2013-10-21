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

public class NetHandler {
	private static 	String 	GET_URL 	= null;
	private static 	String 	POST_URL 	= null;
	private 		String 	userCode	= null;
	private 		boolean userCodeSet;
	private 		String 	lastError 	= null;
	private final 	String 	USER_AGENT 	= "Mozilla/5.0";
	
	public NetHandler() {
		GET_URL = "http://org.ntnu.no/it1901h13g11/public_html/client_services.php?ucg=";
		POST_URL = "http://org.ntnu.no/it1901h13g11/public_html/client_services.php?ucp=";
		userCodeSet = false;
	}

	//==================================================//
	// POST A LOGIN REQUEST AND RECEIVE CONFIRMATION   //
	//================================================//
	public String login(String username, String password) {
		String data = null;
		String url = "http://org.ntnu.no/it1901h13g11/public_html/client_services.php?pid="
						+sha1("clientlogin");
		System.out.println("Ber om innlogging for bruker " + username + " med passord " + password);
		
		// Send POST data.
		HttpClient 	 client 	= new DefaultHttpClient();
		HttpPost 	 post 		= new HttpPost(url);

		// Set parameters.
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	    nameValuePairs.add(new BasicNameValuePair("username", username));
	    nameValuePairs.add(new BasicNameValuePair("password", password));

		// Set headers.
		post.setHeader("Host", "org.ntnu.no");
	    post.setHeader("charset", "utf-8");
	    post.setHeader("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.5");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");

	    // Add to post.
	    try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) { e.printStackTrace(); }

	    // SEND POSTDATA TO URL.
	    try {
			HttpResponse response 	= client.execute(post);
			System.out.println("Sent!");
			
			int statuscode = response.getStatusLine().getStatusCode();
			if(statuscode == 200) { System.out.println("Svar: OK"); } 
			else 		    	  { System.out.println("Svar: " + response.toString() ); }

			data = getStringFromResponse(response);
		} 
	    catch (ClientProtocolException e) 	{ e.printStackTrace(); } 
	    catch (IOException e) 				{ e.printStackTrace(); }

		return data;
	}
	
	//=========================================//
	// POST FORM DATA TO SERVER			      //
	//=======================================//
	private boolean post(String[] fieldNames, String[] fieldData, String params) throws IOException {

	    if(fieldNames.length != fieldData.length) {
	    lastError = "fieldNames.length != fieldData.length";
	    return false;
	    }

		HttpClient 	 client 	= new DefaultHttpClient();
		HttpPost 	 post 		= new HttpPost(POST_URL);
		
		// Set parameters.
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	    nameValuePairs.add(new BasicNameValuePair("usercode", userCode));
	    
	    for(int i = 0; i < fieldNames.length; i++) 
	    	nameValuePairs.add(new BasicNameValuePair(fieldNames[i], fieldData[i]));
	     
	    // Add to post.
	    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	    // SEND POSTDATA TO URL.
	    HttpResponse response 	= client.execute(post);

	    // Check response from server.
	    
	    
	    
	    return true;
	}
	
	//=========================================//
	// POST JSON DATA TO SERVER			      //
	//=======================================//
	private boolean postJSON(Object data) throws IOException {

	   return true;
	}
	
	//=========================================//
	// GET FEEDBACK FROM POST DATA			  //
	//=======================================//
	private String getStringFromResponse(HttpResponse r) throws IOException {
		byte[] bytes 		= null;
		InputStream is 		= r.getEntity().getContent();
		int contentSize 	= (int) r.getEntity().getContentLength();
		
		System.out.println("Content size ["+contentSize+"]");
		BufferedInputStream bis = new BufferedInputStream(is, 512);
		
		bytes 			= new byte[contentSize];
		int bytesRead 	= 0;
		int offset 		= 0;
		
		while (bytesRead != -1 && offset < contentSize) {
		    bytesRead = bis.read(bytes, offset, contentSize - offset);
		    offset += bytesRead;
		} 
		return new String(bytes, "UTF-8");
	}
	
	//=========================================//
	// GET JSON OBJECT FROM REQUEST			  //
	//=======================================//
	private String get(String query, String altURL) throws IOException {

		// Set URL to get JSON from.
		URL url = null;
		if(altURL != null) 	{ url = new URL(altURL);  } 
		else 				{ url = new URL(GET_URL); }
		
        // Start download stream.
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));

        // Fill data from stream.
        String data = null;
        if(in.ready()) {
	        while ((data = in.readLine()) != null) {
	            //System.out.println(data);
	        }
        }	
	        in.close();
      
	return data;
	}

	//=========================================//
	// MISC. FUNCTIONS						  //
	//=======================================//
	
	// Creates a sha1 hash string 40 characters long.
    public String sha1(String toHash)
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
    public boolean contains(String haystack, String needle) {
	  haystack = haystack == null ? "" : haystack;
	  needle = needle == null ? "" : needle;
	  return haystack.toLowerCase().contains( needle.toLowerCase() );
    }
    
	public void setUserCode(String code) {
		if (!userCodeSet) {
			this.userCode = code;
			this.userCodeSet = true;
			GET_URL += code;
			POST_URL += code;
		}
	}
	
	public String getLastError() 			{ return lastError; }
	
	// Main for testing
//	public static void main(String[] args) {
//
//		NetHandler net 	= new NetHandler();
//		String res 		= net.login("mads", "mads");
//		System.out.println("Resultat: " + res);
//	}
}
