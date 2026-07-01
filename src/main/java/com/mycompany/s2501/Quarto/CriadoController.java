package com.mycompany.s2501.Quarto;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class CriadoController {

    @FXML
    private void voltar() throws IOException {
        App.setRoot("quarto");
    }    
    
    @FXML
    private void verdiario() throws IOException {
        App.setRoot("diario");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }

    
    @FXML
    private Button botaoContinuar;
     
    @FXML
    private Label floatingText;

    private final String dialogoId = "criado_inicio";

    private String[] falas = {
        "Esse diário é meu?"
    };

    @FXML
    public void initialize() {
        App.iniciarDialogoUmaVez(dialogoId, floatingText, botaoContinuar, falas);
    }

    @FXML
    public void proximaFrase() {
        App.proximaFraseDialogo(dialogoId, floatingText, botaoContinuar, falas);
    }
}