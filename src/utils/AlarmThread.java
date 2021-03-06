package utils;

import gui.AlarmPanel;
import characters.Alarm;
import serverconnection.JsonHandler;
import serverconnection.NetHandler;
import serverconnection.Response;

import java.util.ArrayList;

/**
 * Thread for AlarmHandling
 * @author Håkon Ødegård Løvdal
 */
public class AlarmThread extends Thread implements Runnable {

	private SwingThread swingRef;
	private boolean debug = false;
			;

	/**
	 * Constructor for AlarmThread
	 * @param swingRef reference to swingthread
	 */
	public AlarmThread(SwingThread swingRef) {
		this.swingRef = swingRef;
	}

	@Override
	public void run() {
		try {
			if (debug) {System.out.println("AlarmThread: Wait for SwingThread to complete init");}
			swingRef.join(); // Wait until SwingThread is completed/alive
			NetHandler handler = swingRef.getHandler();
			AlarmPanel alarmPanel = swingRef.getAlarmPanel();
			if (debug) {System.out.println("AlarmThread: Joined Swing");}
			if (debug) {System.out.println("AlarmThread: Got NetHandler and AlarmPanel");}

			while (true) {
				if (debug) {System.out.println("AlarmThread: Waiting for log in");}

				while (!handler.isLoggedIn()) {
					if (debug) {System.out.println("AlarmThread: Not logged in\nAlarmThread: Sleeping in half a second");}
					Thread.sleep(500);
				}

				if (debug) {System.out.println("AlarmThread: Logged in");}

				while (handler.isLoggedIn()) {
					if (debug) {System.out.println("AlarmThread: Sleeping one second before getting alarms");}
					Thread.sleep(1000); // Wait 1 seconds before getting alarms
					if (debug) {System.out.println("AlarmThread: Getting all alarms");}

					Response r = handler.getAlarm(-1);
					if (!handler.isError(r.msg)) {

						if (debug) {System.out.println(r.msg);}

						ArrayList<Alarm> alarms = JsonHandler.parseJsonAndReturnAlarms(r, swingRef.pf);
						if (!alarms.isEmpty()) {
							for (Alarm a : alarms) {
								alarmPanel.addAlarm(a);
							}
							if (debug) {System.out.println("AlarmThread: Added all alarms to alarmPanel");}
						}
					}
					if (debug) {System.out.println("AlarmThread: Sleeping for one minute");}

					Thread.sleep(10000); // Check for new alarms every 60 seconds  59000 + 1000 // 10000 for presentasjon
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Exception in AlarmThread");
		}
	}
}