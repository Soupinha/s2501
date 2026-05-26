package com.mycompany.s2501.Fora;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class ForaController {

    @FXML
    private void irpraca() throws IOException {
        App.setRoot("praca");
    }
    @FXML
    private void irrua1() throws IOException {
        App.setRoot("rua1");
    }
    @FXML
    private void voltarcorredorp() throws IOException {
        App.setRoot("corredorprincipal");
    }
    @FXML
    private void irrua2() throws IOException {
        App.setRoot("rua2");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}