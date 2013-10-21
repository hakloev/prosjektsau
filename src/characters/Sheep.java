package characters;

/**
 * 
 * @author Håkon Ødegård Løvdal
 * @author Max Melander
 *
 */

public class Sheep {
	
	private final int sheepId;
	private String nick;
	private int birthYear;
	private Farmer farmer;
	private Position location = null;
	
	/**
	 * 
	 * @param idNr
	 * @param nick
	 * @param birthYear
	 * @param ownerId
	 */
	public Sheep(int sheepId, String nick, int birthYear, Farmer farmer, double latitude, double longtidude) {
		this.sheepId = sheepId;
		setNick(nick);
		setBirthYear(birthYear);
		this.farmer = farmer;
		setLocation(latitude, longtidude);
	}
	/**
	 * Sets the sheep's location
	 * 
	 * @param latitude 
	 * @param longtidude
	 * @param height
	 */
	public void setLocation(double latitude, double longtidude) {
		if (location == null) {
			location = new Position(latitude, longtidude);
		} else {
			location.editLocation(latitude, longtidude);
		}	
	}
	/**
	 * 
	 * @return returns the Sheep's location
	 */
	public Position getLocation() {
		return location;
	}
	
	/**
	 * 
	 * @return returns the sheep's birthyear
	 */
	public int getAgeOfSheep(){
		int year = 2013;
		return year - this.birthYear;
	}
	
	//Trenger exception handler
	/**
	 * 
	 * @param ownerId
	 */
	public Farmer getFarmer() {
		return farmer;
	}
	
	/**
	 * Sets the sheep's nick
	 * 
	 * @param nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	/**
	 * 
	 * @return returns the sheep's nick
	 */
	public String getNick() {
		return nick;
	}
	
	/**
	 * 
	 * @return returns the sheep's ID
	 */
	public int getIdNr() {
		return sheepId;
	}
	/**
	 * 
	 * @param birthYear
	 */
	private void setBirthYear(int birthYear){
		this.birthYear = birthYear;
	}
	/**
	 * 
	 * @return returns the sheep's year of birth
	 */
	public int getBirthYear(){
		return birthYear;
	}
	
	@Override
	public String toString() {
		return nick + ": " + sheepId;
	}

}
