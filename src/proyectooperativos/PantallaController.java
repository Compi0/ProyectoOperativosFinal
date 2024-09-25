/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package proyectooperativos;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author madie
 */
public class PantallaController implements Initializable {
    
    //<editor-fold defaultstate="collapsed" desc="Variables Globales">
    private ObservableList<String> list = FXCollections.observableArrayList("A", "B", "C", "D", "E", "F", "G","H","I","J", "K", "L");
    private Tree arbol= new Tree();
    private ArrayList<Rectangle> disponibles = new ArrayList<>();
    private ArrayList<Rectangle> ocupado = new ArrayList<>();
    public Pane copiaBarra;
    public float fragmentacion = 0, total = 0;
    
    private AnchorPane Pantalla1;
    @FXML
    private Button botonAgregar;
    @FXML
    private Button botonLiberar;
    @FXML
    private ChoiceBox<String> cAgregar;
    @FXML
    private ChoiceBox<String> cLiberar;
    @FXML
    private TextField tamSoli;
    @FXML
    private Pane barra;
    @FXML
    private TextArea registro;
    //</editor-fold>
    @FXML
    private Pane fondo;

    /**
     * Initializes the controller class.
     */
    //<editor-fold defaultstate="collapsed" desc="Inicializa">
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cAgregar.setValue("A");
        cAgregar.setItems(list);
        cLiberar.setValue("A");
        cLiberar.setItems(list);
        Label num = new Label();
        num.setText("1024");
        num.textFillProperty().set(Color.web("#012535"));
        num.setFont(new Font("Lucida Sans", 20));
        num.setLayoutX(470);
        num.setLayoutY(85);
        barra.getChildren().add(num);
        num.toFront();
        
