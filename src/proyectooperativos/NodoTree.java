
package proyectooperativos;

/**
 *
 * @author madie
 */
public class NodoTree{
    NodoTree padre;
    NodoTree left;
    NodoTree right;
    int key, tamanio;
    String name;
    float frag;
    
    public NodoTree(int key, int tamanio) {
        this.key = key;
        this.name = null;
        this.tamanio = tamanio;
    }
}
