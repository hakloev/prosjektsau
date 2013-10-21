package serverconnection;

import java.util.Date;

import characters.Sheep;

public class Alarm {

	private Sheep sheep;
	private Date date;
	private String description;
	
	public Alarm(Sheep sheep, String description) {
		this.sheep = sheep;
		this.description = description;
		this.date = new Date();
	}
	
	public String getAlarmPostition() {
		return sheep.getLocation().getLatitude() + ", " + sheep.getLocation().getLongtidude();
	}
	
	public String getAlarmDate() {
		return date.toString();
	}
	
	public String getAlarmDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return "ID: " + sheep.getIdNr();
	}
	
	
	
	
}
