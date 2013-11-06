package characters;

import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Area {
	private Path2D areaPoly;
	private ArrayList<Position> areaPoints;
	private String name;
	private int farmID, id, list_pos;
	private String coordinates;
	
	public Area(ArrayList<Position> areaPoints){
		areaPoly = new Path2D.Double();
		this.areaPoints = areaPoints;
		createPoly();				
	}
	
	private void createPoly(){
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

	// set
	public void setList_pos(int list_pos) 	{ this.list_pos = list_pos; }
	public void setFarmID(int farmID) 		{ this.farmID 	= farmID; 	}
	public void setName(String name) 		{ this.name 	= name; 	}
	public void setId(int id) 				{ this.id 		= id; 		}
	public void setCoordinates(String c) 	{ this.coordinates = c; 	} // return all points as "1.2,5.1,4.6," etc. (merged to string)
	
	// get
	public int getList_pos() 				{ return list_pos; 	}
	public int getFarmID() 					{ return farmID; 	}
	public String getName() 				{ return name; 		}
	public int getId() 						{ return id; 		}
	public String getCoordinates() 			{ return coordinates; }
	
	
		
}