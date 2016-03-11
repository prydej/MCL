
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.lang.Integer;
import java.lang.Double;

/** The GUI
 * @author Savanh
 */
public class GUI extends Application{

	private BorderPane MCLPane; 						//border pane
	GridPane grid = new GridPane();						//gridpane
	private MenuBar menuBar;							// MenuBar
	private Menu menuFile, menuHelp;					// Menus
	private MenuItem miSave, miClose;					// save/close
	private MenuItem miAbout;							// Displays info about the program
	private String moveString;

	/** @author Savanh Lu
	 * this is a constructor
	 */
	public GUI(){
		MCLPane = new BorderPane();							//make pane
		//Pane buttonPane = new Pane();
		//create menu items
		miSave = new MenuItem("Save");
		miClose = new MenuItem("Close");
		miAbout = new MenuItem("About");

		// Create Menus and MenuBar
		menuFile = new Menu("File");
		menuHelp = new Menu("Help");
		menuBar = new MenuBar();	

		// Add menu items to respective menus and to menuBaar
		menuFile.getItems().addAll(miSave, miClose);
		menuHelp.getItems().addAll(miAbout);
		menuBar.getMenus().addAll(menuFile, menuHelp);

		//Defining text fields
		final TextField rangeText = new TextField();
		rangeText.setPromptText("Enter a range");
		rangeText.setPrefColumnCount(20);
		rangeText.getText();
		GridPane.setConstraints(rangeText, 2, 0);

		final TextField refPoints = new TextField();
		refPoints.setPromptText("Enter a number of reference points");
		GridPane.setConstraints(refPoints, 2, 1);

		final TextField senseError = new TextField();
		senseError.setPrefColumnCount(25);
		senseError.setPromptText("Enter sensor error percentage");
		GridPane.setConstraints(senseError, 2, 2);

		final TextField waypoints = new TextField();
		waypoints.setPrefColumnCount(25);
		waypoints.setPromptText("Enter amount of waypoints");
		GridPane.setConstraints(waypoints, 2, 3);

		final TextField moveError = new TextField();
		moveError.setPrefColumnCount(25);
		moveError.setPromptText("Enter movement error percentage");
		GridPane.setConstraints(moveError, 2, 4);

		final TextField startPoint = new TextField();
		startPoint.setPrefColumnCount(25);
		startPoint.setPromptText("Enter starting location in the form of (x,y)");
		GridPane.setConstraints(startPoint, 2, 5);

		final TextField endPoint = new TextField();
		endPoint.setPrefColumnCount(25);
		endPoint.setPromptText("Enter end location in the form of (x,y)");
		GridPane.setConstraints(endPoint, 2, 6);

		//Defining the start simulation button
		Button start = new Button("Start Simulation");
		start.setStyle("-fx-base: #6b6a6b;"); //change button color
		GridPane.setConstraints(start, 3, 0);

		//Defining the Clear button
		Button clear = new Button("Clear");
		clear.setStyle("-fx-base:#ebebeb;"); //change button color
		GridPane.setConstraints(clear, 3, 1);
		//add everything to grid
		grid.getChildren().addAll(clear, start, endPoint, startPoint, moveError, rangeText, refPoints, senseError, waypoints);

		clear.setOnAction(new EventHandler<ActionEvent>() {
			/* @author Savanh Lu
			 * (non-Javadoc)
			 * Setting an action for the Clear button
			 * @see javafx.event.EventHandler#handle(javafx.event.Event)
			 */
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
			 * @see javafx.event.EventHandler#handle(javafx.event.Event)
			 */
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
						Integer.parseInt(refPoints.getText()), 
						startPos, 
						endPos, 
						Double.parseDouble(rangeText.getText()), 
						Double.parseDouble(senseError.getText()),
						Double.parseDouble(moveError.getText()));
			}
		});
	}

	//invoke GUI
	/** @author Savanh Lu
	 */
	public void showGUI(){
		
		int a = 1;
		
		Application.launch();
	}

	//set moveString
	/**@author Julian Pryde*/
	public void setMoveString(String moveString){
		this.moveString = moveString;
	}
	/**@author Savanh Lu*/
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		//miAbout.setOnAction(e -> showAbout());			//Event Handlers
		//miClose.setOnAction(e -> Platform.exit());

		/* PUT EVERYTHING TOGETHER */
		Scene scene = new Scene(MCLPane, 950, 850);
		//StackPane root= new StackPane();

		// Add the menu bar and shapes to the border pane
		MCLPane.setTop(menuBar);
		MCLPane.setCenter(grid);

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
	/** Shows information about the program in it's own window 
	 * @author Savanh Lu
	 */
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

		// Add the label to a StackPane
		StackPane pane = new StackPane();
		pane.getChildren().add(aboutLabel);

		// Create and display said the aforementioned pane in a new stage 	
		Scene scene = new Scene(pane, 550, 200);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("About this program");
		stage.setResizable(false);
		stage.show();
	}

}