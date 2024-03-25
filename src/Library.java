import java.util.ArrayList;

public class Library {
    private ArrayList<Book> libraryBooks;
    private ArrayList<Book> displayBooks;
    //public void edit(Book book) { }
    public ArrayList<Book> searchBook(String input, String type) {  
        switch(type){
            case "title":
                for(Book book_Search: libraryBooks){
                    if(book_Search.getTitle().equals("input"));
                    displayBooks.add(book_Search);
                }
                break;
            case "subTitle":
                for(Book book_Search: libraryBooks){
                    if(book_Search.getSubTitle().equals("input"));
                    displayBooks.add(book_Search);
                }
                break;
            case "author":
                for(Book book_Search: libraryBooks){
                    for(String author : book_Search.getAuthor()){
                        if(author.equals(input)){
                            displayBooks.add(book_Search);
                        }
                    }
                }
        } 
        return displayBooks; }
    public void addBook(Book book) { 
        libraryBooks.add(book);
    }
    public void removeBook(Book book) {
        libraryBooks.remove(book);
    }
    public void importJSON(String path) { }
    public void exportJSON(){ }
    
}
