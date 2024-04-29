package cs1302.api;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The {@code AccuWeather} class provides functionality to retrieve weather information
 * from the AccuWeather API.
 */
public class AccuWeather {

    /**
     * Represents an AccuWeather get location key from city API document.
     */
    private static class AccuWeatherLocationKey {
        /**
         * The location key.
         */
        String Key;
        /**
         * The city's English name.
         */
        String EnglishName;
        /**
         * The Administrative Area.
         */
        AdministrativeArea AdministrativeArea;
    } // AccuWeatherLocationKey

    /**
     * Represents represents administrative information about a state.
     */
    private static class AdministrativeArea {
        /**
         * The state's ID.
         */
        String ID;
        /**
         * The state's EnglishName.
         */
        String EnglishName;
    } // AdministrativeArea

    /**
     * Represents temperature information in both metric and imperial units.
     */
    protected static class Temperature {
        /**
         * Metric temperature information.
         */
        Metric Metric;
        /**
         * Imperial temperature information.
         */
        Imperial Imperial;
    }

    /**
     * Represents temperature information in metric units.
     */
    protected static class Metric {
        /**
         * The temperature value.
         */
        double Value;
        /**
         * The temperature unit.
         */
        String Unit;
        /**
         * The temperature unit type.
         */
        int UnitType;
    }

    /**
     * Represents temperature information in imperial units.
     */
    protected static class Imperial {
        /**
         * The temperature value.
         */
        double Value;
        /**
         * The temperature unit.
         */
        String Unit;
        /**
         * The temperature unit type.
         */
        int UnitType;
    }

    /**
     * Represents location information obtained from the IP address.
     */
    protected static class LocationFromIP {
        /**
         * The IP address query.
         */
        String query;
        /**
         * The status of the query.
         */
        String status;
        /**
         * The error message of the query
         */
        String message;
        /**
         * The region name.
         */
        String regionName;
        /**
         * The city name.
         */
        String city;
    } // LocationFromIP


    /**
     * Represents an AccuWeather get current condition API document.
     */
    protected static class AccuWeatherCurrentCondition {
        /**
         * The local observation date and time.
         */
        String LocalObservationDateTime;
        /**
         * The weather text description.
         */
        String WeatherText;
        /**
         * The temperature information.
         */
        Temperature Temperature;
    } // AccuWeatherCurrentCondition

    /**
     * HTTP client for making requests.
     */
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();                                     // builds and returns a HttpClient object

    /**
     * Google {@code Gson} object for parsing JSON-formatted strings.
     */
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()                          // enable nice output when printing
            .create();                                    // builds and returns a Gson object

    // 50 API call per day.
    private static final String API_KEY =
            "G13LhdykGXvFiwcy9AVAsP42fb88lANy";

    private static final String LOCATION_KEY_ENDPOINT =
            "http://dataservice.accuweather.com/locations/v1/cities/search";

    private static final String CURRENT_CONDITION_ENDPOINT =
            "http://dataservice.accuweather.com/currentconditions/v1/";

    private static final String IP_API_ENDPOINT =
            "http://ip-api.com/json/";

    /**
     * Returns the response body string data from a URI.
     *
     * @param uri location of desired content
     * @return response body string
     * @throws IOException          if an I/O error occurs when sending or receiving
     * @throws InterruptedException if the HTTP client's {@code send} method is
     *                              interrupted
     */
    private static String fetchString(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = HTTP_CLIENT
                .send(request, BodyHandlers.ofString());
        final int statusCode = response.statusCode();
        if (statusCode != 200) {
            throw new IOException("response status code not 200:" + statusCode);
        } // if
        return response.body().trim();
    } // fetchString

    /**
     * Retrieves location information based on the IP address of the device.
     *
     * @return location information obtained from the IP address
     */
    protected static LocationFromIP getLocationFromIP() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            String url = IP_API_ENDPOINT + ip.getHostAddress();
            String json = AccuWeather.fetchString(url);
            LocationFromIP location = GSON.fromJson(json, LocationFromIP.class);
            if (location.status.equals("fail")) {
                throw new UnknownHostException(
                    "failed to get localhost due to " + location.message);
            } // if
            return location;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } // try
    } // getLocationFromIP

    /**
     * Retrieves the location key corresponding to the specified city.
     *
     * @param q the city name
     * @return the location key
     */
    private static String getLocationFromCity(String q, String state) {
        String locationKey = "";
        try {
            String url = String.format(
                    "%s?apikey=%s&q=%s",
                    AccuWeather.LOCATION_KEY_ENDPOINT,
                    AccuWeather.API_KEY,
                    URLEncoder.encode(q, StandardCharsets.UTF_8));
            String json = AccuWeather.fetchString(url);
            AccuWeatherLocationKey[] result = GSON.fromJson(json, AccuWeatherLocationKey[].class);
            for (AccuWeatherLocationKey r : result) {
                if (r.EnglishName.equalsIgnoreCase(q)
                        && r.AdministrativeArea.EnglishName.equalsIgnoreCase(state)) {
                    locationKey = r.Key;
                    break;
                } else {
                    throw new IOException("bad input.");
                } // if
            } // for
            return locationKey;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } // try
    } // getLocationFromCity

    /**
     * Retrieves the current weather condition for the specified city.
     *
     * @param q the city name
     * @return the current weather condition
     */
    protected static AccuWeatherCurrentCondition getCurrentCondition(String q, String state) {
        String key = getLocationFromCity(q, state);
        try {
            String url = CURRENT_CONDITION_ENDPOINT + key + "?apikey=" + API_KEY;
            String json = AccuWeather.fetchString(url);
            AccuWeatherCurrentCondition[] result =
                    GSON.fromJson(json, AccuWeatherCurrentCondition[].class);
            return result[0];
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } // try
    } // getCurrentCondition
} // AccuWeather
