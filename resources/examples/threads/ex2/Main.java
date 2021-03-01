package tp.examples.threads.ex2;

public class Main {
	
	public static void main(String[] args) {
		Thread t1 = new Thread( new TEx1("A"));
		Thread t2 = new Thread( new TEx2("B"));
		t1.start();
		t2.start();
		System.out.println("Main is done!");
	}
	
}
