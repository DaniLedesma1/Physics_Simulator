package simulator.factories;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T>{
	
	private List<Builder<T>> _builders;
	private /*static*/ List<JSONObject> _factoryElements;
	
	
	//Constructor
	//Inicializa builders mediante la constructora por copia de ArrayList
	//Inicializa factoryElements recorriendo la lista dada invocando a los metodos getBuilderInfo
	public BuilderBasedFactory(List<Builder<T>> list){
		
		_builders = list;
		_factoryElements = new ArrayList<>();
		for(Builder<T> b:_builders) {
			_factoryElements.add(b.getBuilderinfo());	
		}
	}

	@Override
	//Recorre todo builders probando si funciona algun constructor, en ese caso lo devuelve
	//Suponemos que no hay dos iguales
	public T createInstance(JSONObject info) {

		for(Builder<T> b:_builders) {
			T o = b.createInstance(info);
			if(o != null) {
				return o;
			}
		}
		
		throw new IllegalArgumentException("No constructor can be created");
	}

	
	@Override
	//Devuelve en una lista las estructuras JSON devueltas por getBuilderInfo().
	public List<JSONObject> getInfo() {
		return _factoryElements;
	}

}