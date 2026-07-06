package com.mycompany.s2501.Sala;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class ArmariosController {
    
    @FXML
    private void voltar() throws IOException {
        App.setRoot("sala");
    }
    
    @FXML
    private void abrir() throws IOException {
        App.setRoot("armariosact");
    }
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}