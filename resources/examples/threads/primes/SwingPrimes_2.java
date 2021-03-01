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
public class SwingPrimes_2 extends JFrame {

	private JTextArea primes;
	private JButton clearButton;
	private JButton startButton;

	private volatile BigInteger n = new BigInteger("1");
	private volatile Thread t;

	public SwingPrimes_2() {
		super("[=] Primes Generator 2 [=]");
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
				if (t == null) {
					t = new Thread() {
						public void run() {
							showNumbers();
							t = null;
						}
					};
					t.start();
				}
				System.out.println("Exit!");
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

		primes = new JTextArea();
		mainPanel.add(new JScrollPane(primes), BorderLayout.CENTER);

		setSize(400, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private void showNumbers() {
		int i = 10;
		while (i > 0) {
			n = Primes.nextPrime(n);
			SwingUtilities.invokeLater(new Runnable() {

			@Override
				public void run() {
					primes.append(n + "\n");
				}
			});
			System.out.println(n);
			sleepabit();
			i--;
		}
	}

	private void sleepabit() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingPrimes_2();
			}
		});
	}
}
