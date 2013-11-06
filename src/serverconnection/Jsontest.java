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
		Farm farm = JsonHandler.parseJsonAndReturnFarm(nethandler.getFarmFromSheepID(1));
		System.out.println("farmID: " + farm.getfarmID() + " Owner ID: " + farm.getOwnerID());
		ArrayList<Area> areaList = farm.getAreaList();
		for (Area area : areaList){
			System.out.println("Area ID: " + area.getId());
			for (Position pos : area.getAreaPoints()){
				System.out.println("lat: " + pos.getLatitude());
				System.out.println("long: " + pos.getLongitude());
			}
		}		

	}

}
