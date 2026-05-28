package com.mycompany.s2501.Quarto;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class CriadoController {

    @FXML
    private void voltar() throws IOException {
        App.setRoot("quarto");
    }    
    
    @FXML
    private void verdiario() throws IOException {
        App.setRoot("diario");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }

}