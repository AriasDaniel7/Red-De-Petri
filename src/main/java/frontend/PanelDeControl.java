package frontend;

import com.formdev.flatlaf.themes.FlatMacLightLaf;

import backend.CargarJSON;
import backend.GraficaPetri;
import backend.Historial;
import backend.Petri;
import backend.entidades.Lugar;
import backend.entidades.Token;

import java.awt.Font;
import net.miginfocom.swing.MigLayout;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Daniel Arias
 */
public class PanelDeControl extends javax.swing.JFrame {

        private Petri petri1;
        private Petri petri2;
        private GraficaPetri graficaPetri;
        private boolean estado;
        private Historial historial;

        public PanelDeControl() {
                initComponents();
                init();
        }

        private void init() {
                this.estado = false;
                this.historial = new Historial();
                setTitle("Red De Petri");
                setLocationRelativeTo(null);
                initPanel();
        }

        private void initPanel() {
                panelPrincipal.setLayout(new MigLayout("fill"));
                panelPrincipal.add(jPanel1, "width 75%, height 100%");

                panelPrincipal.add(jPanel2, "width 25%, height 100%");

                jPanel2.setLayout(new MigLayout("", "0[center]0", "0[center]push[center]push[center]0"));
                jPanel2.add(jPanel3, "width 100%, wrap");
                jPanel2.add(jPanel4, "width 100%, height 100%, wrap");
                jPanel2.add(jPanel5, "width 100%");

                jPanel4.setLayout(new MigLayout("wrap 1", "[grow]", "[][grow]"));
                jPanel4.add(jLabel1, "wrap");
                jPanel4.add(dMasScrollPane, "grow");
                jPanel4.add(dMenosScrollPane, "grow");
                jPanel4.add(marcacionInicialScrollPane, "grow");
                jPanel4.add(tokensScrollPane, "grow");

                jPanel5.setLayout(new MigLayout("fill", "[center]", "[center]10[center]"));
                jPanel5.add(jScrollPane4, "wrap, span");
                jPanel5.add(jComboBox1, "width 100%");
                jPanel5.add(btnDisparar, "");
                jPanel5.add(btnHistorial, "");
                jPanel5.add(btnNormal, "");
        }

        private void cargarTablas(Petri petri) {

                this.dMasTable.setModel(petri.getTableModeldMas());
                this.dMenosTable.setModel(petri.getTableModeldMenos());
                this.MarcacionInicialTable.setModel(petri.getTableModelMarcacionInicial());
                this.transicionHabilitadaTable.setModel(petri.getTableModelTransicionesHabilitadas());
                this.tokensTable.setModel(petri.obtenerDefaultTableModelTokens());

                this.dMasTable.setDefaultRenderer(Object.class, getCentradoRenderer());
                this.dMasTable.setTableHeader(getHeaderNegrilla(dMasTable));
                this.dMenosTable.setDefaultRenderer(Object.class, getCentradoRenderer());
                this.dMenosTable.setTableHeader(getHeaderNegrilla(dMenosTable));
                this.MarcacionInicialTable.setDefaultRenderer(Object.class, getCentradoRenderer());
                this.MarcacionInicialTable.setTableHeader(getHeaderNegrilla(MarcacionInicialTable));
                this.transicionHabilitadaTable.setTableHeader(getHeaderNegrilla(transicionHabilitadaTable));
                this.tokensTable.setTableHeader(getHeaderNegrilla(tokensTable));

                int[] columnas = { 0 };
                centrarColumnas(tokensTable, columnas);

                this.graficaPetri = new GraficaPetri(petri);
                this.graficaPetri.crearPanel(jPanel1);
                jPanel1.revalidate();
                jPanel1.repaint();

                this.jComboBox1.removeAllItems();
                for (int i = 0; i < petri.getTransicionesHabilitadas().size(); i++) {
                        this.jComboBox1.addItem(petri.getTransicionesHabilitadas().get(i).getId());
                }
        }

        private DefaultTableCellRenderer getCentradoRenderer() {
                DefaultTableCellRenderer centradoRenderer = new DefaultTableCellRenderer();
                centradoRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                return centradoRenderer;
        }

        private void centrarColumnas(JTable table, int[] columnas) {
                DefaultTableCellRenderer centradoRenderer = getCentradoRenderer();
                for (int columna : columnas) {
                        table.getColumnModel().getColumn(columna).setCellRenderer(centradoRenderer);
                }
        }

        private JTableHeader getHeaderNegrilla(JTable tabla) {
                JTableHeader header = tabla.getTableHeader();
                header.setFont(header.getFont().deriveFont(Font.BOLD));
                return header;
        }

