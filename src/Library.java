import java.util.*;
public class Library {
    private List<Shelf> sortedShelves = new ArrayList<>();
    private List<Shelf> FIFOShelves = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private Cart[] carts = new Cart[10];

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
        int numOfShelves = (books.size() / 50);
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

    }

    void fillShelves() {
        for (int i = 0; i < books.size(); i++) {
            FIFOShelves.get(i).add(books.get(i));
            if(sortedShelves.get(i).getStartingRange() <= books.get(i).setDestination(books.get(i).getAuthor()) &&  books.get(i).setDestination(books.get(i).getAuthor()) <= sortedShelves.get(i).getEndRange()){
                sortedShelves.get(i).add(books.get(i));
            }

        }

    }

    //Overflow method which deals with situations where a shelf is full by lowering the starting range of the next shelf by 1 and moving books right
    void shiftShelves(Shelf s) {
        s.setStartingRange(s.getStartingRange() - 1);
        if (s)
    }

    Cart addCart() {
        return new Cart();
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
    int findMisplacedBooks() {
        int numOfMisplacedBooks = 0;
        for (Shelf shelf : sortedShelves) {
            for (int j = 0; j < Shelf.Books.length; j++) {
                if (Shelf.Books[j].isMisplaced(Shelf.Books, j)) {
                    numOfMisplacedBooks++;
                }
            }
        }
        return numOfMisplacedBooks;
    }
}