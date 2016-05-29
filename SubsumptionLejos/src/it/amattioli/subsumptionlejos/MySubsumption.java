package it.amattioli.subsumptionlejos;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class MySubsumption {
	
	private static EV3TouchSensor leftTouchSensor() {
		return new EV3TouchSensor(SensorPort.S1);
	}
	
	private static EV3TouchSensor rightTouchSensor() {
		return new EV3TouchSensor(SensorPort.S4);
	}
	
	private static EV3UltrasonicSensor ultrasonicSensor() {
		EV3UltrasonicSensor sensor = new EV3UltrasonicSensor(SensorPort.S2);
		sensor.enable();
		return sensor;
	}

	private static MovePilot pilot() {
		Wheel wheel1 = WheeledChassis.modelWheel(Motor.A, 5.6).offset(-7);
		Wheel wheel2 = WheeledChassis.modelWheel(Motor.D, 5.6).offset(7);
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearSpeed(5);
		pilot.setAngularSpeed(25);
		return pilot;
	}
	
	public static void main(String[] args) {
		EV3TouchSensor leftTouch = leftTouchSensor();
		EV3TouchSensor rightTouch = rightTouchSensor();
		EV3UltrasonicSensor ultrasonicSensor = ultrasonicSensor();
		MovePilot pilot = pilot();
		Logger logger = new Logger();
		Behavior[] behaviors = new Behavior[] {
				new CruiseBehaviour(pilot),
				new ObstacleAvoidingBehaviour(pilot, ultrasonicSensor, 50, logger),
//				new StopBehaviour(),
				new CloseObstacleAvoidingBehaviour(pilot, ultrasonicSensor, 10, logger),
				new CollisionBackupBehaviour(pilot, leftTouch, 1),
				new CollisionBackupBehaviour(pilot, rightTouch, -1)
		};
		Arbitrator arbitrator = new Arbitrator(behaviors);
		arbitrator.go();
	}

}
