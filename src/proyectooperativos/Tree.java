/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectooperativos;

import java.util.ArrayList;

/**
 *
 * @author madie
 */
public class Tree{
    NodoTree root;
    boolean a = true, b = true;
    ArrayList<NodoTree> cola = new ArrayList<>();
    String registro = new String();
    
    public Tree(){
        this.root = new NodoTree(0, 1024);
        this.root.frag = 100;
    }
    
    public void imprimirTree(NodoTree nodo){
        if(nodo != null){
            imprimirTree(nodo.left);
            System.out.println(nodo.tamanio + " -- Nombre: " + nodo.name + " -- Key: " + nodo.key);
            imprimirTree(nodo.right);
        }
    }
    
    public void insertarF(NodoTree padre, NodoTree nuevo, int opc){
        if(opc == 1){
            padre.left = nuevo;
            nuevo.padre = padre;
        }else{
            padre.right = nuevo;
            nuevo.padre = padre;
        }
        
    }
    
    public NodoTree buscaCola(String name){
        NodoTree x = null;
        for (int i = 0; i < cola.size(); i++) {
            if(cola.get(i).name.equals(name)){
                x = cola.get(i);
                cola.remove(i);
                break;
            }
        }
        return x;
    }
    
    public int liberar(NodoTree elim){
        int cont = 0;
        NodoTree nodo = elim.padre;
        elim.name = null;
        elim.key = 0;
        registro += "Se elimina" + elim.name;
        while(nodo != null){
            if(nodo.left.left == null && nodo.right.left == null){
                if(nodo.left.name==null && nodo.right.name==null){
                    System.out.println("se pueden juntar");
                    cont++;
                    nodo.left = null;
                    nodo.right = null;
                    nodo = nodo.padre;
                }else{ 
                    System.out.println("No estan juntos");
                    break;
                }
            }else{
                System.out.println("No estan juntos");
                break;
            }
        }
        registro += " la partición se fusiona " + cont + " veces\n";
        return cont;
    }
    
    public float obtenFrag(){
        float total = 0;
        for (int i = 0; i < cola.size(); i++) {
            System.out.println("total: " + total + "cola" + cola.get(i).frag);
            total =  total + cola.get(i).frag;
        }
        return total;
    }
    
    public int particiona(Tree arbol, NodoTree x, NodoTree z, int cont){
        if((x.tamanio >= z.tamanio) && ((x.tamanio/2) < z.tamanio)){
            if(x.name == null){
                x.name = z.name;
                x.key = z.tamanio;
                if(x.key != x.tamanio){
                    
                    x.frag = (100-((x.key*100)/x.tamanio));
                }else{
                    x.frag = 0;
                }
                a = true;
                b = true;
                cola.add(x);
                registro += x.name + " guardada en memoria " + x.tamanio + " \n";
                return cont;
            }else{
                return -1;
            }
        }else{
            if(x.left == null){
                arbol.insertarF(x, new NodoTree(0, x.tamanio/2), 1);
                x.left.frag = x.frag/2;
                arbol.insertarF(x, new NodoTree(0, x.tamanio/2), 0);
                x.right.frag = x.frag/2;
                registro += "Memoria particionada a " + x.tamanio/2 + " \n";
                cont++;
            }
            if(((x.left.left == null) || ((x.left.tamanio/2) >= z.tamanio)) && (x.left.name == null)){
                return particiona(arbol, x.left, z, cont);
            }else if(((x.right.left == null) || ((x.right.tamanio/2) >= z.tamanio)) && (x.right.name == null)){
                return particiona(arbol, x.right, z, cont);
            }else{
                if(b == true){
                    b = false;
                    if(x.padre.right.name == null){
                        return particiona(arbol, x.padre.right, z, cont);
                    }else{
                        return particiona(arbol, x.padre.padre.right, z, cont);
                    }
                }else{
                    if(a == true){
                        a = false;
                        return particiona(arbol, arbol.root.right,z, cont);
                    }else{
                        return -1;
                    }
                }
            }
        }
    }
    
}
