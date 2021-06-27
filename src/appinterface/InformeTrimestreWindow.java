/*
 * Lucia Calzado Piedrabuena
 * Trabajo de Fin de Grado - Grado en Ingenier�a Inform�tica
 * Universidad de Castilla-La Mancha
 */
package appinterface;

import auxiliar.AuxiliarMethods;
import controladores.ControladorAlumno;
import controladores.ControladorPrueba;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import objects.Alumno;
import objects.Competencia;
import objects.Nota;
import objects.Opciones;
import objects.Prueba;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author lucia
 */
public class InformeTrimestreWindow extends javax.swing.JFrame {

    ControladorAlumno contAlumnos;
    ControladorPrueba contPruebas;
    Opciones opciones;
    int asignatura;
    int trimestre;
    DefaultTableModel modelNotas;
    DefaultTableModel modelPruebas;
    HashMap<String, Integer> pruebaConID = new HashMap<>(); //para almacenar las pruebas con sus IDs

    public InformeTrimestreWindow(ControladorAlumno contAlumnos, ControladorPrueba contPruebas, Opciones opciones, int asignatura, String nombreAsignatura, int trimestre) {

        this.contAlumnos = contAlumnos;
        this.opciones = opciones;
        this.asignatura = asignatura;
        this.trimestre = trimestre;

        if (contPruebas != null) {
            this.contPruebas = contPruebas;
        } else {
            this.contPruebas = new ControladorPrueba();
            try {
                this.contPruebas.cargarPruebasAsignatura(asignatura);
            } catch (SQLException e) {
                AuxiliarMethods.showWarning(e.toString());
            }
        }

        initComponents();
        lblAsignatura.setText(nombreAsignatura);
        lblTitulo.setText("Vista general del " + trimestre + "º Trimestre");
        rellenarTablaNotas();
        rellenarTablaPruebas();

        if (tablaPruebas.getRowCount() == 0) {
            lblNoPruebas.setVisible(true);
        } else {
            lblNoPruebas.setVisible(false);
        }

        ejecutarOpciones();

    }

    private void rellenarTablaNotas() {
        //rellenar tabla con el trimestre en el que estamos
        int i;
        int numPruebas = contPruebas.getPruebasAsignatura().get(asignatura).get(trimestre).size();

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Apellidos");
        tableModel.addColumn("Nombre");
        for (i = 0; i < numPruebas; i++) {
            tableModel.addColumn(contPruebas.getPruebasAsignatura().get(asignatura).get(trimestre).get(i).getEtiqueta());
        }

        tablaNotas.setModel(tableModel);
        modelNotas = (DefaultTableModel) tablaNotas.getModel();

        //añadir alumnos
        Object[] row;
        for (Alumno alumno : this.contAlumnos.getAlumnosAsignatura().get(asignatura)) {
            row = new Object[numPruebas + 2];
            row[0] = alumno.getApellidos();
            row[1] = alumno.getNombre();
            i=2;
            for (Nota n : alumno.getNotas()) {
                for (Prueba p : contPruebas.getPruebasAsignatura().get(asignatura).get(trimestre)) {
                    if (n.getIdPrueba() == p.getIdPrueba() && n.getComentario() != null && !n.getComentario().equals("No tiene que hacer la prueba")) {
                        row[i++] = n.getNota();
                    } else if (n.getIdPrueba() == p.getIdPrueba() && n.getComentario() != null && n.getComentario().equals("No tiene que hacer la prueba")){
                        row[i++] = "-";
                    }
                }
            }

            modelNotas.addRow(row);
        }
    }

