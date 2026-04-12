import java.util.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;



public class Driver {
    public static void main(String[] args) {
        //Introducing a scanner object so that user inputs can be read later
        Scanner scan = new Scanner(System.in);
        System.out.println("Please state the purpose of this run: ");
        String purpose = scan.nextLine();
        System.out.println("Please state the filename of this run: ");
        String filename = scan.nextLine();
        System.out.println("Please input shelf size of size 50 or greater: ");
        int chosenShelfSize = scan.nextInt();
        if (chosenShelfSize < 50) {
            System.out.println("Please restart the program and give a valid input for shelfSize");
            scan.close();
            System.exit(0);
        }
        scan.nextLine();
        System.out.println("Please input number of shelves (at least 1): ");
        int chosenNumOfShelves = scan.nextInt();
        if (chosenNumOfShelves < 1) {
            System.out.println("Please restart the program and give a valid input for number of shelves");
            scan.close();
            System.exit(0);
        }
        scan.nextLine();
        System.out.println("Please input number of carts: ");
        int chosenNumberOfCarts = scan.nextInt();
        if (chosenNumberOfCarts == 0) {
            System.out.println("Please restart the program and give a valid input for number of carts");
            scan.close();
            System.exit(0);
        }
        scan.nextLine();
        System.out.println("Please input cart size (Cart size must be at least 10 and the product of cart size and number of carts must not be equal to half of number of shelves times shelf size): ");
        int chosenCartSize = scan.nextInt();
        //Prevents overfilling
        if (chosenCartSize < 10 || ((chosenCartSize * chosenNumberOfCarts) + (chosenShelfSize * chosenNumOfShelves / 2) >= (chosenNumOfShelves * chosenShelfSize))) {
            System.out.println("Please restart the program and give a valid input for cartSize");
            scan.close();
            System.exit(0);
        }
        scan.nextLine();
        System.out.println("Shelf size: " + chosenShelfSize);
        System.out.println("Number of shelves: " + chosenNumOfShelves);
        System.out.println("Number of carts: " + chosenNumberOfCarts);
        System.out.println("Cart size: " + chosenCartSize);
        //Starting checking duration here to prevent userInput from messing up timestamps
        long runTime = System.currentTimeMillis();
        Library controlLibrary = new Library(chosenShelfSize, chosenNumOfShelves, chosenNumberOfCarts, chosenCartSize);
        Library FIFSLibrary = new Library(controlLibrary);
        Library insertionBinaryInsertionLibrary = new Library(controlLibrary);
        Library insertionSequentialShelvingLibrary = new Library(controlLibrary);
        Library insertionLibrarySortLibrary = new Library(controlLibrary);
        Library insertionACOLibrary = new Library(controlLibrary);
        Library mergeBinaryInsertionLibrary = new Library(controlLibrary);
        Library mergeSequentialShelvingLibrary = new Library(controlLibrary);
        Library mergeLibrarySortLibrary = new Library(controlLibrary);
        Library mergeACOLibrary = new Library(controlLibrary);
        ShelvingAlgorithm ACO = new ACO();
        ShelvingAlgorithm FIFS = new FIFS();
        ShelvingAlgorithm BinaryInsertionSort = new BinaryInsertionSort();
        ShelvingAlgorithm librarySort = new LibrarySort();
        ShelvingAlgorithm SequentialShelving = new SequentialShelving();
        SortingAlgorithm mergeSort = new MergeSort();
        SortingAlgorithm insertionSort = new InsertionSort();
        //FIFS does not sort so sorting time will be set to 0
        FIFSLibrary.LibraryMetrics.setSortingTime(0);
        FIFS.generateShelvingSequence(FIFSLibrary.carts, FIFSLibrary.FIFOShelves, FIFSLibrary);
        FIFSLibrary.LibraryMetrics.setShelfUsage(FIFSLibrary.getOverallUtilizationPercentage(FIFSLibrary.FIFOShelves));
        //Sorting and shelving utilizing insertion sort for carts and Binary Insertion for the shelves
        insertionSort.sortingSequence(insertionBinaryInsertionLibrary);
        BinaryInsertionSort.generateShelvingSequence(insertionBinaryInsertionLibrary.carts, insertionBinaryInsertionLibrary.sortedShelves, insertionBinaryInsertionLibrary);
        insertionBinaryInsertionLibrary.LibraryMetrics.setShelfUsage(insertionBinaryInsertionLibrary.getOverallUtilizationPercentage(insertionBinaryInsertionLibrary.sortedShelves));
        insertionBinaryInsertionLibrary.LibraryMetrics.setNumOfMisplacedBooks(insertionBinaryInsertionLibrary.findMisplacedBooks(true));
        System.out.println("Done with Binary");
        //Sorting and shelving utilizing insertion sort for carts and Sequential Shelving for the shelves
        insertionSort.sortingSequence(insertionSequentialShelvingLibrary);
        SequentialShelving.generateShelvingSequence(insertionSequentialShelvingLibrary.carts, insertionSequentialShelvingLibrary.sortedShelves, insertionSequentialShelvingLibrary);
        insertionSequentialShelvingLibrary.LibraryMetrics.setShelfUsage(insertionSequentialShelvingLibrary.getOverallUtilizationPercentage(insertionSequentialShelvingLibrary.sortedShelves));
        insertionSequentialShelvingLibrary.LibraryMetrics.setNumOfMisplacedBooks(insertionSequentialShelvingLibrary.findMisplacedBooks(true));
        //Sorting and shelving utilizing insertion sort for carts and Library Sort for the shelves
        insertionSort.sortingSequence(insertionLibrarySortLibrary);
        librarySort.generateShelvingSequence(insertionLibrarySortLibrary.carts, insertionLibrarySortLibrary.librarySortedShelves, insertionLibrarySortLibrary);
        insertionLibrarySortLibrary.LibraryMetrics.setShelfUsage(insertionLibrarySortLibrary.getOverallUtilizationPercentage(insertionLibrarySortLibrary.librarySortedShelves));
        //MisplacedBooks is slightly more complicated with library sort (needs to check bigger shelfs but can still be misplaced depending on -2 -1 +1 and +2 books
        insertionLibrarySortLibrary.LibraryMetrics.setNumOfMisplacedBooks(insertionLibrarySortLibrary.findMisplacedBooks(false));
        //Sorting and shelving utilizing insertion sort for carts and ACO for the shelves
        insertionSort.sortingSequence(insertionACOLibrary);
        ACO.generateShelvingSequence(insertionACOLibrary.carts, insertionACOLibrary.sortedShelves, insertionACOLibrary);
        insertionACOLibrary.LibraryMetrics.setShelfUsage(insertionACOLibrary.getOverallUtilizationPercentage(insertionACOLibrary.sortedShelves));
        insertionACOLibrary.LibraryMetrics.setNumOfMisplacedBooks(insertionACOLibrary.findMisplacedBooks(true));
        //Sorting and shelving utilizing merge sort for carts and Binary Insertion for the shelves
        mergeSort.sortingSequence(mergeBinaryInsertionLibrary);
        BinaryInsertionSort.generateShelvingSequence(mergeBinaryInsertionLibrary.carts, mergeBinaryInsertionLibrary.sortedShelves, mergeBinaryInsertionLibrary);
        mergeBinaryInsertionLibrary.LibraryMetrics.setShelfUsage(mergeBinaryInsertionLibrary.getOverallUtilizationPercentage(mergeBinaryInsertionLibrary.sortedShelves));
        mergeBinaryInsertionLibrary.LibraryMetrics.setNumOfMisplacedBooks(mergeBinaryInsertionLibrary.findMisplacedBooks(true));
        //Sorting and shelving utilizing merge sort for carts and Sequential Shelving for the shelves
        mergeSort.sortingSequence(mergeSequentialShelvingLibrary);
        SequentialShelving.generateShelvingSequence(mergeSequentialShelvingLibrary.carts, mergeSequentialShelvingLibrary.sortedShelves, mergeSequentialShelvingLibrary);
        mergeSequentialShelvingLibrary.LibraryMetrics.setShelfUsage(mergeSequentialShelvingLibrary.getOverallUtilizationPercentage(mergeSequentialShelvingLibrary.sortedShelves));
        mergeSequentialShelvingLibrary.LibraryMetrics.setNumOfMisplacedBooks(mergeSequentialShelvingLibrary.findMisplacedBooks(true));
        //Sorting and shelving utilizing merge sort for carts and Library Sort for the shelves
        mergeSort.sortingSequence(mergeLibrarySortLibrary);
        librarySort.generateShelvingSequence(mergeLibrarySortLibrary.carts, mergeLibrarySortLibrary.librarySortedShelves, mergeLibrarySortLibrary);
        mergeLibrarySortLibrary.LibraryMetrics.setShelfUsage(mergeLibrarySortLibrary.getOverallUtilizationPercentage(mergeLibrarySortLibrary.librarySortedShelves));
        mergeLibrarySortLibrary.LibraryMetrics.setNumOfMisplacedBooks(mergeLibrarySortLibrary.findMisplacedBooks(false));
        //Sorting and shelving utilizing merge sort for carts and ACO for the shelves
        mergeSort.sortingSequence(mergeACOLibrary);
        ACO.generateShelvingSequence(mergeACOLibrary.carts, mergeACOLibrary.sortedShelves, mergeACOLibrary);
        mergeACOLibrary.LibraryMetrics.setShelfUsage(mergeACOLibrary.getOverallUtilizationPercentage(mergeACOLibrary.sortedShelves));
        mergeACOLibrary.LibraryMetrics.setNumOfMisplacedBooks(mergeACOLibrary.findMisplacedBooks(true));
        List<Library> libraries = Arrays.asList(controlLibrary, FIFSLibrary, insertionBinaryInsertionLibrary, insertionSequentialShelvingLibrary, insertionLibrarySortLibrary, insertionACOLibrary, mergeBinaryInsertionLibrary, mergeSequentialShelvingLibrary, mergeLibrarySortLibrary, mergeACOLibrary);
        List<String> libraryNames = Arrays.asList("controlLibrary", "FIFSLibrary", "insertionBinaryInsertionLibrary", "insertionSequentialShelvingLibrary", "insertionLibrarySortLibrary", "insertionACOLibrary", "mergeBinaryInsertionLibrary", "mergeSequentialShelvingLibrary", "mergeLibrarySortLibrary", "mergeACOLibrary");
        controlLibrary.LibraryMetrics.setNumOfMisplacedBooks(controlLibrary.findMisplacedBooks(true));
        controlLibrary.LibraryMetrics.setShelfUsage(controlLibrary.getOverallUtilizationPercentage(controlLibrary.sortedShelves));
        exportRunSummary(libraries, libraryNames, chosenShelfSize, chosenNumOfShelves, chosenNumberOfCarts, purpose, filename, runTime, chosenCartSize);
    }

