package cs1302.api;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

/**
 * The {@code USData} class provides methods to initialize a map of US states and their cities.
 */
public class USData {

    /**
     * Initializes the given map with US states as keys and their corresponding cities as values.
     * @param stateCities a map to be initialized with state-city data
     */
    public void initializeStateCities(Map<String, List<String>> stateCities) {
        // Populate the map with state-city data
        stateCities.put("Alabama", Arrays.asList("Birmingham", "Montgomery", "Huntsville"));
        stateCities.put("Alaska", Arrays.asList("Anchorage", "Fairbanks", "Juneau"));
        stateCities.put("Arizona", Arrays.asList("Phoenix", "Tucson", "Mesa"));
        stateCities.put("Arkansas", Arrays.asList("Little Rock", "Fort Smith", "Fayetteville"));
        stateCities.put("California", Arrays.asList("Los Angeles", "San Diego", "San Jose"));
        stateCities.put("Colorado", Arrays.asList("Denver", "Colorado Springs", "Aurora"));
        stateCities.put("Connecticut", Arrays.asList("Bridgeport", "New Haven", "Hartford"));
        stateCities.put("Delaware", Arrays.asList("Wilmington", "Dover", "Newark"));
        stateCities.put("Florida", Arrays.asList("Jacksonville", "Miami", "Tampa"));
        stateCities.put("Georgia", Arrays.asList("Atlanta", "Augusta", "Columbus"));
        stateCities.put("Hawaii", Arrays.asList("Honolulu", "Pearl City", "Hilo"));
        stateCities.put("Idaho", Arrays.asList("Boise", "Meridian", "Nampa"));
        stateCities.put("Illinois", Arrays.asList("Chicago", "Aurora", "Rockford"));
        stateCities.put("Indiana", Arrays.asList("Indianapolis", "Fort Wayne", "Evansville"));
        stateCities.put("Iowa", Arrays.asList("Des Moines", "Cedar Rapids", "Davenport"));
        stateCities.put("Kansas", Arrays.asList("Wichita", "Overland Park", "Kansas City"));
        stateCities.put("Kentucky", Arrays.asList("Louisville", "Lexington", "Bowling Green"));
        stateCities.put("Louisiana", Arrays.asList("New Orleans", "Baton Rouge", "Shreveport"));
        stateCities.put("Maine", Arrays.asList("Portland", "Lewiston", "Bangor"));
        stateCities.put("Maryland", Arrays.asList("Baltimore", "Columbia", "Germantown"));
        stateCities.put("Massachusetts", Arrays.asList("Boston", "Worcester", "Springfield"));
        stateCities.put("Michigan", Arrays.asList("Detroit", "Grand Rapids", "Warren"));
        stateCities.put("Minnesota", Arrays.asList("Minneapolis", "Saint Paul", "Rochester"));
        stateCities.put("Mississippi", Arrays.asList("Jackson", "Gulfport", "Southaven"));
        stateCities.put("Missouri", Arrays.asList("Kansas City", "Saint Louis", "Springfield"));
        stateCities.put("Montana", Arrays.asList("Billings", "Missoula", "Great Falls"));
        stateCities.put("Nebraska", Arrays.asList("Omaha", "Lincoln", "Bellevue"));
        stateCities.put("Nevada", Arrays.asList("Las Vegas", "Henderson", "Reno"));
        stateCities.put("New Hampshire", Arrays.asList("Manchester", "Nashua", "Concord"));
        stateCities.put("New Jersey", Arrays.asList("Newark", "Jersey City", "Paterson"));
        stateCities.put("New Mexico", Arrays.asList("Albuquerque", "Las Cruces", "Rio Rancho"));
        stateCities.put("New York", Arrays.asList("New York City", "Buffalo", "Rochester"));
        stateCities.put("North Carolina", Arrays.asList("Charlotte", "Raleigh", "Greensboro"));
        stateCities.put("North Dakota", Arrays.asList("Fargo", "Bismarck", "Grand Forks"));
        stateCities.put("Ohio", Arrays.asList("Columbus", "Cleveland", "Cincinnati"));
        stateCities.put("Oklahoma", Arrays.asList("Oklahoma City", "Tulsa", "Norman"));
        stateCities.put("Oregon", Arrays.asList("Portland", "Eugene", "Salem"));
        stateCities.put("Pennsylvania", Arrays.asList("Philadelphia", "Pittsburgh", "Allentown"));
        stateCities.put("Rhode Island", Arrays.asList("Providence", "Warwick", "Cranston"));
        stateCities.put("South Carolina", Arrays.asList("Columbia",
                                                        "Charleston", "North Charleston"));
        stateCities.put("South Dakota", Arrays.asList("Sioux Falls", "Rapid City", "Aberdeen"));
        stateCities.put("Tennessee", Arrays.asList("Nashville", "Memphis", "Knoxville"));
        stateCities.put("Texas", Arrays.asList("Houston", "San Antonio", "Dallas"));
        stateCities.put("Utah", Arrays.asList("Salt Lake City", "West Valley City", "Provo"));
        stateCities.put("Vermont", Arrays.asList("Burlington", "South Burlington", "Rutland"));
        stateCities.put("Virginia", Arrays.asList("Virginia Beach", "Norfolk", "Chesapeake"));
        stateCities.put("Washington", Arrays.asList("Seattle", "Spokane", "Tacoma"));
        stateCities.put("West Virginia", Arrays.asList("Charleston", "Huntington", "Morgantown"));
        stateCities.put("Wisconsin", Arrays.asList("Milwaukee", "Madison", "Green Bay"));
        stateCities.put("Wyoming", Arrays.asList("Cheyenne", "Casper", "Laramie"));
    } // initializeStateCities
} // USData
