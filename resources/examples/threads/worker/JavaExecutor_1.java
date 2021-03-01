package tp.examples.threads.worker;

import java.util.concurrent.Executor;

public class JavaExecutor_1 implements Executor {

	@Override
	public void execute(Runnable r) {
		r.run();
	}

}
