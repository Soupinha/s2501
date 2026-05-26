package com.mycompany.s2501.Sala;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class SalaController {

    @FXML
    private void voltarcorredorp() throws IOException {
        App.setRoot("corredorprincipal");
    }
    @FXML
    private void sentarsofa() throws IOException {
        App.setRoot("sofa");
    }
    @FXML
    private void verjanela() throws IOException {
        App.setRoot("janela");
    }
    @FXML
    private void verfoto() throws IOException {
        App.setRoot("foto");
    }
    @FXML
    private void abrirarmario() throws IOException {
        App.setRoot("armarios");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}