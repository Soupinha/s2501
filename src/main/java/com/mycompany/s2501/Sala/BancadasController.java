package com.mycompany.s2501.Sala;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Moeda;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class BancadasController {

    @FXML
    private AnchorPane root;

    @FXML
    private void initialize() {
        Moeda.adicionarMoeda(root, "moeda_bancadas_sala_2", 920, 522, 45);
    }
    
    @FXML
    private void voltar() throws IOException {
        App.setRoot("sala");
    }
    
    @FXML
    private void irfoto() throws IOException {
        App.setRoot("foto");
    }
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}
