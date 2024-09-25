/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectooperativos;

/**
 *
 * @author 52444
 */
public class Nodo {
    Integer size;
    Integer ID;
    boolean disponible;
    double coordX;
    String nombre; 
    int p;
    
    public Nodo(Integer k){
        size = k;
        nombre = "";
    }
    
    public Nodo(Integer k, Integer y, boolean x, double z){
        size = k; ID = y; disponible = x; coordX = z;      nombre = "";
    }
    
    public Nodo(Integer k, Integer y, boolean x, double z, String nombre){
        size = k; ID = y; disponible = x; coordX = z; this.nombre = nombre;      
    }
}
