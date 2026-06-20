package com.mycompany.s2501.Quarto;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class ArmarioqActController {

    @FXML
    private void voltar() throws IOException {
        App.setRoot("quarto");
    }    
    
    @FXML
    private void fechar() throws IOException {
        App.setRoot("armarioq");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}