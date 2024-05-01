package org.example.pagepal;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
//import javafx.scene.shape.VLineTo;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import com.google.gson.Gson;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class App extends Application {

    Stage firstStage = new Stage();
    Stage infoStage = new Stage();
    VBox root = new VBox();
    Library lib = new Library();
    ListView<String> bookList = new ListView<>();
    TextField searchField = new TextField();
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    String newPath;

    @SuppressWarnings("unlikely-arg-type")

    //BOTH FOR EDIT AND SAVE BOOK TAB
    private void genBookInfo(Book book, int type){  // Type 1 = edit | Type 0 = add || genBookInfo is short for general book information
        Stage secondStage = new Stage();
                    
        VBox root = new VBox();
        HBox main = new HBox(20);
        root.setPrefSize(500, 550);
    
        VBox leftBox = new VBox(8);
        leftBox.setAlignment(Pos.CENTER_LEFT);
    
        VBox rightBox = new VBox(8);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
    
        VBox listBox = new VBox(8);
        listBox.setAlignment(Pos.CENTER);

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

        HBox coverBox = new HBox(8);
        StringBuilder pathOfCover = new StringBuilder();
        Label coverLabel = new Label("Cover");
        coverLabel.setFont(new Font(25));
        Button coverChoose = new Button("Choose file");
        Button coverDelete = new Button("Delete cover");
        coverBox.getChildren().addAll(coverChoose, coverDelete);
        FileChooser coverChooser = new FileChooser();
        coverChooser.setTitle("Choose file");
        coverChoose.setOnAction(e -> {
            File selectedCover = coverChooser.showOpenDialog(null);
            if (selectedCover != null) {
                pathOfCover.append(selectedCover.getAbsolutePath().replace('\\', '/'));
                newPath = pathOfCover.toString();
                System.out.println(pathOfCover);
            }
        });
        coverDelete.setOnAction(e -> {
            newPath = "";
        });
    
        Label ratingLabel = new Label("Rating");
        ratingLabel.setFont(new Font(25));
    
        TextField ratingField = new TextField();
        ratingField.setPrefWidth(180);
        
        //RIGHT/////////////////////////////////////////////////////
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

        if (type == 0) {
            
            Button addBookButton = new Button("Add");
            addBookButton.setFont(new Font(14));
            addBookButton.setPrefSize(100.0, 45.0);
            addBookButton.setOnAction(e -> {

            System.out.println(newPath);

            String[] authorArray = authorField.getText().split(",");
            ArrayList<String> authorList = new ArrayList<>(Arrays.asList(authorArray));
            String[] tagArray = tagField.getText().split(",");
            ArrayList<String> tagList = new ArrayList<>(Arrays.asList(tagArray));
            String[] translatorArray = translatorField.getText().split(",");
            ArrayList<String> translatorList = new ArrayList<>(Arrays.asList(translatorArray));

            String convertedDate;

            if(datepPicker.getValue() != null) {
                convertedDate = datepPicker.getValue().toString();
            } else {
                convertedDate = null;
            }

            lib.addBook(new Book(titleField.getText(),subtitleField.getText(),authorList, translatorList, tagList, isbnField.getText(), publisherField.getText(), convertedDate, editionField.getText(), languageField.getText(), ratingField.getText(),newPath));
            secondStage.close();
            });
            //what is this-arda 
            Region spacer = new Region();

            VBox.setMargin(leftBox, new Insets(8));
            VBox.setMargin(rightBox, new Insets(8));
            VBox.setMargin(main, new Insets(8));

            leftBox.getChildren().addAll(titleLabel,titleField,authorLabel,authorField,isbnLabel,isbnField,dateLabel,datepPicker,coverLabel,coverBox,ratingLabel,ratingField);

            rightBox.getChildren().addAll(subtitle,subtitleField,translatorLabel,translatorField,publisherLabel,publisherField,editionLabel,editionField,languagLabel,languageField,tagLabel,tagField,spacer, addBookButton);

            main.getChildren().addAll(leftBox,rightBox);

            root.getChildren().addAll(main);

            Scene scene = new Scene(root);
            secondStage.setScene(scene);
            secondStage.setTitle("Add Book");
            secondStage.show();
        }
        

        if (type == 1) {
            //SETTEXT HERE
            titleField.setText(book.getTitle());
            isbnField.setText(book.getIsbn());
            ratingField.setText(book.getRating());
            subtitleField.setText(book.getSubTitle());
            publisherField.setText(book.getPublisher());
            editionField.setText(book.getEdition());
            languageField.setText(book.getLanguage());



            if (!"null".equals(book.getDate()) && book.getDate() != null) {
                datepPicker.setValue(LocalDate.parse(book.getDate()));
            }


            ArrayList<String> translators = book.getTranslator();
            StringBuilder translatorText = new StringBuilder();
            if(!translators.isEmpty()){
                for (int i = 0; i < translators.size(); i++) {
                    translatorText.append(translators.get(i));
                    if (i < translators.size() - 1) {
                        translatorText.append(", "); 
                    }
                }
                translatorField.setText(translatorText.toString());
            }

            ArrayList<String> tags = book.getTag();
            StringBuilder tagText = new StringBuilder();
            if(!tags.isEmpty()){
                for (int i = 0; i < tags.size(); i++) {
                    tagText.append(tags.get(i));
                    if (i < tags.size() - 1) {
                        tagText.append(", "); 
                    }
                }
                tagField.setText(tagText.toString());
            }
    
    
                ArrayList<String> authors = book.getAuthor();
                StringBuilder authorText = new StringBuilder();
                if (!authors.isEmpty()) {
                    for (int i = 0; i < authors.size(); i++) {
                        authorText.append(authors.get(i));
                        if (i < authors.size() - 1) {
                            authorText.append(", "); 
                        }
                    }
                    authorField.setText(authorText.toString());
                }



            Button saveButton = new Button("Save");
            saveButton.setFont(new Font(14));

            saveButton.setOnAction(e -> {
            String[] authorArray = authorField.getText().split(",");
            ArrayList<String> authorList = new ArrayList<>(Arrays.asList(authorArray));
    
            String[] tagArray = tagField.getText().split(",");
            ArrayList<String> tagList = new ArrayList<>(Arrays.asList(tagArray));
    
            String[] translatorArray = translatorField.getText().split(",");
            ArrayList<String> translatorList = new ArrayList<>(Arrays.asList(translatorArray));

            book.setAuthor(authorList);
            book.setTag(tagList);
            book.setTranslator(translatorList);
            String editedDate;
            if (datepPicker.getValue() != null ) {
                editedDate = datepPicker.getValue().toString();
            } else {
                editedDate = null;
            }
            book.setDate(editedDate);
            book.setEdition(editionField.getText());
            book.setIsbn(isbnField.getText());
            book.setLanguage(languageField.getText());
            book.setPublisher(publisherField.getText());
            book.setRating(ratingField.getText());
            book.setSubTitle(subtitleField.getText());
            book.setTitle(titleField.getText());
            book.setCover(newPath);
            infoStage.close();
            secondStage.close();
        });


        saveButton.setPrefSize(100.0, 45.0);
        Region spacer = new Region();
    
        VBox.setMargin(leftBox, new Insets(8));
        VBox.setMargin(rightBox, new Insets(8));
        VBox.setMargin(main, new Insets(8));
    

        leftBox.getChildren().addAll(titleLabel,titleField,authorLabel,authorField,isbnLabel,isbnField,dateLabel,datepPicker,coverLabel,coverBox,ratingLabel,ratingField);
        rightBox.getChildren().addAll(subtitle,subtitleField,translatorLabel,translatorField,publisherLabel,publisherField,editionLabel,editionField,languagLabel,languageField,tagLabel,tagField,spacer, saveButton);
        main.getChildren().addAll(leftBox,rightBox);
        root.getChildren().addAll(main);
    
        Scene scene = new Scene(root);
        secondStage.setScene(scene);
        secondStage.setTitle("Edit Book");
        secondStage.show();
        
        }
    }

    
    //DISPLAY BOOK TAB 
    private void displayBookInfo(Book displayBook){
        System.out.println(displayBook.getDate());
        VBox bookInfo = new VBox(10);
        HBox bookInfoHB = new HBox();



        VBox leftVBox = new VBox();
        leftVBox.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView();
        imageView.setFitWidth(130.0);
        imageView.setFitHeight(195.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        try (FileInputStream im = new FileInputStream(displayBook.getCover())) {
            Image imm = new Image(im);
            imageView.setImage(imm);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        leftVBox.getChildren().add(imageView);

        VBox rightVBox = new VBox();

        VBox titleVBox = new VBox();
        Label titleLabel = new Label(displayBook.getTitle());
        titleLabel.setFont(new Font(18.0));
        titleVBox.getChildren().add(titleLabel);

        Label subTitleLabel = new Label(displayBook.getSubTitle());
        subTitleLabel.setFont(new Font(9.0));
        titleVBox.getChildren().add(subTitleLabel);
        rightVBox.getChildren().add(titleVBox);

        VBox infoVBox = new VBox();

        Label convertedDate;

        if (!"null".equals(displayBook.getDate()) && displayBook.getDate() != null) {
            convertedDate = new Label("Date : "+displayBook.getDate().toString());
        } else {
            convertedDate = new Label("Date : ");
        }

        Label[] infoLabels = {
                new Label("Author  : "+ displayBook.getAuthor()),
                new Label("Rating : "+displayBook.getRating()),
                new Label("Tags : "+displayBook.getTag()),
                new Label("Publisher : "+displayBook.getPublisher()),
                convertedDate,
                new Label("Edition : "+displayBook.getEdition()),
                new Label("Language : "+displayBook.getLanguage()),
                new Label("ISBN : "+displayBook.getIsbn()),
                new Label ("Translators : " + displayBook.getTranslator()),
        };

        for (Label labels : infoLabels) {
            infoVBox.getChildren().add(labels);
        }

        rightVBox.getChildren().add(infoVBox);
        HBox buttonsHBox = new HBox();
        VBox bottomVBox = new VBox();

        HBox.setMargin(bottomVBox, new Insets(0, 15, 0, 0));
        buttonsHBox.getChildren().add(bottomVBox);

        VBox editVBox = new VBox();
        editVBox.setAlignment(Pos.CENTER_RIGHT);
        HBox editButtonsHBox = new HBox(8);
        editButtonsHBox.setAlignment(Pos.CENTER_RIGHT);

        Button deleteButton = new Button("Delete Book");
        deleteButton.setStyle("-fx-background-color: Crimson");
        deleteButton.setMnemonicParsing(false);
        deleteButton.setPrefWidth(100);

        deleteButton.setOnAction(e->{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Deletion");
            alert.setContentText("You are about to delete this book. Are you sure?");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                bookList.getItems().remove(displayBook);
                lib.removeBook(displayBook);
                searchResults(searchField.getText(), choiceBox.getValue());
                infoStage.close();
            }
        });

        Button editButton = new Button("Edit Book");
        editButton.setMnemonicParsing(false);
        editButton.setPrefWidth(100);
        editButton.setOnAction(e -> genBookInfo(displayBook,1));
        editButtonsHBox.getChildren().addAll(deleteButton,editButton);
        editVBox.getChildren().add(editButtonsHBox);
        HBox.setHgrow(editButtonsHBox, Priority.ALWAYS);

        HBox bottomRightHBox = new HBox();
        bottomRightHBox.getChildren().add(editVBox);
        HBox.setMargin(editVBox, new Insets(0, 0, 0, 120));
        
        buttonsHBox.getChildren().add(bottomRightHBox);
        rightVBox.getChildren().add(buttonsHBox);

        bookInfoHB.getChildren().addAll(leftVBox, rightVBox);

        bookInfo.getChildren().add(bookInfoHB);
        Scene infoScene = new Scene(bookInfo);
        
        infoStage.setScene(infoScene);
        infoStage.show();
    }
    //SEARCH RESULT SHOW FUNCTİON
    private void searchResults(String input, String type) {
        if(input==null || type==null){
            return;
        }
        lib.searchBook(input, type);
        root.getChildren().remove(bookList);
        bookList.getItems().clear();
        lib.setDisplayBooks(lib.searchBook(input, type));

        for (Book book : lib.getDisplayBooks()) {
            bookList.getItems().add(book.getTitle()+" - "+book.getAuthor());
            System.out.println(book.getTitle());
        }

        root.getChildren().add(bookList);

        bookList.setOnMouseClicked(e-> displayBookInfo(lib.getDisplayBooks().get(bookList.getSelectionModel().getSelectedIndex())));

    }

    public void showManual() {
        Stage manualStage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label welcomeLabel = new Label("Welcome to PagePal.");

        Label featuresTitle = new Label("Main Features:");
        Label featuresDetails = new Label(
                "1. Importing data\n" +
                "2. Exporting data\n" +
                "3. Searching through data\n" +
                "4. Adding data\n" +
                "5. Deleting data\n" +
                "6. Editing data"
        );

        Label importingDataTitle = new Label("Importing Data:");
        Label importingDataDetails = new Label("To import data, go to 'File' menu and choose 'Import Books'.");

        Label exportingDataTitle = new Label("Exporting Data:");
        Label exportingDataDetails = new Label("To export data, go to 'File' menu and choose 'Export Books'.");

        Label addingDataTitle = new Label("Adding Data:");
        Label addingDataDetails = new Label(
                "To add data, press 'Add Book' button.\n" +
                "Then enter the information about the book.\n" +
                "Lastly, press 'Add Book' at the bottom."
        );

        Label searchingDataTitle = new Label("Searching Data:");
        Label searchingDataDetails = new Label(
                "To search data, first select the parameter\n" +
                "you want to search from the choice box.\n" +
                "Then write what you want to search in the search bar.\n" +
                "Finally, click the 'Search' button."
        );

        Label editingDeletingDataTitle = new Label("Editing and Deleting Data:");
        Label editingDeletingDataDetails = new Label(
                "To edit and delete data, first search the book.\n" +
                "Then click on the book in the list you want to edit or delete.\n" +
                "In the opening tab, at the right bottom corner,\n" +
                "there are two buttons for deleting and editing.\n" +
                "By pressing them, you can delete or edit the book information."
        );

        root.getChildren().addAll(
                welcomeLabel,
                featuresTitle, featuresDetails,
                importingDataTitle, importingDataDetails,
                exportingDataTitle, exportingDataDetails,
                addingDataTitle, addingDataDetails,
                searchingDataTitle, searchingDataDetails,
                editingDeletingDataTitle, editingDeletingDataDetails
        );
        Scene scene = new Scene(root);
        manualStage.setScene(scene);
        manualStage.setTitle("User Manual");
        manualStage.show();
    }
        
    @Override
    public void start(Stage firstStage) {

        // This reloads the previous data. If there is none, creates a new library.
        lib.setFilePath("library.json");
        File file = new File(lib.getFilePath());
        if (!file.exists()) {
            try {
                FileWriter creator = new FileWriter(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                lib.importJSON(lib.getFilePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // This saves the file when we close the application.
        firstStage.setOnCloseRequest(e -> saveWhenExit());


        root.setPrefSize(640, 400);
        
        //MENU
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem importMenuItem = new MenuItem("Import Books");
        importMenuItem.setOnAction(e->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select JSON File");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
            );
            File selectedFile = fileChooser.showOpenDialog(firstStage);
            if (selectedFile != null) {
                try {
                    lib.setFilePath(selectedFile.getAbsolutePath());

                    lib.importJSON(lib.getFilePath());
                    System.out.println("JSON file imported successfully.");
                } catch (Exception ex) {
                    System.out.println("Error importing JSON file: " + ex.getMessage());
                }
        }
        });
        MenuItem exportMenuItem = new MenuItem("Export Books");
        exportMenuItem.setOnAction(e->lib.exportJSON("library.json"));
        MenuItem createMenuItem = new MenuItem("Create Library");
        createMenuItem.setOnAction(e->{
            Gson gson = new Gson();
        
            lib.setFilePath("library.json");
            try {
                FileWriter writer = new FileWriter(lib.getFilePath());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem saveAsMenuItem = new MenuItem("Save As…");
        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();

        MenuItem preferencesMenuItem = new MenuItem("Preferences…");
        SeparatorMenuItem separatorMenuItem3 = new SeparatorMenuItem();

        MenuItem quitMenuItem = new MenuItem("Quit");
        quitMenuItem.setOnAction(e -> {saveWhenExit();
            firstStage.close();});
        
        fileMenu.getItems().addAll(
            importMenuItem, exportMenuItem, createMenuItem,
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
        manualMenuItem.setOnAction(e -> showManual());
        helpMenu.getItems().addAll(aboutMenuItem,manualMenuItem);
        
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        
        //What is this
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
        
        
        searchField.setPrefWidth(180);

        HBox.setHgrow(searchField, Priority.ALWAYS);
        
        
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

        addButton.setOnAction(e->genBookInfo(null, 0));
        searchButton.setOnAction(e -> searchResults(searchField.getText(), choiceBox.getValue()));
        
        VBox.setMargin(firstLine, new Insets(8));
        VBox.setMargin(secondLine, new Insets(8));
        VBox.setMargin(thirdLine, new Insets(8));
        
        
        firstLine.getChildren().addAll(label);
        secondLine.getChildren().addAll(searchField,searchByLabel,choiceBox);
        thirdLine.getChildren().addAll(searchButton, addButton);

        
        root.getChildren().addAll(menuBar, firstLine,secondLine,thirdLine);

        
        Scene scene = new Scene(root);
        firstStage.setScene(scene);
        firstStage.setTitle("PagePal");
        firstStage.show();


        
    }

    public void saveWhenExit() {
        try {
            lib.exportJSON("library.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
