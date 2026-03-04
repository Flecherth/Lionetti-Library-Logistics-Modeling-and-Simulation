import java.util.*;
public class Library {
    public List<Shelf> sortedShelves;
    //List of First In First Shelved Shelves but the variable name used is FIFO (First in First Out) to prevent accidental misspellings
    public List<Shelf> FIFOShelves;
    public List<Book> books;
    //Array of carts to sort and shelve (will most likely expand the size to 100 for M3)
    public Cart[] carts;
    static int numOfCarts = 0;
    private int numOfShelves;
    public Metrics LibraryMetrics = new Metrics();
    public int currentFIFOShelf;
    //Used to keep track of FIFS Index
    public int booksOnShelf;

    Library() {
        //Creates an array list of books
        books = new ArrayList<>();
        //Filling the list of books with 100-1300 books
        for (int i = 0; i < (int) ((Math.random() * 1201) + 100); i++) {
            books.add(new Book());
        }
        //Sorting these books as the simulation is only calculating the sorting and shelving of carts and not the entire library
        books.sort(Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle));
        //A list of shelves for storing sorted books
        sortedShelves = new ArrayList<>();
        //A list of shelves for storing sorted books
        FIFOShelves = new ArrayList<>();
        //Creating a variable to hold the number of shelves (Minimum 2 and a maximum of 26)
        numOfShelves = (books.size() / 50);
        //The average span of the shelf
        int shelfSize = 26 / numOfShelves;
        int remainder = 26 % numOfShelves;
        int currentStart = 0;
        //Generate shelves including ID and range by calling their constructors
        for (int j = 0; j < numOfShelves; j++) {
            //Integer which helps calculate shelf span with the inclusion of the remainder
            int size = shelfSize;
            if (j < remainder) {
                size++;
            }
            //SortedShelves need an ID as well as a range (the range is calculated by splitting the alphabet into sections based on numOfShelves)
            sortedShelves.add(new Shelf(j + 1, currentStart, currentStart + size - 1));
            //Increasing currentStart by shelfSize to get the startingRange for the next shelf
            currentStart += size;
            //FIFOShelves don't need a range so the other constructor is called
            FIFOShelves.add(new Shelf(j + 1));
        }
        fillShelves();
        carts = new Cart[10];
    }

    void fillShelves() {
        //Keeps track of the current sorted Shelf
        int currentID = 0;
        //Keeps track of the # of books on the current FIFS Shelf
        booksOnShelf = 0;
        //Keeps track of the # of books on the current Sorted Shelf
        int sortedBooksOnShelf = 0;
        //Keeps track of the current FIFS Shelf
        currentFIFOShelf = 0;
        //For every book in the library
        for (Book book : books) {
            //Placing each book on the shelf
            if(booksOnShelf < Shelf.shelfSize){
                FIFOShelves.get(currentFIFOShelf).Books[booksOnShelf] = book;
                booksOnShelf++;
            }
            //Resets the checker for FIFOShelves when the shelf would become full
            else{
                booksOnShelf -= Shelf.shelfSize;
                currentFIFOShelf++;
            }
            //Goes to the target shelf of the given book (but as the list of books is presorted this iterates through each shelf)
            if (currentID < book.setDestination(book,numOfShelves)){
                //Sets books on shelf back to 0 as the next shelf is going to be counted
                sortedBooksOnShelf = 0;
                //Updating the ID to get to the next shelf
                currentID++;
            }
            //Shifts shelves if an overflow would happen
            if (sortedBooksOnShelf >= Shelf.shelfSize){
                shiftShelves(sortedShelves.get(currentID + 1), book);
            }
            //Adds the book to the sorted shelf
            sortedShelves.get(currentID).Books[sortedBooksOnShelf] = book;
            sortedBooksOnShelf++;
            }
        }

    //Overflow method which deals with situations where a shelf is full by lowering the starting range of the next shelf by 1 adding the previous last book and moving books right
    void shiftShelves(Shelf s, Book b) {
        //Changes the range of the shelf to include one prior letter in the range
        s.setStartingRange(s.getStartingRange() - 1);
        //Places the book in the first position on the shelf and ends the method if there was already a space
        if(s.Books[0] == null){
            s.Books[0] = b;
            return;
        }
        //Integer for keeping track of where the empty index is
        int emptyIndex = 0;
        //Check every spot on the shelf for an empty space
        for(int i = 0; i < s.Books.length; i++){
            if (s.Books[i] == null){
                emptyIndex = i;
                break;
            }
            //Shifts shelves further to the right if no empty space was found needs a handler for if there are no extra shelves
            else if (i == s.Books.length - 1 && s.getID()+1 < sortedShelves.size()){
                shiftShelves(sortedShelves.get(s.getID()+1),s.Books[i]);
            }
        }
        //Book that is currently being held to put on the shelf (physically speaking)
        Book heldBook = b;
        //Next book on the shelf which needs to be stored to prevent it from being overwritten
        Book nextBook;
        for(int i = 0; i < emptyIndex; i++){
            nextBook = s.Books[i];
            s.Books[i] = heldBook;
            heldBook = nextBook;
        }
    }


    //Finds the overall utilization percentage of all shelves in the library (not included in initial diagram)
    public double getOverallUtilizationPercentage(List<Shelf> s){
        double utilizationPercentage = 0;
        //Variable to count non-empty shelves for utilization calculation
        int nonEmptyShelves = 0;
        //Iterates through each shelf in the library calculating their usage
        for (Shelf shelf : s){
            //Checking if the shelf is empty as empty shelves will not be included in the calculation
            if(!shelf.isEmpty()) {
                utilizationPercentage += shelf.calculateShelfUsage(shelf);
                nonEmptyShelves++;
            }
        }
        return utilizationPercentage / nonEmptyShelves;
    }

    //Runs through the library checking for misplaced books and returns the # of misplaced books (will optimize in future implementations)
    public int findMisplacedBooks() {
        int numOfMisplacedBooks = 0;
        for (Shelf shelf : sortedShelves) {
            for (int j = 0; j < shelf.Books.length; j++) {
                if (shelf.Books[j].isMisplaced(shelf.Books, j)) {
                    numOfMisplacedBooks++;
                }
            }
        }
        return numOfMisplacedBooks;
    }
}