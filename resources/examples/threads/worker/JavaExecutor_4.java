package tp.examples.threads.worker;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JavaExecutor_4 {
	// private static final ExecutorService exec = Executors.newFixedThreadPool(10);
	// private static final ExecutorService exec = Executors.newCachedThreadPool();
	private static final ExecutorService exec = Executors.newSingleThreadExecutor();

	public static void test1() {
		Future<?> x = exec.submit(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
			}
		});
		try {
			System.out.println(x.get(1,TimeUnit.SECONDS));
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
		}
	}

	public static void test2() {
		Future<Integer> x = exec.submit(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
				}
			}
		}, 5);
		try {
			System.out.println(x.get(1,TimeUnit.SECONDS));
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
		}
	}

	public static void test3() {
		Future<Integer> x = exec.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				if (Math.random() > 0.5 ) {
					return 5;
				} else {
					throw new RuntimeException("Doh!");
				}
			}
		});

		try {
			System.out.println(x.get());
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
			System.out.println(e.getCause());
		}

	}

	public static void main(String[] args) {
		test3();
		exec.shutdown();
	}
}
