package proyectooperativos;
//Declaración de todas las liberarias a utilizar durante el proyecto
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Diego
 */
public class AlgoritmoUltimoAjuste implements Initializable {
    
    int x = 30, y=190;
    int x1 = 30, y1=170;
    int x2 = 30, y2 = 450; int y3 = 430;
    int x5 = 975; int y5 = 120; Integer n = 1; Integer id_total = 0; boolean a = true;
    int key = 0;
    float fragmentacionFinal = 0; float total = 0;
    Nodo puntero;
    
    LinkedList<Nodo> original, principio, sobra;
    @FXML
    private Pane lienzo;
    @FXML
    private Button back, enviar;
    @FXML
    private TextField soli;
    private Button p;
    @FXML
    private ComboBox<String> nombres;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeFadeInTransition();
        original  = new LinkedList<Nodo>(); principio = new LinkedList<Nodo>(); sobra = new LinkedList<Nodo>(); 
        iniciar();
    }    
    
    public void iniciar(){
        Random rand = new Random();
        Rectangle rect; int tam;String st;
        Nodo z;
        int ban=0;
        for (int i = 0; i < 8; i++) {
            tam = rand.nextInt(70-20+1)+20;
            z = new Nodo(tam);
            if(rand.nextInt(1-0+1)+0 == 1)
                z.disponible = true;
            else z.disponible = false;
            z.coordX = x;
            rect = new Rectangle();
            rect.setX(x);
            rect.setY(y);
            rect.setWidth(tam * 2);
            rect.setHeight(100);
            rect.setFill(Color.BLACK);
            if(z.disponible == true)
                st = "-fx-fill: #3B89AD; -fx-stroke: white; -fx-stroke-width: 5;";
            else {st = "-fx-fill: #012535; -fx-stroke: white; -fx-stroke-width: 5;";
                z.nombre = "P"+Integer.toString(n);
                n++;
            }
            
            rect.setStyle(st);
            lienzo.getChildren().add(rect);
            x1 += tam / 2 -1;
            Label lab = new Label(z.size.toString());
            lab.setLayoutX(x1);
            lab.setLayoutY(y1);
            lab.setFont(new Font("Times New Roman", 20));
            z.ID = id_total; id_total++;
            total += z.size;
            if(ban==0){z.p=1; ban=1; puntero=z;puntero.p=1;}
            original.add(z);
            x1 = x += tam*2; 
            lienzo.getChildren().add(lab);
        }
        Iterator<Nodo> iter = original.iterator();
        while(iter.hasNext()){
            z = iter.next();
            if(z.disponible == false){
            nombres.getItems().addAll(
          z.nombre      
            );
            }
            Nodo y;
            if(z==puntero){
            principio.add(puntero);
            }else{
             y = new Nodo(z.size, z.ID, z.disponible, z.coordX);
             principio.add(y);
            }
        }
        
    }
    
  
    private void fragmentacion(){
        float suma;  Nodo z; Nodo k;  float prev = 0;
        Iterator<Nodo> iter2 = principio.iterator();
        pie();
        while(iter2.hasNext()){
            z = iter2.next();
            suma = 0; boolean band = false;
            Iterator<Nodo> iter = original.iterator();
            while(iter.hasNext()){
                k = iter.next();
                if(k.size < z.size && Objects.equals(k.ID, z.ID) && k.disponible == true){
                    suma += k.size;
                    band = true; 
                }
                if(suma == z.size)
                    suma = 0;
            }
            if(band == true){
                prev = ((suma * 100)/z.size)/100;
                float t = (z.size * 100)/total;
                fragmentacionFinal += (t*prev);
            }
        }
    }
    
    public void actualizarCombo(){
         Iterator<Nodo> iter = original.iterator(); Nodo z;
        while(iter.hasNext()){
            z = iter.next();
            if(z.disponible == false){
            nombres.getItems().addAll(
          z.nombre);
            }
        }
    }
    
    int pn=0;
    public void checar(int tam){
        int ban=0;
        Iterator<Nodo> iter = original.iterator(); Nodo z; int i=0;
        boolean band = false; 
        while(iter.hasNext()){
         z = iter.next();
         if(z==puntero){
             ban=1; z.p=0;}
         if(ban==1){
            if(tam == z.size && z.disponible == true){
                nombres.getItems().clear(); 
                z.disponible = false;
                z.nombre = "P"+n;n++;
                z.p=1; pn=i;
                puntero=z;
                limpiar();
                dibujar();
                band = true;
                actualizarCombo(); a = true;
                ban=0;
                System.out.println(""+z.p+"--"+z.nombre);
                break; 
            }else if(tam < z.size && z.disponible == true){
                nombres.getItems().clear();
                pn=i;
                corregir(i, tam);
                band = true; a = true;
                actualizarCombo();
                ban=0; break;
            }
         }
            i++;
            a = false;
        }
        if(band == false){
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No hay espacio disponible");
                alert.show();
            sobra((Integer)tam);
        }  
    }
    
    public void dibujar(){
        Random rand = new Random();
        int i=0;
        Rectangle rect; int tam;String st; Nodo z;
        Iterator<Nodo> iter2 = original.iterator();
        while(iter2.hasNext()){
            z = iter2.next();
            rect = new Rectangle();
            rect.setX(z.coordX);
            rect.setY(y2);
            rect.setWidth(z.size * 2);
            rect.setHeight(100);
            rect.setFill(Color.BLACK);
            if(z.disponible == true)
                st = "-fx-fill: #3B89AD; -fx-stroke: white; -fx-stroke-width: 5;";
            else st = "-fx-fill: #012535; -fx-stroke: white; -fx-stroke-width: 5;";
            rect.setStyle(st);
            lienzo.getChildren().add(rect);
           
            Label lab = new Label(z.size.toString());
            lab.setLayoutX(z.coordX + z.size / 2 -1);
            lab.setLayoutY(y3);
            lab.setFont(new Font("Times New Roman", 20));
            
            lienzo.getChildren().add(lab);
        if (z==puntero) {
            // Dibuja una línea desde el punto medio superior del rectángulo
            
            rect = new Rectangle();
            rect.setX(z.coordX+z.size);
            rect.setY(y2-30);
            rect.setWidth(3);
            rect.setHeight(30);
            rect.setFill(Color.BLACK);
            lienzo.getChildren().add(rect);

            // Dibuja un punto en el centro superior del rectángulo
            Circle punto = new Circle(z.coordX + z.size+2, y2 - 26, 4, Color.RED); // Puedes ajustar el color según tus preferencias
            lienzo.getChildren().add(punto);
          }
          i++;  
        }
    }
    
    public void limpiar(){
        Rectangle rect = new Rectangle();
        rect = new Rectangle();
            rect.setX(30);
            rect.setY(420);
            rect.setWidth(900);
            rect.setHeight(140);
            rect.setFill(Color.rgb(191, 210, 217));
            lienzo.getChildren().add(rect);
    }
    
    public void corregir(int indice, int tam){
        if(indice < original.size() - 1 ){
            Nodo z = original.get(indice);
            Nodo w = original.get(indice+1);
            int tamFinal = z.size - tam;
            double cordTotal = w.coordX - z.coordX;

            double porcentaje = (tam*100)/z.size; porcentaje /= 100;
            double l = cordTotal * porcentaje;

            z.disponible = false;
            z.size = tam;
            z.nombre = "P"+n; n++;
            z.p=1;
             puntero=z;
            Nodo d = new Nodo(tamFinal); d.disponible = true;
            d.ID = z.ID;
            d.coordX = l + z.coordX;
            original.add(indice+1, d);
            limpiar();
            dibujar();
        }if(indice == original.size() -1){
            Nodo z = original.get(indice);
            z.p=1;
             puntero=z;
            int tamFinal = z.size - tam;
            double cordTotal = (z.coordX +  z.size * 2)- z.coordX;

            double porcentaje = (tam*100)/z.size; porcentaje /= 100;
            double l = cordTotal * porcentaje;

            z.disponible = false;
            z.size = tam;
            z.nombre = "P"+n; n++;
            Nodo d = new Nodo(tamFinal); d.disponible = true;
            d.ID = z.ID;
            d.coordX = l + z.coordX;
            original.add(indice+1, d);
            limpiar();
            dibujar();
        }
    }

    @FXML
    private void recibirSolicitud(ActionEvent event) {
        if(soli.getText().equals("") || Integer.parseInt(soli.getText())<= 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Ingrese un valor permitido");
                alert.show();
        }else{
        String s = soli.getText();
        key++;
        checar(Integer.parseInt(s));
        }
    }

    @FXML
    private void automatica(ActionEvent event) {
        Random rand = new Random();
        Rectangle rect = new Rectangle();
        int t = rand.nextInt(55-10+1)+10;
        rect.setX(815);
        rect.setY(570);
        rect.setWidth(140 );
        rect.setHeight(60);
        rect.setFill(Color.rgb(191, 210, 217));
        lienzo.getChildren().add(rect);
        Label lab = new Label("Tamaño proceso: "+ t);
        lab.setFont(new Font("Times New Roman", 20));
        lab.setLayoutX(750);
        lab.setLayoutY(600);
        lienzo.getChildren().add(lab);
        checar(t);
        
    }
    
    private void makeFadeInTransition() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(2));
        fadeTransition.setNode(lienzo);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
    
    private void loadNextScene(){
        try {
            Parent secondView;
            secondView = (Pane) FXMLLoader.load(getClass().getResource("FXMLDocumentMenu.fxml"));
            Scene newScene = new Scene(secondView);
            Stage curStage  = (Stage) lienzo.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentControllerInicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Regresar(ActionEvent event) {
        fragmentacion(); 
        
        Rectangle rect = new Rectangle();
        rect = new Rectangle();
            rect.setX(0);
            rect.setY(0);
            rect.setWidth(1500);
            rect.setHeight(1000);
            rect.setFill(Color.rgb(191, 210, 217));
            lienzo.getChildren().add(rect);
        pie();
        Label lab = new Label("Fragmentacion total: "+ fragmentacionFinal+"%");
        lab.setFont(new Font("High Tower Text", 40));
        lab.setLayoutX(250);
        lab.setLayoutY(50);
        lienzo.getChildren().add(lab);
        Button but = new Button("Regresar al menu");
        but.setLayoutX(400);
        but.setLayoutY(600);
        but.setFont(Font.font("High Tower Text"));
       

        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadNextScene();
            }
        });
        lienzo.getChildren().add(but);
    }
    
    private void sobra(Integer tam){
       Rectangle rect = new Rectangle();
       Nodo k = new Nodo(tam, n, true, 0, "P"+n); n++; 
       k.p=0;
       if(y5 >= 600){
           //No hacer nada
       }else{
            rect.setX(x5);
            rect.setY(y5);
            rect.setWidth(140);
            rect.setHeight(tam);
            rect.setFill(Color.RED);
            rect.setStyle("-fx-fill: RED; -fx-stroke: white; -fx-stroke-width: 5;");
            lienzo.getChildren().add(rect);
            Label lab = new Label(tam.toString());
            lab.setLayoutX(x5-30);
            lab.setLayoutY(y5+tam/2-2);
            lab.setFont(new Font("Times New Roman", 20));
            y5 += tam; 
            sobra.add(k);
            lienzo.getChildren().add(lab);}
    }
    
    private void pie(){
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Memoria fragmentada", fragmentacionFinal),
                new PieChart.Data("Memoria restante", total-fragmentacionFinal));
        
        final PieChart chart = new PieChart(pieChartData);
        chart.setLayoutX(200);
        chart.setLayoutY(150);
        chart.setTitle("Representacion visual de la fragmentacion");
    lienzo.getChildren().add(chart);
}

  public  void ante(){
       Iterator<Nodo> iter = original.iterator(); Nodo z;
        while(iter.hasNext()){
            z = iter.next();
            if(z.disponible==false&&z!=puntero){
                z.p=1;
               puntero=z;
               break;
            }
        }
        limpiar();
        dibujar();
  
  }@FXML
    private void Liberar(ActionEvent event) {
        String p = nombres.getValue();
        Iterator<Nodo> iter = original.iterator(); Nodo z;
        while(iter.hasNext()){
            z = iter.next();
            if(z.nombre == p&&z==puntero){
                z.disponible = true;
                  z.p=0;
                  ante();
                  break;
            }
            if(z.nombre == p){
                z.disponible = true;
                break;
            }
        }
        nombres.getItems().clear();
        actualizarCombo();
        limpiar();
        dibujar();
    }
      
