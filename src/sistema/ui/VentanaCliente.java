/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import sistema.archivos.Archivo;
import sistema.bean.Tropa;
import sistema.ui.bean.Casilla;
import sistema.ui.bean.TropaJL;

/**
 *
 * @author bruno
 */
public class VentanaCliente extends javax.swing.JFrame implements MouseListener{
    private int maxColumnas = 0;
    private int maxFilas = 0;
    private Casilla[][] tablero;
    private JPanel panel;
    private TropaJL tropaMovimientoActual;
    private int jugadorActual = 1;
    private int contadorMovimientosDisponiblesJ1 = 0;
    private int contadorTropasJ1 = 0;
    private int contadorMovimientosDisponiblesJ2 = 0;
    private int contadorTropasJ2 = 0;
    /**
     * Creates new form VentanaCliente
     */
    public VentanaCliente() {
        initComponents();
        definirParametrosIniciales();
        posicionarElementos();    
    }
    
    public void cargarTablero(String contenido, int filas, int columnas){
        try{
            if(panel != null){
                remove(panel);
                txtLog.append("Sobrescribiendo Mapa...\n");
            }
            panel = new JPanel(new GridLayout(filas,columnas,1,1));
            maxColumnas = (columnas>maxColumnas)?columnas:maxColumnas;
            maxFilas = (filas>maxFilas)?filas:maxFilas;
            cargarPaneles(contenido.split("\n"));
            for (int i = 0; i < maxFilas; i++) {
                for (int j = 0; j < maxColumnas; j++) {
                    try{
                        if(tablero[i][j] == null)
                        tablero[i][j] = new Casilla(j,i,"");
                        tablero[i][j].addMouseListener(this);
                        panel.add(tablero[i][j]);
                    }catch(Exception ex){txtLog.append("---->!!!Error al cargar Casilla - Omitida!!!\n");}
                }
            }   
            add(panel, BorderLayout.CENTER);
            txtLog.append("Carga del Mapa terminada\n");
            btnCargarArchivo.setEnabled(true);
            btnCargarTropas.setEnabled(true);
            pack();
        }catch(Exception  ex){
            ex.printStackTrace();
            txtLog.append("---->!!!Error al cargar Mapa!!!\n");
        }
    }
    
    private void cargarPaneles(String[] valores){
        try{
            tablero = new Casilla[maxFilas][maxColumnas];
            txtLog.append("Cargando paneles...\n");
            for (int i = 0; i < valores.length; i++) {
                try{
                    String[] contenidoCoordenada = valores[i].split(",");
                    int fila = (Integer.parseInt(contenidoCoordenada[2]));
                    int columna = (Integer.parseInt(contenidoCoordenada[1]));
                    Casilla casilla = new Casilla(columna, fila, contenidoCoordenada[0]);
                    casilla.setBackground(obtenerColorCasilla(contenidoCoordenada[0]));
                    tablero[fila-1][columna-1] = casilla;
                }catch(Exception ex){txtLog.append("---->!!!Error al cargar Panel - Omitida!!!\n");}
            }
        }catch(Exception ex){
            ex.printStackTrace();
            txtLog.append("---->!!!Error al cargar Paneles!!!\n");
        }
    }
    
    private Color obtenerColorCasilla(String valor){
        switch(valor){
            case "agua": return Color.BLUE;
            case "grama": return Color.GREEN;
            case "arbol": return Color.yellow;
            case "carretera": return Color.GRAY;
            case "montania": return Color.orange;
            case "j1": return Color.RED;
            case "j2": return Color.CYAN;
        }
        return Color.WHITE;
    }
    
    public void cargarTropas(String contenido){
        try{
            String[] valoresContenido = contenido.split("#");
            String[] tropasJugador1 = valoresContenido[0].split("\n");
            contadorTropasJ1 = tropasJugador1.length;
            contadorMovimientosDisponiblesJ1 = contadorTropasJ1;
            String[] tropasJugador2 = valoresContenido[1].split("\n");
            contadorTropasJ2 = tropasJugador2.length;
            contadorMovimientosDisponiblesJ2 = contadorTropasJ2;
            Tropa[][] tableroLogico = new Tropa[maxFilas][maxColumnas];
                
            for (int i = 0; i < tropasJugador1.length; i++) {
                String[] valoresTropa = tropasJugador1[i].split(",");//0-Id/1-Tipo/2-X/3-Y
                int fila = Integer.parseInt(valoresTropa[3]);
                int columna = Integer.parseInt(valoresTropa[2]);
                try{tableroLogico[fila-1][columna-1] = new Tropa(Integer.parseInt(valoresTropa[0]),valoresTropa[1], columna, fila);}catch(Exception ex){txtLog.append("---->!!!Error al cargar Tropas - Fuera de Mapa!!!\n");}
            }
            añadirTropasAlTablero(tableroLogico, 1);
            tableroLogico = new Tropa[maxFilas][maxColumnas];
            for (int i = 0; i < tropasJugador2.length; i++) {
                String[] valoresTropa = tropasJugador2[i].split(",");//0-Id/1-Tipo/2-X/3-Y
                int fila = Integer.parseInt(valoresTropa[3]);
                int columna = Integer.parseInt(valoresTropa[2]);
                try{tableroLogico[fila-1][columna-1] = new Tropa(Integer.parseInt(valoresTropa[0]),valoresTropa[1], columna, fila);}catch(Exception ex){txtLog.append("---->!!!Error al cargar Tropas - Fuera de Mapa!!!\n");}
            }
            añadirTropasAlTablero(tableroLogico, 2);
            pack();
        }catch(Exception ex){
            txtLog.append("---->!!!Error al cargar tropas!!!\n");
        }
    }
    
