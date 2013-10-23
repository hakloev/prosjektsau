package characters;

/**
 * Class holding information about a sheep
 * @author Håkon Ødegård Løvdal
 * @author Max Melander
 */

public class Sheep {
	
	private final int sheepId;
	private String nick;
	private int birthYear;
	private Farmer farmer;
	private Position location = null;
	private int pulse;
	private boolean alarm;
	
	/**
	 * 
	 * @param sheepId Sheep's identification number
	 * @param nick Sheep's nickname
	 * @param birthYear Sheep's birthyear
	 * @param farmer Sheep's owner, farmer-object
	 * @param latitude Latitude position of the sheep
	 * @param longitude Longitude position of the sheep
	 */
	public Sheep(int sheepId, String nick, int birthYear, Farmer farmer, double latitude, double longitude) {
		this.sheepId = sheepId;
		setNick(nick);
		setBirthYear(birthYear);
		this.farmer = farmer;
		setLocation(latitude, longitude);
		this.alarm = false;
	}

	/**
	 * Sets the sheep's location
	 * @param latitude Lat-position of sheep
	 * @param longitude Long-postition of the sheep
	 */
	public void setLocation(double latitude, double longitude) {
		if (location == null) {
			location = new Position(latitude, longitude);
		} else {
			location.editLocation(latitude, longitude);
		}	
	}

	/**
	 * Get sheep's location object
	 * @return Returns the Sheep's location-object
	 */
	public Position getLocation() {
		return location;
	}
	
	/**
	 * Get the sheep's age
	 * @return Returns the sheep's birth year
	 */
	public int getAgeOfSheep(){
		int year = 2013;
		return year - this.birthYear;
	}
	
	/**
	 * Get sheep's farmer-object
	 * @return sheep's Farmer-object
	 */
	public Farmer getFarmer() {
		return farmer;
	}
	
	/**
	 * Sets the sheep's nick
	 * @param nick sets sheep's nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Get the sheep'a nickname
	 * @return returns the sheep's nick
	 */
	public String getNick() {
		return nick;
	}
	
	/**
	 * Get the sheep's identification number
	 * @return returns the sheep's ID
	 */
	public int getIdNr() {
		return sheepId;
	}
	/**
	 * Set the sheep's birth year
	 * @param birthYear sets the birthYear
	 */
	private void setBirthYear(int birthYear){
		this.birthYear = birthYear;
	}
	/**
	 * Get the sheep's birth year
	 * @return returns the sheep's year of birth
	 */
	public int getBirthYear(){
		return birthYear;
	}

	/**
	 * Set the sheep's pulse
	 * @param pulse int current pulse
	 */
	public void setPulse(int pulse){
		this.pulse = pulse;
	}

	/**
	 * Returns sheep's pulse
	 * @return a int, pulse
	 */
	public int getPulse(){
		return pulse;
	}

	/**
	 * Check if sheep is dead
	 * @return boolean pulse > 0
	 */
	public boolean isDead() {
		return (pulse < 1);
	}

	public void setAlarmStatus(boolean bool) {
		alarm = bool;
	}

	public boolean getAlarmStatus() {
		return alarm;
	}

	/**
	 * Used to print sheep identification in sheep list (Sheep Panel)
	 *
	 * @override overrides toString
	 * @return a string of sheep identification and nickname
	 */
	@Override
	public String toString() {
		return "Sau-ID: " + sheepId + " (" + nick + ")";
	}
}
