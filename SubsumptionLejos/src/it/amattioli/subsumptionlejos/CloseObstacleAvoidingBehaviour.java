package it.amattioli.subsumptionlejos;

import java.util.Date;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;
import lejos.robotics.subsumption.Behavior;

public class CloseObstacleAvoidingBehaviour implements Behavior {
	private float minDistance;
	private MovePilot pilot;
	private EV3UltrasonicSensor ultrasonicSensor;
	private boolean suppressed = false;
	private Logger logger;
	private OdometryPoseProvider poseProvider;
	
	public CloseObstacleAvoidingBehaviour(MovePilot pilot, EV3UltrasonicSensor ultrasonicSensor, float minDistance, Logger logger) {
		this.minDistance = minDistance;
		this.pilot = pilot;
		this.ultrasonicSensor = ultrasonicSensor;
		this.poseProvider = new OdometryPoseProvider(pilot);
		this.logger = logger;
	}
	
	@Override
	public boolean takeControl() {
		SampleProvider distance = ultrasonicSensor.getMode("Distance");
		SampleProvider averageDistanceProvider = new MeanFilter(distance, 5);
		float[] sample = new float[averageDistanceProvider.sampleSize()];
		averageDistanceProvider.fetchSample(sample, 0);
//		System.out.println(sample[0]*100);
		Pose pose = poseProvider.getPose();
		float lastDistanceSampled = sample[0]*100;
		logger.log(pose.getX(), pose.getY(), pose.getHeading(), lastDistanceSampled);
		return lastDistanceSampled < minDistance;
	}

	@Override
	public void action() {
		pilot.travel(-20);
		pilot.rotate(90);
	}

	@Override
	public void suppress() {
		this.suppressed = true;
	}

}
