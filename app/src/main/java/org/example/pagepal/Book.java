package org.example.pagepal;
import java.util.ArrayList;

public class Book {
    private String title;
    private String subtitle;
    private ArrayList<String> authors = new ArrayList<>();
    private ArrayList<String> translators = new ArrayList<>();
    private ArrayList<String> tags = new ArrayList<>();
    private String isbn;
    private String publisher;
    private String date;
    private String edition;
    private String language;
    private String rating;
    private String cover;

    public Book(
        String title, 
        String subtitle, 
        ArrayList<String> authors, 
        ArrayList<String> translators,
        ArrayList<String> tags,
        String isbn,
        String publisher,
        String date,
        String edition,
        String language,
        String rating,
        String cover
    ){
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.tags = tags;
        this.isbn = isbn;
        this.publisher = publisher;
        this.date = date;
        this.edition = edition;
        this.translators = translators;
        this.language = language;
        this.rating = rating;
        this.cover = cover;
    }

    public void setTitle(String title) { this.title = title; }

    public void setSubTitle(String subtitle) { this.subtitle = subtitle; }

    public void setAuthor(ArrayList<String> authors) { this.authors = authors; }

    public void setTranslator(ArrayList<String> translators) { this.translators = translators; }

    public void setTag(ArrayList<String> tag) { this.tags = tags; }

    public void setIsbn(String isbn) { this.isbn = isbn; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public void setDate(String date) { this.date = date; }

    public void setEdition(String edition) { this.edition = edition; }

    public void setLanguage(String language) { this.language = language; }

    public void setRating(String rating) { this.rating = rating; }

    public void setCover(String cover){this.cover = cover;}


    public ArrayList<String> getAuthor() {
        return authors;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subtitle;
    }

    public ArrayList<String> getTag() {
        return tags;
    }

    public ArrayList<String> getTranslator() {
        return translators;
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

    public String getCover(){
        return cover;
    }
    
}
