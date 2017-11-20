import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDateTime.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

public class ArrivalTest {

    @Test
    public void whenArrival10MinutesInFuture_MinutesUntilArrivalIs10(){
        LocalDateTime tenMinInFuture = now().plus(10, ChronoUnit.MINUTES);
        Arrival arrival = new Arrival("whale", tenMinInFuture, "starfish");

        assertThat(arrival.minutesUntilArrival(), greaterThanOrEqualTo(9L));
    }
}
