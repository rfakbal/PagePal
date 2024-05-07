package org.example.pagepal;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Library {
    private ArrayList<Book> libraryBooks = new ArrayList<>();
    private ArrayList<Book> displayBooks = new ArrayList<>();
    private ArrayList<Book> searchedByTags = new ArrayList<>();
    private String filePath;

    // public void edit(Book book) { }
    public ArrayList<Book> searchBook(String input, String type, Boolean listByTags) {
        displayBooks.clear(); 

        if(!listByTags){
            if("All Books".contains(type)){
                for(Book book : libraryBooks){
                    displayBooks.add(book);
                }
            }
            if ("Title".contains(type)) {
                for (Book book : libraryBooks) {
                    if (book.getTitle().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else if ("Subtitle".contains(type)) {
                for (Book book : libraryBooks) {
                    if (book.getSubTitle().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else if ("Author".contains(type)) {
                for (Book book : libraryBooks) {
                    for (String author : book.getAuthor()) {
                        if (author.contains(input)) {
                            displayBooks.add(book);
                            break; 
                        }
                    }
                }
            } else if ("Translator".contains(type)) {
                for (Book book : libraryBooks) {
                    for (String translator : book.getTranslator()) {
                        if (translator.contains(input)) {
                            displayBooks.add(book);
                            break;
                        }
                    }
                }
            } else if ("Tag".contains(type)) {
                for (Book book : libraryBooks) {
                    for (String tag : book.getTag()) {
                        if (tag.contains(input)) {
                            displayBooks.add(book);
                            break; 
                        }
                    }
                }
            } else if ("ISBN".contains(type)) {
                for (Book book : libraryBooks) {
                    if (book.getIsbn().contains(input)) {
                        displayBooks.add(book);
                        break;
                    }
                }
            } else if ("Publisher".contains(type)) {
                for (Book book : libraryBooks) {
                    if (book.getPublisher().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else if ("Date".contains(type)) {
                for (Book book : libraryBooks) {
                        if (book.getDate().contains(input)) {
                            displayBooks.add(book);
                        }
                    }
            }else if ("Edition".contains(type)) {
                for (Book book : libraryBooks) {
                    if (book.getEdition().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else if ("Language".contains(type)) {
                for (Book book : libraryBooks) {
                    if (book.getLanguage().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else if ("Rating".contains(type)) {
                for (Book book : libraryBooks) {
                    if (book.getRating().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else {
                System.out.println("Geçersiz arama türü: " + type);
            }
        }

        else{
            if("All Books".contains(type)){
                for(Book book : searchedByTags){
                    displayBooks.add(book);
                }
            }
            if ("Title".contains(type)) {
                for (Book book : searchedByTags) {
                    if (book.getTitle().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else if ("Subtitle".contains(type)) {
                for (Book book : searchedByTags) {
                    if (book.getSubTitle().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else if ("Author".contains(type)) {
                for (Book book : searchedByTags) {
                    for (String author : book.getAuthor()) {
                        if (author.contains(input)) {
                            displayBooks.add(book);
                            break;
                        }
                    }
                }
            } else if ("Translator".contains(type)) {
                for (Book book : searchedByTags) {
                    for (String translator : book.getTranslator()) {
                        if (translator.contains(input)) {
                            displayBooks.add(book);
                            break; 
                        }
                    }
                }
            } else if ("Tag".contains(type)) {
                for (Book book : searchedByTags) {
                    for (String tag : book.getTag()) {
                        if (tag.contains(input)) {
                            displayBooks.add(book);
                            break; 
                        }
                    }
                }
            } else if ("ISBN".contains(type)) {
                for (Book book : searchedByTags) {
                    if (book.getIsbn().contains(input)) {
                        displayBooks.add(book);
                        break;
                    }
                }
            } else if ("Publisher".contains(type)) {
                for (Book book : searchedByTags) {
                    if (book.getPublisher().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else if ("Date".contains(type)) {
                for (Book book : searchedByTags) {
                        if (book.getDate().contains(input)) {
                            displayBooks.add(book);
                        }
                    }
            }else if ("Edition".contains(type)) {
                for (Book book : searchedByTags) {
                    if (book.getEdition().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else if ("Language".contains(type)) {
                for (Book book : searchedByTags) {
                    if (book.getLanguage().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else if ("Rating".contains(type)) {
                for (Book book : searchedByTags) {
                    if (book.getRating().contains(input)) {
                        displayBooks.add(book);
                    }
                }
            } else {
                System.out.println("Geçersiz arama türü: " + type);
            }
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
        //TODO empty json file error - will be solved
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
                writer.write("  \"subtitle\": \"" + libraryBooks.get(i).getSubTitle() + "\",\n");
                writer.write("  \"authors\": " + gson.toJson(libraryBooks.get(i).getAuthor()) + ",\n");
                writer.write("  \"translators\": " + gson.toJson(libraryBooks.get(i).getTranslator()) + ",\n");
                writer.write("  \"tags\": " + gson.toJson(libraryBooks.get(i).getTag()) + ",\n");
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

    public void setSearchedByTags(ArrayList<Book> searchedByTags) {
        this.searchedByTags = searchedByTags;
    }

    // searchedByTags ArrayList'ini getirmek için metot
    public ArrayList<Book> getSearchedByTags() {
        return searchedByTags;
    }

}
