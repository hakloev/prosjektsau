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
	private long previousUpdateTime;
	private long timeNow;
	private Random rand;
	private Position sheepLocation;
	public static final int MSINDAY = 86400000;
	public static final int NUMBEROFUPDATESPERDAY = 3;
	public static final int NEGATIVE = 0;
	public static final int POSITIVE = 1;

	public Simulation() {
		rand = new Random();
		sheepList = new ArrayList<Sheep>();
	}
	
	public void runSimulation(){
		boolean running = true;
		long updateInterval = MSINDAY/(sheepList.size() * NUMBEROFUPDATESPERDAY) ; //The interval between sheep updates
		int sign;
		
		while (running){
			timeNow = System.currentTimeMillis() % MSINDAY; //MS after midnight		
			for (Sheep currentSheep : sheepList){
				sign = rand.nextInt(2);
				sheepLocation = currentSheep.getLocation();
				if (sign == NEGATIVE){
					//decrement sheep position
				}
				else{
					//increment sheep position
				}
				
				//check if a sheep attack should occur
				//update sheep in database
				
				while (timeNow - previousUpdateTime <= updateInterval){}//waits until enough time has passed	
			}
			previousUpdateTime = timeNow;
		}
	}
	
	public void sheepAttack(){
		//fucks shit up yo
	}
}
