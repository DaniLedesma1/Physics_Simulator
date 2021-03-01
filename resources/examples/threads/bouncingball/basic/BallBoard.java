package tp.examples.threads.bouncingball.basic;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class BallBoard extends JComponent {
	private ArrayList<Ball> balls;
	
	public BallBoard() {
		balls = new ArrayList<Ball>();		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(Ball b : balls ) {
			g.setColor(b.getColor());
			g.fillOval(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		}
	}
	
	public void addBall(Ball b) {
		balls.add(b);
		repaint();		
	}

}
