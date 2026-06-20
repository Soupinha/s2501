package com.mycompany.s2501.Quarto;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class ArmarioqController {

    @FXML
    private void voltar() throws IOException {
        App.setRoot("quarto");
    }    
    
    @FXML
    private void abrir() throws IOException {
        App.setRoot("armarioqact");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}