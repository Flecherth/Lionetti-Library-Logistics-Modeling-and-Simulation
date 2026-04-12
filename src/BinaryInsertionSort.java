import java.util.*;

//Method that should perform better than insertion sort when shelving books from sorted carts into sorted shelves
public class BinaryInsertionSort implements ShelvingAlgorithm {
    @Override
    public List<Shelf> generateShelvingSequence(Cart[] carts, List<Shelf> s, Library L) {
        long shelvingStartTime = System.currentTimeMillis();
        Comparator<Book> comp = Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle);
        for (Cart cart : carts) {
            for (Book book : cart.books) {
                if (book == null) continue;
                int destination = Book.setDestination(book, s.size());
                L.LibraryMetrics.updateDistance(cart.getCurrentShelf(), destination);
                cart.setCurrentShelf(destination);
                Book[] shelfBooks = s.get(destination).Books;
                int low = 0;
                int high = shelfBooks.length - 1;
                while (low <= high) {
                    int mid = (low + high) / 2;
                    Book midBook = shelfBooks[mid];

                    if (midBook == null) {
                        high = mid - 1;
                    } else {
                        int cmp = comp.compare(book, midBook);
                        if (cmp < 0) {
                            high = mid - 1;
                        } else {
                            low = mid + 1;
                        }
                    }
                }
                if (low >= shelfBooks.length) {
                    low = shelfBooks.length - 1;
                }
                // shift right
                for (int i = shelfBooks.length - 1; i > low; i--) {
                    shelfBooks[i] = shelfBooks[i - 1];
                }
                shelfBooks[low] = book;
            }
        }
        L.LibraryMetrics.setShelvingTime(System.currentTimeMillis() - shelvingStartTime);
        return s;
    }
}