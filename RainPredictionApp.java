import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RainPredictionApp extends Application {

    private TextField humidityField, temperatureField, cloudCoverField;
    private Label resultLabel;
    private Slider rainChanceSlider;
    private ImageView weatherIcon;

    @Override
    public void start(Stage primaryStage) {
        // Window title
        primaryStage.setTitle("Rain Prediction System");

        // Header
        Label header = new Label("Rain Prediction System");
        header.setFont(Font.font("Times New Roman", FontWeight.BOLD, 45));
        header.setTextFill(Color.web("#4A90E2"));

        // Weather icon (rainy cloud) - Optional local or online image
        weatherIcon = new ImageView(new Image("file:rain_icon.jpg"));
        weatherIcon.setFitHeight(80);
        weatherIcon.setFitWidth(80);

        // Input GridPane
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(12);
        inputGrid.setVgap(12);
        inputGrid.setPadding(new Insets(20));
        inputGrid.setAlignment(Pos.CENTER);

        inputGrid.add(createStyledLabel("Humidity (%):"), 0, 0);
        humidityField = new TextField();
        inputGrid.add(humidityField, 1, 0);

        inputGrid.add(createStyledLabel("Temperature (°C):"), 0, 1);
        temperatureField = new TextField();
        inputGrid.add(temperatureField, 1, 1);

        inputGrid.add(createStyledLabel("Cloud Cover (%):"), 0, 2);
        cloudCoverField = new TextField();
        inputGrid.add(cloudCoverField, 1, 2);

        inputGrid.add(createStyledLabel("Chance of Rain (%):"), 0, 3);
        rainChanceSlider = new Slider(0, 100, 50);
        rainChanceSlider.setShowTickMarks(true);
        rainChanceSlider.setShowTickLabels(true);
        rainChanceSlider.setMajorTickUnit(25);
        inputGrid.add(rainChanceSlider, 1, 3);

        // Predict and Reset buttons
        Button predictButton = createStyledButton("Predict");
        predictButton.setOnAction(e -> predictRain());

        Button resetButton = createStyledButton("Reset");
        resetButton.setOnAction(e -> resetFields());

        HBox buttonBox = new HBox(15, predictButton, resetButton);
        buttonBox.setAlignment(Pos.CENTER);

        resultLabel = new Label("Result will appear here...");
        resultLabel.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 25));
        resultLabel.setTextFill(Color.web("#7F8C8D"));

        VBox mainLayout = new VBox(20, header, weatherIcon, inputGrid, buttonBox, resultLabel);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(25));
        mainLayout.setBackground(new Background(
                new BackgroundFill(Color.web("#D6EAF8"), CornerRadii.EMPTY, Insets.EMPTY)
        ));

        Scene scene = new Scene(mainLayout, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 20));
        label.setTextFill(Color.web("#4A90E2"));
        return label;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #4A90E2; -fx-background-radius: 10px;");
        button.setPrefWidth(140);
        return button;
    }

    // Prediction logic
    private void predictRain() {
        try {
            int humidity = Integer.parseInt(humidityField.getText());
            double temperature = Double.parseDouble(temperatureField.getText());
            int cloudCover = Integer.parseInt(cloudCoverField.getText());
            int rainChance = (int) rainChanceSlider.getValue();

            if (humidity < 0 || humidity > 100 || cloudCover < 0 || cloudCover > 100 || 
                temperature < -50 || temperature > 60) {
                showMessage("Values out of range! Please enter valid inputs.", Color.RED);
                return;
            }

            String prediction = getPrediction(humidity, temperature, cloudCover, rainChance);
            showMessage(prediction, Color.web("#27AE60"));
        } catch (NumberFormatException e) {
            showMessage("Invalid input! Please enter valid numbers.", Color.RED);
        }
    }

    private String getPrediction(int humidity, double temperature, int cloudCover, int rainChance) {
        if (humidity > 70 && cloudCover > 50 && temperature < 35 && rainChance > 60) {
            return "High chance of rain today.";
        } else if (temperature < 0) {
            return "Chances of snow instead of rain.";
        } else if (humidity == 100 && cloudCover == 0) {
            return "Fog expected but no rain.";
        } else if (cloudCover == 100 && humidity < 40) {
            return "Cloudy but no rain expected.";
        } else {
            return "It will not rain today.";
        }
    }

    private void showMessage(String message, Color color) {
        resultLabel.setText(message);
        resultLabel.setTextFill(color);
    }

    // Reset
    private void resetFields() {
        humidityField.clear();
        temperatureField.clear();
        cloudCoverField.clear();
        rainChanceSlider.setValue(50);
        resultLabel.setText("Result will appear here...");
        resultLabel.setTextFill(Color.web("#7F8C8D"));
        weatherIcon.setOpacity(1); 
    }

    // Main method
    public static void main(String[] args) {
        launch(args);
    }
}