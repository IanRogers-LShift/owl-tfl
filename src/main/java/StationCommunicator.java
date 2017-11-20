import java.util.List;

/**
 * Send announcements to the station
 */
public class StationCommunicator {


    /**
     * In absense of actual connection to station, write information
     * about the given arrival to a text file
     */
    public static void sendArrival(Arrival arrival){
        print("", arrival);
    }

    /**
     * In absense of actual connection to station, write information
     * about subsequent arrivals to a text file
     */
    public static void sendFollowingArrivals(List<Arrival> arrivals){
        for(Arrival arrival:arrivals){
            print("", arrival);
        }
    }

    private static void print(String file, Arrival arrival){
        System.out.println(arrival);
    }

}
