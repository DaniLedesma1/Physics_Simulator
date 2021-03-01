package tp.examples.threads.bouncingball.basic;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
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
	private JButton moveBalls;

	public BouncingBall() {
		super("[=] Bouncing Ball [=]");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel( new BorderLayout() );
		
		board = new BallBoard();
		mainPanel.add( board, BorderLayout.CENTER);
		
		JPanel statusBar = new JPanel();
		mainPanel.add(statusBar, BorderLayout.PAGE_END);

		numBalls = 0;
		numBallsLabel = new JLabel("Balls: "+numBalls);
		statusBar.add( numBallsLabel );

		JPanel buttonsPanel = new JPanel();
		mainPanel.add( buttonsPanel, BorderLayout.PAGE_START );

		addBall = new JButton("Add Ball");
		buttonsPanel.add(addBall);
		
		moveBalls = new JButton("Start");
		buttonsPanel.add(moveBalls);
		
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater( new Runnable() {
			
			@Override
			public void run() {
				new BouncingBall();
			}
		});
	}

}
