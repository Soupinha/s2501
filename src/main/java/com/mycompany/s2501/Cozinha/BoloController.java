
package com.mycompany.s2501.Cozinha;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Moeda;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class BoloController {

    @FXML
    private AnchorPane root;

      @FXML
    private void voltar() throws IOException {
        App.setRoot("bancadac");
    }
     @FXML
    private void irrecado() throws IOException {
        App.setRoot("recado");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
    
    @FXML
    private Button botaoContinuar;
     
    @FXML
    private Label floatingText;

    // Frases
    private String[] falas = {
        "Está mofado...",
        "O que é isso?"
       
    };

    private final String dialogoId = "bolo_inicio";

    @FXML
    public void initialize() {
        Moeda.adicionarMoeda(root, "moeda_bolo_1", 255, 128,60);
        App.iniciarDialogoUmaVez(dialogoId, floatingText, botaoContinuar, falas);
    }

    @FXML
    public void proximaFrase() {
        App.proximaFraseDialogo(dialogoId, floatingText, botaoContinuar, falas);
    }
    
}
