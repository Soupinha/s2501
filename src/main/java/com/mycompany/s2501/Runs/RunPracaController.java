
package com.mycompany.s2501.Runs;

import com.mycompany.s2501.App;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;
import java.util.Iterator;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import com.mycompany.s2501.Model.EstadoJogo;
import com.mycompany.s2501.Model.InventarioJogador;
import com.mycompany.s2501.dao.RunDAO;


public class RunPracaController {

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView background;

    @FXML
    private ImageView player;

    @FXML
    private AnchorPane npcLayer;
     
    private boolean jogoIniciado = false;

    private final double larguraPlayer = 160;
    private final double alturaPlayer = 230;

    private final double larguraNpc = 120;
    private final double alturaNpc = 230;

    private final double distanciaInteracaoNpc = 140;
    
    private static final String CODIGO_RUN = "RUN_PRACA";
    private static final String NOME_RUN = "Run Praça";
    private static final int TEMPO_LIMITE_RUN = 90;
    
    @FXML
    private AnchorPane painelInicio;

    @FXML
    private AnchorPane painelFinal;

    @FXML
    private Label mensagemFinalLabel;

    @FXML
    private Label tempoLabel;

    private final Random random = new Random();
    private final List<ImageView> npcs = new ArrayList<>();

    private AnimationTimer gameLoop;
    private Timeline tempoTimeline;
    private Timeline spawnTimeline;
    
    private boolean podeFecharJanela = false;

    private boolean jaSeMoveu = false;
    private long ultimoMovimentoNano = 0;
    private final long tempoParadoParaFrontNano = 10_000_000_000L;

    private boolean esquerdaPressionada;
    private boolean direitaPressionada;
    private boolean jogoRodando;
    private boolean interagiuComNpc;
    private boolean venceu;

    private int tempoRestante = TEMPO_LIMITE_RUN;
    private int tempoLimiteRun = TEMPO_LIMITE_RUN;

    private final double larguraTela = 1200;
    private final double alturaTela = 400;

    private final double velocidadePlayer = 4;
    private final double velocidadeNpc = 2;

    private final double chaoY = 397;

    private final double bancoXInicio = 260;
    private final double bancoXFim = 560;

    private Image bg0;
    private Image bg1;
    private Image bg2;

    private Image playerSide;
    private Image playerFront;
    private Image playerSit;

    private Image[] playerWalkFrames;
    private Image[] npcWalkFrames;

    private int walkFrameAtual = 0;
    private int npcFrameAtual = 0;

    private long ultimoFrameTroca = 0;
    private long ultimoFrameNpcTroca = 0;

