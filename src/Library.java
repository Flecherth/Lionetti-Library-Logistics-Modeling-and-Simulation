import java.util.*;
public class Library {
    public List<Shelf> sortedShelves;
    //List of First In First Shelved Shelves but the variable name used is FIFO (First in First Out) to prevent accidental misspellings
    public List<Shelf> FIFOShelves;
    public List<Shelf> librarySortedShelves;
    public List<Book> books;
    //Array of carts to sort and shelve (will most likely expand the size to 100 for M3)
    public Cart[] carts;
    public int numOfShelves;
    public Metrics LibraryMetrics = new Metrics();
    public int currentFIFOShelf;
    //Used to keep track of FIFS Index
    public int booksOnShelf;
    //Setting a decent sized upper limit for # of misplaced Books
    private Book[] misplacedBooks = new Book[5000];

    //Copy constructor for running the simulation with the same books that are originally generated
    Library (Library masterLibrary){
        this.books = new ArrayList<>();
        for (Book b : masterLibrary.books) {
            this.books.add(new Book(b)); // requires Book copy constructor
        }
        this.sortedShelves = new ArrayList<>();
        for (Shelf s : masterLibrary.sortedShelves) {
            this.sortedShelves.add(new Shelf(s)); // requires Shelf copy constructor
        }
        this.FIFOShelves = new ArrayList<>();
        for (Shelf s : masterLibrary.FIFOShelves) {
            this.FIFOShelves.add(new Shelf(s));
        }
        this.librarySortedShelves = new ArrayList<>();
        for (Shelf s : masterLibrary.librarySortedShelves) {
            this.librarySortedShelves.add(new Shelf(s));
        }
        this.carts = new Cart[masterLibrary.carts.length];
        for (int i = 0; i < masterLibrary.carts.length; i++) {
            this.carts[i] = new Cart(masterLibrary.carts[i]); // requires Cart copy constructor
        }
        this.numOfShelves = masterLibrary.numOfShelves;
        this.currentFIFOShelf = masterLibrary.currentFIFOShelf;
        this.booksOnShelf = masterLibrary.booksOnShelf;
        this.LibraryMetrics = new Metrics();
        this.misplacedBooks = new Book[5000];
    }
    Library(int booksPerShelf, int inputtedNumOfShelves, int inputtedNumOfCarts, int cartSize) {
        //Creates an array list of books
        books = new ArrayList<>();
        //Filling the list of books with a number of books based on half the capacity of the shelves times the number of shelves
        for (int i = 0; i < ((inputtedNumOfShelves * booksPerShelf) / 2); i++) {
            books.add(new Book());
        }
        //Sorting these books as the simulation is only calculating the sorting and shelving of carts and not the entire library
        books.sort(Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle));
        //A list of shelves for storing sorted books
        sortedShelves = new ArrayList<>();
        //A list of shelves for storing sorted books
        FIFOShelves = new ArrayList<>();
        librarySortedShelves = new ArrayList<>();
        //Creating a variable to hold the number of shelves (Minimum 2 and a maximum of 26)
        numOfShelves = inputtedNumOfShelves;
        //shelfRange is the number of letters that the range of the shelf should cover Ex: range 2 could be A-B
        int shelfRange = 26 / numOfShelves;
        //Remainder is utilized to make sure that every letter is accounted for by adding the remainder to the range of a number of shelves
        int remainder = 26 % numOfShelves;
        int currentStart = 0;
        //Generate shelves including ID and range by calling their constructors
        for (int j = 0; j < numOfShelves; j++) {
            //Integer which helps calculate shelf span with the inclusion of the remainder
            int rangeSize = shelfRange;
            if (j < remainder) {
                rangeSize++;
            }
            //SortedShelves need an ID as well as a range (the range is calculated by splitting the alphabet into sections based on numOfShelves)
            sortedShelves.add(new Shelf(j + 1, currentStart, currentStart + rangeSize - 1, booksPerShelf));
            //Library Sort requires twice the amount of space, so I am doubling the size of the shelves (doubling the num of shelves also works but would need to be implemented differently)
            librarySortedShelves.add(new Shelf(j + 1, currentStart, currentStart + rangeSize - 1, booksPerShelf * 2));
            //Increasing currentStart by shelfSize to get the startingRange for the next shelf
            currentStart += rangeSize;
            //FIFOShelves don't need a range so the other constructor is called
            FIFOShelves.add(new Shelf(j + 1, booksPerShelf));
        }
        fillShelves();
        carts = new Cart[inputtedNumOfCarts];
        for (int p = 0; p < carts.length; p++){
            carts[p] = new Cart(cartSize);
        }
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
            if(booksOnShelf < FIFOShelves.get(currentFIFOShelf).shelfSize){
                FIFOShelves.get(currentFIFOShelf).Books[booksOnShelf] = book;
                booksOnShelf++;
            }
            //Resets the checker for FIFOShelves when the shelf would become full
            else{
                booksOnShelf -= FIFOShelves.get(currentFIFOShelf).shelfSize;
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
            if (sortedBooksOnShelf >= sortedShelves.get(currentID).Books.length) {
                //Try to push to next shelf
                if (currentID + 1 < sortedShelves.size()) {
                    shiftShelves(sortedShelves.get(currentID + 1), book);
                }
                continue;
            }
            //Adds the book to the sorted shelf
            sortedShelves.get(currentID).Books[sortedBooksOnShelf] = book;
            //Filling every other slot in the array
            librarySortedShelves.get(currentID).Books[sortedBooksOnShelf*2] = book;
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

    //Runs through the library checking for misplaced books and returns the # of misplaced books (needs to work for librarySorted shelves)
    public int findMisplacedBooks(boolean route) {
        long startTime = System.currentTimeMillis();
        int numOfMisplacedBooks = 0;
        //If true checks normal sorted shelves otherwise checks librarySortedShelves (no handler for FIFS as there are no misplaced books and the routes are necessary as librarySortedShelves have more books)
        if (route) {
            for (Shelf shelf : sortedShelves) {
                for (int j = 0; j < shelf.Books.length; j++) {
                    if (shelf.Books[j] != null) {
                        if (shelf.Books[j].isMisplaced(shelf.Books, j, route)) {
                            misplacedBooks[numOfMisplacedBooks] = shelf.removeBook(j);
                            numOfMisplacedBooks++;
                            j--;
                        }
                    }
                }
            }
        } else {
            for (Shelf shelf : librarySortedShelves) {
                for (int j = 0; j < shelf.Books.length; j++) {
                    if (shelf.Books[j] != null) {
                        if (shelf.Books[j].isMisplaced(shelf.Books, j, route)) {
                            misplacedBooks[numOfMisplacedBooks] = shelf.removeLibrarySortBook(j);
                            numOfMisplacedBooks++;
                            j--;
                        }
                    }
                }
            }
        }
        LibraryMetrics.setMisplacedBooksRuntime(System.currentTimeMillis() - startTime);
        return numOfMisplacedBooks;
    }
}