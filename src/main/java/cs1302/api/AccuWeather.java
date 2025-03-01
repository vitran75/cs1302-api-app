package cs1302.api;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.GsonBuilder;

/**
 * The {@code AccuWeather} class provides functionality to retrieve weather information
 * from the AccuWeather API.
 */
public class AccuWeather {

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
         * The error message of the query.
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
     * Represents an AccuWeather location key obtained from the city search API.
     */
    protected static class AccuWeatherLocationKey {
        /**
         * The location key.
         */
        @SerializedName("Key")
        String key;
        /**
         * The city's English name.
         */
        @SerializedName("EnglishName")
        String englishName;
        /**
         * The city's region.
         */
        @SerializedName("Region")
        Region region;
        /**
         * The Administrative Area.
         */
        @SerializedName("AdministrativeArea")
        AdministrativeArea administrativeArea;
        /**
         * The city's timezone.
         */
        @SerializedName("TimeZone")
        TimeZone timeZone;
    } // AccuWeatherLocationKey

    /**
     * Represents an AccuWeather location region obtained from the city search API.
     */
    private static class Region {
        /**
         * The region's ID.
         */
        @SerializedName("ID")
        String id;
        /**
         * The region's English name.
         */
        @SerializedName("EnglishName")
        String englishName;
    } // Region

    /**
     * Represents administrative information about a state.
     */
    private static class AdministrativeArea {
        /**
         * The state's EnglishName.
         */
        @SerializedName("EnglishName")
        String englishName;
    } // AdministrativeArea

    /**
     * Represents a city's timezone.
     */
    protected static class TimeZone {
        /**
         * The timezone code.
         */
        @SerializedName("Code")
        String code;
    } // TimeZone

    /**
     * Represents the current weather condition obtained from the AccuWeather API.
     */
    protected static class AccuWeatherCurrentCondition {
        /**
         * The local observation date and time.
         */
        @SerializedName("LocalObservationDateTime")
        String localObservationDateTime;
        /**
         * The weather text description.
         */
        @SerializedName("WeatherText")
        String weatherText;
        /**
         * The temperature information.
         */
        @SerializedName("Temperature")
        Temperature temperature;
        /**
         * The relative humidity information.
         */
        @SerializedName("RelativeHumidity")
        int relativeHumidity;
    } // AccuWeatherCurrentCondition

    /**
     * Represents temperature information in both metric and imperial units.
     */
    protected static class Temperature {
        /**
         * Metric temperature information.
         */
        @SerializedName("Metric")
        Metric metric;
    } // Temperature

    /**
     * Represents temperature information in metric units.
     */
    protected static class Metric {
        /**
         * The temperature value.
         */
        @SerializedName("Value")
        double value;
    } // Metric

    /**
     * HTTP client for making requests.
     */
    protected static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();                                     // builds and returns a HttpClient object

    /**
     * Google {@code Gson} object for parsing JSON-formatted strings.
     */
    protected static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()                          // enable nice output when printing
            .create();                                    // builds and returns a Gson object

    // 50 API call per day.
    private static final String API_KEY =
            "G13LhdykGXvFiwcy9AVAsP42fb88lANy";

    /**
     * The endpoint for searching cities and obtaining location keys.
     */
    private static final String LOCATION_KEY_ENDPOINT =
            "http://dataservice.accuweather.com/locations/v1/cities/search";

    /**
     * The endpoint for retrieving current weather conditions.
     */
    private static final String CURRENT_CONDITION_ENDPOINT =
            "http://dataservice.accuweather.com/currentconditions/v1/";

    /**
     * The endpoint for retrieving location information based on IP address.
     */
    private static final String IP_API_ENDPOINT =
            "http://ip-api.com/json/";

