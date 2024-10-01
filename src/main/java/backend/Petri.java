package backend;

import backend.entidades.Arco;
import backend.entidades.Lugar;
import backend.entidades.Red;
import backend.entidades.Transicion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel Arias
 */
public class Petri {

    private Map<String, Integer> transicionIndex;
    private Red red;
    private int[][] dMas;
    private int[][] dMenos;
    private int[] marcacionInicial;
    private ArrayList<Transicion> transicionesHabilitadas;
    private String ultimaTransicionDisparada;

    public Petri(Red red) {
        this.red = red;
        this.transicionIndex = new HashMap<>();

        ArrayList<Transicion> transiciones = red.getTransiciones();
        for (int i = 0; i < transiciones.size(); i++) {
            this.transicionIndex.put(transiciones.get(i).getId(), i);
        }
        cargar_matrices();
        calcularTransicionesHabilitadas();
    }

    public void cargar_matrices() {
        // System.out.println();
        // for (Lugar lugare : this.red.getLugares()) {
        // System.out.println(lugare);
        // }

        ArrayList<Lugar> lugares = this.red.getLugares();
        ArrayList<Transicion> transiciones = this.red.getTransiciones();
        ArrayList<Arco> arcos = this.red.getArcos();

        int numLugares = lugares.size();
        int numTransiciones = transiciones.size();

        this.dMas = new int[numTransiciones][numLugares];
        this.dMenos = new int[numTransiciones][numLugares];
        this.marcacionInicial = new int[numLugares];

        Map<String, Integer> lugarIndex = new HashMap<>();
        Map<String, Integer> transicionIndex = new HashMap<>();

        for (int i = 0; i < numLugares; i++) {
            lugarIndex.put(lugares.get(i).getId(), i);
            this.marcacionInicial[i] = lugares.get(i).getMarcas();
        }

        for (int i = 0; i < numTransiciones; i++) {
            transicionIndex.put(transiciones.get(i).getId(), i);
        }

        for (Arco arco : arcos) {
            String desde = arco.getDesde();
            String hacia = arco.getHacia();
            int peso = arco.getPeso();

            if (lugarIndex.containsKey(desde) && transicionIndex.containsKey(hacia)) {
                int lugarIdx = lugarIndex.get(desde);
                int transicionIdx = transicionIndex.get(hacia);
                this.dMenos[transicionIdx][lugarIdx] = peso;
            } else if (transicionIndex.containsKey(desde) && lugarIndex.containsKey(hacia)) {
                int transicionIdx = transicionIndex.get(desde);
                int lugarIdx = lugarIndex.get(hacia);
                this.dMas[transicionIdx][lugarIdx] = peso;
            }
        }

        // // Imprimir matrices para verificar
        // System.out.println("Matriz D+ (Post-Incidencia):");
        // imprimirMatriz(dMas);
        // System.out.println("Matriz D- (Pre-Incidencia):");
        // imprimirMatriz(dMenos);
        // System.out.println("Marcacion Inicial:");
        // imprimirVector(marcacionInicial);

    }

    private void calcularTransicionesHabilitadas() {
        this.transicionesHabilitadas = (ArrayList<Transicion>) this.red.getTransiciones().stream()
                .filter(t -> {
                    int index = transicionIndex.get(t.getId());
                    for (int j = 0; j < dMenos[index].length; j++) {
                        if (dMenos[index][j] > marcacionInicial[j])
                            return false;
                    }
                    return true;
                }).collect(Collectors.toList());
    }

    public Petri dispararTransicion(String transicionId) {
        int transicionIdx = transicionIndex.get(transicionId);

        int[] nuevaMarcacion = this.marcacionInicial.clone();
        ArrayList<Lugar> lugares = this.red.getLugares();
        // imprimirVector(nuevaMarcacion);

        for (int j = 0; j < this.dMenos[transicionIdx].length; j++) {
            nuevaMarcacion[j] -= this.dMenos[transicionIdx][j];
            nuevaMarcacion[j] += this.dMas[transicionIdx][j];

            Lugar lugar = lugares.get(j);
            lugar.adjustTokens(this.dMas[transicionIdx][j], this.dMenos[transicionIdx][j]);
        }

        // imprimirVector(nuevaMarcacion);

        Petri nuevaPetri = new Petri(this.red);
        nuevaPetri.marcacionInicial = nuevaMarcacion;
        nuevaPetri.ultimaTransicionDisparada = transicionId;

        return nuevaPetri;
    }

