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
	private int pulse;
	
	/**
	 * 
	 * @param sheepId Sheeps identification number
	 * @param nick Sheeps nickname
	 * @param birthYear Sheeps birthyear
	 * @param farmer Sheeps owner, farmer-object
	 * @param latitude Latitude position of the sheep
	 * @param longtiude Longtiude position of the sheep
	 */
	public Sheep(int sheepId, String nick, int birthYear, Farmer farmer, double latitude, double longtiude) {
		this.sheepId = sheepId;
		setNick(nick);
		setBirthYear(birthYear);
		this.farmer = farmer;
		setLocation(latitude, longtiude);
	}
	/**
	 * Sets the sheep's location
	 * 
	 * @param latitude Lat-position of sheep
	 * @param longtiude Long-postition of the sheep
	 */
	public void setLocation(double latitude, double longtiude) {
		if (location == null) {
			location = new Position(latitude, longtiude);
		} else {
			location.editLocation(latitude, longtiude);
		}	
	}
	/**
	 * @return location returns the Sheep's location-object
	 */
	public Position getLocation() {
		return location;
	}
	
	/**
	 * @return birthYear returns the sheep's birthyear
	 */
	public int getAgeOfSheep(){
		int year = 2013;
		return year - this.birthYear;
	}
	
	/**
	 * @return sheeps-farmer object
	 */
	public Farmer getFarmer() {
		return farmer;
	}
	
	/**
	 * Sets the sheep's nick
	 * 
	 * @param nick set sheeps nickname
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
	 * @param birthYear sets the birthYear
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
		return "Sau-ID: " + sheepId + " (" + nick + ")";
	}
	
	public void setPulse(int pulse){
		this.pulse = pulse;
	}
	
	public int getPulse(){
		return pulse;
	}
	
	public boolean isDead() {
		return (pulse < 1);
	}

}
