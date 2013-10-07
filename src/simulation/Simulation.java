package simulation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Klasse for aa simulere sauenes posisjonendringer
 * 
 * @author Max er best, Thomas er en god nummer to.
 */

public class Simulation {
	private long previousUpdateTime = System.currentTimeMillis();
	private long timeNow;
	private int numberofSheep;

	public Simulation() {
		
	}//simulation
	
	public void runSimulation(){
		boolean running = true;
		int updateIntervalLimit = 1000; // tid i MS som sier hvor ofte sau skal oppdateres.
		ArrayList<Integer> sheepIdList = new ArrayList<Integer>();  //faar alle saueID som liste
		//
		//kode for � hente id fra DB for legge dem i liste
		//

		while (running){
			timeNow = System.currentTimeMillis();

			for (int sheepID : sheepIdList){
				//
				//kode for � sende inkrementering/dekrementering for sauePOS til DB
				//
				System.out.println("sheep " + sheepID + " updated." );


				//klokka n� minus hva klokka var istad = timePassed siden forrige update
				while (timeNow - previousUpdateTime <= updateIntervalLimit){ 
					//vent med � gaa videre til det er tid for neste update.
				
				}//while wait for neste udpdate
			}//for sheep
		}//while running
	}

	// metode som tar inn en sau og oppdaterer en posisjon til denne. 
	private void updateSheep (int sheepId){
	}
}
