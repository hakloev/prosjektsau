package characters;

public class Sheep {
	
	private final int idNr;
	private String nick;
	private int birthYear;
	private int ownerId;
	private SheepLocation location = null;
	
	public Sheep(int idNr, String nick, int birthYear, int ownerId) {
		this.idNr = idNr;
		setNick(nick);
		setBirthYear(birthYear);
		setOwnerId(ownerId);
	}
	
	public void setLocation(double latitude, double longtidude, double height) {
		if (location == null) {
			location = new SheepLocation(latitude, longtidude, height);
		} else {
			location.editLocation(latitude, longtidude, height);
		}
		
	}
	public SheepLocation getLocation() {
		return location;
	}
	
	
	public int getAgeOfSheep(int birthYear){
		int year = 2013;
		return year - birthYear;
	}
	
	//Trenger exception handler
	private void setOwnerId(int ownerId){
		if (ownerId < 1000000){
			this.ownerId = ownerId;
		}
	}
	
	public int getOwnerId() {
		return ownerId;
	}
	
	
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public String getNick() {
		return nick;
	}
	
	
	public int getIdNr() {
		return idNr;
	}
	
	private void setBirthYear(int birthYear){
		this.birthYear = birthYear;
	}
	
	public int getBirthYear(){
		return birthYear;
	}
	
	
	public String toString() {
		return "idNR" + idNr;
	}

}
