package backend;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;

import backend.entidades.Lugar;
import backend.entidades.Transicion;
import backend.entidades.Token;

import net.miginfocom.swing.MigLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Daniel Arias
 */
public class GraficaPetri {

    private ListenableGraph<String, DefaultEdge> grafo;
    private JGraphXAdapter<String, DefaultEdge> graphAdapter;
    private mxGraphComponent graphComponent;
    private Petri petri;

    public GraficaPetri(Petri petri) {
        this.grafo = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));
        this.graphAdapter = new JGraphXAdapter<>(grafo);
        this.graphAdapter.setHtmlLabels(true); // Habilitar etiquetas HTML
        this.graphComponent = new mxGraphComponent(graphAdapter);
        this.petri = petri;
        crearGrafo();
    }

    private void crearGrafo() {

        Object parent = graphAdapter.getDefaultParent();

        graphAdapter.getModel().beginUpdate();
        try {

            ArrayList<Lugar> lugares = petri.getRed().getLugares();
            ArrayList<Transicion> transiciones = petri.getRed().getTransiciones();
            // int[] marcacion = petri.getMarcacionInicial();
            int[][] dMas = petri.getdMas();

            Map<String, Object> vertexMap = new HashMap<>();

            for (int i = 0; i < lugares.size(); i++) {
                Lugar lugar = lugares.get(i);

                // String etiqueta = lugar.getId() + " (" + marcacion[i] + ")";
                // Object vertex = graphAdapter.insertVertex(parent, null, etiqueta, 50, 50,
                // 100, 100,
                // "shape=ellipse;fillColor=#0094FF;strokeColor=#000000;fontColor=#000000;fontSize=14;fontStyle=1;align=center;verticalAlign=middle;");
                // vertexMap.put(lugar.getId(), vertex);

                StringBuilder etiqueta = new StringBuilder();

                etiqueta.append("<html><center>").append(lugar.getId()).append("<br>")
                        .append("<div style='display: flex; flex-wrap: wrap;'>");

                for (Token token : lugar.getTokens()) {
                    etiqueta.append("<span style='color:").append(token.getColor())
                            .append("; font-size: 25px; font-weight: bold;'>")
                            .append("●")
                            .append("</span>");
                }

                etiqueta.append("</div></center></html>");

                Object vertex = graphAdapter.insertVertex(parent, null, etiqueta, 50, 50,
                        100, 100,
                        "shape=ellipse;fillColor=#FFFFFF;strokeColor=#000000;fontColor=#000000;fontSize=14;fontStyle=1;align=center;verticalAlign=middle;");
                vertexMap.put(lugar.getId(), vertex);
            }

            for (Transicion transicion : transiciones) {
                Object vertex = graphAdapter.insertVertex(parent, null, transicion.getId(), 50, 50, 30, 60,
                        "shape=rectangle;fillColor=#FFFFFF;strokeColor=#000000;fontColor=#000000;fontSize=14;fontStyle=1;align=center;verticalAlign=middle;");
                vertexMap.put(transicion.getId(), vertex);
            }

            for (int i = 0; i < dMas.length; i++) {
                for (int j = 0; j < dMas[i].length; j++) {
                    if (dMas[i][j] > 0) {
                        Object source = vertexMap.get(transiciones.get(i).getId());
                        Object target = vertexMap.get(lugares.get(j).getId());
                        String label = String.valueOf(dMas[i][j]);
                        graphAdapter.insertEdge(parent, null, label, source, target);
                    }
                }
            }

            int[][] dMenos = petri.getdMenos();
            for (int i = 0; i < dMenos.length; i++) {
                for (int j = 0; j < dMenos[i].length; j++) {
                    if (dMenos[i][j] > 0) {
                        Object source = vertexMap.get(lugares.get(j).getId());
                        Object target = vertexMap.get(transiciones.get(i).getId());
                        String label = String.valueOf(dMenos[i][j]);
                        graphAdapter.insertEdge(parent, null, label, source, target);
                    }
                }
            }
        } finally {
            graphAdapter.getModel().endUpdate();
        }

        // Cambiar el color de la transición disparada
        String ultimaTransicionDisparada = petri.getUltimaTransicionDisparada();
        if (ultimaTransicionDisparada != null) {
            Object[] cells = graphAdapter.getChildVertices(parent);
            for (Object cell : cells) {
                if (graphAdapter.getModel().getValue(cell).equals(ultimaTransicionDisparada)) {
                    graphAdapter.setCellStyle(mxConstants.STYLE_FILLCOLOR + "=#FF0000;"
                            + mxConstants.STYLE_FONTCOLOR + "=#FFFFFF;"
                            + mxConstants.STYLE_FONTSTYLE + "=" + mxConstants.FONT_BOLD,
                            new Object[] { cell }); // Cambiar color a rojo, letra blanca y negrita
                    break;
                }
            }
        }
    }

    public void crearPanel(JPanel panel) {
        // Estilo para las aristas
        Map<String, Object> edgeStyle = graphAdapter.getStylesheet().getDefaultEdgeStyle();
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000"); // Color de la arista
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC); // Estilo de la flecha
        edgeStyle.put(mxConstants.STYLE_FONTCOLOR, "#000000"); // Color de la fuente
        edgeStyle.put(mxConstants.STYLE_FONTSIZE, 12); // Tamaño de fuente más grande
        edgeStyle.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_TOP); // Alineación de la etiqueta
        edgeStyle.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_TOP); // Alineación vertical de la etiqueta
        edgeStyle.put(mxConstants.STYLE_STROKEWIDTH, 2); // Grosor de la arista
        edgeStyle.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_TOP); // Posición de la etiqueta
        edgeStyle.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_TOP); // Posición vertical de la
        // etiqueta
        edgeStyle.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#FFFFFF"); // Color de fondo de la etiqueta

        // Usar mxHierarchicalLayout para un diseño jerárquico
        mxHierarchicalLayout layout = new mxHierarchicalLayout(graphAdapter, SwingConstants.WEST);
        layout.setIntraCellSpacing(80); // Espacio entre nodos en el mismo nivel
        layout.setInterRankCellSpacing(80); // Espacio entre nodos en diferentes niveles
        layout.setDisableEdgeStyle(false); // Mantiene los estilos de las aristas

        layout.execute(graphAdapter.getDefaultParent());

        // Forzar la actualización del gráfico
        graphAdapter.refresh();

        panel.setLayout(new MigLayout("fill")); // Centrar el gráfico en el panel
        panel.add(graphComponent, "width 100%, height 100%");
        panel.repaint();
        panel.revalidate();
    }
}
