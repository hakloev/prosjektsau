package utils;

import gui.AlarmPanel;
import gui.ProgramFrame;
import serverconnection.NetHandler;
import serverconnection.Response;

/**
 * Created with IntelliJ IDEA.
 * User: hakloev
 * Date: 06/11/13
 * Time: 14:35
 */
public class AlarmThread extends Thread {

	private Runnable swingThread;

	public AlarmThread(Runnable swingThread) {
		this.swingThread = swingThread;
	}


	@Override
	public void run() {
		try {
			swingThread.
			handler = pf.getNetHandler();
			ap = pf.getAlarmPanel();
			System.out.println("run");
			System.out.println(handler.isLoggedIn());
			while (!handler.isLoggedIn()) {
				swingThread.wait();
				System.out.println("not logged in");
				System.out.println("while");
				swingThread.notify();
			}
		} catch  (InterruptedException e)  {
			System.out.println("interupt");
		}
	}



}
