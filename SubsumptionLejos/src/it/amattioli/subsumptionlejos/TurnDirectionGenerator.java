package it.amattioli.subsumptionlejos;

import java.util.Date;

public class TurnDirectionGenerator {
	private long timeThreshold = 4000;
	private long lastInvoked = new Date().getTime();
	private float lastGenerated = 1.0f;
	
	public float getTurnDirection() {
		long now = new Date().getTime();
		if (now-lastInvoked > timeThreshold) {
			lastGenerated = (float)Math.signum(Math.random()-0.5f);
			System.out.println("Generated "+lastGenerated+" at "+now);
		}
		lastInvoked = now;
		return lastGenerated;
	}
}
