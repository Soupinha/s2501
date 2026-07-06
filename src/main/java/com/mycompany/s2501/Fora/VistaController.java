package com.mycompany.s2501.Fora;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class VistaController {
    @FXML
    private void voltar() throws IOException {
        App.setRoot("banco");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}