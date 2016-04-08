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

		/**
		 * creating a local array that points to the 
		 * array of reference points generated by the
		 * Map class.
		 */
		int[][] refPointLoc = Map.refPoints;

		/**variables to represent the sensor range based
		 * on the user defined scope and the robots' 
		 * current  X and Y location
		 */
		double rangeSensorX = rangeOfSensor + robotX;
		double rangeSensorY = rangeOfSensor + robotY;

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
		 * distance from the point to the robot's current location
		 */
		double distFromRobot = 0.0;

		/**
		 * loop to access the information in the reference point
		 * array. Based on the region where the reference point is 
		 * sensed, the error will be the (1) user-defined max at the
		 * edge, (2) two-thirds of the error in the midpoint region, 
		 * and (3) one-third of the error in the inner ring and right
		 * on top of a point.
		 */
		
		for(i = 0; i < (refPointLoc.length -1); i++){

			for(j = 0; j < refPointLoc[i].length; j++){

				if( (refPointLoc[i][j] > ((2/3)*rangeSensorX) && refPointLoc[i][j] <= (rangeSensorX))  && (refPointLoc[i+1][j] > ((2/3)*rangeSensorY) && refPointLoc[i+1][j] <= (rangeSensorY)) ){

					errorInX = ( (sensorError/100)*refPointLoc[i][j] );
					pointDetectedX = refPointLoc[i][j] + errorInX;
					errorInY = ( (sensorError/100)*refPointLoc[i+1][j] );
					pointDetectedY = refPointLoc[i+1][j] + errorInY;

					saveToFile(robotX, robotY, pointDetectedX, pointDetectedY);
					
				}
				if(refPointLoc[i][j] <= ((1/3)*rangeSensorX) && refPointLoc[i+1][j] <= ((1/3)*rangeSensorY)){

					errorInX = ( ((0.3)*sensorError/100)*refPointLoc[i][j] );
					pointDetectedX = refPointLoc[i][j] + errorInX;
					errorInY = ( ((0.3)*sensorError/100)*refPointLoc[i+1][j] );
					pointDetectedY = refPointLoc[i+1][j] + errorInY;

					saveToFile(robotX, robotY, pointDetectedX, pointDetectedY);
				}
				if( (refPointLoc[i][j] > ((1/3)*rangeSensorX) && refPointLoc[i][j] <= ((2/3)*rangeSensorX)) && (refPointLoc[i+1][j] > ((1/3)*rangeSensorY) && refPointLoc[i+1][j] <= ((2/3)*rangeSensorY)) ){

					errorInX = ( ((0.6)*sensorError/100)*refPointLoc[i][j] );
					pointDetectedX = refPointLoc[i][j] + errorInX;
					errorInY = ( ((0.6)*sensorError/100)*refPointLoc[i+1][j] );
					pointDetectedY = refPointLoc[i+1][j] + errorInY;

					saveToFile(robotX, robotY, pointDetectedX, pointDetectedY);
				}
			}
		}
	}
		
	/**
	 * This will save the points detected in the format 
	 * of (point1,point2,point3, point4) in a file.
	 * 
	 * @param rx
	 * @param ry
	 * @param sx
	 * @param sy
	 */
	public void saveToFile(double rx, double ry, double sx, double sy){

		try{
			File saveDetectedPoints = new File("DetectedPoints.txt");
			saveDetectedPoints.createNewFile();
			BufferedWriter bWSavePoints = new BufferedWriter(new FileWriter(saveDetectedPoints, true));
			bWSavePoints.write(rx + "," + ry + "," + sx + "," + sy + "\n");
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
	/**
	 * The distanceBetweenPoints method will get the 
	 * 
	 * @exception IOException is thrown
	 */
	public void distanceBetweenPoints() throws IOException{
		
		int counter1 = 0;
		
		int counter2 = 0;
		
		double[] actRobotsX = new double[Map.refPoints.length];
		
		double[] actRobotsY = new double[Map.refPoints.length];
		
		double[] refPointX = new double[Map.refPoints.length];
		
		double[] refPointY = new double[Map.refPoints.length];
		
		double distancePt1 = 0.0;
		
		double distancePt2 = 0.0;
		
		double distancePt12 = 0.0;
		
		double distanceToPt = 0.0;
		
		double angleToPt = 0.0;
		
		double ratioDistances = 0.0;
		
		double[] estRobotsX = new double[Map.refPoints.length];
		
		double[] estRobotsY = new double[Map.refPoints.length];
		
		try{
			
			File foundPointHandle = new File("DetectedPoints.txt");
			
			Scanner readPointsFound = new Scanner(foundPointHandle);
			
			readPointsFound.useDelimiter(",");
			
			while(readPointsFound.next() != null){
				
				actRobotsX[counter1] = readPointsFound.nextDouble();
				
				actRobotsY[counter1] = readPointsFound.nextDouble();
				
				refPointX[counter1] = readPointsFound.nextDouble();
				
				refPointY[counter1] = readPointsFound.nextDouble();
				
				counter1++;
			}
			
			for(counter2 = 0; counter2 < counter1-1; counter2++){
			
				if(actRobotsX[counter2] == actRobotsX[counter2+1] && actRobotsY[counter2] == actRobotsY[counter2+1]){
				//from dzone.com
				distancePt1 = Math.sqrt(Math.pow((actRobotsX[counter2] - refPointX[counter2]), 2) + Math.pow((actRobotsY[counter2]-refPointY[counter2]), 2));
				
				distancePt12 = Math.sqrt(Math.pow((refPointX[counter2] - refPointX[counter2+1]), 2) + Math.pow((refPointY[counter2]-refPointY[counter2+1]), 2));
				
				ratioDistances = distancePt12 / distancePt1;
				
				estRobotsX[counter2] = (1 - ratioDistances)*refPointX[counter2] + ratioDistances*refPointX[counter2+1];
				
				estRobotsY[counter2] = (1 - ratioDistances)*refPointY[counter2] + ratioDistances*refPointY[counter2+1];
 			
				}
				else{
					
					distanceToPt = Math.sqrt(Math.pow((actRobotsX[counter2]- refPointX[counter2]), 2) + Math.pow((actRobotsY[counter2]- refPointY[counter2]),2));
					
					angleToPt = (double) Math.toDegrees(Math.atan2((actRobotsY[counter2] -refPointY[counter2]), (actRobotsX[counter2] - refPointX[counter2])));
					
					estRobotsX[counter2] = refPointX[counter2] + (distanceToPt * Math.cos(angleToPt));
					
					estRobotsY[counter2] = refPointY[counter2] + (distanceToPt * Math.cos(angleToPt));
				}
			}
			readPointsFound.close();
			
		}catch(IOException iOEx2){
		
			Stage fileNotFound2 = new Stage();
			fileNotFound2.initStyle(StageStyle.UNIFIED);
			Scene scene = new Scene(new Group(new Text(25, 25, "The file you want to get information from is nowhere to be found :(")));
			fileNotFound2.setScene(scene);
			fileNotFound2.show();
		}
	}
}