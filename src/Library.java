import java.util.ArrayList;

public class Library {
    private ArrayList<Book> libraryBooks = new ArrayList<>();
    private ArrayList<Book> displayBooks = new ArrayList<>();

    // public void edit(Book book) { }
    public ArrayList<Book> searchBook(String input, String type) {
        switch (type) {
            case "Title":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getTitle().equals("input"))
                        ;
                    displayBooks.add(book_Search);
                }
                break;

            case "Subtitle":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getSubTitle().equals("input"))
                        ;
                    displayBooks.add(book_Search);
                }
                break;

            case "Author":
                for (Book book_Search : libraryBooks) {
                    for (String author : book_Search.getAuthor()) {
                        if (author.equals(input)) {
                            displayBooks.add(book_Search);
                        }
                    }
                }
                break;

            case "Translator":
                for (Book book_Search : libraryBooks) {
                    for (String translator : book_Search.getTranslator()) {
                        if (translator.equals(input)) {
                            displayBooks.add(book_Search);
                        }
                    }
                }
                break;

            case "Tag":
                for (Book book_Search : libraryBooks) {
                    for (String tag : book_Search.getTag()) {
                        if (tag.equals(input)) {
                            displayBooks.add(book_Search);
                        }
                    }
                }
                break;

            case "ISBN":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getIsbn().equals(input)) {
                        displayBooks.add(book_Search);
                        break;
                    }
                }
                break;

            case "Publisher":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getPublisher().equals(input)) {
                        displayBooks.add(book_Search);
                    }
                }
                break;

            case "Date":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getDate().equals(input)) {
                        displayBooks.add(book_Search);
                    }
                }
                break;

            case "Edition":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getEdition().equals(input)) {
                        displayBooks.add(book_Search);
                    }
                }
                break;

            case "Language":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getLanguage().equals(input)) {
                        displayBooks.add(book_Search);
                    }
                }
                break;

            case "Rating":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getRating().equals(input)) {
                        displayBooks.add(book_Search);
                    }
                }
                break;

        }
        return displayBooks;
    }

    public void addBook(Book book) {
        libraryBooks.add(book);
    }

    public void removeBook(Book book) {
        libraryBooks.remove(book);
    }

    public void importJSON(String path) {
    }

    public void exportJSON() {
    }

    public ArrayList<Book> getLibraryBooks() {
        return libraryBooks;
    }

    public void setLibraryBooks(ArrayList<Book> libraryBooks) {
        this.libraryBooks = libraryBooks;
    }

    public ArrayList<Book> getDisplayBooks() {
        return displayBooks;
    }

    public void setDisplayBooks(ArrayList<Book> displayBooks) {
        this.displayBooks = displayBooks;
    }

}
