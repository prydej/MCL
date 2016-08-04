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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

//import java.util.ArrayList.*;
/**
 * @author Julian Pryde
 * the IO class contains all methods related to file input and output as well as those that chart data.
 */
public class IO {

	//private String strToSave;
	private LineChart<Number, Number> lineChart;

	public IO(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * @author Julian
	 * 
	 * @param positions: The array of all positions that the robot would ideally have visited
	 * @param positionsWError: The array of all positions the robot actually visited including error.
	 */
	@SuppressWarnings("unchecked")
	public void writeRunData(double[][] positions, double[][] positionsWError, double[][] positionsEstimate){

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

		//System.out.println(runPositions);

		//Write y positions with movement error to json object
		JSONArray y_ideal = new JSONArray(); //create json array of all y comps of pos w/ error
		for (int dog = 0; dog < positions.length; dog++){

			y_ideal.add(positions[dog][1]);
		}
		runPositions.put("y_ideal", y_ideal); //add y comps of pos w/error to object

		//System.out.println(runPositions);

		//Write positions without error to json array
		//write x positions WITHOUT movement error to json object
		JSONArray x_actual = new JSONArray(); //create json array of all x comps of pos w/o error
		for (int fish = 0; fish < positionsWError.length; fish++){

			x_actual.add(positionsWError[fish][0]);
		}
		runPositions.put("x_actual", x_actual); //add x comps of pos w/o error to object

		//System.out.println(runPositions);

		//write y positions WITHOUT movement error to json object
		JSONArray y_actual = new JSONArray(); //create json array of all y comps of pos w/o error
		for (int hamster = 0; hamster < positionsWError.length; hamster++){

			y_actual.add(positionsWError[hamster][1]);
		}
		runPositions.put("y_actual", y_actual); //add y comps of pos w/o error to object

		//write y positions WITHOUT movement error to json object
		JSONArray x_estimate = new JSONArray(); //create json array of all y comps of pos w/o error
		for (int vuvuzela = 0; vuvuzela < positionsWError.length; vuvuzela++){

			x_estimate.add(positionsEstimate[vuvuzela][0]);
		}
		runPositions.put("x_estimate", x_estimate); //add y comps of pos w/o error to object

		//write y positions WITHOUT movement error to json object
		JSONArray y_estimate = new JSONArray(); //create json array of all y comps of pos w/o error
		for (int trumpet = 0; trumpet < positionsWError.length; trumpet++){

			y_estimate.add(positionsEstimate[trumpet][0]);
		}
		runPositions.put("y_estimate", y_estimate); //add y comps of pos w/o error to object

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
			BufferedWriter writer = new BufferedWriter(new FileWriter("log/" + filename + ".json", false)); //create writer object
			writer.write(prettyOutput);

			//Close filewriter
			writer.close();
			System.out.println("Ding!");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author Stephen Kristin
	 * 
	 * The ParseMethod reads the data from the file and adds them to variables the program can read.
	 * 
	 * @param filename: The name of the file from which to pull data
	 */
	public double[][] parseMethod(String filename){
		JSONParser  parser = new JSONParser ();

		double X_ideal_array[];
		double Y_ideal_array[];
		double X_actual_array[];
		double Y_actual_array[];
		double X_estimate_array[];
		double Y_estimate_array[];

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
		int i = 0;
		while (iterator.hasNext()) {

			X_ideal_array[i] = iterator.next();

			i++;
		}
		// loop array for y_ideal
		JSONArray y_ideal = (JSONArray) ParseObject.get("y_ideal");
		Y_ideal_array = new double[y_ideal.size()];
		Iterator<Double> iterator1 = y_ideal.iterator();
		int j = 0;
		while (iterator1.hasNext()) {

			Y_ideal_array[j] = iterator1.next();

			j++;
		}
		// loop array for x_error
		JSONArray x_actual = (JSONArray) ParseObject.get("x_actual");
		X_actual_array = new double[x_actual.size()];
		Iterator<Double> iterator2 = x_actual.iterator();
		int k = 0;
		while (iterator2.hasNext()) {

			X_actual_array[k] = iterator2.next();

			k++;
		}

		// loop array for y_actual
		JSONArray y_actual = (JSONArray) ParseObject.get("y_actual");
		Y_actual_array = new double[y_actual.size()];
		Iterator<Double> iterator3 = y_actual.iterator();
		int m = 0;
		while (iterator3.hasNext()) {

			Y_actual_array[m] = iterator3.next();

			m++;

		}

		// loop array for y_actual
		JSONArray x_estimate = (JSONArray) ParseObject.get("x_estimate");
		X_estimate_array = new double[x_estimate.size()];
		Iterator<Double> iterator4 = x_estimate.iterator();
		int n = 0;
		while (iterator4.hasNext()) {

			X_estimate_array[n] = iterator4.next();

			n++;

		}

		// loop array for y_actual
		JSONArray y_estimate = (JSONArray) ParseObject.get("y_estimate");
		Y_estimate_array = new double[y_estimate.size()];
		Iterator<Double> iterator5 = y_estimate.iterator();
		int p = 0;
		while (iterator3.hasNext()) {

			Y_estimate_array[p] = iterator3.next();

			p++;

		}

		double[][] parsedArray = new double[][] {X_ideal_array, Y_ideal_array, X_actual_array, Y_actual_array,
			X_estimate_array, Y_estimate_array};

		return parsedArray;
	}

	/**
	 * Truncates a 2-d array of doubles to 3-decimal place precision for easy-to-read output.
	 * @param positions
	 * @return array: The truncated array
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

	// Create chart
	/**
	 * @author Julian and Savanh
	 * @param positions: positions that the Robot has visited ideally
	 * @param positionsWError: positions that the Robot has visited with error added
	 * 
	 * Charts the positions with and without error and the robot's estimated positions
	 */
	public void showChart(double[][] positions, double[][] positionsWError, double[][] positionsEstimate){
		StackPane pane = new StackPane();	// Add the label to a StackPane

		// Create and display the aforementioned pane in a new stage 	
		Scene scene = new Scene(pane, 600, 600);
		Stage chartStage = new Stage();
		chartStage.setScene(scene);
		chartStage.setTitle("Robot Positions");
		chartStage.setResizable(false);
		chartStage.show();

		//chart things
		NumberAxis xAxis= new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		lineChart = new LineChart(xAxis, yAxis);

		lineChart.setData(setChartData(positions, positionsWError, positionsEstimate));

		lineChart.setTitle("Data from Simulation");
		StackPane root= new StackPane();
		pane.getChildren().add(lineChart);//add line chart'

		//set 
	}

	/**
	 * @author Savanh and Julian
	 * @param positionsArray : array of x and y values for ideal positions of robot
	 * @param positionsErrorArray : array of x and y values for actual positions of robot
	 * Gets data from two 2-d arrays of doubles and formats it for display on a lineChart
	 */
	public ObservableList<XYChart.Series<Number, Number>> setChartData(double[][] positionsArray, double[][] positionsErrorArray,
			double[][] positionsEstimateArray){
		//declare variables
		double xValue, yValue, xValError, yValError, xValEstimate, yValEstimate;

		ObservableList<XYChart.Series<Number, Number>> dataToDisplay = FXCollections.observableArrayList();

		Series<Number, Number> positions = new Series<>();
		Series<Number, Number> positionsError = new Series<>();
		Series<Number, Number> positionsEstimate = new Series<>();

		positions.setName("Ideal Positions");
		positionsError.setName("Position With Error");
		positionsEstimate.setName("Estimated Positions");

		for(int i = 0; i < positionsArray.length; i++){
			xValue = positionsArray[i][0];
			yValue = positionsArray[i][1];
			positions.getData().add(new XYChart.Data(xValue, yValue));

			xValError = positionsErrorArray[i][0];
			yValError = positionsErrorArray[i][1];
			positionsError.getData().add(new XYChart.Data(xValError, yValError));

			xValEstimate = positionsEstimateArray[i][0];
			yValEstimate = positionsEstimateArray[i][1];
			positionsEstimate.getData().add(new XYChart.Data(xValEstimate, yValEstimate));
		}

		dataToDisplay.addAll(positions, positionsError, positionsEstimate);

		return dataToDisplay;
	}

}
