public class Cart {

    //Given cart a new integer variable called currentShelf which keeps track of where it is at
    private int currentShelf = 0;
    public Book[] books;
    Cart(){
        //Carts will have an array of no more than 50 books
        books = new Book[50];
        for(int i = 0; i < 50; i++){
            books[i] = new Book();
        }
    }

    //Adds the book to the cart in the first available position
    public void addBook(Book b){
        for (int i = 0; i < books.length; i++){
            if (books[i] == null){
                books[i] = b;
                //Ends the loop early if the book is added
                break;
            }
        }
    }

    //This method returns and removes a book from the cart
    public Book removeBook(int j){
        Book removedBook = books[j];
        books[j] = null;
        return removedBook;
    }

    public int getCurrentShelf() {
        return currentShelf;
    }

    public void setCurrentShelf(int currentShelf) {
        this.currentShelf = currentShelf;
    }
}
