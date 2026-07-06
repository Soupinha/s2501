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
import javafx.scene.input.MouseEvent;
import com.mycompany.s2501.Model.EstadoJogo;

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
    private Label mensagemLoja;

    @FXML
    private Button comprarButton;

    private Item itemSelecionado;
    
    @FXML
    private Label precoRemedioLabel;
    
    private int precoSelecionado;
    private boolean itemSelecionadoCompravel;

    private static final int ID_MEMORAX = 1;
    private static final int ID_TEMPRAZOL = 2;
    private static final int ID_OBLIVIONEX = 3;
    private static final int ID_LUCIDRALINA = 4;

    private static final int PRECO_MEMORAX = 75;
    private static final int PRECO_TEMPRAZOL = 50;

    @FXML
    private void initialize() {
        configurarRemedio(remedio1Box, "Remédio 1");
        configurarRemedio(remedio2Box, "Remédio 2");
        configurarRemedio(remedio3Box, "Remédio 3");
        configurarRemedio(remedio4Box, "Remédio 4");

        atualizarMoedasLoja();
    }

    private void configurarRemedio(AnchorPane remedioBox, String nome) {
        remedioBox.setCursor(Cursor.HAND);
        Tooltip.install(remedioBox, new Tooltip("Ver " + nome));

        remedioBox.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            remedioBox.setScaleX(1.06);
            remedioBox.setScaleY(1.06);
        });

        remedioBox.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            remedioBox.setScaleX(1.0);
            remedioBox.setScaleY(1.0);
        });
    }
    
    public void atualizarMoedasLoja() {
        moedasLabel.setText("Moedas: " + EstadoJogo.getMoedas());
        atualizarEstadoBotaoCompra();
    }

    @FXML
    private void mostrarRemedio1() {
        mostrarInfo(new Item(
                ID_MEMORAX,
                "Memorax-Duo",
                "Dá 2x mais pontos pelo resto do jogo.",
                0,
                "/com/mycompany/s2501/loja/c1.png"
        ), PRECO_MEMORAX, !EstadoJogo.isMemoraxComprado());
    }

    @FXML
    private void mostrarRemedio2() {
        mostrarInfo(new Item(
                ID_TEMPRAZOL,
                "Temprazol-60",
                "Adiciona +1 minuto na próxima memória.",
                0,
                "/com/mycompany/s2501/loja/c2.png"
        ), PRECO_TEMPRAZOL, true);
    }

    @FXML
    private void mostrarRemedio3() {
        mostrarInfo(new Item(
                ID_OBLIVIONEX,
                "Oblivionex",
                "Esgotado. Não está disponível para compra.",
                0,
                "/com/mycompany/s2501/loja/c3.png"
        ), 0, false);
    }

    @FXML
    private void mostrarRemedio4() {
        mostrarInfo(new Item(
                ID_LUCIDRALINA,
                "Lucidralina",
                "Ainda indisponível. Será usada para revelar pistas importantes.",
                0,
                "/com/mycompany/s2501/loja/c4.png"
        ), 0, false);
    }

    private void mostrarInfo(Item item, int preco, boolean compravel) {
    itemSelecionado = item;
    precoSelecionado = preco;
    itemSelecionadoCompravel = compravel;

    painelInfoRemedio.setVisible(true);

    nomeRemedioLabel.setText(item.getNome());
    descricaoRemedioLabel.setText(item.getDescricao());
    precoRemedioLabel.setText(compravel ? "Preço: " + precoSelecionado + " moedas" : "Indisponível");

    atualizarQuantidadeDisponivel();
    atualizarEstadoBotaoCompra();
}

    @FXML
    private void comprarSelecionado() {
        if (itemSelecionado == null) {
            return;
        }

        int disponivel = getDisponivelSelecionado();

        if (!itemSelecionadoCompravel || disponivel <= 0) {
            atualizarQuantidadeDisponivel();
            atualizarEstadoBotaoCompra();
            return;
        }

        if (EstadoJogo.getMoedas() < precoSelecionado) {
            mensagemLoja.setText("Dinheiro insuficiente.");
            atualizarEstadoBotaoCompra();
            return;
        }

        if (itemSelecionado.getId() == ID_MEMORAX && !EstadoJogo.registrarCompraMemorax()) {
            mensagemLoja.setText("Memorax-Duo só pode ser comprado uma vez.");
            itemSelecionadoCompravel = false;
            atualizarEstadoBotaoCompra();
            return;
        }

        EstadoJogo.gastarMoedas(precoSelecionado);
        boolean comprou = InventarioJogador.adicionarItem(itemSelecionado);

        if (comprou) {
            App.atualizarInventarioAberto();
            App.atualizarProgressoBanco();
            mensagemLoja.setText(itemSelecionado.getNome() + " comprado.");
        } else if (itemSelecionado.getId() == ID_MEMORAX) {
            EstadoJogo.adicionarMoedas(precoSelecionado);
        }

        atualizarQuantidadeDisponivel();
        atualizarMoedasLoja();
    }

    private void atualizarQuantidadeDisponivel() {
        if (itemSelecionado == null) {
            return;
        }

        int disponivel = getDisponivelSelecionado();
        int comprados = InventarioJogador.getQuantidade(itemSelecionado.getId());
        int limite = itemSelecionado.getId() == ID_MEMORAX ? 1 : InventarioJogador.getLimitePorItem();

        if (!itemSelecionadoCompravel) {
            quantidadeRemedioLabel.setText(itemSelecionado.getId() == ID_OBLIVIONEX ? "Esgotado" : "Disponível: 0");
            return;
        }

        quantidadeRemedioLabel.setText(
                "Disponível: " + disponivel + "\nComprados: " + comprados + "/" + limite
        );
    }

    private void atualizarEstadoBotaoCompra() {
        if (comprarButton == null || itemSelecionado == null) {
            return;
        }

        boolean semEstoque = getDisponivelSelecionado() <= 0;
        boolean semMoedas = EstadoJogo.getMoedas() < precoSelecionado;
        boolean memoraxJaComprado = itemSelecionado.getId() == ID_MEMORAX && EstadoJogo.isMemoraxComprado();

        comprarButton.setDisable(!itemSelecionadoCompravel || semEstoque || semMoedas || memoraxJaComprado);
    }

    private int getDisponivelSelecionado() {
        if (itemSelecionado == null) {
            return 0;
        }

        if (itemSelecionado.getId() == ID_MEMORAX) {
            return EstadoJogo.isMemoraxComprado() ? 0 : 1;
        }

        return InventarioJogador.getQuantidadeDisponivel(itemSelecionado.getId());
    }
}
