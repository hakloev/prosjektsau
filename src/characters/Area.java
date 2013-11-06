package characters;

import java.awt.geom.Path2D;
import java.util.ArrayList;

/**
 * Class with area polygon. Used to check if a position is contained within the area.
 * @author maxmelander, madsmidtlyng
 *
 */
public class Area {
	private Path2D areaPoly;
	private ArrayList<Position> areaPoints;
	private String name;
	private int farmID, id, list_pos;
	
	/**
	 * 
	 * @param areaPoints The coordinate points used to create the area polygon
	 */
	public Area(String name, int farmID, ArrayList<Position> areaPoints){
		this.name = name;
		this.farmID = farmID;
		areaPoly = new Path2D.Double();
		this.areaPoints = areaPoints;
		createPoly();				
	}
	
	/**
	 * Creates a polygon, using the list of positions, areaPoints
	 */
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
	/**
	 * 
	 * @return returns the area polygon
	 */
	public Path2D returnAreaPoly(){
		return areaPoly;
	}
	
	public ArrayList<Position> getAreaPoints(){
		return areaPoints;
	}
	/**
	 * 
	 * @param position 	the position to check
	 * @return 			returns true or false depending on if the given position if contained within the area polygon or not
	 */
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