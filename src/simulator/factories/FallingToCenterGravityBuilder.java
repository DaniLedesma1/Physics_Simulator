package simulator.factories;

import org.json.JSONObject;

import simulator.model.FallingToCenterGravity;
import simulator.model.GravityLaws;

public class FallingToCenterGravityBuilder extends Builder<GravityLaws>{

	//Constructor1
	public FallingToCenterGravityBuilder() {
		_type = "ftcg";
		_desc = "Fall to the center (ftcg)";
	}
	
	
	
	//@Override
	public GravityLaws createTheInstance(JSONObject obj) {
		return new FallingToCenterGravity();
	}
	
}
