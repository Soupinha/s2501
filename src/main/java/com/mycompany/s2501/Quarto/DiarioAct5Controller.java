package com.mycompany.s2501.Quarto;


import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class DiarioAct5Controller {

    @FXML
    private void voltar() throws IOException {
        App.setRoot("criado");
    }    
    
    @FXML
    private void virarpagina() throws IOException {
        App.setRoot("diarioact6");
    }
    @FXML
    private void voltarpagina() throws IOException {
        App.setRoot("diarioact4");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}