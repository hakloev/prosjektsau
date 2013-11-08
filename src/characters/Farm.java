package characters;

import java.util.ArrayList;

public class Farm {
	
	private ArrayList<Area> areas;
	private int farmID;
	private int ownerID;
	private String farmName;
	private String farmAddress;
	
	/**
	 * 
	 * @param areas A list of areas included in this farm
	 * @param farmID This farm's id number
	 * @param ownerID This farm's owner id
	 * @param farmName This farm's name
	 * @param farmAddress This farm's address
	 */
	public Farm(ArrayList<Area> areas, int farmID, int ownerID, String farmName, String farmAddress){
		this.farmID = farmID;
		this.ownerID = ownerID;
		this.farmName = farmName;
		this.farmAddress = farmAddress;
		this.areas = areas;
	}
	
	/**
	 * 
	 * @return Returns the farm id number
	 */
	public int getfarmID() {
		return farmID;
	}
	/**
	 * 
	 * @return Returns this farm's owner's id number
	 */
	public int getOwnerID() {
		return ownerID;
	}
	/**
	 * 
	 * @return Returns the name of this farm
	 */
	public String getFarmName() {
		return farmName;
	}
	/**
	 * 
	 * @return Returns the address of this farm
	 */
	public String getFarmAddress() {
		return farmAddress;
	}
	/**
	 * This constructor is most likely never used, but hey, what are you going to do? Right?
	 */
	public Farm(){
		this.areas = new ArrayList<Area>();
	}
	/**
	 * 
	 * @param area An Area object to be added to this farm's area list
	 */
	public void addArea(Area area){
		this.areas.add(area);
	}
	/**
	 * 
	 * @return Returns this farm's list of areas
	 */
	public ArrayList<Area> getAreaList(){
		return this.areas;
	}
}
