/**
 * @author julian and stephen
 * @version 1.25
 * @created 13-Feb-2016 1:56:50 PM
 * @modified 8-Apr-2016 3:16:25 PM
 */

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

//import java.util.ArrayList.*;
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
	public void writeRunData(double[][] positions, double[][] positionsWError){

		//Truncate positionsWError to 2 decimals
		positions = this.truncate2DArray(positions);
		positionsWError = this.truncate2DArray(positionsWError);

		//Create json object for all run positions
		JSONObject runPositions = new JSONObject();

		//Write positions with error to json array
		//Write x positions with movement error to json object
		JSONArray x_ideal = new JSONArray(); //create json array of all x comps of pos w/o error
		for (int cat = 0; cat < positions.length; cat++){

			x_ideal.add(positions[cat][0]); //add all x componenents of positions w/ error to array
		}
		runPositions.put("x_ideal", x_ideal); //add x comps of pos w/error to object

		System.out.println(runPositions);

		//Write y positions with movement error to json object
		JSONArray y_ideal = new JSONArray(); //create json array of all y comps of pos w/ error
		for (int dog = 0; dog < positions.length; dog++){

			y_ideal.add(positions[dog][1]);
		}
		runPositions.put("y_ideal", y_ideal); //add y comps of pos w/error to object

		System.out.println(runPositions);

		//Write positions without error to json array
		//write x positions WITHOUT movement error to json object
		JSONArray x_actual = new JSONArray(); //create json array of all x comps of pos w/o error
		for (int fish = 0; fish < positionsWError.length; fish++){

			x_actual.add(positionsWError[fish][0]);
		}
		runPositions.put("x_actual", x_actual); //add x comps of pos w/o error to object

		System.out.println(runPositions);

		//write y positions WITHOUT movement error to json object
		JSONArray y_actual = new JSONArray(); //create json array of all y comps of pos w/o error
		for (int hamster = 0; hamster < positionsWError.length; hamster++){

			y_actual.add(positionsWError[hamster][1]);
		}
		runPositions.put("y_actual", y_actual); //add y comps of pos w/o error to object

		//for testing

		//Create name for new file
		DateFormat formatObj = new SimpleDateFormat("ddMMMYY-HH-mm-ss"); //Format for date for name of file
		Date dateObj = new Date(); //date for filename
		String filename = formatObj.format(dateObj); //set filename

		//Write to file
		//Create Gson object for pretty printing
		Gson prettyPositions = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(runPositions.toJSONString());
		String prettyOutput = prettyPositions.toJson(je);

		//If Windows, replace \n's with \r\n's for accurate printing
		String pattern = "Windows.*";
		Pattern r = Pattern.compile(pattern);
		Matcher matcher = r.matcher(System.getProperty("os.name"));

		if (matcher.matches()){
			prettyOutput.replace("\n", "\r\n");
		}

		System.out.println(prettyOutput);

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".json", false)); //create writer object
			writer.write(prettyOutput);

			//Close filewriter
			writer.close();
			System.out.println("Ding!");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * @author Stephen Kristin
	 * 
	 * The ParseMethod reads the data from the file and adds them to variables the program can read.
	 * 
	 */
	public double[][] parseMethod(String filename){
		JSONParser  parser = new JSONParser ();

		double X_ideal_array[];
		double Y_ideal_array[];
		double X_actual_array[];
		double Y_actual_array[];
		
		Object obj = null;

		try {

			obj = parser.parse(new FileReader(filename));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JSONObject ParseObject = (JSONObject) obj;

		// loop array for x_ideal
		JSONArray x_ideal = (JSONArray) ParseObject.get("x_ideal");
		
		//Instantiate output arrays
		X_ideal_array = new double[x_ideal.size()];
		Iterator<Double> iterator = x_ideal.iterator();
		while (iterator.hasNext()) {
			int i = 0;

			X_ideal_array[i] = iterator.next();

			i++;
		}
		// loop array for y_ideal
		JSONArray y_ideal = (JSONArray) ParseObject.get("y_ideal");
		Y_ideal_array = new double[y_ideal.size()];
		Iterator<Double> iterator1 = y_ideal.iterator();
		while (iterator1.hasNext()) {
			int i = 0;

			Y_ideal_array[i] = iterator1.next();

			i++;
		}
		// loop array for x_error
		JSONArray x_actual = (JSONArray) ParseObject.get("x_actual");
		X_actual_array = new double[x_actual.size()];
		Iterator<Double> iterator2 = x_actual.iterator();
		while (iterator2.hasNext()) {
			int i = 0;

			X_actual_array[i] = iterator2.next();

			i++;
		}
		// loop array for y_actual
		JSONArray y_actual = (JSONArray) ParseObject.get("y_actual");
		Y_actual_array = new double[y_actual.size()];
		Iterator<Double> iterator3 = y_actual.iterator();
		while (iterator3.hasNext()) {
			int i = 0;

			Y_actual_array[i] = iterator3.next();

			i++;

		}

		double[][] parsedArray = new double[][] {X_ideal_array, Y_ideal_array, X_actual_array, Y_actual_array};

		return parsedArray;
	}

	/**
	 * Truncates a 2-d array of doubles to 3-decimal place precision for easy-to-read output.
	 * @param positions
	 * @return
	 */
	public double[][] truncate2DArray(double[][] array){
		//Loop through 1st level of array
		for (int camel = 0; camel < array.length; camel++){

			//Loop through second level of array
			for (int giraffe = 0; giraffe < array[0].length; giraffe++){

				//Truncate value to three decimal places
				array[camel][giraffe] = Math.floor(array[camel][giraffe] * 1000) / 1000;
			}
		}

		return array;
	}

}
