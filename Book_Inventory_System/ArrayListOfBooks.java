
import java.util.ArrayList;

public class ArrayListOfBooks {

    public static void main(String[] args) throws InvalidGenreException, InvalidPriceException {
        // 1. Create an ArrayList to store Book objects
        ArrayList<Book> bookList = new ArrayList<>();

        bookList.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 10, "Novella", "Scribner", "978-0743273565", 180, "English"));
        bookList.add(new Book("To Kill a Mockingbird", "Harper Lee", 1960, 15, "Novel", "J.B. Lippincott & Co.", "978-0061120084", 281, "English"));
        bookList.add(new Book("1984", "George Orwell", 1949, 12, "Dystopian", "Secker & Warburg", "978-0451524935", 328, "English"));
        bookList.add(new Book("Pride and Prejudice", "Jane Austen", 1813, 8, "Romance", "T. Egerton", "978-1503290563", 279, "English"));
        bookList.add(new Book("The Hobbit", "J.R.R. Tolkien", 1937, 14, "Fantasy", "George Allen & Unwin", "978-0547928227", 310, "English"));
        bookList.add(new Book("Moby Dick", "Herman Melville", 1851, 18, "Adventure", "Harper & Brothers", "978-1503280786", 635, "English"));
        bookList.add(new Book("War and Peace", "Leo Tolstoy", 1869, 20, "Historical", "The Russian Messenger", "978-1400079988", 1225, "Russian"));
        bookList.add(new Book("The Catcher in the Rye", "J.D. Salinger", 1951, 10, "Novel", "Little, Brown and Company", "978-0316769488", 214, "English"));
        bookList.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, 25, "Fantasy", "George Allen & Unwin", "978-0544003415", 1178, "English"));
        bookList.add(new Book("The Alchemist", "Paulo Coelho", 1988, 12, "Adventure", "HarperOne", "978-0061122415", 208, "Portuguese"));

        // 2. Display details of all books
        System.out.println();
        for (Book b : bookList) {
            System.out.println("Title: " + b.getTitle());
            System.out.println("Author: " + b.getAuthor());
            System.out.println("Year: " + b.getYear());
            System.out.println("Price: $" + b.price);
            System.out.println("Genre: " + b.genre);
            System.out.println("Publisher: " + b.publisher);
            System.out.println("ISBN: " + b.isbn);
            System.out.println("Pages: " + b.pages);
            System.out.println("Language: " + b.language);
            System.out.println();
        }

        // 3. Display titles of all Fantasy books
        System.out.println("Fantasy Books:");
        bookList.forEach(b -> {
            if (b.genre.equals("Fantasy")) {
                System.out.println(b.getTitle());
            }
        });

        // 4. Calculate and display total price of all English books
        System.out.println();
        int totalPrice = bookList.stream()
                .filter(b -> b.language.equals("English"))
                .mapToInt(b -> b.price)
                .sum();
        System.out.println("Total price of English books: $" + totalPrice);

        // 5. Handle exceptions for invalid price and genre
        System.out.println();
        Book book = new Book("The Little Prince", "Antoine de Saint-Exupéry", 1943);
        try {
            book.setPrice(10);
            book.setGenre("Novella");
        } catch (InvalidPriceException e) {
            System.out.println("Price Error: " + e.getMessage());
        } catch (InvalidGenreException e) {
            System.out.println("Genre Error: " + e.getMessage());
        }

        // 6. Validate all books in the list
        for (Book b : bookList) {
            try {
                b.setPrice(b.price);  // validate
                b.setGenre(b.genre);  // validate
            } catch (InvalidPriceException | InvalidGenreException e) {
                System.out.println("");
                System.out.println("Error with book " + b.getTitle() + ": " + e.getMessage());
            }
        }

        // 7. Attempt to create a book with invalid price and genre
        System.out.println();
        Book base = new Book("Wuthering Heights", "Emily Brontë", 1847);
        try {
            Book invalidBook = new Book(base, -5, "Fantasy"); // negative price
        } catch (InvalidPriceException e) {
            System.out.println("Price Error: " + e.getMessage());
        } catch (InvalidGenreException e) {
            System.out.println("Genre Error: " + e.getMessage());
        }

        // 8. Calculate and display average price of all books
        System.out.println();
        double avgPrice = bookList.stream()
                .mapToInt(b -> b.price)
                .average()
                .orElse(0);
        System.out.println("Average price: $" + avgPrice);
    }
}
