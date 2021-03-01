package tp.examples.threads.worker;

public class Main_2 {
	public static void main(String[] args) {
		Worker_2 w1 = new Worker_2();
		Worker_2 w2 = new Worker_2();
		w1.startWorker();
		w2.startWorker();
		
		w2.addTask( new Runnable() {
			public void run() {
				System.out.println("Task 1");
			}
		});

		w1.addTask( new Runnable() {
			public void run() {
				System.out.println("Task 2");
			}
		});

		w1.addTask( new Runnable() {
			public void run() {
				System.out.println("Task 3");
			}
		});
		
		w2.addTask( new Runnable() {
			public void run() {
				System.out.println("Task 4");
			}
		});
		
		System.out.println("Main is done!");
	}
}
