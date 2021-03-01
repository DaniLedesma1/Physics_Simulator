package tp.examples.threads.worker;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker_1 {

	private Queue<Runnable> tasks;
	private volatile boolean stopped;
	private volatile boolean started;

	public Worker_1() {
		tasks = new ConcurrentLinkedQueue<Runnable>();
		stopped = started = false;
	}

	public void addTask(Runnable t) {
		if (stopped || !started) {
			return;
		}
		tasks.add(t);
	}

	public void startWorker() {
		if (started) {
			return;
		}

		started = true;

		new Thread() {
			@Override
			public void run() {
				processTasks();
			}
		}.start();
	}

	public void stopWorker() {
		stopped = true;
	}

	private void processTasks() {
		while (!stopped || !tasks.isEmpty()) {
			Runnable task = tasks.poll();
			if (task != null)
				task.run();
			else
				sleepabit();
		}
	}

	private void sleepabit() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
