
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;




public class App extends Application {

    Library lib = new Library();

    private void searchResults(String input, String type) {
        lib.searchBook(input, type);

        for (Book book : lib.getDisplayBooks()) {
            System.out.println(book.getTitle());
        }
    }

    private void btnOkDetect(){
        
        //display stage
        Stage secondStage = new Stage();
        
        VBox root = new VBox();
        HBox main = new HBox(20);
        root.setPrefSize(500, 550);

        
        VBox leftBox = new VBox(8);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        VBox rightBox = new VBox(8);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        //LEFT////////////////////////////////////////////////////////////////////////////

        Label titleLabel = new Label("Title");
        titleLabel.setFont(new Font(25));

        TextField titleField = new TextField();
        titleField.setPrefWidth(180);

        Label authorLabel = new Label("Author/Authors");
        authorLabel.setFont(new Font(25));

        TextField authorField = new TextField();
        authorField.setPrefWidth(180);

        Label isbnLabel = new Label("ISBN");
        isbnLabel.setFont(new Font(25));

        TextField isbnField = new TextField();
        isbnField.setPrefWidth(180);

        Label dateLabel = new Label("Date");
        dateLabel.setFont(new Font(25));

        DatePicker datepPicker = new DatePicker();

        Label coverLabel = new Label("Cover");
        coverLabel.setFont(new Font(25));

        //upload thing

        Label ratingLabel = new Label("Rating");
        ratingLabel.setFont(new Font(25));

        TextField ratingField = new TextField();
        ratingField.setPrefWidth(180);



        //right/////////////////////////////////////////////////////

        Label subtitle = new Label("Subtitle");
        subtitle.setFont(new Font(25));

        TextField subtitleField = new TextField();
        subtitleField.setPrefWidth(180);

        Label translatorLabel = new Label("Translator/Translators");
        translatorLabel.setFont(new Font(25));

        TextField translatorField = new TextField();
        translatorField.setPrefWidth(180);

        Label publisherLabel = new Label("Publisher");
        publisherLabel.setFont(new Font(25));

        TextField publisherField = new TextField();
        publisherField.setPrefWidth(180);

        Label editionLabel = new Label("Edition");
        editionLabel.setFont(new Font(25));

        TextField editionField = new TextField();
        editionField.setPrefWidth(180);

        Label languagLabel = new Label("Language");
        languagLabel.setFont(new Font(25));

        TextField languageField = new TextField();
        languageField.setPrefWidth(180);

        Label tagLabel = new Label("Tag/Tags");
        tagLabel.setFont(new Font(25));

        TextField tagField = new TextField();
        tagField.setPrefWidth(180);

        Button addBookButton = new Button("Add");
        addBookButton.setFont(new Font(14));
        addBookButton.setPrefSize(100.0, 45.0);
        addBookButton.setOnAction(e -> {
            ArrayList<String> author = new ArrayList<>();
            author.add(authorField.getText());
            ArrayList<String> translator = new ArrayList<>();
            translator.add(translatorField.getText());
            ArrayList<String> tag = new ArrayList<>();
            tag.add(tagField.getText());
            lib.addBook(new Book(titleField.getText(),subtitleField.getText(),author, translator, tag, isbnField.getText(), publisherField.getText(), datepPicker.getValue(), editionField.getText(), languageField.getText(), ratingField.getText()));
            secondStage.close();
        });

        Region spacer = new Region();



    


        VBox.setMargin(leftBox, new Insets(8));
        VBox.setMargin(rightBox, new Insets(8));
        VBox.setMargin(main, new Insets(8));
    



        leftBox.getChildren().addAll(titleLabel,titleField,authorLabel,authorField,isbnLabel,isbnField,dateLabel,datepPicker,coverLabel,ratingLabel,ratingField);

        rightBox.getChildren().addAll(subtitle,subtitleField,translatorLabel,translatorField,publisherLabel,publisherField,editionLabel,editionField,languagLabel,languageField,tagLabel,tagField,spacer, addBookButton);

        main.getChildren().addAll(leftBox,rightBox);

        root.getChildren().addAll(main);



        Scene scene = new Scene(root);
        secondStage.setScene(scene);
        secondStage.setTitle("Add Book");
        secondStage.show();
    }
    
    
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setPrefSize(640, 400);
        
        //MENU
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem addMenuItem = new MenuItem("Add Book");
        MenuItem importMenuItem = new MenuItem("Import Books");
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem saveAsMenuItem = new MenuItem("Save As…");
        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();

        MenuItem preferencesMenuItem = new MenuItem("Preferences…");
        SeparatorMenuItem separatorMenuItem3 = new SeparatorMenuItem();

        MenuItem quitMenuItem = new MenuItem("Quit");
        
        fileMenu.getItems().addAll(
            addMenuItem, importMenuItem,
            separatorMenuItem, saveMenuItem,
            saveAsMenuItem, separatorMenuItem2,
            preferencesMenuItem, separatorMenuItem3, quitMenuItem
        );
        
        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About PagePal");
        aboutMenuItem.setOnAction(e -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("About PagePal");
            alert.setContentText("This application is made by Ege Sevindi, Ruşen Akbal, Arda Sarı and Ege Yılmaz. It is the project of the course CE 216.");
            alert.setTitle("About");
            alert.showAndWait();
        });

        MenuItem manualMenuItem = new MenuItem("Manual");
        helpMenu.getItems().addAll(aboutMenuItem,manualMenuItem);
        
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        
        //WHAT İS THİS
        ContextMenu contextMenu = new ContextMenu(new MenuItem("Unspecified Action"));
        menuBar.setContextMenu(contextMenu);
        
        //main pane
        HBox firstLine = new HBox();
        firstLine.setAlignment(Pos.CENTER);

        HBox secondLine = new HBox(8);
        secondLine.setAlignment(Pos.CENTER);

        HBox thirdLine = new HBox();
        thirdLine.setSpacing(10);
        thirdLine.setAlignment(Pos.CENTER);
        
        
        Label label = new Label("Book Search");
        label.setFont(new Font(25));
        
        
        TextField searchField = new TextField();
        searchField.setPrefWidth(180);

        HBox.setHgrow(searchField, Priority.ALWAYS);
        
        
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll("Title", "Subtitle", "Author", "Translator", "Tag", "ISBN", "Publisher", "Date", "Edition", "Language", "Rating");
        choiceBox.setItems(options);
        
        
        Label searchByLabel = new Label("Search by :");
        
        Button searchButton = new Button("Search");
        searchButton.setFont(new Font(14));
        searchButton.setPrefSize(100.0, 45.0);

        Button addButton = new Button("Add Book");
        addButton.setFont(new Font(14));
        addButton.setPrefSize(100.0, 45.0);

        addButton.setOnAction(e->btnOkDetect());
        searchButton.setOnAction(e -> searchResults(searchField.getText(), choiceBox.getValue()));
        
        VBox.setMargin(firstLine, new Insets(8));
        VBox.setMargin(secondLine, new Insets(8));
        VBox.setMargin(thirdLine, new Insets(8));
        
        
        firstLine.getChildren().addAll(label);
        secondLine.getChildren().addAll(searchField,searchByLabel,choiceBox);
        thirdLine.getChildren().addAll(addButton, searchButton);

        
        root.getChildren().addAll(menuBar, firstLine,secondLine,thirdLine);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PagePal");
        primaryStage.show();


        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
