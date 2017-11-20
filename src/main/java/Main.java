import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    private final static String stopPoint = "940GZZLUWLO";
    private final static String lineId = "bakerloo";

    public static void main(String[] args){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

        ProgramExecutor programExecutor = new ProgramExecutor(stopPoint, lineId, scheduler);
        programExecutor.startBoardUpdates();
        programExecutor.startGettingAnnouncements();

        try {
            scheduler.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
