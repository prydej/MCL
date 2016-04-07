/*Author: Jadeira Savanh Lu */
//Date: 14 March 2016
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
	//chart
public class chart extends Application{

		public  void  showChart(){
			Application.launch();
		}
		
		@Override
		public void start(Stage primaryStage){
			//create axis'
			CategoryAxis xAxis= new CategoryAxis();
			NumberAxis yAxis = new NumberAxis();
			LineChart<String, Double> lineChart = new LineChart(xAxis, yAxis);
			lineChart.setData(getChart());
			lineChart.setTitle("Graph of Data");
			primaryStage.setTitle("Data Chart");
			StackPane root= new StackPane();
			root.getChildren().add(lineChart);
			primaryStage.setScene(new Scene(root, 600, 500));
			primaryStage.show();
		}
		//creates the chart
		private ObservableList<XYChart.Series<String, Double>> getChart(){
			double bValue = 19.26;
			double gValue= 13.93;
			ObservableList<XYChart.Series<String, Double>>answer = FXCollections.observableArrayList();
			Series<String, Double> blue = new Series<>();
			Series<String, Double> green = new Series<>();
			blue.setName("blue");
			green.setName("green");
			
			for(int i = 2011; i < 2016; i++){
				blue.getData().add(new XYChart.Data(Integer.toString(i), bValue));
				bValue = bValue + 6 * Math.random() -.2;
				green.getData().add(new XYChart.Data(Integer.toString(i), gValue));
				gValue = gValue + 4 * Math.random() - 2;
			}
			answer.addAll(blue, green);
			return answer;
		}
}
