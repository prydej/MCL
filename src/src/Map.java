package src;

import java.util.Random;

/**
 * @author Stephen Kristin
 * @version 1.1
 * @last modified 3/7/2016
 * 
 * This class uses random number generation to give x and y values for the reference points
 * The reference points are simulated by the 2 dimensional array refPoints[][]
 * 
 */

public class Map{
	
	// seed random number generation
	Random gen = new Random();

	// array for reference points
	public static int[][] refPoints;

	// exception handling 
	public void finalize() throws Throwable {}

	// take the number of points the user wants from the GUI and give the points x and y values 
	
	/**
	 * @param NumberRefPoints
	 */
	public void createPoints(int NumberRefPoints){
		// variables for loop iteration
		int i, j;
		
		for (i=0; i < NumberRefPoints; i++ ){// NumberRefPoints is the number from the GUI
			for (j=0; j<2; j++){
				// j index 0 and 1 for x and y
				refPoints[i][j]= gen.nextInt(100); 
			}			
		}
	}
} 
/**
* end of map class
*/