/**
 * @author julian
 * @version 1.0
 */


import java.util.Random;
import java.io.IOException;

public class Robot {

	private double[] calcPosition, nextPosition; //2 elements: 1st is x positions, 2nd is y positions
	private double[][] positionsWError;
	private double[][] waypoints = {{0,0},{0,0}};
	public double[][] positions;
	private double distBetweenWaypoints, xError, yError, distToNextWaypoint;
	public int fromWaypoint, toWaypoint, numWaypoints, chipmunk;

	/*
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
		this.positions = new double[totalDist][2]; //ceiling of total distance travelled by robot
		positions[0][0] = start[0];
		positions[0][1] = start[1];

		//initialize positions with error
		this.positionsWError = new double[totalDist][2];
		positionsWError[0][0] = start[0];
		positionsWError[0][1] = start[1];

		// Instantiate nextPosition
		nextPosition = new double[2]; 
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
	 * move() moves the robot to its next positions, calls sense and calculate
	 * @return double array of positions betwen waypoints after robot hits a waypoint
	 */
	public void move(double range, double sensorError,double movementError, GUI gui, Map map, Sensor sensor){

		//Find distance between fromWaypoint and toWaypoint
		distBetweenWaypoints = Math.sqrt(Math.pow((waypoints[toWaypoint][0] - waypoints[fromWaypoint][0]),2) + 
				Math.pow((waypoints[toWaypoint][1] - waypoints[fromWaypoint][1]),2));

		// Initialize nextPosition
		nextPosition[0] = (waypoints[1][0] - positions[0][0])/distBetweenWaypoints; 
		nextPosition[1] = (waypoints[1][1] - positions[0][1])/distBetweenWaypoints;

		//loop through all stops between waypoints
		for (chipmunk = 1; chipmunk < distBetweenWaypoints; chipmunk++){

			//change positions var to new positions
			positions[chipmunk][0] = Math.floor((positions[chipmunk - 1][0] + nextPosition[0])*1000)/1000;
			positions[chipmunk][1] = Math.floor((positions[chipmunk - 1][0] + nextPosition[1])*1000)/1000;

			
			//add movement error
			Random errorGen = new Random(); //create rng object

			xError = errorGen.nextGaussian() * movementError; //multiply for standard deviation
			yError = errorGen.nextGaussian() * movementError;

			positionsWError[chipmunk][0] = Math.floor((positionsWError[chipmunk - 1][0] + nextPosition[0] + xError)*1000)/1000; //add error in x direction to positions
			positionsWError[chipmunk][1] = positionsWError[chipmunk - 1][1] + nextPosition[1] + yError; //add error in y direction to positions

			//call sensor.sense()
			try{
				sensor.detectPoints(range, positionsWError[chipmunk][0], positionsWError[chipmunk][1], sensorError);
			} catch (IOException e) {
				System.out.println("IO Exception");
			}

			//find next positions 1 unit away from last positions
			// find distance from next waypoint
			distToNextWaypoint = this.distFormula(waypoints[toWaypoint], positions[chipmunk]);
			
			// ivide horz and vert component by distance between actual positions and toWaypoint #UnitVector
			nextPosition[0] = (waypoints[toWaypoint][0] - positions[chipmunk][0])/distToNextWaypoint; 
			nextPosition[1] = (waypoints[toWaypoint][1] - positions[chipmunk][1])/distToNextWaypoint;
		}

		fromWaypoint = toWaypoint; //set current toWaypoint to fromWaypoint
		toWaypoint++;//	set next waypoint to toWaypoint
		System.out.println("Next Waypoint");

		//send info from run to file
		IO.writeRunData(positions, positionsWError);
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