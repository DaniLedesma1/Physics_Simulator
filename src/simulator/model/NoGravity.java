package simulator.model;

import java.util.List;

public class NoGravity implements GravityLaws{

	//FUNCIONES DE LA CALSE
	@Override
	public void apply(List<Body> bodies) {
		//No hace nada, aceleraci�n constante
	}

	@Override
	public String toString() {
		String info = "";
		info = "No Gravity Law";
		
		return info;
	}
}
