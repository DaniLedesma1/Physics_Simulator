package tp.examples.threads.ex3;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduce an integer: ");
		long n = 0; //sc.nextLong();
		
		// uncomment the next line to execute with the same n for different
		// configurations.
		//
		n=1000000000;

		long startTime = System.currentTimeMillis();

		Function t = new F(n);
		t.start();
		t.join();

		long taskTimeMs = System.currentTimeMillis() - startTime;

		System.out.println("Res: " + t.getRes());
		System.out.println("Time: " + taskTimeMs + "ms");
	}
}
