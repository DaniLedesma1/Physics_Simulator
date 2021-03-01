package tp.examples.threads.worker;

public class Worker_2 {

	private BlockingQueue<Runnable> tasks;
	private volatile boolean stopped;
	private volatile boolean started;

	public Worker_2() {
		tasks = new BlockingQueue<Runnable>();
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
		tasks.add(new Runnable() {
			@Override
			public void run() {
				stopped = true;
			}
		});
	}

	private void processTasks() {
		while (!stopped || !tasks.isEmpty()) {
			Runnable task = tasks.poll();
			task.run();
		}
	}

}
