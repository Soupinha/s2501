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
        App.setRoot("praca");
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

    // Frases
    private String[] falas = {
        "Que frio..."
       
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
    
}