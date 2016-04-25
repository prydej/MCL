import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
//import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import javax.swing.JOptionPane;

import java.io.File;
import java.lang.Double;

/** The GUI
 * @author Savanh
 * @author Stephen
 */
public class GUI extends Application{
	private BorderPane MCLPane; 						//border pane
	GridPane grid = new GridPane();						//gridpane
	private MenuBar menuBar;							// MenuBar
	private Menu menuFile, menuHelp, menuChart;			// Menus
	private MenuItem miSave, miClose;					// save/close
	private MenuItem miAbout,miInstructions, miShow;	// Displays info about the program
	private String moveString;
	private Button load;

	/** @author Savanh Lu
	 * this is a constructor*/
	public GUI(){
		MCLPane = new BorderPane();							//make pane
		//create menu items
		miSave = new MenuItem("Save");
		miClose = new MenuItem("Close");
		miAbout = new MenuItem("About");
		miInstructions = new MenuItem("Instructions");
		//miChart = new CheckMenuItem("See Data");
		miShow = new MenuItem("Show Data");
		// Create Menus and MenuBar
		menuFile = new Menu("File");
		menuHelp = new Menu("Help");
		menuBar = new MenuBar();	
		menuChart = new Menu("Data Chart");
		// Add menu items to respective menus and to menuBaar
		menuFile.getItems().addAll(miSave, miClose);
		menuHelp.getItems().addAll(miAbout, miInstructions);
		menuChart.getItems().addAll(miShow);
		menuBar.getMenus().addAll(menuFile, menuHelp, menuChart);
		//Defining text fields
		final TextField rangeText = new TextField();
		Label label1 = new Label ("Range:");
		rangeText.setPromptText("Enter a double");
		rangeText.setPrefColumnCount(20);
		rangeText.getText();
		GridPane.setConstraints(rangeText, 2, 0);
		GridPane.setConstraints(label1, 1, 0);
		final TextField refPoints = new TextField();
		Label label2 = new Label ("Reference Points:");
		refPoints.setPromptText("Enter a number of reference points");
		GridPane.setConstraints(refPoints, 2, 1);
		GridPane.setConstraints(label2, 1, 1);
		final TextField senseError = new TextField();
		Label label3 = new Label ("Sensor Error %:");
		senseError.setPrefColumnCount(25);
		senseError.setPromptText("Enter a percentage");
		GridPane.setConstraints(senseError, 2, 2);
		GridPane.setConstraints(label3, 1, 2);
		//Defining text field
		//final TextField waypoints = new TextField();
		//		Label label4 = new Label ("Waypoints:");
		//		waypoints.setPrefColumnCount(25);
		//		waypoints.setPromptText("Enter a double");
		//		GridPane.setConstraints(waypoints, 2, 3);
		//		GridPane.setConstraints(label4, 1, 3);
		//Defining text field
		final TextField moveError = new TextField();
		Label label5 = new Label ("Movment Error SD:");
		moveError.setPrefColumnCount(25);
		moveError.setPromptText("Enter a percentage");
		GridPane.setConstraints(moveError, 2, 3);
		GridPane.setConstraints(label5, 1, 3);
		//Defining text field
		final TextField startPoint = new TextField();
		Label label6 = new Label ("Start Point:");
		startPoint.setPrefColumnCount(25);
		startPoint.setPromptText("Enter in the form of x,y");
		GridPane.setConstraints(startPoint, 2, 4);
		GridPane.setConstraints(label6, 1, 4);
		//Defining text field
		final TextField endPoint = new TextField();
		Label label7 = new Label ("End Point:");
		endPoint.setPrefColumnCount(25);
		endPoint.setPromptText("Enter in the form of x,y");
		GridPane.setConstraints(endPoint, 2, 5);
		GridPane.setConstraints(label7, 1, 5);
		//Defining the start sim button
		Button start = new Button("Start Simulation");
		GridPane.setConstraints(start, 3, 0);
		//Defining the Clear button
		Button clear = new Button("Clear");
		GridPane.setConstraints(clear, 3, 1);
		//Define Load file button
		load = new Button("Load File");
		GridPane.setConstraints(load, 3, 2);

		grid.getChildren().addAll(rangeText, refPoints, senseError,  
				moveError, startPoint, endPoint, start, clear, load, label1, label2, label3, label5, label6, label7);

		// Clear button event handler
		clear.setOnAction(new EventHandler<ActionEvent>() {
			/* @author Savanh Lu
			 * (non-Javadoc)
			 * Setting an action for the Clear button
			 * @see javafx.event.EventHandler#handle(javafx.event.Event) */
			@Override
			public void handle(ActionEvent e) {
				rangeText.clear();
				refPoints.clear();
				senseError.clear();
				moveError.clear();
				startPoint.clear();
				endPoint.clear();
			}
		});

		start.setOnAction(new EventHandler<ActionEvent>() {

			/* @author Julian Pryde
			 * Set action for start simulation button
			 * (non-Javadoc)
			 * @see javafx.event.EventHandler#handle(javafx.event.Event)
			 */
			@Override
			public void handle(ActionEvent e){

				//get text, split by comma, convert each element part to int
				int startx = Integer.parseInt(startPoint.getText().split(",")[0]);
				if( startx < 0 || startx > 100 )/*set parameters for users by Savanh*/{
					System.out.print("Error! Enter a number between 0-100\n");
				}
				int starty = Integer.parseInt(startPoint.getText().split(",")[1]);
				if( starty < 0 || starty > 100 )/*set parameters for users by Savanh*/{
					System.out.print("Error! Enter a number between 0-100\n");
				}
				int endx = Integer.parseInt(endPoint.getText().split(",")[0]);
				if( endx < 0 || endx > 100 )/*set parameters for users by Savanh*/{
					System.out.print("Error! Enter a number between 0-100\n");
				}
				int endy = Integer.parseInt(endPoint.getText().split(",")[1]);
				if( endy < 0 || endy > 100 )/*set parameters for users by Savanh*/{
					System.out.print("Error! Enter a number between 0-100\n");
				}
				int[] startPos = {startx, starty};
				int[] endPos = {endx, endy};


				/*created new variables by Savanh and added user parameters*/
				int refPoint = Integer.parseInt(refPoints.getText());
				double range = Double.parseDouble(rangeText.getText()); 
				double sense = Double.parseDouble(senseError.getText()); 
				double move = Double.parseDouble(moveError.getText());
				try{
					if (refPoint < 0 || refPoint > 100){
						System.out.print("Error! Enter a number between 0-100\n");
					}
					if (range < 0 || range > 100){
						System.out.print("Error! Enter a number between 0-100\n");
					}
					if (sense < 0 || sense > 100){
						System.out.print("Error! Enter a number between 0-100\n");
					}
					if (move < 0 || move > 100){
						System.out.print("Error! Enter a number between 0-100\n");
					}
				}catch(Exception ex){
					ex.getStackTrace();
					return;
				}
				Main.simulate(
						refPoint,
						startPos,
						endPos,
						range,
						sense,
						move
						);
			}
		});
	}
	/**@author Savanh Lu*/
	/* (non-Javadoc)
	 * @see 
	 * javafx.application.Application#
	 * start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		miInstructions.setOnAction(e -> showInstructions());
		miAbout.setOnAction(e -> showAbout());			//Event Handlers
		miClose.setOnAction(e -> Platform.exit());
		//miShow.setOnAction(new ShowHandler());
		load.setOnAction(e -> showFiles());
		//load.setOnAction(e -> loadFile());
		/* PUT EVERYTHING TOGETHER */
		Scene scene = new Scene(MCLPane, 850, 850);
		// Add the menu bar and shapes to the border pane
		MCLPane.setTop(menuBar);
		MCLPane.setCenter(grid);
		//MCLPane.setBottom(lineChart);
		// Configure and display the stage
		stage.setScene(scene);
		stage.setTitle("Monte Carlo Localization Simulator");
		//won't allow user to resize grid
		stage.setResizable(false);
		stage.show();
		//GridPane things
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(5);
		grid.setHgap(5);
	}

	/**
	 * @author Julian
	 */
	private void showFiles(){

		//Get files from current directory ending in .json
		File folder = new File(".");
		File[] fileArray = folder.listFiles();

		//Convert fileList to arrayList of filenames
		ArrayList<File> fileList = new ArrayList<File>(Arrays.asList(fileArray));

		//Create string array of all filenames that end in .json
		ArrayList<String> dispFiles = new ArrayList<String>(); //Create array
		for (File file : fileList){
			if (file.getName().contains(".json")){
				dispFiles.add(file.getName());
			}
		}

		// Show dropdown box to choose file
		ChoiceDialog<String> fileChoice = new ChoiceDialog<>(dispFiles.get(1), dispFiles);
		fileChoice.setTitle("Saved Files");
		fileChoice.setHeaderText("Choose a saved File:");

		Optional<String> choice = fileChoice.showAndWait();
		choice.ifPresent(fileChosen -> System.out.println("Choice: " + fileChosen));
	}

	/** Shows information about the program in it's own window 
	 * @author Savanh Lu */
	private void showAbout(){
		//customize text
		final String aboutText ="This program was designed by Miralda Rodney,"
				+ " Jadeira Lu, Julian Pryde, and Stephen Kristin in collaboration with"
				+ " Dr.Garfield.";
		// Create the text label
		Label aboutLabel = new Label();
		aboutLabel.setWrapText(true);
		aboutLabel.setTextAlignment(TextAlignment.CENTER);
		aboutLabel.setFont(Font.font("Times New Roman", 22));
		aboutLabel.setText(aboutText);
		StackPane pane = new StackPane();	// Add the label to a StackPane
		pane.getChildren().add(aboutLabel);
		// Create and display said the aforementioned pane in a new stage 	
		Scene scene = new Scene(pane, 550, 200);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("About this program");
		stage.setResizable(false);
		stage.show();
	}

	/**@author Savanh
	 * shows chart */
	/*private class ShowHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent e) {

			stage.show();

			/*StackPane pane = new StackPane();	// Add the label to a StackPane
			// Create and display the aforementioned pane in a new stage 	
			Scene scene = new Scene(pane, 600, 600);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Data Chart");
			stage.setResizable(false);
			stage.show();
			//chart things
			CategoryAxis xAxis= new CategoryAxis();
			NumberAxis yAxis = new NumberAxis();
			lineChart = new LineChart(xAxis, yAxis);

			/*if (robot instanceof Robot){
				lineChart.setData(getChartData());
			}

			lineChart.setTitle("Data from Simulation");
			StackPane root= new StackPane();
			pane.getChildren().add(lineChart);//add line chart
		} 
	}*/

	/** Shows instructions on each item in the GUI
	 * @author Stephen Kristin
	 */
	private void showInstructions(){
		//customize text
		final String infoText ="File menu:\n" 
				+ "Save: Saves the current run to txt file.\n"
				+ "Close: Closes the program.\n\n"
				+ "Help menu:\n"
				+ "About: Shows info on authors.\n"
				+ "Information: Shows this window.\n\n"
				+ "Data Chart menu:\n"
				+ "Show Data: Shows graphs of data from simulation.\n"
				+ "See Data: Shows the data.\n\n"
				+ "Text Fields:\n"
				+ "Range: User input to determine sensor range as Double value.\n"
				+ "Reference Points: User input to determine number of reference points as Integer value.\n"
				+ "Sensor Error: User input to determine percentage of sensor error as Double value.\n"
				+ "Waypoints: User input to determine number of waypoints as Double value.\n"
				+ "Movement Error: User input to determine percentage of movement error as Double value.\n"
				+ "Start Point: User input to determine robot starting point, input as (x,y) as Integers.\n"
				+ "End Point: User input to determine robot ending point, input as (x,y) as Integers.\n\n"
				+ "Start Simualtion button: Uses user input data to run the simulation.\n"
				+ "Clear button: Clears all input info from the text boxes.";
		// Create the text label
		Label infoLabel = new Label();
		infoLabel.setWrapText(true);
		infoLabel.setTextAlignment(TextAlignment.LEFT);
		infoLabel.setFont(Font.font("Times New Roman", 22));
		infoLabel.setText(infoText);
		StackPane pane = new StackPane();	// Add the label to a StackPane
		pane.getChildren().add(infoLabel);
		// Create and display said the aforementioned pane in a new stage 	
		Scene scene = new Scene(pane, 800, 800);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Instructions on program features.");
		stage.setResizable(false);
		stage.show();
	}
}

