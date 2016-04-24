import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class MCLJunitTesting {

	/**
	 * @author Stephen
	 * 
	 * Junit test cases for testing the map class and making sure the reference points are generated properly
	 * 
	 */	

	@Test
	public void createPointsTest() {
		Map a = new Map();

		a.createPoints(0);
		assertEquals(0, a.refPoints.length);

	}

	/**
	 * Test each reference point for proper values between the limits of 1 and 100
	 */
	@Test
	public void referencePointsLimitTest(){
		Map b = new Map();

		b.createPoints(10);

		int value = 0;
		int i, j;		

		for (i=0; i < 10; i++ ){
			for (j=0; j<2; j++){
				// loop through each point value				
				if (((b.refPoints[i][j]) > 100)||((b.refPoints[i][j]) < 1)){
					value = 1;
				}
			}			
		}		
		assertEquals(value, 0);
	}// end reference points limit testing

	//Robot Class tests

	/**
	 * @author Julian Pryde
	 * Tests that a new robot is created with 
	 */
	@Test
	public void testNormalConstruction() {

		try {
			Robot robot = new Robot(new int[] {0, 0}, new int[] {100, 100});
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * @author Julian Pryde
	 * Tests that the robot ends at the correct end x- and y-value when no error is added and robot moves from 0,0 to 100,100
	 */
	@Test
	public void testDiagDestination(){

		Robot robot = new Robot(new int[] {0, 0}, new int[] {100, 100});

		//For passing to robot.move
		Map map = new Map();
		Sensor sensor = new Sensor();
		IO io = new IO();

		robot.move(0, 0, 1, map, sensor, io, true); //debug == true

		//Test that the final x coordinate is within a tolerance of 1 unit due to rounding error
		if (!(Math.abs(robot.positions[robot.positions.length][0] - 100) < 1)){ //if last x-position is NOT less than 1 unit from 100
			fail();
		}

		//Test that the final y coordinate is within a tolerance of 1 unit due to rounding error
		if (!(Math.abs(robot.positions[robot.positions.length][1] - 100) < 1)){ //if last x-position is NOT less than 1 unit from 100
			fail();
		}
	}
	
	/**
	 * @author Julian Pryde
	 * Tests that robot ends at correct x- and y- value when no error is added and robot moves along a wall
	 */
	@Test
	public void testVertDestination(){
		Robot robot = new Robot(new int[] {0, 0}, new int[] {100, 0});

		//For passing to robot.move
		Map map = new Map();
		Sensor sensor = new Sensor();
		IO io = new IO();

		robot.move(0, 0, 1, map, sensor, io, true); //debug == true

		//Test that the final x coordinate is within a tolerance of 1 unit due to rounding error
		if (!(Math.abs(robot.positions[robot.positions.length][0] - 100) < 1)){ //if last x-position is NOT less than 1 unit from 100
			fail();
		}

		//Test that the final y coordinate is within a tolerance of 1 unit due to rounding error
		if (!(Math.abs(robot.positions[robot.positions.length][1] - 0) < 1)){ //if last x-position is NOT less than 1 unit from 100
			fail();
		}
	}
	
	/**
	 * @author Julian Pryde
	 * Tests that the robot "bounces" back off of a boundary
	 *  - Must be run more than once to make sure that the robot never tried to cross a boundary
	 *  - Tests specifically for crossing the left boundary
	 */
	@Test
	public void test(){
		
		Robot robot = new Robot(new int[] {0, 0}, new int[] {100, 0});

		//For passing to robot.move
		Map map = new Map();
		Sensor sensor = new Sensor();
		IO io = new IO();

		robot.move(0, 0, 1, map, sensor, io, true); //debug == true
		
		for (int jaguar = 0; jaguar < 100; jaguar++){
			if (robot.positions[jaguar][0] < 0){
				fail();
			}
		}
	}
	
	
	@Test
	public void testDistanceCalculation(){
		
		Sensor s1 = new Sensor();
		
		double difAllow = 0.001;
		
		try {
			assertEquals(35.35, s1.distanceBetweenPoints(0, 0, 25, 25, 8), difAllow);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testFileCreation(){
		
		Sensor s2 = new Sensor();
		
		try{
			assertEquals(0, s2.saveToFile(0, 0, 0, 0, 0, 0, 0));
		}catch(IOException e){
			
			e.printStackTrace();
		}
		
	}
}
