package it.amattioli.subsumptionlejos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Logger {
	private Writer writer;
	
	public Logger() {
		try {
			writer = new FileWriter("/home/lejos/programs/logger.txt");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public synchronized void log(float x, float y, float heading, float dist) {
		if (dist < 100.0) {
			try {
				writer.write(""+x+","+y+","+heading+","+dist+"\n");
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
