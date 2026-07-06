package com.mycompany.s2501.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistoricoPartidaDAO {

    public void salvarHistoricoFinal(
            int jogadorId,
            int pontuacaoTotal,
            int runsVencidas,
            int runsPerdidas,
            int tempoTotal,
            boolean finalAlcancado
    ) {
        String sql =
                "INSERT INTO historico_partida " +
                "(jogador_id, pontuacao_total, runs_vencidas, runs_perdidas, tempo_total, final_alcancado) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jogadorId);
            stmt.setInt(2, pontuacaoTotal);
            stmt.setInt(3, runsVencidas);
            stmt.setInt(4, runsPerdidas);
            stmt.setInt(5, tempoTotal);
            stmt.setBoolean(6, finalAlcancado);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}