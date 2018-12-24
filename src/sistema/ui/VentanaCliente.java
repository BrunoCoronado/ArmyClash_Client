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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import sistema.archivos.Archivo;
import sistema.ui.bean.Casilla;
import sistema.ui.bean.TropaJL;

/**
 *
 * @author bruno
 */
public class VentanaCliente extends javax.swing.JFrame implements MouseListener{
    private int maxColumnas;
    private int maxFilas;
    private Casilla[][] tablero;
    private JPanel panel;
    private TropaJL tropaMovimientoActual;
    private int jugadorActual;
    private int contadorMovimientosDisponiblesJ1;
    private int contadorTropasJ1;
    private int contadorMovimientosDisponiblesJ2;
    private int contadorTropasJ2;
    private boolean atacando;
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
            maxColumnas = columnas;
            maxFilas = filas;
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
            txtLog.append("---->!!!Error al cargar Mapa!!!\n");
        }
    }
    
    private void cargarPaneles(String[] valores){
        try{
            tablero = new Casilla[maxFilas][maxColumnas];
            txtLog.append("Cargando paneles...\n");
            for (String valore : valores) {
                try {
                    String[] contenidoCoordenada = valore.split(",");
                    int fila = (Integer.parseInt(contenidoCoordenada[2]));
                    int columna = (Integer.parseInt(contenidoCoordenada[1]));
                    Casilla casilla = new Casilla(columna, fila, contenidoCoordenada[0]);
                    casilla.setBackground(obtenerColorCasilla(contenidoCoordenada[0]));
                    tablero[fila-1][columna-1] = casilla;
                }catch(NumberFormatException ex){txtLog.append("---->!!!Error al cargar Panel - Omitida!!!\n");}
            }
        }catch(Exception ex){
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
        }
        return Color.WHITE;
    }
    
    public void cargarTropas(String contenido){
        try{
            String[] valoresContenido = contenido.split("#");
            String[] tropasJugador1 = valoresContenido[0].split("\n");
            String[] tropasJugador2 = valoresContenido[1].split("\n");
            for (String tropasJugador11 : tropasJugador1) {
                String[] valoresTropa = tropasJugador11.split(","); //0-Id/1-Tipo/2-X/3-Y
                int fila = Integer.parseInt(valoresTropa[3]);
                int columna = Integer.parseInt(valoresTropa[2]);
                TropaJL tropaJL = new TropaJL(Integer.parseInt(valoresTropa[0]),valoresTropa[1], columna, fila, 1, tablero[fila-1][columna-1].getTipoCasilla());
                tropaJL.addMouseListener(this);
                if(validarNivelDesplazamientoTropa(tropaJL.getNivelDesplazamiento(), tablero[fila-1][columna-1])){
                    if(tablero[fila-1][columna-1].getTropa() == null){
                        try{
                            tablero[fila-1][columna-1].setTropa(tropaJL);
                            actualizarContadorCelda(convertirNumeroCapa(tablero[fila-1][columna-1].getTipoCasilla()),columna,fila);
                            contadorTropasJ1++;
                        }catch(Exception ex){txtLog.append("---->!!!Error al cargar Tropas - Fuera de Mapa!!!\n");}
                    }else
                        txtLog.append("---->!!!Error al cargar Tropas - Omitida - Generada En Terreno Ocupado!!!\n");
                }else
                    txtLog.append("---->!!!Error al cargar Tropas - Omitida - Generada En Terreno Invalido!!!\n");
            }       
            for (String tropasJugador21 : tropasJugador2) {
                String[] valoresTropa = tropasJugador21.split(","); //0-Id/1-Tipo/2-X/3-Y
                int fila = Integer.parseInt(valoresTropa[3]);
                int columna = Integer.parseInt(valoresTropa[2]);
                TropaJL tropaJL = new TropaJL(Integer.parseInt(valoresTropa[0]),valoresTropa[1], columna, fila,2, tablero[fila-1][columna-1].getTipoCasilla());
                tropaJL.addMouseListener(this);
                if(validarNivelDesplazamientoTropa(tropaJL.getNivelDesplazamiento(), tablero[fila-1][columna-1])){
                    if(tablero[fila-1][columna-1].getTropa() == null){
                        try{
                            tablero[fila-1][columna-1].setTropa(tropaJL);
                            actualizarContadorCelda(convertirNumeroCapa(tablero[fila-1][columna-1].getTipoCasilla()),columna,fila);
                            contadorTropasJ2++;
                        }catch(Exception ex){txtLog.append("---->!!!Error al cargar Tropas - Fuera de Mapa!!!\n");}
                    }else
                        txtLog.append("---->!!!Error al cargar Tropas - Omitida - Generada En Terreno Ocupado!!!\n");
                }else
                    txtLog.append("---->!!!Error al cargar Tropas - Omitida - Generada En Terreno Invalido!!!\n");
            }
            txtLog.append("--> Tropas Cargadas\n");
            contadorMovimientosDisponiblesJ1 = contadorTropasJ1;
            contadorMovimientosDisponiblesJ2 = contadorTropasJ2;
            btnFinalizarTurno.setEnabled(true);
            pack();
        }catch(Exception ex){
            ex.printStackTrace();
            txtLog.append("---->!!!Error al cargar tropas!!!\n");
        }
    }
    
    private void posicionarElementos(){
        JPanel panelAdministrativo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelAdministrativo.add(CboxCargaArchivos);
        panelAdministrativo.add(btnCargarArchivo);
        panelAdministrativo.add(btnCargarTropas);
        panelAdministrativo.add(btnCargarMapa);
        panelAdministrativo.add(btnFinalizarTurno);
        add(panelAdministrativo, BorderLayout.PAGE_START);
        DefaultCaret caret = (DefaultCaret) txtLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollpane = new JScrollPane(txtLog);
        add(scrollpane, BorderLayout.PAGE_END);
    }
    
    private void reiniciarParametros(){
        txtLog.setText("");
        maxColumnas = 0;
        maxFilas = 0;
        tablero = null;
        remove(panel);
        panel = null;        
        jugadorActual = 1;
        tropaMovimientoActual = null;
        contadorMovimientosDisponiblesJ1 = 0;
        contadorTropasJ1 = 0;
        contadorMovimientosDisponiblesJ2 = 0;
        contadorTropasJ2 = 0;
        atacando = false;
        btnCargarArchivo.setEnabled(false);
        btnCargarTropas.setEnabled(false);
        btnFinalizarTurno.setEnabled(false);
        pack();
        txtLog.setText("Inicializando juego...\n");
    }
    
    private void definirParametrosIniciales(){
        setSize(1300, 900);
        txtLog.setText("");
        maxColumnas = 0;
        maxFilas = 0;
        tablero = null;
        panel = null;        
        jugadorActual = 1;
        tropaMovimientoActual = null;
        contadorMovimientosDisponiblesJ1 = 0;
        contadorTropasJ1 = 0;
        contadorMovimientosDisponiblesJ2 = 0;
        contadorTropasJ2 = 0;
        atacando = false;
        txtLog.setText("Inicializando juego...\n");
        this.setLayout(new BorderLayout());
        CboxCargaArchivos.addItem("Tropas Jugador 1");
        CboxCargaArchivos.addItem("Tropas Jugador 2");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(".army", "army");
        fileChooser.setFileFilter(filtro);
        //manejamos el comportamiento del JPopupMenu con un listener para el componenete
        menuJugador.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {}

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent pme) {
                if(!atacando){
                    flujoDeTurnos(jugadorActual);
                    tropaMovimientoActual = null;
                }
            }
        });
    }
    
    private void flujoDeTurnos(int jugador){
        if(tropaMovimientoActual != null){
            switch(jugador){
                case 1:
                    contadorMovimientosDisponiblesJ1--;
                    txtLog.append("--> Movimientos Restantes: "+contadorMovimientosDisponiblesJ1+" Jugador"+jugador+"\n");
                    if(contadorMovimientosDisponiblesJ1 == 0){
                        jugadorActual = 2;
                        txtLog.append("--> Turno del Jugador"+jugadorActual+"\n");
                        contadorMovimientosDisponiblesJ1 = contadorTropasJ1;
                        reiniciarTropasJugador(1);
                    }
                    break;
                case 2:
                    contadorMovimientosDisponiblesJ2--;
                    txtLog.append("--> Movimientos Restantes: "+contadorMovimientosDisponiblesJ2+" Jugador"+jugador+"\n");
                    if(contadorMovimientosDisponiblesJ2 == 0){
                        jugadorActual = 1;
                        txtLog.append("--> Turno del Jugador"+jugadorActual+"\n");
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
                if(tablero[i][j].getTropa() != null){
                    if(tablero[i][j].getTropa().getJugador() == jugador)
                       tablero[i][j].getTropa().setMovido(false);
                }
            }
        }
    }
    
    private void enviarCambiosTropa(int id, int posicionX, int posicionY, double vida, int jugador, String identificadorMensaje){
        main.Main.emisor.enviarPeticion(id+","+posicionX+","+posicionY+","+vida+">"+identificadorMensaje+""+jugador+"<0");
        txtLog.setText(txtLog.getText() + "--> Enviando informacion al servidor...\n");
    }
    
    private boolean validarMovimiento(TropaJL tropa, Casilla casilla){
        //validar que no este en ataque
        if(atacando){
            main.Main.ventanaCliente.txtLog.append("--> Movimiento Invalido - Ataque En Curso Finalizado!!!\n");                      
            atacando = false;
            flujoDeTurnos(jugadorActual);
            tropaMovimientoActual = null;
            return false;
        }
        //validar que este disponible
        if(casilla.getTropa() != null){
            main.Main.ventanaCliente.txtLog.append("--> Movimiento Invalido - Casilla Ocupada!!!\n");                      
            return false;
        }
        //validar el terreno
        if(!validarNivelDesplazamientoTropa(tropa.getNivelDesplazamiento(), casilla)){
            main.Main.ventanaCliente.txtLog.append("--> Movimiento Invalido - Terreno Invalido!!!\n");
            return false;
        }
        //validar limite movimiento
        int casillasMovidas = Math.abs(tropa.getPosicionX()-casilla.getPosX())+Math.abs(tropa.getPosicionY()-casilla.getPosY());
        if(casillasMovidas > tropa.getAlcanceMovimiento()){
            main.Main.ventanaCliente.txtLog.append("--> Movimiento Invalido - Fuera de Alcance!!!\n");
            return false;
        }
        return true;
    }
    
    private boolean validarNivelDesplazamientoTropa(int nivelDesplazamiento, Casilla casilla){
        if(!casilla.getTipoCasilla().equals("")){
            if(nivelDesplazamiento != 0){
                switch(nivelDesplazamiento){
                    case 1:
                        if(casilla.getTipoCasilla().equals("agua")){
                            return false;
                        }
                        break;
                    case 2:
                        if(casilla.getTipoCasilla().equals("montania") || casilla.getTipoCasilla().equals("agua")){
                            return false;
                        }
                        break;
                    case 3:
                        if(!casilla.getTipoCasilla().equals("grama")){
                            if(!casilla.getTipoCasilla().equals("carretera")){
                                return false;
                            }
                        }
                        break;
                    case 4:
                        if(!casilla.getTipoCasilla().equals("carretera")){
                            return false;
                        }
                        break;
                    default:
                        return false;
                }
            }
            return true;
        }
        return false;
    }
    
    private void moverTropa(Casilla casilla){
        main.Main.ventanaCliente.txtLog.append("--> Moviendo a Columa "+casilla.getPosX()+" Fila "+casilla.getPosY()+" del tipo "+casilla.getTipoCasilla()+"\n");   
        actualizarContadorCelda(convertirNumeroCapa(casilla.getTipoCasilla()),casilla.getPosX(),casilla.getPosY());
        enviarCambiosTropa(tropaMovimientoActual.getId(), casilla.getPosX(), casilla.getPosY(), tropaMovimientoActual.getVida(), jugadorActual, "ac");
        tablero[tropaMovimientoActual.getPosicionY()-1][tropaMovimientoActual.getPosicionX()-1].removeTropa();
        tropaMovimientoActual.setPosicionX(casilla.getPosX());
        tropaMovimientoActual.setPosicionY(casilla.getPosY());
        tropaMovimientoActual.setBonus(tablero[casilla.getPosY()-1][casilla.getPosX()-1].getTipoCasilla());
        tablero[casilla.getPosY()-1][casilla.getPosX()-1].setTropa(tropaMovimientoActual);
        repaint();
    }
    
    private void atacarTropa(TropaJL atacante, TropaJL atacado){
        if(atacante.getJugador() != atacado.getJugador()){
            int distanciaAtaque = Math.abs(atacante.getPosicionX()-atacado.getPosicionX())+Math.abs(atacante.getPosicionY()-atacado.getPosicionY());
            if(distanciaAtaque <= atacante.getAlcanceAtaque()){
                main.Main.ventanaCliente.txtLog.append("--> Atacando Con "+atacante.getNombre()+" ID "+atacante.getId()+" con Vida "+atacante.getVida()+" de jugador "+atacante.getJugador()+" a "+atacado.getNombre()+" ID "+atacado.getId()+" con vida "+atacado.getVida()+" de jugador "+atacado.getJugador()+"\n");
                atacado.setVida(atacado.getVida() - calcularAtaque(atacante.getAtaque(), atacante.getVida(), atacante.getVidaTotal(), atacante.getBonus()));
                if(atacado.getVida() > 0){
                    main.Main.ventanaCliente.txtLog.append("--> Resultado: "+atacado.getNombre()+" ID "+atacado.getId()+" vida "+atacado.getVida()+" de jugador "+atacado.getJugador()+"!!!\n");
                    tablero[atacado.getPosicionY()-1][atacado.getPosicionX()-1].getTropa().setVida(atacado.getVida());
                    enviarCambiosTropa(atacado.getId(), atacado.getPosicionX(), atacado.getPosicionY(), atacado.getVida(), atacado.getJugador(), "atq");
                    if(distanciaAtaque <= atacado.getAlcanceAtaque()){
                        main.Main.ventanaCliente.txtLog.append("--> Contraatque de "+atacado.getNombre()+" ID "+atacado.getId()+" con Vida "+atacado.getVida()+" de jugador "+atacado.getJugador()+" a "+atacante.getNombre()+" ID "+atacante.getId()+" con vida "+atacante.getVida()+" de "+atacante.getNombre()+"\n");
                        atacante.setVida(atacante.getVida() - calcularAtaque(atacado.getAtaque(), atacado.getVida(), atacado.getVidaTotal(), atacado.getBonus()));

                        if(atacante.getVida() > 0){
                            main.Main.ventanaCliente.txtLog.append("--> Resultado: "+atacante.getNombre()+" ID "+atacante.getId()+" vida "+atacante.getVida()+" de jugador "+atacante.getJugador()+"!!!\n");
                            tablero[atacante.getPosicionY()-1][atacante.getPosicionX()-1].getTropa().setVida(atacante.getVida());   
                            enviarCambiosTropa(atacante.getId(), atacante.getPosicionX(), atacante.getPosicionY(), atacante.getVida(), atacante.getJugador(), "atq");
                        }else{
                            main.Main.ventanaCliente.txtLog.append("--> Resultado: "+atacante.getNombre()+" ID "+atacante.getId()+" de "+atacante.getJugador()+" MUERTO!!!\n");
                            tablero[atacante.getPosicionY()-1][atacante.getPosicionX()-1].removeTropa();
                            repaint();
                            informarMuerte(atacante.getId(), atacante.getJugador(), "mrt");
                            main.Main.ventanaCliente.txtLog.append("--> atacante muerto!!!\n");
                        }
                    }
                }else{
                    main.Main.ventanaCliente.txtLog.append("--> Resultado: "+atacado.getNombre()+" ID "+atacado.getId()+" de "+atacado.getJugador()+" MUERTO!!!\n");
                    tablero[atacado.getPosicionY()-1][atacado.getPosicionX()-1].removeTropa();
                    repaint();
                    informarMuerte(atacado.getId(), atacado.getJugador(), "mrt");
                }
            }else{
                main.Main.ventanaCliente.txtLog.append("--> Ataque Fuera de alcance!!!\n");
            }
            
        }else
            main.Main.ventanaCliente.txtLog.append("--> Imposible Atacar Tropas Aliadas!!!\n");
    }
    
    private void informarMuerte(int id, int jugador, String identificadorMensaje){
        if(jugador == 1){
            contadorTropasJ1--;
            contadorMovimientosDisponiblesJ1--;
        }else{
            contadorTropasJ2--;
            contadorMovimientosDisponiblesJ2--;
        }
        main.Main.emisor.enviarPeticion(id+">"+identificadorMensaje+""+jugador+"<0");
        txtLog.setText(txtLog.getText() + "--> Enviando informacion al servidor...\n");
        validarPartida(jugador);
        
    }
    
    private void validarPartida(int jugador){
        int tropas = (jugador==1)?contadorTropasJ1:contadorTropasJ2;
        if(tropas <= 0){
            int ganador = (jugador==1)?2:1;
            JOptionPane.showMessageDialog(this, "Vicotoria para el jugador "+ganador);
            reiniciarCliente();
            main.Main.emisor.enviarPeticion("0>pf<0");
        }
    }
    
    private void reiniciarCliente(){
        reiniciarParametros();  
    }
    
    private void actualizarContadorCelda(int capa, int x, int y){
        main.Main.emisor.enviarPeticion(capa+","+x+","+y+">cc<0");
    }
    
    private double calcularAtaque(int daño, double vidaAtacante, int vidaTotalAtacante, double bonus){
        double dañoInfligido = 0;
        double dañoBonus = 0;
        txtLog.setText(txtLog.getText() + "---> Calculando Daño infligido con > daño tropa: "+daño+" vida tropa: "+vidaAtacante+" vida total: "+vidaTotalAtacante+" bonus: "+bonus+"\n");
        dañoInfligido =((daño)*(vidaAtacante/vidaTotalAtacante));
        dañoBonus = ((daño)*(vidaAtacante/vidaTotalAtacante)*bonus);
        txtLog.setText(txtLog.getText() + "---> Daño infligido: "+dañoInfligido+" Daño bonus: "+dañoBonus+" Daño infligido total: "+(dañoInfligido+dañoBonus)+"\n");
        return dañoInfligido+dañoBonus;
    }
    
    private int convertirNumeroCapa(String capa){
        switch(capa){
            case "agua": return 0;
            case "grama": return 1;
            case "arbol": return 2;
            case "carretera": return 3;
            case "montania": return 4;
        }
        return -1;
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
        menuJugador = new javax.swing.JPopupMenu();
        Atacar = new javax.swing.JMenuItem();
        Finalizar = new javax.swing.JMenuItem();
        btnFinalizarTurno = new javax.swing.JButton();

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

        Atacar.setText("Atacar");
        Atacar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtacarActionPerformed(evt);
            }
        });
        menuJugador.add(Atacar);

        Finalizar.setText("Finalizar");
        Finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FinalizarActionPerformed(evt);
            }
        });
        menuJugador.add(Finalizar);

        btnFinalizarTurno.setText("Finalizar Turno");
        btnFinalizarTurno.setEnabled(false);
        btnFinalizarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarTurnoActionPerformed(evt);
            }
        });

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

    private void FinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FinalizarActionPerformed
        flujoDeTurnos(jugadorActual);
        tropaMovimientoActual = null;
    }//GEN-LAST:event_FinalizarActionPerformed

    private void AtacarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtacarActionPerformed
        atacando = true;
        txtLog.setText(txtLog.getText() + "--> Atacando con "+tropaMovimientoActual.getNombre()+" de jugador "+tropaMovimientoActual.getJugador()+"\n");
    }//GEN-LAST:event_AtacarActionPerformed

    private void btnFinalizarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarTurnoActionPerformed
        tropaMovimientoActual = null;
        atacando = false;
        switch(jugadorActual){
            case 1:
                contadorMovimientosDisponiblesJ1 = 0;
                txtLog.setText("--> Turno Jugador 1 Finalizado\n");
                if(contadorMovimientosDisponiblesJ1 == 0){
                    jugadorActual = 2;
                    txtLog.setText(txtLog.getText() + "--> Turno del Jugador"+jugadorActual+"\n");
                    contadorMovimientosDisponiblesJ1 = contadorTropasJ1;
                    reiniciarTropasJugador(1);
                }
                break;
            case 2:
                contadorMovimientosDisponiblesJ2 = 0;
                txtLog.setText("--> Turno Jugador 2 Finalizado\n");
                if(contadorMovimientosDisponiblesJ2 == 0){
                    jugadorActual = 1;
                    txtLog.setText(txtLog.getText() + "--> Turno del Jugador"+jugadorActual+"\n");
                    contadorMovimientosDisponiblesJ2 = contadorTropasJ2;
                    reiniciarTropasJugador(2);
                }
                break;
        }
    }//GEN-LAST:event_btnFinalizarTurnoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Atacar;
    private javax.swing.JComboBox<String> CboxCargaArchivos;
    private javax.swing.JMenuItem Finalizar;
    private javax.swing.JButton btnCargarArchivo;
    private javax.swing.JButton btnCargarMapa;
    private javax.swing.JButton btnCargarTropas;
    private javax.swing.JButton btnFinalizarTurno;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JPopupMenu menuJugador;
    public static javax.swing.JTextArea txtLog;
    // End of variables declaration//GEN-END:variables
    
    /**
     *
     * @param me
     */
    @Override
    public void mouseClicked(MouseEvent me) {
        if(me.getComponent() instanceof Casilla){
            if(tropaMovimientoActual != null){
                if(validarMovimiento(tropaMovimientoActual, (Casilla)me.getComponent())){
                    moverTropa((Casilla)me.getComponent());
                    if(!me.isPopupTrigger()){
                        menuJugador.show(me.getComponent(), me.getX(), me.getY());
                    }
                }
            }
        }else{
            if(tropaMovimientoActual == null){
                TropaJL tropa = (TropaJL)me.getComponent();
                if(tropa.getJugador() == jugadorActual){    
                    if(!tropa.isMovido()){
                        tropa.setMovido(true);
                        tropaMovimientoActual = tropa;
                        VentanaCliente.txtLog.append("--> Moviendo "+tropa.getNombre()+" ID "+tropa.getId()+ " Columa "+tropa.getPosicionX()+" Fila "+tropa.getPosicionY()+" Jugador "+tropa.getJugador()+"\n");
                    }else
                        VentanaCliente.txtLog.append("*** Tropa "+tropa.getNombre()+" sin movimientos disponibles!!! \n");
                }else
                    VentanaCliente.txtLog.append("*** Tropa de jugador Contrario!!!\n");
            }else{
                if(!atacando)
                    VentanaCliente.txtLog.append("*** Terminar movimiento con "+tropaMovimientoActual.getNombre()+"!!! \n");
                else{
                    //seleccione una tropa para el ataque
                    atacarTropa(tropaMovimientoActual, (TropaJL)me.getComponent());
                    atacando = false;
                    flujoDeTurnos(jugadorActual);
                    tropaMovimientoActual = null;
                }
            }
        }
    }

    /**
     *
     * @param me
     */
    @Override
    public void mousePressed(MouseEvent me) {}

    /**
     *
     * @param me
     */
    @Override
    public void mouseReleased(MouseEvent me) {}

    /**
     *
     * @param me
     */
    @Override
    public void mouseEntered(MouseEvent me) {}
    
    /**
     *
     * @param me
     */
    @Override
    public void mouseExited(MouseEvent me) {}
}
