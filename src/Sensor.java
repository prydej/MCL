import java.text.*;
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
	 * @param rangeOfSensor user defined sensor range 
	 * @param robotX the robot's x location at each movement
	 * @param robotY the robot's y location at each movement
	 * @param sensorError user defined error in sensor
	 * @param map object that contains the reference point locations
	 * 
	 * @exception IOException is thrown if the file is not found or if there
	 * is a problem writing to the file.
	 **/
	public void detectPoints(double rangeOfSensor, double robotX, double robotY, double sensorError, Map map) throws IOException{

		/**
		 * loop counters to use in for loops in order
		 * to access points in 2D array of points.
		 */
		int i = 0;
		int j = 0;

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
		 * number of reference points that the 
		 * array created in the map class holds
		 */
		int numberOfRefPoints = map.refPoints.length;

		/**
		 * the distance between the robot at its current
		 * location and the reference point with some error.
		 */
		double distance = 0;


		/**
		 * loop to go through all the reference points
		 * with error to figure out if the robot is 
		 * within the range it should be.
		 */
		for(i = 0; i < numberOfRefPoints; i++){
			
			errorInX = ( (sensorError/100)*map.refPoints[i][j] );
			pointDetectedX = map.refPoints[i][j] + errorInX;
			errorInY = ( (sensorError/100)*map.refPoints[i][j+1] );
			pointDetectedY = map.refPoints[i][j+1] + errorInY;

			distance = Math.sqrt( Math.pow((robotX - pointDetectedX), 2) + Math.pow((robotY - pointDetectedY), 2));

			//if the robot is within the correct range, it will go to the
			//next method to calculate the same distance as a checker.
			if(distance < rangeOfSensor){
				distanceBetweenPoints(robotX, robotY, pointDetectedX, pointDetectedY, sensorError);
			}	
		}
	}


	/**
	 * The distanceBetweenPoints method will figure out the distance
	 * between the robot at each point and a reference point in its
	 * sensing range.
	 * 
	 * @param rx the robot's current x location
	 * @param ry the robot's current y location
	 * @param sx the reference points x location
	 * @param sy the reference points y location 
	 * @param sensorError the error in the sensor 
	 */
	public double distanceBetweenPoints(double rx, double ry, double sx, double sy, double sensorError) throws IOException{

		double distance = 0.0;

		distance = Math.sqrt(Math.pow((rx - sx), 2) + Math.pow((ry-sy), 2));

		calculateRobotLocation(rx, ry, sx, sy, distance, sensorError);
		
		return distance;
	}

	/**
	 * The method determines the robot's estimated location 
	 * from the location given and the reference point with error
	 * and adds some error to the calculations 
	 *
	 * 
	 * @param rx the robot's x location
	 * @param ry the robot's y location
	 * @param sx the reference point x location
	 * @param sy the reference point y location
	 * @param distance the distance between the robot and the reference point
	 * @param sensorError the error in the sensor
	 */
	public double calculateRobotLocation(double rx, double ry, double sx, double sy, double distance, double sensorError) throws IOException{

		double estimatedRobotX = 0.0;

		double estimatedRobotY = 0.0;

		double radians = Math.atan2((sy - ry),(sx - rx));

		estimatedRobotX = sx - (distance * Math.cos(radians));

		estimatedRobotY = sy - (distance * Math.sin(radians));

		double errorInEstX = (sensorError/100)*estimatedRobotX;

		double errorInEstY = (sensorError/100)*estimatedRobotY;

		estimatedRobotX = estimatedRobotX + errorInEstX;

		estimatedRobotY = estimatedRobotY + errorInEstY;

		saveToFile(rx, ry, sx, sy, distance, estimatedRobotX, estimatedRobotY);
		
		return radians;
	}

	/**
	 * This will save the reference points detected by the sensor,
	 * the robots location when it senses the point, the distance 
	 * between the robot and the reference point, and the robots
	 * estimation of its location at each instant.
	 * 
	 * @param arx the robot's x location
	 * @param ary the robot's y location
	 * @param rpx the reference point x location
	 * @param rpy the reference point y location 
	 * @param dist the distance between the robot and the reference point
	 * @param erx the robot's estimated x location
	 * @param ery the robot's estimated y location 
	 * 
	 * @exception catches IO exception
	 */
	public int saveToFile(double arx, double ary, double rpx, double rpy, double dist, double erx, double ery) throws IOException{

		DecimalFormat df1 = new DecimalFormat("#.###");

		String aRX = df1.format(arx);

		String aRY = df1.format(ary);

		String rPX = df1.format(rpx);

		String rPY = df1.format(rpy);

		String dIst = df1.format(dist);

		String eRX = df1.format(erx);

		String eRY = df1.format(ery);

		try{
			File saveFile = new File("DataFile.txt");
			saveFile.createNewFile();
			BufferedWriter bWSavePoints = new BufferedWriter(new FileWriter(saveFile, true));
			bWSavePoints.write(aRX + "\t" + aRY + "\t" + rPX + "\t" + rPY + 
					"\t" + dIst + "\t" + eRX + "\t" + eRY);
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
		
		return 0;
	}
}
