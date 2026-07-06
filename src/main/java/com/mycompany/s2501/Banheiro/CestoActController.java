package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Moeda;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class CestoActController {

    @FXML
    private AnchorPane root;

    @FXML
    private void initialize() {

        Moeda.adicionarMoeda(root, "moeda_cesto_act_2", 707, 560, 30);
    }

    @FXML
    private void encher() throws IOException {
        App.setRoot("cesto");
    }
    @FXML
    private void voltar() throws IOException {
        App.setRoot("banheiro");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}
