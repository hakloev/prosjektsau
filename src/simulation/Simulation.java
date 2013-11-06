package simulation;
import java.util.ArrayList;
import java.util.Random;

import serverconnection.JsonHandler;
import serverconnection.NetHandler;
import serverconnection.NetHandler.MailTo;
import serverconnection.NetHandler.MailType;
import serverconnection.NetMain;
import serverconnection.Response;
import characters.Area;
import characters.Farmer;
import characters.Farm;
import characters.Position;
import characters.Sheep;

/**
 * Simulates sheep movement and diseases
 * @author maxmelander
 *
 */
public class Simulation {
	private 				ArrayList<Sheep> 	sheepList;
	private 				long 				previousUpdateTime 		= 0;
	private 				long 				timeNow;
	private					long				updateInterval;
	private 				Random 				rand;
	private 				Position 			sheepLocation;
	public static final 	int 				EARTHRADIUS 			= 6378137;
	public static final 	int 				MSINDAY 				= 86400000;
	public static final 	int 				NUMBEROFUPDATESPERDAY 	= 7000;
	public static final 	int 				NEGATIVE 				= 0;
	public static final 	int 				POSITIVE 				= 1;
	public static final		int					MOVEMENTSCALE			= 1000;
	public static final 	long				NORESPONSETIME			= 10000;
	private 				boolean 			simHasDisease;
	private 				Disease 			currentDisease;
	private 				int 				daysOfDisease 			= 0;
	private 				NetHandler 			netHandler;
	private					Response			response				= new Response();
	/**
	 * The simulation constructor
	 * @param sheepList The list of sheep that should be moving around and stuff. You know what i'm talking about-
	 */
	public Simulation() {
		netHandler = new NetHandler();
		netHandler.login("Simulering", "Simulering");
		sheepList = new ArrayList<Sheep>(JsonHandler.parseJsonAndReturnSheepList(netHandler.getSimulatorSheep(-1)));
		rand = new Random();
		simHasDisease = false;
	}
	
	/**
	 * Runs the simulation
	 */
	public void runSimulation(){
		for (Sheep sheep : sheepList){
			sheep.cure();
		}
		
		boolean running = true;
		int sign;
		
		while (running){
			
			while(sheepList.size() == 0){
				System.out.println("No response from server or no sheep in database");
				sheepList = new ArrayList<Sheep>(JsonHandler.parseJsonAndReturnSheepList(netHandler.getSimulatorSheep(-1)));
				try {
					Thread.sleep(NORESPONSETIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			previousUpdateTime = timeNow;
			updateInterval = MSINDAY/(sheepList.size() * NUMBEROFUPDATESPERDAY) ; //The interval between sheep updates
			
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
				
				//Random sheep movement
				sign = rand.nextInt(2);
				if (sign == NEGATIVE){
					if (rand.nextInt(2) == 0){
						double latrads = (rand.nextDouble() * MOVEMENTSCALE) / EARTHRADIUS;
						double longrads = (rand.nextDouble() * MOVEMENTSCALE) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
						currentSheep.setLocation(sheepLocation.getLatitude() - latrads * 180 / Math.PI , sheepLocation.getLongitude() - longrads * 180 / Math.PI);
					}
					else{
						double latrads = (rand.nextDouble() * MOVEMENTSCALE) / EARTHRADIUS;
						double longrads = (rand.nextDouble() * MOVEMENTSCALE) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
						currentSheep.setLocation(sheepLocation.getLatitude() - latrads * 180 / Math.PI , sheepLocation.getLongitude() + longrads * 180 / Math.PI);

					}
				}
				else{
					if (rand.nextInt(2) == 0){
						double latrads = (rand.nextDouble() * MOVEMENTSCALE) / EARTHRADIUS;
						double longrads = ((rand.nextDouble() * MOVEMENTSCALE)) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
						currentSheep.setLocation(sheepLocation.getLatitude() + latrads * 180 / Math.PI , sheepLocation.getLongitude() + longrads * 180 / Math.PI);
					}
					else{
						double latrads = (rand.nextDouble() * MOVEMENTSCALE) / EARTHRADIUS;
						double longrads = ((rand.nextDouble() * MOVEMENTSCALE)) / (EARTHRADIUS * Math.cos(Math.PI * sheepLocation.getLatitude() / 180));
						currentSheep.setLocation(sheepLocation.getLatitude() + latrads * 180 / Math.PI , sheepLocation.getLongitude() - longrads * 180 / Math.PI);

					}
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
				if (currentSheep.isInfected() && simHasDisease && (daysOfDisease > currentDisease.getIncubationPeriod())){
					currentSheep.setPulse(currentSheep.getPulse() - currentDisease.getDamage());
				}
				
				/*if (!isInArea(currentSheep)){
					netHandler.sendMail(MailType.SHEEP_ESCAPE, MailTo.USER_ID, ""+currentSheep.getFarmer().getFarmerId(), new String[]{""+currentSheep.getIdNr()}, null);
				}*/
				
				System.out.println("ID: " + currentSheep.getIdNr() + " Lat: " + currentSheep.getLocation().getLatitude() 
									+ " Long: " + currentSheep.getLocation().getLongitude() + " Pulse: " + currentSheep.getPulse() 
									+ " Name: " + currentSheep.getNick() + " Infected: " + currentSheep.isInfected());
				
				netHandler.updateSheep(currentSheep);
				previousUpdateTime = timeNow;
			}
			System.out.println("");
			
			sheepList = new ArrayList<Sheep>(JsonHandler.parseJsonAndReturnSheepList(netHandler.getSimulatorSheep(-1)));
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
	 * @param sheep The sheep that should be killed
	 */
	public void killSheep(Sheep sheep){
		sheep.setPulse(0);
		netHandler.updateSheep(sheep);

		
	}
	
	/**
	 * Generates a disease with some random attributes 
	 */
	public void generateDisease(){
		System.out.println("Disease generated");
		daysOfDisease = 0;
		currentDisease = new Disease(rand.nextDouble(), rand.nextInt(15), rand.nextInt(10), rand.nextInt(5), rand.nextInt(20));
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
	
	/**
	 * Infects a sheep
	 * @param index The index of sheep to infect
	 */
	public void infectSheep(int index){
		if (!sheepList.get(index).isInfected()){
			System.out.println("Sheep: " + index + " got infected");
			sheepList.get(index).innfect();
			
		}
	}
	/**
	 * A function to check distance between sheep
	 * @param sheep1 The first sheep
	 * @param sheep2 The second sheep
	 * @return Absolute value of the biggest distance in lat or long between the two sheep
	 */
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
	/**
	 * Checks if a sheep is in it's area
	 * @param sheep  The sheep to check
	 * @return Returns true or false depending on whether the sheep is in it's are or not
	 */
	/*private boolean isInArea(Sheep sheep){
		boolean inArea = false;
		for (Area area : netHandler.getAreas(sheep.getFarmID())){
			if (area.containsPosition(sheep.getLocation())){
				inArea = true;
			}
		}
		return inArea;
		
	}*/
}
