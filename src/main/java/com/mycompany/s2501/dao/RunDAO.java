package com.mycompany.s2501.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RunDAO {

    public void salvarRun(
            int jogadorId,
            String codigoRun,
            String nomeRun,
            boolean venceu,
            int pontos,
            int bonusTempo,
            int erros,
            int tempoGasto
    ) {
        String sql =
                "INSERT INTO run " +
                "(jogador_id, codigo_run, nome_run, venceu, pontos, bonus_tempo, erros, tempo_gasto) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jogadorId);
            stmt.setString(2, codigoRun);
            stmt.setString(3, nomeRun);
            stmt.setBoolean(4, venceu);
            stmt.setInt(5, pontos);
            stmt.setInt(6, bonusTempo);
            stmt.setInt(7, erros);
            stmt.setInt(8, tempoGasto);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void marcarRunComoVencida(int jogadorId, String codigoRun) {
        String sql =
                "INSERT INTO progresso_run (jogador_id, codigo_run, vencida, data_vitoria) " +
                "VALUES (?, ?, TRUE, CURRENT_TIMESTAMP) " +
                "ON CONFLICT (jogador_id, codigo_run) " +
                "DO UPDATE SET vencida = TRUE, data_vitoria = CURRENT_TIMESTAMP";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jogadorId);
            stmt.setString(2, codigoRun);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}