package tp.examples.threads.ex3;

import java.math.BigInteger;


public abstract class Function extends Thread {

	protected long result;

	protected abstract void apply() throws InterruptedException;

	public long getRes() {
		return result;
	}

	public void run() {
		try {
			apply();
		} catch (InterruptedException e) {
			System.err.println("Something went wrong");
		}
	}

}
