package com.mycompany.s2501.Cozinha;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Model.InventarioJogador;
import com.mycompany.s2501.Model.Item;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

public class LojaController {

    @FXML
    private AnchorPane remedio1Box;

    @FXML
    private AnchorPane remedio2Box;

    @FXML
    private AnchorPane remedio3Box;

    @FXML
    private AnchorPane remedio4Box;

    @FXML
    private AnchorPane painelInfoRemedio;

    @FXML
    private Label nomeRemedioLabel;

    @FXML
    private Label descricaoRemedioLabel;

    @FXML
    private Label quantidadeRemedioLabel;

    @FXML
    private Label moedasLabel;

    @FXML
    private Button comprarButton;

    private Item itemSelecionado;

    @FXML
    private void initialize() {
        configurarRemedio(remedio1Box, "Remédio 1");
        configurarRemedio(remedio2Box, "Remédio 2");
        configurarRemedio(remedio3Box, "Remédio 3");
        configurarRemedio(remedio4Box, "Remédio 4");

        moedasLabel.setText("Moedas: 0");
    }

    private void configurarRemedio(AnchorPane remedioBox, String nome) {
        remedioBox.setCursor(Cursor.HAND);
        Tooltip.install(remedioBox, new Tooltip("Ver " + nome));

        remedioBox.setOnMouseEntered(event -> {
            remedioBox.setScaleX(1.06);
            remedioBox.setScaleY(1.06);
        });

        remedioBox.setOnMouseExited(event -> {
            remedioBox.setScaleX(1.0);
            remedioBox.setScaleY(1.0);
        });
    }

    @FXML
    private void mostrarRemedio1() {
        mostrarInfo(new Item(
                1,
                "Comprimido Azul",
                "Recupera uma pequena parte da energia do jogador.",
                0,
                "/com/mycompany/s2501/loja/c1.png"
        ));
    }

    @FXML
    private void mostrarRemedio2() {
        mostrarInfo(new Item(
                2,
                "Comprimido Verde",
                "Ajuda o jogador a resistir melhor aos efeitos da casa.",
                0,
                "/com/mycompany/s2501/loja/c2.png"
        ));
    }

    @FXML
    private void mostrarRemedio3() {
        mostrarInfo(new Item(
                3,
                "Comprimido Vermelho",
                "Aumenta temporariamente a força de ação do jogador.",
                0,
                "/com/mycompany/s2501/loja/c3.png"
        ));
    }

    @FXML
    private void mostrarRemedio4() {
        mostrarInfo(new Item(
                4,
                "Comprimido Roxo",
                "Ajuda o jogador em momentos de maior perigo.",
                0,
                "/com/mycompany/s2501/loja/c4.png"
        ));
    }

    private void mostrarInfo(Item item) {
        itemSelecionado = item;

        painelInfoRemedio.setVisible(true);

        nomeRemedioLabel.setText(item.getNome());
        descricaoRemedioLabel.setText(item.getDescricao());

        atualizarQuantidadeDisponivel();

        comprarButton.setDisable(InventarioJogador.getQuantidadeDisponivel(item.getId()) <= 0);
    }

    @FXML
    private void comprarSelecionado() {
        if (itemSelecionado == null) {
            return;
        }

        int disponivel = InventarioJogador.getQuantidadeDisponivel(itemSelecionado.getId());

        if (disponivel <= 0) {
            quantidadeRemedioLabel.setText("Disponível: 0");
            comprarButton.setDisable(true);
            return;
        }

        boolean comprou = InventarioJogador.adicionarItem(itemSelecionado);

        if (comprou) {
            App.atualizarInventarioAberto();
        }

        atualizarQuantidadeDisponivel();

        if (InventarioJogador.getQuantidadeDisponivel(itemSelecionado.getId()) <= 0) {
            comprarButton.setDisable(true);
        }
    }

    private void atualizarQuantidadeDisponivel() {
        if (itemSelecionado == null) {
            return;
        }

        int disponivel = InventarioJogador.getQuantidadeDisponivel(itemSelecionado.getId());
        int comprados = InventarioJogador.getQuantidade(itemSelecionado.getId());
        int limite = InventarioJogador.getLimitePorItem();

        quantidadeRemedioLabel.setText(
                "Disponível: " + disponivel + "\nComprados: " + comprados + "/" + limite
        );
    }
}