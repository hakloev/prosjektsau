package characters;

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
	private String woolColor;
	private String description;
	private int sheepCreatedDate;

	/**
	 * Constructor for sheep
	 * @param sheepId sheep's id
	 * @param nick sheep's nick
	 * @param birthYear sheep's birth year
	 * @param gender sheep's gender
	 * @param weight sheep's weight
	 * @param farmer sheep's farmer
	 * @param pulse sheep's pulse
	 * @param latitude sheep's latitude
	 * @param longitude sheep's longitude
	 * @param infected if sheep is infected of disease or not
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
	 * Equal to other constructor, but also takes farmID
	 * @param sheepId
	 * @param nick
	 * @param birthYear
	 * @param gender
	 * @param weight
	 * @param farmer
	 * @param pulse
	 * @param latitude
	 * @param longitude
	 * @param infected
	 * @param farmID
	 */
	public Sheep(int sheepId, String nick, int birthYear, String gender, int weight, Farmer farmer, int pulse, double latitude, double longitude, int infected, int farmID) {
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
		this.farmID = farmID;
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
	 * @override overrides toString
	 * @return a string of sheep identification and nickname
	 */
	@Override
	public String toString() {
		return "(" + sheepId + ") " + nick;
	}
	
	/**
	 * Method to det boolean containing info is sheep is infected or not
	 * @return Returns the boolean infected
	 */
	public boolean isInfected(){
		return infected;
	}

	/**
	 * Infects this sheep. Never mind the spelling error, bro.
	 */
	public void innfect(){
		infected = true;
	}
	/**
	 * Cures this sheep
	 */
	public void cure(){
		infected = false;
	}
	/**
	 * Get the sheep's gender
	 * @return Returns the gender of this sheep
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * Sets the gender of this sheep
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Get the farms id
	 * @return Returns this sheep's farm id
	 */
	public int getFarmID() {
		return farmID;
	}

	/**
	 * Sets a farm id to this sheep
	 * @param farmID
	 */
	public void setFarmID(int farmID) {
		this.farmID = farmID;
	}

	/**
	 * Get sheeps last update time
	 * @return Returns this sheep's last update time
	 */
	public int getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * Sets this sheep's last update time
	 * @param lastUpdate
	 */
	public void setLastUpdate(int lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * Get sheeps age
	 * @return Returns this sheep's age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Sets this sheep's age
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * Is sheep pregnant?
	 * @return Returns true if this sheep is pregnant. Male sheep can probably get pregnant to. Let's call it progressive. 
	 */
	public boolean isPregnant() {
		return isPregnant;
	}

	/**
	 * Sets the pregnant state of this sheep
	 * @param isPregnant
	 */
	public void setPregnant(boolean isPregnant) {
		this.isPregnant = isPregnant;
	}

	/**
	 * Get the sheep's wool color
	 * @return Returns this sheep's wool color
	 */
	public String getWoolColor() {
		return woolColor;
	}
	/**
	 * Sets this sheep's wool color
	 * @param woolColor
	 */
	public void setWoolColor(String woolColor) {
		this.woolColor = woolColor;
	}

	/**
	 * Get sheep's description
	 * @return Returns a description of this sheep
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets a happy little description to this sheep
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the date sheep was created
	 * @return Returns the date that this sheep was created on. It was a happy day for all involved.
	 */
	public int getSheepCreatedDate() {
		return sheepCreatedDate;
	}

}
