/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.s2501.Runs;

import com.mycompany.s2501.App;
import java.util.ArrayList;
import java.util.Iterator;
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
    private final double alturaPlayer = 220;

    private final double larguraNpc = 70;
    private final double alturaNpc = 100;
    
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

    private boolean esquerdaPressionada;
    private boolean direitaPressionada;
    private boolean jogoRodando;
    private boolean interagiuComNpc;
    private boolean venceu;

    private int tempoRestante = 90;

    private final double larguraTela = 1200;
    private final double alturaTela = 400;

    private final double velocidadePlayer = 4;
    private final double velocidadeNpc = 2;

    private final double chaoY = 390;

    private final double bancoXInicio = 260;
    private final double bancoXFim = 560;

    private Image bg0;
    private Image bg1;
    private Image bg2;

    private Image playerSide;
    private Image playerFront;
    private Image playerSit;

    private Image[] playerWalkFrames;
    private int walkFrameAtual = 0;
    private long ultimoFrameTroca = 0;

    @FXML
private void initialize() {
    carregarImagens();

    background.setImage(bg0);

    player.setFitWidth(larguraPlayer);
    player.setFitHeight(alturaPlayer);
    player.setImage(playerSide);

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
                        pararTudo();
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
            new Image(App.class.getResourceAsStream("/com/mycompany/s2501/runp/walk3.png"))
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
        tempoRestante = 90;

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

        tempoTimeline.setCycleCount(90);
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
                moverPlayer();
                moverNpcs();
            }
        };

        gameLoop.start();
    }

private void moverPlayer() {
    double x = player.getLayoutX();
    boolean estaAndando = false;

    if (esquerdaPressionada) {
        x -= velocidadePlayer;

        // A imagem side.png já olha para a esquerda
        player.setScaleX(1);

        estaAndando = true;
    }

    if (direitaPressionada) {
        x += velocidadePlayer;

        // Para andar para a direita, precisa espelhar
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
        animarCaminhada();
    } else {
        player.setImage(playerSide);
        walkFrameAtual = 0;
    }
}
 private void criarNpc() {
    ImageView npc = new ImageView(playerSide);

    npc.setFitWidth(larguraNpc);
    npc.setFitHeight(alturaNpc);
    npc.setPreserveRatio(true);

    boolean vemDaEsquerda = random.nextBoolean();

    if (vemDaEsquerda) {
        npc.setLayoutX(-80);

        // NPC vai para a direita, então precisa espelhar
        npc.setScaleX(-1);

        npc.setUserData("direita");
    } else {
        npc.setLayoutX(larguraTela + 80);

        // NPC vai para a esquerda, imagem original já olha para a esquerda
        npc.setScaleX(1);

        npc.setUserData("esquerda");
    }

    npc.setLayoutY(chaoY - npc.getFitHeight());

    npcs.add(npc);
    npcLayer.getChildren().add(npc);
}
private void mandarNpcEmbora(ImageView npc) {
    if (npc.getLayoutX() < player.getLayoutX()) {
        npc.setUserData("esquerda");

        // Indo embora para a esquerda
        npc.setScaleX(1);
    } else {
        npc.setUserData("direita");

        // Indo embora para a direita
        npc.setScaleX(-1);
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
        for (ImageView npc : npcs) {
            double distancia = Math.abs(player.getLayoutX() - npc.getLayoutX());

            if (distancia < 80) {
                return npc;
            }
        }

        return null;
    }

   private void mandarNpcEmbora(ImageView npc) {
    if (npc.getLayoutX() < player.getLayoutX()) {
        npc.setUserData("esquerda");

        // Indo embora para a esquerda
        npc.setScaleX(1);
    } else {
        npc.setUserData("direita");

        // Indo embora para a direita
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
        mensagemFinalLabel.setText("Você encontrou descanso no silêncio.");

        fecharDepoisDeAlgunsSegundos();
    }

    private void perder() {
        if (venceu) {
            return;
        }

        jogoRodando = false;

        pararTudo();

        painelFinal.setVisible(true);
        mensagemFinalLabel.setText("A praça escureceu. O descanso não veio.");

        fecharDepoisDeAlgunsSegundos();
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
            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        }));

        fechar.setCycleCount(1);
        fechar.play();
    }
}