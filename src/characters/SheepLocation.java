package characters;

public class SheepLocation {

	private double latitude;
	private double longtidude;
	private double height;
	
	public SheepLocation(double latitude, double longtidude, double height) {
		this.latitude = latitude;
		this.longtidude = longtidude;
		this.height = height;
	}
	
	public void editLocation(double latitude, double longtidude, double height) {
		this.latitude = latitude;
		this.longtidude = longtidude;
		this.height = height;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongtidude() {
		return longtidude;
	}

	public double getHeight() {
		return height;
	}

	
	
	
}
