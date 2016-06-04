import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static java.lang.Math.*;

public class ShowMap {
	private static int MAX_X = 600;
	private static int MAX_Y = 600;
	private static double SCALE = 4.0;
	private static int DISPL_X = 200;
	private static int DISPL_Y = 300;

	private JFrame frame;
	private DrawPanel drawPanel;
    
	private int oneX, oneY;
	private double heading;
    
	private RobotPath path;
	private List<Integer> mapX = new ArrayList<>();
	private List<Integer> mapY = new ArrayList<>();

    public static void main(String[] args) throws Exception {
    	
        new ShowMap().go();
    }

    private void go() throws Exception {
    	path = RobotPath.read();
    	
        frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawPanel = new DrawPanel();

        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(MAX_X, MAX_Y);
        frame.setLocation(375, 55);
        moveIt();
    }

    class DrawPanel extends JPanel {
        public void paintComponent(Graphics g) {
//            g.setColor(Color.BLUE);
//            g.fillRect(0, 0, this.getWidth(), this.getHeight());
//            g.setColor(Color.RED);
//            g.fillRect(3, 3, this.getWidth()-6, this.getHeight()-6);
            g.setColor(Color.WHITE);
            g.fillRect(1, 1, this.getWidth()-2, this.getHeight()-2);
            g.setColor(Color.BLACK);
            
            Polygon p = new Polygon();
            double r = 10;
            for (int i=0; i<3; i++) {
            	p.addPoint((int)(oneX+r*cos(heading+i*2*PI/3.0)), (int)(oneY+r*sin(heading+i*2*PI/3.0)));
            }
            g.drawPolygon(p);
            
            for (int i=0; i<mapX.size(); i++) {
            	g.fillRect(mapX.get(i)-2, mapY.get(i)-2, 5, 5);
            }
//            g.fillRect(oneX, oneY, 6, 6);
        }
    }

    private void moveIt() {
        for (RobotPose currPose: path){
            oneX = (int)(currPose.getX()*SCALE+DISPL_X);
            oneY = (int)(currPose.getY()*SCALE+DISPL_Y);
            heading = currPose.getTheta();
            mapX.add((int)(currPose.getOx()*SCALE+DISPL_X));
            mapY.add((int)(currPose.getOy()*SCALE+DISPL_Y));
            System.out.println(""+oneX+" "+oneY);
            try{
                Thread.sleep(10);
            } catch (Exception exc){}
            frame.repaint();
        }
    }
	
}
