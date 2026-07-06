package com.mycompany.s2501.Cozinha;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Moeda;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class GeladeiraActController {

    @FXML
    private AnchorPane root;

    @FXML
    private void fechar() throws IOException {
        App.setRoot("geladeira");
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
