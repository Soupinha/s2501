package com.mycompany.s2501.Fora;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class ForaController {

    @FXML
    private void irpraca() throws IOException {
        App.setRoot("caminho");
    }
    @FXML
    private void irrua1() throws IOException {
        App.setRoot("rua1");
    }
    @FXML
    private void voltar() throws IOException {
        App.setRoot("corredorprincipal");
    }
    @FXML
    private void irrua2() throws IOException {
        App.setRoot("rua2");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    } 
    
    @FXML
    private Button botaoContinuar;
     
    @FXML
    private Label floatingText;

    private final String dialogoId = "fora_inicio";

    private String[] falas = {
        "Está frio..."
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