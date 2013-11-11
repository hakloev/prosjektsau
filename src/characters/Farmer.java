package characters;

import java.util.ArrayList;

/**
 * Class holding information about the farmer currently logged in
 * @author Andreas Lønes
 * @author Thomas Mathisen
 */
public class Farmer {

	private final int farmerId;
	private final String farmerHash;
	private String userName;
	private String email;
	private ArrayList<ArrayList<Position>> areaPositionList = new ArrayList<ArrayList<Position>>();
	private ArrayList<Area> areaList = new ArrayList<Area>();
	private int farmId;
	private Farm farm;

	/**
	 * Constructor for farmer-class
	 * @param farmerHash the farmers unique hash-identification given by database
	 * @param farmerId the farmers identification number
	 * @param userName the farmer's user name
	 * @param email the farmers Email address
	 */
	public Farmer(int farmerId, String farmerHash, String userName, String email) {
		this.farmerId = farmerId;
		this.farmerHash = farmerHash;
		setEmail(email);
		this.userName = userName;
	}
	
	/**
	 * Adds an area to the farmers list of areas "areaList".
	 * Adds an array of the postition objects of the area to the list "areaPositionList".
	 * @param ArrayList<Position> area
	 * @return 
	 */
	public Area addAreaAndReturn(ArrayList<Position> area) {
		areaPositionList.add(area);
		Area tempArea = new Area ("NewArea", farmId, area, 0);
		areaList.add(tempArea);
		return (tempArea);
	}
	/**
	 * Method to add area to farmer
	 * @param area An Area object to be added to this farmer's areaposition list.
	 */
	public void addArea(Area area) {
		areaPositionList.add(area.getAreaPoints());
		areaList.add(area);
	}

	/**
	 * removes an area from the farmers list of areas "areaList".
	 * @param ArrayList<Position> area
	 */
	public void removeArea(ArrayList<Position> area) {
		areaPositionList.remove(area);
	}


	/**
	 * Get the farmer's email address
	 * @return returns the farmer's Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the farmer's Email address
	 * @param email set the email-address
	 */
	private void setEmail(String email) {
		if(email.contains("@")){
			this.email = email;
		}
	}

	/**
	 * Get the farmer's username
	 * @return returns the farmer's username
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @return Returns this farmer's farm id
	 */
	public int getFarmId() {
		return farmId;
	}

	/**
	 * Sets a farm id to this farmer
	 * @param farmId
	 */
	public void setFarmId(int farmId) {
		this.farmId = farmId;
	}
	
	/**
	 * Get the farmers identification number
	 * @return returns the farmer's owner ID
	 */
	public int getFarmerId() {
		return farmerId;
	}

	/**
	 * Get the farmers unique hashcode
	 * @return returns the farmers hash-code
	 */
	public String getHash() {
		return farmerHash;
	}

	/**
	 * Override the toString method
	 * @override
	 * @return Returns the farmer's information as a string
	 */
	@Override
	public String toString() {
		return "Farmer [farmerId=" + farmerId + ", farmerHash=" + farmerHash
				+ ", userName=" + userName + "]";
	}

	/**
	 * Method to get the areas that belong to the farmer
	 * @return returns an array of arrays containing the position of the areas belonging to the farmer.
	 */
	public ArrayList<ArrayList<Position>> getAreaPositionList() {
		return areaPositionList;
	}

	/**
	 * Sets a farm to this farmer
	 * @param farm
	 */
	public void setFarm(Farm farm){
		this.farm = farm;
	}

	/**
	 * Method to get the farmers farm
	 * @return Returns this farmer's farm
	 */
	public Farm getFarm(){
		return this.farm;
	}
}