    private void añadirTropasAlTablero(Tropa[][] tropas, int jugador){
        try{    
            for (int i = 0; i < maxFilas; i++) {
                for (int j = 0; j < maxColumnas; j++) {
                    TropaJL tropaJL;
                    if(tropas[i][j] != null){
                        tropaJL = new TropaJL(tropas[i][j].getId(), tropas[i][j].getNombre(), tropas[i][j].getPosicionX(), tropas[i][j].getPosicionY(), jugador);  
                        tropaJL.setText(tropas[i][j].getNombre());
                    }else
                        tropaJL = new TropaJL();  
                    tropaJL.addMouseListener(this);
                    try{
                        tablero[i][j].setTropa(tropaJL);
                        tablero[i][j].add(tropaJL);
                    }catch(Exception ex){txtLog.append("---->!!!Error al cargar Tropas - Fuera de Mapa!!!\n");}
                }
            }
        }catch(Exception ex){
            txtLog.append("---->!!!Error al cargar ¨Paneles!!!\n");
        }
    }
    
    private void posicionarElementos(){
        JPanel panelAdministrativo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelAdministrativo.add(CboxCargaArchivos);
        panelAdministrativo.add(btnCargarArchivo);
        panelAdministrativo.add(btnCargarTropas);
        panelAdministrativo.add(btnCargarMapa);
        add(panelAdministrativo, BorderLayout.PAGE_START);
        DefaultCaret caret = (DefaultCaret) txtLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollpane = new JScrollPane(txtLog);
        add(scrollpane, BorderLayout.PAGE_END);
    }
    
    private void definirParametrosIniciales(){
        setSize(1300, 900);
        txtLog.setText("Inicializando juego...\n");
        this.setLayout(new BorderLayout());
        CboxCargaArchivos.addItem("Tropas Jugador 1");
        CboxCargaArchivos.addItem("Tropas Jugador 2");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(".army", "army");
        fileChooser.setFileFilter(filtro);
    }
    
    private void flujoDeTurnos(int jugador){
        if(tropaMovimientoActual != null){
            switch(jugador){
                case 1:
                    contadorMovimientosDisponiblesJ1--;
                    txtLog.setText(txtLog.getText() + "--> Movimientos Restantes: "+contadorMovimientosDisponiblesJ1+" Jugador"+jugador+"\n");
                    if(contadorMovimientosDisponiblesJ1 == 0){
                        jugadorActual = 2;
                        txtLog.setText(txtLog.getText() + "--> Turno del Jugador"+jugadorActual+"\n");
                        contadorMovimientosDisponiblesJ1 = contadorTropasJ1;
                        reiniciarTropasJugador(1);
                    }
                    break;
                case 2:
                    contadorMovimientosDisponiblesJ2--;
                    txtLog.setText(txtLog.getText() + "--> Movimientos Restantes: "+contadorMovimientosDisponiblesJ2+" Jugador"+jugador+"\n");
                    if(contadorMovimientosDisponiblesJ2 == 0){
                        jugadorActual = 1;
                        txtLog.setText(txtLog.getText() + "--> Turno del Jugador"+jugadorActual+"\n");
                        contadorMovimientosDisponiblesJ2 = contadorTropasJ2;
                        reiniciarTropasJugador(2);
                    }
                    break;
            }
        }
    }
    
    private void reiniciarTropasJugador(int jugador){
        for (int i = 0; i < maxFilas; i++) {
            for (int j = 0; j < maxColumnas; j++) {
                if(tablero[i][j].getTropa().getNombre() != null){
                    if(tablero[i][j].getTropa().getJugador() == jugador)
                       tablero[i][j].getTropa().setMovido(false);
                }
            }
        }
    }
    
