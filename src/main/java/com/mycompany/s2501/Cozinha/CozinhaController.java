package com.mycompany.s2501.Cozinha;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class CozinhaController {

    @FXML
    private void voltarcorredorp() throws IOException {
        App.setRoot("corredorprincipal");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}