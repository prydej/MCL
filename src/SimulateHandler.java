import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * 
 */

/**
 * @author Julian Pryde
 *
 */
public class SimulateHandler implements EventHandler<ActionEvent> {

	/**
	 *  @author Julian Pryde
	 * Set action for start simulation button
	 * (non-Javadoc)
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent e){

		//get text, split by comma, convert each element part to int
		int startx = Integer.parseInt(GUI.startPoint.getText().split(",")[0]);
		if( startx < 0 || startx > 100 )/*set parameters for users by Savanh*/{
			System.out.print("Error! Enter a number between 0-100\n");
		}
		int starty = Integer.parseInt(GUI.startPoint.getText().split(",")[1]);
		if( starty < 0 || starty > 100 )/*set parameters for users by Savanh*/{
			System.out.print("Error! Enter a number between 0-100\n");
		}
		int endx = Integer.parseInt(GUI.endPoint.getText().split(",")[0]);
		if( endx < 0 || endx > 100 )/*set parameters for users by Savanh*/{
			System.out.print("Error! Enter a number between 0-100\n");
		}
		int endy = Integer.parseInt(GUI.endPoint.getText().split(",")[1]);
		if( endy < 0 || endy > 100 )/*set parameters for users by Savanh*/{
			System.out.print("Error! Enter a number between 0-100\n");
		}
		int[] startPos = {startx, starty};
		int[] endPos = {endx, endy};


		/*created new variables by Savanh and added user parameters*/
		int refPoint = Integer.parseInt(GUI.refPoints.getText());
		double range = Double.parseDouble(GUI.rangeText.getText()); 
		double sense = Double.parseDouble(GUI.senseError.getText()); 
		double move = Double.parseDouble(GUI.moveError.getText());
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
}
