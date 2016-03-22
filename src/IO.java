

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

}