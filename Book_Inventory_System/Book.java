
public class Book {

    // 1. Attributes (Fields)
    private String title;
    private String author;
    private int year;
    public int price;
    public String genre;
    public String publisher;
    public String isbn; // International Standard Book Number
    public int pages;
    public String language;

    // 2. Constructors
    public Book() {
        title = "Unknown";
        author = "Unknown";
        year = 0;
        price = 0;
        genre = "Unknown";
        publisher = "Unknown";
        isbn = "Unknown";
        pages = 0;
        language = "Unknown";
    }

    public Book(String title, String author, int year, int price, String genre, String publisher, String isbn, int pages, String language) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
        this.genre = genre;
        this.publisher = publisher;
        this.isbn = isbn;
        this.pages = pages;
        this.language = language;
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book(Book b) {
        this.title = b.title;
        this.author = b.author;
        this.year = b.year;
        this.price = b.price;
        this.genre = b.genre;
        this.publisher = b.publisher;
        this.isbn = b.isbn;
        this.pages = b.pages;
        this.language = b.language;
    }

    public Book(Book b, int price, String genre) throws InvalidGenreException, InvalidPriceException {
        this(b);
        if (price < 0) {
            throw new InvalidPriceException("Price must be greater than 0. Provided price: " + price);
        }
        if (!genre.equalsIgnoreCase("Novel") && !genre.equalsIgnoreCase("Dystopian") && !genre.equalsIgnoreCase("Romance") && !genre.equalsIgnoreCase("Fantasy") && !genre.equalsIgnoreCase("Adventure") && !genre.equalsIgnoreCase("Historical")) {
            throw new InvalidGenreException("This genre is not valid. Valid genres are: Novel, Dystopian, Romance, Fantasy, Adventure, Historical.");
        }
        this.price = price;
        this.genre = genre;
    }

    // 3. Getters and Setters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(int price) throws InvalidPriceException {
        if (price <= 0) {
            throw new InvalidPriceException("Price must be greater than 0. Provided price: " + price);
        }
        this.price = price;
    }

    public void setGenre(String genre) throws InvalidGenreException {
        if (!genre.equalsIgnoreCase("Novel") && !genre.equalsIgnoreCase("Dystopian") && !genre.equalsIgnoreCase("Romance") && !genre.equalsIgnoreCase("Fantasy") && !genre.equalsIgnoreCase("Adventure") && !genre.equalsIgnoreCase("Historical")) {
            throw new InvalidGenreException("This genre is not valid. Valid genres are: Novel, Dystopian, Romance, Fantasy, Adventure, Historical.");
        }
        this.genre = genre;
    }

    // 4. Methods
    public String getDetails() {
        return "Title: " + title + ", Author: " + author + ", Year: " + year + ", Price: " + price + ", Genre: " + genre + ", Publisher: " + publisher + ", ISBN: " + isbn + ", Pages: " + pages + ", Language: " + language;
    }

}
