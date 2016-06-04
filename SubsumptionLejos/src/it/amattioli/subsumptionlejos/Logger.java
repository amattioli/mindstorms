package it.amattioli.subsumptionlejos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Pose;

public class Logger {
	private OdometryPoseProvider poseProvider;
	private Writer writer;
	
	public Logger(MovePilot pilot) {
		try {
			writer = new FileWriter("/home/lejos/programs/logger.txt");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		poseProvider = new OdometryPoseProvider(pilot);
	}
	
	public synchronized void log(float dist) {
		if (dist < 100.0) {
			Pose pose = poseProvider.getPose();
			try {
				writer.write(""+pose.getX()+","+pose.getY()+","+pose.getHeading()+","+dist+"\n");
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
