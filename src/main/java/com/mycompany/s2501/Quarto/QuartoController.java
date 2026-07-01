package com.mycompany.s2501.Quarto;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

//tudo okay
public class QuartoController {
    
    @FXML
    private void ircorredor() throws IOException {
        App.setRoot("corredor");
    }
    @FXML
    private void ircriado() throws IOException {
        
        App.setRoot("criado");
    }
    @FXML
    private void irarmario() throws IOException {
        
        App.setRoot("armarioq");
    }
    
    @FXML
    private Button botaoContinuar;
     
    @FXML
    private Label floatingText;

    private final String dialogoId = "quarto_inicio";

    private String[] falas = {
        "... Onde estou?",
        "Eu conheço esse lugar?"
    };

    @FXML
    public void initialize() {
        App.iniciarDialogoUmaVez(dialogoId, floatingText, botaoContinuar, falas);
    }

    @FXML
    public void proximaFrase() {
        App.proximaFraseDialogo(dialogoId, floatingText, botaoContinuar, falas);
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
    
}
