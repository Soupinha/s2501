package com.mycompany.s2501.Fora;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class CaminhoController {

    @FXML
    private void irpraca() throws IOException {
        App.setRoot("praca");
    }
    @FXML
    private void voltar() throws IOException {
        App.setRoot("fora");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}