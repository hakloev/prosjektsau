package simulation;

import java.util.ArrayList;

import characters.Farmer;
import characters.Sheep;

public class Testsim {
	
	public static void main(String[] args){
		ArrayList<Sheep> sheeplist = new ArrayList<Sheep>();
		Farmer farmer = new Farmer(0, "hahshahsd", "Farmerboyx", "email");
		for (int x = 0; x < 11; x++){
			Sheep sheep = new Sheep(x, "nick" + x, 1990, farmer, 62.38123, 9.16686);
			sheep.setPulse(90);
			sheeplist.add(sheep);
		}
		
		Simulation sim = new Simulation(sheeplist);
		sim.runSimulation();
		
	}
}
