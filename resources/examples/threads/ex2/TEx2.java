package tp.examples.threads.ex2;

public class TEx2 implements Runnable {

	private String id;
	
	TEx2(String id) {
		this.id = id;
	}
	
	public void run() {
		int i=0;
		while (i<100) {
			System.out.println(id+": "+i);
			i++;
		}
	}
}

