package characters;

import java.util.ArrayList;

/**
 * Class holding information about the farmer currently logged in
 * @author Andreas Lønes
 */
public class Farmer {

	private final int farmerId;
	private final String farmerHash;
	private String userName;
	private String email;
	private Farm farm;
	private ArrayList<ArrayList<Position>> areaList = new ArrayList<ArrayList<Position>>();
	
	/**
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
	 * @param ArrayList<Position> area
	 */
	public void addArea(ArrayList<Position> area) {
		System.out.println("YOLOO" + area);
		areaList.add(area);
	}
	
	/**
	 * removes an area from the farmers list of areas "areaList".
	 * @param ArrayList<Position> area
	 */
	public void removeArea(ArrayList<Position> area) {
		areaList.remove(area);
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
	//Trenger exception handler
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

	public void setFarm(Farm farm){
		this.farm = farm;
	}

	public Farm getFarm(){
		return this.farm;
	}
}

