import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        Library myLibrary = new Library();
        //Introducing a scanner object so that user inputs can be read later
        Scanner scan = new Scanner(System.in);
        int choice;
        //Menu Loop that runs until the user chooses option 10
        while (true) {
            //Menu Text
            System.out.print("\n\n---------MAIN MENU---------\n1 - Generate Cart \n2 - Sort Cart Using Library Sort\n3 - Sort Cart Using Insertion Sort\n4 - Start Shelving Using FIFO\n5 - Shelve the Cart using Sequential Shelving\n6 - Shelve the Cart Using ACO\n7 - Find Misplaced Books\n8 - Calculate Shelf Usage\n9 - Print Results\n10 - Exit program\n\nEnter option number:");
            //Reading user's choice
            choice = scan.nextInt();
            scan.nextLine();
            //Menu options
            switch (choice) {
                //Generates a cart of books when option 1 is selected
                case 1:
                    myLibrary.addCart();
                    break;
                //Sorts a cart using Library Sort when option 2 is selected
                case 2:
                    //LibrarySort;
                    break;
                //Sorts a cart using regular insertion sort when option 3 is selected
                case 3:
                    //InsertionSort; (not yet implemented)
                    break;
                //Shelve using the cart generated with option 1 using the FIFO method when option 4 is selected
                case 4:

                    break;
                //Shelves the sorted cart using Sequential Shelving when ACO is selected
                case 5:

                    break;
                //Shelves the sorted cart using ACO when option 6 is selected (not yet implemented)
                case 6:
                    //ACO.generateShelvingSequence();
                    break;
                //Finds misplaced books, counts them, and adds them to a cart when option 7 is selected
                case 7:
                    break;
                //Calculates shelf usage and records it when option 8 is selected
                case 8:

                    break;
                //Prints the results of this run when option 9 is selected
                case 9:
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
