import java.util.*;

public class LibrarySort implements ShelvingAlgorithm {

    private final Comparator<Book> comp = Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle);

    @Override
    public List<Shelf> generateShelvingSequence(Cart[] carts, List<Shelf> shelves, Library L) {
        long shelvingStartTime = System.currentTimeMillis();
        for (Cart cart : carts) {
            int numOfShelves = shelves.size();
            for (Book book : cart.books) {
                if (book == null) continue;
                int targetShelfID = Book.setDestination(book, numOfShelves);
                Shelf targetShelf = shelves.get(targetShelfID);
                boolean placed = tryInsertIntoShelf(targetShelf, book);
                if (!placed) {
                    rebalanceShelf(targetShelf);
                    placed = tryInsertIntoShelf(targetShelf, book);
                }
                // spillover if still full
                if (!placed && targetShelfID + 1 < numOfShelves) {
                    Shelf nextShelf = shelves.get(targetShelfID + 1);
                    rebalanceShelf(nextShelf);
                    placed = tryInsertIntoShelf(nextShelf, book);
                    if (placed) {
                        targetShelf = nextShelf;
                        targetShelfID = targetShelf.getID();
                    }
                }
                L.LibraryMetrics.updateDistance(
                        cart.getCurrentShelf(),
                        targetShelf.getID()
                );
                cart.setCurrentShelf(targetShelf.getID());
            }
        }
        L.LibraryMetrics.setShelvingTime(System.currentTimeMillis() - shelvingStartTime);
        return shelves;
    }

    private boolean tryInsertIntoShelf(Shelf shelf, Book book) {
        Book[] arr = shelf.Books;
        //Find sorted position (first element greater than book)
        int i = 0;
        while (i < arr.length) {
            if (arr[i] != null && comp.compare(arr[i], book) > 0) {
                break;
            }
            i++;
        }
        //Find nearest gap to the right
        int gap = i;
        while (gap < arr.length && arr[gap] != null) {
            gap++;
        }
        if (gap >= arr.length) {
            return false;
        }
        //Shift elements right toward the gap
        for (int j = gap; j > i; j--) {
            arr[j] = arr[j - 1];
        }
        arr[i] = book;
        return true;
    }

    private void rebalanceShelf(Shelf shelf) {
        Book[] oldArr = shelf.Books;
        Book[] newArr = new Book[oldArr.length * 2];
        int j = 0;
        for (Book b : oldArr) {
            if (b != null) {
                newArr[j] = b;
                j += 2; // maintain gaps
            }
        }
        shelf.Books = newArr;
    }
}