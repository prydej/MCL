/**
 * @author julian
 * credit to: Jason Samuel Koch for gaussian rng.
 * @version 1.0
 */

import java.util.Random;
import java.io.IOException;
import java.util.Arrays;

public class Robot {

	private Sensor sensor;
	private double[] calcPosition, nextPosition; //2 elements: 1st is x position, 2nd is y position
	private double[] positionWError = {0,0};
	private double[][] waypoints = {{0,0},{0,0}};
	private double[][] position;
	private double movementError, distBetweenWaypoints, distFromLastWaypoint, 
			error, xError, yError;
	private int fromWaypoint, toWaypoint, numWaypoints, chipmunk;
	private int numMoves = 0;
	private String runString;

	/**
	 * @param start: starting position x and y
	 * @param end: ending position x and y
	 */
	public Robot(int[] start, int[] end){
		sensor = new Sensor();

		// Set waypoint values
		waypoints[0][0] = (double) start[0];
		waypoints[0][1] = (double) start[1];
		waypoints[1][0] = (double) end[0];
		waypoints[1][1] = (double) end[1];

		// Instantiate position vector
		int totalDist = (int) Math.ceil(this.findTotalDist(waypoints));
		
		this.position = new double[totalDist][2]; //ceiling of total distance travelled by robot
		
		// Instantiate nextPosition
		nextPosition = new double[2];
	}
	
	public int getNumWaypoints(){
		return this.numWaypoints;
	}

	public void finalize() throws Throwable {

	}



	/**
	 * move() moves the robot to its next position, calls sense and calculate.
	 *
	 * @param range: Sensor range.
	 * @param sensorError: Error in the return from the sensors at max range. Error decreases linearly to zero with
	 *                   distance of sensed point from the sensor.
	 * @return double array of positions betwen waypoints after robot hits a waypoint
	 */
	public double[][][] move(){ //double range, double sensorError){

		//Find distance between fromWaypoint and toWaypoint
		distBetweenWaypoints = Math.sqrt(Math.pow((waypoints[toWaypoint][0] - waypoints[fromWaypoint][0]),2) + 
				Math.pow((waypoints[toWaypoint][1] - waypoints[fromWaypoint][1]),2));

		//Create array of positions with error for testing
		int totalMoves = (int) Math.ceil(this.findTotalDist(waypoints));
		double[][] arrPosWError = new double[totalMoves][2];

		//loop through all stops between waypoints
		for (chipmunk = 0; chipmunk < totalMoves; chipmunk++){
			
			//find next position 1 unit away from last position
			//	divide horz and vert component by distance between actual position and toWaypoint #UnitVector
			nextPosition[0] = (waypoints[1][0] - position[0][0])/distBetweenWaypoints;
			nextPosition[1] = (waypoints[toWaypoint][1] - position[chipmunk][1])/distBetweenWaypoints;

			//change position var to new position
			position[chipmunk][0] = nextPosition[0];
			position[chipmunk][1] = nextPosition[1];
			numMoves++;//count number of updates since last change of waypoint

			//add error
			Random errorGen = new Random(); //create rng object

			while (Math.pow(xError,2) + Math.pow(yError,2) > 4) { //make sure x, y are in sensible range
				xError = errorGen.nextGaussian() * 2;
				yError = errorGen.nextGaussian() * 2;
			}

			positionWError[0] += xError; //add error in x direction to position
			positionWError[1] += yError; //add error in y direction to position

			//after robot has traveled ceil(distBetweenWaypoint)
			if (numMoves == Math.ceil(distBetweenWaypoints)){
				fromWaypoint = toWaypoint; //set current toWaypoint to fromWaypoint
				toWaypoint++;//	set next waypoint to toWaypoint
			}

			//call sensor.sense()
//			try{
//				sensor.detectPoints(range, positionWError[0], positionWError[1], sensorError);
//			} catch (IOException e) {
//				System.out.println("IO Exception.");
//			}
			
			//Calculate position
			//position[chipmunk] = this.calculate(positionWError, Map.refPoints);
			
			//send info to GUI
			runString = runString + "Move: " + chipmunk + "\n" +
					"Ideal Positions: " + Arrays.toString(position[chipmunk]) + "\n" +
					"Positions with Error " + Arrays.toString(positionWError) + "\n";

			//save positions for testing
			arrPosWError[chipmunk][0] = positionWError[0];
			arrPosWError[chipmunk][1] = positionWError[1];

		}

		//Create array of positions and positions with error for testing
		double[][][] runPositions = new double[2][totalMoves][2];

		runPositions[0] = position; //first layer is idealized positions
		runPositions[1] = arrPosWError; //second layer is positions with error

		return runPositions;
	}
	
	public double findTotalDist(double[][] waypoints){
		
		double distance = 0;
		
		int length = waypoints.length;
		
		//For loop to loop through each distance traveled
		for(int beaver = 0; beaver < length - 1; beaver++){
			
			//Find distance between waypoints[beaver] and waypoints[beaver + 1]
			distance += Math.sqrt(Math.pow((waypoints[beaver][0] - waypoints[beaver + 1][1]),2) +
					Math.pow((waypoints[beaver][1] - waypoints[beaver + 1][1]),2));
		}
		
		return distance;
	}

}