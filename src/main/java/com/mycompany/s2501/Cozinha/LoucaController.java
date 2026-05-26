package com.mycompany.s2501.Cozinha;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class LoucaController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}