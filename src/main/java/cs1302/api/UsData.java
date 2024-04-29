package cs1302.api;

import java.util.List;
import java.util.Map;
import java.util.Arrays;

/**
 * The {@code UsData} class provides methods to initialize a map of US states and their cities.
 */
public class UsData {

    /**
     * Initializes the given map with US states as keys and their corresponding cities as values.
     * @param stateCities a map to be initialized with state-city data
     */
    public void initializeStateCities(Map<String, List<String>> stateCities) {
        // Populate the map with state-city data
        stateCities.put("Alabama", Arrays.asList("Birmingham", "Montgomery", "Huntsville",
                "Mobile", "Tuscaloosa", "Hoover"));
        stateCities.put("Alaska", Arrays.asList("Anchorage", "Fairbanks", "Juneau",
                "Sitka", "Ketchikan", "Wasilla"));
        stateCities.put("Arizona", Arrays.asList("Phoenix", "Tucson", "Mesa",
                "Chandler", "Scottsdale", "Glendale"));
        stateCities.put("Arkansas", Arrays.asList("Little Rock", "Fort Smith", "Fayetteville",
                "Springdale", "Jonesboro", "North Little Rock"));
        stateCities.put("California", Arrays.asList("Los Angeles", "San Diego", "San Jose",
                "San Francisco", "Fresno", "Sacramento"));
        stateCities.put("Colorado", Arrays.asList("Denver", "Colorado Springs", "Aurora",
                "Fort Collins", "Lakewood", "Thornton"));
        stateCities.put("Connecticut", Arrays.asList("Bridgeport", "New Haven", "Hartford",
                "Stamford", "Waterbury", "Norwalk"));
        stateCities.put("Delaware", Arrays.asList("Wilmington", "Dover", "Newark",
                "Middletown", "Smyrna", "Milford"));
        stateCities.put("Florida", Arrays.asList("Jacksonville", "Miami", "Tampa",
                "Orlando", "St. Petersburg", "Hialeah"));
        stateCities.put("Georgia", Arrays.asList("Atlanta", "Augusta", "Columbus",
                "Savannah", "Athens", "Suwanee"));
        stateCities.put("Hawaii", Arrays.asList("Honolulu", "Pearl City", "Hilo",
                "Kailua", "Waipahu", "Kaneohe"));
        stateCities.put("Idaho", Arrays.asList("Boise", "Meridian", "Nampa",
                "Idaho Falls", "Pocatello", "Caldwell"));
        stateCities.put("Illinois", Arrays.asList("Chicago", "Aurora", "Rockford",
                "Joliet", "Naperville", "Springfield"));
        stateCities.put("Indiana", Arrays.asList("Indianapolis", "Fort Wayne", "Evansville",
                "South Bend", "Carmel", "Fishers"));
        stateCities.put("Iowa", Arrays.asList("Des Moines", "Cedar Rapids", "Davenport",
                "Sioux City", "Iowa City", "Waterloo"));
        stateCities.put("Kansas", Arrays.asList("Wichita", "Overland Park", "Kansas City",
                "Olathe", "Topeka", "Lawrence"));
        stateCities.put("Kentucky", Arrays.asList("Louisville", "Lexington", "Bowling Green",
                "Owensboro", "Covington", "Richmond"));
        stateCities.put("Louisiana", Arrays.asList("New Orleans", "Baton Rouge", "Shreveport",
                "Lafayette", "Lake Charles", "Kenner"));
        stateCities.put("Maine", Arrays.asList("Portland", "Lewiston", "Bangor",
                "South Portland", "Auburn", "Biddeford"));
        stateCities.put("Maryland", Arrays.asList("Baltimore", "Columbia", "Germantown",
                "Silver Spring", "Waldorf", "Frederick"));
        stateCities.put("Massachusetts", Arrays.asList("Boston", "Worcester", "Springfield",
                "Lowell", "Cambridge", "New Bedford"));
        stateCities.put("Michigan", Arrays.asList("Detroit", "Grand Rapids", "Warren",
                "Sterling Heights", "Lansing", "Ann Arbor"));
        stateCities.put("Minnesota", Arrays.asList("Minneapolis", "Saint Paul", "Rochester",
                "Duluth", "Bloomington", "Brooklyn Park"));
        stateCities.put("Mississippi", Arrays.asList("Jackson", "Gulfport", "Southaven",
                "Hattiesburg", "Biloxi", "Meridian"));
        stateCities.put("Missouri", Arrays.asList("Kansas City", "Saint Louis", "Springfield",
                "Independence", "Columbia", "Lee's Summit"));
        stateCities.put("Montana", Arrays.asList("Billings", "Missoula", "Great Falls",
                "Bozeman", "Butte", "Helena"));
        stateCities.put("Nebraska", Arrays.asList("Omaha", "Lincoln", "Bellevue",
                "Grand Island", "Kearney", "Fremont"));
        stateCities.put("Nevada", Arrays.asList("Las Vegas", "Henderson", "Reno",
                "North Las Vegas", "Sparks", "Carson City"));
        stateCities.put("New Hampshire", Arrays.asList("Manchester", "Nashua", "Concord",
                "Derry", "Dover", "Rochester"));
        stateCities.put("New Jersey", Arrays.asList("Newark", "Jersey City", "Paterson",
                "Elizabeth", "Edison", "Woodbridge"));
        stateCities.put("New Mexico", Arrays.asList("Albuquerque", "Las Cruces", "Rio Rancho",
                "Santa Fe", "Roswell", "Farmington"));
        stateCities.put("New York", Arrays.asList("New York City", "Buffalo", "Rochester",
                "Yonkers", "Syracuse", "Albany"));
        stateCities.put("North Carolina", Arrays.asList("Charlotte", "Raleigh", "Greensboro",
                "Durham", "Winston-Salem", "Fayetteville"));
        stateCities.put("North Dakota", Arrays.asList("Fargo", "Bismarck", "Grand Forks",
                "Minot", "West Fargo", "Mandan"));
        stateCities.put("Ohio", Arrays.asList("Columbus", "Cleveland", "Cincinnati",
                "Toledo", "Akron", "Dayton"));
        stateCities.put("Oklahoma", Arrays.asList("Oklahoma City", "Tulsa", "Norman",
                "Broken Arrow", "Lawton", "Edmond"));
        stateCities.put("Oregon", Arrays.asList("Portland", "Eugene", "Salem",
                "Gresham", "Hillsboro", "Beaverton"));
        stateCities.put("Pennsylvania", Arrays.asList("Philadelphia", "Pittsburgh", "Allentown",
                "Erie", "Reading", "Scranton"));
        stateCities.put("Rhode Island", Arrays.asList("Providence", "Warwick", "Cranston",
                "Pawtucket", "East Providence", "Woonsocket"));
        stateCities.put("South Carolina", Arrays.asList("Columbia", "Charleston",
                "North Charleston", "Mount Pleasant", "Rock Hill", "Greenville"));
        stateCities.put("South Dakota", Arrays.asList("Sioux Falls", "Rapid City", "Aberdeen",
                "Brookings", "Watertown", "Mitchell"));
        stateCities.put("Tennessee", Arrays.asList("Nashville", "Memphis", "Knoxville",
                "Chattanooga", "Clarksville", "Murfreesboro"));
        stateCities.put("Texas", Arrays.asList("Houston", "San Antonio", "Dallas",
                "Austin", "Fort Worth", "El Paso"));
        stateCities.put("Utah", Arrays.asList("Salt Lake City", "West Valley City", "Provo",
                "West Jordan", "Orem", "Sandy"));
        stateCities.put("Vermont", Arrays.asList("Burlington", "South Burlington", "Rutland",
                "Essex", "Colchester", "Bennington"));
        stateCities.put("Virginia", Arrays.asList("Virginia Beach", "Norfolk", "Chesapeake",
                "Richmond", "Newport News", "Alexandria"));
        stateCities.put("Washington", Arrays.asList("Seattle", "Spokane", "Tacoma",
                "Vancouver", "Bellevue", "Kent"));
        stateCities.put("West Virginia", Arrays.asList("Charleston", "Huntington", "Morgantown",
                "Parkersburg", "Wheeling", "Weirton"));
        stateCities.put("Wisconsin", Arrays.asList("Milwaukee", "Madison", "Green Bay",
                "Kenosha", "Racine", "Appleton"));
        stateCities.put("Wyoming", Arrays.asList("Cheyenne", "Casper", "Laramie",
                "Gillette", "Rock Springs", "Sheridan"));
    } // initializeStateCities
} // UsData
