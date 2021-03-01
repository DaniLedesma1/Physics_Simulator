package tp.examples.threads.ex6;

public class SynchedCounter {
	private int c = 0;

	public void increment() {
		synchronized (this) {
			c++;
		}
	}

	public void decrement() {
		synchronized (this) {
			c--;
		}
	}

	public int value() {
		return c;
	}

	public static void main(String[] args) throws InterruptedException {
		final SynchedCounter c = new SynchedCounter();

		Thread t1 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 100000; i++)
					c.increment();
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 100000; i++)
					c.decrement();
			}
		});

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println("Counter = " + c.value());
	}
}