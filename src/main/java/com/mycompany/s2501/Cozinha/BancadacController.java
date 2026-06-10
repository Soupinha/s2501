package com.mycompany.s2501.Cozinha;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class BancadacController {

      @FXML
    private void irlouca() throws IOException {
        App.setRoot("louca");
    }
     @FXML
    private void irbolo() throws IOException {
        App.setRoot("bolo");
    }
  
    @FXML
    private void voltar() throws IOException {
        App.setRoot("cozinha");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}