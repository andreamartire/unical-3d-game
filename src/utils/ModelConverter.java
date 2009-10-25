package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.management.MalformedObjectNameException;

import jmetest.renderer.loader.TestX3DLoading;

import com.jme.util.export.Savable;
import com.jme.util.export.binary.BinaryExporter;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.converters.AseToJme;
import com.jmex.model.converters.FormatConverter;
import com.jmex.model.converters.MaxToJme;
import com.jmex.model.converters.Md2ToJme;
import com.jmex.model.converters.Md3ToJme;
import com.jmex.model.converters.ObjToJme;

/** Classe che permette di convertire modelli 3d nel formato binario jme. <br>
 * Modelli supportati: 3ds, md2, md3, obj, ase.<br>
 * Esempio d'uso:<br>
 * <code>ModelConverter.convert( "model.3ds", "model.jme" )
 * </code>
 * 
 * @param inputFilePath -> (String) il path del modello 3d da convertire
 * @param outFilePath -> (String) il path del file .jme da creare
 */
public class ModelConverter {

    static final Logger logger = Logger.getLogger(TestX3DLoading.class.getName());
	static FormatConverter converter;

	/** Funzione che permette di convertire un modello 3d nel formato binario jme. <br>
	 * Modelli supportati: 3ds, md2, md3, obj, ase.<br>
	 * Esempio d'uso:<br>
	 * <code>ModelConverter.convert( "model.3ds", "model.jme" )
	 * </code>
	 * 
	 * @param inputFilePath -> (String) il path del modello 3d da convertire
	 * @param outFilePath -> (String) il path del file .jme da creare
	 */
	public static void convert( String inputFilePath, String outFilePath ){

		Savable model = null;
		converter = null;

		try {			
			if( ! Pattern.matches(".+\\.(?i)jme", outFilePath) )
				throw new MalformedObjectNameException();
			
			if ( Pattern.matches(".+\\.(?i)md3", inputFilePath) ) {
				logger.info( "Converting md3 file..." );
				converter = new Md3ToJme();
			}	
			else if ( Pattern.matches(".+\\.(?i)md2", inputFilePath) ) {
				logger.info( "Converting md2 file..." );
				converter =  new Md2ToJme();
			}	
			else if ( Pattern.matches(".+\\.(?i)3ds", inputFilePath) ) {
				logger.info( "Converting 3ds file..." );
				converter = new MaxToJme();
			}	
			else if ( Pattern.matches(".+\\.(?i)obj", inputFilePath) ) {
				logger.info( "Converting obj file..." );
				converter = new ObjToJme();
			}	
			else if ( Pattern.matches(".+\\.(?i)ase", inputFilePath) ) {
				logger.info( "Converting ase file..." );
				converter = new AseToJme();
			}	
			else {
				/* 	if the modelPath doesen't match with any of those regular expressions, 
				 *  the model type are not supported by the converter (es: .fbx) or the 
				 *  extension was not correctly written (es: .33ds)
				 */
				throw new MalformedObjectNameException();
			}
			
			/* model Converting */
			
			URL modelUrl = ModelLoader.class.getClassLoader().getResource( inputFilePath );
			
			/* convert the 3d model with the right converter */
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			if( converter instanceof ObjToJme )
				converter.setProperty( "mtllib", modelUrl );
			converter.convert( modelUrl.openStream(), outStream );
			
			/* write the buffer to a file */
			model = BinaryImporter.getInstance().load( new ByteArrayInputStream( outStream.toByteArray() ) );
			BinaryExporter.getInstance().save( model, new File( "src/" + outFilePath ) );

			logger.info( "Model converted. Created file: " + outFilePath );
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			logger.log( Level.SEVERE, "The model you are trying to convert doesen't exist" +
									  "\nMake sure you entered the correct path" );
			System.exit(0);
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
			logger.log( Level.SEVERE, "You are trying to convert an unsupported model\n");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			logger.log( Level.SEVERE, "Conversion Failed\n" );
			System.exit(0);
		} 
	}

}