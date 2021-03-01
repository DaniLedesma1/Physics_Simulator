package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import simulator.Control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial") 
public class ControlPanel extends JPanel implements SimulatorObserver {
	
	private Controller _ctlr;
	
	//BarraSuperior
	volatile Thread _thread;
	private JSpinner steps;
	private JSpinner _delay;
	private JButton stop;
	private JButton run;
	private JButton pause;
	private JButton gLaws;
	private JButton fileChoose;
	private JSONObject selectedLaw;
	private JTextField deltaTime;
	private int nBodies; //Para saber si poder inicial el simulador (obviamente no se puede sin cuerpos)
	
	ControlPanel(Controller ctlr) {
		_ctlr = ctlr;
		steps = createSpinner();
		_delay = createSpinnerDelay();
		
		initGUI();
		_ctlr.addObserver(this);
	}
	
	private void initGUI() {
						
		BorderLayout ly =  new BorderLayout();
			
		this.setLayout(ly);
		this.add(left(), BorderLayout.WEST);
		this.add(right(), BorderLayout.EAST);
		this.setBorder( BorderFactory.createBevelBorder(0));
		
	}

	private void run_sim(int n, long delay) {
		
		while (n > 0 && !Thread.interrupted()) {
			
			
			try {
				_ctlr.run(1);
			}
			catch(Exception e) {
				JOptionPane.showMessageDialog(new JFrame(), e);

				//Enable all buttons
				gLaws.setEnabled(true);
				fileChoose.setEnabled(true);
				stop.setEnabled(true);
				run.setEnabled(true);
				pause.setEnabled(false);
				steps.setEnabled(true);
				_delay.setEnabled(true);
				deltaTime.setEditable(true);
			}
			
			try { 
				Thread.sleep(10);
			 } catch (InterruptedException e) {
				 System.out.println("Int while sleeping"); 
				 Thread.currentThread().interrupt(); 
			 }
			
			n--;
		}
	
	}

	
	//Other private methods
	private JPanel left() {
		JPanel left = new JPanel();
		
 		left.add(fileChooseBt());
		left.add(gravityLawsBt());
		left.add(playBt());
		left.add(pauseBt());
		left.add(new JLabel("Delay: "));
		left.add(_delay);
		left.add(new JLabel("Steps: "));
		left.add(steps);
		left.add(new JLabel("Delta-time: "));
		left.add(deltaTime());
		return left;
	}
	
	
	private JPanel right() {
		JPanel right = new JPanel();
		right.add(stopBt());
		return right;
	}
	
	
	//----------------------------------------DeltaTime----------------------------------------
	private JTextField deltaTime() {
		
		String def = ""; 
		def = String.valueOf(_ctlr.getDelta());
		deltaTime = new JTextField(def);
		deltaTime.addKeyListener(new KeyAdapter() {
			
			//Comprueba que lo introducido SOLO sean numeros y si es valido lo mete al controlador
			@Override	
			public void keyReleased(KeyEvent e) {
				try {
					double dt = Double.parseDouble(deltaTime.getText());
					_ctlr.setDeltaTime(dt);
				} catch (NumberFormatException nfe){
					JOptionPane.showMessageDialog(new JFrame(), "Delta-time is not a valid value");
					
				}
			}
			
		});
		
		
		return deltaTime;
	}
	
	//----------------------------------------Steps----------------------------------------

	private JSpinner createSpinner() {
		
		JSpinner spinner;
	    SpinnerNumberModel numbers;
	 
	    //if(_ctlr.getSteps() > 0) current = _ctlr.getSteps();
	    Integer current = 10000; 	
	    Integer min = 0;
	    Integer max = 200000;
	    Integer step = 50;
	    
	    numbers = new SpinnerNumberModel(current, min, max, step);
	    spinner = new JSpinner(numbers);
	    
	    return spinner;
	}
	
	
	//Delay
	private JSpinner createSpinnerDelay() {
		
		JSpinner spinner;
	    SpinnerNumberModel numbers;
	 
	    Long current = (long) 10; 	
	    Long min = (long) 0;
	    Long max = (long) 10000;
	    Long step = (long) 1;
	    
	    numbers = new SpinnerNumberModel(current, min, max, step);
	    spinner = new JSpinner(numbers);
	    
	    return spinner;
	}
	
	
	//----------------------------------------LawSelector----------------------------------------
	private JPanel lawSelector(JFrame father) {
				
		JPanel frame = new JPanel();
		List<JSONObject> laws = _ctlr.getGravityLawsFactory().getInfo();
		JComboBox<String> list = chooserPanel(laws);
		

		frame.setLayout(new BorderLayout(5,8));
		frame.add(new JLabel("Select gravity laws to be used."), BorderLayout.NORTH);
		frame.add(chooserPanel(laws), BorderLayout.CENTER);
		frame.add(confirmationPanel(father), BorderLayout.SOUTH);
		frame.setVisible(true);
	
		return frame;	
	}
	
