package StartWindow.Reproductor;
import javazoom.jlgui.basicplayer.BasicPlayer;


import java.io.File;

import StartWindow.ListasEnlazadas.Double_CircularLinkedList;

public class Reproducir_Musica {
    Boolean Estado;
    Thread hilo1;

    private  BasicPlayer player = new BasicPlayer();
    
    
    
    /** 
     * @param Canción
     * @throws Exception
     */
    public void Play(String Canción) throws Exception{
        Stop();
        player.open(new File("Canciones/"+Canción));
        player.play();
        
    }

    
    /** 
     * @param Canción
     * @param Lista
     * @throws Exception
     */
    public void Reprodución_Continua(String Canción, Double_CircularLinkedList Lista) throws Exception {

                Stop();
                player.open(new File("Canciones/"+Canción));
                Play(Canción);
                hilo1 = new Hilo("Canciones", Lista);
                hilo1.start();
        
    }

    
    /** 
     * @return int
     */
    public int Status(){
        return player.getStatus();
    }

    
    /** 
     * @param ruta
     * @throws Exception
     */
    public void EscogerMusica(String ruta) throws Exception{
        player.open(new File(ruta));
    }

    
    /** 
     * @throws Exception
     */
    public void Pausa() throws Exception{
        player.pause();
    }

    
    /** 
     * @throws Exception
     */
    public void Continuar() throws Exception{
        player.resume();
    }

    
    /** 
     * @param Volume
     * @throws Exception
     */
    public void ChangeVolume(Double Volume) throws Exception {
        player.setGain(Volume);
    }

    
    /** 
     * @throws Exception
     */
    public void Stop() throws Exception{
        player.stop();
    }

}
