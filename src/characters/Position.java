package characters;

/**
 * Class holding positioning data
 * @author Håkon Ødegård Løvdal
 */
public class Position {

	private double latitude;
	private double longitude;

	/**
	 * Constructor of position-object
	 * @param latitude lat-position
	 * @param longitude long-position
	 */
	public Position(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Change location
	 * @param latitude lat-position
	 * @param longitude long-position
	 */
	public void editLocation(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Get latitude
	 * @return latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Get longitude
	 * @return longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Returns string with latitude and longitude
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return latitude + "," + longitude;
	}
}