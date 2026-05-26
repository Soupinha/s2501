package com.mycompany.s2501.Fora;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class Rua1Controller {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}