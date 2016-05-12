import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.GraphicsLCD;
import static lejos.hardware.lcd.GraphicsLCD.*;
import lejos.utility.Delay;

public class HelloLeJos {
	
	public static void main(String[] args) {
		GraphicsLCD g = BrickFinder.getDefault().getGraphicsLCD();
		g.drawString("Hello LeJos", 0, 0, VCENTER | LEFT);
		Delay.msDelay(5000);
	}

}