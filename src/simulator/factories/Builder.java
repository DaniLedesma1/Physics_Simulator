package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Builder<T> {

	protected String _type;
	protected String _desc;
	
	//Constructor1
	public Builder() {}
	
	
	//Devuelve un objeto T ya creado dada una info, llama a las clases que heredan
	protected abstract T createTheInstance(JSONObject obj);

	
	//Crea un objeto T a partir de un objeto JSON
	public T createInstance(JSONObject info) {
		
		T ret = null;
		if((_type != null) && (_type.equals(info.getString("type")))) {
			JSONObject aux = info.getJSONObject("data");
			if(aux != null) {
				ret = createTheInstance(aux);

			}	
			else throw new IllegalArgumentException("No type-data matches found");
		}
																										//DICE DEVOLVER EL OBJETO CON TYPE, DATA Y DESC PERO SOLO DEVOLVEMOS DATA
																										//HE MODIFICADO ESTO PARA QUE ESTÉ MAS ACORDE AL ENUNCIADO, FALTA METER DESC??? NO CREO QUE ESTE TERMINADO
		return ret;
	}
	
	
	//Devuelve el objeto JSON asociado a un objeto
	public JSONObject getBuilderinfo(){
		
		JSONObject info = new JSONObject();
		info.put("type", _type);
		info.put("data", createData());
		info.put("desc", _desc);
		
		return info;
	}
	
	
	//Devuelve la un objeto JSON con la data del objeto
	protected JSONObject createData() {
		return new JSONObject(); 																	 //ESTO LLAMA A LAS CLASES CONCRETAS
	}																								 //En la guia pone que se rellenan los cmapos con string 
	

	//Devuelve un array a partir de una estructura JSONArray
	protected double[] jsonArrayToDouble(JSONArray j) {
		
	    double[] d = new double[j.length()];
	    for (int i = 0; i < d.length; i++)
	    d[i] = j.getDouble(i);
	    return d;
	}


	
}
