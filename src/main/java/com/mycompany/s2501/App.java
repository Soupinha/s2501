package com.mycompany.s2501;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage inventarioStage;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("menu"), 1200, 800);

        stage.setScene(scene);
        
        stage.setTitle("Jogo");
        stage.setResizable(false); // trava o tamanho da janela

        stage.show();
    }
    
    public static void toggleInventario() {
        try {
            if (inventarioStage != null && inventarioStage.isShowing()) {
                inventarioStage.close();
                inventarioStage = null;
                return;
            }

            if (inventarioStage == null) {
                FXMLLoader loader =
                    new FXMLLoader(App.class.getResource("inventario.fxml"));

                Parent root = loader.load();

                inventarioStage = new Stage();
                inventarioStage.setTitle("Inventário");
                inventarioStage.setScene(new Scene(root, 600, 400));
                inventarioStage.setResizable(false);

                inventarioStage.setOnHidden(event -> {
                    inventarioStage = null;
                });
            }

            inventarioStage.show();
            inventarioStage.toFront();

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