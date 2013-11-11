package simulation;

/**
 * 
 * @author maxmelander
 *
 */
public class Disease {
	private double spreadDistance;
	private int spreadChance;
	private int damage;
	private int incubationPeriod;
	private int diseaseLength;
	
	/**
	 * 
	 * @param distance The max distance between sheep for the disease to spread
	 * @param chance The chance of the disease spreading when two sheep inside distance
	 * @param damage The damage the disease does each simulation update
	 * @param period The incubation period, where spread happens but no damage is done
	 * @param length How long the disease lasts
	 */
	public Disease(double distance, int chance, int damage, int period, int length){
		this.spreadDistance = distance / 20;
		this.spreadChance = chance;
		if (damage == 0){
			this.damage = 5;
		}
		
		this.damage = damage;
		
		if (period >= length - 5){
			period = 0;
		}
		else{
			this.incubationPeriod = period;
		}
		if (length > 5){
			this.diseaseLength = length;
		}
		else{
			this.diseaseLength = 5;
		}
	}
	/**
	 * Returns the maximum spread distance
	 * @return Returns the maximum spread distance
	 */
	public double getSpreadDistance() {
		return spreadDistance;
	}
	
	/**
	 * Returns the chance of the disease spreading
	 * @return Returns the chance of the disease spreading
	 */
	public int getSpreadChance() {
		return spreadChance;
	}
	
	/**
	 * Returns damage
	 * @return Returns damage
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Returns the incubation period
	 * @return Returns the incubation period
	 */
	public int getIncubationPeriod() {
		return incubationPeriod;
	}
	
	/**
	 * Returns how long the disease lasts
	 * @return Returns how long the disease lasts
	 */
	public int getDiseaseLength() {
		return diseaseLength;
	}
	

}
