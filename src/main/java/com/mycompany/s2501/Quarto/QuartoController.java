package com.mycompany.s2501.Quarto;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.util.Duration;


public class QuartoController {
    @FXML
private Label floatingText;
    
    @FXML
    private void ircorredor() throws IOException {
        App.setRoot("corredor");
    }
    @FXML
    private void lerdiario() throws IOException {
        
        App.setRoot("diario");
    }
    @FXML
    private void abrirarmario() throws IOException {
        
        App.setRoot("armarioq");
    }
    
     @FXML
    private Button botaoContinuar;

    // Frases
    private String[] falas = {
        "Esse cheiro...",
        "Eu conheço esse lugar?",
        "O que aconteceu aqui?"
    };

    private int indice = 0;

    @FXML
    public void initialize() {

        mostrarTextoLento(falas[indice]);
    }

    public void mostrarTextoLento(String texto) {

        floatingText.setText("");

        // Esconde botão enquanto escreve
        botaoContinuar.setVisible(false);

        Timeline timeline = new Timeline();

        for (int i = 0; i < texto.length(); i++) {

            final int index = i;

            KeyFrame keyFrame = new KeyFrame(
                Duration.millis(40 * i),

                e -> {
                    floatingText.setText(
                        texto.substring(0, index + 1)
                    );
                }
            );

            timeline.getKeyFrames().add(keyFrame);
        }

        // Quando terminar
        timeline.setOnFinished(e -> {

            botaoContinuar.setVisible(true);
        });

        timeline.play();
    }

    @FXML
    public void proximaFrase() {

        indice++;

        if (indice < falas.length) {

            mostrarTextoLento(falas[indice]);

        } else {

            // Acabaram as frases
            floatingText.setVisible(false);
            botaoContinuar.setVisible(false);
        }
    }
    
  
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
    
}
