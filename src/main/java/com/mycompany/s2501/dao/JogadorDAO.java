package com.mycompany.s2501.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JogadorDAO {

    public int criarJogador() {
        String sql = "INSERT INTO jogador (nome) VALUES (?) RETURNING id";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "Jogador");

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void atualizarProgresso(
            int jogadorId,
            int pontosTotais,
            int fragmentosChave,
            int runsVencidas,
            int runsPerdidas,
            int tempoTotalGameplay,
            int upgradeTempo,
            int upgradeBonus,
            boolean quartoDesbloqueado
    ) {
        String sql =
                "UPDATE jogador SET " +
                "pontos_totais = ?, " +
                "fragmentos_chave = ?, " +
                "runs_vencidas = ?, " +
                "runs_perdidas = ?, " +
                "tempo_total_gameplay = ?, " +
                "upgrade_tempo = ?, " +
                "upgrade_bonus = ?, " +
                "quarto_desbloqueado = ? " +
                "WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pontosTotais);
            stmt.setInt(2, fragmentosChave);
            stmt.setInt(3, runsVencidas);
            stmt.setInt(4, runsPerdidas);
            stmt.setInt(5, tempoTotalGameplay);
            stmt.setInt(6, upgradeTempo);
            stmt.setInt(7, upgradeBonus);
            stmt.setBoolean(8, quartoDesbloqueado);
            stmt.setInt(9, jogadorId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}