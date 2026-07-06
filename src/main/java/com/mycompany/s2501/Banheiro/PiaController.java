package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class PiaController {

    @FXML
    private void espelho() throws IOException {
        App.setRoot("espelho");
    }
         @FXML
    private void abrirarmario() throws IOException {
        App.setRoot("armariob");
    }
    @FXML
    private void voltar() throws IOException {
        App.setRoot("banheiro");
    }
        @FXML
    private void abrir() throws IOException {
        App.setRoot("gaveta");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
    
}