    private void enviarCambiosTropa(int id, int posicionX, int posicionY, int vida, int jugador){
        main.Main.emisor.enviarPeticion(id+","+posicionX+","+posicionY+","+vida+">ac"+jugador+"<0");
        txtLog.setText(txtLog.getText() + "--> Enviando informacion al servidor...\n");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        btnCargarArchivo = new javax.swing.JButton();
        CboxCargaArchivos = new javax.swing.JComboBox<>();
        btnCargarMapa = new javax.swing.JButton();
        btnCargarTropas = new javax.swing.JButton();
        txtLog = new javax.swing.JTextArea();

        fileChooser.setAutoscrolls(true);

        btnCargarArchivo.setText("Subir Archivo Tropas");
        btnCargarArchivo.setEnabled(false);
        btnCargarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbtnCargarArchivo(evt);
            }
        });

        btnCargarMapa.setText("Cargar Mapa");
        btnCargarMapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarMapaActionPerformed(evt);
            }
        });

        btnCargarTropas.setText("Cargar Tropas");
        btnCargarTropas.setEnabled(false);
        btnCargarTropas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarTropasActionPerformed(evt);
            }
        });

        txtLog.setEditable(false);
        txtLog.setColumns(20);
        txtLog.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        txtLog.setRows(5);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnbtnCargarArchivo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbtnCargarArchivo
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == fileChooser.APPROVE_OPTION) {
            Archivo archivo = new Archivo();
            archivo.cargarTropas(fileChooser.getSelectedFile().getAbsolutePath(), (CboxCargaArchivos.getSelectedIndex() + 1));
        }
    }//GEN-LAST:event_btnbtnCargarArchivo

    private void btnCargarMapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarMapaActionPerformed
        main.Main.emisor.enviarPeticion("mapa<1");
        txtLog.setText(txtLog.getText() + "--> Solicitando Mapa al servidor...\n");
    }//GEN-LAST:event_btnCargarMapaActionPerformed

    private void btnCargarTropasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarTropasActionPerformed
        main.Main.emisor.enviarPeticion("tropas<1");
        txtLog.setText(txtLog.getText() + "--> Solicitando tropas al servidor...\n");
    }//GEN-LAST:event_btnCargarTropasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboxCargaArchivos;
    private javax.swing.JButton btnCargarArchivo;
    private javax.swing.JButton btnCargarMapa;
    private javax.swing.JButton btnCargarTropas;
    private javax.swing.JFileChooser fileChooser;
    public static javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables
    
    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getComponent() instanceof Casilla){
            if(tropaMovimientoActual != null){
                Casilla casilla = (Casilla)me.getComponent();
                main.Main.ventanaCliente.txtLog.append("--> Moviendo a Columa "+casilla.getPosX()+" Fila "+casilla.getPosY()+" del tipo "+casilla.getTipoCasilla()+"\n");   
                enviarCambiosTropa(tropaMovimientoActual.getId(), casilla.getPosX(), casilla.getPosY(), tropaMovimientoActual.getVida(), jugadorActual);
                TropaJL tropaJL = new TropaJL();  
                tropaJL.addMouseListener(this);
                tablero[tropaMovimientoActual.getPosicionY()-1][tropaMovimientoActual.getPosicionX()-1].setTropa(tropaJL);
                tablero[tropaMovimientoActual.getPosicionY()-1][tropaMovimientoActual.getPosicionX()-1].remove(tropaMovimientoActual);
                tablero[tropaMovimientoActual.getPosicionY()-1][tropaMovimientoActual.getPosicionX()-1].add(tropaJL);
                tropaMovimientoActual.setPosicionX(casilla.getPosX());
                tropaMovimientoActual.setPosicionY(casilla.getPosY());
                tablero[casilla.getPosY()-1][casilla.getPosX()-1].setTropa(tropaMovimientoActual);
                tablero[casilla.getPosY()-1][casilla.getPosX()-1].add(tropaMovimientoActual);
                flujoDeTurnos(jugadorActual);
                tropaMovimientoActual = null;
                repaint();
            }
        }else{
            if(tropaMovimientoActual == null){
                TropaJL tropa = (TropaJL)me.getComponent();
                if(tropa.getJugador() == jugadorActual){    
                    if(!tropa.isMovido()){
                        tropa.setMovido(true);
                        tropaMovimientoActual = tropa;
                        main.Main.ventanaCliente.txtLog.append("--> Moviendo "+tropa.getNombre()+" ID "+tropa.getId()+ " Columa "+tropa.getPosicionX()+" Fila "+tropa.getPosicionY()+" Jugador "+tropa.getJugador()+"\n");
                    }else
                        main.Main.ventanaCliente.txtLog.append("*** Tropa "+tropa.getNombre()+" sin movimientos disponibles!!! \n");
                }else
                    main.Main.ventanaCliente.txtLog.append("*** Tropa de jugador Contrario!!!\n");
            }else{
                main.Main.ventanaCliente.txtLog.append("*** Terminar moviento con "+tropaMovimientoActual.getNombre()+"!!! \n");
            }
        }
        
    }

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
}
