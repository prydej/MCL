/**
 * @author julian
 * credit to: Jason Samuel Koch for gaussian rng.
 * @version 1.0
 */


import java.util.Random;
import java.io.IOException;
import java.util.Arrays;

public class Robot {

	public Sensor sensor;
	public SensedMap m_SensedMap;
	private double[] calcPosition, positionWError, nextPosition; //2 elements: 1st is x position, 2nd is y position
	private double[][] waypoints = {{0,0},{0,0}};
	public double[][] position;
	private double movementError, distBetweenWaypoints, distFromLastWaypoint, 
			error, xError, yError;
	public int fromWaypoint, toWaypoint, numWaypoints, chipmunk;
	private int numMoves = 0;
	private String outputString;

	public Robot(){
		sensor = new Sensor();
		
		// Instantiate position vector
		this.position = new double[(int) Math.ceil(this.findTotalDist(waypoints))][2]; //ceiling of total distance travelled by robot
		
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
	 * move() moves the robot to its next position, calls sense and calculate.
	 * 
	 * @param gui class
	 * @param map: class with reference points
	 * @param start: starting position x and y
	 * @param end: ending position x and y
	 * @return double array of positions betwen waypoints after robot hits a waypoint
	 */
	public double[][] move(GUI gui, Map map, int[] start, int[] end, double range, double sensorError){
		
		
		waypoints[0][0] = (double) start[0];
		waypoints[0][1] = (double) start[1];
		waypoints[1][0] = (double) end[0];
		waypoints[1][1] = (double) end[1];

		//Find distance between fromWaypoint and toWaypoint
		distBetweenWaypoints = Math.sqrt(Math.pow((waypoints[toWaypoint][0] - waypoints[fromWaypoint][0]),2) + 
				Math.pow((waypoints[toWaypoint][1] - waypoints[fromWaypoint][1]),2));

		//loop through all stops between waypoints
		for (chipmunk = 0; chipmunk < distBetweenWaypoints; chipmunk++){
			
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
			try{
			sensor.detectPoints(range, positionWError[0], positionWError[1], sensorError);
			} catch (IOException e) {
				System.out.println("IO Exception");
			}
			
			//call this.calculate()
			position[chipmunk] = this.calculate(positionWError, Map.refPoints);
			
			//send info to GUI
			outputString = "Ideal Positions: " + Arrays.toString(position[chipmunk]) + 
					"\nPositions with Error " + Arrays.toString(positionWError);
			IO.setMoveString(outputString);		
			
		}
		return position;
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
		
		//For loop to loop through each distance traveled
		for(int beaver = 0; beaver < waypoints.length - 1; beaver++){
			//Find distance between waypoints[beaver] and waypoints[beaver + 1]
			distance += Math.sqrt(Math.pow((waypoints[beaver][0] - waypoints[beaver + 1][1]),2) + 
					Math.pow((waypoints[beaver][1] - waypoints[beaver + 1][1]),2));
		}
		
		return distance;
	}

}