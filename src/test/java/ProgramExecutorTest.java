import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class ProgramExecutorTest {

    private final static String stopPoint = "940GZZLUWLO";
    private final static String lineId = "bakerloo";

    private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    private static ProgramExecutor programExecutor;

    @BeforeClass
    public static void setup(){
        programExecutor = new ProgramExecutor(stopPoint, lineId, scheduler);
    }


    @Test
    public void whenArrivalScheduled_ArrivalAddedToQueue(){
        Arrival arrival = new Arrival("line1", LocalDateTime.now().plus(1, ChronoUnit.MINUTES), "blah", "blah");

        programExecutor.schedule(arrival);

        assertThat(programExecutor.numberArrivals(), is(equalTo(1)));
    }
}
