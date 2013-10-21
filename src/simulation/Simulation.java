package simulation;

import java.util.ArrayList;
import java.util.Random;

import characters.Position;
import characters.Sheep;

/**
 * A simulation of sheep movement and attacks
 * 
 * @author Max Melander
 */

public class Simulation {
	private ArrayList<Sheep> sheepList;
	private long previousUpdateTime = 0;
	private long timeNow;
	private Random rand;
	private Position sheepLocation;
	public static final int EARTHRADIUS = 6378137;
	public static final int MSINDAY = 86400000;
	public static final int NUMBEROFUPDATESPERDAY = 100000;
	public static final int NEGATIVE = 0;
	public static final int POSITIVE = 1;

	public Simulation(ArrayList<Sheep> sheepList) {
		rand = new Random();
		this.sheepList = sheepList;
	}

	public void runSimulation(){
		boolean running = true;
		long updateInterval = MSINDAY/(sheepList.size() * NUMBEROFUPDATESPERDAY) ; //The interval between sheep updates
		int sign;
		
		while (running){
		previousUpdateTime = timeNow;

			for (Sheep currentSheep : sheepList){
				if (currentSheep.isDead()){
					continue;
				}
				sheepLocation = currentSheep.getLocation();
				
				sign = rand.nextInt(2);
				if (sign == NEGATIVE){
					double latrads = (rand.nextDouble() * 100) / EARTHRADIUS;
					double longrads = (rand.nextDouble() * 100) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
					currentSheep.setLocation(sheepLocation.getLatitude() - latrads * 180 / Math.PI , sheepLocation.getLongitude() - longrads * 180 / Math.PI);
				}
				else{
					double latrads = (rand.nextDouble() * 100) / EARTHRADIUS;
					double longrads = ((rand.nextDouble() * 100)) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
					currentSheep.setLocation(sheepLocation.getLatitude() + latrads * 180 / Math.PI , sheepLocation.getLongitude() + longrads * 180 / Math.PI);
				}												
				//waits until enough time has passed
				while (timeNow - previousUpdateTime <= updateInterval){
					timeNow = System.currentTimeMillis() % MSINDAY;
				}
				previousUpdateTime = timeNow;	

			}
			
			//check if a sheep attack should occur
			if (rand.nextInt(100) < 10){
				sheepAttack();
			}
			previousUpdateTime = timeNow;
			
			//update sheep in database	
		}
	}
	
	public void sheepAttack(){
		for (int i = 0; i < sheepList.size(); i++){
			int sheepIndex = rand.nextInt(sheepList.size());
			if (sheepList.get(i).getPulse() != 0){
				if (rand.nextInt(100) < 50){
					killSheep(sheepList.get(sheepIndex));
					return;
				}
			}
		}
	}

	public void killSheep(Sheep sheep){
		sheep.setPulse(0);
	}
}
