/**
 * @author julian
 * @version 1.0
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.IOException;

public class Robot {

	private double[] calcPosition; //2 elements: 1st is x positions, 2nd is y positions
	public double[][] positionsWError, positions, positionsEstimate;
	public double[][] waypoints = {{0,0},{0,0}};
	private double distBetweenWaypoints, xError, yError, distToNextWaypoint;
	public int fromWaypoint, toWaypoint, numWaypoints, chipmunk;

	/**
	 * @param start: starting positions x and y
	 * @param end: ending positions x and y
	 */

	public Robot(int[] start, int[] end){

		// Set waypoint values
		waypoints[0][0] = (double) start[0];
		waypoints[0][1] = (double) start[1];
		waypoints[1][0] = (double) end[0];
		waypoints[1][1] = (double) end[1];

		// Instantiate positions vector
		int totalDist = (int) Math.ceil(this.findTotalDist(waypoints));
		this.positions = new double[totalDist + 1][2]; //ceiling of total distance travelled by robot
		positions[0][0] = start[0];
		positions[0][1] = start[1];

		//initialize positions with error
		this.positionsWError = new double[totalDist + 1][2];
		positionsWError[0][0] = start[0];
		positionsWError[0][1] = start[1];
		
		this.positionsEstimate = new double[totalDist + 1][2];
		positionsEstimate[0][0] = start[0];
		positionsEstimate[0][1] = start[1];
	}

	public int getNumWaypoints(){
		return this.numWaypoints;
	}

	public void finalize() throws Throwable {

	}

	public double[] findPosition(){
		return null;
	}

	/**
	 * move() moves the robot to its next positions, calls calculate
	 * @return double array of positions betwen waypoints after robot hits a waypoint
	 * @param range: Sensor range
	 * @param sensorError: percentage of detection distance
	 * @param movementError: Standard Deviation of gaussian random number
	 * @param map: object of class map
	 * @param sensor: object of class sensor
	 * @param io: object of class IO
	 * @param debug: whether or not to call detectPoints and run to file
	 */
	public ArrayList<double[][]> move(double range, double sensorError,double movementError, Map map, Sensor sensor, IO io, boolean debug){

		//Find distance between fromWaypoint and toWaypoint
		distBetweenWaypoints = Math.sqrt(Math.pow((waypoints[1][0] - waypoints[0][0]),2) + 
				Math.pow((waypoints[1][1] - waypoints[0][1]),2));
		
		//Find length of one move in x and y direction
		double moveLengthX = (waypoints[1][0] - waypoints[0][0])/distBetweenWaypoints;
		double moveLengthY = (waypoints[1][1] - waypoints[0][1])/distBetweenWaypoints;
		
		//Create evenly-spaced array of x and y values between waypoints
		for (int piano = 1; piano < distBetweenWaypoints + 1; piano++){
			positions[piano][0] = positions[piano - 1][0] + moveLengthX;
			positions[piano][1] = positions[piano - 1][1] + moveLengthY;
			
		}

		//loop through all stops between waypoints
		for (chipmunk = 1; chipmunk < distBetweenWaypoints + 1; chipmunk++){

			//add movement error
			Random errorGen = new Random(); //create rng object

			xError = errorGen.nextGaussian() * movementError; //multiply for standard deviation
			yError = errorGen.nextGaussian() * movementError;

			//update positions with error, quit program if robot runs off the map
			//add error in x direction to positions
			positionsWError[chipmunk][0] = positionsWError[chipmunk - 1][0] + moveLengthX + xError;

			//stop at a boundary
			if (positionsWError[chipmunk][0] < 0){
				positionsWError[chipmunk][0] = 0;

			} else if (positionsWError[chipmunk][0] > 100){
				positionsWError[chipmunk][0] = 100;
			}

			//add error in y direction to positions
			positionsWError[chipmunk][1] = positionsWError[chipmunk - 1][1] + moveLengthY + yError;

			//reverse direction if robot hits top or bottom boundary
			if (positionsWError[chipmunk][1] < 0){
				positionsWError[chipmunk][1] = 0;

			} else if (positionsWError[chipmunk][1] > 100){
				positionsWError[chipmunk][1] = 100;
			}

			if (debug == false){ //Do not detect points if debugging
				//call sensor.sense()
				try{
					this.positionsEstimate[chipmunk] = sensor.detectPoints(range, positionsWError[chipmunk][0], 
							positionsWError[chipmunk][1], sensorError, map);
				} catch (IOException e) {
					System.out.println("IO Exception");
				}
			}
		}
		
		System.out.println("The robot estimates that it is at " + Arrays.toString(this.positionsEstimate[40]));

		//fromWaypoint = toWaypoint; //set current toWaypoint to fromWaypoint
		//toWaypoint++;//	set next waypoint to toWaypoint

		if (debug == false){ //Do not write to file if in debug mode
			//send info from run to file
			io.writeRunData(positions, positionsWError, positionsEstimate);
		}
		
		ArrayList<double[][]> posReturn = new ArrayList<>();
		
		posReturn.add(positions);
		posReturn.add(positionsWError);
		posReturn.add(positionsEstimate);
		
		return posReturn;
	}

	/**
	 * 
	 * @param position 
	 * @param points
	 * @return
	 */
	public double[] calculate(double[] position, int[][] points){

		calcPosition[0] = 0;
		calcPosition[1] = 1;

		return calcPosition;
	}

	public double findTotalDist(double[][] waypoints){

		double distance = 0;

		int length = waypoints.length;

		//For loop to loop through each distance traveled
		for(int beaver = 0; beaver < length - 1; beaver++){

			//Find distance between waypoints[beaver] and waypoints[beaver + 1]
			distance += Math.sqrt(Math.pow((waypoints[beaver][0] - waypoints[beaver + 1][0]),2) + 
					Math.pow((waypoints[beaver][1] - waypoints[beaver + 1][1]),2));
		}

		return distance;
	}

	public double distFormula(double[] one, double[] two){

		double distance;

		//Distance formula sqrt((x1 - x2)^2 + (y1 - y2)^2)
		distance = Math.sqrt(Math.pow((one[0] - two[0]),2) + Math.pow((one[1] - two[1]),2));

		return distance;
	}

}