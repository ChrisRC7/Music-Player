package StartWindow.Main;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import StartWindow.Usuarios.Credentials;
import com.opencsv.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.*;

//import java.io.ObjectOutputStream.PutField;
//import StartWindow.ListasEnlazadas.*;
//import panamahitek.Arduino.PanamaHitek_Arduino;

import StartWindow.ListasEnlazadas.Double_CircularLinkedList;
import StartWindow.Reproductor.*;

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
  
    JButton Playbtn, Pausebtn, Continuebtn, Stopbtn, AgregarCanciónBtn, EliminarCanciónBtn, 
    AgregarBibliotecaBtn, EliminarBibliotecaBtn, Anteriorbtn, Siguientebtn, Favorita, Statusbtn;

    public Main() throws IOException{

        /*try {
            conexion.arduinoTX("COM3",9600);
        } catch (ArduinoExcepcion e) {
             throw new RuntimeException(e);
        }*/

        setLayout(null);

        Favorita= new JButton("Fav");
        Favorita.setBounds(120, 85, 70, 50);
        add(Favorita);
        Favorita.addActionListener(this);

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

        JPanel ContenedorVolume= new JPanel();
        ContenedorVolume.setBounds(10, 210, 450, 90);
        ContenedorVolume.add(Volume);
        add(ContenedorVolume);

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
        //CancionesDisponibles();
        
        
        //add(SeleciónCanción);
        //SeleciónCanción.addActionListener(this);

        
        
    }
 
    @Override
    public void actionPerformed(ActionEvent btn) {

        if (btn.getSource() == AgregarBibliotecaBtn) {
            CSVWriter csvWriter;
            BufferedReader archivocsv;

            try {
                archivocsv = new BufferedReader(new FileReader("SampleProject4Git/src/StartWindow/Usuarios/Usuarios.csv"));
                int NumDeLinea = -2;
                String linea;
                while ((linea= archivocsv.readLine()) != null) {
                    NumDeLinea++;
                 }
                String [] NuevaLinea= new String[1];
                NuevaLinea[0] = "Biblioteca "+NumDeLinea;
                csvWriter = new CSVWriter(new FileWriter("SampleProject4Git/src/StartWindow/Usuarios/Usuarios.csv", true));
                csvWriter.writeNext(NuevaLinea);
                csvWriter.close();
                archivocsv.close();

                BibliotecasDisponibles();
         }
         catch(Exception ee) {
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
                Reproductor.Continuar();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (btn.getSource() == Stopbtn) {
            try {
                Reproducción= 3;
                Reproductor.Stop();
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
                    
                    archivocsv = new BufferedReader(new FileReader("SampleProject4Git/src/StartWindow/Usuarios/Usuarios.csv"));
                    linea= archivocsv.readLine();
                    csvWriter = new CSVWriter(new FileWriter("SampleProject4Git/src/StartWindow/Usuarios/Usuarios.csv"));
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
                    
                    String [] Escribir3= new String[largo_de_datos+1];
                    
                    while (num_datos < largo_de_datos) {
                        Escribir3[num_datos]= datos[num_datos].replaceAll("\"", "");
                            num_datos++;
                        
                        }
                    Escribir3[num_datos]= Nombre_Canción;            
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
                        }
                        catch(Exception ee) {
                            System.out.println("error");
                        }

                    try {
                        CancionesDisponibles();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                        
                    }
                }

        if (btn.getSource() == Anteriorbtn) {
            String Canción= (String) Listas_de_Canciones.GetPrevious(CancionActual());
            Cambiar(Canción);
            
            try {
                Reproductor.Play(Canción);
                Reproducción= Reproductor.Status();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
          

        }

        if (btn.getSource() == Siguientebtn) {
            String Canción= (String) Listas_de_Canciones.GetNext(CancionActual());
            Cambiar(Canción);
            
            try {
                Reproductor.Play(Canción);
                Reproducción= Reproductor.Status();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
           
        }

        if (btn.getSource()== Statusbtn) {
            Reproductor.Status();
        }
    }

    public static void PlayNext() throws Exception {
        String Canción= (String) Listas_de_Canciones.GetNext(CancionActual());
        Cambiar(Canción);
        Reproductor.Play(Canción);
    }


    public static int StatusUsuario() {
        return Reproducción;
    }

    
    public static int Status() {
        return Reproductor.Status();
    }

    public static Double_CircularLinkedList ListaActual(){
        
        return Listas_de_Canciones;
    }

    public static String CancionActual() {
        return (String) SeleciónCanción.getSelectedItem();
    }

    public static void Cambiar(String Canción) {
        SeleciónCanción.setSelectedItem(Canción);
    }

    public void BibliotecasDisponibles() {
        BufferedReader archivocsv;
        try {
            SeleciónBiblioteca.removeAllItems();
            archivocsv = new BufferedReader(new FileReader("SampleProject4Git/src/StartWindow/Usuarios/Usuarios.csv"));
            archivocsv.readLine();
            archivocsv.readLine();
            String linea;
            while ((linea= archivocsv.readLine()) != null) {
                String lista[] = linea.split(",", -1);
                SeleciónBiblioteca.addItem(lista[0].replaceAll("\"", ""));
            }
            archivocsv.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException ex) {
            System.out.println("Error");
        }
    }

    public void CancionesDisponibles() throws IOException {
        BufferedReader archivocsv;
        archivocsv = new BufferedReader(new FileReader("SampleProject4Git/src/StartWindow/Usuarios/Usuarios.csv"));
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
        System.out.println("siguiente while");
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

    public static void VentanaInicio() throws IOException {
        Main ReproductorVentana = new Main();
                ReproductorVentana.setBounds(0, 0, 750, 500); //Tamaño provicional, posiblemente cambie
                ReproductorVentana.setVisible(true);
                ReproductorVentana.setTitle("El mp3 con la tula mas grande que hay");
                ReproductorVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //Main Method
    public static void main(String[] args){
        try {
            VentanaInicio();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //new Credentials();
    }
}
