package com.mycompany.s2501.Corredor;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class FinalController {

    @FXML
    private AnchorPane painelPreto;

    @FXML
    private Label fraseFinalLabel;

    @FXML
    private Button botaoContinuarFinal;

    private final String dialogoId = "final_tela_preta";

    private final String[] falas = {
        "Eu já estou morta?",
        "Isso foi real?"
    };

    @FXML
    public void initialize() {
        painelPreto.setVisible(false);
        fraseFinalLabel.setText("");
        fraseFinalLabel.setOpacity(1);
        botaoContinuarFinal.setVisible(false);

        Timeline esperaFinal = new Timeline(new KeyFrame(Duration.seconds(10), event -> iniciarTelaPreta()));
        esperaFinal.setCycleCount(1);
        esperaFinal.play();
    }

    private void iniciarTelaPreta() {
        painelPreto.setVisible(true);
        painelPreto.setOpacity(0);

        FadeTransition fadePreto = new FadeTransition(Duration.seconds(2.5), painelPreto);
        fadePreto.setFromValue(0);
        fadePreto.setToValue(1);

        fadePreto.setOnFinished(event -> {
            App.iniciarDialogoSituacional(dialogoId, fraseFinalLabel, botaoContinuarFinal, falas);
        });

        fadePreto.play();
    }

    @FXML
    private void proximaFraseFinal() {
        App.proximaFraseDialogo(dialogoId, fraseFinalLabel, botaoContinuarFinal, falas);

        if (!botaoContinuarFinal.isVisible()) {
            finalizarEIrRanking();
        }
    }

    private void finalizarEIrRanking() {
        PauseTransition pausa = new PauseTransition(Duration.seconds(1.5));

        pausa.setOnFinished(event -> {
            App.finalizarPartidaEIrRanking();
        });

        pausa.play();
    }
}