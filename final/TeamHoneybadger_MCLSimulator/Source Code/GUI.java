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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	private Menu menuFile, menuHelp;			// Menus
	private MenuItem miSave, miClose;					// save/close
	private MenuItem miAbout,miInstructions;	// Displays info about the program;
	private Button load, start;
	public static TextField startPoint, endPoint, rangeText, refPoints, moveError, senseError;

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
		// Create Menus and MenuBar
		menuFile = new Menu("File");
		menuHelp = new Menu("Help");
		menuBar = new MenuBar();
		// Add menu items to respective menus and to menuBaar
		menuFile.getItems().addAll(miSave, miClose);
		menuHelp.getItems().addAll(miAbout, miInstructions);
		menuBar.getMenus().addAll(menuFile, menuHelp);
		//Defining text fields
		rangeText = new TextField();
		Label label1 = new Label ("Sensor Range:");
		rangeText.setPromptText("Enter a double");
		rangeText.setPrefColumnCount(20);
		rangeText.getText();
		GridPane.setConstraints(rangeText, 2, 0);
		GridPane.setConstraints(label1, 1, 0);
		refPoints = new TextField();
		Label label2 = new Label ("Reference Points:");
		refPoints.setPromptText("Enter a number of reference points");
		GridPane.setConstraints(refPoints, 2, 1);
		GridPane.setConstraints(label2, 1, 1);
		senseError = new TextField();
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
		moveError = new TextField();
		Label label5 = new Label ("Movment Error SD:");
		moveError.setPrefColumnCount(25);
		moveError.setPromptText("Enter a double");
		GridPane.setConstraints(moveError, 2, 3);
		GridPane.setConstraints(label5, 1, 3);
		//Defining text field
		startPoint = new TextField();
		Label label6 = new Label ("Start Point:");
		startPoint.setPrefColumnCount(25);
		startPoint.setPromptText("Enter in the form of x,y");
		GridPane.setConstraints(startPoint, 2, 4);
		GridPane.setConstraints(label6, 1, 4);
		//Defining text field
		endPoint = new TextField();
		Label label7 = new Label ("End Point:");
		endPoint.setPrefColumnCount(25);
		endPoint.setPromptText("Enter in the form of x,y");
		GridPane.setConstraints(endPoint, 2, 5);
		GridPane.setConstraints(label7, 1, 5);
		//Defining the start sim button
		start = new Button("Start Simulation");
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

			/**
			 *  @author Julian Pryde
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
		start.setOnAction(new SimulateHandler());
		load.setOnAction(e -> showFiles());
		//load.setOnAction(e -> loadFile());
		/* PUT EVERYTHING TOGETHER */
		Scene scene = new Scene(MCLPane, 650, 260);
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
		try {
			ChoiceDialog<String> fileChoice = new ChoiceDialog<>(dispFiles.get(0), dispFiles);
		
			fileChoice.setTitle("Saved Files");
			fileChoice.setHeaderText("Choose a saved File:");
	
			IO io = new IO();
			
			Optional<String> choice = fileChoice.showAndWait();
			choice.ifPresent(fileChosen -> {
				double[][] fromFile = io.parseMethod(fileChosen);
				
				double[][] positions = new double[fromFile[0].length][2];
				double[][] positionsWError = new double[fromFile[0].length][2];
				double[][] positionsEstimate = new double[fromFile[0].length][2];
				
				//put data from file in coordinate format for chart
				for (int bassoon = 0; bassoon < fromFile.length; bassoon++){
					positions[bassoon][0] = fromFile[0][bassoon]; //fromFile is in format {x_ideal, y_ideal, x_actual, y_actual}
					positions[bassoon][1] = fromFile[1][bassoon]; //positions must be in format {{x,y}, {x,y}, ...}
					
					positionsWError[bassoon][0] = fromFile[2][bassoon];
					positionsWError[bassoon][1] = fromFile[3][bassoon];
					
					positionsEstimate[bassoon][0] = fromFile[4][bassoon];
					positionsEstimate[bassoon][1] = fromFile[5][bassoon];
					
				}
				
				io.showChart(positions, positionsWError, positionsEstimate);
			});
		} catch (Exception e){
			Alert noneFound = new Alert(AlertType.ERROR);
			noneFound.setContentText("No .json files found in the current directory");
			noneFound.setTitle("No Files Found");
			noneFound.setHeaderText("404");
			
			noneFound.showAndWait();
		}
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
				+ "Show Data: Shows graphs of data from simulation.\n"
				+ "See Data: Shows the data.\n\n"
				+ "Text Fields:\n"
				+ "Range: User input to determine sensor range as Double value.\n"
				+ "Reference Points: User input to determine number of reference points as Integer value.\n"
				+ "Sensor Error: User input to determine percentage of sensor error as Double value.\n"
				+ "Waypoints: User input to determine number of waypoints as Double value.\n"
				+ "Movement Error: User input to determine standard deviation of the movement error as Double value.\n"
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

