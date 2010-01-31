package game.common;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/** Class GameSettings used to read the xml configuration file<br>
 * 
 * Needed library: <a href="http://www.jdom.org">JDOM</a>
 * 
 * @author Giuseppe Leone 
 */
public class GameConfiguration {
	
	private final static String SETTINGS_FILE = "src/game/data/game.conf";
	
	/** the xml document */
	static Document document;
	
	/** the root of the xml file */
	static Element root;
	
	/**
	 * Default Values
	 */
	static final LinkedHashMap< String, String > defaultSettings = new LinkedHashMap<String, String>();
	static {
		defaultSettings.put( "forward_key", "W" );
		defaultSettings.put( "backward_key", "S" );
		defaultSettings.put( "turnright_key", "D" );
		defaultSettings.put( "turnleft_key", "A" );
		defaultSettings.put( "run_key", "LSHIFT" ); 
		defaultSettings.put( "pause_key", "P" ); 
		
		// screen
		defaultSettings.put( "resolution_width", "1024" );
		defaultSettings.put( "resolution_height", "768" );
		defaultSettings.put( "resolution_depth", "16" );
		defaultSettings.put( "resolution_frequency", "-1" );
		defaultSettings.put( "is_fullscreen", "false" );
		
		// Sound
		defaultSettings.put( "sound_enabled", "false" );
		
		//XML path scene file
		defaultSettings.put( "scene_file_path", "src/game/data/level/island.xml" );
	}
	
	/**
	 * User defined values
	 */
	static LinkedHashMap< String, String > settings;

	static List<EnemyInfo> enemies;
	
	static LinkedHashMap< String, String > parameters;
	
	static LinkedHashMap< String, String > phrases;
	
	
	public static void init() {
		settings = new LinkedHashMap< String, String >();
		settings.putAll(defaultSettings);
		
		enemies = new LinkedList< EnemyInfo >();
		parameters = new LinkedHashMap< String, String >();
		phrases = new LinkedHashMap< String, String >();
		
		load();
		
		/* 
		 * Change depth and frequency according to the operating system in use
		 */
		if( System.getProperties().getProperty("os.name").equals( "Linux" ) ) {
			settings.put( "resolution_depth", "24" );
			settings.put( "resolution_frequency", "50" );
		} else {
			settings.put( "resolution_depth", "32" );
			settings.put( "resolution_frequency", "60" );
		}
	}
	
	/**
	 * Load user defined values from XML file setting 
	 */
	@SuppressWarnings("unchecked")
	public static void load() {
		SAXBuilder builder = new SAXBuilder();
		
		/* Try to load user defined settings*/
		try {
			document = builder.build( new File( SETTINGS_FILE ) );
			root = document.getRootElement();
			
			for( Iterator<Element> it = root.getChild("settings").getChildren().iterator(); it.hasNext();  ) {
				Element element = it.next();
				settings.put( element.getAttributeValue("name"), element.getText() );
			}
			
			List<Element> enemy_list = root.getChild("enemies").getChildren();
			for( Element enemy : enemy_list ) {
				EnemyInfo enemyInfo = new EnemyInfo();
						
				enemyInfo.setPosX( Float.valueOf( enemy.getAttributeValue("x") ) );
				
				enemyInfo.setPosZ( Float.valueOf( enemy.getAttributeValue("z") ) );
				
				enemyInfo.setState( State.toState( enemy.getAttributeValue("state") ) );
				
				//load path's points
				PointPath pointPath = new PointPath();
				
				List<Element> point_list = enemy.getChildren();
				for( Element pointInfo : point_list ) {
					int x = Integer.valueOf( pointInfo.getAttributeValue("x") );
					int z = Integer.valueOf( pointInfo.getAttributeValue("z") );
					Point point = new Point( x, z );
					pointPath.add(point);
				}
				enemyInfo.setMovements( pointPath.generateMovementsList() );
				
				enemies.add( enemyInfo );
			}
			
			/**
			 * Load all parameters
			 */
			List<Element> parameter_list = root.getChild("parameters").getChildren();
			for( Element param : parameter_list ) {
				String key = param.getAttributeValue("name");
				String value = param.getAttributeValue("value");
				
				parameters.put( key, value );
			}
			
			/**
			 * Load all phrases
			 */
			List<Element> phrase_list = root.getChild("phrases").getChildren();
			for( Element phrase : phrase_list ) {
				String key = phrase.getAttributeValue("name");
				String value = phrase.getAttributeValue("value");
				
				phrases.put( key, value );
			}
			
		} catch (Exception e) {
			Logger.getAnonymousLogger().log( Level.SEVERE, 
					"Error loading the configuration file, make sure the path is correct." +
					"\nDefault configuration will be loaded" );
		}
	}
	
