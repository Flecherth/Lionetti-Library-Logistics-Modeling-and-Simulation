import java.util.*;
public class Library {
    Library(){
        //Creates an array list of books
        List<Book> books = new ArrayList<>();
        //Filling the list of books with 100-1300 books
        for (int i = 0; i < (int)((Math.random() * 1201) + 100); i++){
            books.add(new Book());
        }
        //Sorting these books as the simulation is only calculating the sorting and shelving of carts and not the entire library
        books.sort(Comparator.comparing(Book::getAuthor).thenComparing(Book::getTitle));
        //A list of shelves for storing sorted books
        List<Shelf> sortedShelves = new ArrayList<>();
        //A list of shelves for storing sorted books
        List<Shelf> FIFOShelves = new ArrayList<>();
        //Creating a variable to hold the number of shelves (Minimum 2 and a maximum of 26)
        int numOfShelves = (books.size() / 50);
        //Generate shelves including ID and range by calling their constructors
        for (int j = 0; j<numOfShelves; j++){
            //SortedShelves need an ID as well as a range (the range is calculated by splitting the alphabet into sections based on numOfShelves)
            sortedShelves.add(new Shelf(j+1,""));
            //FIFOShelves don't need a range so the other constructor is called
            FIFOShelves.add(new Shelf(j+1));
        }

    }

    void fillShelves(){
        for(sortedShelves.size()){

        }
    }


    static Cart addCart(){
        return new Cart();
    }


}