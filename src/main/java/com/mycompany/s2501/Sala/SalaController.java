package com.mycompany.s2501.Sala;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
    //tudo okay
public class SalaController {
    
    @FXML
    private void voltar() throws IOException {
        App.setRoot("corredorprincipal");
    }
    @FXML
    private void sentar() throws IOException {
        App.setRoot("sofa");
    }
    @FXML
    private void verj() throws IOException {
        App.setRoot("janela");
    }
    @FXML
    private void verf() throws IOException {
        App.setRoot("foto");
    }
    @FXML
    private void abrir() throws IOException {
        App.setRoot("armarios");
    }
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
    
    
    @FXML
    private Button botaoContinuar;
     
    @FXML
    private Label floatingText;

}