package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLossingBodyBuilder extends Builder<Body>{

	public MassLossingBodyBuilder() {
		_type = "mlb";
		_desc = "Mass losing body";
	}
	
	
	//Devuelve un objeto Body(msaslosing) ya creado dada una data
	public MassLosingBody createTheInstance(JSONObject data) {
		MassLosingBody mbd = null;
		
		if(data != null) { //Si tiene atributos
			String id = data.getString("id");
			double[] pos  = jsonArrayToDouble(data.getJSONArray("pos"));
			double[] vel =  jsonArrayToDouble(data.getJSONArray("vel"));
			double mass = data.getDouble("mass");
			double freq = data.getDouble("freq");
			double factor = data.getDouble("factor");
			
			mbd = new MassLosingBody(id, new Vector(vel), new Vector(vel.length), new Vector(pos), mass, factor, freq);
		}
		
		return mbd;
	}
	
	
	@Override
	//Devuelve la estructura de un masslosing body (data) para luego usarla para crear el objeto de informacion de la clase
	public JSONObject createData() {
		
		JSONObject data = new JSONObject();

		data.put("id", "the identifier");
		data.put("pos", "array of possitions (Vector)");
		data.put("vel", "array of velocity (Vector)");
		data.put("mass", "mass of the ovject");
		data.put("freq", "frecuency of time");
		data.put("factor", "factor of losing mass");
		return  new JSONObject();
	}

}
