import javafx.application.Application;
import javafx.stage.Stage;
//import javafx.stage.*;

/**
 * @author julian
 * @version 1.0
 * @created 13-Feb-2016 1:56:50 PM
 */

public class Main extends Application{

	public Map m_Map;
	public Robot m_Robot;
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
	 * 
	 * 
	 * @param numRefPoints
	 * @param waypoint1
	 * @param waypoint2
	 * @param range
	 * @param sensorError
	 * @param movementError
	 */
	public static void simulate(int numRefPoints, int[] waypoint1, int[] waypoint2, double range,
			double sensorError, double movementError){

		//Instantiate robot
		Robot robot = new Robot();

		//Instantiate map
		Map map = new Map();
		
		int squirrel;
		
		robot.fromWaypoint = 0;
		robot.toWaypoint = 1;
		
		for (squirrel = 0; squirrel < 2; squirrel++){
			robot.move(gui, map, waypoint1, waypoint2, range, sensorError);
		}
		
		
	}

}