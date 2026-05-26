package com.mycompany.s2501.Sala;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class BancadasController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}