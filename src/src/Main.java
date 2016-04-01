package src;
import javafx.application.Application;
import javafx.stage.Stage;
//import javafx.stage.*;

/**
 * @author julian
 * @version 1.0
 * @created 13-Feb-2016 1:56:50 PM
 */

public class Main extends Application{

	public static GUI gui;

	public Main(){
		
	}

	public void finalize() throws Throwable {

	}

	public static void main(String[] args) throws Exception {
		
		Application.launch(args);
	}
	
	
	public void start(Stage stage) throws Exception{
		GUI gui = new GUI();
		
		gui.start(stage);
	}

	/**
	 * @param numRefPoints number of reference points
	 * @param waypoint1 first point in robot's path
	 * @param waypoint2 2nd point in robot's path
	 * @param range sensor range
	 * @param sensorError sensor error decimal
	 * @param movementError movement error decimal
	 */
	public static void simulate(int numRefPoints, int[] waypoint1, int[] waypoint2, double range,
			double sensorError, double movementError){

		//Instantiate robot
		Robot robot = new Robot(waypoint1, waypoint2);

		//Instantiate map
		Map map = new Map();
		
		int squirrel;
		
		robot.fromWaypoint = 0;
		robot.toWaypoint = 1;
		
		for (squirrel = 0; squirrel < 2; squirrel++){
			robot.move(gui, map, range, sensorError);
		}
		
		
	}

}