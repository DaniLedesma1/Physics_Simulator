package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{

//	private String _type;
//	private String _desc;
	
	
	//Constructor
	public BasicBodyBuilder() {
		_type = "basic";
		_desc = "Default body";
	}
	
	//Devuelve un objeto Body(comun) ya creado dada una data
	public Body createTheInstance(JSONObject data) {
		
		if(data == null) return null;
		
		//Si tiene atributos
		String id = data.getString("id");
		double[] vel =  jsonArrayToDouble(data.getJSONArray("vel"));
		double[] pos  = jsonArrayToDouble(data.getJSONArray("pos"));
		double mass = data.getDouble("mass");
		
			
		return new Body(id, new Vector(vel), new Vector(vel.length), new Vector(pos), mass);
	}
	
		
	
	
	@Override
	//Devuelve la estructura de un basic body (data) para luego usarla para crear el objeto de informacion de la clase
	public JSONObject createData() {
		
		JSONObject data = new JSONObject();

		data.put("id", "the identifier");
		data.put("pos", "array of possitions (Vector)");
		data.put("vel", "array of velocity (Vector)");
		data.put("mass", "mass of the ovject");
		
		return  new JSONObject();
	}

}


