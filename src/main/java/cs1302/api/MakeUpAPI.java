package cs1302.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private static MakeUp[] getMakeUpProductByType(String endpoint)
            throws IOException, InterruptedException {
        String json = AccuWeather.fetchString(endpoint);
        return AccuWeather.GSON.fromJson(json, MakeUp[].class);
    } // getMakeUpProduct

    private static List<MakeUp> productCheck(MakeUp[] product, String[][] tag)
            throws IOException, InterruptedException {
        List<MakeUp[]> products = new ArrayList<>();
        products.add(getMakeUpProductByType(BLUSH_ENDPOINT));
        products.add(getMakeUpProductByType(EYELINER_ENDPOINT));
        products.add(getMakeUpProductByType(FOUNDATION_ENDPOINT));
        products.add(getMakeUpProductByType(LIPSTICK_ENDPOINT));
        products.add(getMakeUpProductByType(MASCARA_ENDPOINT));

        for (MakeUp p : product) {
            return products;
        }
    }

    // Method to recommend makeup based on weather conditions
    public static String[][] recommendMakeUpTag(String weatherText, int relativeHumidity) {
        if (weatherText.contains("hot") && relativeHumidity > 60) {
            // Hot and humid weather
            // Recommend lightweight and long-lasting makeup products such as oil-free foundation,
            // waterproof mascara, and mattifying setting powder to combat sweat and shine.
            return new String[][]{new String[0], new String[0], new String[] {"oil-free"},
                    new String[0], new String[]{"water", "waterproof"}};
        } else if (weatherText.contains("cold") && relativeHumidity > 60) {
            // Cold and humid weather
            // Recommend hydrating foundation formulas, moisturizing lipsticks,
            // and cream blushes to keep the skin hydrated and prevent dryness.
            return new String[][]{new String[]{"cream"}, new String[0], new String[] {"hydrating"},
                    new String[]{"moisture", "moisturizing"}, new String[0]};
        } else if (weatherText.contains("hot") && relativeHumidity < 30) {
            // Hot and dry weather
            // Recommend hydrating and lightweight makeup products to prevent dryness and flakiness.
            // Use a moisturizing primer and dewy finish foundation.
            return new String[][]{new String[0], new String[0],
                    new String[]{"hydrating"}, new String[0], new String[0]};
        } else if (weatherText.contains("cold") && relativeHumidity < 30) {
            // Cold and dry weather
            // Recommend hydrating foundation formulas, moisturizing lipsticks,
            // and cream blushes to keep the skin hydrated and prevent dryness.
            return new String[][]{new String[]{"cream"}, new String[0], new String[] {"hydrating"},
                    new String[]{"moisture", "moisturizing"}, new String[0]};
        } else if (weatherText.contains("rain") || weatherText.contains("showers")) {
            // Rainy weather
            // Recommend waterproof makeup products such as
            // waterproof eyeliner and mascara, as well as
            // long-lasting lipsticks that withstand rain and humidity.
            return new String[][]{new String[0], new String[]{"water-resistant", "waterproof"},
                    new String[0], new String[]{"long"}, new String[]{"water", "waterproof"}};
        } else if (weatherText.contains("sunny") && relativeHumidity < 30) {
            // Sunny and dry weather
            // Recommend makeup products with built-in SPF protection,
            // lightweight and breathable formulas to prevent sweat and shine.
            return new String[][]{new String[0], new String[0],
                    new String[]{"spf"}, new String[0], new String[0]};
        } else if ((weatherText.contains("sunny") && relativeHumidity > 60) |
                (weatherText.contains("windy") && relativeHumidity > 60)) {
            // Sunny and humid weather
            // Recommend lightweight makeup products with mattifying properties
            // to control shine and oiliness. Use a primer to prolong makeup wear.
            return new String[][]{new String[]{"matte"}, new String[]{"matte"},
                    new String[]{"matte"}, new String[]{"matte"}, new String[0]};
        } else if (weatherText.contains("windy") && relativeHumidity < 30) {
            // Windy and dry weather
            // Recommend long-lasting and transfer-resistant makeup,
            // including smudge-proof eyeliners, waterproof mascaras,
            // and matte lipsticks. Setting sprays can also help to lock makeup in place.
            return new String[][]{new String[0], new String[]{"smudger", "smudgeliner"},
                    new String[0], new String[]{"matte"}, new String[]{"water", "waterproof"}};
        } // if
        return new String[0][];
    } // recommendMakeUp
} // MakeUpAPI
