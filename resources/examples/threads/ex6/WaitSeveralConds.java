package tp.examples.threads.ex6;

public class WaitSeveralConds {
	boolean cond1 = false;
	boolean cond2 = false;

	public synchronized void f() {
		System.out.println("f: started");
		while (!cond1) {
			try {
				System.out.println("f: cond1 is false, going to sleep a bit");				
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("f: cond1 = "+cond1);
	}

	public synchronized void h() {
		System.out.println("f: started");
		while (!cond2) {
			try {
				System.out.println("f: cond2 is false, going to sleep a bit");				
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("f: cond2 = "+cond2);
	}

	public synchronized void g() {
		System.out.println("g: started");
		sleepabit(5000);
		cond1 = true;		
		System.out.println("g: setting cond to "+cond1);
		this.notify();
	}

	public synchronized void m() {
		System.out.println("m: started");
		sleepabit(5000);
		cond2 = true;		
		System.out.println("m: setting cond to "+cond2);
		this.notify();
	}

	private void sleepabit(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
		}
	}
	
	static public void  main(String[] args) {
		final WaitSeveralConds x = new WaitSeveralConds();
		
		new Thread() {
			public void run() { x.f(); }
		}.start();

		new Thread() {
			public void run() { x.h(); }
		}.start();

		new Thread() {
			public void run() { x.g(); }
		}.start();

		new Thread() {
			public void run() { x.m(); }
		}.start();

	}

}
