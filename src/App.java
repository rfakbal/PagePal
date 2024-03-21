
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        root.setPrefSize(640, 400);
        
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem openMenuItem = new MenuItem("Open…");
        Menu openRecentMenu = new Menu("Open Recent");
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        MenuItem closeMenuItem = new MenuItem("Close");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem saveAsMenuItem = new MenuItem("Save As…");
        MenuItem revertMenuItem = new MenuItem("Revert");
        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();
        MenuItem preferencesMenuItem = new MenuItem("Preferences…");
        SeparatorMenuItem separatorMenuItem3 = new SeparatorMenuItem();
        MenuItem quitMenuItem = new MenuItem("Quit");
        
        fileMenu.getItems().addAll(
            newMenuItem, openMenuItem, openRecentMenu,
            separatorMenuItem, closeMenuItem, saveMenuItem,
            saveAsMenuItem, revertMenuItem, separatorMenuItem2,
            preferencesMenuItem, separatorMenuItem3, quitMenuItem
        );
        
        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About MyHelloApp");
        helpMenu.getItems().add(aboutMenuItem);
        
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        
        ContextMenu contextMenu = new ContextMenu(new MenuItem("Unspecified Action"));
        menuBar.setContextMenu(contextMenu);
        
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        
        Label label = new Label("Book Search");
        label.setFont(new Font(18));
        AnchorPane.setLeftAnchor(label, 142.0);
        AnchorPane.setTopAnchor(label, 103.0);
        
        TextField textField = new TextField();
        AnchorPane.setLeftAnchor(textField, 142.0);
        AnchorPane.setTopAnchor(textField, 131.0);
        
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        AnchorPane.setLeftAnchor(choiceBox, 383.0);
        AnchorPane.setTopAnchor(choiceBox, 132.0);
        
        Label searchByLabel = new Label("Search by");
        AnchorPane.setLeftAnchor(searchByLabel, 393.0);
        AnchorPane.setTopAnchor(searchByLabel, 135.0);
        
        Button addButton = new Button("Add Book");
        addButton.setFont(new Font(14));
        addButton.setPrefSize(108.0, 46.0);
        AnchorPane.setLeftAnchor(addButton, 262.0);
        AnchorPane.setTopAnchor(addButton, 187.0);
        
        anchorPane.getChildren().addAll(label, textField, choiceBox, searchByLabel, addButton);
        
        root.getChildren().addAll(menuBar, anchorPane);
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("My Hello App");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
