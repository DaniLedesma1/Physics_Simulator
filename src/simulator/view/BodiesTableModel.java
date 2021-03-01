package simulator.view;


import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import simulator.Control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

	private List<Body> _bodies;
	public static final String[] columnNames = new String[]{"Id", "Mass", "Position", "Velocity", "Aceleration"};
	
	
	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	
	@Override
	public int getRowCount() {
		return _bodies.size();
	}
	
	@Override
	public int getColumnCount() { //Id, vel, acc, pos, mass
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	
		if(rowIndex < 0 || rowIndex >= _bodies.size()) return null;
		else{
			Body b = _bodies.get(rowIndex);
	        switch(columnIndex) {
	            case 0: return b.getId();   
	            case 1: return b.getMass(); 
	            case 2: return b.getPosition();
	            case 3: return b.getVelocity();
	            case 4: return b.getAcceleration();
	
	            default: return null;
	        }
		}
	
	}
	
	
	
	//SimulationObserver
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String gLawsDesc) {
		
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() { 
					_bodies = bodies;
					fireTableStructureChanged();
				}
		});

	}
	
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String gLawsDesc) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() { 
					_bodies = bodies;
					fireTableStructureChanged();
				}
		});

	}
	
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() { 
					_bodies = bodies;
					fireTableStructureChanged();
				}
		});
	
	}
	
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater( new Runnable() {
			@Override
			    public void run() { 
					fireTableStructureChanged();
				}
		});

	}
	
	@Override
	public void onDeltaTimeChanged(double dt) {}
	
	@Override
	public void onGravityLawChanged(String gLawsDesc) {}
		
}