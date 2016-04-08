/**
 * @author julian
 * credit to: Jason Samuel Koch for gaussian rng.
 * @version 1.0
 */


import java.util.Random;
import java.io.IOException;

public class Robot {

	private double[] calcPosition, nextPosition; //2 elements: 1st is x positions, 2nd is y positions
	private double[][] positionsWError;
	private double[][] waypoints = {{0,0},{0,0}};
	public double[][] positions;
	private double movementError, distBetweenWaypoints, distFromLastWaypoint, 
			error, xError, yError;
	public int fromWaypoint, toWaypoint, numWaypoints, chipmunk;
	private int numMoves = 0;
	private String outputString;

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
		
		this.positions = new double[totalDist][2]; //ceiling of total distance travelled by robot
		this.positionsWError = new double[totalDist][2];
		
		// Instantiate nextPosition
		nextPosition = new double[2];
		
		// 
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
	public double[][] move(double range, double sensorError, GUI gui, Map map, Sensor sensor){

		//Find distance between fromWaypoint and toWaypoint
		distBetweenWaypoints = Math.sqrt(Math.pow((waypoints[toWaypoint][0] - waypoints[fromWaypoint][0]),2) + 
				Math.pow((waypoints[toWaypoint][1] - waypoints[fromWaypoint][1]),2));

		//loop through all stops between waypoints
		for (chipmunk = 0; chipmunk < distBetweenWaypoints; chipmunk++){
			
			//find next positions 1 unit away from last positions
			//	divide horz and vert component by distance between actual positions and toWaypoint #UnitVector
			nextPosition[0] = (waypoints[1][0] - positions[0][0])/distBetweenWaypoints;
			nextPosition[1] = (waypoints[toWaypoint][1] - positions[chipmunk][1])/distBetweenWaypoints;

			//change positions var to new positions
			positions[chipmunk][0] = nextPosition[0];
			positions[chipmunk][1] = nextPosition[1];
			numMoves++;//count number of updates since last change of waypoint

			//add error
			Random errorGen = new Random(); //create rng object

			while (Math.pow(xError,2) + Math.pow(yError,2) > 4) { //make sure x, y are in sensible range
				xError = errorGen.nextGaussian() * 2;
				yError = errorGen.nextGaussian() * 2;
			}

			positionsWError[chipmunk][0] += xError; //add error in x direction to positions
			positionsWError[chipmunk][1] += yError; //add error in y direction to positions

			//after robot has traveled ceil(distBetweenWaypoint)
			if (numMoves == Math.ceil(distBetweenWaypoints)){
				fromWaypoint = toWaypoint; //set current toWaypoint to fromWaypoint
				toWaypoint++;//	set next waypoint to toWaypoint
			}

			//call sensor.sense()
			try{
			sensor.detectPoints(range, positionsWError[chipmunk][0], positionsWError[chipmunk][1], sensorError);
			} catch (IOException e) {
				System.out.println("IO Exception");
			}
			
			//send info to file
			IO.writeRunData(positions, positionsWError);
		}
		return positions;
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
			distance += Math.sqrt(Math.pow((waypoints[beaver][0] - waypoints[beaver + 1][1]),2) + 
					Math.pow((waypoints[beaver][1] - waypoints[beaver + 1][1]),2));
		}
		
		return distance;
	}

}