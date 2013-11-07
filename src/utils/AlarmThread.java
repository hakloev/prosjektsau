package utils;

import gui.AlarmPanel;
import serverconnection.Alarm;
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

	public AlarmThread(SwingThread swingRef) {
		this.swingRef = swingRef;
	}

	@Override
	public void run() {
		try {
			if (debug) {System.out.println("AlarmThread: Wait for SwingThread to complete init");}

			swingRef.join(); // Wait until SwingThread is completed/alive

			if (debug) {System.out.println("AlarmThread: Joined Swing");}

			NetHandler handler = swingRef.getHandler();
			AlarmPanel alarmPanel = swingRef.getAlarmPanel();

			if (debug) {System.out.println("AlarmThread: Got NetHandler and AlarmPanel");}
			if (debug) {System.out.println("AlarmThread: Waiting for log in");}

			while (!handler.isLoggedIn()) {
				if (debug) {System.out.println("AlarmThread: Not logged in\nSleeping in one second");}

				Thread.sleep(1000);
			}
			if (debug) {System.out.println("AlarmThread: Logged in");}

			while (true) {
				if (debug) {System.out.println("AlarmThread: Getting all alarms");}

				Response r = handler.getAlarm(-1);
				if (!handler.isError(r.msg)) {

					if (debug) {System.out.println(r.msg);}

					ArrayList<Alarm> alarms = JsonHandler.parseJsonAndReturnAlarms(r, swingRef.pf);
					for (Alarm a : alarms) {
						alarmPanel.addAlarm(a);
					}
					if (debug) {System.out.println("AlarmThread: Added all alarms to alarmPanel");}
					if (debug) {System.out.println("AlarmThread: Sleeping for five seconds");}
				}
				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			System.out.println("Exception in AlarmThread");
		}
	}




}
