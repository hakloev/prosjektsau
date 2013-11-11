package characters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for holding log-information
 * @author Håkon Ødegård Løvdal
 */
public class LogItem {

	private int logId;
	private Date date;
	private Position pos;
	private int pulse;
	private int highestPulse;
	private Date pulseDate;
	private int weight;
	private int age;
	private String nick;
	private int sheepId;

	/**
	 * Constructor for logitem
	 * @param logId log id number
	 * @param date date log created as string
	 * @param lat latitude for sheep
	 * @param longt longtiude for sheep
	 * @param pulse sheeps pulse on creation
	 * @param highestPulse highest puls measured
	 * @param pulseDate date for highest pulse as string
	 * @param weight sheeps weight
	 * @param age sheeps age
	 * @param nick sheeps nick
	 * @param sheepId sheeps id
	 */
	public LogItem(int logId, String date, double lat, double longt, int pulse, int highestPulse, String pulseDate, int weight, int age, String nick, int sheepId) {
		this.logId = logId;
		this.date = setDate(date);
		this.pos = setPosition(lat, longt);
		this.pulse = pulse;
		this.highestPulse = highestPulse;
		this.pulseDate = setDate(pulseDate);
		this.weight = weight;
		this.age = age;
		this.nick = nick;
		this.sheepId = sheepId;
	}

	/**
	 * Method used to parse and set date on invokation
	 * @param date date as string
	 * @return date as Date-object
	 */
	private Date setDate(String date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d = null;
		try {
			d = sf.parse(date);
		} catch (ParseException e) {
			System.out.println("Parse-date error in LogItem-class");
		}
		return d;
	}

	/**
	 * Method to set position
	 * @param lat double latitide
	 * @param longt double longitide
	 * @return Position-object
	 */
	private Position setPosition(double lat, double longt) {
		return new Position(lat, longt);
	}

	/**
	 * Returns date as String
	 * @return String containing date
	 */
	public String getDateAsString(){
		return date.toString();
	}

	/**
	 * Method to get log id
	 * @return log id as int
	 */
	public int getLogId() {
		return logId;
	}

	@Override
	public String toString() {
		return  "Logg-ID: " + logId +
				"\n\nDato: " + date +
				"\n\nBreddegrad: " + pos.getLatitude() +
				"\nLengdegrad: " + pos.getLongitude() +
				"\n\nPulse: " + pulse +
				"\nHøysete puls: " + highestPulse +
				"\nDato for høyeste puls: " + pulseDate +
				"\n\nVekt: " + weight +
				"\nAlder: " + age +
				"\nKallenavn: " + nick  +
				"\nSau-ID: " + sheepId +
				"\n\n====================================\n";
	}
}
