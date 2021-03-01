package tp.examples.threads.worker;

import java.util.concurrent.Executor;

public class JavaExecutor_2 implements Executor {

	@Override
	public void execute(Runnable r) {
		new Thread(r).start();
	}

}
