package com.mycompany.s2501;


import java.io.IOException;
import javafx.fxml.FXML;
public class MenuController {
    

    @FXML
    private void comecar() throws IOException {
        App.setRoot("quarto");
    }
}