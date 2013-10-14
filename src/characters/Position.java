package characters;

public class Position {

	private double latitude;
	private double longitude;
	private double height;
	
	public Position(double latitude, double longitude, double height) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.height = height;
	}
	
	public void editLocation(double latitude, double longtidude, double height) {
		this.latitude = latitude;
		this.longitude = longtidude;
		this.height = height;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getHeight() {
		return height;
	}
	
	@Override
	public String toString(){
		return ("Latitude: " + this.latitude + " Longitude: " + this.longitude+ " Height: " + this.height);
		
	}
	
	
	
}
