package characters;

import java.util.ArrayList;

public class Farm {
	
	private ArrayList<Area> areas;
	private int farmID;
	private int ownerID;
	private String farmName;
	private String farmAddress;
	
	public Farm(ArrayList<Area> areas, int farmID, int ownerID, String farmName, String farmAddress){
		this.farmID = farmID;
		this.ownerID = ownerID;
		this.farmName = farmName;
		this.farmAddress = farmAddress;
		this.areas = areas;
	}
	
	public int getfarmID() {
		return farmID;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public String getFarmName() {
		return farmName;
	}

	public String getFarmAddress() {
		return farmAddress;
	}

	public Farm(){
		this.areas = new ArrayList<Area>();
	}
	
	public void addArea(Area area){
		this.areas.add(area);
	}
	
	public ArrayList<Area> getAreaList(){
		return this.areas;
	}
}
