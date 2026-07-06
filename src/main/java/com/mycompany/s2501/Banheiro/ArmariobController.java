package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class ArmariobController {

    @FXML
    private void abrir() throws IOException {
        App.setRoot("armariobact");
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