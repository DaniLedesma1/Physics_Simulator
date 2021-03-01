package tp.examples.threads.ex1;

public class ThreadEx1 extends Thread {

	private String id;
	
	ThreadEx1(String id) {
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
		Thread t1 = new ThreadEx1("TEx1");
		Thread t2 = new ThreadEx1("TEx2");
		t1.start();
		t2.start();
		System.out.println("Main is done!");
	}
}