    /**
     * Returns the response body string data from a URI.
     *
     * @param uri The URI of the content to fetch.
     * @return The response body string.
     * @throws IOException          If an I/O error occurs when sending or receiving.
     * @throws InterruptedException If the HTTP clients send method is interrupted.
     */
    protected static String fetchString(String uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = HTTP_CLIENT
                .send(request, BodyHandlers.ofString());
        final int statusCode = response.statusCode();
        if (statusCode != 200) {
            throw new IOException(errorCodeParser(statusCode));
        } // if
        return response.body().trim();
    } // fetchString

    /**
     * Retrieves the location information based on the IP address of the local host.
     *
     * @return The location information obtained from the IP address.
     * @throws IOException          If an I/O exception occurs during the process.
     * @throws InterruptedException If the operation is interrupted.
     */
    protected static LocationFromIP getLocationFromIP() throws IOException, InterruptedException {
        String json = AccuWeather.fetchString(IP_API_ENDPOINT);
        LocationFromIP location = GSON.fromJson(json, LocationFromIP.class);
        if (location.status.equals("fail")) {
            throw new IOException(
                    "failed to get localhost due to " + location.message);
        } // if
        return location;
    } // getLocationFromIP

    /**
     * Retrieves the location key from the AccuWeather API
     * based on the provided city name and state.
     *
     * @param q     The name of the city.
     * @param state The name of the state.
     * @return The location key obtained from the AccuWeather API.
     * @throws IOException          If an I/O exception occurs during the process.
     * @throws InterruptedException If the operation is interrupted.
     */
    protected static AccuWeatherLocationKey getLocationFromCity(String q, String state)
            throws IOException, InterruptedException {
        String url = String.format(
                "%s?apikey=%s&q=%s",
                AccuWeather.LOCATION_KEY_ENDPOINT,
                AccuWeather.API_KEY,
                URLEncoder.encode(q, StandardCharsets.UTF_8));
        String json = AccuWeather.fetchString(url);
        AccuWeatherLocationKey[] result = GSON.fromJson(json, AccuWeatherLocationKey[].class);
        if (result.length == 0) {
            throw new IOException("no city information found.");
        }
        for (int i = 0; i < result.length ; i++) {
            if (result[i].englishName.equalsIgnoreCase(q)
                    && result[i].administrativeArea.englishName.equalsIgnoreCase(state)) {
                return result[i];
            } // if
        } // for
        return result[0];
    } // getLocationFromCity

    /**
     * Retrieves the current weather condition from the AccuWeather API
     * based on the provided city name and state.
     *
     * @param key     The API key.
     * @return The current weather condition obtained from the AccuWeather API.
     * @throws IOException          If an I/O exception occurs during the process.
     * @throws InterruptedException If the operation is interrupted.
     */
    protected static AccuWeatherCurrentCondition getCurrentCondition(String key)
            throws IOException, InterruptedException {
        String url = CURRENT_CONDITION_ENDPOINT + key + ".json?apikey=" + API_KEY + "&details=true";
        String json = AccuWeather.fetchString(url);
        AccuWeatherCurrentCondition[] result =
                GSON.fromJson(json, AccuWeatherCurrentCondition[].class);
        return result[0];
    } // getCurrentCondition

    /**
     * Parses the HTTP error code and returns the corresponding error message.
     *
     * @param statusCode The HTTP status code.
     * @return The error message associated with the HTTP status code.
     */
    private static String errorCodeParser(int statusCode) {
        String errorMessage = switch (statusCode) {
        case 400 -> "Request had bad syntax or the parameters supplied were invalid.";
        case 401 -> "Unauthorized. API authorization failed.";
        case 403 -> "Unauthorized. You do not have permission to access this endpoint.";
        case 404 -> "Server has not found a route matching the given URI.";
        case 500 -> "Server encountered an unexpected condition " +
            "which prevented it from fulfilling the request.";
        default -> "Unknown error occurred with status code: " + statusCode;
        }; // switch
        return errorMessage;
    } // errorCodeParser
} // AccuWeather
