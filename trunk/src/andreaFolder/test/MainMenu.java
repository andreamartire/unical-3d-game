package andreaFolder.test;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class Main Menu
 * 
 * @author Andrea Martire, Salvatore Loria, Giuseppe Leone
 */
public class MainMenu extends JFrame {
	
	/** Main Panel */
	MainPanel p;
	/** Thread Monitor */
	ThreadController tc;
	/** background wallpaper */
	Image sfondo;
	/** Pointer to thread Game used in other classes*/
	public testGame game;
	
	/**
	 * Constructor
	 * 
	 * @param tc - ThreadController
	 */
	public MainMenu(ThreadController tc){
		super();
		this.tc = tc;
		sfondo = Toolkit.getDefaultToolkit().getImage("src/andreaFolder/shark.jpg");
		
		this.setSize(1000, 600);
//		setDefaultLookAndFeelDecorated(true);
//		this.setUndecorated(true); 
		
//		this.setResizable(true);
//		this.setAlwaysOnTop(true);

//		Dimension screenSize = 
//	        Toolkit.getDefaultToolkit().getScreenSize();
//	    setBounds(0,0,screenSize.width, screenSize.height);
	    
		hideCursor();
		
		this.setTitle("Main Menu");
		p = new MainPanel(tc, this);
		createMenu();
		
		//set full screen
//		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//	    device.setFullScreenWindow(this);
	}
	
	/**
	 * Create Main Menu
	 */
	public void createMenu(){
		//create main panel
		
		JPanel b = new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				g.drawImage(sfondo, 0, 0, this);
				super.paintComponent(g);
			}
		};
		
		b.setLayout(new BorderLayout());
		b.setOpaque(false);
		this.setContentPane(b);
		b.add(p, BorderLayout.CENTER);
		
		//add left vertical empty panel
		JPanel pVerticalEmpty1 = new JPanel();
		pVerticalEmpty1.setOpaque(false);
		pVerticalEmpty1.setPreferredSize(new Dimension(350, 1));
		b.add(pVerticalEmpty1,BorderLayout.WEST);
		
		//add right vertical empty panel
		JPanel pVerticalEmpty2 = new JPanel();
		pVerticalEmpty2.setOpaque(false);
		pVerticalEmpty2.setPreferredSize(new Dimension(350, 1));
		b.add(pVerticalEmpty2,BorderLayout.EAST);
		
		//add lower horizontal empty panel
		JPanel pHorizontalEmpty1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pHorizontalEmpty1.setOpaque(false);
		pHorizontalEmpty1.setPreferredSize(new Dimension(1, 100));
		b.add(pHorizontalEmpty1,BorderLayout.SOUTH);
		
		//add upper horizontal empty panel
		JPanel pHorizontalEmpty2 = new JPanel();
		pHorizontalEmpty2.setOpaque(false);
		pHorizontalEmpty2.setPreferredSize(new Dimension(1, 250));
		b.add(pHorizontalEmpty2,BorderLayout.NORTH);
		
		this.setVisible(true);
		
		/**
		 * Custom Listener
		 * 
		 * @author Andrea Martire, Salvatore Loria, Giuseppe Leone
		 */
		class KeyHandler implements KeyListener{
			/** Main Panel */
			MainPanel panel;
			/**
			 * Constructor
			 * @param p - MainPanel
			 */
			public KeyHandler( MainPanel p){
				this.panel = p;
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_UP)
					panel.next();
				if(e.getKeyCode()==KeyEvent.VK_DOWN)
					panel.prev();
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
					panel.executeSelectedItem();
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		}
		
		this.addKeyListener( new KeyHandler(p));
		this.setFocusable(true);
	}
	
	/**
	 * Hide system cursor
	 */
	public void hideCursor(){
		setCursor(getToolkit().createCustomCursor(
				new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
				new Point(), "null"));
	}
	
	/**
	 * Show system cursor
	 */
	public void showCursor(){
		setCursor(Cursor.getDefaultCursor());
	}
}