package simulator.factories;

import java.util.List;

import org.json.JSONObject;

public interface Factory<T> {

	//Describe el objeto que va a crear, devuelve una instancia de la clase que le corresponde
	public T createInstance(JSONObject info);

	//Devuelve una lista de plantillas para estructuras validas
	public List<JSONObject> getInfo();
	
}
