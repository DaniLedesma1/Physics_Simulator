package simulator.Control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.GravityLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	
	private PhysicsSimulator _sim;
	private Factory<Body> _bodiesFactory;
	private Factory<GravityLaws> _gl;
	private int _steps;
	
	public Controller(PhysicsSimulator sim, Factory<Body> _bodyFactory, Factory<GravityLaws> gl) {
		this._sim = sim;
		this._bodiesFactory = _bodyFactory;
		this._gl = gl;
	}

	public void loadBodies(InputStream in) {
		
		JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
		JSONArray bodies = jsonInupt.getJSONArray("bodies");
		
		for (int i = 0; i < bodies.length(); i++) {
			_sim.addBody(_bodiesFactory.createInstance(bodies.getJSONObject(i)));
		}
	}

	public void run(int steps, OutputStream out) {
		PrintStream p;
		_steps = steps;
		if(out == null) p = System.out;
		else p = new PrintStream(out);
		
		p.println("{\n\"states\": [\n" + _sim.toString() + ","); //Estado inicial
		
		
		for(int i = 0; i < _steps - 1; i++) {
			_sim.advance();
			if(i < _steps - 1) {
				p.println(_sim.toString() + ",");
			}
		}
		p.println(_sim.toString());
		p.println("]\n}");
		
	}
	
	public void reset() {
		this._sim.reset();
	}
	
	public void setDeltaTime(double dt) {
		this._sim.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		this._sim.addObserver(o);
	}
	
	public void run(int n) {
		
		for(int i = 0; i < n; i++) {
			_sim.advance();
		}
	}
	
	public Factory<GravityLaws> getGravityLawsFactory() {
		return _gl;
	}

	
	public void setGravityLaws(JSONObject info) {
		_sim.setGravityLaws(_gl.createInstance(info));
	}

	public double getDelta() {
		return _sim.getDeltaTime();
	}
	
	public int getSteps() {
		return _steps;
	}
}
