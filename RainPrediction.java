// File: RainPredictionGUI.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RainPredictionGUI extends JFrame implements ActionListener {

    private JTextField humidityField, temperatureField, cloudCoverField;
    private JButton predictButton;
    private JLabel resultLabel;

    // Constructor to set up the GUI
    public RainPredictionGUI() {
        setTitle("Rain Prediction System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));  // 5 rows, 2 columns

        humidityField = new JTextField();
        temperatureField = new JTextField();
        cloudCoverField = new JTextField();
        predictButton = new JButton("Predict");
        resultLabel = new JLabel("Enter values and press Predict", SwingConstants.CENTER);

        predictButton.addActionListener(this);

        // Add components to the frame
        add(new JLabel("Humidity (%):"));
        add(humidityField);
        add(new JLabel("Temperature (°C):"));
        add(temperatureField);
        add(new JLabel("Cloud Cover (%):"));
        add(cloudCoverField);
        add(new JLabel());  // Placeholder for alignment
        add(predictButton);
        add(new JLabel());  // Placeholder for alignment
        add(resultLabel);

        setVisible(true);
    }

    // Logic for rain prediction
    private boolean predictRain(int humidity, double temperature, int cloudCover) {
        return humidity > 70 && cloudCover > 50 && temperature < 35;
    }

    // Action listener for the Predict button
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int humidity = Integer.parseInt(humidityField.getText());
            double temperature = Double.parseDouble(temperatureField.getText());
            int cloudCover = Integer.parseInt(cloudCoverField.getText());

            boolean willRain = predictRain(humidity, temperature, cloudCover);

            if (willRain) {
                resultLabel.setText("Prediction: It will rain today.");
            } else {
                resultLabel.setText("Prediction: It will not rain today.");
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter valid numbers.");
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RainPredictionGUI::new);
    }
}
