package tp.examples.threads.worker;

public class Main_1 {
	public static void main(String[] args) {
		Worker_1 w1 = new Worker_1();
		Worker_1 w2 = new Worker_1();
		w1.startWorker();
		w2.startWorker();
		
		w2.addTask( new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+"Task 1");
			}
		});

		w1.addTask( new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+"Task 2");
			}
		});

		w1.addTask( new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+"Task 3");
			}
		});
		
		w2.addTask( new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+"Task 4");
			}
		});
		
		System.out.println(Thread.currentThread().getName()+": "+"Main is done!");
		w1.stopWorker();
		w2.stopWorker();
	}
}
