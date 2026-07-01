package com.mycompany.s2501;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    private static Stage primaryStage;
    private static Stage inventarioStage;
    private static Stage lojaStage;
    private static Stage runPracaStage;
    private static boolean runPracaAbrindo = false;
    private static final Set<String> dialogosJaIniciados = new HashSet<>();
    private static final Map<String, Integer> indicesDialogo = new HashMap<>();
    private static final Map<String, Timeline> timelinesDialogo = new HashMap<>();    
    private static boolean dialogoPerdaRunPracaPendente = false;

    private static InventarioController inventarioController;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        scene = new Scene(loadFXML("menu"), 1200, 800);

        stage.setScene(scene);
        stage.setTitle("Jogo");
        stage.setResizable(false);
        stage.show();
    }
    
    public static void abrirRunPraca() {
    try {
        if (runPracaAbrindo) {
            return;
        }

        if (runPracaStage != null && runPracaStage.isShowing()) {
            runPracaStage.toFront();
            return;
        }

        runPracaAbrindo = true;

        FXMLLoader loader = new FXMLLoader(App.class.getResource("runPraca.fxml"));
        Parent root = loader.load();

        runPracaStage = new Stage();
        runPracaStage.setTitle("Run Praça");
        runPracaStage.setScene(new Scene(root, 1200, 400));
        runPracaStage.setResizable(false);

        runPracaStage.initOwner(primaryStage);
        runPracaStage.initModality(Modality.WINDOW_MODAL);

        runPracaStage.setOnHidden(event -> {
            runPracaStage = null;
            runPracaAbrindo = false;
        });

        runPracaStage.show();
        runPracaStage.toFront();

    } catch (IOException e) {
        runPracaAbrindo = false;
        e.printStackTrace();
    }
}

    public static void toggleInventario() {
        try {
            if (inventarioStage != null && inventarioStage.isShowing()) {
                inventarioStage.close();
                inventarioStage = null;
                inventarioController = null;
                return;
            }

            FXMLLoader loader = new FXMLLoader(App.class.getResource("inventario.fxml"));
            Parent root = loader.load();

            inventarioController = loader.getController();

            inventarioStage = new Stage();
            inventarioStage.setTitle("Inventário");
            inventarioStage.setScene(new Scene(root, 600, 400));
            inventarioStage.setResizable(false);

            inventarioStage.setOnHidden(event -> {
                inventarioStage = null;
                inventarioController = null;
            });

            inventarioStage.show();
            inventarioStage.toFront();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void atualizarInventarioAberto() {
        if (inventarioController != null) {
            inventarioController.atualizarItens();
        }
    }

    public static void abrirLoja() {
        try {
            if (lojaStage != null && lojaStage.isShowing()) {
                lojaStage.toFront();
                return;
            }

            FXMLLoader loader = new FXMLLoader(App.class.getResource("loja.fxml"));
            Parent root = loader.load();

            lojaStage = new Stage();
            lojaStage.setTitle("Loja");
            lojaStage.setScene(new Scene(root, 600, 400));
            lojaStage.setResizable(false);

            lojaStage.initOwner(primaryStage);
            lojaStage.initModality(Modality.WINDOW_MODAL);

            lojaStage.setOnHidden(event -> {
                lojaStage = null;
            });

            lojaStage.show();
            lojaStage.toFront();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void iniciarDialogoUmaVez(String idDialogo, Label floatingText, Button botaoContinuar, String[] falas) {
        if (floatingText == null || botaoContinuar == null) {
            return;
        }

        if (falas == null || falas.length == 0) {
            esconderDialogo(floatingText, botaoContinuar);
            return;
        }

        if (dialogosJaIniciados.contains(idDialogo)) {
            esconderDialogo(floatingText, botaoContinuar);
            return;
        }

        dialogosJaIniciados.add(idDialogo);
        indicesDialogo.put(idDialogo, 0);

        floatingText.setVisible(true);
        botaoContinuar.setVisible(false);

        mostrarTextoLento(idDialogo, floatingText, botaoContinuar, falas[0]);
    }

    public static void proximaFraseDialogo(String idDialogo, Label floatingText, Button botaoContinuar, String[] falas) {
        if (floatingText == null || botaoContinuar == null) {
            return;
        }

        if (!indicesDialogo.containsKey(idDialogo)) {
            esconderDialogo(floatingText, botaoContinuar);
            return;
        }

        int proximoIndice = indicesDialogo.get(idDialogo) + 1;

        if (proximoIndice < falas.length) {
            indicesDialogo.put(idDialogo, proximoIndice);
            mostrarTextoLento(idDialogo, floatingText, botaoContinuar, falas[proximoIndice]);
        } else {
            finalizarDialogo(idDialogo, floatingText, botaoContinuar);
        }
    }

    private static void mostrarTextoLento(String idDialogo, Label floatingText, Button botaoContinuar, String texto) {
        Timeline timelineAnterior = timelinesDialogo.remove(idDialogo);

        if (timelineAnterior != null) {
            timelineAnterior.stop();
        }

        floatingText.setText("");
        floatingText.setVisible(true);
        botaoContinuar.setVisible(false);

        Timeline timeline = new Timeline();

        for (int i = 0; i < texto.length(); i++) {
            final int index = i;

            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(60 * i),
                    event -> floatingText.setText(texto.substring(0, index + 1))
            );

            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.setOnFinished(event -> {
            botaoContinuar.setVisible(true);
            timelinesDialogo.remove(idDialogo);
        });

        timelinesDialogo.put(idDialogo, timeline);
        timeline.play();
    }

    private static void finalizarDialogo(String idDialogo, Label floatingText, Button botaoContinuar) {
        Timeline timeline = timelinesDialogo.remove(idDialogo);

        if (timeline != null) {
            timeline.stop();
        }

        indicesDialogo.remove(idDialogo);
        esconderDialogo(floatingText, botaoContinuar);
    }

    private static void esconderDialogo(Label floatingText, Button botaoContinuar) {
        floatingText.setText("");
        floatingText.setVisible(false);
        botaoContinuar.setVisible(false);
    }

    public static void resetarDialogosDaPartida() {
        for (Timeline timeline : timelinesDialogo.values()) {
            timeline.stop();
        }

        timelinesDialogo.clear();
        indicesDialogo.clear();
        dialogosJaIniciados.clear();
    }    
    
    
    public static void voltarParaQuartoAposPerdaRunPraca() {
        dialogoPerdaRunPracaPendente = true;

        try {
            setRoot("quarto");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean consumirDialogoPerdaRunPraca() {
        if (dialogoPerdaRunPracaPendente) {
            dialogoPerdaRunPracaPendente = false;
            return true;
        }

        return false;
    }
    
    public static void iniciarDialogoSituacional(String idDialogo, Label floatingText, Button botaoContinuar, String[] falas) {
        if (floatingText == null || botaoContinuar == null) {
            return;
        }

        if (falas == null || falas.length == 0) {
            esconderDialogo(floatingText, botaoContinuar);
            return;
        }

        Timeline timelineAnterior = timelinesDialogo.remove(idDialogo);

        if (timelineAnterior != null) {
            timelineAnterior.stop();
        }

        indicesDialogo.put(idDialogo, 0);

        floatingText.setVisible(true);
        botaoContinuar.setVisible(false);

        mostrarTextoLento(idDialogo, floatingText, botaoContinuar, falas[0]);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}