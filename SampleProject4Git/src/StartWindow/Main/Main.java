package StartWindow.Main;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;
import StartWindow.ListasEnlazadas.Double_CircularLinkedList;
//import java.io.ObjectOutputStream.PutField;
//import StartWindow.ListasEnlazadas.*;
import StartWindow.Reproductor.*;


public class Main extends JFrame implements ActionListener {
    static Double_CircularLinkedList Listas_de_Canciones= new Double_CircularLinkedList();
    static Reproducir_Musica Reproductor= new Reproducir_Musica();
    
    String Nombre_Canción, ruta;
    private static JComboBox<String> SeleciónCanción= new JComboBox<String>();
  
    JButton Playbtn, Pausebtn, Continuebtn, Stopbtn, AgregarBtn, Anteriorbtn, Siguientebtn, Play2btn, Statusbtn;
    public Main() throws IOException{
        setLayout(null);
        Playbtn= new JButton("<html>Play<html>");
        Playbtn.setBounds(155, 100, 60, 50);
        add(Playbtn);
        Playbtn.addActionListener(this);

        Pausebtn= new JButton("<html>Pause<html>");
        Pausebtn.setBounds(215, 100, 65, 50);
        add(Pausebtn);
        Pausebtn.addActionListener(this);

        Continuebtn= new JButton("<html>Resume<html>");
        Continuebtn.setBounds(280, 100, 80, 50);
        add(Continuebtn);
        Continuebtn.addActionListener(this);

        Stopbtn= new JButton("<html>Stop<html>");
        Stopbtn.setBounds(20, 100, 60, 50);
        add(Stopbtn);
        Stopbtn.addActionListener(this);

        AgregarBtn= new JButton("Agregar Canción");
        AgregarBtn.setBounds(120, 20, 150, 50);
        add(AgregarBtn);
        AgregarBtn.addActionListener(this);

        Siguientebtn= new JButton("<html>Next Music<html>");
        Siguientebtn.setBounds(360, 100, 75, 50);
        add(Siguientebtn);
        Siguientebtn.addActionListener(this);

        Anteriorbtn= new JButton("<html>Previous Music<html>");
        Anteriorbtn.setBounds(80, 100, 75, 50);
        add(Anteriorbtn);
        Anteriorbtn.addActionListener(this);

        Statusbtn= new JButton("Status");
        Statusbtn.setBounds(200, 190, 70, 50);
        add(Statusbtn);
        Statusbtn.addActionListener(this);
        
        Play2btn= new JButton("Play2");
        Play2btn.setBounds(100, 300, 70, 50);
        add(Play2btn);
        Play2btn.addActionListener(this);

        JLabel Canciona_Selecionada = new JLabel("Sin selecionar");
        Canciona_Selecionada.setVerticalAlignment(JLabel.TOP);
        Canciona_Selecionada.setHorizontalAlignment(JLabel.CENTER);
        add(Canciona_Selecionada);

        CancionesDisponibles();
   
        //JLabel Label2= new JLabel("<html>Selecciona la canción<html>");
        //Label2.setBounds(0,0 ,100 ,100);
        SeleciónCanción.setBounds(10,10,80,20);
        add(SeleciónCanción);
        SeleciónCanción.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent btn) {

        if (btn.getSource()==Playbtn) {
            try {
                Reproductor.Reprodución_Continua(CancionActual(), Listas_de_Canciones);
            } catch (Exception ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
        }

        if (btn.getSource() == Pausebtn) {
            try {
                Reproductor.Pausa();
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
                Reproductor.Stop();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (btn.getSource() == AgregarBtn) {
            
            Scanner entrada = null;
        JFileChooser Escoger_Canción = new JFileChooser();
        Escoger_Canción.showOpenDialog(Escoger_Canción);
        try {
            ruta = Escoger_Canción.getSelectedFile().getAbsolutePath();                                        
            File archivo = new File(ruta);
            Nombre_Canción= archivo.getName();
            entrada = new Scanner(archivo);
            while (entrada.hasNext()) {
                System.out.println(entrada.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("No se ha seleccionado ningún fichero");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (entrada != null) {
                entrada.close();
            }
        }

        if (entrada!=null){
            CSVWriter csvWriter;
            BufferedReader archivocsv;
            try {
                
                archivocsv = new BufferedReader(new FileReader("SampleProject4Git/src/StartWindow/Usuarios/Usuarios.csv"));
                String linea= archivocsv.readLine();
                String[] datos= linea.split(",", -1);
                int largo_de_datos = datos.length; 
                int num_datos = 0;
                String[] Escribir= new String[largo_de_datos];
                csvWriter = new CSVWriter(new FileWriter("SampleProject4Git/src/StartWindow/Usuarios/Usuarios.csv"));

                while (num_datos < largo_de_datos) {
                    Escribir[num_datos]= datos[num_datos].replaceAll("\"", "");
                    num_datos++;
                }
                csvWriter.writeNext(Escribir);
                linea= archivocsv.readLine();
                if(linea!=null) {
                    datos= linea.split(",", -1);
                    largo_de_datos = datos.length;
                    num_datos = 0; 
                } else {
                    largo_de_datos=0;
                }
                
                Escribir= new String[largo_de_datos+1];
                if (largo_de_datos!= 0) {
                        while (num_datos < largo_de_datos) {
                            Escribir[num_datos]= datos[num_datos].replaceAll("\"", "");
                            num_datos++;
                     
                        }
                        Escribir[num_datos]= Nombre_Canción;
                        
                }else {
                    Escribir[largo_de_datos]= Nombre_Canción;
                }
        
                csvWriter.writeNext(Escribir);
                
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
            SeleciónCanción.setSelectedItem(Canción);
            
            try {
                Reproductor.Play(Canción);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
          

        }

        if (btn.getSource() == Siguientebtn) {
            String Canción= (String) Listas_de_Canciones.GetNext(CancionActual());
            SeleciónCanción.setSelectedItem(Canción);
            
            try {
                Reproductor.Play(Canción);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
           
        }

        

        if (btn.getSource() == Play2btn) {
            try {
                Reproductor.Play2();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (btn.getSource()== Statusbtn) {
            Reproductor.Status();
        }
    }
    public static void PlayNext() throws Exception {
        String Canción= (String) Listas_de_Canciones.GetNext(CancionActual());
        System.out.println(CancionActual());
        System.out.println(Canción);

        Reproductor.Play(Canción);
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

    public static void Cambiar() {
        String Canción= (String) Listas_de_Canciones.GetNext(CancionActual());
        SeleciónCanción.setSelectedItem(Canción);
    }
    

    public void CancionesDisponibles() throws IOException {
        BufferedReader archivocsv;
        String linea[];
        archivocsv = new BufferedReader(new FileReader("SampleProject4Git/src/StartWindow/Usuarios/Usuarios.csv"));
        archivocsv.readLine();
        SeleciónCanción.removeAllItems();
        linea= archivocsv.readLine().split(",", -1);
        String lista;
        int i = 0;
        Listas_de_Canciones.Restart();
        while(i< linea.length) {
            lista = linea[i].replaceAll("\"", "");
            Listas_de_Canciones.insertLast(lista);
            SeleciónCanción.addItem(lista);
            i++;
        }
        
        archivocsv.close();
    }

    public static void VentanaInicio() throws IOException {
        Main ReproductorVentana = new Main();
                ReproductorVentana.setBounds(0, 0, 500, 500); //Tamaño provicional, posiblemente cambie
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
