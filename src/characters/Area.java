package characters;

import java.awt.Polygon;
import java.util.ArrayList;

/**
 * Class holding information about a area given by position
 * @author Max Melander
 * @author Thomas Mathiesen
 */
public class Area {
	private String name;
	private ArrayList<Position> polyPoints;
	private int sheepCount;
	private Polygon areaPoly;
	private static final int DOUBLEDESTROYER = 10000;
	
	/**
	 * Contructor of Area-class
	 * @param name The area name
	 * @param sheepCount Number of sheep in area
	 */
	public Area(String name, int sheepCount){
		setName(name);
		polyPoints = new ArrayList<Position>();
		areaPoly = new Polygon();
	}
	
	/**
	 * Sets the name of the area
	 * @param name
	 */
	private void setName(String name){
		this.name = name;
	}
	
	/**
	 * Get the area name
	 * @return returns the area's name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Sets number of sheep in area
	 * @param count number of sheeps in area
	 */
	public void setSheepCount(int count){
		this.sheepCount = count;
	}
	
	/**
	 * Get how many sheeps are in the area
	 * @return the number of sheeps
	 */
	public int getSheepCount(){
		return sheepCount;
	}
	
	/**
	 * Add a polypoint
	 * @param position Outerpoint
	 */
	public void addPolyPoint(Position position){
		polyPoints.add(position);
	}
	
	/**
	 * Creates the polygon
	 */
	public void createPoly(){
		for (Position pos : polyPoints){
			areaPoly.addPoint((int)(pos.getLongitude() * DOUBLEDESTROYER), (int)(pos.getLatitude() * DOUBLEDESTROYER));
		}
	}
	
	/**
	 * Returns the polygon
	 * @return returns the area as a polygon
	 */
	public Polygon getArea(){
		return areaPoly;
	}
}