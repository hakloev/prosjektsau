package serverconnection;

import java.util.ArrayList;

import characters.Farmer;
import characters.Sheep;

public class NetMain {

	public static void main(String[] args) {

		NetHandler net 	= new NetHandler();
		
		// Example 1) Login to service:
		Response response;
		response = net.login("mads", "mads");
		response.consoletime();

		// - Check if response given from server is an error or not.
		// - Check errors for each response, not just login.
		if(net.isError(response.msg)) 
		{ 
			System.out.println("Feil under pålogging: " + net.getLastError());
		}
		// Now you're logged in, do main things in here.
		else if(net.isLoggedIn())
		{
			// Response on login from server:
			System.out.println(response.msg);
			JsonHandler json = new JsonHandler();
			
			// Get all sheep
			response = net.getSheep(-1);
			if(net.isError(response.msg)) { 
				System.out.println("Kunne ikke hente sau: " + net.getLastError());
			} else { System.out.println("Svar fra server: " + response.msg); }	
			
			// Update a sheep.
			Farmer f = json.parseJsonAndReturnUser(net.getUser());
			System.out.println("farmer: "  + f.getUserName());
			ArrayList<Sheep> sheepList = json.parseJsonAndReturnSheepList(response, f);
			Sheep sheep = sheepList.get(0);
			System.out.println("has sheep: " + sheep.getIdNr());
			sheep.setPulse(666);
			response = net.updateSheep(sheep);
			if(net.isError(response.msg)) { 
				System.out.println("Kunne ikke oppdatere sau: " + net.getLastError());
			} else { System.out.println("Svar fra server: " + response.msg); }	
			
			
			/*
			response = net.makeDebugSheep(net.getFarmCode(), 100);
			if(net.isError(response.msg)) { 
				System.out.println("Kunne ikke opprette sauer: " + net.getLastError());
			} else { System.out.println("Svar fra server: " + response.msg); response.consoletime(); }	
			*/
			// Create debug sheep
			/*
			response = net.makeDebugSheep(net.getFarmCode(), 100);
			if(net.isError(response.msg)) { 
				System.out.println("Kunne ikke opprette sauer: " + net.getLastError());
			} else { System.out.println("Svar fra server: " + response.msg); response.consoletime(); }	
			*/
			// Get system vars:
			/*
			response = net.getSystem();
			if(net.isError(response.msg)) { 
				System.out.println("Kunne ikke hente systemvariabler: " + net.getLastError());
			} else { System.out.println("Svar fra server: " + response.msg); 
			response.consoletime(); }	
			*/
			
			// Create a new farm.
			/*
			response = net.newFarm();
			if(net.isError(response.msg)) { 
				System.out.println("Kunne ikke sende opprette g�rdinstans: " + net.getLastError());
			} else { System.out.println("Svar fra server: " + response.msg); }
			*/

			
			/*
			// Change farm share code
			response = net.newFarmShareCode();
			if(net.isError(response.msg)) { 
				System.out.println("Kunne ikke endre koden: " + net.getLastError());
			} else { System.out.println("Svar fra server: " + response.msg); }	
			
			
			// Send SMS message through PHPserver:
			response = net.sendSMS("91882850", net.SMS_CARRIER_TELENOR, "test melding 123.");
			if(net.isError(response)) { 
				System.out.println("Kunne ikke sende SMS: " + net.getLastError());
			} else { System.out.println("Svar fra server: " + response); }

			
			// Send mail regarding escaped sheep.
			response = net.sendMail(MailType.SHEEP_ESCAPE, MailTo.FARM_ID, "0", new String[]{"0","1","2"}, null);
			if(net.isError(response)) { 
				System.out.println("Kunne ikke sende e-post: " + net.getLastError());
			} else { System.out.println("Svar fra server: " + response); }
			*/
			
			/*
			// Example 2) Get userdata:
			try {
				response = net.getUser();

				if(net.isError(response)) 
				{ 
					System.out.println("Kunne ikke hente ut brukerdata: " + net.getLastError());
				} else {
					// Parse response through jackson.
					// use data for.. something.
					System.out.println(response);
					
				}
			} catch (IOException e) { e.printStackTrace(); }
			
			// Example 3) Get all sheep:
			try {
				response = net.getSheep(-1);

				if(net.isError(response)) 
				{ 
					System.out.println("Kunne ikke hente ut sau: " + net.getLastError());
				} else {
					// Parse response through jackson.
					// use data for.. something.
					System.out.println(response);
					
				}
			} catch (IOException e) { e.printStackTrace(); }
			*/
			
			// Example 4) Logout:
			response = net.logout();
			System.out.println( response.msg );
		}

		
	//EOM
	}
}
