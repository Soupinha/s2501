package com.mycompany.s2501;

import com.mycompany.s2501.Model.InventarioJogador;
import com.mycompany.s2501.Model.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

public class InventarioController {

    @FXML
    private FlowPane areaItensMaoEsquerda;

    @FXML
    private FlowPane areaItensMaoDireita;

    @FXML
    private void initialize() {
        atualizarItens();
    }

    public void atualizarItens() {
        if (areaItensMaoEsquerda == null || areaItensMaoDireita == null) {
            return;
        }

        areaItensMaoEsquerda.getChildren().clear();
        areaItensMaoDireita.getChildren().clear();

        int contador = 0;

        for (Item item : InventarioJogador.getItens()) {
            int quantidade = InventarioJogador.getQuantidade(item.getId());

            for (int i = 0; i < quantidade; i++) {
                AnchorPane visualItem = criarVisualItem(item);

                if (contador < 6) {
                    areaItensMaoEsquerda.getChildren().add(visualItem);
                } else {
                    areaItensMaoDireita.getChildren().add(visualItem);
                }

                contador++;
            }
        }
    }

    private AnchorPane criarVisualItem(Item item) {
        AnchorPane box = new AnchorPane();

        box.setPrefWidth(50);
        box.setPrefHeight(50);
        box.setStyle("-fx-background-color: transparent;");

        Image imagem = new Image(App.class.getResourceAsStream(item.getImagemInventario()));

        ImageView imageView = new ImageView(imagem);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);

        Tooltip.install(box, new Tooltip(
                item.getNome() + "\n" + item.getDescricao()
        ));

        box.getChildren().add(imageView);

        return box;
    }
}