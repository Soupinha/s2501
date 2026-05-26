package com.mycompany.s2501.Corredor;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class CorredorController {

    @FXML
    private void irquartot() throws IOException {
        App.setRoot("quartotrancado");
    }
    @FXML
    private void irbanheiro() throws IOException {
        App.setRoot("banheiro");
    }
    @FXML
    private void descerescada() throws IOException {
        App.setRoot("corredorprincipal");
    }
    @FXML
    private void voltarquarto() throws IOException {
        App.setRoot("quarto");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
    
    
}