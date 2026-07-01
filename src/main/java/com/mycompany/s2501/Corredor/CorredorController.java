package com.mycompany.s2501.Corredor;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class CorredorController {

    @FXML
    private void irquartot() throws IOException {
        App.setRoot("quartotrancado");
    }
    @FXML
    private void irbanheiro() throws IOException {
        App.setRoot("banheiro");
    }
    @FXML
    private void descerescada() throws IOException {
        App.setRoot("corredorprincipal");
    }
    @FXML
    private void voltar() throws IOException {
        App.setRoot("quarto");
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
        "Tem alguém ai?"
       
    };

    private final String dialogoId = "corredor_inicio";

    @FXML
    public void initialize() {
        App.iniciarDialogoUmaVez(dialogoId, floatingText, botaoContinuar, falas);
    }

    @FXML
    public void proximaFrase() {
        App.proximaFraseDialogo(dialogoId, floatingText, botaoContinuar, falas);
    }
    
}