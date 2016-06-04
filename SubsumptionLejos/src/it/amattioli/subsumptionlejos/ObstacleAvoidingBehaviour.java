package it.amattioli.subsumptionlejos;

import java.util.Date;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class ObstacleAvoidingBehaviour implements Behavior {
	private float minDistance;
	private MovePilot pilot;
	private EV3UltrasonicSensor ultrasonicSensor;
//	private SampleProvider averageDistanceProvider;
	private boolean suppressed = false;
	private float lastDistanceSampled;
	private TurnDirectionGenerator turnDirectionGenerator = new TurnDirectionGenerator();
	private Logger logger;
	
	public ObstacleAvoidingBehaviour(MovePilot pilot, EV3UltrasonicSensor ultrasonicSensor, float minDistance, Logger logger) {
		this.minDistance = minDistance;
		this.pilot = pilot;
		this.ultrasonicSensor = ultrasonicSensor;
		this.lastDistanceSampled = minDistance * 2.0f;
		this.logger = logger;
	}
	
	@Override
	public boolean takeControl() {
		SampleProvider distance= ultrasonicSensor.getMode("Distance");
		SampleProvider averageDistanceProvider = new MeanFilter(distance, 5);
		float[] sample = new float[averageDistanceProvider.sampleSize()];
		averageDistanceProvider.fetchSample(sample, 0);
//		System.out.println(sample[0]*100);
		this.lastDistanceSampled = sample[0]*100;
		logger.log(this.lastDistanceSampled);
		return this.lastDistanceSampled < minDistance;
	}

	@Override
	public void action() {
		pilot.arcForward(this.lastDistanceSampled/2.0f * turnDirectionGenerator.getTurnDirection());
		Date start = new Date();
		long passedTime = 0;
		while(!suppressed && (passedTime < 1000)) {
			Thread.yield();
//			Delay.msDelay(100);
			passedTime = (new Date()).getTime() - start.getTime();
//			System.out.println(passedTime);
		}
		pilot.stop();
		this.suppressed = false;
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}

}
