import org.junit.After;
import org.junit.Before;
import org.testng.annotations.Test;
import java.util.Arrays;
import org.junit.*;

/**
 * Created by julian on 3/22/16.
 * Author: Julian Pryde
 */
public class RobotTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void move() throws Exception {
        Robot robot = new Robot(new int[]{0, 0}, new int[]{10, 10});

        double[][][] runData;

        runData = robot.move();

        System.out.println(Arrays.toString(runData));
    }

    @Test
    public void findTotalDist() throws Exception {

    }
}