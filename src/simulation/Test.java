package simulation;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingUtilities;

import characters.Sheep;

import gui.MapPanel;

public class Test {

	public static void main(String[] args){
		ArrayList<Sheep> sheepList = new ArrayList<Sheep>();
		Random rand = new Random();
		for (int i = 0; i < 11; i++){
			Sheep sheep = new Sheep(i, "Nick" + i, 1991, 201);
			sheep.setPulse(100); 
			sheep.setLocation(rand.nextInt(20), rand.nextInt(20), rand.nextInt(20));
			sheepList.add(sheep);
		}
		Simulation sim = new Simulation(sheepList);
		sim.runSimulation();
	}
}
