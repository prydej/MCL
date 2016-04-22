import static org.junit.Assert.*;

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
	

}
