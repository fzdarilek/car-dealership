package com.example;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class carsFrame {
    private static final String JSON_FILE_PATH = "carsList.json";

    public static void main(String[] args) {
        final JFrame frame = new JFrame("carsJframe"); // Set the title of the JFrame

        final JTextArea textArea = new JTextArea(); // Create a JTextArea to display the JSON data
        JScrollPane scrollPane = new JScrollPane(textArea); // Wrap the JTextArea in a JScrollPane for scrolling

        frame.add(scrollPane, BorderLayout.CENTER); // Add the JScrollPane to the center of the JFrame

        JButton addButton = new JButton("Add Car"); // Create an "Add Car" button
        JPanel buttonPanel = new JPanel(); // Create a panel to hold the button
        buttonPanel.add(addButton); // Add the button to the panel
        frame.add(buttonPanel, BorderLayout.SOUTH); // Add the panel to the bottom of the JFrame

        // Set the size of the JFrame
        int width = 800;
        int height = 600;
        frame.setSize(width, height);

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the visibility of the JFrame
        frame.setVisible(true);

        // Read the JSON file and display its contents in the JTextArea
        try {
            String jsonContent = readJsonFile(JSON_FILE_PATH);
            textArea.setText(jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Action listener for the "Add Car" button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a bubble window for user input
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());

                // Create labels and text fields for each input field
                JLabel brandLabel = new JLabel("Brand:");
                JTextField brandField = new JTextField(10);
                panel.add(brandLabel, BorderLayout.WEST);
                panel.add(brandField, BorderLayout.EAST);

                JLabel vinLabel = new JLabel("VIN:");
                JTextField vinField = new JTextField(10);
                panel.add(vinLabel, BorderLayout.WEST);
                panel.add(vinField, BorderLayout.EAST);

                JLabel nameLabel = new JLabel("Name:");
                JTextField nameField = new JTextField(10);
                panel.add(nameLabel, BorderLayout.WEST);
                panel.add(nameField, BorderLayout.EAST);

                JLabel modelLabel = new JLabel("Model:");
                JTextField modelField = new JTextField(10);
                panel.add(modelLabel, BorderLayout.WEST);
                panel.add(modelField, BorderLayout.EAST);

                JLabel yearLabel = new JLabel("Year:");
                JTextField yearField = new JTextField(10);
                panel.add(yearLabel, BorderLayout.WEST);
                panel.add(yearField, BorderLayout.EAST);

                JLabel priceLabel = new JLabel("Price:");
                JTextField priceField = new JTextField(10);
                panel.add(priceLabel, BorderLayout.WEST);
                panel.add(priceField, BorderLayout.EAST);

                // Show the bubble window and wait for user input
                int result = JOptionPane.showConfirmDialog(frame, panel, "Add Car", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        // Get the existing JSON content from the JTextArea
                        String jsonContent = textArea.getText();

                        // Parse the JSON content as a JSONArray
                        JSONArray jsonArray = new JSONArray(jsonContent);

                        // Create a new car JSONObject
                        JSONObject newCar = new JSONObject();
                        JSONObject carObject = new JSONObject();
                        carObject.put("Brand", brandField.getText());
                        carObject.put("VIN", vinField.getText());
                        carObject.put("name", nameField.getText());
                        carObject.put("model", modelField.getText());
                        carObject.put("year", yearField.getText());
                        carObject.put("price", priceField.getText());
                        newCar.put("Car", carObject);

                        // Append the new car JSONObject to the existing JSONArray
                        jsonArray.put(newCar);

                        // Convert the updated JSONArray back to a formatted JSON string
                        String updatedJsonContent = jsonArray.toString(4);

                        // Save the updated JSON content to the file
                        writeJsonFile(JSON_FILE_PATH, updatedJsonContent);

                        // Update the JTextArea with the updated JSON content
                        textArea.setText(updatedJsonContent);

                        JOptionPane.showMessageDialog(frame, "Car added and saved successfully!");
                    } catch (JSONException | IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Failed to add and save the car!");
                    }
                }
            }
        });
    }

    private static String readJsonFile(String filePath) throws IOException {
        StringBuilder jsonContent = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            jsonContent.append(line);
            jsonContent.append("\n");
        }
        reader.close();
        return jsonContent.toString();
    }

    private static void writeJsonFile(String filePath, String jsonContent) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(jsonContent);
        writer.close();
    }
}
