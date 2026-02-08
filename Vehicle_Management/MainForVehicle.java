
public class MainForVehicle {

    public static void main(String[] args) {
        Vehicle vehicle1 = new Vehicle();
        vehicle1.brandName = "Toyota";
        vehicle1.modelName = "Camry";
        vehicle1.color = "Red";
        vehicle1.noOfSeats = 5;
        vehicle1.noOfWheels = 4;
        vehicle1.price = 30000.0;
        vehicle1.fuelType = 'P';
        vehicle1.fuelCapacity = 50.0f;
        vehicle1.yearOfMfg = java.time.Year.of(2020);
        vehicle1.weight = 1500.0;

        Vehicle vehicle2 = new Vehicle("Honda", "Civic", 2019);
        vehicle2.color = "Blue";
        vehicle2.noOfSeats = 5;
        vehicle2.price = 25000.0;
        vehicle2.fuelType = 'P';
        vehicle2.fuelCapacity = 45.0f;

        Vehicle vehicle3 = new Vehicle(vehicle1); // Using copy constructor

        Vehicle vehicle4 = new Vehicle(); // Using default constructor
        vehicle4.setVinNumber("1HGCM82633A004352"); // Setting VIN number for vehicle4
        vehicle4.brandName = "Ford";
        vehicle4.modelName = "Mustang";
        vehicle4.color = "Black";
        vehicle4.noOfSeats = 4;
        vehicle4.price = 35000.0;
        vehicle4.fuelType = 'E';
        vehicle4.fuelCapacity = 60.0f;

        System.out.println("\nDisplaying Initial Vehicle Information:\n");
        Vehicle.displayInfo(vehicle1);
        System.out.println("\n"); // Adding a newline for better readability

        Vehicle.displayInfo(vehicle2);
        System.out.println("\n");

        Vehicle.displayInfo(vehicle3);
        System.out.println("\n");

        Vehicle.displayInfo(vehicle4);
        System.out.println("\n");

        System.out.println("\nDriving Vehicles:\n");
        vehicle1.start();
        vehicle1.accelerate(60);
        String mileage = vehicle1.calculateMileage(600, 40);
        System.out.println("Mileage: " + mileage + " km/l");
        vehicle1.mileage = mileage; // Update mileage attribute of vehicle1
        vehicle1.stop();
        System.out.println("\n");

        vehicle2.start();
        vehicle2.accelerate(80);
        vehicle2.decelerate(35);
        vehicle2.mileage = vehicle2.calculateMileage(800, 50);
        System.out.println("Mileage: " + vehicle2.mileage + " km/l");
        vehicle2.stop();
        System.out.println("\n");

        vehicle3.start();
        vehicle3.changeSpeed('A', 100);
        vehicle3.mileage = vehicle3.calculateMileage(1000, 70);
        System.out.println("Mileage: " + vehicle3.mileage + " km/l");
        vehicle3.stop();
        System.out.println("\n");

        vehicle4.start();
        vehicle4.changeSpeed('A', 120);
        vehicle4.changeSpeed('D', 40); // Decelerate by 40 km/h
        vehicle4.mileage = vehicle4.calculateMileage(1200, 80);
        System.out.println("Mileage: " + vehicle4.mileage + " km/l");
        vehicle4.stop();
        System.out.println("\n");

        Vehicle[] vehicles = {vehicle1, vehicle2, vehicle3, vehicle4};
        System.out.println("\nVehicle Information:");

        // Column headers for table format
        String header1 = "Brand Name";
        String header2 = "Model Name";
        String header3 = "Price ($)";
        String header4 = "Mileage (km/l)";

        // Define a format string with specific widths and left-alignment
        // %-10s means a left-aligned string with a width of 10 characters
        // %10.2f means a right-aligned float with a width of 10 chars and 2 decimal places
        // %-10d means a left-aligned integer with a width of 10 characters
        String formatString = "| %-10s | %-15s | %10s | %-15s |%n";

        // Print the header row and a separator
        System.out.printf("---------------------------------------------------------------%n");
        System.out.printf(formatString, header1, header2, header3, header4);
        System.out.printf("---------------------------------------------------------------%n");

        for (Vehicle vehicle : vehicles) {
            System.out.printf(formatString, vehicle.brandName, vehicle.modelName, vehicle.price, vehicle.mileage);
        }

        System.out.printf("---------------------------------------------------------------%n");
    }
}
