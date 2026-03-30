import java.util.*;

//Algorithms that determine how a cart will be shelved
public interface ShelvingAlgorithm {
    List<Shelf> generateShelvingSequence(Cart[] carts, List<Shelf> s, Library L);
}
