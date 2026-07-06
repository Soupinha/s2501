package com.mycompany.s2501;

import com.mycompany.s2501.Model.EstadoJogo;
import com.mycompany.s2501.Model.InventarioJogador;
import com.mycompany.s2501.dao.JogadorDAO;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MenuController {

    @FXML
    private AnchorPane painelCreditos;

    @FXML
    private AnchorPane fadePane;

    private boolean iniciandoJogo = false;

    @FXML
    private void initialize() {
        if (painelCreditos != null) {
            painelCreditos.setVisible(false);
        }

        if (fadePane != null) {
            fadePane.setVisible(false);
            fadePane.setOpacity(0);
        }
    }

    @FXML
    private void comecar() {
        if (iniciandoJogo) {
            return;
        }

        iniciandoJogo = true;

        if (fadePane == null) {
            iniciarPartidaNoQuarto();
            return;
        }

        fadePane.setVisible(true);
        fadePane.setOpacity(0);
        fadePane.toFront();

        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), fadePane);
        fade.setFromValue(0);
        fade.setToValue(1);

        fade.setOnFinished(event -> {
            iniciarPartidaNoQuarto();
        });

        fade.play();
    }

    private void iniciarPartidaNoQuarto() {
        try {
            App.resetarDialogosDaPartida();

            InventarioJogador.limpar();

            JogadorDAO jogadorDAO = new JogadorDAO();
            int jogadorId = jogadorDAO.criarJogador();

            EstadoJogo.iniciarNovaPartida(jogadorId);

            App.setRoot("quarto");

        } catch (IOException e) {
            e.printStackTrace();
            iniciandoJogo = false;
        }
    }

    @FXML
    private void abrirRanking() throws IOException {
        App.setRoot("ranking");
    }

    @FXML
    private void abrirCreditos() {
        if (painelCreditos != null) {
            painelCreditos.setVisible(true);
            painelCreditos.toFront();
        }
    }

    @FXML
    private void fecharCreditos() {
        if (painelCreditos != null) {
            painelCreditos.setVisible(false);
        }
    }

    @FXML
    private void encerrarJogo() {
        App.encerrarJogo();
    }
}