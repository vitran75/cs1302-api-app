/**
 * The {@code AccuWeather} class provides functionality to retrieve weather information
 * from the AccuWeather API.
 */
public class AccuWeather {

    /**
     * Represents an AccuWeather API key document.
     */
    private static class AccuWeatherKey {
        /** The API key. */
        String Key;
    } // AccuWeatherKey

    /**
     * Represents temperature information in both metric and imperial units.
     */
    private static class Temperature {
        /** Metric temperature information. */
        Metric Metric;
        /** Imperial temperature information. */
        Imperial Imperial;
    } // Temperature

    /**
     * Represents temperature information in metric units.
     */
    private static class Metric {
        /** The temperature value. */
        double Value;
        /** The temperature unit. */
        String Unit;
        /** The temperature unit type. */
        int UnitType;
    } // Metric

    /**
     * Represents temperature information in imperial units.
     */
    private static class Imperial {
        /** The temperature value. */
        double Value;
        /** The temperature unit. */
        String Unit;
        /** The temperature unit type. */
        int UnitType;
    } // Imperial

    /**
     * Represents location information obtained from the IP address.
     */
    private static class LocationFromIP {
        /** The IP address query. */
        String query;
        /** The status of the query. */
        String status;
        /** The region name. */
        String regionName;
        /** The city name. */
        String city;
    } // LocationFromIP

    /**
     * Represents an AccuWeather API document.
     */
    protected static class AccuWeatherAPI {
        /** The local observation date and time. */
        String LocalObservationDateTime;
        /** The weather text description. */
        String WeatherText;
        /** The temperature information. */
        Temperature Temperature;
    } // AccuWeatherAPI

    /** HTTP client for making requests. */
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
            .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
            .build();                                     // builds and returns a HttpClient object

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
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
     * @param uri location of desired content
     * @return response body string
     * @throws IOException if an I/O error occurs when sending or receiving
     * @throws InterruptedException if the HTTP client's {@code send} method is
     *    interrupted
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
     * @return location information obtained from the IP address
     */
    private static LocationFromIP getLocationFromIP() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            String url = IP_API_ENDPOINT + ip.getHostAddress();
            String json = AccuWeather.fetchString(url);
            return GSON.fromJson(json, LocationFromIP.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } // try
    } // getLocationFromIP

    /**
     * Retrieves the location key corresponding to the specified city.
     * @param q the city name
     * @return the location key
     */
    private static String getLocationFromCity(String q) {
        try {
            String url = String.format(
                "%s?apikey=%s&q=%s",
                AccuWeather.LOCATION_KEY_ENDPOINT,
                AccuWeather.API_KEY,
                URLEncoder.encode(q, StandardCharsets.UTF_8));
            String json = AccuWeather.fetchString(url);
            AccuWeatherKey[] result = GSON.fromJson(json, AccuWeatherKey[].class);
            return result[0].Key;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } // try
    } // getLocationFromCity

    /**
     * Retrieves the current weather condition for the specified city.
     * @param q the city name
     * @return the current weather condition
     */
    protected static AccuWeatherAPI getCurrentCondition(String q) {
        String key = getLocationFromCity(q);
        try {
            String url = CURRENT_CONDITION_ENDPOINT + key + "?apikey=" + API_KEY;
            String json = AccuWeather.fetchString(url);
            AccuWeatherAPI[] result = GSON.fromJson(json, AccuWeatherAPI[].class);
            return result[0];
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } // try
    } // getCurrentCondition
} // AccuWeather
