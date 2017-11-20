import mjson.Json;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

/**
 * Get information as needed from the TFL API
 */
public class TFLAPICommunicator {

    private static final String ARRIVAL_ID = "id";
    private static final String TOWARDS = "towards";
    private static final String EXPECTED_ARRIVAL = "expectedArrival";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");

    private static final String BASE_URI = "https://api.tfl.gov.uk/Line/%s/Arrivals/%s?direction=inbound";
    private static final HttpClient httpClient = HttpClients.createDefault();

    /**
     * Get the most recent arrivals from the TFL API given a stop point and line ID
     */
    public static List<Arrival> getArrivals(String stopPoint, String lineID){
        String stringURI = String.format(BASE_URI, lineID, stopPoint);

        try {
            URI uri = new URI(stringURI);

            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader(CONTENT_TYPE, APPLICATION_JSON.getMimeType());

            HttpResponse response = httpClient.execute(httpGet);

            Json jsonResponse = asJsonHandler.handleResponse(response);

            List<Arrival> arrivals = new ArrayList<>();
            for(Json arrivalJson:jsonResponse.asJsonList()){
                String id = arrivalJson.at(ARRIVAL_ID).asString();
                String destination = arrivalJson.at(TOWARDS).asString();
                String arrivalTime = arrivalJson.at(EXPECTED_ARRIVAL).asString();

                Arrival arrival = new Arrival(lineID,
                        LocalDateTime.parse(arrivalTime, formatter), destination, id
                );

                arrivals.add(arrival);
            }

            return arrivals;
        } catch (URISyntaxException e){
            throw new RuntimeException("Invalid URI " + stringURI);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Response handler code taken from previous project. Maps HTTPReponse
     * to Json object.
     */
    private final static ResponseHandler<Json> asJsonHandler = response -> {
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))){
            return Json.read(reader.lines().collect(Collectors.joining("\n")));
        }
    };
}
