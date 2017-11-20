import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Continuously running process to get incoming arrivals.
 *
 * Print the most recent three trips every minute.
 * Update arrivals every minute.
 *
 * Scheduled threadpool for announcements - index by arrival ID to deal with updates
 * Scheduled task to print the three most recent trains every minute.
 *
 *
 * Maybe we want to validate stop point?
 */
public class ProgramExecutor {

    private final String stopPoint;
    private final String lineId;
    private TreeSet<Arrival> arrivals;

    private ScheduledExecutorService announcements;
    private ScheduledExecutorService boardUpdates;

    public ProgramExecutor(String stopPoint, String lineId){
        this.stopPoint = stopPoint;
        this.lineId = lineId;

        // Only have one announcement at once
        this.announcements = Executors.newScheduledThreadPool(1);
        this.boardUpdates = Executors.newScheduledThreadPool(2);
        this.arrivals = new TreeSet<>();

        boardUpdates.scheduleAtFixedRate(updateBoard(), 1,1, TimeUnit.MINUTES);
        boardUpdates.scheduleAtFixedRate(getAnnouncements(), 0, 1, TimeUnit.MINUTES);

        try {
            boardUpdates.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private Runnable getAnnouncements() {
        return () -> {
            List<Arrival> arrivals = TFLAPICommunicator.getArrivals(stopPoint, lineId);

            for(Arrival arrival:arrivals){
                schedule(arrival);
            }
        };
    }

    private void schedule(Arrival arrival){

        if(!arrivals.contains(arrival)) {
            announcements.schedule(
                    makeAnnouncement(arrival), arrival.secondsUntilArrival(), TimeUnit.SECONDS);

            System.out.println("Arrival in " + arrival.minutesUntilArrival());

            arrivals.add(arrival);
        }
    }

    /**
     * Make announcement with first arrival
     */
    private Runnable makeAnnouncement(Arrival arrival){
        return () -> {
            StationCommunicator.sendArrival(arrival);

            // Remove arrival from queue
            arrivals.pollFirst();
        };
    }

    /**
     * Execute update board function with top three arrivals
     */
    private Runnable updateBoard(){
        return () -> {
            List<Arrival> firstThree = Lists.newArrayList(Iterables.limit(arrivals, 3));

            StationCommunicator.sendFollowingArrivals(firstThree);
        };
    }
}
