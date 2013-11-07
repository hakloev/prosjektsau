package serverconnection;

import java.util.ArrayList;

import characters.Area;
import characters.Farm;
import characters.Position;

public class Jsontest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NetHandler nethandler = new NetHandler();
		nethandler.login("Simulering", "Simulering");
		ArrayList<Area> areas = JsonHandler.parseJsonAndReturnAreas(nethandler.getAreas());		

	}

}
