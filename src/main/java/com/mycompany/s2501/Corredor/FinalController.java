package com.mycompany.s2501.Corredor;

import com.mycompany.s2501.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class FinalController {


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