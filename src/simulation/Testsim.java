package simulation;

import java.util.ArrayList;

import characters.Farmer;
import characters.Sheep;

public class Testsim {
	
	public static void main(String[] args){
		ArrayList<Sheep> sheeplist = new ArrayList<Sheep>();
		Farmer farmer = new Farmer(0, "hahshahsd", "Farmerboyx", "email");
		sheeplist.add(new Sheep(0, "nick" + 0, 1990, farmer, 62.38123, 9.16686));
		
		Simulation sim = new Simulation(sheeplist);
		sim.runSimulation();
		
	}
}
