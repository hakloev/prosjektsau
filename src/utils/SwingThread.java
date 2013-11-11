package utils;

import gui.AlarmPanel;
import gui.ProgramFrame;
import serverconnection.NetHandler;

/**
 * Swing-thread, to invoke the clientprogram
 * @author Håkon Ødegård Løvdal
 */
public class SwingThread extends Thread implements Runnable {

	protected ProgramFrame pf;
	private boolean debug = false;

	@Override
	public void run() {
		try {
			if (debug) {System.out.println("Swing: Init ProgramFrame");}

			pf = new ProgramFrame();

			if (debug) {System.out.println("Swing: Init ProgramFrame Completed");}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in SwingThread");
		}
	}

	/**
	 * Get the netHandler from programFrame to use in other threads
	 * @return netHandler object
	 */
	protected NetHandler getHandler() {
		return pf.getNetHandler();
	}

	/**
	 * Get the AlarmPanel from programFrame to use in other threads
	 * @return alarmPanel object
	 */
	protected AlarmPanel getAlarmPanel() {
		return pf.getAlarmPanel();
	}
}
