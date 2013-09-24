package characters;

public class SheepLocation {

	private double x;
	private double y;
	private double z;
	
	public SheepLocation(double latitude, double longtidude, double height) {
		this.x = latitude;
		this.y = longtidude;
		this.z = height;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}
	
	
}
