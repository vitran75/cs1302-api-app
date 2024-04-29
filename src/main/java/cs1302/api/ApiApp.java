package cs1302.api;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
 * The {@code MakeupRecommendationApp} class provides methods to recommend makeup tools
 * based on the current weather condition.
 */
public class ApiApp extends Application {

    private Stage stage;
    private Scene scene;
    private VBox root;

    private HBox setLocationHBox;
    private ComboBox<String> cityComboBox;
    private ComboBox<String> stateComboBox;
    private Button setLocation;
    private Button getLocation;

    private Label locationLabel;
    private HBox weatherHBox;
    private Label weatherLabel;
    private Separator sep;
    private Label temperatureLabel;

    USData USdata = new USData();
    private final Map<String, List<String>> stateCities = new HashMap<>();

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
        this.setLocation = new Button("Set");
        this.getLocation = new Button("Get Location");
        this.locationLabel = new Label("Location: ");
        this.weatherHBox = new HBox(10);
        this.weatherLabel = new Label("Current Condition: ");
        this.sep = new Separator(Orientation.VERTICAL);
        this.temperatureLabel= new Label("Temperature: ");
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
            }
        });

        setLocationHBox.getChildren().addAll(cityComboBox, stateComboBox, setLocation);
        weatherHBox.getChildren().addAll(weatherLabel, sep, temperatureLabel);

        root.setPadding(new Insets(10));
        root.getChildren().addAll(setLocationHBox, getLocation, locationLabel, weatherHBox);

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
        root.setPrefWidth(320);

        // setup stage
        stage.setTitle("ApiApp!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
    } // start

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        System.out.println("stop() called");
    } // stop

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
