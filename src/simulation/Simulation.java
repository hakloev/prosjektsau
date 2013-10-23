package simulation;

import java.util.ArrayList;
import java.util.Random;

import characters.Position;
import characters.Sheep;

/**
 * A simulation of sheep movement and wolf attacks
 * 
 * @author maxmelander
 */

public class Simulation {
	private ArrayList<Sheep> sheepList;
	private long previousUpdateTime = 0;
	private long timeNow;
	private Random rand;
	private Position sheepLocation;
	public static final int EARTHRADIUS = 6378137;
	public static final int MSINDAY = 86400000;
	public static final int NUMBEROFUPDATESPERDAY = 50000;
	public static final int NEGATIVE = 0;
	public static final int POSITIVE = 1;
	
	/**
	 * The simulation constructor
	 * @param sheepList The list of sheep that should be moving around and stuff
	 */
	public Simulation(ArrayList<Sheep> sheepList) {
		//TODO: Getting the correct list from the database
		rand = new Random();
		this.sheepList = sheepList;
	}
	
	/**
	 * Starts the simulation 
	 */
	public void runSimulation(){
		boolean running = true;
		long updateInterval = MSINDAY/(sheepList.size() * NUMBEROFUPDATESPERDAY) ; //The interval between sheep updates
		int sign;
		
		while (running){
		previousUpdateTime = timeNow;

			for (Sheep currentSheep : sheepList){
				//waits until enough time has passed
				while (timeNow - previousUpdateTime <= updateInterval){
					timeNow = System.currentTimeMillis() % MSINDAY;
				}
				
				if (currentSheep.isDead()){
					continue;
				}
				sheepLocation = currentSheep.getLocation();
				
				sign = rand.nextInt(2);
				if (sign == NEGATIVE){
					double latrads = (rand.nextDouble() * 1000) / EARTHRADIUS;
					double longrads = (rand.nextDouble() * 1000) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
					currentSheep.setLocation(sheepLocation.getLatitude() - latrads * 180 / Math.PI , sheepLocation.getLongitude() - longrads * 180 / Math.PI);
				}
				else{
					double latrads = (rand.nextDouble() * 1000) / EARTHRADIUS;
					double longrads = ((rand.nextDouble() * 1000)) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
					currentSheep.setLocation(sheepLocation.getLatitude() + latrads * 180 / Math.PI , sheepLocation.getLongitude() + longrads * 180 / Math.PI);
				}
				
				if (currentSheep.getPulse() < 100 && !currentSheep.isDead()){
					currentSheep.setPulse(currentSheep.getPulse() + 10);
				}
				previousUpdateTime = timeNow;	

				System.out.println("ID: " + currentSheep.getIdNr() + " Lat: " + currentSheep.getLocation().getLatitude() + " Long: " + currentSheep.getLocation().getLongitude() + " Pulse: " + currentSheep.getPulse());
			}
			System.out.println("");
			
			//checks if a sheep attack should occur
			if (rand.nextInt(100) < 10){
				System.out.println("Wolf attack");
				sheepAttack();
			}
			previousUpdateTime = timeNow;
			
			//TODO: Add code for updating the database	
		}
	}
	
	/**
	 * The wolf tries to kill 5 sheep. 90% chance of stopping after every kill.  
	 */
	public void sheepAttack(){
		Sheep currentSheep = null;
		for (int i = 0; i < sheepList.size(); i++){
			int sheepIndex = rand.nextInt(sheepList.size());
			currentSheep = sheepList.get(sheepIndex);
			if (!currentSheep.isDead()){
				if (rand.nextInt(100) < 20){
					System.out.println("Kill success on sheep: " + sheepIndex);
					killSheep(currentSheep);
					
					//10% chance of trying to kill another sheep
					if (rand.nextInt(10) != 0){
						return;
					}
				}
				//Hurts a sheep if it is not killed
				else{
					currentSheep.setPulse(currentSheep.getPulse() - 50);
				}
			}
		}
	}
	
	/**
	 * Kills a sheep
	 * @param sheep Sheep that should be killed
	 */
	public void killSheep(Sheep sheep){
		sheep.setPulse(0);
	}
}
