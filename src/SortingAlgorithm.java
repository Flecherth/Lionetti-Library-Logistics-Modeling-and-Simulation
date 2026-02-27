import java.util.List;

//Pivoted from design in M1 and split the algorithm interface into two based on shelving and sorting to make the methods clearer
//
public interface SortingAlgorithm {
    List<Book> sortingSequence(Cart cart);
}
