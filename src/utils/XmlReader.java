package utils;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.jme.math.Vector3f;

/** Classe di test per la lettura del file xml generato da FreeWorld3D<br>
 * In questo test leggo e stampo a video la posizione di ogni mesh caricata in FW3D.
 * Come potete immaginare � la base del sistema di caricamento della scena da file xml.
 * In modo del tutto simile si pu� leggere ovviamente qualsiasi file xml, lo useremo anche
 * per il nostro game.cfg contenente tutti i parametri di gioco.
 * 
 * Libreria necessaria: <a href="http://www.jdom.org">JDOM</a>
 * 
 * @author slash17
 */
public class XmlReader {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			/* creo un builder attraverso cui carico il file xml in un oggetto Document */
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build( new File("src/utils/world.cfg") );
			
			/* salvo l'elemento root del file xml */
			Element root = doc.getRootElement();
			
			/* prendo l'elemento SceneLayer figlio di SceneLayers, a sua volta figlio di root
			 * in SceneLayer FW3D mette le informazioni sui modelli 3d, posizione, rotazione, scala
			 */
			Element sceneLayer = root.getChild("SceneLayers").getChild("SceneLayer");
			
			/* prendo tutti gli elementi figli di SceneLayer (ovvero i modelli 3d) */
			List<Element> children = sceneLayer.getChildren();
			Iterator<Element> it = children.iterator();
			
			while( it.hasNext() ) {
				/* salvo l'elemento corrente */
				Element curr = it.next();
				System.out.println( "filename = " + curr.getAttributeValue("Name") );
				
				/* salvo la sua posizione prendendo il valore dell'attributo Position */
				String positionString = curr.getAttributeValue("Position");
				
				/* la posizione salvata nell'xml � una stringa dal formato (x,y,z) 
				 * quindi la "splitto" per virgole ottendo un array di 3 elementi
				 */
				String[] positionArray = positionString.split(",");
				
				Vector3f position = new Vector3f();
				
				/* ottengo i 3 float da mettere nel mio vector3f convertendo le sottostringhe ottenute dallo split */
				position.x = Float.valueOf( positionArray[0] );
				position.y = Float.valueOf( positionArray[1] );
				position.z = Float.valueOf( positionArray[2] );
				
				/* stampo felicemente la posizione */
				System.out.println( "position = " + position.toString() );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
