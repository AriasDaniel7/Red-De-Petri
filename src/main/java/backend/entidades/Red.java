package backend.entidades;

import java.util.ArrayList;

/**
 *
 * @author Daniel Arias
 */
public class Red {

    private ArrayList<Lugar> lugares;
    private ArrayList<Transicion> transiciones;
    private ArrayList<Arco> arcos;

    public ArrayList<Lugar> getLugares() {
        return lugares;
    }

    public void setLugares(ArrayList<Lugar> lugares) {
        this.lugares = lugares;
    }

    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<Transicion> transiciones) {
        this.transiciones = transiciones;
    }

    public ArrayList<Arco> getArcos() {
        return arcos;
    }

    public void setArcos(ArrayList<Arco> arcos) {
        this.arcos = arcos;
    }

    public ArrayList<Lugar> getLugaresClone() {
        ArrayList<Lugar> lugaresClone = new ArrayList<>();
        for (Lugar lugar : lugares) {
            lugaresClone.add(lugar.clone());
        }
        return lugaresClone;
    }
}