public void limpiar2(){
        Rectangle rect = new Rectangle();
        rect = new Rectangle();
            rect.setX(940);
            rect.setY(115);
            rect.setWidth(500);
            rect.setHeight(520);
            rect.setFill(Color.rgb(191, 210, 217));
            lienzo.getChildren().add(rect);
    }
        public void dibujar2(){
        Random rand = new Random();
        Rectangle rect; int tam;String st; Nodo z;
        Iterator<Nodo> iter2 = sobra.iterator(); int temp = 120;
        while(iter2.hasNext()){
            z = iter2.next();
            rect = new Rectangle();
            rect.setX(975);
            rect.setY(temp);
            rect.setWidth(140);
            rect.setHeight(z.size);
            rect.setFill(Color.BLACK);
            rect.setStyle("-fx-fill: RED; -fx-stroke: white; -fx-stroke-width: 5;");
            lienzo.getChildren().add(rect);
           
            Label lab = new Label(z.size.toString());
            lab.setLayoutX(945);
            lab.setLayoutY(temp+z.size/2-2);
            lab.setFont(new Font("Times New Roman", 20));
            temp += z.size;
            lienzo.getChildren().add(lab);
        }

    }
    @FXML
    private void Compactar(ActionEvent event) {
    Iterator<Nodo> iter2 = original.iterator(); Nodo z,k; Integer tam = 0;principio.clear(); 
           y2 = 450; y3 = 430; double x9 = 30; int h = 0; n = 1;
           while(iter2.hasNext()){
               z = iter2.next();
                if(z.disponible == true && iter2.hasNext() == true)
                        tam += z.size;
                    else{
                        if(iter2.hasNext() == false && tam > 0 && z.disponible == true&&puntero!=z){
                            tam += z.size;
                            k = new Nodo(tam, id_total, true, x9); id_total++;
                            principio.add(k);
                        }else{
                            k = new Nodo(tam, id_total, true, x9); id_total++;
                            principio.add(k);
                            x9 += tam * 2;
                            tam = 0;
                            k = new Nodo(z.size,id_total, z.disponible, x9, "P"+n); id_total++; n++;
                            x9 += z.size * 2;
                            if(z==puntero){
                                principio.add(puntero);
                            }else   principio.add(k);
                        }
                    }
               h++;
           }
           Iterator<Nodo> iter = principio.iterator(); original.clear();
           while(iter.hasNext()){
               z = iter.next();
               if(z==puntero){
                 original.add(puntero);
               }else original.add(new Nodo(z.size, z.ID,z.disponible,z.coordX,z.nombre));
           }
        limpiar();
        dibujar();
    }

    @FXML
    private void verificar(ActionEvent event) {
        Iterator<Nodo> iter2 = sobra.iterator(); Nodo w; int i = 0;
        if(sobra.isEmpty()){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No hay procesos en espera que se puedan asignar");
                alert.show();
        }else{
                while(iter2.hasNext()){
                    w = iter2.next();
                    checar(w.size);
                    if(a == true)
                        sobra.remove(w); break;
                }
            limpiar2();
            dibujar2();
        }
    }
}
