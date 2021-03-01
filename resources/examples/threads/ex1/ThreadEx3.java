package tp.examples.threads.ex1;

public class ThreadEx3 {

	public static void main(String args[]) {

		new Thread() {
			public void run() {
				int i = 0;
				while (i < 100) {
					System.out.println("T1: " + i);
					i++;
				}
			}
		}.start();

		new Thread(new Runnable() {
			public void run() {
				int i = 0;
				while (true) {
					System.out.println("T2: " + i);
					i++;
				}
			}
		}).start();

		System.out.println("Main is done!");
	}
}