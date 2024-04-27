package org.example.pagepal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage; 

public class Controller {

    @FXML
    private TextField tfName;
    
    private Stage mainWindow; 

    public void setMainWindow(Stage mainWindow) {
        this.mainWindow = mainWindow;
    }


    @FXML
    void genBookInfo(ActionEvent event) {
        String title = tfName.getText();
        mainWindow.setTitle(title);
    }

}
    

