package simulation;

public class Disease {
	private double spreadDistance;
	private int spreadChance;
	private int damage;
	private int incubationPeriode;
	private int diseaseLength;
	
	public Disease(double distance, int chance, int damage, int periode, int length){
		this.spreadDistance = distance / 10;
		this.spreadChance = chance;
		if (damage == 0){
			this.damage = 5;
		}
		else if(damage == 1){
			this.damage = 10;
		}
		else{
			this.damage = 15;
		}
		if (periode >= length - 5){
			periode = 0;
		}
		else{
			this.incubationPeriode = periode;
		}
		if (length > 5){
			this.diseaseLength = length;
		}
		else{
			this.diseaseLength = 5;
		}
	}

	public double getSpreadDistance() {
		return spreadDistance;
	}

	public int getSpreadChance() {
		return spreadChance;
	}

	public int getDamage() {
		return damage;
	}

	public int getIncubationPeriode() {
		return incubationPeriode;
	}

	public int getDiseaseLength() {
		return diseaseLength;
	}
	

}