    public static void exportRunSummary(List<Library> libraries, List<String> libraryNames,
                                        int shelfSize, int numOfShelves, int numOfCarts,
                                        String purpose, String fileName, long runTime, int cartSize) {

        long totalRunTime = System.currentTimeMillis() - runTime;

        // Ensure file ends with .csv
        if (!fileName.endsWith(".csv")) {
            fileName += ".csv";
        }

        try (PrintWriter writer = new PrintWriter(fileName)) {

            // Metadata (each as its own row)
            writer.println("Purpose of Run," + purpose);
            writer.println("Total Run Time (ms)," + totalRunTime);
            writer.println("Shelf Size," + shelfSize);
            writer.println("Number of Shelves," + numOfShelves);
            writer.println("Number of Carts," + numOfCarts);
            writer.println("Cart Size," + cartSize);
            writer.println(); // blank line

            // Column Headers
            writer.println("LibraryName,SortingTimeMs,ShelvingTimeMs,ShelfUsage%,TotalDistanceTraveled(Meters),NumOfMisplacedBooks,MisplacedBooksRuntime");

            // Data Rows
            for (int i = 0; i < libraries.size(); i++) {
                Library lib = libraries.get(i);
                String libraryName = libraryNames.get(i);

                writer.println(
                        libraryName + "," +
                                lib.LibraryMetrics.getSortingTime() + "," +
                                lib.LibraryMetrics.getShelvingTime() + "," +
                                lib.LibraryMetrics.getShelfUsage() + "," +
                                lib.LibraryMetrics.getTotalDistanceTraveled() + "," +
                                lib.LibraryMetrics.getNumOfMisplacedBooks() + "," +
                                lib.LibraryMetrics.getMisplacedBooksRuntime()
                );
            }

            System.out.println("Export complete! File saved as: " + fileName);

        } catch (FileNotFoundException e) {
            System.out.println("Error exporting file.");
            e.printStackTrace();
        }
    }
}
