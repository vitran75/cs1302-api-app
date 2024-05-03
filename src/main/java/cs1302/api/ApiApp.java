package cs1302.api;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.stage.Modality;

/**
 * The {@code ApiApp} class provides methods to recommend makeup tools
 * based on the current weather condition.
 */
public class ApiApp extends Application {

    private Stage stage;
    private Scene scene;
    private VBox root;

    private HBox setLocationHBox;
    private ComboBox<String> cityComboBox;
    private ComboBox<String> stateComboBox;
    private Button setLocationBtn;
    private Button clearLocation;

    private Label locationLabel;
    String currentCity;
    String currentState;

    private HBox localTime;
    private Label date;
    private Label time;
    private Label timeZone;

    private HBox weatherHBox;
    private Label weatherLabel;
    private Label temperatureLabel;
    private Label humidityLabel;

    private HBox statusBox;
    private Label status;

    UsData usData = new UsData();
    private final Map<String, List<String>> stateCities = new HashMap<>();

    AccuWeather.AccuWeatherLocationKey cityInfo =
            new AccuWeather.AccuWeatherLocationKey();

    AccuWeather.AccuWeatherCurrentCondition currentCondition =
            new AccuWeather.AccuWeatherCurrentCondition();

    List<List<MakeUpAPI.MakeUp>> recommendMakeUpProducts =
            new ArrayList<>();

    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        this.stage = null;
        this.scene = null;
        this.root = new VBox(5);
        this.setLocationHBox = new HBox(5);
        this.cityComboBox = new ComboBox<>();
        this.cityComboBox.setEditable(true);
        this.cityComboBox.setPromptText("City");
        this.stateComboBox = new ComboBox<>();
        this.stateComboBox.setEditable(true);
        this.stateComboBox.setPromptText("State");
        this.setLocationBtn = new Button("Set");
        this.clearLocation = new Button("Clear");
        this.locationLabel = new Label("Location: none");
        this.localTime = new HBox(10);
        this.date = new Label("Date: yyyy-mm-dd");
        this.time = new Label("Time: hh:mm:ss");
        this.timeZone = new Label("Timezone: Z");
        this.weatherHBox = new HBox(10);
        this.weatherLabel = new Label("Current Condition: none");
        this.temperatureLabel = new Label("Temperature: none");
        this.humidityLabel = new Label("Humidity: none");
        this.statusBox = new HBox();
        this.status = new Label("Waiting...");
    } // ApiApp

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        // Get cities and states data
        usData.initializeStateCities(stateCities);
        // Populate the states ComboBox with the keys from the stateCities HashMap
        stateComboBox.getItems().addAll(stateCities.keySet());

        // Add a listener to the states ComboBox to populate
        // the cities ComboBox with the corresponding cities
        stateComboBox.setOnAction(event -> {
            String selectedState = stateComboBox.getSelectionModel().getSelectedItem();
            List<String> cities = stateCities.get(selectedState);
            cityComboBox.getItems().clear();
            if (cities != null) {
                cityComboBox.getItems().addAll(cities);
            } // if
        });
        statusBox.getChildren().add(status);
        statusBox.setAlignment(Pos.CENTER);
        // location box
        setLocationHBox.getChildren().addAll(cityComboBox, stateComboBox,
                setLocationBtn, clearLocation);
        // local time box
        localTime.getChildren().addAll(date, new Separator(Orientation.VERTICAL),
                time, new Separator(Orientation.VERTICAL), timeZone);
        // weather condition box
        weatherHBox.getChildren().addAll(weatherLabel, new Separator(Orientation.VERTICAL),
                temperatureLabel, new Separator(Orientation.VERTICAL), humidityLabel);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(setLocationHBox, new Separator(),
                locationLabel, localTime, weatherHBox,
                new Separator(), statusBox);

        setLocationBtn.setOnAction((ActionEvent e) -> {
            if (getWeatherInfo(e)[0]) {
                Thread makeUpThread = new Thread(() -> {
                    try {
                        // delay for weather info
                        Thread.sleep(5000);
                        popUpMakeUpProducts();
                    } catch (IOException | InterruptedException ex) {
                        Platform.runLater(() -> status.setText("Failed to get makeup products."));
                        alertError(ex);
                    } finally {
                        setLocationBtn.setDisable(false);
                        clearLocation.setDisable(false);
                    } // try
                });
                makeUpThread.start();
            } else {
                setLocationBtn.setDisable(false);
                clearLocation.setDisable(false);
            } // if
        });

        clearLocation.setOnAction(e -> {
            cityComboBox.setValue(null);
            stateComboBox.setValue(null);
        });

    } // init

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) {
        // Set the default font for the entire application
        Font defaultFont = Font.font("Arial", 12);
        String style = String.format("-fx-font-family: '%s'; -fx-font-size: %.1f;",
                defaultFont.getFamily(), defaultFont.getSize());
        this.stage = stage;
        scene = new Scene(root);
        root.setStyle(style);
        root.setPrefWidth(482);
        this.stage.setResizable(false);

        // setup stage
        this.stage.setTitle("ApiApp!");
        this.stage.setScene(scene);
        this.stage.setOnCloseRequest(event -> Platform.exit());
        this.stage.sizeToScene();
        this.stage.show();
    } // start

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        System.out.println("stop() called");
    } // stop

    /**
     * Adds a makeup product to the UI.
     *
     * @param box          The VBox container to which the makeup product will be added.
     * @param makeUpByType The makeup product to be added.
     */
    private void addMakeUpProductToUI(VBox box, MakeUpAPI.MakeUp makeUpByType) {
        HBox hBox = new HBox(5);
        VBox vBox = new VBox(2);
        ImageView imgView = new ImageView(new Image(makeUpByType.imageLink));
        imgView.setFitHeight(100);
        imgView.setFitWidth(100);
        Label name = new Label(makeUpByType.name);
        Label brand = new Label("Brand: " + makeUpByType.brand);
        Label price = new Label("Price: " + makeUpByType.price + "$");
        Hyperlink productLink = new Hyperlink(makeUpByType.productLink);

        vBox.getChildren().addAll(name, brand, price, productLink);
        hBox.getChildren().addAll(imgView, new Separator(Orientation.VERTICAL), vBox);
        box.getChildren().add(hBox);
    }

    /**
     * Displays a popup window with makeup products recommendation.
     * This method first updates the status label to indicate that
     * makeup products are being retrieved.
     * It then retrieves makeup products based on weather conditions
     * and displays them in a popup window.
     * Finally, it updates the status label to indicate success.
     *
     * @throws IOException          If an I/O error occurs.
     * @throws InterruptedException If the thread is interrupted
     *                              while waiting for the popup window to close.
     */
    private void popUpMakeUpProducts() throws IOException, InterruptedException {

        Platform.runLater(() -> status.setText("Getting makeup products. It may take a while..."));

        // get tag list
        String[] tagList = MakeUpAPI.recommendMakeUpTag(currentCondition.weatherText,
                currentCondition.temperature.metric.value, currentCondition.relativeHumidity);
        // get makeup products
        recommendMakeUpProducts = MakeUpAPI.chooseOneProductRandomly(
            MakeUpAPI.recommendMakeUp(tagList));

        Platform.runLater(() -> {
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("MakeUp Products Recommendation");

            VBox popupRoot = new VBox(5);
            Scene popupScene = new Scene(popupRoot, 500, 520);
            popupStage.setResizable(false);

            for (List<MakeUpAPI.MakeUp> mul : recommendMakeUpProducts) {
                addMakeUpProductToUI(popupRoot, mul.get(0));
            }

            status.setText("Success.");
            popupStage.setScene(popupScene);
            popupStage.showAndWait(); // Show the popup window and wait for it to be closed
        });
    }

    /**
     * Updates the weather user interface with the current weather information.
     * If the UI is empty, initializes the UI components and adds them to the root pane.
     */
    public void updateWeatherUI() {
        locationLabel.setText("Location: " + this.currentCity + ", " + this.currentState);
        date.setText("Date: " + this.currentCondition.localObservationDateTime.substring(0, 10));
        time.setText("Time: " + this.currentCondition.localObservationDateTime.substring(11, 19));
        timeZone.setText("Timezone: UTC" + currentCondition.localObservationDateTime.substring(19) +
                "(" + this.cityInfo.timeZone.code + ")");
        weatherLabel.setText("Condition: " + this.currentCondition.weatherText);
        temperatureLabel.setText("Temperature: " + this.currentCondition.temperature.metric.value
                + " Celsius");
        humidityLabel.setText("Humidity: " + this.currentCondition.relativeHumidity + "%");
    } // updateWeatherUI

    /**
     * Retrieves weather information based on the selected state and city.
     * Disables the location-related buttons during the process.
     * If no state or city is selected, retrieves location information from IP address.
     * Updates the UI with the retrieved weather information.
     *
     * @param e The ActionEvent triggering the method call.
     * @return An array of booleans indicating the success of the weather information retrieval.
     *         The first element represents the success status. If the retrieval is successful,
     *         it will be true; otherwise, it will be false.
     */
    public boolean[] getWeatherInfo(ActionEvent e) {
        setLocationBtn.setDisable(true);
        clearLocation.setDisable(true);
        Platform.runLater(() -> status.setText("Getting weather information..."));
        final boolean[] success = {true};
        if (this.stateComboBox.getValue() == null || this.cityComboBox.getValue() == null) {
            AccuWeather.LocationFromIP location = new AccuWeather.LocationFromIP();
            try {
                location = AccuWeather.getLocationFromIP();
            } catch (IOException | InterruptedException ex) {
                Platform.runLater(() -> {
                    status.setText("Failed to get current weather condition.");
                    alertError(ex);
                });
                success[0] = false;
            } // try
            currentCity = location.city;
            currentState = location.regionName;
            Platform.runLater(() -> {
                this.cityComboBox.setValue(currentCity);
                this.stateComboBox.setValue(currentState);
            });
        } else {
            currentCity = this.cityComboBox.getValue();
            currentState = this.stateComboBox.getValue();
        } // if

        Thread weatherThread = new Thread(() -> {
            try {
                cityInfo = AccuWeather.getLocationFromCity(currentCity, currentState);
                currentCondition = AccuWeather.getCurrentCondition(cityInfo.key);
                Platform.runLater(() -> {
                    updateWeatherUI();
                    status.setText("Success.");
                });
            } catch (IOException | InterruptedException ex) {
                Platform.runLater(() -> {
                    status.setText("Failed to get current weather condition.");
                    alertError(ex);
                });
                success[0] = false;
            } // try
        });
        weatherThread.start();
        return success;
    } // getWeatherInfo

    /**
     * Show a modal error alert based on {@code cause}.
     *
     * @param cause a {@link java.lang.Throwable Throwable} that caused the alert
     */
    public static void alertError(Throwable cause) {
        TextArea text = new TextArea(cause.toString());
        text.setEditable(false);
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().setContent(text);
        alert.setResizable(true);
        alert.showAndWait();
    } // alertError

} // ApiApp
