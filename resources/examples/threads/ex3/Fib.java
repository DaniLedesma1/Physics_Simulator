package tp.examples.threads.ex3;

public class Fib extends Function {

	private long n;

	Fib(long n) {
		this.n = n;
	}

	protected void apply() { // factorial
		long a = 0, b = 1, i = 0, c;

		while (i < n) {
			c = a + b;
			a = b;
			b = c;
			i++;
		}

		this.result = a;
	}

}
