package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Moeda;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ArmariobActController {

    @FXML
    private AnchorPane root;

    @FXML
    private void initialize() {
        Moeda.adicionarMoeda(root, "moeda_armariob_act_1", 208, 404, 45);
        Moeda.adicionarMoeda(root, "moeda_armariob_act_3", 560, 626, 45);
    }

    @FXML
    private void fechar() throws IOException {
        App.setRoot("armariob");
    }
    @FXML
    private void irremedio() throws IOException {
        App.setRoot("remedio");
    }
    @FXML
    private void voltar() throws IOException {
        App.setRoot("pia");
    }
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}