	private JComboBox chooserPanel(List<JSONObject> laws) {
		
		String[] names = new String[100];
		
		for (int i = 0; i < laws.size(); i++) {names[i] = (laws.get(i).getString("desc"));}	
		
		JComboBox<String> list = new JComboBox<String>(names);
		list.setSize(30, 10);
		
		list.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < laws.size(); i++) {
					if(laws.get(i).get("desc") == list.getSelectedItem()) {
						selectedLaw = laws.get(i); 
					}
				}
		    }
		});
		
		return list;
	}

	
	//----------------------------------------External Panels----------------------------------------
	private JPanel confirmationPanel(JFrame father) {		
		 JPanel conf = new JPanel(new FlowLayout());
		 conf.add(confirmationBt(father, false));
		 conf.add(cancelBt(father));
		 
		 
		 return conf;
	}
	
	
	private JPanel exitConfirmation(JFrame father) {
	
		JPanel conf = new JPanel(new BorderLayout());
		conf.add(new JLabel("Are you sure you want to exit?"), BorderLayout.NORTH);
		
		JPanel bt = new JPanel(new FlowLayout());
		bt.add(confirmationBt(father, true));
		bt.add(cancelBt(father));
		
		conf.add(bt);
		return conf;
	}

	
	//----------------------------------------Buttons----------------------------------------
	
	private JButton stopBt() {
		stop = new JButton();
		stop.setIcon(new ImageIcon("resources/icons/exit.png"));	
		stop.setSize(32, 32);
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFrame exit = new JFrame("Exit confirmation");
				exit.setBounds(500, 400, 300, 200);
				exit.add(exitConfirmation(exit));
				exit.setVisible(true);
				exit.pack();

			}
		});
		return stop;
	}
	
	private JButton pauseBt() {
		pause = new JButton();
		pause.setIcon(new ImageIcon("resources/icons/stop.png"));	
		pause.setSize(32, 32);
		pause.setEnabled(false);
		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if(_thread != null) { _thread.interrupt(); }
				
//				gLaws.setEnabled(false);
//				fileChoose.setEnabled(false);
//				stop.setEnabled(false);
//				run.setEnabled(false);
//				pause.setEnabled(true);
//				steps.setEnabled(false);
//				_delay.setEnabled(false);
//				deltaTime.setEditable(false);
			}
			
		});
		return pause;
	}

	private JButton playBt() {
		run = new JButton();
		run.setIcon(new ImageIcon("resources/icons/run.png"));	
		run.setSize(32, 32);		
		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {		
				if(nBodies > 0) {
					
					gLaws.setEnabled(false);	
					fileChoose.setEnabled(false);
					stop.setEnabled(false);
					run.setEnabled(false);
					pause.setEnabled(true);
					steps.setEnabled(false);
					_delay.setEnabled(false);
					deltaTime.setEditable(false);
						
					
					_thread = new Thread() {
						public void run() {
							Integer stp = (Integer)steps.getValue();
							long del = (Long)_delay.getValue();
							
							run_sim(stp, del);
							
							gLaws.setEnabled(true);
							fileChoose.setEnabled(true);
							stop.setEnabled(true);
							run.setEnabled(true);
							pause.setEnabled(false);
							steps.setEnabled(true);
							_delay.setEnabled(true);
							deltaTime.setEditable(true);
						}
					};
					_thread.start();
				}
				
				else JOptionPane.showMessageDialog(new JFrame(), "You need bodies to start simulating");
			}	
		});
		return run;
	}

	private JButton gravityLawsBt() {
		gLaws = new JButton();
		gLaws.setIcon(new ImageIcon("resources/icons/physics.png"));	
		gLaws.setSize(32, 32);
		gLaws.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFrame selector = null;

				selector = new JFrame("Gravity Laws Selector");
				
				selector.add(lawSelector(selector));
				selector.setBounds(500, 400, 300, 200);
				selector.setVisible(true);	
				selector.pack();
			}
			
		});
		return gLaws;
	}

	
	private JButton fileChooseBt() {
		fileChoose = new JButton();
		fileChoose.setIcon(new ImageIcon("resources/icons/open.png"));	
		fileChoose.setSize(32, 32);
		
		fileChoose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int ret = chooser.showOpenDialog(null);
		
				if (ret == JFileChooser.APPROVE_OPTION) {
				
					try {
						_ctlr.reset();
						_ctlr.loadBodies(new FileInputStream(chooser.getSelectedFile()));
					} catch (FileNotFoundException ex) {
						System.err.println("Something went wrong ...");
						System.err.println();
						ex.printStackTrace();
					}
					
				}
				
				else { System.out.println("Load cancelled by user."); }
				
			}
			
		});
		return fileChoose;
	}
	

	//Opciones de ventanas
	
	private JButton confirmationBt(JFrame father, boolean exit) {
		 JButton ok = new JButton("Confirm");

		 ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!exit) {
						_ctlr.setGravityLaws(selectedLaw);
						father.dispose();	
					}
					else System.exit(0);
				}
			});
			 
		return ok;
	}
	
	
	private JButton cancelBt(JFrame father) {
		 
		 JButton cancel = new JButton("Cancel");
		 cancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					father.dispose();
				}
			});
		return cancel;
	}
	
	
	//----------------------------------------SimulatorObserver----------------------------------------
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() { 
					nBodies = bodies.size();	
				}
		});
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() { 
					nBodies = bodies.size();	
				}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() { 
					nBodies = bodies.size();	
				}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onGravityLawChanged(String gLawsDesc) {}
}

	