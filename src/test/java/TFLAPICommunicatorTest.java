import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TFLAPICommunicatorTest {

    private String stopPoint = "940GZZLUWLO";
    private String lineId = "bakerloo";

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void whenGettingArrivalsFromAPI_ReturnsCorrectNumberOfArrivals(){

        List<Arrival> arrivals = TFLAPICommunicator.getArrivals(stopPoint, lineId);

        assertThat(arrivals, is(not(empty())));
    }

    @Test
    public void whenGettingArrivalsWithInvalidLineId_InvalidURIExceptionThrown(){
        exception.expect(RuntimeException.class);
        exception.expectMessage(containsString("Invalid URI"));

        TFLAPICommunicator.getArrivals(stopPoint, "^#&*^!%#");
    }

}
