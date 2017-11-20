import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;

import static junit.framework.TestCase.fail;

public class StationCommunicatorTest {

    @Test
    public void whenSendArrivalCalled_ArrivalWrittenToOutputFile(){

        Arrival dummy = new Arrival("line1", LocalDateTime.now(), "l", "1" );

        StationCommunicator.sendArrival(dummy);

        // assert system out contains correct output
    }

    @Test
    public void whenSendFollowingArrivalsCalled_ArrivalsWrittenToOutputFile(){
        //implement
        fail();
    }

    @Test
    public void whenSentFollowingArrivalsCalledMultipleTimes_ArrivalsWrittenToOutputFile(){
        //implement
        fail();
    }
}
