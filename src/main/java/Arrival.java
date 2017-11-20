import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Arrival {

    private static final String PRINT_FORMAT = "%s  --  %s  --  %s";

    private final String lineId;
    private final LocalDateTime arrivalTime;
    private final String towardsDestination;
    private final String arrivalId;

    public Arrival(String lineId, LocalDateTime arrivalTime, String towardsDestination, String arrivalId){
        this.lineId = lineId;
        this.arrivalTime = arrivalTime;
        this.towardsDestination = towardsDestination;
        this.arrivalId = arrivalId;
    }

    public String id(){
        return arrivalId;
    }

    /**
     * Return the number of minutes until the train arrives
     */
    public long minutesUntilArrival(){
        return ChronoUnit.MINUTES.between(LocalDateTime.now(ZoneId.of("UTC+00:00")), arrivalTime);
    }

    public long secondsUntilArrival(){
        return ChronoUnit.SECONDS.between(LocalDateTime.now(ZoneId.of("UTC+00:00")), arrivalTime);
    }

    @Override
    public String toString() {
        return String.format(PRINT_FORMAT, lineId, towardsDestination, minutesUntilArrival());
    }
}
