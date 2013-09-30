package simulation;

import java.util.ArrayList;
/**
 * Klasse for aa simulere sauenes posisjonendringer
 * 
 * @author Max er best, Thomas er en god nummer to.
 */


public class Simulation {
	private long previousUpdateTime = System.currentTimeMillis();
	private long timeNow;

	public Simulation() {
		boolean running = true;
		int updateIntervalLimit = 1000; // tid i MS som sier hvor ofte sau skal oppdateres.
		ArrayList<Integer> sheepIdList = new ArrayList<>();  //faar alle saueID som liste
		//
		////kode for å hente id fra DB for legge dem i liste
		//

		while (running){
			timeNow = System.currentTimeMillis();

			for (int sheepID : sheepIdList){
				//
				////kode for å sende inkrementering/dekrementering for sauePOS til DB
				//
				System.out.println("sheep " + sheepID + " updated." );


				//klokka nå minus hva klokka var istad = timePassed siden forrige update
				while (timeNow - previousUpdateTime <= updateIntervalLimit){ 
					//vent med å gaa videre til det er tid for neste update.
				
				}//while wait for neste udpdate
			}//for sheep
		}//while running
	}//simulation









	// metode som tar inn en sau og oppdaterer en posisjon til denne. 
	private void updateSheep (int sheepId){


		previousUpdateTime = System.currentTimeMillis();

	}

}
