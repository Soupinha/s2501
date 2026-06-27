package com.mycompany.s2501;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    private static Stage primaryStage;
    private static Stage inventarioStage;
    private static Stage lojaStage;
    private static Stage runPracaStage;

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
            if (runPracaStage != null && runPracaStage.isShowing()) {
                runPracaStage.toFront();
                return;
            }

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
            });

            runPracaStage.show();
            runPracaStage.toFront();

        } catch (IOException e) {
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