package simulator.model;

import java.util.List;

import simulator.misc.Vector;


public class NewtonUniversalGravitation implements GravityLaws{
	
	private static final double gravityConst = 6.67E-11; // m/kgï¿½
	

	//FUNCIONES DE LA CLASE
	@Override
	public void apply(List<Body> bodies) {		
		
		for(Body i: bodies) { //Cuerpos a modificar	
			Vector F = new Vector(i.getVelocity().dim());

			
			if(i.getMass() == 0.0) {
				
				i.setAceleration(new Vector(i.getVelocity().dim()));
				i.setVelocity(new Vector(i.getVelocity().dim()));
			}
			
			else {
				for(Body j : bodies) { 	//Cuerpos que intervienen
					Vector Faux = new Vector(j.getVelocity().dim());
					Vector d;
					double f;
					
					if(j != i) {
									
						//Ley de gravitaciÃ³n universal
						f = gravityConst* ((i.getMass() * j.getMass()) / (Math.pow(j.getPosition().distanceTo(i.getPosition()), 2)));
						d = j.getPosition().minus(i.getPosition()).direction();
						
						Faux = d.scale(f);

						F = F.plus(Faux); //Primera ley de Newton (suma de las fuerzas de los cuerpos)
					}
				}	
				
				//Segunda ley de Newton
				i.setAceleration(F.scale(1/i.getMass()));
			}
			

		}
		
	}
	
	@Override
	public String toString() {
		String info = "";
		info = "Newton´s Universal Gravitation Law";
		
		return info;
	}
	
	
}
