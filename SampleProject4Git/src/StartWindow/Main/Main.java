package StartWindow.Main;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import StartWindow.Usuarios.Credentials;
import com.opencsv.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.imageio.IIOException;
import javax.sound.midi.Patch;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.event.*;

//import java.io.ObjectOutputStream.PutField;
//import StartWindow.ListasEnlazadas.*;
//import panamahitek.Arduino.PanamaHitek_Arduino;

import StartWindow.ListasEnlazadas.Double_CircularLinkedList;
import StartWindow.Reproductor.*;
import StartWindow.XmlControladores.XMLCreator;

import comunicacionserial.ArduinoExcepcion;
import comunicacionserial.ComunicacionSerial_Arduino;

public class Main extends JFrame implements ActionListener {

    ComunicacionSerial_Arduino conexion = new ComunicacionSerial_Arduino();
    static Double_CircularLinkedList Listas_de_Canciones= new Double_CircularLinkedList();
    static Reproducir_Musica Reproductor= new Reproducir_Musica();
    
    String Nombre_Canción, ruta;
    static JSlider Volume= new JSlider(JSlider.HORIZONTAL, 0, 100, 50 );
    private static JComboBox<String> SeleciónCanción = new JComboBox<String>();
    private static JComboBox<String> SeleciónBiblioteca = new JComboBox<String>();

    public  static  int Reproducción;
    private static String Path;

    static JCheckBox FavoritaBtn;
  
