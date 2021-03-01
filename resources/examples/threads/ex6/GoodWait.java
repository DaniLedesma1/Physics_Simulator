package tp.examples.threads.ex6;

public class GoodWait {
	boolean cond = false;

	public  void f() {
		System.out.println("f: started");
		while (!cond) {
			// ....
			synchronized (this) {
			try {
				if (!cond) this.wait();
			} catch (InterruptedException e) {
			}
			}
		}
		System.out.println("f: cond = "+cond);
	}

	public synchronized void g() {
		System.out.println("g: started");
		sleepabit(15000);
		cond = true;
		System.out.println("g: setting cond to " + cond);
		this.notify();
	}

	private void sleepabit(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
		}
	}

	static public void main(String[] args) {
		final GoodWait x = new GoodWait();

		new Thread() {
			public void run() {
				x.f();
			}
		}.start();

		new Thread() {
			public void run() {
				x.g();
			}
		}.start();

	}

}
