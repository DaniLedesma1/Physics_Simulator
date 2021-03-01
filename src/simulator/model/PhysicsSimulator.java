package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class PhysicsSimulator {
	
	private GravityLaws _gravityLaws;
	private List<Body> _bodies;
	private double _dt;
	private double _time = 0.0;
	private List<SimulatorObserver> _so;
	
	public PhysicsSimulator(GravityLaws gravityLaws, Double _dtime) {
		if(gravityLaws == null) throw new IllegalArgumentException("You need a gravity law to apply");
		if(_dtime < 0) throw new IllegalArgumentException("Time must be positive");
		
		this._gravityLaws = gravityLaws;
		this._dt = _dtime;
		this._bodies = new ArrayList<Body>();
		this._so = new ArrayList<SimulatorObserver>();
	}

	

	public void advance() {
		_gravityLaws.apply(_bodies);
		for(int i = 0; i < _bodies.size(); i++) {
			_bodies.get(i).move(_dt);
		}
		_time += _dt;
		
		for(SimulatorObserver so: _so) {
			so.onAdvance(_bodies, _time);
		}
	}

	public void addBody(Body b) throws IllegalArgumentException {
		
		for (Body i : _bodies) {
			if(i.getId().equals(b.getId())) {
				throw new IllegalArgumentException("Another body with same ID already exits");
			}
		}
			_bodies.add(b);
			
		for(SimulatorObserver so: _so) {
			so.onBodyAdded(_bodies, b);
		}
	}
	
	public String toString() {
		String info = "{ \"time\": " + _time + ", \"bodies\": [";
		int i;
		
		//Lo hacemos de esta manera para tener en cuenta que el ultimo caso no lleva coma, hay que distinguirlo
		//USAMOS LA VARIABLE LOCAL I FUERA DEL BUCLE Y NO RECORREMOS TODOS LOS ELEMENTOS
		for(i = 0; i < _bodies.size() - 1; i++) {	
			info += _bodies.get(i).toString() + ", ";
		}
		info += _bodies.get(i).toString();
		info += "] }";
		return info;
		
	}
	
	
	public void reset(){
		
		this._bodies = new ArrayList<Body>();
		this._time = 0.0;
		for(SimulatorObserver so: _so) {
			so.onReset(_bodies, _time, _dt, _gravityLaws.toString());
		}
		
	}
	
	
	public void setDeltaTime(double dt){
		
		if(dt < 0) throw new IllegalArgumentException("Time must be positive");
		else {
			this._dt = dt;
		}
		
		for(SimulatorObserver so: _so) {
			so.onDeltaTimeChanged(dt);
		}
		
	}
	
	public void setGravityLaws(GravityLaws gravityLaws){
		
		if(gravityLaws == null) throw new IllegalArgumentException("You need a gravity law to apply");
		else {
			this._gravityLaws = gravityLaws;
		}
		
		for(SimulatorObserver so: _so) {
			so.onGravityLawChanged(_gravityLaws.toString());
		}
	}
	
	
	public void addObserver(SimulatorObserver o) {
		
		if(!_so.contains(o)) {
			_so.add(o);
			o.onRegister(_bodies, _time, _dt, _gravityLaws.toString());
		}
		
	}
	
	
	public double getDeltaTime(){
		return _dt;
	}
	
}
