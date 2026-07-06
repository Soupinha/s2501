package com.mycompany.s2501.Corredor;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.mycompany.s2501.Model.EstadoJogo;

public class QuartoTrancadoController {

    @FXML
    private void irfinal() throws IOException {
        if (EstadoJogo.isQuartoDesbloqueado()) {
            App.setRoot("final");
        } else {
            App.pontuarEventoUmaVez("TENTOU_QUARTO_TRANCADO", 5);
            floatingText.setVisible(true);
            floatingText.setText("Ainda falta alguma coisa...");
        }
    }
    @FXML
    private void voltar() throws IOException {
        App.setRoot("corredor");
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
   

    private final String dialogoId = "quartot_inicio"; 
    private String[] falas = {
        "..."
       
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