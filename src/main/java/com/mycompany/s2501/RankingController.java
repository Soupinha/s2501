package com.mycompany.s2501;

import com.mycompany.s2501.Model.RankingRegistro;
import com.mycompany.s2501.dao.RankingDAO;
import java.io.File;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class RankingController {

    @FXML
    private TableView<RankingRegistro> tabelaRanking;

    @FXML
    private TableColumn<RankingRegistro, Integer> colJogatina;

    @FXML
    private TableColumn<RankingRegistro, Integer> colPontos;

    @FXML
    private TableColumn<RankingRegistro, Integer> colRunsVencidas;

    @FXML
    private TableColumn<RankingRegistro, Integer> colRunsPerdidas;

    @FXML
    private TableColumn<RankingRegistro, Integer> colTempo;

    @FXML
    private TableColumn<RankingRegistro, Boolean> colFinal;

    private final RankingDAO rankingDAO = new RankingDAO();
    private ObservableList<RankingRegistro> dadosRanking;

    @FXML
    private void initialize() {
        colJogatina.setCellValueFactory(new PropertyValueFactory<>("numeroJogatina"));
        colPontos.setCellValueFactory(new PropertyValueFactory<>("pontuacaoTotal"));
        colRunsVencidas.setCellValueFactory(new PropertyValueFactory<>("runsVencidas"));
        colRunsPerdidas.setCellValueFactory(new PropertyValueFactory<>("runsPerdidas"));
        colTempo.setCellValueFactory(new PropertyValueFactory<>("tempoTotal"));
        colFinal.setCellValueFactory(new PropertyValueFactory<>("finalAlcancado"));

        dadosRanking = FXCollections.observableArrayList(rankingDAO.listarRanking());
        tabelaRanking.setItems(dadosRanking);
    }

    @FXML
    private void exportarCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar ranking em CSV");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
        fileChooser.setInitialFileName("ranking.csv");

        File arquivo = fileChooser.showSaveDialog(tabelaRanking.getScene().getWindow());

        if (arquivo != null) {
            try {
                rankingDAO.exportarCSV(arquivo, dadosRanking);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void exportarTXT() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar ranking em TXT");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
        fileChooser.setInitialFileName("ranking.txt");

        File arquivo = fileChooser.showSaveDialog(tabelaRanking.getScene().getWindow());

        if (arquivo != null) {
            try {
                rankingDAO.exportarTXT(arquivo, dadosRanking);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void voltar() throws IOException {
        App.setRoot("menu");
    }
}