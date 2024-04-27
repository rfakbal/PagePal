package org.example.pagepal;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Library {
    private ArrayList<Book> libraryBooks = new ArrayList<>();
    private ArrayList<Book> displayBooks = new ArrayList<>();
    private String filePath;

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
        }else if ("Edition".equals(type)) {
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

    public void importJSON(String path) throws Exception {
        //empty json file error - will be solved
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new FileReader(path));
        Book[] data = gson.fromJson(reader, Book[].class);
        libraryBooks.clear();
        displayBooks.clear();
        
        for(int i = 0;i<data.length;i++){
            addBook(data[i]);
        }

    }

    public void exportJSON(String path) {
        Gson gson = new Gson();
        String[] booksArray = new String[libraryBooks.size()];  
        for (int i = 0; i < libraryBooks.size(); i++) {
            booksArray[i] = gson.toJson(libraryBooks.get(i));
        }
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("[\n");
            for (int i = 0; i < libraryBooks.size(); i++) {
                writer.write("{\n");
                writer.write("  \"title\": \"" + libraryBooks.get(i).getTitle() + "\",\n");
                writer.write("  \"subTitle\": \"" + libraryBooks.get(i).getSubTitle() + "\",\n");
                writer.write("  \"author\": " + gson.toJson(libraryBooks.get(i).getAuthor()) + ",\n");
                writer.write("  \"translator\": " + gson.toJson(libraryBooks.get(i).getTranslator()) + ",\n");
                writer.write("  \"tag\": " + gson.toJson(libraryBooks.get(i).getTag()) + ",\n");
                writer.write("  \"isbn\": \"" + libraryBooks.get(i).getIsbn() + "\",\n");
                writer.write("  \"publisher\": \"" + libraryBooks.get(i).getPublisher() + "\",\n");
                writer.write("  \"edition\": \"" + libraryBooks.get(i).getEdition() + "\",\n");
                writer.write("  \"language\": \"" + libraryBooks.get(i).getLanguage() + "\",\n");
                writer.write("  \"rating\": \"" + libraryBooks.get(i).getRating() + "\",\n");
                writer.write("  \"date\": \"" + libraryBooks.get(i).getDate() + "\",\n");
                writer.write("  \"cover\": \"" + libraryBooks.get(i).getCover() + "\"\n");
                if(i==libraryBooks.size()-1){
                    writer.write("}\n");
                }
                else{
                    writer.write("},\n");
                }
            }
            writer.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public String getFilePath(){
        return filePath;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

}
