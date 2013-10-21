package characters;

public class Position {

	private double latitude;
	private double longtidude;
	
	public Position(double latitude, double longtidude) {
		this.latitude = latitude;
		this.longtidude = longtidude;
	}
	
	public void editLocation(double latitude, double longtidude) {
		this.latitude = latitude;
		this.longtidude = longtidude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongtidude() {
		return longtidude;
	}

}