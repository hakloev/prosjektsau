package simulation;

import java.util.ArrayList;
import java.util.Random;

import characters.Position;
import characters.Sheep;

/**
 * A simulation of sheep movement and attacks
 * 
 * @author.
 */

public class Simulation {
	private ArrayList<Sheep> sheepList;
	private long previousUpdateTime = 0;
	private long timeNow;
	private Random rand;
	private Position sheepLocation;
	public static final int EARTHRADIUS = 6378137;
	public static final int MSINDAY = 86400000;
	public static final int NUMBEROFUPDATESPERDAY = 10000;
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
			timeNow = System.currentTimeMillis() % MSINDAY; //MS after midnight
			previousUpdateTime = timeNow;
			for (Sheep currentSheep : sheepList){
				sign = rand.nextInt(2);
				sheepLocation = currentSheep.getLocation();
				
				if (sign == NEGATIVE){
					double latrads = (rand.nextDouble() * 10) / EARTHRADIUS;
					double longrads = (rand.nextDouble() * 10) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
					currentSheep.setLocation(sheepLocation.getLatitude() - latrads * 180 / Math.PI , sheepLocation.getLongitude() - longrads * 180 / Math.PI, sheepLocation.getHeight());
				}
				else{
					double latrads = (rand.nextDouble() * 10) / EARTHRADIUS;
					double longrads = ((rand.nextDouble() * 10)) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
					currentSheep.setLocation(sheepLocation.getLatitude() + latrads * 180 / Math.PI , sheepLocation.getLongitude() + longrads * 180 / Math.PI, sheepLocation.getHeight());

				}
				
				//check if a sheep attack should occur
				//update sheep in database	
				

				while (timeNow - previousUpdateTime <= updateInterval){
					timeNow = System.currentTimeMillis() % MSINDAY;
					//System.out.println(timeNow - previousUpdateTime);
				}//waits until enough time has passed	
				
				System.out.println("Sheep: " + currentSheep.getNick() + " Loc: " + sheepLocation.toString());
				
			}
			System.out.println(" ");
			previousUpdateTime = timeNow;
		}
	}
	
	public void sheepAttack(){
		//fucks shit up yo
	}
}
