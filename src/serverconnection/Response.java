package serverconnection;

// Use this to handle server responses and track time elapsed from method start to return.
public class Response {
	long start = 0, stop = 0, elapsed = 0;
	public String msg = null;
	public Response() 			{}
	public Response(String m) 	{ set(m);				 	     }
	public void set(String m)	{ msg 		= m; 				 }
	public void start() 		{ start 	= System.nanoTime(); }
	public void stop() 			{ stop 		= System.nanoTime(); }
	public long nanotime() 		{ return 	stop - start; 		 }
	public double time()		{ return (double) nanotime() / 1000000000.0; }
	public void consoletime()	{ System.out.println("Tid brukt: " + time() + " sekunder."); }
}