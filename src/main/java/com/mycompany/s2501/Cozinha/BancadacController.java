package com.mycompany.s2501.Cozinha;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Moeda;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class BancadacController {

    @FXML
    private AnchorPane root;

    @FXML
    private void initialize() {
        Moeda.adicionarMoeda(root, "moeda_bancadac_1", 50, 352, 30);
    }

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
