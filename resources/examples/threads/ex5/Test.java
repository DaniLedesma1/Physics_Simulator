package tp.examples.threads.ex5;

public class Test extends Thread {

	static List f = new List();
	private int n;
	private String id;

	Test(int n, String id) {
		this.n = n;
		this.id = id;
	}

	public void run() {
		System.out.println("- Starting '"+id+"'");
		for (int i = 0; i < n; i++) {
			f.add(1);
		}
	}

	static public void main(String[] args) throws InterruptedException {
		Thread t1 = new Test(1000, "T1");
		Thread t2 = new Test(1000, "T2");
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println("Length: " + f.length());
	}

}
