package com.mycompany.s2501.Cozinha;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class CozinhaController {
     @FXML
    private void irgeladeira() throws IOException {
        App.setRoot("geladeira");
    }
    @FXML
    private void irdespensa() {
        App.abrirLoja();
    }
     @FXML
    private void irbancadac() throws IOException {
        App.setRoot("bancadac");
    }
  
    @FXML
    private void voltar() throws IOException {
        App.setRoot("corredorprincipal");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}