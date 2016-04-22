import java.util.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * @author Miralda Rodney
 * 
 * @version 1.0
 * 
 * 
 * In this Sensor Class, the sensor will determine whether or not 
 * there is a point within its range that is a "recognized" reference point.
 * When a point is detected, robots' location and the estimated reference point 
 * location is saved in a file.
 * 
 * The Sensor Class will perform calculation to determine the distance between 
 * itself (robot) and the points around it. It will "track" its location in relation
 * to the points around it so that the Robot can determine it's own x and y location to 
 * figure out where it began on the "field".
 *
 **/

public class Sensor {

	/**
	 * This is the default constructor for the sensor method.
	 */
	public Sensor(){
	}

	/**
	 * The detectPoints method uses the static array of randomly generated 
	 * reference points from the Map class. The sensor will check the area 
	 * in its scope (defined by the user) to figure out if there are any 
	 * points in the vicinity. When a point is detected, the estimated location
	 * of the point is saved formatted in a file.
	 *
	 *
	 * @param rangeOfSensor - user defined sensor range 
	 * @param robotX - the robot's x location at each movement
	 * @param robotY - the robot's y location at each movement
	 * @param sensorError - user defined error in sensor
	 * 
	 * 
	 * @exception IOException is thrown if the file is not found or if there
	 * is a problem writing to the file.
	 **/
	public void detectPoints(double rangeOfSensor, double robotX, double robotY, double sensorError) throws IOException{

		/**
		 * loop counters to use in for loops in order
		 * to access points in 2D array of points.
		 */
		int i = 0;
		int j = 0;

		/**variables to represent the sensor range based
		 * on the user defined scope and the robots' 
		 * current  X and Y location
		 */
		double rangeSensor = Math.sqrt(Math.pow((robotX - rangeOfSensor),2) + Math.pow(robotY - rangeOfSensor, 2));

		/**
		 * variables to hold the sensed reference point
		 * x- and y-values with accounted error.
		 */
		double pointDetectedX = 0.0;
		double pointDetectedY = 0.0;

		/**
		 * variables to hold the error account for based on 
		 * where the point is in the robots' range. Closer means
		 * less error, farther means full error as defined by user.
		 */
		double errorInX = 0.0;
		double errorInY = 0.0;

		/**
		 * loop to access the information in the reference point
		 * array. Based on the region where the reference point is 
		 * sensed, the error will be the (1) user-defined max at the
		 * edge, (2) two-thirds of the error in the midpoint region, 
		 * and (3) one-third of the error in the inner ring and right
		 * on top of a point.
		 */
		
		for(i = 0; i< Map.refPoints.length; i++){

			for(j = 0; j < Map.refPoints[i].length - 2; j++){

				//if( (Map.refPoints[i][j] > ((2/3)*rangeSensor) && Map.refPoints[i][j] <= (rangeSensor))  && (Map.refPoints[i][j+1] > ((2/3)*rangeSensor) && Map.refPoints[i][j+1] <= (rangeSensor)) ){

					errorInX = ( (sensorError/100)*Map.refPoints[i][j] );
					pointDetectedX = Map.refPoints[i][j] + errorInX;
					errorInY = ( (sensorError/100)*Map.refPoints[i][j+1] );
					pointDetectedY = Map.refPoints[i][j+1] + errorInY;

					distanceBetweenPoints(robotX, robotY, pointDetectedX, pointDetectedY);
					
				//}
				/*if(Map.refPoints[i][j] <= ((1/3)*rangeSensor) && Map.refPoints[i][j+1] <= ((1/3)*rangeSensor)){

					errorInX = ( ((0.3)*sensorError/100)*Map.refPoints[i][j] );
					pointDetectedX = Map.refPoints[i][j] + errorInX;
					errorInY = ( ((0.3)*sensorError/100)*Map.refPoints[i][j+1] );
					pointDetectedY = Map.refPoints[i][j+1] + errorInY;

					distanceBetweenPoints(robotX, robotY, pointDetectedX, pointDetectedY);
				}
				if( (Map.refPoints[i][j] > ((1/3)*rangeSensor) && Map.refPoints[i][j] <= ((2/3)*rangeSensor)) && (Map.refPoints[i][j+1] > ((1/3)*rangeSensor) && Map.refPoints[i][j+1] <= ((2/3)*rangeSensor)) ){

					errorInX = ( ((0.6)*sensorError/100)*Map.refPoints[i][j] );
					pointDetectedX = Map.refPoints[i][j] + errorInX;
					errorInY = ( ((0.6)*sensorError/100)*Map.refPoints[i][j+1] );
					pointDetectedY = Map.refPoints[i][j+1] + errorInY;

					distanceBetweenPoints(robotX, robotY, pointDetectedX, pointDetectedY);*/
				}
			}
		}
	//}
		
	/**
	 * The distanceBetweenPoints method will figure out the distance
	 * between the robot at each point and a reference point in its
	 * sensing range.
	 * 
	 * @exception IOException is thrown
	 */
	public void distanceBetweenPoints(double rx, double ry, double sx, double sy){
		
		double distance = 0.0;
		
		distance = Math.sqrt(Math.pow((rx - sx), 2) + Math.pow((ry-sy), 2));
		
		calculateRobotLocation(rx, ry, sx, sy, distance);
	}
	
	public void calculateRobotLocation(double rx, double ry, double sx, double sy, double distance){
		
		double estimatedRobotX = 0.0;
		
		double estimatedRobotY = 0.0;
		
		double radians = Math.atan2((sy - ry),(sx - rx));
		
		estimatedRobotX = sx + (distance * Math.cos(radians));
		
		estimatedRobotY = sy + (distance * Math.sin(radians));
		
		saveToFile(rx, ry, sx, sy, distance, estimatedRobotX, estimatedRobotY);
	}
	
	/**
	 * This will save the reference points detected by the sensor,
	 * the robots location when it senses the point, the distance 
	 * between the robot and the reference point, and the robots
	 * estimation of its location at each instant.
	 * 
	 * @param arx
	 * @param ary
	 * @param rpx
	 * @param rpy
	 * @param dist
	 * @param erx
	 * @param ery
	 * 
	 * @exception IO exception
	 */
	public void saveToFile(double arx, double ary, double rpx, double rpy, double dist, double erx, double ery){

		try{
			File saveFile = new File("DataFile.txt");
			saveFile.createNewFile();
			BufferedWriter bWSavePoints = new BufferedWriter(new FileWriter(saveFile, true));
			bWSavePoints.write(arx + "\t" + ary + "\t" + rpx + "\t" + rpy + 
					"\t" + dist + "\t" + erx + "\t" + ery);
			bWSavePoints.newLine();
			bWSavePoints.close();
		}
		catch (IOException iOEx1){
			Stage fileNotFound = new Stage();
			fileNotFound.initStyle(StageStyle.UNIFIED);
			Scene scene = new Scene(new Group(new Text(25, 25, "There was an error while trying to create and write your information to the file :(")));
			fileNotFound.setScene(scene);
			fileNotFound.show();
		}	
	}
}
