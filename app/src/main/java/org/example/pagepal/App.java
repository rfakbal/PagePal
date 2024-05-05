package org.example.pagepal;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import javax.sound.sampled.SourceDataLine;

import com.google.gson.Gson;
import com.google.gson.internal.sql.SqlTypesSupport;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class App extends Application {

    Stage firstStage = new Stage();
    Stage infoStage = new Stage();
    Stage secondStage = new Stage();
    Stage manualStage = new Stage();
    Stage tagStage = new Stage();
    VBox root = new VBox();
    Library lib = new Library();
    ListView<String> bookList = new ListView<>();
    ArrayList<String> tagList = new ArrayList<>();
    TableView bookTable = new TableView();
    HBox forBookTablePadding = new HBox(bookTable);
    TextField searchField = new TextField();
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    ListView<String> tagListView = new ListView<>();
    Boolean listedByTags = false;
    HBox thirdLine = new HBox();
    Button deListButton = new Button("Remove Filter");

    @SuppressWarnings("unlikely-arg-type")

    public void displayTagsMenu() {
        tagListView.getItems().clear();
        TextField newTagField = new TextField();
        for (Book book : lib.getLibraryBooks()) {
            for (String tag : book.getTag()) {
                tagList.add(tag);
            }
        }

        HashSet<String> set = new HashSet<>();
        ArrayList<String> nonDuplicates = new ArrayList<>();

        for (String str : tagList) {
            if (set.add(str)) {
                nonDuplicates.add(str);
            }
        }
        tagList.clear();
        tagList.addAll(nonDuplicates);

        for (String tag : tagList) {
            if (tag.equals("")) {
                tagList.remove(tag);
            }
        }

        tagListView.getItems().addAll(tagList);

        newTagField.setPromptText("Add New Tag or Search Tag");

        newTagField.setPrefWidth(180);
        newTagField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        newTagField.setStyle("-fx-background-radius: 35;");

        newTagField.setBorder(new Border(
            new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(35), BorderStroke.THIN)));

        Button addButton = new Button("Add");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        searchButton.setOnAction(e -> {
            ArrayList<String> found = new ArrayList<>();
            for (String tag : tagList) {
                if (tag.contains(newTagField.getText())) {
                    found.add(tag);
                }
                tagListView.getItems().clear();
                tagListView.getItems().addAll(found);
            }

        });

        Button listTags = new Button("List By Tags");
        listTags.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        listTags.setOnAction(e -> {
            ObservableList<String> Obs = tagListView.getSelectionModel().getSelectedItems();
            ArrayList<Book> tempBookList = new ArrayList<>();
            ArrayList<Book> libraryClone = new ArrayList<>(lib.getLibraryBooks());

            for (String tag : Obs) {
                ArrayList<Book> tempBooksToRemove = new ArrayList<>(); 
                for (Book book : libraryClone) {
                    for (String tempTag : book.getTag()) {
                        if (tempTag.equals(tag)) {
                            tempBookList.add(book);
                            tempBooksToRemove.add(book); 
                            break; 
                        }
                    }
                }
                libraryClone.removeAll(tempBooksToRemove);
            }

            lib.setSearchedByTags(tempBookList);
            listedByTags = true;
            searchResults("","All Books");
            if (listedByTags) {
                thirdLine.getChildren().add(deListButton);
            }
            tagStage.close();
        });

        addButton.setOnAction(e -> {
            Alert tagAlert = new Alert(AlertType.WARNING);
            tagAlert.setHeaderText("Warning");

            boolean addToList = true;
            String newTag = newTagField.getText().trim();
            if (!newTag.isEmpty()) {
                for (String tag : tagList) {
                    if (tag.equals(newTag)) {
                        addToList = false;
                    }
                }
                if (addToList) {
                    tagListView.getItems().add(newTag);
                    tagList.add(newTag);
                    newTagField.clear();
                } else {
                    tagAlert.setContentText("The Tag you have entered is already defined try to add another tag.");
                    tagAlert.setTitle("Tag Error");
                    tagAlert.showAndWait();
                }

            }
        });

        Button deleteButton = new Button("Remove");
        deleteButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        deleteButton.setOnAction(e -> {
            int selectedIndex = tagListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                String tag = tagListView.getItems().get(selectedIndex);
                tagListView.getItems().remove(selectedIndex);
                tagList.remove(tag);
                for (Book book : lib.getLibraryBooks()) {
                    for (String bookTag : book.getTag()) {
                        if (tag.equals(bookTag)) {
                            book.getTag().remove(tag);
                        }
                    }
                }
            }
        });

        tagListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        HBox forButtons = new HBox(10);
        forButtons.getChildren().addAll(searchButton, addButton, deleteButton, listTags);
        forButtons.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(tagListView, newTagField, forButtons);
        vbox.setStyle("-fx-background-color: linear-gradient(to top right, #6fa8dc, #ffffff); ");
        Scene scene = new Scene(vbox, 300, 200);
        tagStage.setScene(scene);

        tagStage.show();
    }

    // BOTH FOR EDIT AND SAVE BOOK TAB
    private void genBookInfo(Book book, int type) { // Type 1 = edit | Type 0 = add || genBookInfo is short for general book information

        VBox root = new VBox();
        Scene scene = new Scene(root);
        root.setStyle("-fx-background-color: linear-gradient(to top right, #6fa8dc, #ffffff); ");
        HBox main = new HBox(20);
        root.setPrefSize(500, 550);

        VBox leftBox = new VBox(8);
        leftBox.setAlignment(Pos.TOP_LEFT);

        VBox rightBox = new VBox(8);
        rightBox.setAlignment(Pos.TOP_RIGHT);

        VBox listBox = new VBox(8);
        listBox.setAlignment(Pos.CENTER);

        // LEFT////////////////////////////////////////////////////////////////////////////
        Label titleLabel = new Label("Title");
        titleLabel.setFont(new Font(25));

        TextField titleField = new TextField();
        titleField.setPrefWidth(180);
        titleField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        titleField.setStyle("-fx-background-radius: 5;");
        titleField.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));

        Label authorLabel = new Label("Author/Authors");
        authorLabel.setFont(new Font(25));

        TextField authorField = new TextField();
        authorField.setPromptText("Enter multiple using (,)");
        authorField.setPrefWidth(180);
        authorField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        authorField.setStyle("-fx-background-radius: 5;");
        authorField.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));

        Label isbnLabel = new Label("ISBN");
        isbnLabel.setFont(new Font(25));

        TextField isbnField = new TextField();
        isbnField.setPromptText("Enter a 13 digit number.");
        isbnField.setPrefWidth(180);
        isbnField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        isbnField.setStyle("-fx-background-radius: 5;");
        isbnField.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));


        Label dateLabel = new Label("Date");
        dateLabel.setFont(new Font(25));

        DatePicker datepPicker = new DatePicker();
        datepPicker.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        datepPicker.setStyle("-fx-background-radius: 5;");
        datepPicker.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));

        
        // For cover path selecting and making it compatible with java
        HBox coverBox = new HBox(8);
        StringBuilder pathOfCover = new StringBuilder();
        Label coverLabel = new Label("Cover");
        coverLabel.setFont(new Font(25));
        Button coverChoose = new Button("Choose file");
        coverChoose.setStyle(
            "-fx-background-color: #4CAF50; " +
                    "-fx-background-radius: 5; " +
                    "-fx-text-fill: white; ");
        Button coverDelete = new Button("Delete cover");
        coverDelete.setStyle(
            "-fx-background-color: #ff3333; " +
                    "-fx-background-radius: 5; " +
                    "-fx-text-fill: white; ");
        coverBox.getChildren().addAll(coverChoose, coverDelete);
        FileChooser coverChooser = new FileChooser();
        coverChooser.setTitle("Choose file");
        coverChoose.setOnAction(e -> {
            File selectedCover = coverChooser.showOpenDialog(null);
            if (selectedCover != null) {
                if (((selectedCover.getAbsolutePath()).contains(".jpg") || (selectedCover.getAbsolutePath().contains(".png")))){
                pathOfCover.append(selectedCover.getAbsolutePath().replace('\\', '/'));
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setContentText("The file you have selected is in the wrong type. Please select a file which is JPG or PNG.");
                alert.setTitle("Warning");
                alert.setHeaderText("Wrong file type.");
                alert.showAndWait();
            }
        }});
        coverDelete.setOnAction(e -> {
            pathOfCover.setLength(0);
        });

        Label ratingLabel = new Label("Rating");
        ratingLabel.setFont(new Font(25));

        TextField ratingField = new TextField();
        ratingField.setPromptText("Enter a value between 0-10");
        ratingField.setPrefWidth(180);
        ratingField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        ratingField.setStyle("-fx-background-radius: 5;");
        ratingField.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));


        // RIGHT/////////////////////////////////////////////////////
        Label subtitle = new Label("Subtitle");
        subtitle.setFont(new Font(25));

        TextField subtitleField = new TextField();
        subtitleField.setPrefWidth(180);
        subtitleField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        subtitleField.setStyle("-fx-background-radius: 5;");
        subtitleField.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));

        Label translatorLabel = new Label("Translator/Translators");
        translatorLabel.setFont(new Font(25));

        TextField translatorField = new TextField();
        translatorField.setPromptText("Enter multiple using (,)");
        translatorField.setPrefWidth(180);
        translatorField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        translatorField.setStyle("-fx-background-radius: 5;");
        translatorField.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));


        Label publisherLabel = new Label("Publisher");
        publisherLabel.setFont(new Font(25));

        TextField publisherField = new TextField();
        publisherField.setPrefWidth(180);
        publisherField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        publisherField.setStyle("-fx-background-radius: 5;");
        publisherField.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));


        Label editionLabel = new Label("Edition");
        editionLabel.setFont(new Font(25));

        TextField editionField = new TextField();
        editionField.setPrefWidth(180);
        editionField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        editionField.setStyle("-fx-background-radius: 5;");
        editionField.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));


        Label languagLabel = new Label("Language");
        languagLabel.setFont(new Font(25));

        TextField languageField = new TextField();
        languageField.setPrefWidth(180);
        languageField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        languageField.setStyle("-fx-background-radius: 5;");
        languageField.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));


        Label tagLabel = new Label("Tag/Tags");
        tagLabel.setFont(new Font(25));

        TextField tagField = new TextField();
        tagField.setPromptText("Enter multiple using (,)");
        tagField.setPrefWidth(180);
        tagField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        tagField.setStyle("-fx-background-radius: 5;");
        tagField.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, new CornerRadii(5), BorderStroke.THIN)));


        if (type == 0) {
            Button addBookButton = new Button("Add");
            addBookButton.setFont(new Font(14));
            addBookButton.setPrefSize(100.0, 45.0);
            addBookButton.setOnAction(e -> {
                boolean checki = true;
                boolean checkr = true;
                boolean control = true;
                String isbnText = isbnField.getText();
                float rating = 0;
                try {
                    if (!isbnText.equals("")) {
                        long isbn = Long.parseLong(isbnText);
                        for (Book ibook : lib.getLibraryBooks()) {
                            if (ibook.getIsbn().equals(isbnText)) {
                                if (ibook != book) {
                                    control = false;
                                }
                            }
                        }
                        if (isbnText.length() != 13) {
                            control = false;
                        }
                    }
                } catch (NumberFormatException exc) {
                    checki = false;
                }

                try {
                    rating = Float.parseFloat(ratingField.getText());
                } catch (NumberFormatException exc) {
                    checkr = false;
                }

                if (ratingField.getText().isEmpty()) {
                    checkr = true;
                }
                Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("Warning");
                if (!checki) {
                    alert.setContentText("The ISBN you have entered has unknown characters.");
                    alert.setTitle("ISBN Error");
                    alert.showAndWait();
                } else if (!control) {
                    alert.setContentText("The ISBN you have entered already exists or is of the wrong length.");
                    alert.setTitle("ISBN Error");
                    alert.showAndWait();
                } else if (!checkr) {
                    alert.setContentText("The Rating you have entered has either unknown characters.");
                    alert.setTitle("Rating Error");
                    alert.showAndWait();
                } else if (rating > 10 || rating < 0) {
                    alert.setContentText("The Rating you have entered is not between 0 and 10.");
                    alert.setTitle("Rating Error");
                    alert.showAndWait();
                } else {
                    System.out.println(pathOfCover.toString());
                    String[] authorArray = authorField.getText().split(",");
                    ArrayList<String> authorList = new ArrayList<>(Arrays.asList(authorArray));
                    String[] tagArray = tagField.getText().split(",");
                    ArrayList<String> tagList = new ArrayList<>(Arrays.asList(tagArray));
                    String[] translatorArray = translatorField.getText().split(",");
                    ArrayList<String> translatorList = new ArrayList<>(Arrays.asList(translatorArray));

                    String convertedDate;

                    if (datepPicker.getValue() != null) {
                        convertedDate = datepPicker.getValue().toString();
                    } else {
                        convertedDate = null;
                    }

                    lib.addBook(new Book(titleField.getText(), subtitleField.getText(), authorList, translatorList,
                            tagList, isbnField.getText(), publisherField.getText(), convertedDate,
                            editionField.getText(), languageField.getText(), ratingField.getText(),
                            pathOfCover.toString()));

                    searchResults(searchField.getText(), choiceBox.getValue());
                    pathOfCover.setLength(0);
                    secondStage.close();
                }
            });

            // what is this-arda
            Region spacer = new Region();

            VBox.setMargin(leftBox, new Insets(8));
            VBox.setMargin(rightBox, new Insets(8));
            VBox.setMargin(main, new Insets(8));

            leftBox.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, isbnLabel, isbnField,
                    dateLabel, datepPicker, coverLabel, coverBox, ratingLabel, ratingField);

            rightBox.getChildren().addAll(subtitle, subtitleField, translatorLabel, translatorField, publisherLabel,
                    publisherField, editionLabel, editionField, languagLabel, languageField, tagLabel, tagField, spacer,
                    addBookButton);

            main.getChildren().addAll(leftBox, rightBox);
            main.setAlignment(Pos.CENTER);
            root.getChildren().addAll(main);
            root.setAlignment(Pos.CENTER);

            secondStage.setScene(scene);
            secondStage.setTitle("Add Book");
            secondStage.show();
        }

        if (type == 1) {
            // SETTEXT HERE
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
            if (!translators.isEmpty()) {
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
            if (!tags.isEmpty()) {
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
                boolean checki = true;
                boolean checkr = true;
                boolean control = true;
                String isbnText = isbnField.getText();
                float rating = 0;
                try {
                    if (!isbnText.equals("")) {
                        long isbn = Long.parseLong(isbnText);
                        for (Book ibook : lib.getLibraryBooks()) {
                            if (ibook.getIsbn().equals(isbnText)) {
                                if (ibook != book) {
                                    control = false;
                                }
                            }
                        }
                        if (isbnText.length() != 13) {
                            control = false;
                        }
                    }
                } catch (NumberFormatException exc) {
                    checki = false;
                }

                try {
                    rating = Float.parseFloat(ratingField.getText());
                } catch (NumberFormatException exc) {
                    checkr = false;
                }

                if (ratingField.getText().isEmpty()) {
                    checkr = true;
                }
                Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("Warning");
                if (!checki) {
                    alert.setContentText("The ISBN you have entered has unknown characters.");
                    alert.setTitle("ISBN Error");
                    alert.showAndWait();
                } else if (!control) {
                    alert.setContentText("The ISBN you have entered already exists or is of the wrong length.");
                    alert.setTitle("ISBN Error");
                    alert.showAndWait();
                } else if (!checkr) {
                    alert.setContentText("The Rating you have entered has either unknown characters.");
                    alert.setTitle("Rating Error");
                    alert.showAndWait();
                } else if (rating > 10 || rating < 0) {
                    alert.setContentText("The Rating you have entered is not between 0 and 10.");
                    alert.setTitle("Rating Error");
                    alert.showAndWait();
                } else {

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
                    if (datepPicker.getValue() != null) {
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
                    book.setCover(pathOfCover.toString());
                    pathOfCover.setLength(0);

                    infoStage.close();
                    secondStage.close();
                    searchResults(searchField.getText(), choiceBox.getValue());
                }
            });

            saveButton.setPrefSize(100.0, 45.0);
            Region spacer = new Region();

            VBox.setMargin(leftBox, new Insets(8));
            VBox.setMargin(rightBox, new Insets(8));
            VBox.setMargin(main, new Insets(8));

            leftBox.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, isbnLabel, isbnField,
                    dateLabel, datepPicker, coverLabel, coverBox, ratingLabel, ratingField);
            rightBox.getChildren().addAll(subtitle, subtitleField, translatorLabel, translatorField, publisherLabel,
                    publisherField, editionLabel, editionField, languagLabel, languageField, tagLabel, tagField, spacer,
                    saveButton);
            main.getChildren().addAll(leftBox, rightBox);
            root.getChildren().addAll(main);
            root.setStyle("-fx-background-color: linear-gradient(to top right, #6fa8dc, #ffffff); ");
            secondStage.setScene(scene);
            secondStage.setTitle("Edit Book");
            secondStage.show();

        }
    }

    // DISPLAY BOOK TAB
    private void displayBookInfo(Book displayBook) {
        VBox mainBox = new VBox(10);
        HBox topBox = new HBox(10);
        VBox leftBox = new VBox(10);
        VBox rightBox = new VBox(10);
        VBox bottomVBox = new VBox(10);

        mainBox.setStyle("-fx-background-color: linear-gradient(to top right, #6fa8dc, #ffffff); ");


        Label spacer = new Label("   ");
        ImageView imageView = new ImageView();
        VBox forImage = new VBox(10);
        VBox imageSpace = new VBox(10);
        forImage.getChildren().addAll(imageSpace, imageView);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(infoStage.widthProperty().multiply(0.5));
        imageView.fitHeightProperty().bind(infoStage.heightProperty().multiply(0.88));
        imageView.setImage(null);
        System.out.println(displayBook.getCover());
        if (displayBook.getCover() != null && !displayBook.getCover().equals("null")
                && !displayBook.getCover().equals("")) {
            try (FileInputStream im = new FileInputStream(displayBook.getCover())) {
                Image imm = new Image(im);
                imageView.setImage(imm);
            } catch (IOException e1) {
                Image imm = new Image("file:../app/cat.jpg");
                imageView.setImage(imm);
            }
        } else {
            Image imm = new Image("file:../app/cat.jpg");
            imageView.setImage(imm);
        }
        leftBox.getChildren().add(forImage);

        Label titleLabel = new Label(displayBook.getTitle());
        titleLabel.setFont(new Font(20.0));
        rightBox.getChildren().add(titleLabel);

        Label convertedDate;

        if (!"null".equals(displayBook.getDate()) && displayBook.getDate() != null) {
            convertedDate = new Label("Date : " + displayBook.getDate().toString());
        } else {
            convertedDate = new Label("Date : ");
        }

        Label[] infoLabels = {
                new Label("Author  : " + displayBook.getAuthor()),
                new Label("Rating : " + displayBook.getRating()),
                new Label("Tags : " + displayBook.getTag()),
                new Label("Publisher : " + displayBook.getPublisher()),
                convertedDate,
                new Label("Edition : " + displayBook.getEdition()),
                new Label("Language : " + displayBook.getLanguage()),
                new Label("ISBN : " + displayBook.getIsbn()),
                new Label("Translators : " + displayBook.getTranslator()),
                new Label("Subtitle  : " + displayBook.getSubTitle())
        };

        for (Label labels : infoLabels) {
            labels.setFont(new Font(14));
            rightBox.getChildren().add(labels);
        }

        HBox buttonHBox = new HBox();
        buttonHBox.setAlignment(Pos.BOTTOM_CENTER);

        // VBox.setMargin(buttonHBox, new Insets(0, 15, 0, 0));
        bottomVBox.getChildren().addAll(buttonHBox);
        Button deleteButton = new Button("Delete Book");
        deleteButton.setStyle(
                "-fx-background-color: #ff3333; " +
                        "-fx-background-radius: 5; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 5 10; " +
                        "-fx-font-size: 14px;");

        deleteButton.setOnMouseEntered(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), deleteButton);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });

        deleteButton.setOnMouseExited(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), deleteButton);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });

        deleteButton.setMnemonicParsing(false);
        deleteButton.setPrefWidth(100);

        deleteButton.setOnAction(e -> {
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
                searchResults(searchField.getText(), choiceBox.getValue());
            }
        });

        Button editButton = new Button("Edit Book");
        editButton.setStyle(
                "-fx-background-color: #6fa8dc; " +
                        "-fx-background-radius: 5; " +
                        "-fx-text-fill: black; " +
                        "-fx-padding: 5 10; " +
                        "-fx-font-size: 14px;");

        editButton.setOnMouseEntered(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), editButton);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.play();
        });

        editButton.setOnMouseExited(event -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), editButton);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.play();
        });

        editButton.setMnemonicParsing(false);
        editButton.setPrefWidth(100);
        editButton.setOnAction(e -> genBookInfo(displayBook, 1));
        buttonHBox.getChildren().addAll(deleteButton, spacer, editButton);

        topBox.getChildren().addAll(leftBox, rightBox);

        forImage.setAlignment(Pos.CENTER);
        buttonHBox.setAlignment(Pos.CENTER);
        bottomVBox.setAlignment(Pos.BOTTOM_CENTER);
        rightBox.setAlignment(Pos.CENTER);
        leftBox.setAlignment(Pos.CENTER);
        topBox.setAlignment(Pos.CENTER);

        mainBox.getChildren().addAll(topBox, bottomVBox);

        VBox.setVgrow(mainBox, Priority.ALWAYS);
        HBox.setHgrow(mainBox, Priority.ALWAYS);

        infoStage.setWidth(500);
        infoStage.setHeight(425);
        Scene infoScene = new Scene(mainBox);
        infoStage.setScene(infoScene);
        infoStage.show();
    }

    // SEARCH RESULT SHOW FUNCTİON
    private void searchResults(String input, String type) {
        if (input == null || type == null) {
            return;
        }
        bookTable.setVisible(true);
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> subtitleColumn = new TableColumn<>("Subtitle");
        subtitleColumn.setCellValueFactory(new PropertyValueFactory<>("subTitle"));

        TableColumn<Book, ArrayList<String>> tagsColumn = new TableColumn<>("Tags");
        tagsColumn.setCellValueFactory(new PropertyValueFactory<>("tag"));

        TableColumn<Book, ArrayList<String>> authorsColumn = new TableColumn<>("Authors");
        authorsColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, ArrayList<String>> translatorsColumn = new TableColumn<>("Translators");
        translatorsColumn.setCellValueFactory(new PropertyValueFactory<>("translator"));

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<Book, String> publisherColumn = new TableColumn<>("Publisher");
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));

        TableColumn<Book, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Book, String> editionColumn = new TableColumn<>("Edition");
        editionColumn.setCellValueFactory(new PropertyValueFactory<>("edition"));

        TableColumn<Book, String> languageColumn = new TableColumn<>("Language");
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));

        TableColumn<Book, String> ratingColumn = new TableColumn<>("Rating");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        TableColumn<?, ?>[] columns = {
                titleColumn, isbnColumn,
                dateColumn, editionColumn, ratingColumn
        };
        adjustSize(bookTable, columns, 0.2);

        bookTable.getColumns().setAll(titleColumn, isbnColumn, dateColumn, editionColumn, ratingColumn);

        bookTable.getItems().clear();
        lib.setDisplayBooks(lib.searchBook(input, type, listedByTags));
        bookTable.getItems().addAll(lib.getDisplayBooks());

        forBookTablePadding.setPadding(new Insets(10));
        VBox.setVgrow(forBookTablePadding, Priority.ALWAYS);

        if (!root.getChildren().contains(forBookTablePadding)) {
            root.getChildren().add(forBookTablePadding);
        }

        HBox.setHgrow(bookTable, Priority.ALWAYS);
        VBox.setVgrow(bookTable, Priority.ALWAYS);

        bookTable.setOnMouseClicked(event -> {
            Object selectedObject = bookTable.getSelectionModel().getSelectedItem();
            if (selectedObject instanceof Book) {
                Book chosen = (Book) selectedObject;
                displayBookInfo(chosen);
            }
        });
    }

    private void adjustSize(TableView<?> table, TableColumn<?, ?>[] columns, double widthPercentage) {
        for (TableColumn<?, ?> column : columns) {
            column.prefWidthProperty().bind(table.widthProperty().multiply(widthPercentage));
        }
    }

    public void showManual() {
        HBox root = new HBox(10);
        root.setStyle("-fx-background-color: linear-gradient(to top right, #6fa8dc, #ffffff); ");
        root.setPadding(new Insets(10));
        VBox left = new VBox(10);
        VBox right = new VBox(10);

        Label welcomeLabel = new Label("Welcome to PagePal.");

        Label featuresTitle = new Label("Main Features:");
        Label featuresDetails = new Label(
                "1. Importing data\n" +
                        "2. Exporting data\n" +
                        "3. Searching through data\n" +
                        "4. Filtering By Tags\n"+
                        "5. Adding data\n" +
                        "6. Deleting data\n" +
                        "7. Editing data");

        Label importingDataTitle = new Label("Importing Data:");
        Label importingDataDetails = new Label("To import data, go to 'File' menu and choose 'Import Books'.");

        Label exportingDataTitle = new Label("Exporting Data:");
        Label exportingDataDetails = new Label("To export data, go to 'File' menu and choose 'Export Books'.");

        Label addingDataTitle = new Label("Adding Data:");
        Label addingDataDetails = new Label(
                "To add data, press 'Add Book' button.\n" +
                        "Then enter the information about the book.\n" +
                        "Lastly, press 'Add Book' at the bottom.");

        Label searchingDataTitle = new Label("Searching Data:");
        Label searchingDataDetails = new Label(
                "To search data, first select the parameter\n" +
                        "you want to search from the choice box.\n" +
                        "Then write what you want to search in the search bar.\n" +
                        "Finally, click the 'Search' button.");

        Label filteringByTags = new Label("Filtering by Tags:");
        Label filteringByTagDetails = new Label(
                "To filter by tags, you have to click on 'Tag Filter' button.\n"+
                "Then, select the tag(s) you want to filter by.\n" +
                "After that, hit 'List by Tags' button."

        );

        Label editingDeletingDataTitle = new Label("Editing and Deleting Data:");
        Label editingDeletingDataDetails = new Label(
                "To edit and delete data, first search the book.\n" +
                        "Then click on the book in the list you want to edit or delete.\n" +
                        "In the opening tab, at the bottom,\n" +
                        "there are two buttons for deleting and editing.\n" +
                        "By pressing them, you can either delete or edit the book information.");

        Label specialConditionsTitle = new Label("Special Conditions");
        Label specialConditionsType = new Label("Searching by Date");
        Label specialConditionsDate = new Label(
                                                    "Date can be searched in 4 different ways.\n"+
                                                    "1- Specific Date\n"+
                                                    "You can search a specific date with the pattern 'yyyy-mm-dd'.\n"+
                                                    "2- Searching by Year\n"+
                                                    "To search by year, it is enough to enter the year to search bar.\n"+
                                                    "3- Searching by Month\n"+
                                                    "To search by month, use the pattern '-mm-'.\n"+
                                                    "4- Searching by Day \n"+
                                                    "To search by day, use the pattern '-dd'.\n");

        left.getChildren().addAll(
                welcomeLabel,
                featuresTitle, featuresDetails,
                importingDataTitle, importingDataDetails,
                exportingDataTitle, exportingDataDetails,
                addingDataTitle, addingDataDetails,
                searchingDataTitle, searchingDataDetails,
                filteringByTags, filteringByTagDetails,
                editingDeletingDataTitle, editingDeletingDataDetails);
        right.getChildren().addAll(specialConditionsTitle,specialConditionsType,specialConditionsDate);
        left.setAlignment(Pos.CENTER);
        right.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(left,right);
        Scene scene = new Scene(root);
        manualStage.setScene(scene);
        manualStage.setTitle("User Manual");
        manualStage.show();
    }

    @Override
    public void start(Stage firstStage) {
        try {
            firstStage.getIcons().add(new Image("file:../app/icon.png"));
            secondStage.getIcons().add(new Image("file:../app/icon.png"));
            infoStage.getIcons().add(new Image("file:../app/icon.png"));
            manualStage.getIcons().add(new Image("file:../app/icon.png"));
            tagStage.getIcons().add(new Image("file:../app/icon.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        File fi = new File("library.json");
        lib.setFilePath("library.json");
        if (fi.exists()) {
            try {
                lib.importJSON(lib.getFilePath());
            } catch (Exception e) {
            }
        } else {
            try {
                fi.createNewFile();
            } catch (Exception e) {
            }
        }
        // This saves the file when we close the application.
        firstStage.setOnCloseRequest(e -> saveJSON());

        root.setPrefSize(640, 400);

        // MENU
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: linear-gradient(to top right, #6fa8dc, #ffffff); ");
        Menu fileMenu = new Menu("File");
        MenuItem importMenuItem = new MenuItem("Import Books");
        importMenuItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select JSON File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JSON Files", "*.json"));
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
        exportMenuItem.setOnAction(e -> lib.exportJSON("library.json"));
        MenuItem createMenuItem = new MenuItem("Create Library");
        createMenuItem.setOnAction(e -> {
            Gson gson = new Gson();

            lib.setFilePath("library.json");
            try {
                FileWriter writer = new FileWriter(lib.getFilePath());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });

        MenuItem deleteMenuItem = new MenuItem("Delete Library");
        deleteMenuItem.setOnAction(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Delete Library");
            alert.setContentText("You are about to delete 'library.json' file. Are you sure?");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    File toDelete = new File(lib.getFilePath());
                    if (toDelete.exists()) {
                        toDelete.delete();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        MenuItem clearMenuItem = new MenuItem("Clear All");
        clearMenuItem.setOnAction(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Clear All");
            alert.setContentText("You are about to clear all the data present. Are you sure?");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ArrayList<Book> toClear = new ArrayList<>();
                lib.setLibraryBooks(toClear);
                lib.setDisplayBooks(toClear);
            }
            searchResults(searchField.getText(), choiceBox.getValue());
        });

        MenuItem clearDMenuItem = new MenuItem("Clear Displayed");
        clearDMenuItem.setOnAction(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Clear Displayed");
            alert.setContentText("You are about to clear displayed books. Are you sure?");
            alert.setTitle("Warning");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ArrayList<Book> libTemp = lib.getLibraryBooks();
                ArrayList<Book> disTemp = lib.getDisplayBooks();
                libTemp.removeAll(disTemp);
                disTemp.clear();
                lib.setDisplayBooks(disTemp);
                lib.setLibraryBooks(libTemp);
            }
            searchResults(searchField.getText(), choiceBox.getValue());
        });

        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

        MenuItem saveMenuItem = new MenuItem("Save");
        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem(); // old three dot : …

        MenuItem quitMenuItem = new MenuItem("Quit");
        quitMenuItem.setOnAction(e -> {
            saveJSON();
            firstStage.close();
        });

        fileMenu.getItems().addAll(
                importMenuItem, exportMenuItem, createMenuItem, deleteMenuItem, clearDMenuItem, clearMenuItem,
                separatorMenuItem, saveMenuItem,
                separatorMenuItem2,
                quitMenuItem);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About PagePal");
        aboutMenuItem.setOnAction(e -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("About PagePal");
            alert.setContentText(
                    "This application is made by Ege Sevindi, Rusen Akbal, Arda Sari and Ege Yilmaz. It is the project of the course CE 216.");
            alert.setTitle("About");
            alert.showAndWait();
        });

        MenuItem manualMenuItem = new MenuItem("Manual");
        manualMenuItem.setOnAction(e -> showManual());
        helpMenu.getItems().addAll(aboutMenuItem, manualMenuItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);

        // What is this
        ContextMenu contextMenu = new ContextMenu(new MenuItem("Unspecified Action"));
        menuBar.setContextMenu(contextMenu);

        // main pane
        HBox firstLine = new HBox();
        firstLine.setAlignment(Pos.CENTER);

        HBox secondLine = new HBox(8);
        secondLine.setAlignment(Pos.CENTER);

        thirdLine.setSpacing(10);
        thirdLine.setAlignment(Pos.CENTER);

        Label labelPage = new Label("Page");
        labelPage.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        labelPage.setStyle("-fx-text-fill: #0079C1");

        Label labelPal = new Label("Pal");
        labelPal.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        labelPal.setStyle("-fx-text-fill: #00457C");

        searchField.setPrefWidth(180);
        searchField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        searchField.setStyle("-fx-background-radius: 35;");

        searchField.setBorder(new Border(
                new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(35), BorderStroke.THIN)));

        HBox.setHgrow(searchField, Priority.ALWAYS);

        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll("All Books", "Title", "Subtitle", "Author", "Translator", "ISBN", "Publisher", "Date",
                "Edition", "Language", "Rating");
        choiceBox.setItems(options);
        choiceBox.setValue("All Books");

        Label searchByLabel = new Label("Search by :");
        searchByLabel.setFont(new Font("Arial", 16));

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        searchButton.setFont(new Font(14));
        searchButton.setPrefSize(100.0, 45.0);

        Button addButton = new Button("Add Book");

        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        addButton.setPrefSize(100.0, 45.0);

        addButton.setOnAction(e -> genBookInfo(null, 0));
        searchButton.setOnAction(e -> searchResults(searchField.getText(), choiceBox.getValue()));

        Button tagsButton = new Button("Tag Filter");
        tagsButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        tagsButton.setFont(new Font(14));
        tagsButton.setPrefSize(100.0, 45.0);

        tagsButton.setOnAction(e -> displayTagsMenu());

        deListButton.setStyle(
            "-fx-background-color: #ff3333; " +
                    "-fx-background-radius: 5; " +
                    "-fx-text-fill: white; " +
                    "-fx-padding: 5 10; " +
                    "-fx-font-size: 14px;");
        deListButton.setFont(new Font(14));
        deListButton.setPrefSize(120.0, 45.0);
        deListButton.setOnAction(e -> {
            listedByTags = false;
            thirdLine.getChildren().remove(deListButton);
            bookTable.setVisible(false);
        });

        root.setStyle("-fx-background-color: linear-gradient(to top right, #6fa8dc, #ffffff);");
        VBox.setMargin(firstLine, new Insets(10));
        VBox.setMargin(secondLine, new Insets(10));
        VBox.setMargin(thirdLine, new Insets(10));

        firstLine.setSpacing(2);
        secondLine.setSpacing(10);
        thirdLine.setSpacing(10);

        firstLine.getChildren().addAll(labelPage, labelPal);
        secondLine.getChildren().addAll(searchField, searchByLabel, choiceBox);
        thirdLine.getChildren().addAll(tagsButton, searchButton, addButton);

        root.getChildren().addAll(menuBar, firstLine, secondLine, thirdLine);

        firstStage.setHeight(480);
        firstStage.setWidth(1050);
        Scene scene = new Scene(root, Color.BEIGE);
        firstStage.setScene(scene);
        firstStage.setTitle("PagePal");
        firstStage.show();

    }

    public void saveJSON() {
        File file = new File("library.json");
        if (file.exists()) {
            if (lib.getLibraryBooks().size() != 0) {
                try {
                    lib.exportJSON("library.json");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                file.delete();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
