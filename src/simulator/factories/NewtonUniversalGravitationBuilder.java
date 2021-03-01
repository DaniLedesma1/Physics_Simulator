package simulator.factories;

import org.json.JSONObject;

import simulator.model.GravityLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<GravityLaws> {

	public NewtonUniversalGravitationBuilder() {
		_type = "nlug";
		_desc = "Newton's gavitation (nlug)";
	}
	
	
		//@Override
	protected GravityLaws createTheInstance(JSONObject obj) {
		return new NewtonUniversalGravitation();
	}

}
