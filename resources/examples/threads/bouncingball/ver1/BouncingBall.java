package tp.examples.threads.bouncingball.ver1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class BouncingBall extends JFrame {

	private BallBoard board;
	private JLabel numBallsLabel;
	private int numBalls;
	private JButton addBall;
	private JButton pauseBalls;
	private volatile boolean stopped;
	private JComboBox<BallSpeed> speedCB;
	private volatile int delay;
	private volatile Thread t;

	private enum BallSpeed {
		SLOW("Slow", 100), MEDIUM("Medium", 50), FAST("Fast", 20), SUPER("Very Fast", 10);

		private String name;
		private int delay;

		BallSpeed(String name, int speed) {
			this.name = name;
			this.delay = speed;
		}

		public String getName() {
			return name;
		}

		public int getDelay() {
			return delay;
		}

		@Override
		public String toString() {
			return getName();
		}
	}

	public BouncingBall() {
		super("[=] Bouncing Ball [=]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());

		board = new BallBoard();
		mainPanel.add(board, BorderLayout.CENTER);

		JPanel statusBar = new JPanel();
		mainPanel.add(statusBar, BorderLayout.PAGE_END);

		numBalls = 0;
		numBallsLabel = new JLabel("Balls: " + numBalls);
		statusBar.add(numBallsLabel);

		JPanel buttonsPanel = new JPanel();
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_START);

		addBall = new JButton("Add Ball");
		buttonsPanel.add(addBall);
		addBall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final Ball b = new Ball(board.getWidth(), board.getHeight());
				board.addBall(b);
				numBalls++;
				numBallsLabel.setText("Balls: " + numBalls);
			}
		});

		pauseBalls = new JButton("Pause");
		buttonsPanel.add(pauseBalls);
		pauseBalls.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (stopped) {
					pauseBalls.setText("Pause");
					synchronized (t) {
						stopped = false;
						t.notify();
					}
				} else {
					pauseBalls.setText("Resume");
					stopped = true;
				}
			}
		});

		speedCB = new JComboBox<BallSpeed>();
		for (BallSpeed s : BallSpeed.values()) {
			speedCB.addItem(s);
		}
		delay = BallSpeed.SLOW.getDelay();
		speedCB.setSelectedItem(BallSpeed.SLOW);
		speedCB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				delay = ((BallSpeed) speedCB.getSelectedItem()).getDelay();
			}
		});
		buttonsPanel.add(speedCB);

		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

		t = new Thread() {
			public void run() {
				moveBalls();
			}

		};
		t.start();

	}

	private void moveBalls() {

		while (true) {
			while (stopped) {
				//....
				synchronized (t) {
						try {
							if (stopped) t.wait();
						} catch (InterruptedException e) {
						}
				}
			}
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					board.moveballs();
				}
			});
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new BouncingBall();
			}
		});
	}

}
