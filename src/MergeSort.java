import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSort implements SortingAlgorithm {

    @Override
    public Cart[] sortingSequence(Library L) {
        long startingSortingTime = System.currentTimeMillis();
        for (Cart cart : L.carts) {
            if (cart == null || cart.books == null || cart.books.length <= 1) {
                continue;
            }
            //Collect only the actual books (ignore nulls)
            List<Book> nonNullBooks = new ArrayList<>();
            for (Book book : cart.books) {
                if (book != null) {
                    nonNullBooks.add(book);
                }
            }
            //Convert to array for merge sort
            Book[] temp = nonNullBooks.toArray(new Book[0]);
            //Sort the actual books
            mergeSort(temp, 0, temp.length - 1);
            //Put sorted books back into cart
            for (int i = 0; i < temp.length; i++) {
                cart.books[i] = temp[i];
            }
            //Fill the rest with null
            for (int i = temp.length; i < cart.books.length; i++) {
                cart.books[i] = null;
            }
        }
        L.LibraryMetrics.setSortingTime(System.currentTimeMillis() - startingSortingTime);
            return L.carts;
    }

    private void mergeSort(Book[] books, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        mergeSort(books, left, mid);
        mergeSort(books, mid + 1, right);
        merge(books, left, mid, right);
    }

    private void merge(Book[] books, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;
        Book[] leftArray = new Book[leftSize];
        Book[] rightArray = new Book[rightSize];
        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = books[left + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = books[mid + 1 + j];
        }
        int i = 0;
        int j = 0;
        int k = left;
        Comparator<Book> comparator = Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle);
        while (i < leftSize && j < rightSize) {
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                books[k++] = leftArray[i++];
            } else {
                books[k++] = rightArray[j++];
            }
        }
        while (i < leftSize) {
            books[k++] = leftArray[i++];
        }
        while (j < rightSize) {
            books[k++] = rightArray[j++];
        }
    }
}