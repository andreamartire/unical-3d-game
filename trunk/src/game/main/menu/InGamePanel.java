package game.main.menu;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class GamePanel
 * 
 * @author Andrea Martire, Salvatore Loria, Giuseppe Leone
 */
public class InGamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/** current selected item */
	public int current = 0;
	
	/** items of game menu */
	ArrayList<JLabel> item;
	
	/** images of every items */
	ArrayList<String> imageFolder;
	
	/** Pointer to inGame Menu (owner this panel)*/
	InGameMenu inGameMenu;
	
	/** Pointer to Main Menu */
	MainMenu mainMenu;
	
	/**
	 * Constructor
	 * 
	 * @param inGameMenu - Game Menu
	 * @param mainMenu - Main Menu
	 */
	public InGamePanel( InGameMenu inGameMenu, MainMenu mainMenu ) {
		super();
		this.inGameMenu = inGameMenu;
		this.mainMenu = mainMenu;
		initImageFolder();
		initItem();
		
		this.setLayout( new GridLayout( 5, 1 ) );
		this.setOpaque(false);
	}
	
	/**
	 * Get previous element number
	 * 
	 * @return (int) current
	 */
	public int prev() {
		this.current = this.current + 1;
		if( this.current >= item.size() )
			this.current = 0;
		refreshMenu();
		return current;
	}
	
	/**
	 * Get next element number
	 * 
	 * @return (int) current
	 */
	public int next() {
		this.current = this.current - 1;
		if( this.current < 0 )
			this.current = item.size() - 1;
		refreshMenu();
		return current;
	}
	
	/**
	 * Get Path String of unselected image of element i
	 * 
	 * @param i
	 * @return (String) path
	 */
	public String StandardImage(int i) {
		return imageFolder.get( i*2 );
	}
	
	/**
	 * Get Path String of selected image of element i
	 * 
	 * @param i
	 * @return (String) path
	 */
	public String SelectedImage(int i) {
		return imageFolder.get( i*2+1 );
	}
	
	/**
	 * Refresh Game Menu
	 */
	public void refreshMenu() {
		for( int i=0; i < item.size(); i++ ) {
			if(current == i)
				item.get(i).setIcon( new ImageIcon( SelectedImage(i) ) );
			else
				item.get(i).setIcon( new ImageIcon( StandardImage(i) ) );
		}
	}
	
	/**
	 * Initialize item images
	 */
	public void initItem() {
		item = new ArrayList<JLabel>();
		item.add( new JLabel( new ImageIcon( imageFolder.get(1) ) ) );
		this.add( item.get(0) );
		item.add( new JLabel( new ImageIcon( imageFolder.get(2) ) ) );
		this.add( item.get(1) );
		item.add( new JLabel( new ImageIcon( imageFolder.get(4) ) ) );
		this.add( item.get(2) );
	}
	
	/**
	 * Initialize image path list
	 */
	public void initImageFolder() {
		imageFolder = new ArrayList<String>();
		imageFolder.add( "src/game/data/images/menu/resume.png" );
		imageFolder.add( "src/game/data/images/menu/resume2.png" );
		imageFolder.add( "src/game/data/images/menu/save.png" );
		imageFolder.add( "src/game/data/images/menu/save2.png" );
		imageFolder.add( "src/game/data/images/menu/exit.png" );
		imageFolder.add( "src/game/data/images/menu/exit2.png" );
	}

	/**
	 * Execute operation of selected element
	 */
	public void executeSelectedItem() {
		switch (current) {
		case 0:
			inGameMenu.setVisible(false);
			mainMenu.threadController.waitThread();
			inGameMenu.setVisible(true);
			break;
		case 1:
			inGameMenu.setVisible(false);
			SaveMenu sm = new SaveMenu(inGameMenu);
			sm.setVisible(true);
			break;
		case 2:
			inGameMenu.setVisible(false);
			mainMenu.setVisible(true);
			mainMenu.threadController.notifyCloseGame();
			break;
		}
	}
}
