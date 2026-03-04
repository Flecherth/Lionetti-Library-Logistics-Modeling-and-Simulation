import java.util.*;
public class Shelf {
    //Identifies the shelf
    private int id;
    //Alphabetical start of the range for the shelves as an integer (0=A and Z=25)
    private int startingRange;
    //Alphabetical end of the range for the shelves as an integer (0=A and Z=25)
    private int endRange;
    //Shelves can hold 300 books
    static int shelfSize = 300;
    //An array of books for the shelf to hold (Should it be a list?)
    public Book[] Books = new Book[300];

    //FIFO Shelf Constructor (doesn't utilize range as shelves aren't sorted alphabetically)
    Shelf(int id){
        this.id = id;
        Book[] Books = new Book[300];
        this.shelfSize = Books.length;
    }

    //Normal Shelf constructor (utilizes range to shelve alphabetically)
    Shelf(int id, int startingRange, int endRange){
        this.id = id;
        this.startingRange = startingRange;
        this.endRange = endRange;
        Book[] Books = new Book[300];
        this.shelfSize = Books.length;
    }


    //Might want to change method of sorting to Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle
    //Method to insert a book into the shelf
    public void insertBook(Book b){
        for (int i = 0; i<Books.length-1; i++){
            //Needs a handler for overflow and title as well as moving the books to the right of the insertion
            if (Books[i].getAuthor().compareTo(b.getAuthor()) < 0 && (b.getAuthor().compareTo(Books[i+1].getAuthor()) > 0)){
                //Book that is currently being held to put on the shelf (physically speaking)
                Book heldBook = b;
                //Next book on the shelf which needs to be stored to prevent it from being overwritten
                Book nextBook;
                for(int j = i; j < Books.length; j++){
                    //Only shifts book to the right when there is a book to move
                    if(Books[j] != null){
                        nextBook = Books[j];
                        Books[j] = heldBook;
                        heldBook = nextBook;
                    }
                }
                return;
            }
        }
    }

    //Method to remove and return a book from the shelf given an index
    public Book removeBook(int b){
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
        return ((filledSlots * 100) / 300);
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
