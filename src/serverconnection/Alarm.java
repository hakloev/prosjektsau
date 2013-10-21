package serverconnection;

import java.util.Date;

import characters.Sheep;

/**
 * Class holding information about an alarm
 * @author Håkon Ødegård Løvdal
 */
public class Alarm {

	private Sheep sheep;
	private Date date;
	private String description;

	/**
	 * Constructor, takes two parameters
	 * @param sheep sheep-object the alarm is about
	 * @param description description of the alarm
	 */
	public Alarm(Sheep sheep, String description) {
		this.sheep = sheep;
		this.description = description;
		this.date = new Date();
	}

	public String getAlarmPostition() {
		return sheep.getLocation().getLatitude() + ", " + sheep.getLocation().getLongitude();
	}
	
	public String getAlarmDate() {
		return date.toString();
	}
	
	public String getAlarmDescription() {
		return description;
	}
	
	public String getSheepId() {
		return "Sau-ID: " + this.sheep.getIdNr() + "  //  Nick: " + this.sheep.getNick();
	}
	
	@Override
	public String toString() {
		return "Sau-ID: " + sheep.getIdNr() + " (" + sheep.getNick() + ")";
	}
	
	
	
	
}
