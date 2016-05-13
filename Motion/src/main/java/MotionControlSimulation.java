import static java.lang.Math.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.awt.Color;
import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

public class MotionControlSimulation extends JFrame {
	private static class Pose {
		int step;
		double x, y, theta;
	}
	
	public MotionControlSimulation() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);

        DataTable data = new DataTable(Double.class, Double.class);
        for (Pose curr: calc()) {
            data.add(curr.x, curr.y);
        }
        XYPlot plot = new XYPlot(data);
        getContentPane().add(new InteractivePanel(plot));
        LineRenderer lines = new DefaultLineRenderer2D();
        plot.setLineRenderers(data, lines);
        Color color = new Color(0.0f, 0.3f, 1.0f);
        plot.getPointRenderers(data).get(0).setColor(color);
        plot.getLineRenderers(data).get(0).setColor(color);
    }

	public ArrayList<Pose> calc() {
		final double t_step = 1E-3;
		final int max_steps = 10000;
		final double rho_threshold = 1E-2;
		
		final double k_rho = 3;
		final double k_alfa = 8;
		final double k_beta = -1.5;
		
		final double x0 = 10.0;
		final double y0 = 5.0;
		final double theta0 = -1.0/2.0*PI;
		
		double rho = sqrt(x0*x0 + y0*y0);
		double alfa = -theta0 + atan2(-y0,-x0);
		double beta = -theta0 - alfa;
		
		int i = 0;
		ArrayList<Pose> result = new ArrayList<Pose>();
		while(rho > rho_threshold && (i++) < max_steps) {
	 		double rho_p = -k_rho * rho * cos(alfa);
			double alfa_p = k_rho * sin(alfa) - k_alfa * alfa - k_beta * beta;
			double beta_p = -k_rho * sin(alfa);
			
//			System.out.println("rho_p:"+rho_p+"  alfa_p:"+alfa_p+"  beta_p"+beta_p);
			
			rho = rho + rho_p * t_step;
			alfa = alfa + alfa_p * t_step;
			beta = beta + beta_p * t_step;
			
//			System.out.println((i++)+"  rho:"+rho+"  alfa:"+alfa+"  beta:"+beta);
			
			double x = -rho * cos(-beta);
			double y = -rho * sin(-beta);
			double theta = -beta - alfa;
			
			if (i==1 || i%100 == 0) System.out.println(i+"  x:"+x+"  y:"+y+"  theta:"+theta);
			Pose curr = new Pose();
			curr.step = i;
			curr.x = x;
			curr.y = y;
			curr.theta = theta;
			result.add(curr);
		}
		return result;
	}
	
	public static void main(String[] args) {
        MotionControlSimulation frame = new MotionControlSimulation();
        frame.setVisible(true);
    }
}
