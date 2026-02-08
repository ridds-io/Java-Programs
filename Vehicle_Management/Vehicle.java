
public class Vehicle {

    // 1. Attributes
    public String brandName;
    public String modelName;
    public String color;
    public int noOfSeats;
    public int noOfWheels;
    public double price;
    public char fuelType; // 'E' for Electric, 'P' for Petrol, 'D' for Diesel, 'H' for Hybrid, 'A' for Air(Hydrogen), 'N' for None
    public float fuelCapacity;
    public java.time.Year yearOfMfg;
    public double weight;
    private String vinNumber; // Vehicle Identification Number
    // vinNumber is private to demonstrate encapsulation, public getter and setter for it
    // vinNumber is a unique identifier for each vehicle, typically 17 characters long, containing both letters and numbers. 
    // It can be used to track the vehicle's history, ownership, and other details.
    // Hence, it is important to keep it private and provide controlled access through getter and setter methods.

    public int currSpeed;
    public String mileage;

    // 2. Constructors
    public Vehicle() {
        this.brandName = "Unknown";
        this.modelName = "Unknown";
        this.color = "Unpainted";
        this.noOfSeats = 0;
        this.noOfWheels = 0;
        this.price = 0.0;
        this.fuelType = 'N';
        this.fuelCapacity = 0.0f;
        this.yearOfMfg = java.time.Year.now();
        this.weight = 0.0;
        this.vinNumber = "Unknown";
        this.currSpeed = 0;
        this.mileage = "0.0";
    } // Default constructor, initializes attributes with default values

    public Vehicle(String brand, String model, String color, int seats, int wheels,
            double price, char fuelType, float fuelCap,
            java.time.Year year, double weight, String vinNumber) {
        this.brandName = brand;
        this.modelName = model;
        this.color = color;
        this.noOfSeats = seats;
        this.noOfWheels = wheels;
        this.vinNumber = vinNumber;
        this.price = price;
        this.fuelType = fuelType;
        this.fuelCapacity = fuelCap;
        this.yearOfMfg = year;
        this.weight = weight;
        this.currSpeed = 0;
        this.mileage = "0.0";
    } // Parameterized constructor, allows setting all attributes at the time of object creation

    public Vehicle(String brandName, String modelName, int mfgYear) {
        this.brandName = brandName;
        this.modelName = modelName;
        this.yearOfMfg = java.time.Year.of(mfgYear);
    } // Overloaded constructor, allows setting only brand, model, and year of manufacture

    public Vehicle(Vehicle v) {
        this.brandName = v.brandName;
        this.modelName = v.modelName;
        this.color = v.color;
        this.noOfSeats = v.noOfSeats;
        this.noOfWheels = v.noOfWheels;
        this.price = v.price;
        this.fuelType = v.fuelType;
        this.fuelCapacity = v.fuelCapacity;
        this.yearOfMfg = v.yearOfMfg;
        this.weight = v.weight;
        this.vinNumber = v.vinNumber;
        this.currSpeed = v.currSpeed;
        this.mileage = v.mileage;
    } // Copy constructor, creates a new Vehicle object by copying the attributes of an existing Vehicle object

    // 3.Setter and Getter for vinNumber
    public String setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
        return vinNumber;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    // 4. Methods
    public void start() {
        System.out.println("Starting Vehicle...");
    }

    public void stop() {
        System.out.println("Stopping Vehicle...");
    }

    public void drive() {
        System.out.println("Comencing to drive...");
    }

    public void accelerate(int increment) {
        currSpeed += increment;
        System.out.println("Current speed: " + currSpeed + " km/h");
    }

    public void decelerate(int decrement) {
        currSpeed -= decrement;
        if (currSpeed < 0) {
            currSpeed = 0;
        }
        System.out.println("Current speed: " + currSpeed + " km/h");
    }

    public void changeSpeed(char action, int value) {
        switch (action) {
            case 'A' ->
                accelerate(value);
            case 'D' ->
                decelerate(value);
            default ->
                System.out.println("Invalid action! Use 'A' for accelerate and 'D' for decelerate.");
        }
    }
    // this new way of defining switch case is called "Rule-based Switch" and is available from Java 14 onwards. 
    // It allows for more concise and readable code when dealing with multiple cases in a switch statement. 
    // The "->" syntax is used to define the action for each case, and the "default" case is used to handle any unexpected input.

    public String calculateMileage(double distanceTraveled, double fuelConsumed) {
        if (fuelConsumed != 0) {
            double value = distanceTraveled / fuelConsumed;
            return String.format("%.2f", value);
        } else {
            System.out.println("Error: Fuel consumed cannot be zero.");
            return "0.0";
        }
    }

    public static void displayInfo(Vehicle vehicle) {
        System.out.println("Brand Name: " + vehicle.brandName);
        System.out.println("Model Name: " + vehicle.modelName);
        System.out.println("Color: " + vehicle.color);
        System.out.println("Number of Seats: " + vehicle.noOfSeats);
        System.out.println("Number of Wheels: " + vehicle.noOfWheels);
        System.out.println("Price: " + vehicle.price);
        System.out.println("Fuel Type: " + vehicle.fuelType);
        System.out.println("Fuel Capacity: " + vehicle.fuelCapacity + " liters");
        System.out.println("Year of Manufacture: " + vehicle.yearOfMfg);
        System.out.println("Weight: " + vehicle.weight + " kg");
        System.out.println("VIN Number: " + vehicle.getVinNumber());
        System.out.println("Current Speed: " + vehicle.currSpeed + " km/h");
        System.out.println("Mileage: " + vehicle.mileage + " km/l");
    }

    /* 5. Main method for testing
  
    public static void main(String[] args) {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.brandName = "Toyota";
        vehicle1.modelName = "Camry";
        vehicle1.color = "Blue";
        vehicle1.noOfSeats = 5;
        vehicle1.noOfWheels = 4;
        vehicle1.price = 30000.0;
        vehicle1.fuelType = 'P';
        vehicle1.fuelCapacity = 50.0f;
        vehicle1.yearOfMfg = java.time.Year.of(2020);
        vehicle1.weight = 1500.0;
        vehicle1.currSpeed = 0;

        displayInfo(vehicle1);
        vehicle1.start();
        vehicle1.accelerate(60);
        double mileage = vehicle1.calculateMileage(600.0, 40.0);
        System.out.println("Mileage: " + mileage + " km/l");
        vehicle1.stop();
    }

     */
}
