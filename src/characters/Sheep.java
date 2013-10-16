package characters;

public class Sheep {
	
	private final int idNr;
	private String nick;
	private int birthYear;
	private int ownerId;
	private Position location = null;
	private int pulse;
	
	/**
	 * 
	 * @param idNr
	 * @param nick
	 * @param birthYear
	 * @param ownerId
	 */
	public Sheep(int idNr, String nick, int birthYear, int ownerId) {
		this.idNr = idNr;
		setNick(nick);
		setBirthYear(birthYear);
		setOwnerId(ownerId);
	}
	/**
	 * Sets the sheep's location
	 * 
	 * @param latitude 
	 * @param longtidude
	 * @param height
	 */
	public void setLocation(double latitude, double longtidude, double height) {
		if (location == null) {
			location = new Position(latitude, longtidude, height);
		} else {
			location.editLocation(latitude, longtidude, height);
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
	 * @param birthYear
	 * @return returns the sheep's birthyear
	 */
	public int getAgeOfSheep(int birthYear){
		int year = 2013;
		return year - birthYear;
	}
	
	//Trenger exception handler
	/**
	 * 
	 * @param ownerId
	 */
	private void setOwnerId(int ownerId){
		if (ownerId < 1000000){
			this.ownerId = ownerId;
		}
	}
	/**
	 * 
	 * @return returns the owner's ID number
	 */
	public int getOwnerId() {
		return ownerId;
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
		return idNr;
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
	
	public String toString() {
		return "idNR" + idNr;
	}
	
	public void setPulse(int pulse){
		this.pulse = pulse;
	}
	
	public int getPulse(){
		return pulse;
	}
	
	public boolean isDead(){
		if (pulse < 1){
			return true;
		}
		else{
			return false;
		}
	}

}
