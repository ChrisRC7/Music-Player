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



   
    public void run()  {
        Estado= true;
        while (Estado) {
            if (Main.Status()==3) {
            Estado= false;
            }
            if (Main.Status()==2) {
                try {
                    Main.PlayNext();
                    System.out.println("Cambio Auto");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }
    
}
