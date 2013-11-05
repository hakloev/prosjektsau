package characters;

import simulation.Disease;

/**
 * Class holding information about a sheep
 * @author Håkon Ødegård Løvdal
 * @author Max Melander
 */

public class Sheep {
	
	private final int sheepId;
	private int farmID;
	private String nick;
	private int birthYear;
	private Farmer farmer;
	private Position location = null;
	private int pulse;
	private int weight;
	private boolean alarm;
	private boolean infected;
	private int lastUpdate;
	private int age;
	private String gender;
	private boolean isPregnant;
	private int highestPulse;
	private int highestPulseDate;
	private String woolColor;
	private String description;
	private int lastUpdateDate;
	private int sheepCreatedDate;
	

	/**
	 * 
	 * @param sheepId Sheep's identification number
	 * @param nick Sheep's nickname
	 * @param birthYear Sheep's birthyear
	 * @param farmer Sheep's owner, farmer-object
	 * @param latitude Latitude position of the sheep
	 * @param longitude Longitude position of the sheep
	 */
	public Sheep(int sheepId, String nick, int birthYear, String gender, int weight, Farmer farmer, int pulse, double latitude, double longitude, int infected) {
		this.sheepId = sheepId;
		setNick(nick);
		setBirthYear(birthYear);
		this.gender = gender;
		this.weight = weight;
		this.farmer = farmer;
		this.pulse = pulse;
		setLocation(latitude, longitude);
		this.alarm = false;
		if (infected == 1){
			this.infected = true;
		}
		else{
			this.infected = false;
		}
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

	/**
	 * Set alarm status on sheep
	 * @param bool boolean that gives alarmstatus
	 */
	public void setAlarmStatus(boolean bool) {
		alarm = bool;
	}

	/**
	 * Get alarm status
	 * @return the boolean alarm status
	 */
	public boolean getAlarmStatus() {
		return alarm;
	}

	/**
	 * Set the sheep's weight
	 * @param weight int, giving the sheep's weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * Get the sheep's weight
	 * @return the sheep's weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Used to print sheep identification in sheep list (Sheep Panel)
	 *
	 * @override overrides toString
	 * @return a string of sheep identification and nickname
	 */
	@Override
	public String toString() {
		return "(" + sheepId + ") " + nick;
	}
	
	public boolean isInfected(){
		return infected;
	}
	
	public void innfect(){
		infected = true;
	}
	
	public void cure(){
		infected = false;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getFarmID() {
		return farmID;
	}

	public void setFarmID(int farmID) {
		this.farmID = farmID;
	}

	public int getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(int lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isPregnant() {
		return isPregnant;
	}

	public void setPregnant(boolean isPregnant) {
		this.isPregnant = isPregnant;
	}

	public int getHighestPulse() {
		return highestPulse;
	}

	public void setHighestPulse(int highestPulse) {
		this.highestPulse = highestPulse;
	}

	public int getHighestPulseDate() {
		return highestPulseDate;
	}

	public void setHighestPulseDate(int highestPulseDate) {
		this.highestPulseDate = highestPulseDate;
	}

	public String getWoolColor() {
		return woolColor;
	}

	public void setWoolColor(String woolColor) {
		this.woolColor = woolColor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(int lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public int getSheepCreatedDate() {
		return sheepCreatedDate;
	}

}
