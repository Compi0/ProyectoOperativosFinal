/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package buddyarboles;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author 52444
 */
public class PantallaController implements Initializable {

    @FXML
    private Pane fondo;
    @FXML
    private Button botonAgregar;
    @FXML
    private Button botonLiberar;
    @FXML
    private TextField tamSoli;
    @FXML
    private ChoiceBox<?> cAgregar;
    @FXML
    private ChoiceBox<?> cLiberar;
    @FXML
    private Pane barra;
    @FXML
    private TextArea registro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void imprimeSol(ActionEvent event) {
    }

    @FXML
    private void imprimeLib(ActionEvent event) {
    }

    @FXML
    private void salir(ActionEvent event) {
    }
    
}
