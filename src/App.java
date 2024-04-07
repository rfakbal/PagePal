
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    
    
    
    private void btnOkDetect(){
        
        //display stage
        Stage secondStage = new Stage();
        
        VBox root = new VBox();
        HBox main = new HBox(20);
        root.setPrefSize(400, 500);

        
        VBox leftBox = new VBox(8);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        VBox rightBox = new VBox(8);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        //LEFT////////////////////////////////////////////////////////////////////////////

        Label titleLabel = new Label("Title");
        titleLabel.setFont(new Font(25));

        TextField titleTextField = new TextField();
        titleTextField.setPrefWidth(180);

        Label authorLabel = new Label("Author/Authors");
        authorLabel.setFont(new Font(25));

        TextField auTextField = new TextField();
        auTextField.setPrefWidth(180);

        Label isbnLabel = new Label("ISBN");
        isbnLabel.setFont(new Font(25));

        TextField isbnTextField = new TextField();
        isbnTextField.setPrefWidth(180);

        Label dateLabel = new Label("Date");
        dateLabel.setFont(new Font(25));

        DatePicker datepPicker = new DatePicker();

        Label coverLabel = new Label("Cover");
        coverLabel.setFont(new Font(25));

        //upload thing

        Label ratingLabel = new Label("Rating");
        ratingLabel.setFont(new Font(25));

        TextField ratingTextField = new TextField();
        ratingTextField.setPrefWidth(180);



        //right/////////////////////////////////////////////////////

        Label subtitle = new Label("Subtitle");
        subtitle.setFont(new Font(25));

        TextField subtitleTextField = new TextField();
        subtitleTextField.setPrefWidth(180);

        Label translatorLabel = new Label("Translator/Translators");
        translatorLabel.setFont(new Font(25));

        TextField translatorTextField = new TextField();
        translatorTextField.setPrefWidth(180);

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

        TextField tagTextField = new TextField();
        tagTextField.setPrefWidth(180);

    


        VBox.setMargin(leftBox, new Insets(8));
        VBox.setMargin(rightBox, new Insets(8));
        VBox.setMargin(main, new Insets(8));
    



        leftBox.getChildren().addAll(titleLabel,titleTextField,authorLabel,auTextField,isbnLabel,isbnTextField,dateLabel,datepPicker,coverLabel,ratingLabel,ratingTextField);

        rightBox.getChildren().addAll(subtitle,subtitleTextField,translatorLabel,translatorTextField,publisherLabel,publisherField,editionLabel,editionField,languagLabel,languageField,tagLabel,tagTextField);

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

        VBox thirdLine = new VBox();
        thirdLine.setAlignment(Pos.CENTER);
        
        
        Label label = new Label("Book Search");
        label.setFont(new Font(25));
        
        
        TextField textField = new TextField();
        textField.setPrefWidth(180);

        HBox.setHgrow(textField, Priority.ALWAYS);
        
        
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        
        
        Label searchByLabel = new Label("Search by :");
        
        
        Button addButton = new Button("Add Book");
        addButton.setFont(new Font(14));
        addButton.setPrefSize(100.0, 45.0);

        addButton.setOnAction(e->btnOkDetect());
        
        VBox.setMargin(firstLine, new Insets(8));
        VBox.setMargin(secondLine, new Insets(8));
        VBox.setMargin(thirdLine, new Insets(8));
        
        
        firstLine.getChildren().addAll(label);
        secondLine.getChildren().addAll(textField,searchByLabel,choiceBox);
        thirdLine.getChildren().addAll(addButton);

        
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
