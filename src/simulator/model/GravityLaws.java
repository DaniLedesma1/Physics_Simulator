package simulator.model;

import java.util.List;

public interface GravityLaws {
	
	//FUNCION DE LA CLASE (IMPLEMENTADAS POR LAS HIJAS)
	public void apply(List<Body>bodies);
	public String toString();
	
}
