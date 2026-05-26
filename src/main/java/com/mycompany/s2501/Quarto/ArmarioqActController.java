package com.mycompany.s2501.Quarto;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class ArmarioqActController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}