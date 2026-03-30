import java.util.*;

public class LibrarySort implements ShelvingAlgorithm{
    @Override
    public List<Shelf> generateShelvingSequence(Cart[] carts, List<Shelf> s, Library L) {
        long shelvingStartTime = System.currentTimeMillis();
        for (Cart cart : carts) {
            int numOfShelves = s.size();
            for (Book book : cart.books) {
                if (book != null) {
                    //Determine the target shelf based on alphabetical range
                    int targetShelfID = Book.setDestination(book, numOfShelves);
                    Shelf targetShelf = s.get(targetShelfID);
                    // Find the first available slot with gaps
                    int index = 0;
                    while (index < targetShelf.Books.length && targetShelf.Books[index] != null) {
                        index += 2;  // gap size = 1,
                    }
                    //If shelf has room, insert the book
                    if (index < targetShelf.Books.length) {
                        targetShelf.Books[index] = book;
                    } else {
                        //Rebalance the shelf if no gaps remain
                        rebalanceShelf(targetShelf);
                        // After rebalance, find first available slot
                        index = 0;
                        while (index < targetShelf.Books.length && targetShelf.Books[index] != null) {
                            index += 2;
                        }
                        if (index < targetShelf.Books.length) {
                            targetShelf.Books[index] = book;
                        } else {
                            //Spillover to next shelf if still full,
                            if (targetShelfID + 1 < numOfShelves) {
                                Shelf nextShelf = s.get(targetShelfID + 1);
                                rebalanceShelf(nextShelf);
                                nextShelf.Books[0] = book;  // first slot of next shelf
                            }
                        }
                    }
                    //Update distance metric for LibraryMetrics
                    L.LibraryMetrics.updateDistance(cart.getCurrentShelf(), targetShelf.getID());
                    cart.setCurrentShelf(targetShelf.getID());
                }
            }
        }
        L.LibraryMetrics.setShelvingTime(System.currentTimeMillis() - shelvingStartTime);
        return s;
    }

    private void rebalanceShelf(Shelf s) {
        //Count actual books
        int bookCount = 0;
        for (Book b : s.Books) {
            if (b != null) bookCount++;
        }
        //Compute new gap spacing
        int gap = (s.Books.length - bookCount) / (bookCount + 1); // evenly distribute gaps
        if (gap < 1) gap = 1;  // minimum gap 1,
        //Create temporary array with double the size
        Book[] newArr = new Book[s.Books.length*2];
        int idx = gap;  // start with first gap
        for (Book b : s.Books) {
            if (b != null) {
                newArr[idx] = b;
                idx += gap + 1;
            }
        }
        //Overwrite original shelf
        s.Books = newArr;
    }
}
