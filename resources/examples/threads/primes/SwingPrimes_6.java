package tp.examples.threads.primes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

@SuppressWarnings("serial")
public class SwingPrimes_6 extends JFrame {
	private JTextArea primes;
	private volatile BigInteger n = new BigInteger("1");
	private JButton clearButton;
	private JButton startButton;
	private JButton stopButton;
	private SwingWorker<Void, BigInteger> worker;
	
	public SwingPrimes_6() {
		super("[=] Primes Generator 6 [=]");
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
				worker = new SwingWorker<Void, BigInteger>() {

					@Override
					protected Void doInBackground() throws Exception {
						while ( !isCancelled() ) {
							n = Primes.nextPrime(n);
							publish(n);
							sleepabit();
						}

						return null;
					}
					
					@Override
					protected void process(List<BigInteger> chunks) {
						for( BigInteger x : chunks ) {
							primes.append(x+"\n");
						}
					}
					
					@Override
					protected void done() {
						startButton.setEnabled(true);
						clearButton.setEnabled(true);
					}
					
				};
				worker.execute();
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
				if ( worker != null ) {
					worker.cancel(true);
					worker = null;
				}
			}
		});

		primes = new JTextArea();
		mainPanel.add(new JScrollPane(primes), BorderLayout.CENTER);

		setSize(400, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private void sleepabit() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingPrimes_6();
			}
		});
	}
}
