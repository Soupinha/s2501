package com.mycompany.s2501.Sala;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Moeda;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ArmariosActController {

    @FXML
    private AnchorPane root;

    @FXML
    private void initialize() {
        Moeda.adicionarMoeda(root, "moeda_armarios_sala_1", 246, 588, 45);
        Moeda.adicionarMoeda(root, "moeda_armarios_sala_3", 888, 572, 45);
    }
    
    @FXML
    private void voltar() throws IOException {
        App.setRoot("sala");
    }
    
    @FXML
    private void fechar() throws IOException {
        App.setRoot("armarios");
    }
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}
