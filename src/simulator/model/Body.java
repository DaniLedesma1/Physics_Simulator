package simulator.model;

import simulator.misc.Vector;

public class Body {
	private String _id;
	protected Vector _v;
	protected Vector _a;
	protected Vector _p;
	protected double _m;
	
	
	//CONSTRUCTOR 1
	public Body(String id, Vector v, Vector a, Vector p, double m) {
		this._id = id;
		this._v = v;
		this._a = a;
		this._p = p;
		this._m = m;
	}
	

	//CONSTRUCTOR 2
	public Body() {}
	
	//FUNCIONES DE LA CLASE
	public String getId() {
		return this._id;
	}
	
	public void setId(String id) {
		this._id = id;
	}
	
	public Vector getVelocity() {
		return new Vector(_v);
	}
	
	public Vector getPosition() {
		return new Vector(_p);
	}
	
	public Vector getAcceleration() {
		return new Vector(_a);
	}
	
	public double getMass() {
		return this._m;
	}
	
	void setVelocity(Vector v) {
		 _v = new Vector(v);
	}
	
	void setAceleration(Vector a) {
		 _a = new Vector(a);
	}
	
	void setPosition(Vector p) {
		 _p = new Vector(p);
	}
	
	void move(double t) {
		double aux = t*t;
		
		//p += vt + 0.5at^2
		//No necesita setters ya que trabaja sobre la propia clase
		_p = _p.plus(_v.scale(t).plus(_a.scale(0.5*aux)));
 
		//v += at
		_v = _v.plus(_a.scale(t));
	}
	
	public String toString() {
		return  "{ " + " \"id\": \"" + this._id + "\", \"mass\": " + this._m + ", \"pos\": " + this._p + ", \"vel\": " + this._v + ", \"acc\": " + this._a + "}";
	}
}
