import java.time.Duration;
import java.time.LocalDateTime;

public class Arrival {

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
    public Duration timeUntilArrival(){
        return null;
    }

    @Override
    public String toString() {
        return "Arrival{" +
                "lineId='" + lineId + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", towardsDestination='" + towardsDestination + '\'' +
                '}';
    }
}
