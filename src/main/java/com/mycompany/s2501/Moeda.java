package com.mycompany.s2501;

import com.mycompany.s2501.Model.EstadoJogo;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Moeda {

    private static final String IMAGEM_MOEDA = "/com/mycompany/s2501/jogotlp/m2.png";

    private Moeda() {
    }

    public static void adicionarMoeda(AnchorPane root, String idMoeda, double x, double y, double tamanho) {
        if (root == null || EstadoJogo.moedaJaColetada(idMoeda)) {
            return;
        }

        ImageView moeda = new ImageView(new Image(App.class.getResourceAsStream(IMAGEM_MOEDA)));
        moeda.setLayoutX(x);
        moeda.setLayoutY(y);
        moeda.setFitWidth(tamanho);
        moeda.setFitHeight(tamanho);
        moeda.setPreserveRatio(true);
        moeda.setCursor(Cursor.HAND);
        moeda.setOpacity(0.82);
        moeda.setPickOnBounds(true);

        moeda.setOnMouseClicked(event -> {
            if (EstadoJogo.coletarMoedaUmaVez(idMoeda)) {
                root.getChildren().remove(moeda);
                App.atualizarLojaAberta();
            }
        });

        root.getChildren().add(moeda);
        moeda.toFront();
    }
}
