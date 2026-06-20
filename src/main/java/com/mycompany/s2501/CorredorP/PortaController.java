package com.mycompany.s2501.CorredorP;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class PortaController {

    @FXML
    private void voltar() throws IOException {
        App.setRoot("corredorprincipal");
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