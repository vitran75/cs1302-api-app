package cs1302.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a MakeUp object retrieved from the Makeup API.
 */
public class MakeUpAPI {

    /**
     * Represents the colors of a product.
     */
    protected static class ProductColors {
        // Color hex value
        @SerializedName("hex_value")
        String hexValue;
        // Color name
        @SerializedName("colour_name")
        String colourName;
    } // ProductColors

    /**
     * Represents a makeup product.
     */
    protected static class MakeUp {
        // Product ID
        int id;
        // Brand name
        String brand;
        // Product name
        String name;
        // Product price
        String price;
        // Price Sign ($)
        @SerializedName("price_sign")
        String priceSign;
        // Currency (CAD)
        String currency;
        // link to product image
        @SerializedName("image_link")
        String imageLink;
        // Product description
        String description;
        // Category
        String category;
        // Product type
        @SerializedName("product_type")
        String productType;
        // Product tag list
        @SerializedName("tag_list")
        String[] tagList;
        // Product API URL
        @SerializedName("product_api_url")
        String productApiUrl;
        // Product colors list
        @SerializedName("product_colors")
        ProductColors[] productColors;
    } // MakeUp

    // Makeup API endpoint
    private static final String ENDPOINT = "http://makeup-api.herokuapp.com/api/v1/products.json";
    // Makeup API blushes endpoint
    private static final String BLUSH_ENDPOINT =
            "http://makeup-api.herokuapp.com/api/v1/products.json?product_type=blush";
    // Makeup API eyeliners endpoint
    private static final String EYELINER_ENDPOINT =
            "http://makeup-api.herokuapp.com/api/v1/products.json?product_type=eyeliner";
    // Makeup API foundations endpoint
    private static final String FOUNDATION_ENDPOINT =
            "http://makeup-api.herokuapp.com/api/v1/products.json?product_type=foundation";
    // Makeup API lipsticks endpoint
    private static final String LIPSTICK_ENDPOINT =
            "http://makeup-api.herokuapp.com/api/v1/products.json?product_type=lipstick";
    // Makeup API mascaras endpoint
    private static final String MASCARA_ENDPOINT =
            "http://makeup-api.herokuapp.com/api/v1/products.json?product_type=mascara";

    /**
     * Retrieves makeup products from the specified API endpoint based on the given type.
     *
     * @param endpoint The API endpoint from which to retrieve makeup product data.
     * @return An array of Makeup objects containing makeup product data
     *         obtained from the specified endpoint.
     * @throws IOException          If an I/O error occurs while fetching data from the API.
     * @throws InterruptedException If the thread is interrupted while waiting for the API response.
     */
    private static MakeUp[] getMakeUpProductByType(String endpoint)
            throws IOException, InterruptedException {
        String json = AccuWeather.fetchString(endpoint);
        return AccuWeather.GSON.fromJson(json, MakeUp[].class);
    } // getMakeUpProduct

