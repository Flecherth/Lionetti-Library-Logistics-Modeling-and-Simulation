import java.util.*;
public class Shelf {
    //Identifies the shelf
    private int id;
    //Alphabetical start of the range for the shelves as an integer (0=A and Z=25)
    private int startingRange;
    //Alphabetical end of the range for the shelves as an integer (0=A and Z=25)
    private int endRange;
    //Shelfs can hold 60 books
    static int shelfSize = 60;
    //An array of books for the shelf to hold (Should it be a list?)
    static Book[] Books = new Book[60];

    //FIFO Shelf Constructor (doesn't utilize range as shelves aren't sorted alphabetically)
    Shelf(int id){
        this.id = id;
        Book[] Books = new Book[60];
    }

    //Normal Shelf constructor (utilizes range to shelve alphabetically)
    Shelf(int id, int startingRange, int endRange){
        this.id = id;
        this.startingRange = startingRange;
        this.endRange = endRange;
        Book[] Books = new Book[60];
    }



    //Method to insert a book into the shelf
    static void insertBook(Book b){
        for (int i = 0; i<Books.length; i++){
            //Needs a handler for overflow as well as moving the books to the right of the insertion
            if (Books[i-1].getAuthor().compareTo(b.getAuthor()) < 0 && (b.getAuthor().compareTo(Books[i].getAuthor()) > 0)){
                Books[i] = b;
            }
        }
    }

    //Method to remove and return a book from the shelf given an index
    static Book removeBook(int b){
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
    static boolean isNotFull(){
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
        for (int i = 0; i<60; i++){
            if(s.Books[i] != null){
                filledSlots++;
            }
        }
        return ((filledSlots * 100) / 60);
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

}
