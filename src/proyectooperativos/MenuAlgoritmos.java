/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package proyectooperativos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author 52444
 */
public class MenuAlgoritmos implements Initializable {

    @FXML
    private Button pajuste;
    @FXML
    private Pane menu;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeFadeInTransition();
    }    

    @FXML
    private void PrimerAjuste(ActionEvent event) {
        loadNextScene1();
    }
    
    private void loadNextScene1(){
        try {
            Parent secondView;
            secondView = (Pane) FXMLLoader.load(getClass().getResource("FXMLDocumentPrimerAjuste.fxml"));
            Scene newScene = new Scene(secondView);
            Stage curStage  = (Stage) menu.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentControllerInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void makeFadeInTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(1));
        fadeTransition.setNode(menu);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @FXML
    private void MejorAjuste(ActionEvent event) {
        try {
            Parent secondView;
            secondView = (Pane) FXMLLoader.load(getClass().getResource("FXMLDocumentMejorAjuste.fxml"));
            Scene newScene = new Scene(secondView);
            Stage curStage  = (Stage) menu.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentControllerInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void SistemaBuddy(ActionEvent event) {
        try {
            Parent secondView;
            secondView = (Pane) FXMLLoader.load(getClass().getResource("Pantalla.fxml"));
            Scene newScene = new Scene(secondView);
            Stage curStage  = (Stage) menu.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentControllerInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    private void UltimoAjuste(ActionEvent event) {
        try {
            Parent secondView;
            secondView = (Pane) FXMLLoader.load(getClass().getResource("FXMLDocumentUltimoAjuste.fxml"));
            Scene newScene = new Scene(secondView);
            Stage curStage  = (Stage) menu.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentControllerInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