    @FXML
private void initialize() {
    carregarImagens();

    background.setImage(bg0);

    player.setFitWidth(larguraPlayer);
    player.setFitHeight(alturaPlayer);
    player.setImage(playerFront);
    player.setScaleX(1);

    posicionarPlayerNoChao();

    root.setFocusTraversable(true);

    registrarFechamentoDaJanela();

    root.setOnKeyPressed(event -> {
        if (!jogoRodando) {
            return;
        }

        if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
            esquerdaPressionada = true;
        }

        if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
            direitaPressionada = true;
        }

        if (event.getCode() == KeyCode.E) {
            interagir();
        }
    });

    root.setOnKeyReleased(event -> {
        if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
            esquerdaPressionada = false;
        }

        if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
            direitaPressionada = false;
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
    private void carregarImagens() {
        bg0 = new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/bg_stage0.png"));
        bg1 = new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/bg_stage1.png"));
        bg2 = new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/bg_stage2.png"));

        playerSide = new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/side.png"));
        playerFront = new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/front.png"));
        playerSit = new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/sit.png"));

        playerWalkFrames = new Image[]{
            new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/walk1.png")),
            new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/walk2.png")),
            new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/walk3.png")),
            
        };
        
        npcWalkFrames = new Image[]{
            new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/npc/npcw1.png")),
            new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/npc/npcw2.png")),
            new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/npc/npcw3.png")),
            new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/npc/npcw2.png"))
        };
        
    }

    private void posicionarPlayerNoChao() {
        player.setLayoutY(chaoY - player.getFitHeight());
    }

    @FXML
    private void iniciarJogo() {
        if (jogoIniciado || jogoRodando) {
            return;
        }

        jogoIniciado = true;

        painelInicio.setVisible(false);
        jogoRodando = true;
        interagiuComNpc = false;
        venceu = false;
        tempoLimiteRun = EstadoJogo.consumirTempoLimiteComBonus(TEMPO_LIMITE_RUN);
        tempoRestante = tempoLimiteRun;
        
        jaSeMoveu = false;
        ultimoMovimentoNano = System.nanoTime();

        player.setImage(playerFront);
        player.setScaleX(1);

        Platform.runLater(() -> root.requestFocus());

        iniciarTempo();
        iniciarSpawnNpc();
        iniciarLoop();
    }
    
 

    private void iniciarTempo() {
        tempoLabel.setText("Tempo: " + tempoRestante);

        tempoTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tempoRestante--;
            tempoLabel.setText("Tempo: " + tempoRestante);

            if (tempoRestante == 60) {
                background.setImage(bg1);
            }

            if (tempoRestante == 30) {
                background.setImage(bg2);
            }

            if (tempoRestante <= 0) {
                perder();
            }
        }));

        tempoTimeline.setCycleCount(tempoLimiteRun);
        tempoTimeline.play();
    }

    private void iniciarSpawnNpc() {
        spawnTimeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            if (!jogoRodando) {
                return;
            }

            if (tempoRestante <= 10) {
                return;
            }

            int chance;

            if (tempoRestante > 60) {
                chance = 90;
            } else if (tempoRestante > 30) {
                chance = 55;
            } else {
                chance = 25;
            }

            if (random.nextInt(100) < chance) {
                criarNpc();
            }
        }));

        spawnTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnTimeline.play();
    }

    private void iniciarLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moverPlayer(now);
                moverNpcs(now);
            }
        };

        gameLoop.start();
    }

    private void moverPlayer(long now) {
        double x = player.getLayoutX();
        boolean estaAndando = false;

        if (esquerdaPressionada) {
            x -= velocidadePlayer;

            // A imagem side/walk olha para a esquerda.
            player.setScaleX(1);

            estaAndando = true;
        }

        if (direitaPressionada) {
            x += velocidadePlayer;

            // Para andar para a direita, espelha.
            player.setScaleX(-1);

            estaAndando = true;
        }

        if (x < 0) {
            x = 0;
        }

        if (x > larguraTela - player.getFitWidth()) {
            x = larguraTela - player.getFitWidth();
        }

        player.setLayoutX(x);
        posicionarPlayerNoChao();

        if (estaAndando) {
            jaSeMoveu = true;
            ultimoMovimentoNano = now;
            animarCaminhada();
        } else {
            if (!jaSeMoveu || now - ultimoMovimentoNano >= tempoParadoParaFrontNano) {
                player.setImage(playerFront);
                player.setScaleX(1);
            } else {
                player.setImage(playerSide);
            }

            walkFrameAtual = 0;
        }
    }
    private void criarNpc() {
        ImageView npc = new ImageView(npcWalkFrames[0]);

        npc.setFitHeight(alturaNpc);
        npc.setPreserveRatio(true);
        npc.setSmooth(false);

        boolean vemDaEsquerda = random.nextBoolean();

        if (vemDaEsquerda) {
            npc.setLayoutX(-larguraNpc);

            // O sprite original olha/anda para a esquerda.
            // Para ir para a direita, precisa espelhar.
            npc.setScaleX(-1);

            npc.setUserData("direita");
        } else {
            npc.setLayoutX(larguraTela + larguraNpc);

            // Indo para a esquerda, usa normal.
            npc.setScaleX(1);

            npc.setUserData("esquerda");
        }

        npc.setLayoutY(chaoY - alturaNpc);

        npcs.add(npc);
        npcLayer.getChildren().add(npc);
    }
    
    
    private void moverNpcs(long now) {
        animarNpcs(now);

        Iterator<ImageView> iterator = npcs.iterator();

        while (iterator.hasNext()) {
            ImageView npc = iterator.next();

            npc.setImage(npcWalkFrames[npcFrameAtual]);
            npc.setFitHeight(alturaNpc);
            npc.setPreserveRatio(true);
            npc.setSmooth(false);

            String direcao = (String) npc.getUserData();

            if ("direita".equals(direcao)) {
                npc.setLayoutX(npc.getLayoutX() + velocidadeNpc);
                npc.setScaleX(-1);
            } else {
                npc.setLayoutX(npc.getLayoutX() - velocidadeNpc);
                npc.setScaleX(1);
            }

            npc.setLayoutY(chaoY - alturaNpc);

            if (npc.getLayoutX() < -larguraNpc - 40 || npc.getLayoutX() > larguraTela + larguraNpc + 40) {
                npcLayer.getChildren().remove(npc);
                iterator.remove();
            }
        }
    }
    
    private void animarNpcs(long now) {
        if (now - ultimoFrameNpcTroca > 180_000_000) {
            npcFrameAtual++;

            if (npcFrameAtual >= npcWalkFrames.length) {
                npcFrameAtual = 0;
            }

            ultimoFrameNpcTroca = now;
        }
    }

    
    private void interagir() {
        ImageView npcProximo = encontrarNpcProximo();

        if (npcProximo != null) {
            interagiuComNpc = true;
            mandarNpcEmbora(npcProximo);
            return;
        }

        if (estaPertoDoBanco()) {
            sentarNoBanco();
        }
    }

    private ImageView encontrarNpcProximo() {
        double centroPlayer = player.getLayoutX() + player.getFitWidth() / 2;

        for (ImageView npc : npcs) {
            double centroNpc = npc.getLayoutX() + larguraNpc / 2;
            double distancia = Math.abs(centroPlayer - centroNpc);

            if (distancia < distanciaInteracaoNpc) {
                return npc;
            }
        }

        return null;
    }

    private void mandarNpcEmbora(ImageView npc) {
        if (npc.getLayoutX() < player.getLayoutX()) {
            npc.setUserData("esquerda");
            npc.setScaleX(1);
        } else {
            npc.setUserData("direita");
            npc.setScaleX(-1);
        }
    }

    private boolean estaPertoDoBanco() {
        double centroPlayer = player.getLayoutX() + player.getFitWidth() / 2;
        return centroPlayer >= bancoXInicio && centroPlayer <= bancoXFim;
    }

    private void sentarNoBanco() {
        if (interagiuComNpc) {
            return;
        }

        vencer();
    }

    private void vencer() {
        venceu = true;
        jogoRodando = false;

        pararTudo();

        npcLayer.getChildren().clear();
        npcs.clear();

        player.setImage(playerSit);
        player.setScaleX(1);
        player.setFitWidth(larguraPlayer);
        player.setFitHeight(alturaPlayer);
        player.setLayoutX(390);
        posicionarPlayerNoChao();

        painelFinal.setVisible(true);
        mensagemFinalLabel.setText("Ela não queria estar só, apenas não sabia mais chamar alguém.");
        
        registrarVitoriaNoSistema();

        fecharDepoisDeAlgunsSegundos();
    }

    private void perder() {
        if (venceu) {
            return;
        }

        jogoRodando = false;

        pararTudo();

        npcLayer.getChildren().clear();
        npcs.clear();

        tempoLabel.setVisible(false);

        painelFinal.setVisible(true);
        painelFinal.setOpacity(0);
        painelFinal.setStyle("-fx-background-color: black;");

        mensagemFinalLabel.setText("A praça escureceu. O banco parece maior quando se senta sozinha.");
        mensagemFinalLabel.setOpacity(0);
        registrarDerrotaNoSistema();

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
            App.voltarParaQuartoAposPerdaRunPraca();
        });

        transicaoPerda.play();
    }

private void pararTudo() {
    jogoRodando = false;

    esquerdaPressionada = false;
    direitaPressionada = false;

    if (gameLoop != null) {
        gameLoop.stop();
        gameLoop = null;
    }

    if (tempoTimeline != null) {
        tempoTimeline.stop();
        tempoTimeline = null;
    }

    if (spawnTimeline != null) {
        spawnTimeline.stop();
        spawnTimeline = null;
    }
}


    
    private void animarCaminhada() {
        long agora = System.nanoTime();

        if (agora - ultimoFrameTroca > 150_000_000) {
            walkFrameAtual++;

            if (walkFrameAtual >= playerWalkFrames.length) {
                walkFrameAtual = 0;
            }

            player.setImage(playerWalkFrames[walkFrameAtual]);
            ultimoFrameTroca = agora;
        }
    }

    private void fecharDepoisDeAlgunsSegundos() {
        Timeline fechar = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            fecharStageDaRun();
        }));

        fechar.setCycleCount(1);
        fechar.play();
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
                0,
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
                1,
                tempoGasto
        );

        App.atualizarProgressoBanco();
    }    
    
    
    
}
