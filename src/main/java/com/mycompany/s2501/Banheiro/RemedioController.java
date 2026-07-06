
package com.mycompany.s2501.Banheiro;

import com.mycompany.s2501.App;
import java.io.IOException;
import javafx.fxml.FXML;

/**
 *
 * @author livia
 */
public class RemedioController {
    @FXML
    private void voltar() throws IOException {
        App.setRoot("armariobact");
    }
    @FXML
    private void abrirInventario() {
        App.toggleInventario();
    }
}
