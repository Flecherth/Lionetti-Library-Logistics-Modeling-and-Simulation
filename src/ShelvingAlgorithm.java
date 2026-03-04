import java.util.*;

//Algorithms that determine how a cart will be shelved
public interface ShelvingAlgorithm {
    List<Shelf> generateShelvingSequence(Cart cart, List<Shelf> s, Library L);
}
