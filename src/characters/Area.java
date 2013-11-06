package characters;

import java.awt.geom.Path2D;
import java.util.ArrayList;

public class Area {
	private Path2D areaPoly;
	private ArrayList<Position> areaPoints;
	private String name;
	private int farmID, id, list_pos;
	
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
	// get
	public int getList_pos() 				{ return list_pos; 	}
	public int getFarmID() 					{ return farmID; 	}
	public String getName() 				{ return name; 		}
	public int getId() 						{ return id; 		}
		
}