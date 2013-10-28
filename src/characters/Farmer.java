package characters;

/**
 * Class holding information about the farmer currently logged in
 * @author Andreas Lønes
 */
public class Farmer 
{

	private final int farmerId;
	private final String farmerHash;
	private String userName;
	private String email;
	private Area area;
	
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
	
	/**
	 * sets the farmer's area
	 * 
	 * @param area
	 */
	//Trenger exception-handler!!!!!!!!
	public void setArea(Area area) {
		this.area = area;
	}
	
	
	/**
	 * 
	 * @return returns the farmer's are
	 */
	public Area getArea() {
		return area;
	}
}

