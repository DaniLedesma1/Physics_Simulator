package tp.examples.threads.ex1;


public class ThreadEx4 implements Runnable {

	private String id;
	
	ThreadEx4(String id) {
		this.id = id;
	}

	public void run() {
		int i=0;
		while (i<1000) {
			System.out.println(id+": "+i);
			i++;
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		Thread t1 = new Thread( new ThreadEx4("TEx1") );
		Thread t2 = new Thread( new ThreadEx4("TEx2") );
		t1.start();
		t2.start();
		//t1.join();
		//t2.join();
		System.out.println("Main is done!");
	}
}
