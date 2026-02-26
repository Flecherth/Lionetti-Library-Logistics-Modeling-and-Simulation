import java.util.*;
public class Shelf {
    //Identifies the shelf
    private int id;
    //Alphabetical range for the shelves
    private String range;
    //Shelfs can hold 60 books
    static int shelfSize = 60;
    //An array of books for the shelf to hold (Should it be a list?)
    Book[] Books = new Book[60];

    //FIFO Shelf Constructor (doesn't utilize range as shelves aren't sorted alphabetically)
    Shelf(int id){
        this.id = id;
        Book[] Books = new Book[60];
    }

    //Normal Shelf constructor (utilizes range to shelve alphabetically)
    Shelf(int id, String range){
        this.id = id;
        this.range = range;
        Book[] Books = new Book[60];
    }



    //Method to insert a book into the shelf
    static void insertBook(Book b){

    }

    //Method to remove a book from the shelf
    static void removeBook(int b){

    }

    //Function that gets distance to the shelf from current position
    int getDistanceTo(Shelf s){
        int distance;
        return distance;
    }

}
