package tp.examples.threads.ex7;

public class MemoryModelEx1 {
	// add the keyword 'volatile' to the next like to make it well-synchronized
	//
	volatile static boolean f = false;
	//static boolean f = false;

	public static void main(String[] args) throws InterruptedException {

		new Thread() {
			public void run() {
				System.out.println("Thread is Starting");
				while (!f) {
					// do something
				}
				System.out.println("Thread is Finishing");
			};
		}.start();

		Thread.sleep(2000);
		System.out.println("Setting f to 'true'");
		f = true;
	}
}
