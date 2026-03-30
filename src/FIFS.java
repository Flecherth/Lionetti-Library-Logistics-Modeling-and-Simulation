import java.util.List;

public class FIFS implements ShelvingAlgorithm {
    @Override
    public List<Shelf> generateShelvingSequence(Cart[] carts, List<Shelf> s, Library L) {
        long shelvingStartTime = System.currentTimeMillis();
        //Travel to shelf while calculating distance
        L.LibraryMetrics.updateDistance(0, L.currentFIFOShelf);
        for (Cart individualCart : carts) {
            //For each book in the cart insert without sorting
            for (Book book : individualCart.books) {
                if (L.booksOnShelf < s.get(0).shelfSize) {
                    s.get(L.currentFIFOShelf).Books[L.booksOnShelf] = book;
                    L.booksOnShelf++;
                }
                //Resets the index checker for FIFOShelves when the shelf would become full
                else {
                    if ((L.currentFIFOShelf + 1) <= s.size() - 1) {
                        L.booksOnShelf = 0;
                        //Updating distance as the cart travels to the next shelf
                        L.LibraryMetrics.updateDistance(L.currentFIFOShelf, L.currentFIFOShelf + 1);
                        //Moving to the next shelf
                        L.currentFIFOShelf++;
                    }
                }
            }
        }
        L.LibraryMetrics.setShelvingTime(System.currentTimeMillis() - shelvingStartTime);
        return s;
    }
}