        private DefaultTableModel obtenerDefaultTableModelHistorial() {
                DefaultTableModel model = new DefaultTableModel();

                model.addColumn("Lugar");
                model.addColumn("Tokens");

                for (int i = 0; i < this.historial.size(); i++) {
                        ArrayList<Lugar> lugares = this.historial.get(i);
                        model.addRow(new Object[] { "Transición disparada: ", this.historial.getTransicion(i) });

                        for (Lugar lugar : lugares) {
                                Object[] fila = new Object[2];
                                fila[0] = lugar.getId();
                                StringBuilder tokensString = new StringBuilder();
                                for (Token token : lugar.getTokens()) {
                                        String color = token.getColor();
                                        String tokenId = token.getId();
                                        tokensString.append("<span style='color:").append(color)
                                                        .append("; font-size: 15px;'>●</span>")
                                                        .append("<span style='color:black;'>(")
                                                        .append(tokenId)
                                                        .append(")</span>").append(" ");
                                }
                                fila[1] = "<html>" + tokensString.toString() + "</html>";
                                model.addRow(fila);
                        }
                        model.addRow(new Object[] { "", "" });
                }
                return model;
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                panelPrincipal = new javax.swing.JPanel();
                jPanel1 = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                jPanel3 = new javax.swing.JPanel();
                jTextField1 = new javax.swing.JTextField();
                btnImportar = new javax.swing.JButton();
                jPanel4 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                dMasScrollPane = new javax.swing.JScrollPane();
                dMasTable = new javax.swing.JTable();
                dMenosScrollPane = new javax.swing.JScrollPane();
                dMenosTable = new javax.swing.JTable();
                marcacionInicialScrollPane = new javax.swing.JScrollPane();
                MarcacionInicialTable = new javax.swing.JTable();
                tokensScrollPane = new javax.swing.JScrollPane();
                tokensTable = new javax.swing.JTable();
                jPanel5 = new javax.swing.JPanel();
                jScrollPane4 = new javax.swing.JScrollPane();
                transicionHabilitadaTable = new javax.swing.JTable();
                jComboBox1 = new javax.swing.JComboBox<>();
                btnDisparar = new javax.swing.JButton();
                btnNormal = new javax.swing.JButton();
                btnHistorial = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 1004, Short.MAX_VALUE));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 0, Short.MAX_VALUE));

                jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                jTextField1.setEditable(false);
                jTextField1.setDisabledTextColor(new java.awt.Color(255, 255, 255));

                btnImportar.setText("Importar JSON");
                btnImportar.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnImportarActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(btnImportar)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jTextField1)
                                                                .addContainerGap()));
                jPanel3Layout.setVerticalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel3Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(btnImportar)
                                                                                .addComponent(jTextField1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                jLabel1.setText("Matrices:");

                dMasTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {

                                }));
                dMasScrollPane.setViewportView(dMasTable);

                dMenosTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {

                                }));
                dMenosScrollPane.setViewportView(dMenosTable);

                MarcacionInicialTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {

                                }));
                marcacionInicialScrollPane.setViewportView(MarcacionInicialTable);

                tokensTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {

                                }));
                tokensScrollPane.setViewportView(tokensTable);

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(dMenosScrollPane,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                572, Short.MAX_VALUE)
                                                                                .addComponent(dMasScrollPane)
                                                                                .addGroup(jPanel4Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jLabel1)
                                                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                                                .addComponent(marcacionInicialScrollPane)
                                                                                .addComponent(tokensScrollPane))
                                                                .addContainerGap()));
                jPanel4Layout.setVerticalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel1)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(dMasScrollPane,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                89,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(dMenosScrollPane,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                100,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(marcacionInicialScrollPane,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                59,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(tokensScrollPane,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                58,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));

                jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                transicionHabilitadaTable.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {

                                },
                                new String[] {

                                }));
                jScrollPane4.setViewportView(transicionHabilitadaTable);

                btnDisparar.setText("Disparar");
                btnDisparar.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnDispararActionPerformed(evt);
                        }
                });

                btnNormal.setText("Red Inicial");
                btnNormal.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnNormalActionPerformed(evt);
                        }
                });

                btnHistorial.setText("Historial");
                btnHistorial.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnHistorialActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                jPanel5.setLayout(jPanel5Layout);
                jPanel5Layout.setHorizontalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel5Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jScrollPane4,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                572, Short.MAX_VALUE)
                                                                                .addGroup(jPanel5Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jComboBox1,
                                                                                                                0,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(btnDisparar)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(btnHistorial)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(btnNormal)))
                                                                .addContainerGap()));
                jPanel5Layout.setVerticalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jScrollPane4,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                260, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel5Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jComboBox1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(btnDisparar)
                                                                                .addComponent(btnNormal)
                                                                                .addComponent(btnHistorial))
                                                                .addContainerGap()));

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel3,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel4,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel5,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap()));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jPanel3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel4,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel5,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));

                javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
                panelPrincipal.setLayout(panelPrincipalLayout);
                panelPrincipalLayout.setHorizontalGroup(
                                panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelPrincipalLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap()));
                panelPrincipalLayout.setVerticalGroup(
                                panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(panelPrincipalLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(panelPrincipalLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel2,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel1,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap()));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(panelPrincipal,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(panelPrincipal,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap()));

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHistorialActionPerformed
                if (this.petri1 != null) {
                        if (this.historial.size() > 0) {
                                JDialog historialDialog = new JDialog(this, "Historial", true);
                                historialDialog.setSize(800, 500);
                                historialDialog.setLocationRelativeTo(this);

                                // Crear una tabla para mostrar el historial
                                JTable historialTable = new JTable(obtenerDefaultTableModelHistorial());
                                int[] columnas = { 0 };
                                centrarColumnas(historialTable, columnas);
                                historialTable.setTableHeader(getHeaderNegrilla(historialTable));
                                historialTable.setFont(new Font("Segoe UI", 0, 14));

                                // Envolver la tabla en un JScrollPane
                                JScrollPane scrollPane = new JScrollPane(historialTable,
                                                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                                // Añadir el JScrollPane al JDialog
                                historialDialog.add(scrollPane);
                                historialDialog.setVisible(true);
                        }
                } else {
                        JOptionPane.showMessageDialog(this, "No hay una red de Petri cargada.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }// GEN-LAST:event_btnHistorialActionPerformed

        private void btnNormalActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNormalActionPerformed
                if (this.petri1 != null) {
                        File file = new File(jTextField1.getText());
                        jTextField1.setText(file.getAbsolutePath());
                        CargarJSON cargarJSON = new CargarJSON(file);
                        this.petri1 = new Petri(cargarJSON.cargar());
                        this.estado = true;
                        limpiarComponentes();
                        cargarTablas(this.petri1);
                        this.historial = new Historial();
                        this.historial.add(this.petri1.getRed().getLugaresClone(), "Inicial");
                } else {
                        JOptionPane.showMessageDialog(this, "No hay una red de Petri cargada.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }// GEN-LAST:event_btnNormalActionPerformed

        private void btnDispararActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDispararActionPerformed
                if (this.petri1 != null) {
                        String transicion = (String) jComboBox1.getSelectedItem();
                        if (transicion != null && !transicion.isEmpty()) {
                                if (this.estado) {
                                        this.petri2 = this.petri1.dispararTransicion(transicion);
                                        this.estado = false;
                                } else {
                                        this.petri2 = this.petri2.dispararTransicion(transicion);
                                }
                                limpiarComponentes();
                                this.historial.add(this.petri2.getRed().getLugaresClone(), transicion);
                                cargarTablas(this.petri2);
                        } else if (this.petri2 != null) {
                                if (this.petri2.getTransicionesHabilitadas().isEmpty()) {
                                        JOptionPane.showMessageDialog(this, "No hay transiciones habilitadas.",
                                                        "Advertencia",
                                                        JOptionPane.WARNING_MESSAGE);
                                } else {
                                        JOptionPane.showMessageDialog(this, "Seleccione una transición válida.",
                                                        "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                }
                        }
                } else {
                        JOptionPane.showMessageDialog(this, "No hay una red de Petri cargada.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                }

        }// GEN-LAST:event_btnDispararActionPerformed

        private void btnImportarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
                JFileChooser fileChooser = new JFileChooser();
                int resultado = fileChooser.showOpenDialog(this);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        if (file.getName().endsWith(".json")) {
                                jTextField1.setText(file.getAbsolutePath());
                                CargarJSON cargarJSON = new CargarJSON(file);
                                this.petri1 = new Petri(cargarJSON.cargar());
                                this.estado = true;
                                this.historial = new Historial();
                                this.historial.add(this.petri1.getRed().getLugaresClone(), "Inicial");
                                limpiarComponentes();
                                cargarTablas(this.petri1);
                        } else {
                                JOptionPane.showMessageDialog(this, "El archivo seleccionado no es un archivo JSON",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                        }

                }
        }// GEN-LAST:event_jButton1ActionPerformed

        private void limpiarComponentes() {
                // Limpiar tablas
                this.dMasTable.setModel(new javax.swing.table.DefaultTableModel());
                this.dMenosTable.setModel(new javax.swing.table.DefaultTableModel());
                this.MarcacionInicialTable.setModel(new javax.swing.table.DefaultTableModel());
                this.transicionHabilitadaTable.setModel(new javax.swing.table.DefaultTableModel());

                // Limpiar gráfico
                jPanel1.removeAll();
                jPanel1.revalidate();
                jPanel1.repaint();

                // Limpiar combo box
                this.jComboBox1.removeAllItems();

        }

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                try {
                        UIManager.setLookAndFeel(new FlatMacLightLaf());
                } catch (Exception e) {
                        System.err.println("Failed to initialize LaF");
                }

                /* Create and display the form */
                SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                                new PanelDeControl().setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JTable MarcacionInicialTable;
        private javax.swing.JButton btnDisparar;
        private javax.swing.JButton btnHistorial;
        private javax.swing.JButton btnImportar;
        private javax.swing.JButton btnNormal;
        private javax.swing.JScrollPane dMasScrollPane;
        private javax.swing.JTable dMasTable;
        private javax.swing.JScrollPane dMenosScrollPane;
        private javax.swing.JTable dMenosTable;
        private javax.swing.JComboBox<String> jComboBox1;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JScrollPane marcacionInicialScrollPane;
        private javax.swing.JPanel panelPrincipal;
        private javax.swing.JScrollPane tokensScrollPane;
        private javax.swing.JTable tokensTable;
        private javax.swing.JTable transicionHabilitadaTable;
        // End of variables declaration//GEN-END:variables
}