    JButton Playbtn, Pausebtn, Continuebtn, Stopbtn, AgregarCanciónBtn, EliminarCanciónBtn, 
    AgregarBibliotecaBtn, EliminarBibliotecaBtn, Anteriorbtn, Siguientebtn, Statusbtn, DatosBtn;
    /*
     * Aqui se crean los botones y se ubican, tambien se agrega el evento del JSlider que es el volumen
     */
    public Main() throws IOException{

        /*try {
            conexion.arduinoTX("COM3",9600);
        } catch (ArduinoExcepcion e) {
             throw new RuntimeException(e);
        }*/

        setLayout(null);

        FavoritaBtn= new JCheckBox("Fav");
        FavoritaBtn.setBounds(120, 85, 70, 50);
        add(FavoritaBtn);
        FavoritaBtn.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                //FavoritaBtn.setSelected(false);
                
            }

        });

        AgregarBibliotecaBtn= new JButton("Agregar Biblioteca");
        AgregarBibliotecaBtn.setBounds(340, 20, 150 ,50);
        add(AgregarBibliotecaBtn);
        AgregarBibliotecaBtn.addActionListener(this);

        EliminarBibliotecaBtn= new JButton("Eliminar Biblioteca");
        EliminarBibliotecaBtn.setBounds(340, 85, 150, 50);
        add(EliminarBibliotecaBtn);
        EliminarBibliotecaBtn.addActionListener(this);

        AgregarCanciónBtn= new JButton("Agregar Canción");
        AgregarCanciónBtn.setBounds(520, 20, 150, 50);
        add(AgregarCanciónBtn);
        AgregarCanciónBtn.addActionListener(this);

        EliminarCanciónBtn= new JButton("Eliminar Canción");
        EliminarCanciónBtn.setBounds(520, 85, 150, 50);
        add(EliminarCanciónBtn);
        EliminarCanciónBtn.addActionListener(this);

        Stopbtn= new JButton("<html>Stop<html>");
        Stopbtn.setBounds(20, 160, 60, 50);
        add(Stopbtn);
        Stopbtn.addActionListener(this);

        Anteriorbtn= new JButton("<html>Previous Music<html>");
        Anteriorbtn.setBounds(80, 160, 75, 50);
        add(Anteriorbtn);
        Anteriorbtn.addActionListener(this);

        Playbtn= new JButton("<html>Play<html>");
        Playbtn.setBounds(155, 160, 60, 50);
        add(Playbtn);
        Playbtn.addActionListener(this);

        Pausebtn= new JButton("<html>Pause<html>");
        Pausebtn.setBounds(215, 160, 65, 50);
        add(Pausebtn);
        Pausebtn.addActionListener(this);

        Continuebtn= new JButton("<html>Resume<html>");
        Continuebtn.setBounds(280, 160, 80, 50);
        add(Continuebtn);
        Continuebtn.addActionListener(this);

        Siguientebtn= new JButton("<html>Next Music<html>");
        Siguientebtn.setBounds(360, 160, 75, 50);
        add(Siguientebtn);
        Siguientebtn.addActionListener(this);

        Statusbtn= new JButton("Status");
        Statusbtn.setBounds(430, 300, 75, 50);
        add(Statusbtn);
        Statusbtn.addActionListener(this);

        DatosBtn= new JButton("<html>Datos de Canción<html>");
        DatosBtn.setBounds(430, 350, 75, 50);
        add(DatosBtn);
        DatosBtn.addActionListener(this);

        JPanel ContenedorVolume= new JPanel();
        ContenedorVolume.setBounds(10, 210, 450, 90);
        ContenedorVolume.add(Volume);
        add(ContenedorVolume);

        //Evento del JSlider
        Volume.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                Double Dicibelios= (double) (0.00+ (Volume.getValue()/100.00));
                try {
                    Reproductor.ChangeVolume(Dicibelios);
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            
        });;

        JLabel Canciona_Selecionada = new JLabel("Sin selecionar");
        Canciona_Selecionada.setVerticalAlignment(JLabel.BOTTOM);
        Canciona_Selecionada.setHorizontalAlignment(JLabel.CENTER);
        add(Canciona_Selecionada);
        
        JLabel TextBiblioteca = new JLabel("<html>Seleciona la biblioteca<html>");
        TextBiblioteca.setVisible(true);

        JPanel ContenedorTextBiblioteca= new JPanel();
        ContenedorTextBiblioteca.add(TextBiblioteca);
        ContenedorTextBiblioteca.setBounds(10, 15, 135, 20);
        add(ContenedorTextBiblioteca);
        
        SeleciónBiblioteca.addActionListener(this);
        JPanel ContenedorSelecciónBibliotecas = new JPanel();
        ContenedorSelecciónBibliotecas.setBounds(30, 35, 145, 200);
        ContenedorSelecciónBibliotecas.add(SeleciónBiblioteca);
        add(ContenedorSelecciónBibliotecas);


        JLabel TextCanciones= new JLabel("<html>Selecciona la canción<html>");
        TextCanciones.setVisible(true);

        JPanel ContenedorTextCanciones = new JPanel();
        ContenedorTextCanciones.setBounds(170, 15, 135, 20);
        ContenedorTextCanciones.add(TextCanciones);
        add(ContenedorTextCanciones);
        
        JPanel ContenedorSelecciónCanción = new JPanel();
        ContenedorSelecciónCanción.setBounds(190, 35, 145, 200);
        ContenedorSelecciónCanción.add(SeleciónCanción);
        add(ContenedorSelecciónCanción);

        BibliotecasDisponibles();

        if(EsFavorita()) {
            FavoritaBtn.setSelected(true);
        }

    }
 
    
    /** 
     * @param btn
     */
    @Override
    public void actionPerformed(ActionEvent btn) {

        if (btn.getSource() == AgregarBibliotecaBtn) {
            CSVWriter csvWriter;
            BufferedReader archivocsv;

            try {
                archivocsv = new BufferedReader(new FileReader(Path));
                int NumDeLinea = -2;
                while (archivocsv.readLine() != null) {
                    NumDeLinea++;
                 }
                String [] NuevaLinea= new String[1];
                NuevaLinea[0] = "Biblioteca "+NumDeLinea;
                csvWriter = new CSVWriter(new FileWriter(Path, true)); //Se escribe al final del documento
                csvWriter.writeNext(NuevaLinea);
                csvWriter.close();
                archivocsv.close();

                BibliotecasDisponibles();
         }
         catch(Exception ee) {
            BibliotecasDisponibles();
        }
        }

        if (btn.getSource() == EliminarBibliotecaBtn) {
            CSVWriter csvWriter;
            BufferedReader archivocsv;
            String[] datos;
            int largo_de_datos;
            int num_datos;
            String linea;
            try {
                archivocsv = new BufferedReader(new FileReader(Path));
                //Se lee primero la linea por que CVSWrite la borra inmediadamente cuando se instancia
                linea= archivocsv.readLine();
                csvWriter = new CSVWriter(new FileWriter(Path));
                datos= linea.split(",", -1);
                largo_de_datos= datos.length;
                num_datos= 0;
                String[] Escribir= new String[largo_de_datos];

                //Se rescriben los datos de la primera fila para no perderlos.
                while(num_datos < largo_de_datos) {
                    Escribir[num_datos]= datos[num_datos].replaceAll("\"", "");
                    num_datos++;
                }
                csvWriter.writeNext(Escribir);
                
                String Biblioteca_a_eliminar;
                while((linea= archivocsv.readLine()) != null ){
                    datos= linea.split(",", -1);
                    Biblioteca_a_eliminar= datos[0].replaceAll("\"", "");

                    //Se verifica si en la linea actual se encuentra la biblioteca deseada
                    if (Biblioteca_a_eliminar.equals(SeleciónBiblioteca.getSelectedItem())){
                        linea=archivocsv.readLine();
                        if(linea == null){
                            break;
                        }else {
                            datos= linea.split(",", -1);
                        }
                    }
                    largo_de_datos = datos.length;
                    num_datos = 0;
                    String[] Escribir2= new String[largo_de_datos];

                    //Se rescriben los datos de la linea actual para no perderlos
                    while (num_datos < largo_de_datos) {
                        Escribir2[num_datos]= datos[num_datos].replaceAll("\"", "");
                        num_datos++;
                    }                        
                    csvWriter.writeNext(Escribir2);
            }
                csvWriter.close();
                archivocsv.close();
                BibliotecasDisponibles();
            }catch(Exception ee) {
                BibliotecasDisponibles();
            }
        }

        if (btn.getSource() == SeleciónBiblioteca) {
            try {
                CancionesDisponibles();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (btn.getSource() == Playbtn) {
            try {
                Reproductor.Reprodución_Continua(CancionActual(), Listas_de_Canciones);
                Reproducción= Reproductor.Status();
                conexion.sendData("1");

            } catch (Exception ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
        }

        if (btn.getSource() == Pausebtn) {
            try {
                Reproductor.Pausa();
                conexion.sendData("2");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (btn.getSource() == Continuebtn) {
            try {
                ContinueMusic();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (btn.getSource() == Stopbtn) {
           try {
            StopMusic();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }

        if (btn.getSource() == AgregarCanciónBtn) {
            
            Scanner entrada = null;
            JFileChooser Escoger_Canción = new JFileChooser();
            Escoger_Canción.showOpenDialog(Escoger_Canción);
            try {
                ruta = Escoger_Canción.getSelectedFile().getAbsolutePath();                                        
                File archivo = new File(ruta);
                Nombre_Canción= archivo.getName();
                entrada = new Scanner(archivo);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("No se ha seleccionado ningún fichero");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                if (entrada != null) { //el usuario escogió una canción
                    entrada.close();
                }
            }

            if (entrada!=null){ //el usuario escogió una canción
                CSVWriter csvWriter;
                BufferedReader archivocsv;
                String[] datos;
                int largo_de_datos;
                int num_datos;

                String linea;
                try {
                    
                    archivocsv = new BufferedReader(new FileReader(Path));
                    //Se lee primero la linea por que CVSWrite la borra inmediadamente cuando se instancia
                    linea= archivocsv.readLine();
                    csvWriter = new CSVWriter(new FileWriter(Path));
                    datos= linea.split(",", -1);
                    largo_de_datos= datos.length;
                    num_datos= 0;
                    String[] Escribir= new String[largo_de_datos];

                    //Se rescribe la primera linea para no perderla
                    while(num_datos < largo_de_datos) {
                        Escribir[num_datos]= datos[num_datos].replaceAll("\"", "");
                        num_datos++;
                        
                    }
                    csvWriter.writeNext(Escribir);

                    while (true) {
                        linea= archivocsv.readLine();
                        datos= linea.split(",", -1);
                        largo_de_datos = datos.length;
                        num_datos = 0;
                        String[] Escribir2= new String[largo_de_datos];
                        String Biblioteca= datos[0].replaceAll("\"", "");
                        
                        //Se verifica si estamos en la linea deseada para agregar.
                        if (Biblioteca.equals(SeleciónBiblioteca.getSelectedItem())) {
                            break;
                        }

                        while (num_datos < largo_de_datos) {
                            Escribir2[num_datos]= datos[num_datos].replaceAll("\"", "");
                            num_datos++;
                        }                        
                        csvWriter.writeNext(Escribir2);
                    }
                    
                    String [] Escribir3= new String[largo_de_datos+1];
                    
                    //Se rescribe la linea y luego se agrega el datos
                    while (num_datos < largo_de_datos) {
                        Escribir3[num_datos]= datos[num_datos].replaceAll("\"", "");
                            num_datos++;
                        
                        }
                    Escribir3[num_datos]= Nombre_Canción;            
                    csvWriter.writeNext(Escribir3);
                    
                    //Se continua escribirndo la demas lineas para no perderlas.
                    while((linea= archivocsv.readLine()) != null ){
                        datos= linea.split(",", -1);
                        largo_de_datos = datos.length;
                        num_datos = 0;
                        String[] Escribir4= new String[largo_de_datos];

                        while (num_datos < largo_de_datos) {
                            Escribir4[num_datos]= datos[num_datos].replaceAll("\"", "");
                            num_datos++;
                        }                        
                        csvWriter.writeNext(Escribir4);
                    }
                
                    csvWriter.close();
                    archivocsv.close();
                        }
                        catch(Exception ee) {
                            System.out.println("Error al agregar canción");
                        }

                    //Se agraga la canción al ComboBox ya la lista enlazada
                    SeleciónCanción.addItem(Nombre_Canción);
                    Listas_de_Canciones.insertLast(Nombre_Canción);
                        
                    }
                }

        if (btn.getSource() == EliminarCanciónBtn) {
            CSVWriter csvWriter;
            BufferedReader archivocsv;
            String[] datos;
            int largo_de_datos;
            int num_datos;
            String linea;
            try{
                //Se lee primero la linea por que CVSWrite la borra inmediadamente cuando se instancia
                archivocsv = new BufferedReader(new FileReader(Path));
                linea= archivocsv.readLine();
                csvWriter = new CSVWriter(new FileWriter(Path));
                datos= linea.split(",", -1);
                largo_de_datos= datos.length;
                num_datos= 0;
                String[] Escribir= new String[largo_de_datos];

                while(num_datos < largo_de_datos) {
                    Escribir[num_datos]= datos[num_datos].replaceAll("\"", "");
                    num_datos++;
                            
                }
                csvWriter.writeNext(Escribir);

                while (true) {
                    linea= archivocsv.readLine();
                    datos= linea.split(",", -1);
                    largo_de_datos = datos.length;
                    num_datos = 0;
                    String[] Escribir2= new String[largo_de_datos];
                    String Biblioteca= datos[0].replaceAll("\"", "");

                    if (Biblioteca.equals(SeleciónBiblioteca.getSelectedItem())) {
                        break;
                    }

                    while (num_datos < largo_de_datos) {
                        Escribir2[num_datos]= datos[num_datos].replaceAll("\"", "");
                        num_datos++;
                    }                        
                    csvWriter.writeNext(Escribir2);
                }
                        
                String [] Escribir3= new String[largo_de_datos-1];
                
                String valor_a_eliminar;
                Boolean Se_elimino = false;

                while (num_datos < largo_de_datos) {
                    valor_a_eliminar= datos[num_datos].replaceAll("\"", "");
                    if (valor_a_eliminar.equals(SeleciónCanción.getSelectedItem()) && !Se_elimino) {
                        Listas_de_Canciones.Delete(valor_a_eliminar);
                        Se_elimino = true; 

                    } else {
                        if (Se_elimino){
                            Escribir3[num_datos-1] = valor_a_eliminar;
                        } else {
                            Escribir3[num_datos]= valor_a_eliminar;
                        }
                    } 
                    num_datos++; 
                    
                    }       
                csvWriter.writeNext(Escribir3);

                while((linea= archivocsv.readLine()) != null ){
                    datos= linea.split(",", -1);
                    largo_de_datos = datos.length;
                    num_datos = 0;
                    String[] Escribir4= new String[largo_de_datos];
                    while (num_datos < largo_de_datos) {
                            Escribir4[num_datos]= datos[num_datos].replaceAll("\"", "");
                            num_datos++;
                    }                        
                    csvWriter.writeNext(Escribir4);
                }
            
                csvWriter.close();
                archivocsv.close();
                SeleciónCanción.removeItem(SeleciónCanción.getSelectedItem());

            } catch(Exception ee) {
                System.out.println("error");
            }
        }

        if (btn.getSource() == Anteriorbtn) {
           try {
            PlayPrevious();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
          

        }

        if (btn.getSource() == Siguientebtn) {
            try {
                PlayNext();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
        }

        if (btn.getSource() == Statusbtn) {
            Reproductor.Status();
        }

        if (btn.getSource() == DatosBtn) {
            Tabla();
        }
    }

    public static void ContinueMusic() throws Exception {
        Reproductor.Continuar();
    }
    
    public static void StopMusic() throws Exception {
        Reproducción= 3;
        Reproductor.Stop();
    }

    /** 
     * @throws Exception
     */
    public static void PlayNext() throws Exception {
        String Canción= (String) Listas_de_Canciones.GetNext(CancionActual());
        Cambiar(Canción);
        Reproductor.Play(Canción);
        Reproducción= Reproductor.Status();
    }

    public static void PlayPrevious() throws Exception {
        String Canción= (String) Listas_de_Canciones.GetPrevious(CancionActual());
        Cambiar(Canción);
        Reproductor.Play(Canción);
        Reproducción= Reproductor.Status();

    }



    public static boolean EsFavorita() {
        Boolean Valor= false;
        try{
            BufferedReader archivocsv;
            archivocsv = new BufferedReader(new FileReader(Path));
            archivocsv.readLine();
            String linea[];
            linea= archivocsv.readLine().split(",", -1);
            String lista;
            int i = 1;
            while(i <= (linea.length-1)) {
                lista = linea[i].replaceAll("\"", "");
                if(lista.equals(SeleciónCanción.getSelectedItem())) {
                    Valor= true;
                }
                i++;
            }
            archivocsv.close();
        } catch(IOException ex) {
            System.out.println("Error en EsFavorita");
        }
        return Valor;
    }


    
    /** 
     * @return int
     */
    public static int StatusUsuario() {
        return Reproducción;
    }

    
    
    /** 
     * @return int
     */
    public static int Status() {
        return Reproductor.Status();
    }

    
    /** 
     * @return Double_CircularLinkedList
     */
    public static Double_CircularLinkedList ListaActual(){
        
        return Listas_de_Canciones;
    }

    
    /** 
     * @return String
     */
    public static String CancionActual() {
        return (String) SeleciónCanción.getSelectedItem();
    }

    
    /** 
     * @param Canción
     */
    public static void Cambiar(String Canción) {
        SeleciónCanción.setSelectedItem(Canción);
        if(EsFavorita()){
            FavoritaBtn.setSelected(true);

        } else{
            FavoritaBtn.setSelected(false);
        }
    }

    public void BibliotecasDisponibles() {
        BufferedReader archivocsv;
        try {
            SeleciónBiblioteca.removeAllItems();
            archivocsv = new BufferedReader(new FileReader(Path));
            archivocsv.readLine();
            archivocsv.readLine();
            String linea;
            while ((linea= archivocsv.readLine()) != null) {
                String lista[] = linea.split(",", -1);
                SeleciónBiblioteca.addItem(lista[0].replaceAll("\"", ""));
            }
            archivocsv.close();
            CancionesDisponibles();
        } catch (IOException ex) {
            System.out.println("Error en el JComboBox de bibliotecas");
        }
    }

    
    /** 
     * @throws IOException
     */
    public void CancionesDisponibles() throws IOException {
        BufferedReader archivocsv;
        archivocsv = new BufferedReader(new FileReader(Path));
        archivocsv.readLine();
        archivocsv.readLine();
        String linea[];
        String Biblioteca;
        while(true) {
            linea= archivocsv.readLine().split(",", -1);
            Biblioteca = linea[0].replaceAll("\"", "");
            if (Biblioteca.equals(SeleciónBiblioteca.getSelectedItem())) {
                break;
            }
        }
        SeleciónCanción.removeAllItems();
        Listas_de_Canciones.Restart();
        String lista;
        int i = 1;
        while(i <= (linea.length-1)) {
            lista = linea[i].replaceAll("\"", "");
            Listas_de_Canciones.insertLast(lista);
            SeleciónCanción.addItem(lista);
            i++;
        }
        
        archivocsv.close();
    }

    public static void Tabla() {
        String Cancion = (String) SeleciónCanción.getSelectedItem();
        XMLCreator Datos= new XMLCreator();
        Datos.CrearXML(Cancion.intern());

        JFrame TablaVentana= new JFrame();
        TablaVentana.setBounds(0, 0, 750, 500);
        TablaVentana.setVisible(true);
        TablaVentana.setTitle("Datos de la canción");

        String[] Nombre_Columas = {"Genero", "Artista", "Album", "Año", "Letra"};
        JTable Tabla = new JTable();
        DefaultTableModel Modelo = new DefaultTableModel();
        JScrollPane Desplazamiento = new JScrollPane(Tabla);

        Modelo.setColumnIdentifiers(Nombre_Columas);
        Desplazamiento.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Desplazamiento.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        Tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        Tabla.setFillsViewportHeight(true);
        Tabla.setModel(Modelo);

        Modelo.setRowCount(0);

        String [] DatosDeTabla = {Datos.Genero(), Datos.Artista(), Datos.Almbun(), Datos.Año(), Datos.Letra()};
        Modelo.addRow(DatosDeTabla);

        TablaVentana.getContentPane().add(Desplazamiento, BorderLayout.NORTH);
        TablaVentana.pack();
    }

    
    /** 
     * @param Archivo
     * @throws IOException
     */
    public static void VentanaInicio(String Archivo) throws IOException {
        Path= Archivo;
        Main ReproductorVentana = new Main();
        ReproductorVentana.setBounds(0, 0, 750, 500); //Tamaño provicional, posiblemente cambie
        ReproductorVentana.setVisible(true);
        ReproductorVentana.setTitle("CE MP3");
        ReproductorVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
    /** 
     * @param args
     */
    //Main Method
    public static void main(String[] args){
        /*try {
            VentanaInicio();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        new Credentials();
    }
}
