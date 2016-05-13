package it.amattioli.subsumptionlejos;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class CollisionBackupBehaviour implements Behavior {
	private EV3TouchSensor touch;
	private MovePilot pilot;
	private int turnDirection = 1;
	
	public CollisionBackupBehaviour(MovePilot pilot, EV3TouchSensor touch, int turnDirection) {
		this.pilot = pilot;
		this.touch = touch;
		this.turnDirection = turnDirection;
	}
	
	@Override
	public boolean takeControl() {
		float[] sample = new float[1];
		touch.fetchSample(sample, 0);
		return sample[0] > 0;
	}

	@Override
	public void action() {
		pilot.travel(-20);
		pilot.rotate(90*turnDirection);
	}

	@Override
	public void suppress() {
	}

}
