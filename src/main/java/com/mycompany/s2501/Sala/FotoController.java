package com.mycompany.s2501.Sala;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class FotoController {

       @FXML
    private void voltar() throws IOException {
        App.setRoot("bancadas");
    }   
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
    
    
}