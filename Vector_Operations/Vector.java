
public class Vector {

    // 1. Attributes 
    private double x;
    private double y;

    private double z;
    private int dimensions; // 2 or 3

    double[] components;

    // 2. Constructors
    public Vector(double x, double y) throws InvalidDimensionException {
        this.x = x;
        this.y = y;
        this.dimensions = 2;
    }

    public Vector(double x, double y, double z) throws InvalidDimensionException {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimensions = 3;
    }

    public Vector(double[] components) throws InvalidDimensionException {
        this.components = components;
        if (components.length == 2) {
            this.x = components[0];
            this.y = components[1];
            this.dimensions = 2;
        } else if (components.length == 3) {
            this.x = components[0];
            this.y = components[1];
            this.z = components[2];
            this.dimensions = 3;
        } else {
            throw new InvalidDimensionException(
                    "Only 2D or 3D vectors allowed. Got: " + components.length + "D"
            );
        }
    }

    // Private constructor used internally to build result vectors
    private Vector(double x, double y, double z, int dimensions) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimensions = dimensions;
    }

    // 3. Getters and Setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    // 4. Methods
    void checkDimensions(Vector v) throws InvalidDimensionException {
        if (this.dimensions != v.dimensions) {
            throw new InvalidDimensionException("Vectors must have the same number of dimensions");
        }
    }

    // Addition of two vectors
    public Vector add(Vector v) throws InvalidDimensionException {
        checkDimensions(v);
        if (dimensions == 2) {
            return new Vector(this.x + v.x, this.y + v.y, 0, 2);
        } else {
            return new Vector(this.x + v.x, this.y + v.y, this.z + v.z, 3);
        }
    }

    // Subtraction of two vectors
    public Vector subtract(Vector v) throws InvalidDimensionException {
        checkDimensions(v);
        if (dimensions == 2) {
            return new Vector(this.x - v.x, this.y - v.y, 0, 2);
        } else {
            return new Vector(this.x - v.x, this.y - v.y, this.z - v.z, 3);
        }
    }

    // Dot product of two vectors
    public double dotProduct(Vector v) throws InvalidDimensionException {
        checkDimensions(v);
        if (dimensions == 2) {
            return this.x * v.x + this.y * v.y;
        } else {
            return this.x * v.x + this.y * v.y + this.z * v.z;
        }
    }

    // Display the vector
    public void display() {
        if (dimensions == 2) {
            System.out.println("Vector (" + x + ", " + y + ")");
        } else {
            System.out.println("Vector (" + x + ", " + y + ", " + z + ")");
        }
    }

    public static void main(String[] args) {
        try {
            Vector v1 = new Vector(1, 2);
            Vector v2 = new Vector(3, 4);
            Vector v3 = new Vector(5, 6, 7);

            System.out.println("v1:");
            v1.display();
            System.out.println("");

            System.out.println("v2:");
            v2.display();
            System.out.println("");

            System.out.println("v3:");
            v3.display();
            System.out.println("");

            // Test addition
            Vector sum = v1.add(v2);
            System.out.println("v1 + v2:");
            sum.display();
            System.out.println("");

            // Test subtraction
            Vector diff = v1.subtract(v2);
            System.out.println("v1 - v2:");
            diff.display();
            System.out.println("");

            // Test dot product
            double dot = v1.dotProduct(v2);
            System.out.println("v1 . v2: " + dot);
            System.out.println("");

            // Test dimension mismatch
            try {
                v1.add(v3);
            } catch (InvalidDimensionException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Test invalid dimension altogether
            try {
                Vector invalid = new Vector(new double[]{1, 2, 3, 4}); // 4D - should throw
            } catch (InvalidDimensionException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } catch (InvalidDimensionException e) {
            System.out.println("Initialization Error: " + e.getMessage());
        }
    }
}
