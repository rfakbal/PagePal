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
    private float rating;
    //private (JPG) cover will be added, do not forget to add set and get also;


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

    public void setRating(float rating) { this.rating = rating; }
}
