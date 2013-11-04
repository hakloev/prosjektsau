package characters;

import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Area {
	private Path2D areaPoly;
	private ArrayList<Position> areaPoints;
	
	
	public Area(ArrayList<Position> areaPoints){
		areaPoly = new Path2D.Double();
		this.areaPoints = areaPoints;
		createPoly(areaPoints);				
	}
	
	private void createPoly(ArrayList<Position> areaPoints){
		boolean isFirst = true;
		for (Position position : areaPoints){
			if (isFirst){
				areaPoly.moveTo(position.getLatitude(), position.getLongitude());
			}
			else{
				areaPoly.lineTo(position.getLatitude(), position.getLongitude());
			}
		}
		areaPoly.closePath();
	}
	
	public Path2D returnAreaPoly(){
		return areaPoly;
	}
	
	public boolean containsPosition(Position position){
		return areaPoly.contains(position.getLatitude(), position.getLongitude());
	}
	
	
}