
import java.util.*;
import java.lang.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.*;
import java.math.*;

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
 **/

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
	public void detectPoints(double rangeOfSensor, double robotX, double robotY, double sensorError) throws IOException{
		
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
		//of the reference points from the robot
		double refPointXLoc = 0.0;
		
		double refPointYLoc = 0.0;
		
		//intermediate variables to hold partially
		//calculated values of ref. point locations
		double tempVarX = 0.0;
		
		double tempVarY = 0.0;

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

					tempVarX = sensorError/pointDetectedX;
					
					tempVarY = sensorError/pointDetectedY;
					
						if(j % 2 == 0){
							
							refPointXLoc = pointDetectedX + tempVarX;
					
							refPointYLoc = pointDetectedY + tempVarY;
						}
						
						if(j % 2 != 0){
							
							refPointXLoc = pointDetectedX - tempVarX;
							
							refPointYLoc = pointDetectedY - tempVarY;
							
						}
					
				

					try{
						
						File saveDetectedPoints = new File("DetectedPoints.txt");
						
						saveDetectedPoints.createNewFile();

						BufferedWriter bWSavePoints = new BufferedWriter(new FileWriter(saveDetectedPoints, true));

						bWSavePoints.write(robotX + "," + robotY + "," + refPointXLoc + "," + refPointYLoc + "\n");
						
						bWSavePoints.newLine();

						bWSavePoints.close();
						
					}
					catch (IOException iOEx1){

						//error message and scene and pane to pop up
						//for final project
						
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
	public void distanceBetweenPoints() throws IOException{
		
		//this method will read the detectedPoints file to figure out the
		//distance between every two point, so 1 and 2, 2 and 3, etc.
		
		double distanceOfRef1FromRobot = 0.0;
		
		double distanceOfRef2FromRobot = 0.0;
		
		double distanceBetween2RefPoints = 0.0;
		
		double radiansOfAngle = 0.0;
		
		double angleOfRobotAndTwoPoints = 0.0;
		
		try{
			
			Scanner readFile = new Scanner(new File("detectedPoints.txt"));
			
			readFile.useDelimiter(",");
			
			double robotsX1, robotsY1, refPointX1, refPointY1, robotsX2,
						robotsY2, refPointX2, refPointY2;
			
			while(readFile.next() != null){
				
				robotsX1 = readFile.nextDouble();
				
				robotsY1 = readFile.nextDouble();
				
				refPointX1 = readFile.nextDouble();
				
				refPointY1 = readFile.nextDouble();
				
				readFile.nextLine();
				
				robotsX2 = readFile.nextDouble();
				
				robotsY2 = readFile.nextDouble();
				
				refPointX2 = readFile.nextDouble();
				
				refPointY2 = readFile.nextDouble();
				
				
				if(robotsX1 == robotsX2 && robotsY1 == robotsY2){
				
					distanceOfRef1FromRobot = Math.sqrt(Math.pow((robotsX1 - refPointX1), 2) + Math.pow((robotsY1 - refPointY1), 2));
				
					distanceOfRef2FromRobot = Math.sqrt(Math.pow((robotsX2 - refPointX2), 2) + Math.pow((robotsY2 - refPointY2), 2));
				
					distanceBetween2RefPoints = Math.sqrt(Math.pow((refPointX1 - refPointX2), 2) + Math.pow((refPointY1 - refPointY2), 2));
					
					radiansOfAngle = Math.acos((Math.pow(distanceOfRef1FromRobot, 2) + Math.pow(distanceOfRef2FromRobot, 2) - Math.pow(distanceBetween2RefPoints, 2))/(2*distanceOfRef1FromRobot*distanceOfRef2FromRobot));
				
					angleOfRobotAndTwoPoints = Math.toDegrees(radiansOfAngle);
					
				}
				else{
					
					distanceOfRef1FromRobot = Math.sqrt(Math.pow((robotsX1-refPointX1), 2) + Math.pow((robotsX2-refPointX2), 2));
				
					
				}
				
				readFile.close();		
			}
		}
		catch(IOException iOEx2){
			
			iOEx2.printStackTrace();
		}	
	}
}