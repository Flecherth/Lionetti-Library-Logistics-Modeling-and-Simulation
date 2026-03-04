import java.util.*;

//Method that should perform better than insertion sort when shelving books from sorted carts into sorted shelves
public class BinaryInsertionSort implements ShelvingAlgorithm {
    @Override
    public List<Shelf> generateShelvingSequence(Cart cart, List<Shelf> s, Library L) {
        //Boolean to track when the book has been shelved to move on to the next
        boolean unshelved = true;
        //For every book in the cart
        for (Book book : cart.books){
            int low = 0;
            int high = cart.books.length - 1;
            //Updating distance for the metrics
            L.LibraryMetrics.updateDistance(cart.getCurrentShelf(), book.setDestination(book, s.size()));
            //Updating currentShelf to keep track of distance
            cart.setCurrentShelf(book.setDestination(book, s.size()));
            while (low <= high) {
                //Integer to keep track of the middle of the binary sort
                int shelfSplitter = (low + high) / 2;
                //Checks if the book should be placed alphabetically before the target
                if (Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle).compare(book, s.get(cart.getCurrentShelf()).Books[shelfSplitter]) < 0){
                    high = shelfSplitter-1;
                } else if (Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle).compare(book, s.get(cart.getCurrentShelf()).Books[shelfSplitter]) > 0) {
                    low = shelfSplitter + 1;
                } else {
                    low = shelfSplitter;
                }
            }
            for (int i = s.get(cart.getCurrentShelf()).Books.length  - 1; i > low; i--){
                s.get(cart.getCurrentShelf()).Books[i] = s.get(cart.getCurrentShelf()).Books[i-1] ;
            }
        }
        return s;
    }
}