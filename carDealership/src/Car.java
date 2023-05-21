import java.io.*;

public class Car implements Serializable{
    String VIN;
    String brand;
    String model;
    String name;
    int year;
    float price;
    
    Car() {};   // default constructor
    Car(String VIN, String brand, String model, String name, int year, float price) {
        this.brand = brand;
        this.name = name;
        this.model = model;
        this.VIN = VIN;
        this.year = year;
        this.price = price;
    }
    public boolean addNewCar(String VIN, String brand, String model, String name, int year, float price) {
        this.VIN = VIN;
        this.brand = brand;
        this.model = model;
        this.name = name;
        this.year = year;
        this.price = price;
        return true;
    }

    public String getVIN() {
        return VIN;
    }

    public String getbrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public float getPrice() {
        return price;
    }
    
    
   
}