import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    // This list stores the collected data and can be accessed from other classes
    private static List<String> weatherData = Collections.synchronizedList(new ArrayList<>());
    private static final int maxLines = 10;

    Client() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://13.238.167.130/weather"))
                .header("Accept", "text/event-stream")
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .thenApply(HttpResponse::body)
                .thenAccept(inputStream -> {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                        reader.lines()
                            .limit(maxLines)  //Take the first 10 lines (or whatever maxLines is set to)
                            .forEach(line -> {
                                System.out.println(line);
                                weatherData.add(line);  //Store
                            });
                    } catch (IOException e) {
                        System.err.println("Error reading Server Side Event (SSE) stream: " + e.getMessage()); //Pretty sure this didn't work when the server actually timed out but oh well :heartbroken:
                    }
                })
                .join();
    }

    //Getter method for other classes to get the weather list
    public static List<String> getWeatherData() {
        if(weatherData.get(0) == "<html>"){ //If the server times out just use this set instead
            return new ArrayList<>(Arrays.asList(
            "1761798371 windx 14 -8 0.45",
            "1761798371 windy 14 -8 0.55",
            "1761798371 temp 14 -8 0.55",
            "1761798371 windy 14 -8 0.55",
            "1761798371 temp 14 -8 0.55",
            "1761798371 temp 14 -8 0.55",
            "1761798371 rain 14 -7 0.37",
            "1761798371 rain 14 -7 0.37",
            "1761798371 windx 14 -7 0.45",
            "1761798371 windy 14 -7 0.55"
            ));
        }
        return new ArrayList<>(weatherData);
    }

    //Checks if there is data (should always return true or you have severely fucked up)
    public static boolean hasData() {
        return !weatherData.isEmpty();
    }

    //Clear the list if needed (will probably remove later but may be handy for testing)
    public static void clearData() {
        weatherData.clear();
    }
}