import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
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
    public static void main(String[] args) {
        JFrame frame = new JFrame("carsJframe"); // Set the title of the JFrame

        JTextArea textArea = new JTextArea(); // Create a JTextArea to display the JSON data
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
            BufferedReader reader = new BufferedReader(new FileReader("carsList.json"));

            String line;
            StringBuilder jsonContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
                jsonContent.append("\n");
            }

            reader.close();

            textArea.setText(jsonContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Action listener for the "Add Car" button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the existing JSON content from the JTextArea
                    String jsonContent = textArea.getText();

                    // Parse the JSON content as a JSONArray
                    JSONArray jsonArray = new JSONArray(jsonContent);

                    // Create a new car JSONObject
                    JSONObject newCar = new JSONObject();
                    JSONObject carObject = new JSONObject();
                    carObject.put("Brand", "");
                    carObject.put("VIN", "");
                    carObject.put("name", "");
                    carObject.put("model", "");
                    carObject.put("year", "");
                    carObject.put("price", "");
                    newCar.put("Car", carObject);

                    // Append the new car JSONObject to the existing JSONArray
                    jsonArray.put(newCar);

                    // Convert the updated JSONArray back to a formatted JSON string
                    String updatedJsonContent = jsonArray.toString(4);

                    // Save the updated JSON content to the file
                    BufferedWriter writer = new BufferedWriter(new FileWriter("carsList.json"));
                    writer.write(updatedJsonContent);
                    writer.close();

                    JOptionPane.showMessageDialog(frame, "Car added and saved successfully!");
                } catch (JSONException | IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Failed to add and save the car!");
                }
            }
        });
    }
}
