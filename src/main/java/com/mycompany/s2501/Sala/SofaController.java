package com.mycompany.s2501.Sala;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Model.EstadoJogo;
import com.mycompany.s2501.Moeda;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class SofaController {

    @FXML
    private Button sofa;
    private AnchorPane root;

    @FXML
    private void initialize() {
        if (EstadoJogo.runJaVencida("RUN_TV")) {
            sofa.setVisible(false);
            sofa.setDisable(true);
        }
        
    }

    @FXML
    private void tv() {
        if (EstadoJogo.runJaVencida("RUN_TV")) {
            sofa.setVisible(false);
            sofa.setDisable(true);
            return;
        }

        sofa.setVisible(false);
        sofa.setDisable(true);

        App.abrirRunTV();
    }

    @FXML
    private void voltar() throws IOException {
        App.setRoot("sala");
    }

    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}