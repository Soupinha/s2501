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
    private FlowPane areaItens;

    @FXML
    private void initialize() {
        atualizarItens();
    }

    public void atualizarItens() {
        if (areaItens == null) {
            return;
        }

        areaItens.getChildren().clear();

        for (Item item : InventarioJogador.getItens()) {
            areaItens.getChildren().add(criarVisualItem(item));
        }
    }

    private AnchorPane criarVisualItem(Item item) {
        AnchorPane box = new AnchorPane();

        box.setPrefWidth(60);
        box.setPrefHeight(60);

        // Aqui remove aquela borda/fundo preto
        box.setStyle("-fx-background-color: transparent;");

        Image imagem = new Image(App.class.getResourceAsStream(item.getImagemInventario()));

        ImageView imageView = new ImageView(imagem);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);

        int quantidade = InventarioJogador.getQuantidade(item.getId());

        Label quantidadeLabel = new Label("x" + quantidade);
        quantidadeLabel.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;"
        );

        AnchorPane.setRightAnchor(quantidadeLabel, 0.0);
        AnchorPane.setBottomAnchor(quantidadeLabel, 0.0);

        Tooltip.install(box, new Tooltip(
                item.getNome()
                + "\nQuantidade: " + quantidade
                + "\n" + item.getDescricao()
        ));

        box.getChildren().addAll(imageView, quantidadeLabel);

        return box;
    }
}