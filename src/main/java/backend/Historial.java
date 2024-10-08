package backend;

import backend.entidades.Lugar;
import java.util.ArrayList;

/**
 *
 * @author Daniel Arias
 */
public class Historial {

    private ArrayList<ArrayList<Lugar>> lugares;
    private ArrayList<String> transiciones;

    public Historial() {
        this.lugares = new ArrayList<>();
        this.transiciones = new ArrayList<>();
    }

    public void add(ArrayList<Lugar> lugares, String transicion) {
        this.lugares.add(lugares);
        this.transiciones.add(transicion);
    }

    public ArrayList<ArrayList<Lugar>> getLugares() {
        return lugares;
    }

    public void setLugares(ArrayList<ArrayList<Lugar>> lugares) {
        this.lugares = lugares;
    }

    public void clear() {
        this.lugares.clear();
    }

    public void remove(int index) {
        this.lugares.remove(index);
    }

    public void removeAll() {
        this.lugares.clear();
    }

    public ArrayList<Lugar> get(int index) {
        return this.lugares.get(index);
    }

    public String getTransicion(int index) {
        return this.transiciones.get(index);
    }

    public int size() {
        return this.lugares.size();
    }
}
