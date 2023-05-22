package com.example;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.awt.GridLayout;

public class carsFrame {
    private static final String JSON_FILE_PATH = "carsList.json";
    private static final String[] MODELS = {"Model A", "Model B", "Model C"};

    private static JFrame frame;
    private static JTextArea textArea;

    public static void main(String[] args) {
        frame = new JFrame("Cars JFrame");
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton welcomeButton = new JButton("Welcome");
        JButton addCarButton = new JButton("Add Car");
        JButton seeCarsButton = new JButton("See Cars");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(welcomeButton);
        buttonPanel.add(addCarButton);
        buttonPanel.add(seeCarsButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        int width = 800;
        int height = 600;
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        welcomeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Welcome! Please select an option from the buttons.");
            }
        });

        addCarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addCar();
            }
        });

        seeCarsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCars();
            }
        });
    }

    private static void addCar() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));
    
        JLabel brandLabel = new JLabel("Brand:");
        JTextField brandField = new JTextField(10);
        inputPanel.add(brandLabel);
        inputPanel.add(brandField);
    
        JLabel vinLabel = new JLabel("VIN:");
        JTextField vinField = new JTextField(10);
        inputPanel.add(vinLabel);
        inputPanel.add(vinField);
    
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(10);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
    
        JLabel modelLabel = new JLabel("Model:");
        JComboBox<String> modelComboBox = new JComboBox<>(MODELS);
        inputPanel.add(modelLabel);
        inputPanel.add(modelComboBox);
    
        JLabel yearLabel = new JLabel("Year:");
        JTextField yearField = new JTextField(10);
        inputPanel.add(yearLabel);
        inputPanel.add(yearField);
    
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(10);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);
    
        int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Add Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String brand = brandField.getText();
            String vin = vinField.getText();
            String name = nameField.getText();
            String model = (String) modelComboBox.getSelectedItem();
            String year = yearField.getText();
            String price = priceField.getText();
    
            try {
                JSONObject carData = new JSONObject();
                carData.put("Brand", brand);
                carData.put("VIN", vin);
                carData.put("Name", name);
                carData.put("Model", model);
                carData.put("Year", year);
                carData.put("Price", price);
    
                JSONArray carsArray;
                JSONObject carsData;
    
                if (Files.exists(Paths.get(JSON_FILE_PATH))) {
                    String fileContent = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
                    carsData = new JSONObject(fileContent);
                    if (carsData.has("Cars")) {
                        carsArray = carsData.getJSONArray("Cars");
                    } else {
                        carsArray = new JSONArray();
                    }
                } else {
                    carsData = new JSONObject();
                    carsArray = new JSONArray();
                }
    
                carsArray.put(carData);
                carsData.put("Cars", carsArray);
                Files.write(Paths.get(JSON_FILE_PATH), carsData.toString().getBytes());
    
                JOptionPane.showMessageDialog(frame, "Car added successfully!");
            } catch (JSONException | IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Failed to add car. Please try again.");
            }
        }
    }
    

    private static void showCars() {
        try {
            if (Files.exists(Paths.get(JSON_FILE_PATH))) {
                String fileContent = new String(Files.readAllBytes(Paths.get(JSON_FILE_PATH)));
                JSONObject carsData = new JSONObject(fileContent);
    
                if (carsData.has("Cars")) {
                    JSONArray carsArray = carsData.getJSONArray("Cars");
    
                    if (carsArray.length() > 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Available Cars:\n\n");
    
                        for (int i = 0; i < carsArray.length(); i++) {
                            JSONObject carData = carsArray.getJSONObject(i);
                            sb.append("Car ").append(i + 1).append(":\n");
                            sb.append("Brand: ").append(carData.getString("Brand")).append("\n");
                            sb.append("VIN: ").append(carData.getString("VIN")).append("\n");
                            sb.append("Name: ").append(carData.getString("Name")).append("\n");
                            sb.append("Model: ").append(carData.getString("Model")).append("\n");
                            sb.append("Year: ").append(carData.getString("Year")).append("\n");
                            sb.append("Price: ").append(carData.getString("Price")).append("\n\n");
                        }
    
                        textArea.setText(sb.toString());
                    } else {
                        textArea.setText("No cars available.");
                    }
                } else {
                    textArea.setText("No cars available.");
                }
            } else {
                textArea.setText("No cars available.");
            }
        } catch (JSONException | IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to retrieve car data. Please try again.");
        }
    }
    
    }

