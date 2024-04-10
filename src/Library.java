import java.util.ArrayList;

public class Library {
    private ArrayList<Book> libraryBooks = new ArrayList<>();
    private ArrayList<Book> displayBooks = new ArrayList<>();

    // public void edit(Book book) { }
    public ArrayList<Book> searchBook(String input, String type) {
        displayBooks.clear(); // Önceki aramaların sonuçlarını temizle

        if ("Title".equals(type)) {
            for (Book book : libraryBooks) {
                if (book.getTitle().equals(input)) {
                    displayBooks.add(book);
                }
            }
        } else if ("Subtitle".equals(type)) {
            for (Book book : libraryBooks) {
                if (book.getSubTitle().equals(input)) {
                    displayBooks.add(book);
                }
            }
        } else if ("Author".equals(type)) {
            for (Book book : libraryBooks) {
                for (String author : book.getAuthor()) {
                    if (author.equals(input)) {
                        displayBooks.add(book);
                        break; // İç içe döngüden çık
                    }
                }
            }
        } else if ("Translator".equals(type)) {
            for (Book book : libraryBooks) {
                for (String translator : book.getTranslator()) {
                    if (translator.equals(input)) {
                        displayBooks.add(book);
                        break; // İç içe döngüden çık
                    }
                }
            }
        } else if ("Tag".equals(type)) {
            for (Book book : libraryBooks) {
                for (String tag : book.getTag()) {
                    if (tag.equals(input)) {
                        displayBooks.add(book);
                        break; // İç içe döngüden çık
                    }
                }
            }
        } else if ("ISBN".equals(type)) {
            for (Book book : libraryBooks) {
                if (book.getIsbn().equals(input)) {
                    displayBooks.add(book);
                    break;
                }
            }
        } else if ("Publisher".equals(type)) {
            for (Book book : libraryBooks) {
                if (book.getPublisher().equals(input)) {
                    displayBooks.add(book);
                }
            }
        } else if ("Date".equals(type)) {
            for (Book book : libraryBooks) {
                if (book.getDate().equals(input)) {
                    displayBooks.add(book);
                }
            }
        } else if ("Edition".equals(type)) {
            for (Book book : libraryBooks) {
                if (book.getEdition().equals(input)) {
                    displayBooks.add(book);
                }
            }
        } else if ("Language".equals(type)) {
            for (Book book : libraryBooks) {
                if (book.getLanguage().equals(input)) {
                    displayBooks.add(book);
                }
            }
        } else if ("Rating".equals(type)) {
            for (Book book : libraryBooks) {
                if (book.getRating().equals(input)) {
                    displayBooks.add(book);
                }
            }
        } else {
            System.out.println("Geçersiz arama türü: " + type);
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
