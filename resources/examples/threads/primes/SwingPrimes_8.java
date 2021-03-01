package tp.examples.threads.primes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SwingPrimes_8 extends JFrame {
	JTextArea primes;
	private volatile BigInteger n = new BigInteger("1");
	private JButton clearButton;
	private JButton startButton;
	private JButton stopButton;
	private volatile Thread t;
    private volatile boolean stopped = true;

	public SwingPrimes_8() {
		super("[=] Primes Generator 8 [=]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		JPanel buttonsBars = new JPanel();
		mainPanel.add(buttonsBars, BorderLayout.PAGE_START);

		startButton = new JButton("Start");
		buttonsBars.add(startButton);
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				clearButton.setEnabled(false);
				synchronized ( t ) {
					stopped = false;
					t.notifyAll();
				}
			}
		});

		clearButton = new JButton("Clear");
		buttonsBars.add(clearButton);
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				primes.setText("");
				n = new BigInteger("1");
			}
		});

		stopButton = new JButton("Stop");
		buttonsBars.add(stopButton);
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stopped = true;
				startButton.setEnabled(true);
				clearButton.setEnabled(true);
			}
		});

		primes = new JTextArea();
		mainPanel.add(new JScrollPane(primes), BorderLayout.CENTER);

		t = new Thread() {
			public void run() {
				showNumbers();
			}
		};
		t.start();

		setSize(400, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private void showNumbers() {
		while ( true ) {
			while ( stopped ) { 
				synchronized (t) { 
					try {
						t.wait();
					} catch (InterruptedException e) {
					}
				}
			}

			n = Primes.nextPrime(n);
			final BigInteger x = n;
			SwingUtilities.invokeLater( new Runnable() {
				
				@Override
				public void run() {
					primes.append(x + "\n");					
				}
			});
			sleepabit();
		}
			
	}

	private void sleepabit() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingPrimes_8();
			}
		});
	}
}
