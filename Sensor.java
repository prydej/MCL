
import java.util.*;
import java.lang.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.*;

/**
 * @author Miralda Rodney
 * 
 * @version 1.0
 * 
 * 
 * In this Sensor Class, the 
 * sensor will determine whether or not 
 * there is a point within its range that
 * is a reference point. The points are saved
 * formatted in a file.
 *
 */

/**
 * @author prydej
 *
 */
@SuppressWarnings("unused")

public class Sensor {

	public Sensor(){

	}

	public void finalize() throws Throwable{
	}

	/**
	 * Description of detectPoints method which uses
	 * the map class' reference point array. 
	 * 
	 * @param rangeOfSensor - user defined sensor range 
	 * @param robotX - the robot's x location at each movement
	 * @param robotY - the robot's y location at each movement
	 * @param sensorError - user defined error in sensor
	 * 
	 * Description of detectPoints method which takes in three 
	 * parameters and does not return any values.
	 **/
	public void detectPoints(double rangeOfSensor, double robotX, double robotY, double sensorError){
		
		//loop counters
		int i = 0;

		int j = 0;

		//using map class object
		int[][] refPointLoc = Map.refPoints;

		//defining the sensor range based on range of sensor
		//and the robots' current location
		double rangeSensorX = rangeOfSensor + robotX;

		double rangeSensorY = rangeOfSensor + robotY;

		//created variables to check whether
		//or not a point has actually been detected
		int pointDetectedX = -1;

		int pointDetectedY = -1;
		
		//variables to hold the estimated locations
		//of the reference points and the robot
		double estimatedXLoc = 0.0;
		
		double estimatedYLoc = 0.0;

		//loop to go through array of reference points 
		//and determine if any are within the sensors' range
		for(i = 0; i < refPointLoc.length; i++){

			for(j = 0; j < refPointLoc[i].length; j++){

				if(i == 0){
					if(refPointLoc[i][j] <= rangeSensorX ){
						pointDetectedX = refPointLoc[i][j];
					}
				}	

				if( i == 1){
					if(refPointLoc[i][j] <= rangeSensorY){
						pointDetectedY = refPointLoc[i][j];
					}
				}

				//when the value of point detected changes, then the values
				//are saved to a file with the error accounted for
				if(pointDetectedX != -1 && pointDetectedY != -1){

					estimatedXLoc = pointDetectedX*(sensorError/100);
					
					estimatedYLoc = pointDetectedY*(sensorError/100);
					
					File saveDetectedPoints = new File("DetectedPoints.txt");

					try{
						saveDetectedPoints.createNewFile();

						BufferedWriter bWSavePoints = new BufferedWriter(new FileWriter(saveDetectedPoints, true));

						bWSavePoints.write("" + estimatedXLoc + " " + estimatedYLoc + "\n");
						bWSavePoints.newLine();

						//System.out.println(pointDetectedX + " " + pointDetectedY);

						bWSavePoints.close();
					}
					catch (IOException iOEx1){

						//error message and scene and pane to pop up
						//printing stack trace for demo 1
						iOEx1.printStackTrace();
					}
				}
			}
		}
	}
	/**
	 * distanceBetweenPoints method will get
	 * the points from a file and measure the 
	 * distance between two at a time to estimate robot's 
	 * location
	 */
	public void distanceBetweenPoints(){
		
		//this method will read the detectedPoints file to figure out the
		//distance between every two point, so 1 and 2, 2 and 3, etc.
		
		
		//to hold the robot's estimated x and y locations
		double robotsXLocFromPoints = 0.0;
		
		double robotsYLocFromPoints = 0.0;
	}
}