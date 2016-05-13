package it.amattioli.subsumptionlejos;

import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class CruiseBehaviour implements Behavior {
	private MovePilot pilot;
	private boolean suppressed = false;
	
	public CruiseBehaviour(MovePilot pilot) {
		this.pilot = pilot;
	}
	
	@Override
	public boolean takeControl() {
		this.suppressed = false;
		return true;
	}

	@Override
	public void action() {
		pilot.forward();
		while(!suppressed) {
			Thread.yield();
//			Delay.msDelay(100);
		}
		pilot.stop();
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}

}