	/**
	 * Store the user defined settings into the XML file
	 */
	@SuppressWarnings("unchecked")
	public static void save() {
		List<Element> settingsList = root.getChild("settings").getChildren();
		
		for( Element setting : settingsList ) {
			setting.setText( settings.get( setting.getAttributeValue("name") ) );
		}
		XMLOutputter outputter = new XMLOutputter();
		outputter.setFormat( Format.getPrettyFormat() );
		
		try {
			outputter.output( document, new FileOutputStream( SETTINGS_FILE ) );
		} catch (Exception e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).severe("Error saving game configuration");
		} 
	}
	
	public static String getBackwardKey() {
		return settings.get( "backward_key" );
	}

	public static String getDefaultBackwardKey() {
		return defaultSettings.get( "backward_key" );
	}

	public static String getDefaultForwardKey() {
		return defaultSettings.get( "forward_key" );
	}

	public static String getDefaultIsFullscreen() {
		return defaultSettings.get( "is_fullscreen" );
	}

	public static String getDefaultPauseKey() {
		return defaultSettings.get( "pause_key" );
	}

	public static String getDefaultResolutionHeight() {
		return defaultSettings.get( "resolution_height" );
	}

	public static String getDefaultResolutionWidth() {
		return defaultSettings.get( "resolution_width" );
	}

	public static String getDefaultRunKey() {
		return defaultSettings.get( "run_key" );
	}

	public static String getDefaultSoundEnabled() {
		return defaultSettings.get( "sound_enabled" );
	}

	public static String getDefaultTurnLeftKey() {
		return defaultSettings.get( "turnleft_key" );
	}

	public static String getDefaultTurnRightKey() {
		return defaultSettings.get( "turnright_key" );
	}

	public static String getForwardKey() {
		return settings.get( "forward_key" );
	}

	public static String getPauseKey() {
		return settings.get( "pause_key" );
	}

	public static String getResolutionHeight() {
		return settings.get( "resolution_height" );
	}

	public static String getResolutionWidth() {
		return settings.get( "resolution_width" );
	}

	public static String getRunKey() {
		return settings.get( "run_key" );
	}

	public static String getTurnLeftKey() {
		return settings.get( "turnleft_key" );
	}

	public static String getTurnRightKey() {
		return settings.get( "turnright_key" );
	}

	public static String isFullscreen() {
		return settings.get( "is_fullscreen" );
	}

	public static String isSoundEnabled() {
		return settings.get( "sound_enabled" );
	}

	public static void setBackwardKey( String value ) {
		settings.put( "backward_key", value );
	}

	public static void setForwardKey(String value) {
		settings.put( "forward_key", value );
	}

	public static void setFullscreen(String value) {
		settings.put( "is_fullscreen", value );
	}

	public static void setPauseKey(String value) {
		settings.put( "pause_key", value );
	}

	public static void setResolutionHeight(String value) {
		settings.put( "resolution_height", value );
	}

	public static void setResolutionWidth(String value) {
		settings.put( "resolution_width", value );
	}

	public static void setRunKey(String value) {
		settings.put( "run_key", value );
	}

	public static void setSoundEnabled(String value) {
		settings.put( "sound_enabled", value );
	}

	public static void setTurnLeftKey(String value) {
		settings.put( "turnleft_key", value );
	}

	public static void setTurnRightKey(String value) {
		settings.put( "turnright_key", value );
	}

	public static String getDefaultResolutionDepth() {
		return defaultSettings.get( "resolution_depth" );
	}

	public static String getDefaultResolutionFrequency() {
		return defaultSettings.get( "resolution_frequency" );
	}

	public static String getResolutionDepth() {
		return settings.get( "resolution_depth" );
	}

	public static String getResolutionFrequency() {
		return settings.get( "resolution_frequency" );
	}

	public static void setResolutionDepth(String value) {
		settings.put("resolution_depth", value );
	}

	public static void setResolutionFrequency(String value) {
		settings.put("resolution_frequency", value );
	}
	
	public static String getDefaultSceneFilePath() {
		return defaultSettings.get( "scene_file_path" );
	}
	
	public static String getSceneFilePath() {
		return settings.get( "scene_file_path" );
	}
	
	public static void setSceneFilePath( String path ) {
		settings.put( "scene_file_path", path );
	}
	
	public static List<EnemyInfo> getEmemiesInfoList() {
		return enemies;
	}
	
	public static String getParameter( String param ) {
		return parameters.get( param );
	}
	
	public static String getPhrase( String phrase ) {
		return phrases.get( phrase );
	}
	
	/** Utility class used to store some informations 
	 * for the creation of enemies
	 */
	public static class EnemyInfo {
		float posX;
		float posZ;
		State state;
		LinkedList<Movement> movements;
		
		public void setPosX(float posX) {
			this.posX = posX;
		}
		public float getPosX() {
			return posX;
		}
		
		public void setPosZ(float posZ) {
			this.posZ = posZ;
		}
		
		public float getPosZ() {
			return posZ;
		}
		
		public void setState(State state) {
			this.state = state;
		}
		
		public State getState() {
			return state;
		}
		
		public void setMovements(LinkedList<Movement> movements) {
			this.movements = movements;
		}
		
		public LinkedList<Movement> getMovements() {
			return movements;
		}
	}
}