package com.mycompany.s2501.Model;

import java.util.HashSet;
import java.util.Set;

public class EstadoJogo {

    private static int jogadorId;

    private static int moedas;
    private static int pontosTotais;

    private static int fragmentosChave;

    private static int runsVencidas;
    private static int runsPerdidas;

    private static int upgradeTempo;
    private static int upgradeBonus;

    private static boolean quartoDesbloqueado;
    private static boolean memoraxComprado;
    private static boolean memoraxAtivo;
    private static int bonusTempoProximaRun;

    private static long inicioPartidaMillis;

    private static final Set<String> runsVencidasCodigos = new HashSet<>();
    private static final Set<String> eventosPontuados = new HashSet<>();
    private static final Set<String> moedasColetadas = new HashSet<>();

    private EstadoJogo() {
    }

    public static void iniciarNovaPartida(int idJogador) {
        jogadorId = idJogador;

        moedas = 0;
        pontosTotais = 0;

        fragmentosChave = 0;

        runsVencidas = 0;
        runsPerdidas = 0;

        upgradeTempo = 0;
        upgradeBonus = 0;

        quartoDesbloqueado = false;
        memoraxComprado = false;
        memoraxAtivo = false;
        bonusTempoProximaRun = 0;

        runsVencidasCodigos.clear();
        eventosPontuados.clear();
        moedasColetadas.clear();

        inicioPartidaMillis = System.currentTimeMillis();
    }

    public static int getJogadorId() {
        return jogadorId;
    }

    public static int getMoedas() {
        return moedas;
    }

    public static void adicionarMoedas(int quantidade) {
        moedas += quantidade;
    }

    public static boolean coletarMoedaUmaVez(String idMoeda) {
        if (moedasColetadas.contains(idMoeda)) {
            return false;
        }

        moedasColetadas.add(idMoeda);
        adicionarMoedas(5);
        return true;
    }

    public static boolean moedaJaColetada(String idMoeda) {
        return moedasColetadas.contains(idMoeda);
    }

    public static boolean gastarMoedas(int quantidade) {
        if (moedas < quantidade) {
            return false;
        }

        moedas -= quantidade;
        return true;
    }

    public static int getPontosTotais() {
        return pontosTotais;
    }

    public static void adicionarPontos(int pontos) {
        pontosTotais += pontos;
    }

    public static boolean pontuarEventoUmaVez(String idEvento, int pontos) {
        if (eventosPontuados.contains(idEvento)) {
            return false;
        }

        eventosPontuados.add(idEvento);
        adicionarPontos(aplicarMultiplicadorPontos(pontos));
        return true;
    }

    public static int calcularBonusTempo(int tempoLimiteSegundos, int tempoGastoSegundos) {
        int tempoRestante = tempoLimiteSegundos - tempoGastoSegundos;

        if (tempoRestante < 0) {
            tempoRestante = 0;
        }

        return tempoRestante * 2;
    }

    public static int calcularPontosRun(int tempoLimiteSegundos, int tempoGastoSegundos) {
        int pontosBase = 200;
        int bonusTempo = calcularBonusTempo(tempoLimiteSegundos, tempoGastoSegundos);

        int pontos = pontosBase + bonusTempo;

        if (upgradeBonus > 0) {
            pontos += upgradeBonus * 50;
        }

        return aplicarMultiplicadorPontos(pontos);
    }

    public static boolean registrarVitoriaRun(String codigoRun, int pontos) {
        if (runsVencidasCodigos.contains(codigoRun)) {
            return false;
        }

        runsVencidasCodigos.add(codigoRun);

        runsVencidas++;
        adicionarPontos(pontos);

        if (fragmentosChave < 2) {
            fragmentosChave++;
        }

        if (fragmentosChave >= 2) {
            quartoDesbloqueado = true;
        }

        return true;
    }

    public static void registrarDerrotaRun() {
        runsPerdidas++;
    }

    public static boolean runJaVencida(String codigoRun) {
        return runsVencidasCodigos.contains(codigoRun);
    }

    public static boolean registrarCompraMemorax() {
        if (memoraxComprado) {
            return false;
        }

        memoraxComprado = true;
        return true;
    }

    public static boolean isMemoraxComprado() {
        return memoraxComprado;
    }

    public static void ativarMemorax() {
        memoraxAtivo = true;
    }

    public static boolean isMemoraxAtivo() {
        return memoraxAtivo;
    }

    public static int aplicarMultiplicadorPontos(int pontos) {
        if (memoraxAtivo) {
            return pontos * 2;
        }

        return pontos;
    }

    public static void adicionarBonusTempoProximaRun(int segundos) {
        bonusTempoProximaRun += segundos;
    }

    public static int consumirTempoLimiteComBonus(int tempoBaseSegundos) {
        int tempoFinal = tempoBaseSegundos + bonusTempoProximaRun;
        bonusTempoProximaRun = 0;
        return tempoFinal;
    }

    public static int getFragmentosChave() {
        return fragmentosChave;
    }

    public static int getRunsVencidas() {
        return runsVencidas;
    }

    public static int getRunsPerdidas() {
        return runsPerdidas;
    }

    public static int getTempoTotalGameplay() {
        if (inicioPartidaMillis == 0) {
            return 0;
        }

        long agora = System.currentTimeMillis();
        return (int) ((agora - inicioPartidaMillis) / 1000);
    }

    public static int getUpgradeTempo() {
        return upgradeTempo;
    }

    public static int getUpgradeBonus() {
        return upgradeBonus;
    }

    public static void comprarUpgradeTempo() {
        upgradeTempo++;
    }

    public static void comprarUpgradeBonus() {
        upgradeBonus++;
    }

    public static boolean isQuartoDesbloqueado() {
        return quartoDesbloqueado;
    }
}
