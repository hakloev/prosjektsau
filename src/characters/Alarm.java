package characters;

import java.text.*;
import java.util.Date;

/**
 * Class holding information about an alarm
 * @author Håkon Ødegård Løvdal
 */
public class Alarm {

	private int id;
	private Sheep sheep;
	private Date date;
	private String description;

	/**
	 * Constructor, takes two parameters
	 * @param sheep sheep-object the alarm is about
	 * @param description description of the alarm
	 */
	public Alarm(int id, Sheep sheep, String date, String description) {
		this.id = id;
		this.sheep = sheep;
		this.description = description;
		setDate(date);
	}

	/**
	 * Method to set the date with format
	 * @param date String containing the date
	 */
	private void setDate(String date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmm");
		try {
			this.date = sf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Parse-date error in Alarm-class");
		}
	}

	/**
	 * Method to get the alarm id
	 * @return the alarms id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Method to get the alarm position
	 * @return String containing position of sheep
	 */
	public String getAlarmPostition() {
		return sheep.getLocation().getLatitude() + ", " + sheep.getLocation().getLongitude();
	}

	/**
	 * Method to get the alarm date
	 * @return string containing the alarm date
	 */
	public String getAlarmDate() {
		return date.toString();
	}

	/**
	 * Get the alarm description
	 * @return The alarm description as String
	 */
	public String getAlarmDescription() {
		return description;
	}

	/**
	 * Get the sheep
	 * @return sheep object with alarm
	 */
	public Sheep getSheep() {
		return this.sheep;
	}

	/**
	 * toString used to show sheep in alarmList
	 * @return string in a given format
	 */
	@Override
	public String toString() {
		return "(" + sheep.getIdNr() + ") " +  sheep.getNick();
	}
}
