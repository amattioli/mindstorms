package it.amattioli.subsumptionlejos;

import lejos.hardware.Audio;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.subsumption.Behavior;

public class BeepBehaviour implements Behavior {
	private EV3TouchSensor touch;

	public BeepBehaviour(EV3TouchSensor touch) {
		this.touch = touch;
	}
	
	@Override
	public boolean takeControl() {
		float[] sample = new float[1];
		touch.fetchSample(sample, 0);
		return sample[0] > 0;
	}

	@Override
	public void action() {
		BrickFinder.getDefault().getAudio().systemSound(Audio.DOUBLE_BEEP);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
