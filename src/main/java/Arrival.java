import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Arrival {

    private static final String PRINT_FORMAT = "%s  --  %s  --  %s";

    private final String lineId;
    private final LocalDateTime arrivalTime;
    private final String towardsDestination;

    public Arrival(String lineId, LocalDateTime arrivalTime, String towardsDestination){
        this.lineId = lineId;
        this.arrivalTime = arrivalTime;
        this.towardsDestination = towardsDestination;
    }

    /**
     * Return the number of minutes until the train arrives
     */
    public long minutesUntilArrival(){
        return ChronoUnit.MINUTES.between(LocalDateTime.now(), arrivalTime);
    }

    @Override
    public String toString() {
        return String.format(PRINT_FORMAT, lineId, towardsDestination, minutesUntilArrival());
    }
}
