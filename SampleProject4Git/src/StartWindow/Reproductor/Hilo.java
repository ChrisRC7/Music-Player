package StartWindow.Reproductor;
import StartWindow.ListasEnlazadas.Double_CircularLinkedList;
import StartWindow.Main.Main;
//import StartWindow.Reproductor.Reproducir_Musica;


public class Hilo extends Thread {
    Boolean Estado;
    Double_CircularLinkedList Canciones;
    Reproducir_Musica player ;

    public Hilo(String msg, Double_CircularLinkedList Lista) {
        super(msg);
      
    }

    public void Stop() {
        Estado= false;
    }

   
    public void run()  {
        Estado= true;
        int i=0;
        while (Estado) {
            if (Main.Status()==2) {
                try {
                    Main.PlayNext();
                    Main.Cambiar();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }
    
}
