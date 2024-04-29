package cs1302.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.geometry.Insets;

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
    private Separator comboBoxSep;

    private Label locationLabel;
    String currentCity;
    String currentState;

    private Label localTime;

    private HBox weatherHBox;
    private Label weatherLabel;
    private Separator weatherSep;
    private Label temperatureLabel;

    USData USdata = new USData();
    private final Map<String, List<String>> stateCities = new HashMap<>();

    AccuWeather.AccuWeatherCurrentCondition currentCondition =
            new AccuWeather.AccuWeatherCurrentCondition();

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
        this.stateComboBox = new ComboBox<>();
        this.setLocationBtn = new Button("Set");
        this.clearLocation = new Button("Clear");
        this.comboBoxSep = new Separator(Orientation.HORIZONTAL);
        this.locationLabel = new Label("Location: none");
        this.localTime = new Label("Time: yyyy-mm-ddThh:mm:ssZ");
        this.weatherHBox = new HBox(10);
        this.weatherLabel = new Label("Current Condition: none");
        this.weatherSep = new Separator(Orientation.VERTICAL);
        this.temperatureLabel= new Label("Temperature: none");
    } // ApiApp

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        // Get cities and states data
        USdata.initializeStateCities(stateCities);

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

        setLocationHBox.getChildren().addAll(cityComboBox, stateComboBox,
                setLocationBtn, clearLocation);
        weatherHBox.getChildren().addAll(weatherLabel, weatherSep, temperatureLabel);

        root.setPadding(new Insets(10));
        root.getChildren().addAll(setLocationHBox,
                comboBoxSep, locationLabel, localTime, weatherHBox);

        setLocationBtn.setOnAction((ActionEvent e) -> getWeatherInfo(e));

        clearLocation.setOnAction(e -> {
            cityComboBox.setValue(null);
            stateComboBox.setValue(null);
        });

    } // init
    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        // Set the default font for the entire application
        Font defaultFont = Font.font("Arial", 12);
        String style = String.format("-fx-font-family: '%s'; -fx-font-size: %.1f;",
                defaultFont.getFamily(), defaultFont.getSize());
        this.stage = stage;
        scene = new Scene(root);
        root.setStyle(style);
        root.setPrefWidth(500);

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
     * Updates the weather user interface with the current weather information.
     * If the UI is empty, initializes the UI components and adds them to the root pane.
     */
    public void updateWeatherUI() {
        locationLabel.setText("Location: " + this.currentCity + ", " + this.currentState);
        localTime.setText("Time: " + this.currentCondition.LocalObservationDateTime);
        weatherLabel.setText("Condition: " + this.currentCondition.WeatherText);
        temperatureLabel.setText("Temperature: " + this.currentCondition.Temperature.Metric.Value
                + " Celsius");
    } // updateWeatherUI

    /**
     * Retrieves weather information based on the selected state and city.
     * Disables the location-related buttons during the process.
     * If no state or city is selected, retrieves location information from IP address.
     * Updates the UI with the retrieved weather information.
     *
     * @param e The ActionEvent triggering the method call.
     */
    public void getWeatherInfo(ActionEvent e) {
        setLocationBtn.setDisable(true);
        clearLocation.setDisable(true);
        if (this.stateComboBox.getValue() == null || this.cityComboBox.getValue() == null) {
            AccuWeather.LocationFromIP location = new AccuWeather.LocationFromIP();
            try {
                location = AccuWeather.getLocationFromIP();
            } catch (IOException | InterruptedException ex) {
                Platform.runLater(() -> {
                    alertError(ex);
                });
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
                currentCondition = AccuWeather.getCurrentCondition(currentCity, currentState);
                Platform.runLater(() -> updateWeatherUI() );
            } catch (IOException | InterruptedException ex) {
                Platform.runLater(() -> {
                    alertError(ex);
                });
            } finally {
                Platform.runLater(() -> {
                    setLocationBtn.setDisable(false);
                    clearLocation.setDisable(false);
                });
            } // try
        });
        weatherThread.start();
    } // getWeatherInfo

    /**
     * Show a modal error alert based on {@code cause}.
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
