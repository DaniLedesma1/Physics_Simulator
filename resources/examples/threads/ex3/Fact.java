package tp.examples.threads.ex3;

public class Fact extends Function {

	private long n;

	Fact(long n) {
		this.n = n;
	}

	protected void apply() {

		long r = 1;

		for (long i = 1; i <= n; i++)
			r *= i;

		this.result = r;
	}

}
