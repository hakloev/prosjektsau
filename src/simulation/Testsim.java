package simulation;

import java.util.ArrayList;

import characters.Farmer;
import characters.Sheep;

/**
 * 
 * @author maxmelander
 *
 */
public class Testsim {
	
	public static void main(String[] args){
		ArrayList<Sheep> sheeplist = new ArrayList<Sheep>();
		Farmer farmer = new Farmer(0, "hahshahsd", "Farmerboyx", "email");
		for (int x = 0; x < 21; x++){
			Sheep sheep = new Sheep(x, "nick" + x, 1990, farmer, 100, 62.38123, 9.16686);
			sheep.setPulse(90);
			sheeplist.add(sheep);
		}
		
		Simulation sim = new Simulation(sheeplist);
		sim.runSimulation();
		
	}
}