    /**
     * Recommends makeup products based on the provided tag list.
     *
     * @param tagList An array of tags specifying the desired characteristics of makeup products
     *                for each makeup type (blush, eyeliner, foundation, lipstick, mascara).
     *                Each element in the array corresponds to a makeup type and
     *                contains one or more tags.
     *                If a tag is empty for a makeup type,
     *                products for that type will be chosen randomly.
     * @return A list of lists containing recommended makeup products for each makeup type.
     * Each inner list corresponds to a makeup type (blush, eyeliner, foundation, lipstick, mascara)
     * and contains the recommended makeup products of that type.
     * @throws IOException          If an I/O error occurs while fetching makeup products.
     * @throws InterruptedException If the thread is interrupted while
     *                              waiting for the operation to complete.
     */
    private static List<List<MakeUp>> recommendMakeUp(String[] tagList)
            throws IOException, InterruptedException {

        // list of makeup list contains makeup products sorts by product type
        // (blush, eyeliner, foundation, lipstick, mascara)
        List<List<MakeUp>> products = new ArrayList<>();
        products.add(Arrays.asList(getMakeUpProductByType(BLUSH_ENDPOINT)));
        products.add(Arrays.asList(getMakeUpProductByType(EYELINER_ENDPOINT)));
        products.add(Arrays.asList(getMakeUpProductByType(FOUNDATION_ENDPOINT)));
        products.add(Arrays.asList(getMakeUpProductByType(LIPSTICK_ENDPOINT)));
        products.add(Arrays.asList(getMakeUpProductByType(MASCARA_ENDPOINT)));

        // result list
        List<List<MakeUp>> recommended = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            // temporary list of MakeUp (blush, eyeliner, foundation, lipstick, mascara)
            List<MakeUp> temp = new ArrayList<>(products.get(i));
            // add an empty list of MakeUp
            recommended.add(new ArrayList<>());
            for (int j = 0; j < temp.size(); j++) {
                // temporary MakeUp product
                MakeUp tempMakeUp = temp.get(j);
                // pick one product randomly if no tag returned
                if (tagList[i].isEmpty()) {
                    Random r = new Random();
                    recommended.get(i).add(temp.get(r.nextInt(temp.size())));
                    break;
                } // if
                // add to list when description contains tag
                if (tempMakeUp.description != null) {
                    if (tempMakeUp.description.contains(tagList[i])) {
                        recommended.get(i).add(tempMakeUp);
                    } // if
                } // if
            } // for
        } // for
        return recommended;
    }

    /**
     * Randomly chooses one product from each makeup type list
     * if there are multiple products available.
     *
     * @param productListByType A list of lists containing makeup products
     *                          categorized by makeup type.
     *                          Each inner list corresponds to a makeup type and
     *                          contains makeup products of that type.
     * @return A list of lists containing randomly chosen makeup products for each makeup type.
     * Each inner list corresponds to a makeup type and
     * contains a single randomly chosen makeup product of that type.
     */
    private static List<List<MakeUp>> chooseOneProductRandomly(
        List<List<MakeUp>> productListByType) {

        List<List<MakeUp>> result = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < productListByType.size(); i++) {
            result.add(new ArrayList<>());
            List<MakeUp> temp = productListByType.get(i);
            if (productListByType.get(i).size() > 1) {
                // add 1 random filtered product to its product_by_type list
                result.get(i).add(temp.get(r.nextInt(temp.size())));
            } else if (productListByType.get(i).size() == 1) {
                result.get(i).add(temp.get(0));
            } // if
        } // for
        return result;
    }

    /**
     * Recommends makeup based on weather conditions, temperature, and relative humidity.
     *
     * @param weatherText      The weather condition text.
     * @param temperature      The temperature in Celsius.
     * @param relativeHumidity The relative humidity percentage.
     * @return An array of makeup product tags recommended for each makeup type
     * (blush, eyeliner, foundation, lipstick, mascara).
     * Each element in the array corresponds to a makeup type and
     * contains a tag or tags that represent the recommended makeup products.
     * If no specific recommendation is made for a makeup type,
     * an empty string is returned for that type.
     */
    public static String[] recommendMakeUpTag(
        String weatherText, int temperature, int relativeHumidity) {

        if ((weatherText.contains("hot") | temperature > 35) && relativeHumidity > 60) {
            // Hot and humid weather
            // Recommend lightweight and long-lasting makeup products such as oil-free foundation,
            // waterproof mascara, and mattifying setting powder to combat sweat and shine.
            return new String[]{"", "", "oil-free", "", "waterproof"};
        } else if ((weatherText.contains("cold") | temperature <= 10) && relativeHumidity > 60) {
            // Cold and humid weather
            // Recommend hydrating foundation formulas, moisturizing lipsticks,
            // and cream blushes to keep the skin hydrated and prevent dryness.
            return new String[]{"cream", "", "hydrating", "moisturizing", ""};
        } else if ((weatherText.contains("cold") | temperature <= 10) && relativeHumidity < 30) {
            // Hot and dry weather
            // Recommend hydrating and lightweight makeup products to prevent dryness and flakiness.
            // Use a moisturizing primer and dewy finish foundation.
            return new String[]{"", "", "dewy", "", ""};
        } else if (weatherText.contains("cold") && relativeHumidity < 30) {
            // Cold and dry weather
            // Recommend hydrating foundation formulas, moisturizing lipsticks,
            // and cream blushes to keep the skin hydrated and prevent dryness.
            return new String[]{"cream", "", "hydrating", "moisturizing", ""};
        } else if (weatherText.contains("rain") || weatherText.contains("showers")) {
            // Rainy weather
            // Recommend waterproof makeup products such as
            // waterproof eyeliner and mascara, as well as
            // long-lasting lipsticks that withstand rain and humidity.
            return new String[]{"", "waterproof", "", "long", "waterproof"};
        } else if (weatherText.contains("sunny") && relativeHumidity < 30) {
            // Sunny and dry weather
            // Recommend makeup products with built-in SPF protection,
            // lightweight and breathable formulas to prevent sweat and shine.
            return new String[]{"", "", "spf", "", ""};
        } else if ((weatherText.contains("sunny") && relativeHumidity > 60) |
                (weatherText.contains("windy") && relativeHumidity > 60)) {
            // Sunny and humid weather
            // Recommend lightweight makeup products with mattifying properties
            // to control shine and oiliness. Use a primer to prolong makeup wear.
            return new String[]{"matte", "matte", "matte", "matte", "matte"};
        } else if (weatherText.contains("windy") && relativeHumidity < 30) {
            // Windy and dry weather
            // Recommend long-lasting and transfer-resistant makeup,
            // including smudge-proof eyeliners, waterproof mascaras,
            // and matte lipsticks. Setting sprays can also help to lock makeup in place.
            return new String[]{"", "smudger", "", "matte", "waterproof"};
        } else {
            // anything is ok
            return new String[]{"", "", "", "", ""};
        } // if
    } // recommendMakeUp
} // MakeUpAPI
