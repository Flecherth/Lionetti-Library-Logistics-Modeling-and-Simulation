public class Cart {

    static Book[] books;
    Cart(){
        //Carts will have an array of no more than 50 books
        Book[] books = new Book[50];
        for(int i = 0; i < 40; i++){
            books[i] = (new Book());
        }
    }

    //Adds the book to the cart in the first available position
    static void addBook(Book b){
        for (int i = 0; i < books.length; i++){
            if (books[i] == null){
                books[i] = b;
                //Ends the loop early if the book is added
                break;
            }
        }
    }

    //This method returns and removes a book from the cart
    static Book removeBook(int j){
        Book removedBook = books[j];
        books[j] = null;
        return removedBook;
    }
}
