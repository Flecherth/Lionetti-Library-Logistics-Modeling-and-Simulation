import java.util.*;
public class Book {
    private String title;
    private String author;

    Book(){
    //Randomizer object for creating a title and author for these books
    Random r = new Random();
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //Sets title to a random 3 letter combination
    this.title = "" + alphabet.charAt(r.nextInt(26)) + alphabet.charAt(r.nextInt(26)) + alphabet.charAt(r.nextInt(26));
    //Sets author to a random 3 letter combination
    this.author = "" + alphabet.charAt(r.nextInt(26)) + alphabet.charAt(r.nextInt(26)) + alphabet.charAt(r.nextInt(26));
    }
    //Copy constructor
    public Book(Book other) {
        this.author = other.author;
        this.title = other.title;
        // copy all fields
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }


    //Checks prior and next book on the shelf to see if the book is misplaced
    boolean isMisplaced(Book[] books, int i, boolean route){
        Comparator<Book> comp = Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle);
        Book current = books[i];
        if (current == null) {
            return false;
        }
        if (route) {
            //Check left neighbor
            if (i > 0 && books[i - 1] != null) {
                if (comp.compare(books[i - 1], current) > 0) {
                    return true;
                }
            }
            //Check right neighbor
            if (i < books.length - 1 && books[i + 1] != null) {
                if (comp.compare(current, books[i + 1]) > 0) {
                    return true;
                }
            }
        } else {
            //Check left (2-step)
            if (i > 1 && books[i - 2] != null) {
                if (comp.compare(books[i - 2], current) > 0) {
                    return true;
                }
            }
            //Check right (2-step)
            if (i < books.length - 2 && books[i + 2] != null) {
                if (comp.compare(current, books[i + 2]) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    //Setting destination to a new shelf by returning an integer to go to the id based on the first letter of the author's last name
    public static int setDestination(Book currentBook, int numOfShelves){
        char firstChar = Character.toUpperCase(currentBook.getAuthor().charAt(0));
        //Getting the index by subtracting the ascii value of the first letter of the author name by the ascii value of A dividing it by # of shelves and then 26
        return ((firstChar - 'A') * (numOfShelves) /26);
    }
}
