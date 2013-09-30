package characters;

public class Sheep {
	
	private final int idNr;
	private String nick;
	private final int birthYear;
	private final int ownerId;
	private SheepLocation location = null;
	
	public Sheep(int idNr, String nick, int birthYear, int ownerId) {
		this.idNr = idNr;
		this.nick = nick;
		this.birthYear = birthYear;
		this.ownerId = ownerId;
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
	
	
	public int getAgeOfSheep(int birthYear)
	{
		int year = 2013;
		return year - birthYear;
	}
	
	
	public int getOwnerId() 
	{
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
	
	
	public String toString() {
		return "idNR" + idNr;
	}

}
