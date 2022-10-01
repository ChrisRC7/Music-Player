package StartWindow.XmlControladores;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;


public class XMLCreator {
    
    private static String[] MetaDatos = new String[5];

    
    /** 
     * @param Cancion
     */
    public void CrearXML(String Cancion){

        File carpeta= new File("Canciones");
        String [] ListaCanciones= carpeta.list();
        int CantidadElementos= 0;

        while (CantidadElementos< ListaCanciones.length) {
            CantidadElementos++;
        }

        try{
            
            DocumentBuilderFactory Factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder Builder = Factory.newDocumentBuilder();
            DOMImplementation Implementacion = Builder.getDOMImplementation();

            Document Documento= Implementacion.createDocument(null, "Mp3info", null);
            Documento.setXmlVersion("1.0");

            Element MP3 = Documento.createElement("MP3");

            String TipoGenero, ElArtista, ElAlbum, ElAño, LaLetra;
            for (int i = 0; i < CantidadElementos; i++) {
                
                String CancionActual= ListaCanciones[i];

                File file = new File("Canciones/"+CancionActual);
                AudioFile Audio= AudioFileIO.read(file);
                Tag tags= Audio.getTag();

                TipoGenero= tags.getFirst(FieldKey.GENRE);
                ElArtista= tags.getFirst(FieldKey.ARTIST);
                ElAlbum= tags.getFirst(FieldKey.ALBUM);
                ElAño= tags.getFirst(FieldKey.YEAR);
                LaLetra= tags.getFirst(FieldKey.COMMENT);

            
                Element Canción = Documento.createElement(CancionActual);

                Element Genero= Documento.createElement("Genero");
                Text textGenero= Documento.createTextNode(TipoGenero);
                Genero.appendChild(textGenero);
                Canción.appendChild(Genero);

                Element Artista= Documento.createElement("Artista");
                Text textArtista= Documento.createTextNode(ElArtista);
                Artista.appendChild(textArtista);
                Canción.appendChild(Artista);

                Element Album= Documento.createElement("Album");
                Text textAlbum= Documento.createTextNode(ElAlbum);
                Album.appendChild(textAlbum);
                Canción.appendChild(Album);

                Element Año= Documento.createElement("Año");
                Text textAño= Documento.createTextNode(ElAño);
                Año.appendChild(textAño);
                Canción.appendChild(Año);

                Element Letra= Documento.createElement("Letra");
                Text textLetra= Documento.createTextNode(LaLetra);
                Letra.appendChild(textLetra);
                Canción.appendChild(Letra);

                MP3.appendChild(Canción);
            }
        
            Documento.getDocumentElement().appendChild(MP3);

            Source source = new DOMSource(Documento);
            Result result = new StreamResult(new File("Mp3info.xml"));

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);

        }catch(ParserConfigurationException | TransformerException | CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException ex){

            System.out.println(ex.getMessage());
        }
        Lector(Cancion);
        }

    
    /** 
     * @param Nombre
     */
    public static void Lector(String Nombre) {

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document documento = builder.parse(new File("Mp3info.xml"));

            NodeList Cancion = documento.getElementsByTagName(Nombre);

            Node node = Cancion.item(0);
            Element Indicador= (Element) node;
            NodeList Hijos= Indicador.getChildNodes();
            for (int i = 0; i < Hijos.getLength(); i++) {
                Node Datos= Hijos.item(i);
                Element texto = (Element) Datos;
                MetaDatos[i] = texto.getTextContent();

                
            }

        }catch(ParserConfigurationException |SAXException | IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    
    /** 
     * @return String
     */
    public String Genero(){
        return MetaDatos[0];
    }

    
    /** 
     * @return String
     */
    public String Artista(){
        return MetaDatos[1];
    }

    
    /** 
     * @return String
     */
    public String Almbun(){
        return MetaDatos[2];
    }

    
    /** 
     * @return String
     */
    public String Año(){
        return MetaDatos[3];
    }

    
    /** 
     * @return String
     */
    public String Letra(){
        return MetaDatos[4];
    }
}
