package com.mycompany.s2501.Sala;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
    //tudo okay
public class SalaController {
    
    @FXML
    private void voltar() throws IOException {
        App.setRoot("corredorprincipal");
    }
    @FXML
    private void sentar() throws IOException {
        App.setRoot("sofa");
    }
    @FXML
    private void verj() throws IOException {
        App.setRoot("janela");
    }
    @FXML
    private void verf() throws IOException {
        App.setRoot("foto");
    }
    @FXML
    private void abrir() throws IOException {
        App.setRoot("armarios");
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
        "... Onde estou?",
        "Eu conheço esse lugar?"
       
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