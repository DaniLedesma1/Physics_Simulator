package tp.examples.threads.worker;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue<T> {

	private List<T> queue = new LinkedList<T>();

	public BlockingQueue() {
	}

	public synchronized void add(T item) {
		if (this.queue.size() == 0) {
			notifyAll();
		}
		this.queue.add(item);
	}

	public synchronized T poll() {
		while (this.queue.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		return this.queue.remove(0);
	}

	public synchronized boolean isEmpty() {
		return this.queue.isEmpty();
	}
}
