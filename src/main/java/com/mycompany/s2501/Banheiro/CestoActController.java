package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class CestoActController {
    @FXML
    private void encher() throws IOException {
        App.setRoot("cesto");
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