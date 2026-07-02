package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
public class EspelhoController {

    @FXML
    private void abrir() throws IOException {
        App.setRoot("armarioe");
    }
    @FXML
    private void voltar() throws IOException {
        App.setRoot("pia");
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
        "... Eu sou assim?"
       
    };

    private final String dialogoId = "espelho_inicio";

    @FXML
    public void initialize() {
        App.iniciarDialogoUmaVez(dialogoId, floatingText, botaoContinuar, falas);
    }

    @FXML
    public void proximaFrase() {
        App.proximaFraseDialogo(dialogoId, floatingText, botaoContinuar, falas);
    }  
}