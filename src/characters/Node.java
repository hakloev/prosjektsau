package characters;

public class Node {
	
	private String longitude;
	private String latitude;
	private String[] latlong;
	/**
	 * Creates a new node object to hold latitude and longitude
	 * @param latitude
	 * @param longitude
	 * @author Andreas Lyngby
	 */
	public Node(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		
		latlong = new String[]{latitude, longitude};
	}
	
	/**
	 * Return latitude and longitude in a string array
	 * @return latlong - String[]
	 */
	public String[] getStringArray(){
		return latlong;
	}
	
	/**
	 * Returns latitude as string
	 * @return latitude - String
	 */
	public String getLatitude(){
		return latitude;
	}
	
	/**
	 * Returns longitude as string
	 * @return longitude - String
	 */
	public String getLongitude(){
		return longitude;
	}
	
	@Override
	public String toString(){
		return latitude + "," + longitude;
	}

}
