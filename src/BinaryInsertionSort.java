import java.util.*;

//Method that should perform better than insertion sort when shelving books from sorted carts into sorted shelves
public class BinaryInsertionSort implements ShelvingAlgorithm {
    @Override
    public List<Shelf> generateShelvingSequence(Cart[] carts, List<Shelf> s, Library L) {
        long shelvingStartTime = System.currentTimeMillis();
        for (Cart cart : carts) {
            //For every book in the cart
            for (Book book : cart.books) {
                if (book == null){
                    continue;
                }
                int low = 0;
                int high = s.get(cart.getCurrentShelf()).Books.length-1;
                //Updating distance for the metrics
                L.LibraryMetrics.updateDistance(cart.getCurrentShelf(), Book.setDestination(book, s.size()));
                //Updating currentShelf to keep track of distance
                cart.setCurrentShelf(Book.setDestination(book, s.size()));
                while (low <= high) {
                    //Integer to keep track of the middle of the binary sort
                    int shelfSplitter = (low + high) / 2;
                    //Checks if the book should be placed alphabetically before the target
                    if(s.get(cart.getCurrentShelf()).Books[low] == null || s.get(cart.getCurrentShelf()).Books[high] == null || (s.get(cart.getCurrentShelf()).Books[shelfSplitter]) == null) {
                        high -= 1;
                    } else {
                        //Checks if the book should be placed alphabetically before the target
                        if (Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle).compare(book, s.get(cart.getCurrentShelf()).Books[shelfSplitter]) < 0) {
                            high = shelfSplitter - 1;
                        } else if (Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle).compare(book, s.get(cart.getCurrentShelf()).Books[shelfSplitter]) > 0) {
                            low = shelfSplitter + 1;
                        } else {
                            low = shelfSplitter;
                        }
                    }
                }
                for (int i = s.get(cart.getCurrentShelf()).Books.length - 1; i > low; i--) {
                    s.get(cart.getCurrentShelf()).Books[i] = s.get(cart.getCurrentShelf()).Books[i - 1];
                }
            }
        }
        L.LibraryMetrics.setShelvingTime(System.currentTimeMillis() - shelvingStartTime);
        return s;
    }
}