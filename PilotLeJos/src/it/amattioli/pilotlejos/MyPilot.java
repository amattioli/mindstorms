package it.amattioli.pilotlejos;

import lejos.hardware.motor.Motor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;
import lejos.utility.Delay;

public class MyPilot {
	
	private static void line(MovePilot pilot) {
		pilot.travel(90);
	}
	
	private static void square(MovePilot pilot) {
		for (int i=0; i<4; i++) {
			pilot.travel(50);
			pilot.arc(20, 90);
		}
	}
	
	private static void otto(MovePilot pilot) {
		pilot.travel(30);
		pilot.arc(15, 270);
		pilot.travel(30);
		pilot.arc(-15, 270);
	}

	public static void main(String[] args) throws Exception {
		Wheel wheel1 = WheeledChassis.modelWheel(Motor.A, 5.5634).offset(-7);
		Wheel wheel2 = WheeledChassis.modelWheel(Motor.D, 5.5634).offset(7);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		OdometryPoseProvider poseProvider = new OdometryPoseProvider(pilot);
		pilot.setAngularSpeed(30);
		pilot.setLinearAcceleration(5);
		pilot.setLinearSpeed(10); // cm per second
//		pilot.travel(50); // cm
//		pilot.rotate(-90); // degree clockwise
//		pilot.travel(-50, true); // move backward for 50 cm
//		while (pilot.isMoving()) Thread.yield();
//		pilot.rotate(-90);
		line(pilot);
		Pose pose = poseProvider.getPose();
		System.out.println(pose.getX()+" "+pose.getY());
		pilot.stop();
		Delay.msDelay(5000);
	}

}
