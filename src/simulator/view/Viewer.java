package simulator.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import simulator.Control.Controller;
import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class Viewer extends JComponent implements SimulatorObserver {

	
	private int _centerX;
	private int _centerY;
	
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;
	
	Viewer(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	
	private void initGUI() {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Viewer", TitledBorder.LEFT, TitledBorder.TOP));

			
		_bodies = new ArrayList<>();
		_scale = 1.0;
		_showHelp = true;
		
		addKeyListener(new KeyListener() {
			
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyChar()) {
				case '-':
					_scale = _scale * 1.1;
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
					break;
				case '=':
					autoScale();
					break;
				case 'h':
					_showHelp = !_showHelp;
					break;
				default:
			}
			repaint();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		});
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}	
		});
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		//use ’gr’ to draw not ’g’
		
		//calculate the center
		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;
		
		//Draw a cross at center
		 int lg = 20;

		 //Empieza en el centro menos la mitad de la linea y termina en el centro mas la mitad de la linea
		 gr.setColor(Color.black);
		 for(int i = 0; i < lg; i++) gr.drawLine((int) (_centerX - (lg * 0.5)), (int)(_centerY), (int) (_centerX + (lg * 0.5)), (int)(_centerY));
		 for(int j = 0; j < lg; j++) gr.drawLine((int) (_centerX), (int)(_centerY - (lg * 0.5)), (int) (_centerX), (int)(_centerY + (lg * 0.5)));
		 
		  
		//Draw bodies
		 for(int i = 0; i < _bodies.size(); i++) {
			int posX = (int) (_centerX + _bodies.get(i).getPosition().coordinate(0)/_scale);
			int posY = (int) (_centerY + _bodies.get(i).getPosition().coordinate(1)/_scale);
			
			gr.setColor(Color.blue);
			gr.fillOval(posX, posY, 5, 5);
			 
			gr.setColor(Color.black);
			gr.drawString(_bodies.get(i).getId(), posX, posY - 10);
		 }
		 
		 
		 //Draw help
		 if(_showHelp) {
			 gr.setColor(Color.red);
			 gr.drawString("h: toggle help, +: zoom-in, -: zoom-out, =: fit", 10, 25);
			 gr.drawString("Scaling ratio:  " + _scale, 10, 38);
		 }
	}
	
	
	//other private/protected methods
	
	private void autoScale() {
	
		double max = 1.0;
	
		for (Body b : _bodies) {
				Vector p = b.getPosition();
				for (int i = 0; i < p.dim(); i++) max = Math.max(max, Math.abs(b.getPosition().coordinate(i)));
		}
		
		double size = Math.max(1.0, Math.min((double) getWidth(), (double) getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}


	//SimulatorObserver methods
	
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() {
					_bodies = bodies;
					autoScale();
					repaint();
				}
		});
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() {
					_bodies = bodies;
					autoScale();
					repaint();
				}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() {
					_bodies = bodies;
					autoScale();
					repaint();
				}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() {
					_bodies = bodies;
					repaint();
				}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {}

}