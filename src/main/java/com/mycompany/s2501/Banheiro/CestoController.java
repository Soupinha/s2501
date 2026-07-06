package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class CestoController {
    @FXML
    private void esvaziar() throws IOException {
        App.setRoot("cestoact");
    }
    @FXML
    private void voltar() throws IOException {
        App.setRoot("banheiro");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}