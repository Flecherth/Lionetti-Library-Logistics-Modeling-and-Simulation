import java.util.*;

//Algorithms that determine how a cart will be shelved
public interface ShelvingAlgorithm {
    List<Book> generateShelvingSequence(Cart cart);
}
