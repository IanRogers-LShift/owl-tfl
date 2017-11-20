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
        print("", "Spoken announcement", arrival);
    }

    /**
     * In absense of actual connection to station, write information
     * about subsequent arrivals to a text file
     */
    public static void sendFollowingArrivals(List<Arrival> arrivals){
        for(Arrival arrival:arrivals){
            print("", "Board announcement", arrival);
        }
    }

    private static void print(String file, String preface, Arrival arrival){
        System.out.println(preface);
        System.out.println(arrival);
        System.out.println();
    }

}
