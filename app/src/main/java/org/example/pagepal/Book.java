package org.example.pagepal;
import java.util.ArrayList;

public class Book {
    private String title;
    private String subTitle;
    private ArrayList<String> author;
    private ArrayList<String> translator;
    private ArrayList<String> tag;
    private String isbn;
    private String publisher;
    private String date;
    private String edition;
    private String language;
    private String rating;

    public Book(
        String title, 
        String subTitle, 
        ArrayList<String> author, 
        ArrayList<String> translator,
        ArrayList<String> tag,
        String isbn,
        String publisher,
        String date,
        String edition,
        String language,
        String rating
    ){
        this.title = title;
        this.subTitle = subTitle;
        this.author = author;
        this.tag = tag;
        this.isbn = isbn;
        this.publisher = publisher;
        this.date = date;
        this.edition = edition;
        this.translator = translator;
        this.language = language;
        this.rating = rating;
    }

    public void setTitle(String title) { this.title = title; }

    public void setSubTitle(String subTitle) { this.subTitle = subTitle; }

    public void setAuthor(ArrayList<String> author) { this.author = author; }

    public void setTranslator(ArrayList<String> translator) { this.translator = translator; }

    public void setTag(ArrayList<String> tag) { this.tag = tag; }

    public void setIsbn(String isbn) { this.isbn = isbn; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public void setDate(String date) { this.date = date; }

    public void setEdition(String edition) { this.edition = edition; }

    public void setLanguage(String language) { this.language = language; }

    public void setRating(String rating) { this.rating = rating; }


    public ArrayList<String> getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public ArrayList<String> getTag() {
        return tag;
    }

    public ArrayList<String> getTranslator() {
        return translator;
    }

    public String getIsbn() {
        return isbn;
    }
    
    public String getPublisher() {
        return publisher;
    }

    public String getDate() {
        return date;
    }

    public String getEdition() {
        return edition;
    }

    public String getLanguage() {
        return language;
    }

    public String getRating() {
        return rating;
    }
    
}
