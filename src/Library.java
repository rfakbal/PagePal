import java.util.ArrayList;

public class Library {
    private ArrayList<Book> libraryBooks;
    private ArrayList<Book> displayBooks;

    // public void edit(Book book) { }
    public ArrayList<Book> searchBook(String input, String type) {
        switch (type) {
            case "title":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getTitle().equals("input"))
                        ;
                    displayBooks.add(book_Search);
                }
                break;

            case "subTitle":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getSubTitle().equals("input"))
                        ;
                    displayBooks.add(book_Search);
                }
                break;

            case "author":
                for (Book book_Search : libraryBooks) {
                    for (String author : book_Search.getAuthor()) {
                        if (author.equals(input)) {
                            displayBooks.add(book_Search);
                        }
                    }
                }
                break;

            case "translator":
                for (Book book_Search : libraryBooks) {
                    for (String translator : book_Search.getTranslator()) {
                        if (translator.equals(input)) {
                            displayBooks.add(book_Search);
                        }
                    }
                }
                break;

            case "tag":
                for (Book book_Search : libraryBooks) {
                    for (String tag : book_Search.getTag()) {
                        if (tag.equals(input)) {
                            displayBooks.add(book_Search);
                        }
                    }
                }
                break;

            case "isbn":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getIsbn().equals(input)) {
                        displayBooks.add(book_Search);
                        break;
                    }
                }
                break;

            case "publisher":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getPublisher().equals(input)) {
                        displayBooks.add(book_Search);
                    }
                }
                break;

            case "date":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getDate().equals(input)) {
                        displayBooks.add(book_Search);
                    }
                }
                break;

            case "edition":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getEdition().equals(input)) {
                        displayBooks.add(book_Search);
                    }
                }
                break;

            case "language":
                for (Book book_Search : libraryBooks) {
                    if (book_Search.getLanguage().equals(input)) {
                        displayBooks.add(book_Search);
                    }
                }
                break;

            case "rating":
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

}
