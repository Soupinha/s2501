package com.mycompany.s2501.Model;

public class RankingRegistro {

    private int numeroJogatina;
    private int pontuacaoTotal;
    private int runsVencidas;
    private int runsPerdidas;
    private int tempoTotal;
    private boolean finalAlcancado;

    public RankingRegistro(
            int numeroJogatina,
            int pontuacaoTotal,
            int runsVencidas,
            int runsPerdidas,
            int tempoTotal,
            boolean finalAlcancado
    ) {
        this.numeroJogatina = numeroJogatina;
        this.pontuacaoTotal = pontuacaoTotal;
        this.runsVencidas = runsVencidas;
        this.runsPerdidas = runsPerdidas;
        this.tempoTotal = tempoTotal;
        this.finalAlcancado = finalAlcancado;
    }

    public int getNumeroJogatina() {
        return numeroJogatina;
    }

    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public int getRunsVencidas() {
        return runsVencidas;
    }

    public int getRunsPerdidas() {
        return runsPerdidas;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    public boolean isFinalAlcancado() {
        return finalAlcancado;
    }
}