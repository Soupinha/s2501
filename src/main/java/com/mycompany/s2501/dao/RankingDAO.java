package com.mycompany.s2501.dao;

import com.mycompany.s2501.Model.RankingRegistro;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RankingDAO {

    public List<RankingRegistro> listarRanking() {
        List<RankingRegistro> ranking = new ArrayList<>();

        String sql =
                "SELECT numero_jogatina, pontuacao_total, runs_vencidas, runs_perdidas, tempo_total, final_alcancado " +
                "FROM vw_ranking";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RankingRegistro registro = new RankingRegistro(
                        rs.getInt("numero_jogatina"),
                        rs.getInt("pontuacao_total"),
                        rs.getInt("runs_vencidas"),
                        rs.getInt("runs_perdidas"),
                        rs.getInt("tempo_total"),
                        rs.getBoolean("final_alcancado")
                );

                ranking.add(registro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ranking;
    }

    public void exportarCSV(File arquivo, Collection<RankingRegistro> ranking) throws IOException {
        try (FileWriter writer = new FileWriter(arquivo)) {
            writer.write("Numero da jogatina,Pontos,Runs vencidas,Runs perdidas,Tempo total,Final alcancado\n");

            for (RankingRegistro r : ranking) {
                writer.write(
                        r.getNumeroJogatina() + "," +
                        r.getPontuacaoTotal() + "," +
                        r.getRunsVencidas() + "," +
                        r.getRunsPerdidas() + "," +
                        r.getTempoTotal() + "," +
                        r.isFinalAlcancado() + "\n"
                );
            }
        }
    }

    public void exportarTXT(File arquivo, Collection<RankingRegistro> ranking) throws IOException {
        try (FileWriter writer = new FileWriter(arquivo)) {
            writer.write("RANKING FINAL\n");
            writer.write("==============================\n\n");

            for (RankingRegistro r : ranking) {
                writer.write("Jogatina: " + r.getNumeroJogatina() + "\n");
                writer.write("Pontos: " + r.getPontuacaoTotal() + "\n");
                writer.write("Runs vencidas: " + r.getRunsVencidas() + "\n");
                writer.write("Runs perdidas: " + r.getRunsPerdidas() + "\n");
                writer.write("Tempo total: " + r.getTempoTotal() + " segundos\n");
                writer.write("Final alcançado: " + (r.isFinalAlcancado() ? "Sim" : "Não") + "\n");
                writer.write("------------------------------\n");
            }
        }
    }
}