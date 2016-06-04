import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RobotPath implements Iterable<RobotPose> {
	private List<RobotPose> poses = new ArrayList<>();

	public static RobotPath read() throws Exception {
		RobotPath result = new RobotPath();
		Path path = FileSystems.getDefault().getPath("c:","Users","andrea","Documents","logger.txt");
		List<String> events = Files.readAllLines(path, StandardCharsets.ISO_8859_1);
		for (String event: events) {
			String[] eventElements = event.split(",");
			if (eventElements.length == 4) {
				double x = Double.valueOf(eventElements[0]);
				double y = Double.valueOf(eventElements[1]);
				double heading = Double.valueOf(eventElements[2]);
				double dist = Double.valueOf(eventElements[3]);
				
				double theta = heading/180.0*PI;
				double ox = x+dist*cos(theta);
				double oy = y+dist*sin(theta);
				
				result.poses.add(new RobotPose(x, y, theta, ox, oy));
			}
		}
		return result;
	}
	
	public Iterator<RobotPose> iterator() {
		return poses.iterator();
	}
}
