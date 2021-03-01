package tp.examples.threads.worker;

import java.util.concurrent.Executor;

public class JavaExecutor_3 implements Executor {

	private static BlockingQueue<Runnable> tasks;
	private volatile boolean stopped;
	private volatile boolean started;

	public JavaExecutor_3() {
		tasks = new BlockingQueue<Runnable>();
		stopped = started = false;
		startWorker();
	}

	public void execute(Runnable t) {
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
