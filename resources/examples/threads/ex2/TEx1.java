package tp.examples.threads.ex2;

public class TEx1 implements Runnable {

	private String id;
	
	TEx1(String id) {
		this.id = id;
	}
	
	public void run() {
		int i=0;
		while (true) {
			System.out.println(id+": "+i);
			i++;
		}
	}
}

