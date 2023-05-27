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
import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.filechooser.FileNameExtensionFilter;

public class carsFrame {
    private static final String JSON_FILE_PATH = "carsList.json";
    private static JFrame frame;
    private static JTextArea textArea;
    private static JComboBox<String> comboBox;
    //private static JPanel addCarPanel;

    public static void main(String[] args) {
        frame = new JFrame("Cars Dealership");
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JToggleButton welcomeButton = new JToggleButton("Welcome");
        JToggleButton addCarButton = new JToggleButton("Add car");
        JToggleButton seeCarsButton = new JToggleButton("Show Car");
        buttonPanel.add(welcomeButton);
        buttonPanel.add(addCarButton);
        buttonPanel.add(seeCarsButton);
        frame.add(buttonPanel, BorderLayout.CENTER);

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
            // Create the input fields and labels
            JLabel brandLabel = new JLabel("Brand:");
            JTextField brandField = new JTextField(10);
        
            JLabel vinLabel = new JLabel("VIN:");
            JTextField vinField = new JTextField(10);
        
            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField(10);
        
            JLabel modelLabel = new JLabel("Model:");
            JTextField modelField = new JTextField(10);
        
            JLabel yearLabel = new JLabel("Year:");
            JTextField yearField = new JTextField(10);
        
            JLabel priceLabel = new JLabel("Price:");
            JTextField priceField = new JTextField(10);
        
            JLabel imageLabel = new JLabel("Image:");
            JButton chooseImageButton = new JButton("Choose Image");
            final JLabel selectedImageLabel = new JLabel();
        
            // Set the layout for the input panel
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        
            // Create a separate panel for each row of input fields and labels
            JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            brandPanel.add(brandLabel);
            brandPanel.add(brandField);
            inputPanel.add(brandPanel);
        
            JPanel vinPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            vinPanel.add(vinLabel);
            vinPanel.add(vinField);
            inputPanel.add(vinPanel);
        
            JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            namePanel.add(nameLabel);
            namePanel.add(nameField);
            inputPanel.add(namePanel);
        
            JPanel modelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            modelPanel.add(modelLabel);
            modelPanel.add(modelField);
            inputPanel.add(modelPanel);
        
            JPanel yearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            yearPanel.add(yearLabel);
            yearPanel.add(yearField);
            inputPanel.add(yearPanel);
        
            JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pricePanel.add(priceLabel);
            pricePanel.add(priceField);
            inputPanel.add(pricePanel);
        
            JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            imagePanel.add(imageLabel);
            imagePanel.add(chooseImageButton);
            inputPanel.add(imagePanel);
            inputPanel.add(selectedImageLabel);
        
            // Add an action listener to the chooseImageButton
            chooseImageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                    fileChooser.setFileFilter(filter);
                    int result = fileChooser.showOpenDialog(frame);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        selectedImageLabel.setText(selectedFile.getAbsolutePath());
                    }
                }
            });
        
            // Show the input panel in a JOptionPane
            int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Add Car", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Retrieve the car information
                String brand = brandField.getText();
                String vin = vinField.getText();
                String name = nameField.getText();
                String model = modelField.getText();
                String year = yearField.getText();
                String price = priceField.getText();
                String imagePath = selectedImageLabel.getText();
        
                // Validate the input fields
                if (brand.isEmpty() || vin.isEmpty() || name.isEmpty() || model.isEmpty() || year.isEmpty() || price.isEmpty() || imagePath.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all the fields.");
                    addCar();
                    return;
                }
        
                if (vin.length() != 17 || !vin.matches("[A-HJ-NPR-Z0-9]{17}")) {
                    JOptionPane.showMessageDialog(frame, "Invalid VIN. Please enter a valid VIN.");
                    addCar();
                    return;
                }
        
                try {
                    float floatValue = Float.parseFloat(price);
                    if (floatValue < 0) {
                        JOptionPane.showMessageDialog(frame, "Invalid price. Please enter a valid price.");
                        addCar();
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Invalid price. Please enter a valid price.");
                    addCar();
                    return;
                }
        

            String currentDirectory = System.getProperty("user.dir");
            String destinationPath = currentDirectory + "\\resources\\addedImages";

            File destinationFolder = new File(destinationPath);
            int imageCount = destinationFolder.listFiles().length;

            String newImageName = "image" + (imageCount + 1) + ".png";
            Path destination = Paths.get(destinationPath + File.separator + newImageName);

            try {
                BufferedImage inputImage = ImageIO.read(new File(imagePath));
                File destinationFile = new File(destination.toString());
                ImageIO.write(inputImage, "png", destinationFile);
                System.out.println("Image saved: " + destinationFile.getAbsolutePath());

                JSONObject carData = new JSONObject();
                carData.put("Brand", brand);
                carData.put("VIN", vin);
                carData.put("Name", name);
                carData.put("Model", model);
                carData.put("Year", year);
                carData.put("Price", price);

                final JSONArray carsArray;
                final JSONObject carsData;

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
                        JComboBox<String> comboBox = new JComboBox<>();
                        StringBuilder sb = new StringBuilder();
                        textArea.setText("");

                        for (int i = 0; i < carsArray.length(); i++) {
                            JSONObject carData = carsArray.getJSONObject(i);
                            String name = carData.getString("Name");
                            comboBox.addItem(name);
                        }

                        JOptionPane.showMessageDialog(frame, comboBox);

                        String selectedName = (String) comboBox.getSelectedItem();

                        sb.append("Cars with Name: ").append(selectedName).append("\n");

                        int selectedCarIndex = -1;

                        for (int i = 0; i < carsArray.length(); i++) {
                            JSONObject carData = carsArray.getJSONObject(i);
                            String name = carData.getString("Name");
                            if (name.equals(selectedName)) {
                                sb.append("Car ").append(i + 1).append(":\n");
                                sb.append("Brand: ").append(carData.getString("Brand")).append("\n");
                                sb.append("VIN: ").append(carData.getString("VIN")).append("\n");
                                sb.append("Name: ").append(carData.getString("Name")).append("\n");
                                sb.append("Model: ").append(carData.getString("Model")).append("\n");
                                sb.append("Year: ").append(carData.getString("Year")).append("\n");
                                sb.append("Price: ").append(carData.getString("Price")).append("\n");

                                selectedCarIndex = i + 1;
                            }
                        }

                        textArea.setText(sb.toString());

                        if (selectedCarIndex != -1) {
                            String currentDirectory = System.getProperty("user.dir");
                            String imagePath = currentDirectory + "\\resources\\addedImages\\image" + selectedCarIndex + ".png";

                            try {
                                BufferedImage bufferedImage = ImageIO.read(new File(imagePath));

                                int desiredWidth = 50;
                                int desiredHeight = 100;

                                Image resizedImage = bufferedImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                                ImageIcon imageIcon = new ImageIcon(resizedImage);

                                JLabel label = new JLabel(imageIcon);
                                label.setBounds(10, textArea.getHeight() + 20, desiredWidth, desiredHeight);
                                frame.add(label);

                                frame.revalidate();
                                frame.repaint();
                            } catch (IOException e) {
                                e.printStackTrace();
                                JOptionPane.showMessageDialog(frame, "Failed to load image.");
                            }
                        }
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
