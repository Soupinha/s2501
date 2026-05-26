package com.mycompany.s2501.CorredorP;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class CorredorPrincipalController {

    @FXML
    private void ircozinha() throws IOException {
        App.setRoot("cozinha");
    }
    @FXML
    private void irsala() throws IOException {
        App.setRoot("sala");
    }
    @FXML
    private void voltarcorredor() throws IOException {
        App.setRoot("corredor");
    }
    @FXML
    private void irfora() throws IOException {
        App.setRoot("fora");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}