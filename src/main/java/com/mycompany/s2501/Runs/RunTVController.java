package com.mycompany.s2501.Runs;

import com.mycompany.s2501.App;
import com.mycompany.s2501.Model.EstadoJogo;
import com.mycompany.s2501.Model.InventarioJogador;
import com.mycompany.s2501.dao.RunDAO;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RunTVController {

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane tvScreen;

    @FXML
    private AnchorPane painelInicio;

    @FXML
    private AnchorPane painelFinal;

    @FXML
    private AnchorPane skillArea;

    @FXML
    private Label tempoLabel;

    @FXML
    private Label porcentagemLabel;

    @FXML
    private Label mensagemFinalLabel;

    @FXML
    private ProgressBar sinalBar;

    @FXML
    private Rectangle zonaAcerto;

    @FXML
    private Rectangle marcador;

    @FXML
    private Rectangle falha1;

    @FXML
    private Rectangle falha2;

    @FXML
    private Rectangle falha3;

    @FXML
    private Rectangle falha4;

    @FXML
    private Rectangle falha5;

    private static final String CODIGO_RUN = "RUN_TV";
    private static final String NOME_RUN = "Run TV";
    private static final int TEMPO_LIMITE_RUN = 150;
    private static final double PERDA_POR_SEGUNDO = 0.01;
    private static final double PERDA_POR_ERRO = 0.10;
    private static final double GANHO_POR_ACERTO = 0.08;
    private static final double VELOCIDADE_MINIMA = 3.2;
    private static final double VELOCIDADE_MAXIMA = 7.2;

    private final Random random = new Random();

    private AnimationTimer gameLoop;
    private Timeline tempoTimeline;

    private boolean jogoRodando = false;
    private boolean jogoIniciado = false;
    private boolean venceu = false;
    private boolean podeFecharJanela = false;

    private int tempoRestante = TEMPO_LIMITE_RUN;
    private int tempoLimiteRun = TEMPO_LIMITE_RUN;
    private int erros = 0;

    private double progresso = 0.0;

    private double marcadorX = 0;
    private double velocidadeMarcador = VELOCIDADE_MINIMA;
    private int direcaoMarcador = 1;
    private long ultimoRuido = 0;

    @FXML
    private void initialize() {
        root.setFocusTraversable(true);

        sinalBar.setProgress(0);
        atualizarTelaPorProgresso();

        registrarFechamentoDaJanela();

        root.setOnKeyPressed(event -> {
            if (!jogoRodando) {
                return;
            }

            if (event.getCode() == KeyCode.SPACE) {
                tentarAcerto();
            }
        });
    }

    private void registrarFechamentoDaJanela() {
        root.sceneProperty().addListener((obsScene, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.windowProperty().addListener((obsWindow, oldWindow, newWindow) -> {
                    if (newWindow instanceof Stage) {
                        Stage stage = (Stage) newWindow;

                        stage.setOnCloseRequest(event -> {
                            if (!podeFecharJanela) {
                                event.consume();
                            }
                        });
                    }
                });
            }
        });
    }

    @FXML
    private void iniciarJogo() {
        if (jogoIniciado || jogoRodando) {
            return;
        }

        jogoIniciado = true;
        jogoRodando = true;
        venceu = false;

        tempoLimiteRun = EstadoJogo.consumirTempoLimiteComBonus(TEMPO_LIMITE_RUN);
        tempoRestante = tempoLimiteRun;
        progresso = 0.0;
        erros = 0;

        painelInicio.setVisible(false);

        sortearZonaAcerto();
        atualizarTelaPorProgresso();

        Platform.runLater(() -> root.requestFocus());

        iniciarTempo();
        iniciarLoop();
    }

    private void iniciarTempo() {
        tempoLabel.setText("Tempo: " + tempoRestante);

        tempoTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tempoRestante--;
            tempoLabel.setText("Tempo: " + tempoRestante);
            reduzirProgresso(PERDA_POR_SEGUNDO);
            atualizarVelocidadeMarcador();

            if (tempoRestante <= 0) {
                perder();
            }
        }));

        tempoTimeline.setCycleCount(tempoLimiteRun);
        tempoTimeline.play();
    }

    private void iniciarLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moverMarcador();
                atualizarRuidoVisual(now);
            }
        };

        gameLoop.start();
    }

    private void moverMarcador() {
        double larguraArea = skillArea.getPrefWidth();
        double larguraMarcador = marcador.getWidth();

        marcadorX += velocidadeMarcador * direcaoMarcador;

        if (marcadorX <= 0) {
            marcadorX = 0;
            direcaoMarcador = 1;
        }

        if (marcadorX >= larguraArea - larguraMarcador) {
            marcadorX = larguraArea - larguraMarcador;
            direcaoMarcador = -1;
        }

        marcador.setX(marcadorX);
    }

    private void tentarAcerto() {
        double inicioMarcador = marcador.getX();
        double fimMarcador = marcador.getX() + marcador.getWidth();

        double inicioZona = zonaAcerto.getX();
        double fimZona = zonaAcerto.getX() + zonaAcerto.getWidth();

        boolean acertou = fimMarcador >= inicioZona && inicioMarcador <= fimZona;

        if (acertou) {
            progresso += GANHO_POR_ACERTO;

            if (progresso > 1.0) {
                progresso = 1.0;
            }

            sortearZonaAcerto();

            if (progresso >= 1.0) {
                vencer();
                return;
            }
        } else {
            erros++;
            reduzirProgresso(PERDA_POR_ERRO);
        }

        atualizarTelaPorProgresso();
    }

    private void reduzirProgresso(double quantidade) {
        progresso -= quantidade;

        if (progresso < 0) {
            progresso = 0;
        }

        atualizarTelaPorProgresso();
    }

    private void atualizarVelocidadeMarcador() {
        double tempoPassado = tempoLimiteRun - tempoRestante;
        double fatorTempo = tempoPassado / tempoLimiteRun;

        if (fatorTempo < 0) {
            fatorTempo = 0;
        }

        if (fatorTempo > 1) {
            fatorTempo = 1;
        }

        velocidadeMarcador = VELOCIDADE_MINIMA + (VELOCIDADE_MAXIMA - VELOCIDADE_MINIMA) * fatorTempo;
    }

    private void sortearZonaAcerto() {
        double larguraArea = skillArea.getPrefWidth();
        double larguraZona = zonaAcerto.getWidth();

        double novoX = 20 + random.nextInt((int) (larguraArea - larguraZona - 40));

        zonaAcerto.setX(novoX);
    }

    private void atualizarTelaPorProgresso() {
        sinalBar.setProgress(progresso);
        porcentagemLabel.setText((int) (progresso * 100) + "%");

        int cinza = 110;
        int azulR = 35;
        int azulG = 105;
        int azulB = 210;

        int r = (int) (cinza + (azulR - cinza) * progresso);
        int g = (int) (cinza + (azulG - cinza) * progresso);
        int b = (int) (cinza + (azulB - cinza) * progresso);

        tvScreen.setStyle(
                "-fx-background-color: rgb(" + r + "," + g + "," + b + ");"
        );

        double opacidadeFalha = Math.max(0.04, 0.55 - (progresso * 0.48));
        falha1.setOpacity(opacidadeFalha);
        falha2.setOpacity(opacidadeFalha * 0.8);
        falha3.setOpacity(opacidadeFalha * 0.9);
        falha4.setOpacity(opacidadeFalha * 0.75);
        falha5.setOpacity(opacidadeFalha * 0.85);
    }

    private void atualizarRuidoVisual(long now) {
        if (now - ultimoRuido < 80_000_000) {
            return;
        }

        ultimoRuido = now;

        double intensidade = 1.0 - progresso;
        int cinza = 110;
        int azulR = 35;
        int azulG = 105;
        int azulB = 210;

        int variacao = (int) (random.nextInt(35) * intensidade);
        int r = limitarCor((int) (cinza + (azulR - cinza) * progresso) + variacao);
        int g = limitarCor((int) (cinza + (azulG - cinza) * progresso) + variacao);
        int b = limitarCor((int) (cinza + (azulB - cinza) * progresso) + variacao);

        tvScreen.setStyle(
                "-fx-background-color: rgb(" + r + "," + g + "," + b + ");"
        );

        reposicionarFalha(falha1, intensidade);
        reposicionarFalha(falha2, intensidade);
        reposicionarFalha(falha3, intensidade);
        reposicionarFalha(falha4, intensidade);
        reposicionarFalha(falha5, intensidade);
    }

    private void reposicionarFalha(Rectangle falha, double intensidade) {
        falha.setY(random.nextInt(286));
        falha.setHeight(4 + random.nextInt(18));
        falha.setOpacity(Math.max(0.03, intensidade * (0.18 + random.nextDouble() * 0.38)));
    }

    private int limitarCor(int valor) {
        if (valor < 0) {
            return 0;
        }

        if (valor > 255) {
            return 255;
        }

        return valor;
    }

    private void vencer() {
        if (venceu) {
            return;
        }

        venceu = true;
        jogoRodando = false;

        pararTudo();

        registrarVitoriaNoSistema();

        painelFinal.setVisible(true);
        painelFinal.setOpacity(0);
        painelFinal.setStyle("-fx-background-color: rgba(15, 30, 70, 0.92);");

        mensagemFinalLabel.setText("O sinal voltou. Uma memória foi encontrada.");
        mensagemFinalLabel.setOpacity(0);

        FadeTransition fadeTela = new FadeTransition(Duration.seconds(1.5), painelFinal);
        fadeTela.setFromValue(0);
        fadeTela.setToValue(1);

        FadeTransition fadeMensagem = new FadeTransition(Duration.seconds(1.5), mensagemFinalLabel);
        fadeMensagem.setFromValue(0);
        fadeMensagem.setToValue(1);

        PauseTransition pausa = new PauseTransition(Duration.seconds(2));

        SequentialTransition transicao = new SequentialTransition(
                fadeTela,
                fadeMensagem,
                pausa
        );

        transicao.setOnFinished(event -> {
            fecharStageDaRun();
        });

        transicao.play();
    }

    private void perder() {
        if (venceu) {
            return;
        }

        jogoRodando = false;

        pararTudo();

        registrarDerrotaNoSistema();

        painelFinal.setVisible(true);
        painelFinal.setOpacity(0);
        painelFinal.setStyle("-fx-background-color: black;");

        mensagemFinalLabel.setText("A televisão apagou. A lembrança se perdeu no chiado.");
        mensagemFinalLabel.setOpacity(0);

        FadeTransition fadeTelaPreta = new FadeTransition(Duration.seconds(2.5), painelFinal);
        fadeTelaPreta.setFromValue(0);
        fadeTelaPreta.setToValue(1);

        FadeTransition fadeMensagem = new FadeTransition(Duration.seconds(2), mensagemFinalLabel);
        fadeMensagem.setFromValue(0);
        fadeMensagem.setToValue(1);

        PauseTransition pausaDepoisMensagem = new PauseTransition(Duration.seconds(2));

        SequentialTransition transicaoPerda = new SequentialTransition(
                fadeTelaPreta,
                fadeMensagem,
                pausaDepoisMensagem
        );

        transicaoPerda.setOnFinished(event -> {
            fecharStageDaRun();
            App.voltarParaQuartoAposPerdaRun();
        });

        transicaoPerda.play();
    }

    private void pararTudo() {
        jogoRodando = false;

        if (gameLoop != null) {
            gameLoop.stop();
            gameLoop = null;
        }

        if (tempoTimeline != null) {
            tempoTimeline.stop();
            tempoTimeline = null;
        }
    }

    private void fecharStageDaRun() {
        podeFecharJanela = true;

        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    private void registrarVitoriaNoSistema() {
        int tempoGasto = tempoLimiteRun - tempoRestante;

        if (tempoGasto < 0) {
            tempoGasto = 0;
        }

        int bonusTempo = EstadoJogo.calcularBonusTempo(tempoLimiteRun, tempoGasto);
        int pontos = EstadoJogo.calcularPontosRun(tempoLimiteRun, tempoGasto);

        boolean primeiraVitoria = EstadoJogo.registrarVitoriaRun(CODIGO_RUN, pontos);

        RunDAO runDAO = new RunDAO();

        runDAO.salvarRun(
                EstadoJogo.getJogadorId(),
                CODIGO_RUN,
                NOME_RUN,
                true,
                pontos,
                bonusTempo,
                erros,
                tempoGasto
        );

        if (primeiraVitoria) {
            runDAO.marcarRunComoVencida(EstadoJogo.getJogadorId(), CODIGO_RUN);
            InventarioJogador.adicionarFragmentoChave(EstadoJogo.getFragmentosChave());
        }

        App.atualizarProgressoBanco();
        App.atualizarInventarioAberto();
    }

    private void registrarDerrotaNoSistema() {
        int tempoGasto = tempoLimiteRun - tempoRestante;

        if (tempoGasto < 0) {
            tempoGasto = 0;
        }

        EstadoJogo.registrarDerrotaRun();

        RunDAO runDAO = new RunDAO();

        runDAO.salvarRun(
                EstadoJogo.getJogadorId(),
                CODIGO_RUN,
                NOME_RUN,
                false,
                0,
                0,
                erros,
                tempoGasto
        );

        App.atualizarProgressoBanco();
    }
}
