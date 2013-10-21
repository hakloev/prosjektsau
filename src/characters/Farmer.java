package characters;

public class Farmer 
{

	private final int farmerId;
	private final String farmerHash;
	private String userName;
	private String email;
	
	/**
	 * 
	 * @param ownerID the farmer's owner ID
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
	 * 
	 * @return returns the farmer's Email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * sets the farmer's Email address
	 * 
	 * @param email
	 */
	//Trenger exception handler
	private void setEmail(String email) {
		if(email.contains("@")){
			this.email = email;
		}
	}
	
	/**
	 * 
	 * @return returns the farmer's username
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @return returns the farmer's owner ID
	 */
	public int getFarmerId() {
		return farmerId;
	}
	
	private String getHash() {
		return farmerHash;
	}

	@Override
	public String toString() {
		return "Farmer [farmerId=" + farmerId + ", farmerHash=" + farmerHash
				+ ", userName=" + userName + ", email=" + email + "]";
	}
	
}

