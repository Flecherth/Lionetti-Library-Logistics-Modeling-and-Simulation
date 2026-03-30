import java.util.Comparator;

public class InsertionSort implements SortingAlgorithm {
    @Override
    public Cart[] sortingSequence(Library L) {
        long startingSortingTime = System.currentTimeMillis();
        for (Cart cart : L.carts){
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
        }
        L.LibraryMetrics.setSortingTime(System.currentTimeMillis() - startingSortingTime);
        return L.carts;
    }
}

