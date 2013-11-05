package characters;

import java.util.ArrayList;

public class Farm {
	
	private ArrayList<Area> areas;
	
	public Farm(ArrayList<Area> areas){
		this.areas = areas;
	}
	
	public Farm(){
		this.areas = new ArrayList<Area>();
	}
	
	public void addArea(Area area){
		this.areas.add(area);
	}
	
	public ArrayList<Area> getAreaList(){
		return this.areas;
	}
}
