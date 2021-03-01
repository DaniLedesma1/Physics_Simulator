package tp.examples.threads.ex4;

public class IntEx2 implements Runnable {

	private String id;
	
	IntEx2(String id) {
		this.id = id;
	}

	public void run() {
		int i=0;
		while ( !Thread.interrupted() ) {
			System.out.println(id+": "+i);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				System.out.println("Int while sleeping");
				Thread.currentThread().interrupt();
			}
			i++;
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		Thread t1 = new Thread( new IntEx2("TEx1") );
		t1.start();
		Thread.sleep(5000);
		t1.interrupt();
	}
}
