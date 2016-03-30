
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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
import javafx.scene.control.CheckMenuItem;
import java.lang.Double;

/** The GUI
 * @author Savanh
 */
public class GUI extends Application{
	private BorderPane MCLPane; 						//border pane
	GridPane grid = new GridPane();						//gridpane
	private MenuBar menuBar;							// MenuBar
	private Menu menuFile, menuHelp, menuChart;					// Menus
	private MenuItem miSave, miClose;					// save/close
	private MenuItem miAbout, miShow;							// Displays info about the program
	private String moveString;
	private CheckMenuItem miChart;	// CheckMenuItems for each chart
	private LineChart<String, Double> lineChart;

	/** @author Savanh Lu
	 * this is a constructor*/
	public GUI(){
		MCLPane = new BorderPane();							//make pane
		//create menu items
		miSave = new MenuItem("Save");
		miClose = new MenuItem("Close");
		miAbout = new MenuItem("About");
		miChart = new CheckMenuItem("See Data");
		miShow = new MenuItem("Show Data");
		// Create Menus and MenuBar
		menuFile = new Menu("File");
		menuHelp = new Menu("Help");
		menuBar = new MenuBar();	
		menuChart = new Menu("Data Chart");
		// Add menu items to respective menus and to menuBaar
		menuFile.getItems().addAll(miSave, miClose);
		menuHelp.getItems().addAll(miAbout);
		menuChart.getItems().addAll(miShow, miChart, new SeparatorMenuItem());
		menuBar.getMenus().addAll(menuFile, menuHelp, menuChart);
		//Defining the text field
		final TextField rangeText = new TextField();
		Label label1 = new Label ("Range:");
		rangeText.setPromptText("Enter a double");
		rangeText.setPrefColumnCount(20);
		rangeText.getText();
		GridPane.setConstraints(rangeText, 2, 0);
		GridPane.setConstraints(label1, 1, 0);
		
		//Defining  text field
		final TextField refPoints = new TextField();
		Label label2 = new Label ("Reference Points:");
		refPoints.setPromptText("Enter a number of reference points");
		GridPane.setConstraints(refPoints, 2, 1);
		GridPane.setConstraints(label2, 1, 1);
		
		//Defining text field
		final TextField senseError = new TextField();
		Label label3 = new Label ("Sensor Error %:");
		senseError.setPrefColumnCount(25);
		senseError.setPromptText("Enter a percentage");
		GridPane.setConstraints(senseError, 2, 2);
		GridPane.setConstraints(label3, 1, 2);
		
		//Defining text field
		final TextField waypoints = new TextField();
		Label label4 = new Label ("Waypoints:");
		waypoints.setPrefColumnCount(25);
		waypoints.setPromptText("Enter a double");
		GridPane.setConstraints(waypoints, 2, 3);
		GridPane.setConstraints(label4, 1, 3);
		
		//Defining text field
		final TextField moveError = new TextField();
		Label label5 = new Label ("Movment Error %:");
		moveError.setPrefColumnCount(25);
		moveError.setPromptText("Enter a percentage");
		GridPane.setConstraints(moveError, 2, 4);
		GridPane.setConstraints(label5, 1, 4);
		
		//Defining text field
		final TextField startPoint = new TextField();
		Label label6 = new Label ("Start Point:");
		startPoint.setPrefColumnCount(25);
		startPoint.setPromptText("Enter in the form of (x,y)");
		GridPane.setConstraints(startPoint, 2, 5);
		GridPane.setConstraints(label6, 1, 5);
		
		//Defining text field
		final TextField endPoint = new TextField();
		Label label7 = new Label ("End Point:");
		endPoint.setPrefColumnCount(25);
		endPoint.setPromptText("Enter in the form of (x,y)");
		GridPane.setConstraints(endPoint, 2, 6);
		GridPane.setConstraints(label7, 1, 6);
		//Defining the start sim button
		Button start = new Button("Start Simulation");
		GridPane.setConstraints(start, 3, 0);
		
		//Defining the Clear button
		Button clear = new Button("Clear");
		GridPane.setConstraints(clear, 3, 1);
		
		grid.getChildren().addAll(rangeText, refPoints, senseError, waypoints, 
				moveError, startPoint, endPoint, start, clear, label1, label2, label3,label4, label5, label6, label7);
		
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
				waypoints.clear();
				moveError.clear();
				startPoint.clear();
				endPoint.clear();
			}
		});
		
		start.setOnAction(new EventHandler<ActionEvent>() {
			/* @author Julian Pryde
			 * Set action for start simulation button
			 * (non-Javadoc)
			 * @see javafx.event.EventHandler#handle(javafx.event.Event)*/
			
			@Override
			public void handle(ActionEvent e){
				//get text, take out parens, split by comma, convert each element part to int
				int startx = Integer.parseInt(startPoint.getText().replaceAll("[()]","").split(",")[0]);
				int starty = Integer.parseInt(startPoint.getText().replaceAll("[()]","").split(",")[1]);
				int endx = Integer.parseInt(endPoint.getText().replaceAll("[()]","").split(",")[0]);
				int endy = Integer.parseInt(endPoint.getText().replaceAll("[()]","").split(",")[1]);

				int[] startPos = {startx, starty};
				int[] endPos = {endx, endy};

				Main.simulate(
						startPos, 
						endPos, 
						Double.parseDouble(rangeText.getText()), 
						Double.parseDouble(senseError.getText()),
						Double.parseDouble(moveError.getText())
				);
			}
		});
	}
	/** @author Savanh Lu
	 * Invoke GUI */
	public void showGUI(){

		Application.launch();
	}
	//set moveString
	/**@author Julian Pryde*/
	public void setMoveString(String moveString){
		this.moveString = moveString;
	}
	/**@author Savanh Lu*/
	
	/* (non-Javadoc)
	 * @see 
	 * javafx.application.Application#
	 * start(javafx.stage.Stage)
	 */
	
	@Override
	public void start(Stage stage) throws Exception {
		miAbout.setOnAction(e -> showAbout());			//Event Handlers
		miClose.setOnAction(e -> Platform.exit());
		miChart.setOnAction(new ChartDisplayHandler());
		miShow.setOnAction(new ShowHandler());
		/* PUT EVERYTHING TOGETHER */
		Scene scene = new Scene(MCLPane, 950, 850);
		// Add the menu bar and shapes to the border pane
		MCLPane.setTop(menuBar);
		MCLPane.setCenter(grid);
		MCLPane.setBottom(lineChart);
		//chart things
		CategoryAxis xAxis= new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		lineChart = new LineChart(xAxis, yAxis);
		lineChart.setTitle("Data from Simulation");
		StackPane root= new StackPane();
		root.getChildren().add(lineChart);//add line chart
		miChart.setSelected(true);
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
		//creates the chart
				private ObservableList<XYChart.Series<String, Double>> getChart(boolean miChart){
					//declare variables
					ObservableList<XYChart.Series<String, Double>>answer = FXCollections.observableArrayList();
					if (miChart){
						answer.addAll(new chart().getChartData());}
					return answer;
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
	//tell the buttons what to do using event handler
		class ChartDisplayHandler implements EventHandler<ActionEvent>{
			@Override
			public void handle(ActionEvent arg0) {
			
			}
		}
	
	//shows chart
		private class ShowHandler implements EventHandler<ActionEvent>{
			@Override
			public void handle(ActionEvent e) {
				miChart.setSelected(true);
			}
		}
}