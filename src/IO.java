/**
 * @author julian
 * @version 1.0
 * @created 13-Feb-2016 1:56:50 PM
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;

/**
 * @author prydej
 *
 */
public class IO extends Application {

	//private String strToSave;

	public IO(){

	}

	public void finalize() throws Throwable {

	}

	
	//public String log(String filename, String file){

		//String outputString;
	//}
	
	public void start(Stage stage){
		
	}
	
	/**
	 * 
	 * @param outputString contains information from one run
	 */
	public static void setMoveString(String outputString){
		
		//Create stage, scene, and pane
		Pane pane = new Pane();
		Stage stage = new Stage();
		Scene scene = new Scene(pane);
		
		//Create text object
		Text runString = new Text();
		
		//Set Scene
		stage.setScene(scene);
		
		//add text to pane
		pane.getChildren().add(runString);
		
	}
	
	/*
	 * @author Stephen Kristin
	 * 
	 * The ParseMethod reads the data from the file and adds them to variables the program can read.
	 * 
	 */
	public int ParseMethod(){
		JSONParser  parser = new JSONParser ();

		try {

			Object obj = parser.parse(new FileReader("c:\\test.json"));

			JSONObject ParseObject = (JSONObject) obj;

			// loop array for x_error
			JSONArray x_error = (JSONArray) ParseObject.get("x_error");
			Iterator<String> iterator = x_error.iterator();
			while (iterator.hasNext()) {
				int i = 0;
				double X_error_array[] = null;
				
				X_error_array[i] = Double.parseDouble(iterator.next());
						
				i++;
			}
			// loop array for y_error
			JSONArray y_error = (JSONArray) ParseObject.get("y_error");
			Iterator<String> iterator1 = y_error.iterator();
			while (iterator1.hasNext()) {
				int i = 0;
				double Y_error_array[] = null;
							
				Y_error_array[i] = Double.parseDouble(iterator1.next());
									
				i++;
			}
			// loop array for x_actual
			JSONArray x_actual = (JSONArray) ParseObject.get("x_actual");
			Iterator<String> iterator2 = x_actual.iterator();
			while (iterator2.hasNext()) {
				int i = 0;
				double X_actual_array[] = null;
										
				X_actual_array[i] = Double.parseDouble(iterator2.next());
												
				i++;
			}
			// loop array for y_actual
			JSONArray y_actual = (JSONArray) ParseObject.get("y_actual");
			Iterator<String> iterator3 = y_actual.iterator();
			while (iterator3.hasNext()) {
				int i = 0;
				double Y_actual_array[] = null;
													
				Y_actual_array[i] = Double.parseDouble(iterator3.next());
															
				i++;
			}
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
