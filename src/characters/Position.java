package characters;

public class Position {

	private double latitude;
	private double longitude;
	
	public Position(double latitude, double longtidude) {
		this.latitude = latitude;
		this.longitude = longtidude;
	}
	
	public void editLocation(double latitude, double longtidude) {
		this.latitude = latitude;
		this.longitude = longtidude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongtidude() {
		return longitude;
	}

}