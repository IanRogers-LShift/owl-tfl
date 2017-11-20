import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Arrival implements Comparable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arrival arrival = (Arrival) o;

        return arrivalId != null ? arrivalId.equals(arrival.arrivalId) : arrival.arrivalId == null;
    }

    @Override
    public int hashCode() {
        return arrivalId != null ? arrivalId.hashCode() : 0;
    }

    @Override
    public int compareTo(Object o) {
        if(Objects.equals(arrivalId, ((Arrival) o).arrivalId)){
            return 0;
        }

        return this.arrivalTime.compareTo(((Arrival) o).arrivalTime);
    }
}
