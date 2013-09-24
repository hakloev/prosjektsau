package characters;

public class Sheep {
	
	private int idNr;
	private String nick;
	private int birthYear;
	private int ownerId;
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

}