    public String getUltimaTransicionDisparada() {
        return this.ultimaTransicionDisparada;
    }

    public DefaultTableModel getTableModeldMas() {
        return getTableModel(dMas, "D+");
    }

    public DefaultTableModel getTableModeldMenos() {
        return getTableModel(dMenos, "D-");
    }

    public DefaultTableModel getTableModelMarcacionInicial() {
        return obtenerDefaultTableModelVector(marcacionInicial, "Marcas");
    }

    public DefaultTableModel getTableModelTransicionesHabilitadas() {
        calcularTransicionesHabilitadas();
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Transiciones Habilitadas");
        for (Transicion transicion : this.transicionesHabilitadas) {
            model.addRow(new Object[] { transicion.getId() });
        }

        return model;
    }

    private DefaultTableModel getTableModel(int[][] matriz, String nombre) {
        ArrayList<Lugar> lugares = this.red.getLugares();
        ArrayList<Transicion> transiciones = this.red.getTransiciones();

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn(nombre);
        for (Lugar lugar : lugares) {
            model.addColumn(lugar.getId());
        }

        for (int i = 0; i < matriz.length; i++) {
            Object[] fila = new Object[matriz[i].length + 1];
            fila[0] = transiciones.get(i).getId();
            for (int j = 0; j < matriz[i].length; j++) {
                fila[j + 1] = matriz[i][j];
            }
            model.addRow(fila);
        }

        return model;
    }

    private DefaultTableModel obtenerDefaultTableModelVector(int[] vector, String nombre) {
        ArrayList<Lugar> lugares = this.red.getLugares();
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("");
        for (Lugar lugar : lugares) {
            model.addColumn(lugar.getId());
        }

        Object[] fila = new Object[vector.length + 1];
        fila[0] = nombre;
        for (int i = 0; i < vector.length; i++) {
            fila[i + 1] = vector[i];
        }
        model.addRow(fila);

        return model;
    }

    public void imprimirVector(int[] vector) {
        ArrayList<Lugar> lugares = this.red.getLugares();

        System.out.print("\t");
        for (Lugar lugar : lugares) {
            System.out.print(lugar.getId() + "\t");
        }
        System.out.println();

        System.out.print("Marcas\t");
        for (int valor : vector) {
            System.out.print(valor + "\t");
        }
        System.out.println();
    }

    public void imprimirMatriz(int[][] matriz) {
        ArrayList<Lugar> lugares = this.red.getLugares();
        ArrayList<Transicion> transiciones = this.red.getTransiciones();

        System.out.print("\t");
        for (Lugar lugar : lugares) {
            System.out.print(lugar.getId() + "\t");
        }
        System.out.println();

        for (int i = 0; i < matriz.length; i++) {
            System.out.print(transiciones.get(i).getId() + "\t");
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + "\t");

            }
            System.out.println();
        }
    }

    public ArrayList<Transicion> getTransicionesHabilitadas() {
        return transicionesHabilitadas;
    }

    public void setTransicionesHabilitadas(ArrayList<Transicion> transicionesHabilitadas) {
        this.transicionesHabilitadas = transicionesHabilitadas;
    }

    public int[][] getdMas() {
        return dMas;
    }

    public void setdMas(int[][] dMas) {
        this.dMas = dMas;
    }

    public int[][] getdMenos() {
        return dMenos;
    }

    public void setdMenos(int[][] dMenos) {
        this.dMenos = dMenos;
    }

    public int[] getMarcacionInicial() {
        return marcacionInicial;
    }

    public void setMarcacionInicial(int[] marcacionInicial) {
        this.marcacionInicial = marcacionInicial;
    }

    public Red getRed() {
        return red;
    }

    public void setRed(Red red) {
        this.red = red;
    }

}
