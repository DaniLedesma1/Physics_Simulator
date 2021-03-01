package simulator.model;

import java.util.List;
import simulator.misc.Vector;

public class FallingToCenterGravity implements GravityLaws{
	
	private static final double gConst = 9.81;
	

	@Override
	public void apply(List<Body> bodies) {
		
		for(Body b: bodies) {
			Vector j = b.getPosition();
			b.setAceleration(j.direction().scale(-gConst));
		}
	}
	
	@Override
	public String toString() {
		String info = "Falling To Center Gravity Law";
		return info;
	}
	
	

}