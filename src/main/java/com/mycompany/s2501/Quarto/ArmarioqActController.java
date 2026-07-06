package com.mycompany.s2501.Quarto;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Moeda;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ArmarioqActController {

    @FXML
    private AnchorPane root;

    @FXML
    private void initialize() {
        Moeda.adicionarMoeda(root, "moeda_armarioq_act_1", 435, 397, 30);
        Moeda.adicionarMoeda(root, "moeda_armarioq_act_2", 400, 200, 30);
        Moeda.adicionarMoeda(root, "moeda_armarioq_act_3", 454, 536, 30);
        Moeda.adicionarMoeda(root, "moeda_armarioq_act_4", 570, 612, 30);
    }

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
