package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class ChuveiroActController {

    @FXML
    private void abrir() throws IOException {
        App.setRoot("primary");
    }
}