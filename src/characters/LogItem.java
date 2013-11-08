package characters;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hakloev
 * Date: 08/11/13
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
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

	public LogItem(int logId, Date date, Position pos, int pulse, int highestPulse, Date pulseDate, int weight, int age, String nick, int sheepId) {
		this.logId = logId;
		this.date = date;
		this.pos = pos;
		this.pulse = pulse;
		this.highestPulse = highestPulse;
		this.pulseDate = pulseDate;
		this.weight = weight;
		this.age = age;
		this.nick = nick;
		this.sheepId = sheepId;
	}

	@Override
	public String toString() {
		return "derp" +
				"LogID=" + logId +
				", date=" + date +
				", pos=" + pos +
				", pulse=" + pulse +
				", highestPulse=" + highestPulse +
				", pulseDate=" + pulseDate +
				", weight=" + weight +
				", age=" + age +
				", nick='" + nick + '\'' +
				", sheepId=" + sheepId +
				'}';
	}
}
