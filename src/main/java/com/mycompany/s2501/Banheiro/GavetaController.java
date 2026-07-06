package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Moeda;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class GavetaController {

    @FXML
    private AnchorPane root;

    @FXML
    private void initialize() {
        Moeda.adicionarMoeda(root, "moeda_gaveta_banheiro_1", 303, 510, 31);
        Moeda.adicionarMoeda(root, "moeda_gaveta_banheiro_2", 630, 532, 31);
        Moeda.adicionarMoeda(root, "moeda_gaveta_banheiro_3", 745, 536, 31);
        Moeda.adicionarMoeda(root, "moeda_gaveta_banheiro_4", 435, 550, 31);
    }

    @FXML
    private void fechar() throws IOException {
        App.setRoot("pia");
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
