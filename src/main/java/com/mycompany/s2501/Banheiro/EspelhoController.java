package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class EspelhoController {

    @FXML
    private void abrir() throws IOException {
        App.setRoot("armarioe");
    }
    @FXML
    private void voltar() throws IOException {
        App.setRoot("pia");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}