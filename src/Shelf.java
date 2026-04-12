import java.util.*;
public class Shelf {
    //Identifies the shelf
    private int id;
    //Alphabetical start of the range for the shelves as an integer (0=A and Z=25)
    private int startingRange;
    //Alphabetical end of the range for the shelves as an integer (0=A and Z=25)
    private int endRange;
    //Shelves can hold this many books
    public int shelfSize;
    //An array of books for the shelf to hold (Should it be a list?)
    public Book[] Books;

    //FIFO Shelf Constructor (doesn't utilize range as shelves aren't sorted alphabetically)
    Shelf(int id, int inputtedShelfSize){
        this.id = id;
        this.shelfSize = inputtedShelfSize;
        this.Books = new Book[shelfSize];
    }

    //Normal Shelf constructor (utilizes range to shelve alphabetically)
    Shelf(int id, int startingRange, int endRange, int inputtedShelfSize){
        this.id = id;
        this.startingRange = startingRange;
        this.endRange = endRange;
        this.shelfSize = inputtedShelfSize;
        this.Books = new Book[shelfSize];
    }

    //Copy constructor
    public Shelf(Shelf other) {
        this.shelfSize = other.shelfSize;
        this.id = other.id;
        this.startingRange = other.startingRange;
        this.endRange = other.endRange;
        this.Books = new Book[other.Books.length];
        for (int i = 0; i < other.Books.length; i++) {
            this.Books[i] = other.Books[i];
        }
    }

    //Might want to change method of sorting to Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle
    //Method to insert a book into the shelf
    public void insertBook(Book b) {
        if (b == null) return;
        Comparator<Book> comp = Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle);
        //Find insertion index
        int insertIndex = 0;
        while (insertIndex < Books.length &&
                Books[insertIndex] != null &&
                comp.compare(Books[insertIndex], b) < 0) {
            insertIndex++;
        }
        //If shelf is full do nothing (or handle overflow)
        if (insertIndex >= Books.length) return;
        //Shift right to make space
        for (int i = Books.length - 1; i > insertIndex; i--) {
            Books[i] = Books[i - 1];
        }
        //Insert book
        Books[insertIndex] = b;
    }

    //Method to remove and return a book from the shelf given an index
    public Book removeBook(int b){
        Book removedBook = Books[b];
        for (int i = b; i < Books.length - 1; i++) {
            Books[i] = Books[i + 1];
        }
        Books[Books.length - 1] = null;
        return removedBook;
    }

    public Book removeLibrarySortBook(int b){
        Book removedBook = Books[b];
        Books[b] = null;
        return removedBook;
    }

    //Function that gets distance to the shelf from current position
    int getDistanceTo(Shelf s){
        //Sample distance calculation
        return (s.id- this.id) * 5;
    }

    //New method to determine if the shelf has an empty space for insertion
    public boolean isNotFull(){
        for (Book book : Books) {
            if (book == null) {
                return true;
            }
        }
        return false;
    }

    boolean isEmpty(){
        for (Book book : Books) {
            if (book != null) {
                return false;
            }
        }
        return true;
    }


    //Pivoted and moved the method from the Metrics class to the Shelf class
    //Currently calculating shelf usage by adding % of utilization of each shelf that currently holds books (does not include empty shelves) and divides them by # of occupied shelves
    //Calculates shelf usage of a single shelf
    int calculateShelfUsage(Shelf s){
        int filledSlots = 0;
        for (int i = 0; i<Books.length; i++){
            if(s.Books[i] != null){
                filledSlots++;
            }
        }
        return ((filledSlots * 100) / shelfSize);
    }

    public void setStartingRange(int startingRange) {
        this.startingRange = startingRange;
    }

    public int getStartingRange() {
        return startingRange;
    }

    public int getEndRange() {
        return endRange;
    }

    public int getID() {
        return id;
    }
}
