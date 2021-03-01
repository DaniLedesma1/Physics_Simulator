package tp.examples.threads.ex1;

public class ThreadEx2 implements Runnable {

	private String id;
	
	ThreadEx2(String id) {
		this.id = id;
	}
	
	public void run() {
		int i=0;
		while (i<100) {
			System.out.println(id+": "+i);
			i++;
		}
	}
	
	
	public static void main(String args[]) {
		Thread t1 = new Thread( new ThreadEx2("TEx1") );
		Thread t2 = new Thread( new ThreadEx2("TEx2") );
		t1.start();
		t2.start();
		
		System.out.println("Main is done!");
	}
}
