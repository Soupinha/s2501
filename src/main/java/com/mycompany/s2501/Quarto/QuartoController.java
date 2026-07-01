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

    private String dialogoIdAtivo;
    private String[] falasAtivas;

    private final String[] falasQuartoInicio = {
        "... Onde estou?",
        "Eu conheço esse lugar?"
    };

    private final String[] falasPerdaRunPraca = {
        "... De novo aqui."
    };

    @FXML
    public void initialize() {
        if (App.consumirDialogoPerdaRunPraca()) {
            dialogoIdAtivo = "quarto_perda_run_praca";
            falasAtivas = falasPerdaRunPraca;

            App.iniciarDialogoSituacional(dialogoIdAtivo, floatingText, botaoContinuar, falasAtivas);
        } else {
            dialogoIdAtivo = "quarto_inicio";
            falasAtivas = falasQuartoInicio;

            App.iniciarDialogoUmaVez(dialogoIdAtivo, floatingText, botaoContinuar, falasAtivas);
        }
    }

    @FXML
    public void proximaFrase() {
        App.proximaFraseDialogo(dialogoIdAtivo, floatingText, botaoContinuar, falasAtivas);
    }
    
        @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}
