# **car-dealership Aplication**
This is a Java Swing application that simulates a Cars Dealership system. It allows users to add cars to a JSON file along with their details such as brand, VIN, km travelled, model, year, price, and an associated image. Users can also view the added cars from the JSON file.

## **Installation**
To run the application, follow these steps:
1. Clone the GitHub repository: `git clone <repository_url>`
2. Open the project in your preferred Java IDE.
3. Build the project to resolve any dependencies.

## **Usage**
1. Run the carsFrame class, which contains the main method.
2. A GUI window titled "Cars Dealership" will open.
3. Three buttons, namely "Welcome," "Add car," and "Show Car," will be displayed at the bottom of the window.
4. Click on the buttons to perform the following actions:
    * "Welcome" button: Displays a welcome message in a dialog box.
    * "Add car" button: Opens a dialog box to enter car details such as brand, VIN, km travelled, model, year, price, and an associated image. After filling in the details, click "OK" to add the car to the JSON file.
    * "Show Car" button: Displays a dropdown list of all the added cars' brands. Select a brand from the dropdown to view the car's details, including its image.

## **JSON File**
The application uses a JSON file (`carsList.json`) to store car data. The file is created in the project directory. Each car entry in the JSON file contains the following fields:

```
{
  "Cars": [
    {
      "Brand": "Toyota",
      "VIN": "ABC1234567890DEF",
      "km travelled": "10000",
      "Model": "Camry",
      "Year": "2021",
      "Price": "25000"
    },
    ...
  ]
}

```
## **Dependencies**
The application uses the following dependencies:
* `javax.swing` package for GUI components
* `org.json` library for JSON processing

## **Contributions**
Contributions to the project are welcome. If you find any issues or have suggestions for improvements, please open an issue or submit a pull request.

Thank you for using the Cars Dealership Application!
