package tp.examples.threads.worker;

public class Worker_3 {

	private BlockingQueue<Runnable> tasks = new BlockingQueue<Runnable>();
	private volatile boolean stopped;
	private volatile boolean started;

	public Worker_3() {
		tasks = new BlockingQueue<Runnable>();
		stopped = started = false;
	}

	public void addTask(Runnable t) {
		if (stopped || !started) {
			return;
		}
		tasks.add(t);
	}

	public void addAndWaitTask(final Runnable t) {
		if (stopped || !started) {
			return;
		}

		Runnable t_aux = new Runnable() {
			public void run() {
				synchronized (this) {
					t.run();
					this.notify();
				}
			}
		};

		synchronized (t_aux) {
			tasks.add(t_aux);
			try {
				t_aux.wait();
			} catch (InterruptedException e) {
			}
		}
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
