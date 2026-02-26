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

    String getTitle(){
        return title;
    }

    String getAuthor(){
        return author;
    }

    boolean isMisplaced(){

        return true;
    }
    void setDestination(String s){

    }
}
