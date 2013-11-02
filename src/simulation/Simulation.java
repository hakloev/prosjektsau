package simulation;
import java.util.ArrayList;
import java.util.Random;

import serverconnection.JsonHandler;
import serverconnection.NetHandler;
import serverconnection.NetMain;
import serverconnection.Response;
import characters.Position;
import characters.Sheep;

public class Simulation {
	private 				ArrayList<Sheep> 	sheepList;
	private 				long 				previousUpdateTime 		= 0;
	private 				long 				timeNow;
	private 				Random 				rand;
	private 				Position 			sheepLocation;
	public static final 	int 				EARTHRADIUS 			= 6378137;
	public static final 	int 				MSINDAY 				= 86400000;
	public static final 	int 				NUMBEROFUPDATESPERDAY 	= 7000;
	public static final 	int 				NEGATIVE 				= 0;
	public static final 	int 				POSITIVE 				= 1;
	public static final		int					MOVEMENTSCALE			= 10000;
	private 				boolean 			simHasDisease;
	private 				Disease 			currentDisease;
	private 				int 				daysOfDisease 			= 0;
	private 				NetHandler 			netHandler;
	private					Response			response;
	/**
	 * The simulation constructor
	 * @param sheepList The list of sheep that should be moving around and stuff. You know what i'm talking about-
	 */
	public Simulation() {
		netHandler = new NetHandler();
		netHandler.login("Simulering", "Simulering");
		sheepList = JsonHandler.parseJsonAndReturnSheepList(netHandler.getSimulatorSheep(-1));
		rand = new Random();
		simHasDisease = false;
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
					netHandler.updateSheep(currentSheep);
					continue;
				}
				sheepLocation = currentSheep.getLocation();
				
				sign = rand.nextInt(2);
				if (sign == NEGATIVE){
					double latrads = (rand.nextDouble() * MOVEMENTSCALE) / EARTHRADIUS;
					double longrads = (rand.nextDouble() * MOVEMENTSCALE) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
					currentSheep.setLocation(sheepLocation.getLatitude() - latrads * 180 / Math.PI , sheepLocation.getLongitude() - longrads * 180 / Math.PI);
				}
				else{
					double latrads = (rand.nextDouble() * MOVEMENTSCALE) / EARTHRADIUS;
					double longrads = ((rand.nextDouble() * MOVEMENTSCALE)) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
					currentSheep.setLocation(sheepLocation.getLatitude() + latrads * 180 / Math.PI , sheepLocation.getLongitude() + longrads * 180 / Math.PI);
				}
				
				//Sheep gain health every day they are not sick or at full health
				if (currentSheep.getPulse() < 100 && !currentSheep.isDead() && !currentSheep.isInfected()){
					currentSheep.setPulse(currentSheep.getPulse() + 10);
					if (currentSheep.getPulse() > 100){
						currentSheep.setPulse(100);
					}
				}
				
				//Check if current sheep should get infected
				if (!currentSheep.isInfected() && simHasDisease){
					for (Sheep infSheep : sheepList){
						if (infSheep.isInfected() && (distanceBetween(currentSheep, infSheep) < currentDisease.getSpreadDistance()) && (rand.nextInt(100) < currentDisease.getSpreadChance())){
							infectSheep(sheepList.indexOf(currentSheep));
						}
					}
				}
				//Causes health decrease based of current disease
				if (currentSheep.isInfected() && (daysOfDisease > currentDisease.getIncubationPeriod())){
					currentSheep.setPulse(currentSheep.getPulse() - currentDisease.getDamage());
				}
				System.out.println("ID: " + currentSheep.getIdNr() + " Lat: " + currentSheep.getLocation().getLatitude() 
									+ " Long: " + currentSheep.getLocation().getLongitude() + " Pulse: " + currentSheep.getPulse() 
									+ " Name: " + currentSheep.getNick());
				netHandler.updateSheep(currentSheep);
				previousUpdateTime = timeNow;
			}
			System.out.println("");
			
			//checks if the disease should end based on how long it has lasted
			if (simHasDisease){
				daysOfDisease++;
				if (daysOfDisease > currentDisease.getDiseaseLength()){
					System.out.println("Disease ended");
					for (Sheep infSheep : sheepList){
						infSheep.cure();
					}
					simHasDisease = false;
				}
			}
			
			//checks if a wolf attack should occur
			if (rand.nextInt(100) < 10){
				System.out.println("Wolf attack");
				sheepAttack();
			}
			
			//checks if a new disease should be generated
			if (rand.nextInt(100) < 20 && simHasDisease == false){
				generateDisease();
				simHasDisease = true;
			}
			sheepList = JsonHandler.parseJsonAndReturnSheepList(netHandler.getSimulatorSheep(-1));
			previousUpdateTime = timeNow;
			
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
	
	//Infects one sheep with a randomly generated disease
	public void generateDisease(){
		System.out.println("Disease generated");
		daysOfDisease = 0;
		currentDisease = new Disease(rand.nextDouble(), rand.nextInt(20), rand.nextInt(10), rand.nextInt(5), rand.nextInt(20));
		int breakoutSheepIndex = rand.nextInt(sheepList.size());
		int numberoftries = 0;
		while (sheepList.get(breakoutSheepIndex).isDead() && numberoftries < 10){
			breakoutSheepIndex = rand.nextInt(sheepList.size());
			numberoftries ++;
			if (numberoftries >= 10){
				return;
			}
		}
		infectSheep(breakoutSheepIndex);
	}
	
	//Infects a given sheep if it is not already infected
	public void infectSheep(int index){
		if (!sheepList.get(index).isInfected()){
			System.out.println("Sheep: " + index + " got infected");
			sheepList.get(index).innfect();
		}
	}
	
	//Returns the biggest difference in location between two sheep
	public double distanceBetween(Sheep sheep1, Sheep sheep2){
		double longdist = Math.abs(sheep1.getLocation().getLongitude() - sheep2.getLocation().getLongitude());
		double latdist = Math.abs(sheep1.getLocation().getLatitude() - sheep2.getLocation().getLatitude());
		
		if (longdist >= latdist){
			return longdist;
		}
		else{
			return latdist;
		}
	}
}