    private void rellenarTablaPruebas() {
        modelPruebas = (DefaultTableModel) tablaPruebas.getModel();
        Object[] row = new Object[6];
        int i;
        ArrayList<Prueba> pruebas = contPruebas.getPruebasAsignatura().get(asignatura).get(trimestre);

        for (i = 0; i < pruebas.size(); i++) {
            row[0] = pruebas.get(i).getTitulo();
            row[1] = pruebas.get(i).getEtiqueta();
            row[2] = pruebas.get(i).getPeso();
            row[3] = pruebas.get(i).getFecha();
            pruebaConID.put(contPruebas.getPruebasAsignatura().get(asignatura).get(trimestre).get(i).getTitulo(), contPruebas.getPruebasAsignatura().get(asignatura).get(trimestre).get(i).getIdPrueba());
            modelPruebas.addRow(row);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblTitulo = new javax.swing.JLabel();
        btnExportar3 = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(80, 0), new java.awt.Dimension(80, 0), new java.awt.Dimension(80, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 80), new java.awt.Dimension(0, 80), new java.awt.Dimension(32767, 80));
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 40), new java.awt.Dimension(0, 40), new java.awt.Dimension(32767, 40));
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 40), new java.awt.Dimension(0, 40), new java.awt.Dimension(32767, 40));
        lblNoPruebas = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaNotas = new JTable() {
            public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
                // get the current row
                DefaultTableModel model = (DefaultTableModel) tablaNotas.getModel();
                Component comp = super.prepareRenderer(renderer, Index_row, Index_col);

                if(Index_col != 0 && Index_col != 1 && model.getValueAt(Index_row, Index_col) != null && !model.getValueAt(Index_row, Index_col).equals("")
                    && !model.getValueAt(Index_row, Index_col).equals("-")){
                    if (Double.parseDouble(model.getValueAt(Index_row, Index_col).toString()) < 5.0 && !isCellSelected(Index_row, Index_col)) {
                        comp.setForeground(opciones.getColorSuspensos());
                    } else if (!isCellSelected(Index_row, Index_col)) {
                        comp.setForeground(opciones.getColorAprobados());
                    }
                }
                return comp;
            }
        };
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaPruebas = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaCompetencias = new JList(){
            public String getToolTipText(MouseEvent me) {
                int index = locationToIndex(me.getPoint());
                if (index > -1) {
                    String item = (String) getModel().getElementAt(index);
                    return item;
                }
                return null;
            }
        }
        ;
        btnBorrarPrueba = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblAsignatura = new javax.swing.JLabel();
        filler7 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 50), new java.awt.Dimension(0, 50), new java.awt.Dimension(32767, 50));
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 40), new java.awt.Dimension(0, 40), new java.awt.Dimension(32767, 40));
        btnCerrar = new javax.swing.JButton();
        filler9 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 40), new java.awt.Dimension(0, 40), new java.awt.Dimension(32767, 40));
        txtExplain = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vista general del trimestre");
        setBounds(new java.awt.Rectangle(300, 50, 0, 0));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitulo.setText("Vista general del trimestre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(lblTitulo, gridBagConstraints);

        btnExportar3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnExportar3.setIcon(new javax.swing.ImageIcon("C:\\Users\\lucia\\Desktop\\NoName\\assets\\excel.png")); // NOI18N
        btnExportar3.setText("Exportar notas a Excel");
        btnExportar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportar3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(btnExportar3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 10;
        getContentPane().add(filler1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 8;
        getContentPane().add(filler2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 11;
        getContentPane().add(filler4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 6;
        getContentPane().add(filler3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(filler5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        getContentPane().add(filler6, gridBagConstraints);

        lblNoPruebas.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblNoPruebas.setForeground(new java.awt.Color(255, 51, 51));
        lblNoPruebas.setText("Todavía no hay pruebas en este trimestre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(lblNoPruebas, gridBagConstraints);

        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        tablaNotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "Apellidos", "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaNotas);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1290, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Notas", jPanel2);

        tablaPruebas.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tablaPruebas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Título", "Etiqueta", "Peso (%)", "Fecha"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaPruebas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPruebasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaPruebas);
        if (tablaPruebas.getColumnModel().getColumnCount() > 0) {
            tablaPruebas.getColumnModel().getColumn(0).setResizable(false);
            tablaPruebas.getColumnModel().getColumn(1).setResizable(false);
            tablaPruebas.getColumnModel().getColumn(3).setResizable(false);
        }

        listaCompetencias.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        listaCompetencias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listaCompetencias.setToolTipText("Competencias de la prueba");
        listaCompetencias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaCompetenciasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(listaCompetencias);

        btnBorrarPrueba.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnBorrarPrueba.setIcon(new javax.swing.ImageIcon("C:\\Users\\lucia\\Desktop\\NoName\\assets\\trash.png")); // NOI18N
        btnBorrarPrueba.setText("Borrar prueba");
        btnBorrarPrueba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarPruebaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Competencias:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 935, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBorrarPrueba)
                        .addGap(96, 96, 96))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4)
                .addGap(30, 30, 30)
                .addComponent(btnBorrarPrueba)
                .addGap(75, 75, 75))
        );

        jTabbedPane1.addTab("Pruebas", jPanel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        lblAsignatura.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAsignatura.setText("@Asignatura");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(lblAsignatura, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        getContentPane().add(filler7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        getContentPane().add(filler8, gridBagConstraints);

        btnCerrar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon("C:\\Users\\lucia\\Desktop\\NoName\\assets\\Disquete.png")); // NOI18N
        btnCerrar.setText("Guardar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        getContentPane().add(btnCerrar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        getContentPane().add(filler9, gridBagConstraints);

        txtExplain.setEditable(false);
        txtExplain.setColumns(20);
        txtExplain.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtExplain.setRows(5);
        txtExplain.setText("Puede modificar los datos de una prueba en la tabla \"Pruebas\".\nEsta ventana solo es una vista. Si quiere modificar las notas de los alumnos, haga clic en este texto.");
        txtExplain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtExplainMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 2;
        getContentPane().add(txtExplain, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        boolean salir = true;
        String titulo = "";
        for (int i = 0; i < tablaPruebas.getRowCount(); i++) {
            String tituloAntiguo = contPruebas.getPruebasAsignatura().get(asignatura).get(trimestre).get(i).getTitulo();
            if (tablaPruebas.getValueAt(i, 1).toString().length() <= 5) {

                //validamos que la fecha tiene el formato correcto
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false);

                try {
                    int peso = Integer.parseInt(tablaPruebas.getValueAt(i, 2).toString());
                    titulo = tablaPruebas.getValueAt(i, 0).toString();
                    String etiqueta = tablaPruebas.getValueAt(i, 1).toString();
                    String fecha = tablaPruebas.getValueAt(i, 3).toString();
                    dateFormat.parse(tablaPruebas.getValueAt(i, 3).toString());

                    //buscamos las pruebas que existen, encontramos la que hay ahora mismo recorriéndose y la cambiamos
                    for (Prueba p2 : contPruebas.getPruebasAsignatura().get(asignatura).get(trimestre)) {
                        if (p2.getIdPrueba() == pruebaConID.get(tituloAntiguo)) {
                            if (!titulo.equals("") && !String.valueOf(peso).equals("") && !etiqueta.equals("") && !fecha.equals("")) {
                                p2.setTitulo(titulo);
                                p2.setEtiqueta(etiqueta);
                                p2.setPeso(peso);
                                p2.setFecha(fecha);
                                contPruebas.updatePrueba(p2);
                            } else {
                                throw new NullPointerException();
                            }

                        }
                    }

                } catch (ParseException pe) {
                    salir = false;
                    AuxiliarMethods.showWarning("Introduzca una fecha válida.\nFormato: dd/mm/aaaa");
                } catch (SQLException sql) {
                    salir = false;
                    AuxiliarMethods.showWarning("No se ha podido actualizar la prueba " + titulo + ". Por favor, contacte con un administrador.\nMás información: " + sql.toString());
                } catch (NullPointerException npe) {
                    salir = false;
                    AuxiliarMethods.showWarning("Por favor, asegúrese de que todas las celdas de la tabla están bien escritas.");
                }

            } else {
                salir = false;
                AuxiliarMethods.showWarning("La longitud máxima de la etiqueta de la prueba es 5.");
            }
        }
        if (salir) {
            this.dispose();
        }

    }//GEN-LAST:event_btnCerrarActionPerformed

    private void listaCompetenciasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaCompetenciasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_listaCompetenciasMouseClicked

    private void btnBorrarPruebaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarPruebaActionPerformed
        String nombrePrueba = modelPruebas.getValueAt(tablaPruebas.getSelectedRow(), 0).toString();
        String titulo = "¡CUIDADO!\n¿Seguro que quieres borrar " + nombrePrueba + "?\nEsta acción borrará sus competencias y todas sus calificaciones. Esto no se puede deshacer.";
        int paneBorrar = JOptionPane.showConfirmDialog(null, titulo, "Confirmar borrado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        // 0 --> sí         1 --> no
        if (paneBorrar == 0) {
            listaCompetencias.removeAll();
            ArrayList<Nota> notasAlumno;
            //TODO proceder a borrar la prueba de la tabla, de contPruebas, las notas de los alumnos respecto a esa prueba en contAlumnos y todo de la base de datos
            for (int i = 0; i < contAlumnos.getAlumnosAsignatura().get(asignatura).size(); i++) { //itero sobre los alumnos
                notasAlumno = (ArrayList<Nota>) contAlumnos.getAlumnosAsignatura().get(asignatura).get(i).getNotas().clone();
                for (Nota n : contAlumnos.getAlumnosAsignatura().get(asignatura).get(i).getNotas()) {
                    if (n.getIdPrueba() == pruebaConID.get(nombrePrueba)) {
                        notasAlumno.remove(notasAlumno.indexOf(n));
                    }

                }

                contAlumnos.getAlumnosAsignatura().get(asignatura).get(i).setNotas(notasAlumno);
            }

            try {
                contPruebas.borrarPrueba(pruebaConID.get(nombrePrueba), trimestre, asignatura);
            } catch (SQLException e) {
                AuxiliarMethods.showWarning("No se ha podido borrar la prueba de la base de datos. Por favor, contacte con un administrador.\nMás información: " + e.toString());
            }

            modelPruebas.removeRow(tablaPruebas.getSelectedRow());

        }

    }//GEN-LAST:event_btnBorrarPruebaActionPerformed

    private void tablaPruebasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPruebasMouseClicked
        DefaultListModel<String> modeloAsig = new DefaultListModel<>();
        listaCompetencias.setModel(modeloAsig);
        modeloAsig.clear();
        String pruebaCogida = contPruebas.getPruebasAsignatura().get(asignatura).get(trimestre).get(tablaPruebas.getSelectedRow()).getTitulo();
        int idPruebaCogida = pruebaConID.get(pruebaCogida);
        ArrayList<Competencia> arrayCompetencias = contPruebas.getCompetenciasPrueba().get(idPruebaCogida); //competencias de la prueba seleccionada

        for (int i = 0; i < arrayCompetencias.size(); i++) {
            modeloAsig.addElement(arrayCompetencias.get(i).getNombre());
            System.out.println("nombre: " + arrayCompetencias.get(i).getNombre());
        }


    }//GEN-LAST:event_tablaPruebasMouseClicked

    private void txtExplainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExplainMouseClicked
        CalificarPruebasWindow ctw = new CalificarPruebasWindow(lblAsignatura.getText(), asignatura, contAlumnos, contPruebas, opciones);
        ctw.pack();
        ctw.setVisible(true);
    }//GEN-LAST:event_txtExplainMouseClicked

    private void btnExportar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportar3ActionPerformed
        DefaultTableModel model = (DefaultTableModel) tablaNotas.getModel();
        //First Download Apache POI Library For Dealing with excel files.
        //Then add the library to the current project
        FileOutputStream excelFos = null;
        XSSFWorkbook excelJTableExport = null;
        BufferedOutputStream excelBos = null;
        String excelImagePath = null;
        try {

            //Choosing Saving Location
            //Set default location to C:\Users\Authentic\Desktop or your preferred location
            JFileChooser excelFileChooser = new JFileChooser("C:\\Users\\Authentic\\Desktop");
            //Dialog box title
            excelFileChooser.setDialogTitle("Guardar como...");
            //Filter only xls, xlsx, xlsm files
            FileNameExtensionFilter fnef = new FileNameExtensionFilter("Files", "xls", "xlsx", "xlsm");
            //Setting extension for selected file names
            excelFileChooser.setFileFilter(fnef);
            int chooser = excelFileChooser.showSaveDialog(null);
            //Check if save button has been clicked
            if (chooser == JFileChooser.APPROVE_OPTION) {
                //If button is clicked execute this code
                excelJTableExport = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExport.createSheet("Notas "+trimestre+"º trimestre");
                XSSFRow header = excelSheet.createRow(0);
                XSSFCell headerCell = header.createCell(0);
                headerCell.setCellValue("Notas de "+lblAsignatura.getText()+" del "+trimestre+"º trimestre");
                //Loop through the jtable columns and rows to get its values
                for (int i = 2; i<model.getRowCount()+2; i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j<tablaNotas.getColumnModel().getColumnCount(); j++) {

                        XSSFCell excelCell = excelRow.createCell(j);
                        excelCell.setCellValue(model.getValueAt(i, j).toString());
                    }
                }
                excelFos = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBos = new BufferedOutputStream(excelFos);
                excelJTableExport.write(excelBos);
                JOptionPane.showMessageDialog(null, "Exportado correctamente");
            }

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            try {
                if (excelFos != null) {
                    excelFos.close();
                }
                if (excelBos != null) {
                    excelBos.close();
                }
                if (excelJTableExport != null) {
                    excelJTableExport.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }//GEN-LAST:event_btnExportar3ActionPerformed

    private void ejecutarOpciones() {
        List<Component> components = AuxiliarMethods.getAllComponents(this);

        for (Component c : components) {
            c.setFont(new Font(c.getFont().getName(), c.getFont().getStyle(), opciones.getTamañoLetra()));
            if (opciones.getOscuro() && c.getClass() != JButton.class && c.getClass() != JTextField.class) {
                c.setForeground(Color.LIGHT_GRAY);
            }
        }

        lblAsignatura.setFont(new Font(lblAsignatura.getFont().getName(), Font.PLAIN, opciones.getTamañoLetra() + 4));
        lblTitulo.setFont(new Font(lblTitulo.getFont().getName(), Font.BOLD, opciones.getTamañoLetra() + 15));
        lblNoPruebas.setFont(new Font(lblNoPruebas.getFont().getName(), Font.BOLD, opciones.getTamañoLetra() + 10));

        txtExplain.setBackground(opciones.getColorBackground());

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrarPrueba;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnExportar3;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler7;
    private javax.swing.Box.Filler filler8;
    private javax.swing.Box.Filler filler9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblAsignatura;
    private javax.swing.JLabel lblNoPruebas;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList<String> listaCompetencias;
    private javax.swing.JTable tablaNotas;
    private javax.swing.JTable tablaPruebas;
    private javax.swing.JTextArea txtExplain;
    // End of variables declaration//GEN-END:variables
}
