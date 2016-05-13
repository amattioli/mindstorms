import java.util.Date;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;

public class UltrasonicMain {

	public static void main(String[] args) {
		EV3UltrasonicSensor sensor = new EV3UltrasonicSensor(SensorPort.S2);
		sensor.enable();
		SampleProvider distance= sensor.getMode("Distance");
		SampleProvider averageDistanceProvider = new MeanFilter(distance, 5);
		Date start = new Date();
		while (((new Date()).getTime() - start.getTime()) < 10000) {
			float[] sample = new float[averageDistanceProvider.sampleSize()];
			averageDistanceProvider.fetchSample(sample, 0);
			System.out.println(sample[0]*100);
		}
	}
	
}
