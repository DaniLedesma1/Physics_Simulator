package tp.examples.threads.ex6;

public class BadWait {
	private volatile boolean cond = false;

	public void f() {
		System.out.println("f: started");
		while (!cond) {}
		System.out.println("f: cond = "+cond);
	}

	public void g() {
		System.out.println("g: started");
		sleepabit(15000);
		cond = true;
		System.out.println("g: setting cond to "+cond);
	}

	private void sleepabit(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
		}
	}

	static public void main(String[] args) {
		final BadWait x = new BadWait();

		Thread t1 = new Thread() {
			public void run() {
				x.f();
			}
		};

		Thread t2 = new Thread() {
			public void run() {
				x.g();
			}
		};
		
		t1.start();
		t2.start();		
	}

}
