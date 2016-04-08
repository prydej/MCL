/**
 * @author julian
 * @version 1.0
 * @created 13-Feb-2016 1:56:50 PM
 */

import org.json.simple.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author prydej
 *
 */
public class IO {

	//private String strToSave;

	public IO(){

	}

	public void finalize() throws Throwable {

	}

	@SuppressWarnings("unchecked")
	public static void writeRunData(double[][] positions, double[][] positionsWError){

		//Create json object for all run positions
		JSONObject runPositions = new JSONObject();

		//Write positions with error to json array
		JSONArray x_error = new JSONArray(); //create json array of all x comps of pos w/o error
		for (int cat = 0; cat < positions.length; cat++){
			x_error.add(positionsWError[cat][0]); //add all x componenents of positions w/ error to array
		}
		
		runPositions.put("x_error", x_error); //add x comps of pos w/error to object

		JSONArray y_error = new JSONArray(); //create json array of all y comps of pos w/ error
		for (int dog = 0; dog < positions.length; dog++){
			y_error.add(positionsWError[dog][0]);
		}
		
		runPositions.put("y_error", y_error); //add y comps of pos w/error to object

		//Write positions without error to json array
		JSONArray x_actual = new JSONArray(); //create json array of all x comps of pos w/o error
		for (int cat = 0; cat < positions.length; cat++){
			x_actual.add(positionsWError[cat][0]);
		}
		runPositions.put("x_actual", x_actual); //add x comps of pos w/o error to object

		
		JSONArray y_actual = new JSONArray(); //create json array of all y comps of pos w/o error
		for (int dog = 0; dog < positions.length; dog++){
			y_actual.add(positionsWError[dog][0]);
		}
		runPositions.put("y_actual", y_actual); //add y comps of pos w/o error to object
		
		//Create name for new file
		DateFormat formatObj = new SimpleDateFormat("ddMMMYY-HH-mm-ss"); //Format for date for name of file
		Date dateObj = new Date(); //date for filename
		String filename = formatObj.format(dateObj); //set filename
		
		//Write to file
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false)); //create writer object
			writer.write(runPositions.toJSONString());
			
			//Close filewriter
			writer.close();
			System.out.println("Ding!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}