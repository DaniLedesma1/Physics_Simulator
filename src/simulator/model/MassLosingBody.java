package simulator.model;

import simulator.misc.Vector;

public class MassLosingBody extends Body{
	
	private double lossFactor;
	private double lossFrequency;
	private double c;
	
	//CONSTRUCTOR 1 
	public MassLosingBody (String id, Vector v, Vector a, Vector p, double m, double lossFac, double lossFre){
		super(id, v, a, p, m);
		this.c = 0.0;
		this.lossFactor = lossFac;
		this.lossFrequency = lossFre;
	}
	
	
	//FUNCIONES DE LA CLASE
	@Override
	void move(double t) {
		super.move(t);
		c += t;

		
		//La practica no especifica eliminar un cuerpo si su masa es cero (o inferior) por lo que no lo hemos
		//implementado a pesar de que no tenga sentido
		if(c >= this.lossFrequency) {
			_m = _m * (1 - this.lossFactor);
			c = 0.0;
		}	
	}

	
}
