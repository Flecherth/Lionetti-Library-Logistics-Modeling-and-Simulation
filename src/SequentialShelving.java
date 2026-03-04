import java.util.*;
public class SequentialShelving implements ShelvingAlgorithm{
    @Override
    public List<Shelf> generateShelvingSequence(Cart cart, List<Shelf> s, Library L){
        //Integer to keep track of the id of the current shelf
        cart.setCurrentShelf(0);
        //For every shelf in the library
        for (Shelf shelf : s){
            //Find every book in the cart with an ID that matches the shelf’s
            for(int i = 0; i< cart.books.length; i++){
                if(shelf.getID() == Book.setDestination(cart.books[i], s.size())){
                    //Updates distance metric and moves pointer to the current shelf
                    L.LibraryMetrics.updateDistance(cart.getCurrentShelf(), shelf.getID());
                    cart.setCurrentShelf(shelf.getID());
                    //Places the book onto the shelf
                    shelf.insertBook(cart.books[i]);
                }
            }
        }
        return s;
    }
}