        this.copiaBarra = barra;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Salir">
    @FXML
    public void salir(){
        System.out.println("salir");
        fragmentacion(); 
        
        Rectangle rect = new Rectangle();
        rect = new Rectangle();
            rect.setX(0);
            rect.setY(0);
            rect.setWidth(1000);
            rect.setHeight(1000);
            rect.setFill(Color.rgb(191, 210, 217));
            fondo.getChildren().add(rect);
        pie();
        Label lab = new Label("Fragmentacion total: "+ fragmentacion+"%");
        lab.setFont(new Font("High Tower Text", 40));
        lab.setLayoutX(250);
        lab.setLayoutY(50);
        fondo.getChildren().add(lab);
        Button but = new Button("Regresar al menu");
        but.setLayoutX(400);
        but.setLayoutY(200);
        but.setFont(Font.font("High Tower Text"));
       

        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadNextScene();
            }
        });
        fondo.getChildren().add(but);
    }
    
    private void fragmentacion(){
        /*fragmentacion = 50;
        total = 100;*/
        fragmentacion = arbol.obtenFrag();
        total = 100-fragmentacion;
    }
    
    private void pie(){
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Memoria fragmentada", fragmentacion),
                new PieChart.Data("Memoria restante", total-fragmentacion));
        
        final PieChart chart = new PieChart(pieChartData);
        chart.setLayoutX(200);
        chart.setLayoutY(150);
        chart.setTitle("Representacion visual de la fragmentacion");
        fondo.getChildren().add(chart);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Agregar">
    @FXML
    public void imprimeSol(){
        if(verificaL()){
            return;
        }
        int p = 0;
        NodoTree z = new NodoTree(0, Integer.parseInt(tamSoli.getText()));
        z.name = cAgregar.getValue();
        p = arbol.particiona(arbol, arbol.root, z, p);
        System.out.println("Se particiono " + p + " veces");
        if((disponibles.isEmpty()) && (p > 0)){
            disponibles = partInit(p, disponibles);
        }else if(p == 0){
            disponibles = agregaNuevo(p);
        }else if(p < 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No hay espacio disponible");
            alert.show();
        }else{
            disponibles = agregaPart(p);
        }
        registro.setText(arbol.registro);
        muestra();
    }
    
    public void muestra(){
        System.out.println("Disponible:");
        for (int i = 0; i < disponibles.size(); i++) {
            System.out.print(disponibles.get(i).getUserData() + " - ");
        }
        System.out.println("fin\nOcupado: ");
        
        for (int i = 0; i < ocupado.size(); i++) {
            System.out.print("letra: " + ocupado.get(i).getId() + " tam "+ ocupado.get(i).getUserData() + " - ");
        }
        System.out.println("fin");
    }
    
    public boolean verificaL(){
        for(int i=0; i < ocupado.size(); i++){
            if(ocupado.get(i).getId().equals(cAgregar.getValue())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Ya hay un proceso llamado " + cAgregar.getValue());
                alert.show();
                return true;
            }
        }
        System.out.println(tamSoli.getText());
        if((tamSoli.getText().equals(" ")) || (tamSoli.getText().equals(""))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No le has dado valor al tamaño de la solicitud");
            alert.show();
            return true;
        }
        if(Integer.parseInt(tamSoli.getText()) <= 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Número no válido, debe ser un número mayor a 0 (RECOMENDACIÓN: si lo quieres apreciar mejor deberá ser un número mayor a 32)");
            alert.show();
            return true;
        }
        if(Integer.parseInt(tamSoli.getText()) > 1024){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No hay espacio disponible, procura que el tamaño sea menor a 1024");
            alert.show();
            return true;
        }
        
        return false;
    }
    
    public ArrayList part(int p, ArrayList disponibles, int tam, double w, int x, int index){
        for (int i = 1; i < p+1; i++) {
            w = w/2;
            tam = tam/2;
            Rectangle rect = new Rectangle();
            rect.setX(x);
            rect.setY(5);
            rect.setUserData(tam);
            rect.setWidth(w);
            rect.setHeight(110);
            rect.setFill(Color.web("#012535"));
            barra.getChildren().add(rect);
            rect.toFront();
            if(i == p){
                rect.setId(cAgregar.getValue());
                ocupado.add(rect);
                Label dato = new Label();
                dato.setText(cAgregar.getValue());
                dato.textFillProperty().set(Color.WHITE);
                dato.setFont(new Font("Lucida Sans", 20));
                dato.setLayoutX((rect.getWidth()/2)-7+x);
                dato.setLayoutY(45);
                barra.getChildren().add(dato);
                dato.toFront();
                
                Label num = new Label();
                num.setText(Integer.toString(tam));
                num.textFillProperty().set(Color.WHITE);
                num.setFont(new Font("Lucida Sans", 20));
                num.setLayoutX((rect.getWidth()/2)-10+x);
                num.setLayoutY(85);
                barra.getChildren().add(num);
                num.toFront();
            }
            
            Rectangle sep = new Rectangle();
            sep.setX(w+x);
            sep.setY(5);
            sep.setWidth(2);
            sep.setHeight(110);
            sep.setFill(Color.web("#FFFFFF"));
            barra.getChildren().add(sep);
            
            rect = new Rectangle();
            rect.setX(w+x);
            rect.setY(5);
            rect.setUserData(tam);
            rect.setWidth(w-2);
            rect.setHeight(110);
            rect.setFill(Color.web("#3B89AD"));
            barra.getChildren().add(rect);
            rect.toFront();
            sep.toFront();
            disponibles.set(index, rect);
            Label num = new Label();
            num.setText(Integer.toString(tam));
            num.textFillProperty().set(Color.web("#012535"));
            num.setFont(new Font("Lucida Sans", 20));
            num.setLayoutX(rect.getWidth()/2-10 + rect.getX());
            num.setLayoutY(85);
            barra.getChildren().add(num);
            num.toFront();
        }
        return disponibles;
    }
    
    public ArrayList agregaPart(int p){
        for (int i = disponibles.size()-1; i >= 0; i--) {
            int soli = Integer.parseInt(tamSoli.getText());
            int disp = (int)disponibles.get(i).getUserData();
            int menor = disp;
            for (int j = 0; j < p; j++) {
                menor = menor/2;
                menor = menor/2;
            }
            System.out.println((disp/2) + " <= " + soli + " < " + disp);
            if(soli >= menor && (soli < disp)){
                double w = disponibles.get(i).getWidth();
                int tam = (int)disponibles.get(i).getUserData();
                int x = (int)disponibles.get(i).getX();
                System.out.println("w: " + w + " tam: " + tam + " x: " + x);
                Rectangle par = disponibles.get(i);
                barra.getChildren().remove(par);
                disponibles = part(p, disponibles, tam, w, x+2, i);
                break;
            }
        }
        
        return disponibles;
    }
    
    public ArrayList agregaNuevo(int p){
        for (int i = 0; i < disponibles.size(); i++) {
            int soli = Integer.parseInt(tamSoli.getText());
            int disp = (int)disponibles.get(i).getUserData();
            System.out.println((disp/2) + " < " + soli + " <= " + disp);
            if(soli > (disp/2) && (soli <= disp)){
                disponibles.get(i).setFill(Color.web("#012535"));
                disponibles.get(i).setId(cAgregar.getValue());
                Rectangle lleno = disponibles.get(i);
                disponibles.remove(i);
                ocupado.add(lleno);
                Label dato = new Label();
                dato.setText(cAgregar.getValue());
                dato.textFillProperty().set(Color.WHITE);
                dato.setFont(new Font("Lucida Sans", 20));
                dato.setLayoutX((lleno.getWidth()/2-2) + (lleno.getX()));
                dato.setLayoutY(45);
                barra.getChildren().add(dato);
                dato.toFront();
                
                Label num = new Label();
                num.setText(lleno.getUserData().toString());
                num.textFillProperty().set(Color.WHITE);
                num.setFont(new Font("Lucida Sans", 20));
                num.setLayoutX((lleno.getWidth()/2)-10+lleno.getX());
                num.setLayoutY(85);
                barra.getChildren().add(num);
                num.toFront();
                break;
            }
        }
        
        return disponibles;
    }
    
    public ArrayList partInit(int p, ArrayList disponibles){
        double w = 950;
        int tam = 1024;
        for (int i = 1; i < p+1; i++) {
            w = w/2;
            tam = tam/2;
            Rectangle rect = new Rectangle();
            rect.setX(5);
            rect.setY(5);
            rect.setUserData(tam);
            rect.setWidth(w);
            rect.setHeight(110);
            rect.setFill(Color.web("#012535"));
            barra.getChildren().add(rect);
            rect.toFront();
            if(i == p){
                rect.setId(cAgregar.getValue());
                ocupado.add(rect);
                Label dato = new Label();
                dato.setText(cAgregar.getValue());
                dato.textFillProperty().set(Color.WHITE);
                dato.setFont(new Font("Lucida Sans", 20));
                dato.setLayoutX(rect.getWidth()/2-2);
                dato.setLayoutY(45);
                barra.getChildren().add(dato);
                dato.toFront();
                
                Label num = new Label();
                num.setText(Integer.toString(tam));
                num.textFillProperty().set(Color.WHITE);
                num.setFont(new Font("Lucida Sans", 20));
                num.setLayoutX(rect.getWidth()/2-5);
                num.setLayoutY(85);
                barra.getChildren().add(num);
                num.toFront();
            }
            
            Rectangle sep = new Rectangle();
            sep.setX(w+5);
            sep.setY(5);
            sep.setWidth(2);
            sep.setHeight(110);
            sep.setFill(Color.web("#FFFFFF"));
            barra.getChildren().add(sep);
            
            rect = new Rectangle();
            rect.setX(w+5);
            rect.setY(5);
            rect.setUserData(tam);
            rect.setWidth(w);
            rect.setHeight(110);
            rect.setFill(Color.web("#3B89AD"));
            barra.getChildren().add(rect);
            rect.toFront();
            sep.toFront();
            disponibles.add(rect);
            Label num = new Label();
            num.setText(Integer.toString(tam));
            num.textFillProperty().set(Color.web("#012535"));
            num.setFont(new Font("Lucida Sans", 20));
            num.setLayoutX(rect.getWidth()/2-10 + rect.getX());
            num.setLayoutY(85);
            barra.getChildren().add(num);
            num.toFront();
        }
        return disponibles;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Eliminar">
    @FXML
    public void imprimeLib(){
        if(!verificaB()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("No hay un proceso llamado " + cLiberar.getValue());
            alert.show();
            return;
        }
        int p = 0;
        NodoTree z = arbol.buscaCola(cLiberar.getValue());
        int b = arbol.liberar(z);
        System.out.println("Se junto: " + b + " veces");
        if(b == 0){
            elimina();
        }else{
            expande(b);
        }
        muestra();
    }
    
    public boolean verificaB(){
        for(int i=0; i < ocupado.size(); i++){
            if(ocupado.get(i).getId().equals(cLiberar.getValue())){
                return true;
            }
        }
        
        return false;
    }
    
    public boolean verificaCerca(double pos, double w, int tam){
        for (int i = 0; i < ocupado.size(); i++) {
            System.out.println(ocupado.get(i).getX() + " < " + (pos+w) + " Donde pos : " + pos + " y w: " + w);
            if(ocupado.get(i).getX() > pos){
                if(ocupado.get(i).getX() < (pos+w)){
                    return false;
                }
            }
        }
        
        for (int i = disponibles.size()-1; i >= 0; i--) {
            if((i == disponibles.size()-1) && (disponibles.get(i).getX() > pos)){
                disponibles.remove(i);
                return true;
            }
            if( i > 0 && (disponibles.get(i).getX() < pos) && (disponibles.get(i-1).getX() > pos)){
                if((int)disponibles.get(i).getUserData() == tam){
                    disponibles.remove(i);
                    return false;
                }else if((int)disponibles.get(i).getUserData() != tam && (int)disponibles.get(i-1).getUserData() == tam){
                    disponibles.remove(i+1);
                    return true;
                }
            }
        }
        disponibles.remove(0);
        return true;
    }
    
    public void expande(int b){
        for (int i = 0; i < ocupado.size(); i++) {
            Rectangle x = ocupado.get(i);
            if(x.getId().equals(cLiberar.getValue())){
                ocupado.remove(i);
                //disponibles.add(i, x);
                double w = x.getWidth();
                int tam = (int)x.getUserData();
                for (int j = 0; j < b; j++) {
                    double pos = x.getX();
                    if(!verificaCerca(pos, w*2, tam)){
                        pos = pos-w;
                    }
                    w = w*2;
                        tam = tam*2;
                    if(!ocupado.isEmpty()){
                        Rectangle rect = new Rectangle();
                        rect.setX(pos);
                        rect.setY(5);
                        rect.setUserData(tam);
                        rect.setWidth(w-2);
                        rect.setHeight(110);
                        rect.setFill(Color.web("#3B89AD"));
                        barra.getChildren().add(rect);
                        rect.toFront();
                        //disponibles.set(index, rect);
                        int ind = ajustaIndice(rect.getX());
                        disponibles.add(ind, rect);

                        Label num = new Label();
                        num.setText(Integer.toString(tam));
                        num.textFillProperty().set(Color.web("#012535"));
                        num.setFont(new Font("Lucida Sans", 20));
                        num.setLayoutX(rect.getWidth()/2-10 + rect.getX());
                        num.setLayoutY(85);
                        barra.getChildren().add(num);
                        num.toFront();
                    }
                   
                    if(tam == 1024){
                        disponibles = new ArrayList<>();
                        Rectangle rect = new Rectangle();
                        rect.setX(5);
                        rect.setY(5);
                        rect.setUserData(tam);
                        rect.setWidth(w-2);
                        rect.setHeight(110);
                        rect.setFill(Color.web("#3B89AD"));
                        barra.getChildren().add(rect);
                        rect.toFront();
                        //disponibles.set(index, rect);

                        Label num = new Label();
                        num.setText("1024");
                        num.textFillProperty().set(Color.web("#012535"));
                        num.setFont(new Font("Lucida Sans", 20));
                        num.setLayoutX(470);
                        num.setLayoutY(85);
                        barra.getChildren().add(num);
                        num.toFront();
                    }
                }
            }
        }
    }
    
    public void elimina(){
        for (int i = 0; i < ocupado.size(); i++) {
            Rectangle x = ocupado.get(i);
            if(x.getId().equals(cLiberar.getValue())){
                x.setFill(Color.web("#3B89AD"));
                x.toFront();
                Label num = new Label();
                num.setText(x.getUserData().toString());
                num.textFillProperty().set(Color.web("#012535"));
                num.setFont(new Font("Lucida Sans", 20));
                num.setLayoutX((x.getWidth()/2)-10+x.getX());
                num.setLayoutY(85);
                barra.getChildren().add(num);
                num.toFront();
                ocupado.remove(i);
                int pos = ajustaIndice(x.getX());
                disponibles.add(pos, x);
                
                Rectangle sep = new Rectangle();
                sep.setX(x.getX());
                sep.setY(5);
                sep.setWidth(2);
                sep.setHeight(110);
                sep.setFill(Color.web("#FFFFFF"));
                barra.getChildren().add(sep);
            }
        }
    }
    
    public int ajustaIndice(double x){
        if(disponibles.get(disponibles.size()-1).getX() > x)
            return disponibles.size();
        for (int i = disponibles.size()-1; i > 0; i--) {
            System.out.println(disponibles.get(i).getX() + " < " + x + " < " + disponibles.get(i-1).getX());
            if((disponibles.get(i).getX() < x) && (disponibles.get(i-1).getX() > x)){
                return i;
            }
        }
        return disponibles.size();
    }
    //</editor-fold>
    
    private void makeFadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(1));
        fadeTransition.setNode(fondo);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((ActionEvent event)->{
                loadNextScene();
        });
        fadeTransition.play();
    }
    
    private void loadNextScene(){
        try {
            Parent secondView;
            secondView = (Pane) FXMLLoader.load(getClass().getResource("FXMLDocumentMenu.fxml"));
            Scene newScene = new Scene(secondView);
            Stage curStage  = (Stage) fondo.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(PantallaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
