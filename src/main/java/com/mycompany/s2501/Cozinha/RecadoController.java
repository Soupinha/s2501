/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.s2501.Cozinha;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

public class RecadoController {
     @FXML
    private void voltar() throws IOException {
        App.setRoot("bolo");
    }
    
    
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}
