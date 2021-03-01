package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import simulator.Control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements SimulatorObserver {
	
	private Controller ctrl;
	
	private double time;
	private String gLaw;
	private int nBodies = 0;
	
	JLabel _currTime;
	JLabel _currLaws;
	JLabel _numOfBodies;
	
	StatusBar(Controller ctrl) {
		this.ctrl = ctrl;		
		ctrl.addObserver(this);
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder(1));
		
		
		_currTime = new JLabel("Time: " + time);
		_currLaws = new JLabel("Laws: " + gLaw);
		_numOfBodies = new JLabel("Bodies: " + nBodies);
		
		this.add(_currTime);
		this.add(new JSeparator(SwingConstants.VERTICAL));
		this.add(_numOfBodies);
		this.add(new JSeparator(SwingConstants.VERTICAL));
		this.add(_currLaws);
		

	}
	
	
	// other private/protected methods


	void update(JLabel cTime, JLabel cLaws, JLabel bodies) {
        cTime.setText("Time: " + Double.toString(time));
        cLaws.setText("Law: " + gLaw);
        bodies.setText("Bodies: " + Integer.toString(nBodies));

	}
	
	
	// SimulatorObserver methods
	@Override
	public void onRegister(List<Body> bodies, double t, double dt, String gLawsDesc) {		
		time = t;
		nBodies = bodies.size();
		gLaw = gLawsDesc;		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() { 
					nBodies = bodies.size();
					gLaw = gLawsDesc;	
					update(_currTime, _currLaws, _numOfBodies);
				}
		});
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() { 
					nBodies = bodies.size();
					update(_currTime, _currLaws, _numOfBodies);
			}
		});
	}
	
	@Override
	public void onAdvance(List<Body> bodies, double t) {
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() { 
					time = t;
					nBodies = bodies.size();		
					update(_currTime, _currLaws, _numOfBodies);
				}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() { 
					gLaw = gLawsDesc;
					update(_currTime, _currLaws, _numOfBodies);
				}
		});

	}
	


}