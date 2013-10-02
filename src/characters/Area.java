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
	
	private void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setSheepCount(int count){
		this.sheepCount = count;
	}
	
	public int getSheepCount(){
		return sheepCount;
	}
	
	public void addPolyPoint(Position position){
		polyPoints.add(position);
	}
	
	public void createPoly(){
		for (Position pos : polyPoints){
			areaPoly.addPoint((int)(pos.getLongtidude() * DOUBLEDESTROYER), (int)(pos.getLatitude() * DOUBLEDESTROYER));
		}
		
	}
	
}
