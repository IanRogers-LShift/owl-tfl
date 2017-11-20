import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Continuously running process to get incoming arrivals.
 *
 * Keep arrivals in some sort of map
 * Background process to run and update the trains
 * Scheduled threadpool for announcements - index by arrival ID to deal with updates
 * Scheduled task to print the three most recent trains every minute.
 *
 *
 * Maybe we want to validate stop point?
 */
public class ProgramExecutor implements Runnable {

    private final String stopPoint;
    private final String lineId;
    private Map<String, ScheduledFuture> arrivalMap;

    private ScheduledExecutorService announcements;

    public ProgramExecutor(String stopPoint, String lineId){
        this.stopPoint = stopPoint;
        this.lineId = lineId;

        // Only have one announcement at once
        this.announcements = Executors.newScheduledThreadPool(1);
        this.arrivalMap = new HashMap<>();
    }


    @Override
    public void run() {

        while(!Thread.currentThread().isInterrupted()){

            List<Arrival> arrivals = TFLAPICommunicator.getArrivals(stopPoint, lineId);
            for(Arrival arrival:arrivals){
                schedule(arrival);
            }

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void schedule(Arrival arrival){
        if(!arrivalMap.containsKey(arrival.id())) {
            ScheduledFuture future = announcements.schedule(
                    makeAnnouncement(arrival), arrival.secondsUntilArrival(), TimeUnit.SECONDS);

            System.out.println("Arrival in " + arrival.minutesUntilArrival());

            arrivalMap.put(arrival.id(), future);
        }
    }

    private Runnable makeAnnouncement(Arrival arrival){
        return () -> StationCommunicator.sendArrival(arrival);
    }
}
