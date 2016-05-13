package it.amattioli.subsumptionlejos;

import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;

public class StopBehaviour implements Behavior {

	@Override
	public boolean takeControl() {
		return Button.readButtons() != 0;
	}

	@Override
	public void action() {
		System.out.println("Stopping");
		System.exit(0);
	}

	@Override
	public void suppress() {
		
	}

}
