
public class RobotPose {
	private double x,y,theta,ox,oy;
	
	public RobotPose(double x,double y,double theta,double ox,double oy) {
		this.x = x;
		this.y = y;
		this.theta = theta;
		this.ox = ox;
		this.oy = oy;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getOx() {
		return ox;
	}

	public void setOx(double ox) {
		this.ox = ox;
	}

	public double getOy() {
		return oy;
	}

	public void setOy(double oy) {
		this.oy = oy;
	}
	
	
}
