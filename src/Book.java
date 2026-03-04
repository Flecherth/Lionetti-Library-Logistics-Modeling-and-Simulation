import java.util.*;
public class Book {
    private String title;
    private String author;

    Book(){
    //Randomizer object for creating a title and author for these books
    Random r = new Random();
    String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //Sets title to a random 3 letter combination
    this.title = "" + alphabet.charAt(r.nextInt(26)) + alphabet.charAt(r.nextInt(26)) + alphabet.charAt(r.nextInt(26));
    //Sets author to a random 3 letter combination
    this.author = "" + alphabet.charAt(r.nextInt(26)) + alphabet.charAt(r.nextInt(26)) + alphabet.charAt(r.nextInt(26));
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }


    //Checks prior and next book on the shelf to see if the book is misplaced
    boolean isMisplaced(Book[] books, int i){
        //Note: Needs a handler for overflow and underflow
        return books[i - 1].getAuthor().compareTo(books[i].getAuthor()) >= 0 || (books[i].getAuthor().compareTo(books[i + 1].getAuthor()) <= 0);
    }

    //Setting destination to a new shelf by returning an integer to go to the id based on the first letter of the author's last name
    public static int setDestination(Book currentBook, int numOfShelves){
        char firstChar = Character.toUpperCase(currentBook.getAuthor().charAt(0));
        //Getting the index by subtracting the ascii value of the first letter of the author name by the ascii value of A dividing it by # of shelves and then 26
        return ((firstChar - 'A') * (numOfShelves) /26);
    }
}
