package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NoGravity;

public class NoGravityBuilder extends Builder<GravityLaws> {

	public NoGravityBuilder() {
		_type = "ng";
		_desc = "No gravity (ng)";
	}
	


	//Devuelve una NoGravityLaw sin data ya que no debe tener
	protected GravityLaws createTheInstance(JSONObject obj) {
		return new NoGravity(); 
	}
	

}
