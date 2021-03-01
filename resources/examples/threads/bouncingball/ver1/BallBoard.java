package tp.examples.threads.bouncingball.ver1;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class BallBoard extends JComponent {
	private ArrayList<Ball> balls;
	
	public BallBoard() {
		balls = new ArrayList<Ball>();
		this.addMouseListener( new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				toggleBall(e.getX(), e.getY());
			}
		});
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

	public void moveballs() {
		for(Ball b : balls ) {
			b.move(this.getWidth(), this.getHeight());
		}
		repaint();
	}

	protected void toggleBall(int x, int y) {
		for(Ball b : balls ) {
			if ( b.isInside(x, y) )
				b.toggle();
		}
		
	}

}
