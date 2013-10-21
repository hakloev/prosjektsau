package characters;

import java.awt.Polygon;
import java.util.ArrayList;

public class Area {
	private String name;
	private ArrayList<Position> polyPoints;
	private int sheepCount;
	private Polygon areaPoly;
	private static final int DOUBLEDESTROYER = 10000;
	
	/**
	 * 
	 * @param name The area name
	 * @param sheepCount Number of sheep in area
	 */
	public Area(String name, int sheepCount){
		setName(name);
		polyPoints = new ArrayList<Position>();
		areaPoly = new Polygon();
	}
	
	/**
	 * sets the name of the area 
	 * 
	 * @param name
	 */
	private void setName(String name){
		this.name = name;
	}
	
	/**
	 * 
	 * @return returns the area's name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * sets number of sheep in area
	 * 
	 * @param count
	 */
	public void setSheepCount(int count){
		this.sheepCount = count;
	}
	
	/**
	 * 
	 * @return the number of sheep
	 */
	public int getSheepCount(){
		return sheepCount;
	}
	
	/**
	 * 
	 * @param position ytterpunkt
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
	 * 
	 * @return returns the area as a polygon
	 */
	public Polygon getArea(){
		return areaPoly;
	}
	
}
