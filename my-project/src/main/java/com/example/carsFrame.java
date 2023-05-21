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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class carsFrame {
    private static final String JSON_FILE_PATH = "carsList.json";
    private static final String[] ATTRIBUTES = { "Brand", "VIN", "Name", "Model", "Year", "Price" };

    public static void main(String[] args) {
        final JSONObject carsObject = readJSONFile(JSON_FILE_PATH);

        final JFrame frame = new JFrame("Cars JFrame");
        final JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Car");
        JButton clearButton = new JButton("Clear");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        int width = 800;
        int height = 600;
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel inputPanel = new JPanel(new GridLayout(6, 2));

                JTextField[] attributeFields = new JTextField[ATTRIBUTES.length];

                for (int i = 0; i < ATTRIBUTES.length; i++) {
                    JLabel attributeLabel = new JLabel(ATTRIBUTES[i] + ":");
                    attributeFields[i] = new JTextField(10);
                    inputPanel.add(attributeLabel);
                    inputPanel.add(attributeFields[i]);
                }

                int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Add Car", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        for (int i = 0; i < ATTRIBUTES.length; i++) {
                            String attribute = ATTRIBUTES[i];
                            String value = attributeFields[i].getText();

                            JSONArray attributeList = carsObject.getJSONArray(attribute);
                            attributeList.put(value);
                        }

                        writeJSONFile(JSON_FILE_PATH, carsObject.toString());

                        String carInfo = "Brand: " + attributeFields[0].getText() +
                                "\nVIN: " + attributeFields[1].getText() +
                                "\nName: " + attributeFields[2].getText() +
                                "\nModel: " + attributeFields[3].getText() +
                                "\nYear: " + attributeFields[4].getText() +
                                "\nPrice: " + attributeFields[5].getText() +
                                "\n----------------------------------\n";
                        textArea.append(carInfo);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
    }

    private static JSONObject readJSONFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return new JSONObject(content);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    private static void writeJSONFile(String filePath, String jsonContent) {
        try {
            Files.write(Paths.get(filePath), jsonContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
