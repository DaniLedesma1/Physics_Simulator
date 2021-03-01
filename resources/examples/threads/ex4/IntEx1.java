package tp.examples.threads.ex4;

public class IntEx1 implements Runnable {

	private String id;
	
	IntEx1(String id) {
		this.id = id;
	}

	public void run() {
		int i=0;
		while ( !Thread.interrupted() ) {
			System.out.println(id+": "+i);
			i++;
		}
		
		
	}
	
	public static void main(String args[]) throws InterruptedException {
		Thread t1 = new Thread( new IntEx1("TEx1") );
		t1.start();
		Thread.sleep(5000);
		t1.interrupt();
	}
}
