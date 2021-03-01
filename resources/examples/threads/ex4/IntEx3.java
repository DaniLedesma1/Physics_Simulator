package tp.examples.threads.ex4;

public class IntEx3 implements Runnable {

	public void run() {
		Thread t2 = new Thread() {
			public void run() {
				try {
					sleep(8000);
				} catch (InterruptedException e) {
				}
				System.out.println("T2 is done!");
			}
		};
		t2.start();
		try {
			t2.join();
		} catch (InterruptedException e) {
			System.out.println("T1 was interrupted!");
		}
		System.out.println("T1 is done!");		
	}

	public static void main(String args[]) throws InterruptedException {
		System.out.println("Starting!");
		Thread t1 = new Thread(new IntEx3());
		t1.start();
		Thread.sleep(3000);
		//t1.interrupt();
		System.out.println("Main is done!");
	}
}
