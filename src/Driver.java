import java.util.Comparator;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        boolean cartsInitialized = false;
        boolean FIFOShelving = false;
        Library myLibrary = new Library();
        ShelvingAlgorithm FIFS = new FIFS();
        ShelvingAlgorithm BinaryInsertionSort = new BinaryInsertionSort();
        ShelvingAlgorithm SequentialShelving = new SequentialShelving();
        //Introducing a scanner object so that user inputs can be read later
        Scanner scan = new Scanner(System.in);
        int choice;
        //Menu Loop that runs until the user chooses option 10
        while (true) {
            //Menu Text
            System.out.print("\n\n---------MAIN MENU---------\n1 - Generate Cart \n2 - Sort Cart Using Merge Sort\n3 - Sort Cart Using Insertion Sort\n4 - Start Shelving Using FIFO\n5 - Shelve the Cart using Sequential Shelving\n6 - Shelve the Cart Using ACO\n7 - Find Misplaced Books\n8 - Calculate Shelf Usage\n9 - Print Results\n10 - Exit program\n\nEnter option number:");
            //Reading user's choice
            choice = scan.nextInt();
            scan.nextLine();
            //Menu options
            switch (choice) {
                //Generates the array of carts of books when option 1 is selected
                case 1:
                    if (myLibrary.numOfCarts < myLibrary.carts.length){
                        for (int i = 0; i< myLibrary.carts.length; i++){
                            myLibrary.carts[i] = new Cart();
                            myLibrary.numOfCarts++;

                        }
                    } else {
                        System.out.println("\nToo many carts.");
                    }
                    break;
                //Sorts carts using Merge Sort when option 2 is selected (Chose to change from Library Sort to Merge Sort for sorting carts as it is more accurate to the method I shelve in real life
                case 2:

                    break;
                //Sorts a cart using regular insertion sort when option 3 is selected
                case 3:
                    if (!cartsInitialized){
                        System.out.println("Please initialize carts first and try again");
                        break;
                    }
                    for (Cart cart : myLibrary.carts){
                        for (int i = 1; i < cart.books.length; i++){
                            Book key = cart.books[i];
                            //Prevents NullPointerExceptions
                            if (key == null){
                                continue;
                            }
                            int comparedIndex = i - 1;
                            while (comparedIndex >= 0 && Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle).compare(key, cart.books[comparedIndex]) > 0){
                                cart.books[comparedIndex + 1] = cart.books[comparedIndex];
                                comparedIndex--;
                            }
                            cart.books[comparedIndex + 1] = key;
                        }
                        System.out.println("\nSuccessful Loop");
                    }
                    break;
                //Shelve using the cart generated with option 1 using the FIFO method when option 4 is selected
                case 4:
                    for (Cart cart : myLibrary.carts) {
                        FIFS.generateShelvingSequence(cart, myLibrary.FIFOShelves, myLibrary);
                    }
                    FIFOShelving = true;
                    break;
                //Shelves the sorted cart using Sequential Shelving when option 5 is selected
                case 5:
                    for (Cart cart : myLibrary.carts) {
                        BinaryInsertionSort.generateShelvingSequence(cart, myLibrary.sortedShelves, myLibrary);
                    }
                    break;
                //Shelves the sorted cart using ACO when option 6 is selected (not yet implemented)
                case 6:
                    //ACO.generateShelvingSequence();
                    break;
                //Finds misplaced books, counts them, and adds them to a cart when option 7 is selected
                case 7:
                    //Function needs an overhaul
                    //findMisplacedBooks()
                    break;
                //Calculates shelf usage and records it when option 8 is selected
                case 8:
                    if (FIFOShelving){
                        myLibrary.LibraryMetrics.setShelfUsage(myLibrary.getOverallUtilizationPercentage(myLibrary.FIFOShelves));
                    } else {
                        myLibrary.LibraryMetrics.setShelfUsage(myLibrary.getOverallUtilizationPercentage(myLibrary.sortedShelves));
                    }
                    break;
                //Prints the results of this run when option 9 is selected
                case 9:
                    myLibrary.LibraryMetrics.printResults(myLibrary);
                    break;
                //Shuts the program down when option 10 is selected
                case 10:
                    scan.close();
                    System.out.println("\nShutting down...");
                    System.exit(0);
                    break;
            }
        }
    }
}
