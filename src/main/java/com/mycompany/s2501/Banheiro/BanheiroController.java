package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class BanheiroController {

    @FXML
    private void irpia() throws IOException {
        App.setRoot("pia");
    }
    @FXML
    private void irchuveiro() throws IOException {
        App.setRoot("chuveiro");
    }
    @FXML
    private void ircesto() throws IOException {
        App.setRoot("cesto");
    }
    @FXML
    private void voltar() throws IOException {
        App.setRoot("corredor");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
    
}