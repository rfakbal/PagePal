
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
        firstLine.setAlignment(Pos.BOTTOM_LEFT);

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
