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

	private Position setPosition(double lat, double longt) {
		return new Position(lat, longt);
	}

	public String getDateAsString(){
		return date.toString();
	}

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
