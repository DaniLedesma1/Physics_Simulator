package tp.examples.threads.worker;

public class Main_3 {
	public static void main(String[] args) throws InterruptedException {
		Worker_3 w = new Worker_3();

		w.startWorker();

		System.out.println("Adding 1");
		w.addTask(new Runnable() {
			public void run() {
				System.out.println("Task 1 executing");
			}
		});

		System.out.println("Adding 2");
		w.addTask(new Runnable() {
			public void run() {
				System.out.println("Task 2 executing");
			}
		});

		System.out.println("Adding 3");
		w.addAndWaitTask(new Runnable() {
			public void run() {
				System.out.println("Task 3 executing");
			}
		});

		System.out.println("Adding 4");
		w.addTask(new Runnable() {
			public void run() {
				System.out.println("Task 4 executing");
			}
		});

		System.out.println("Adding 5");
		w.addTask(new Runnable() {
			public void run() {
				System.out.println("Task 5 executing");
			}
		});

		System.out.println("Main is done!");
	}
}
