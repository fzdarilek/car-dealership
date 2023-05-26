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
import java.awt.Graphics2D;
import javax.swing.filechooser.FileNameExtensionFilter;

public class carsFrame {
    private static final String JSON_FILE_PATH = "carsList.json";
    private static JFrame frame;
    private static JTextArea textArea;
    private static JComboBox<String> comboBox;

    public static void main(String[] args) {
        frame = new JFrame("Cars Dealership");
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton welcomeButton = new JButton("Welcome");
        JButton addCarButton = new JButton("Add car");
        JButton seeCarsButton = new JButton("Show Car");

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
        final JPanel inputPanel = new JPanel();
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
        JTextField modelField = new JTextField(10);
        inputPanel.add(modelLabel);
        inputPanel.add(modelField);

        JLabel yearLabel = new JLabel("Year:");
        JTextField yearField = new JTextField(10);
        inputPanel.add(yearLabel);
        inputPanel.add(yearField);

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(10);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);

        JLabel imageLabel = new JLabel("Image:");
        JButton chooseImageButton = new JButton("Choose Image");
        final JLabel selectedImageLabel = new JLabel();
        inputPanel.add(imageLabel);
        inputPanel.add(chooseImageButton);
        inputPanel.add(selectedImageLabel);

        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(inputPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedImageLabel.setText(selectedFile.getAbsolutePath());
                    String currentDirectory = System.getProperty("user.dir");
                    String destinationPath = currentDirectory + "\\resources\\addedImages";

                    File destinationFolder = new File(destinationPath);
                    int imageCount = destinationFolder.listFiles().length;

                    String newImageName = "image" + (imageCount + 1) + ".png";
                    Path destination = Paths.get(destinationPath + File.separator + newImageName);

                    try {
                        BufferedImage inputImage = ImageIO.read(selectedFile);

                        File destinationFile = new File(destinationPath + File.separator + newImageName);
                        ImageIO.write(inputImage, "png", destinationFile);

                        System.out.println("Image saved: " + destinationFile.getAbsolutePath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Add Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String brand = brandField.getText();
            String vin = vinField.getText();
            String name = nameField.getText();
            String model = modelField.getText();
            String year = yearField.getText();
            String price = priceField.getText();
            String imagePath = selectedImageLabel.getText();

            if (brand.isEmpty() || vin.isEmpty() || name.isEmpty() || model.isEmpty() || year.isEmpty() || price.isEmpty() || imagePath.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all the fields.");
                return;
            }
            try {
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
