package com.mycompany.s2501.Cozinha;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class GeladeiraController {

    @FXML
    private void abrir() throws IOException {
        App.setRoot("geladeiraact");
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