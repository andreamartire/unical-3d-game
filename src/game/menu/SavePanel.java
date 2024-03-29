/**
 * Copyright (C) 2010 Salvatore Loria, Andrea Martire, Giuseppe Leone
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package game.menu;

import game.common.GameConf;
import game.core.LogicWorld;
import game.graphics.GraphicalWorld;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class SaveMenu
 * 
 * @author Andrea Martire, Salvatore Loria, Giuseppe Leone
 */
public class SavePanel extends JPanel {
	
	/** Class ID */
	private static final long serialVersionUID = 1L;
	
	/** Useful pointer */
	MainMenu gameMenu;
	
	/** Screen informations */
	Dimension screenSize;
	
	/** Text Field */
	JTextField text;
	
	/**
	 * Constructor of class SaveMenu
	 * 
	 * @param (InGameMenu) - gameMenu
	 */
	public SavePanel(MainMenu mainMenu){
		super();
		this.gameMenu = mainMenu;
		
		/** Get screen size */
		if( GameConf.getSetting( GameConf.IS_FULLSCREEN ).equals("true") ){
			screenSize = new Dimension( 
				GameConf.getIntSetting( GameConf.RESOLUTION_WIDTH ), 
				GameConf.getIntSetting( GameConf.RESOLUTION_HEIGHT ) );
		}
		else{
			screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		}
		
		// apply screen size to frame
		setBounds(0,0,screenSize.width, screenSize.height);
		
		createMenu();
	}
	
	/**
	 * Create a menu that allow to save game
	 */
	public void createMenu(){
		setLayout(new BorderLayout());
		setOpaque(false);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(3,1));
		centerPanel.setOpaque(false);
		add( centerPanel, BorderLayout.CENTER );
		
		JLabel message = new JLabel("Please insert file's name");
		message.setHorizontalAlignment(JLabel.CENTER);
		centerPanel.add(message, BorderLayout.CENTER);
		
		GregorianCalendar gc = new GregorianCalendar();
		text = new JTextField("Game_"+
								gc.get(Calendar.DAY_OF_MONTH)+"-"+
								gc.get(Calendar.MONTH)+"-"+
								gc.get(Calendar.YEAR)+"_"+
								gc.get(Calendar.HOUR_OF_DAY)+"-"+
								gc.get(Calendar.MINUTE)+"-"+
								gc.get(Calendar.SECOND), 40);
		
		text.setEditable(true);
		centerPanel.add(text, BorderLayout.CENTER);
		setOpaque(false);
		
		JButton buttonCancel = new JButton("CANCEL");
		buttonCancel.setMnemonic(KeyEvent.VK_C);
		buttonCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameMenu.switchToPanel("inGamePanel");
			}
		});
		
		JButton buttonSave = new JButton("SAVE");
		buttonSave.setMnemonic(KeyEvent.VK_S);
		buttonSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(text.getText().equals(""))
					return;
				FileOutputStream fin = null;
				try {
						fin = new FileOutputStream("gameSave/"+text.getText());
					} 
				catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				
				ObjectOutputStream ois = null;
				try {
						ois = new ObjectOutputStream(fin);
					} 
				catch (IOException e) {
						e.printStackTrace();
					}
				
				try {
						ois.writeObject((LogicWorld)((GraphicalWorld)gameMenu.game).getCore());
					} 
				catch (IOException e) {
						e.printStackTrace();
					}
				gameMenu.switchToPanel("inGamePanel");
			}
		});
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setOpaque(false);
		buttonsPanel.setLayout(new FlowLayout());
		centerPanel.add(buttonsPanel, BorderLayout.CENTER);
		buttonsPanel.add(buttonCancel);
		buttonsPanel.add(buttonSave);
		
		//add left vertical empty panel
		JPanel pVerticalEmpty1 = new JPanel();
		pVerticalEmpty1.setOpaque(false);
		pVerticalEmpty1.setPreferredSize(new Dimension(screenSize.width/4, 1));
		add(pVerticalEmpty1,BorderLayout.WEST);
		
		//add right vertical empty panel
		JPanel pVerticalEmpty2 = new JPanel();
		pVerticalEmpty2.setOpaque(false);
		pVerticalEmpty2.setPreferredSize(new Dimension(screenSize.width/4, 1));
		add(pVerticalEmpty2,BorderLayout.EAST);
		
		//add lower horizontal empty panel
		JPanel pHorizontalEmpty1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pHorizontalEmpty1.setOpaque(false);
		pHorizontalEmpty1.setPreferredSize(new Dimension(1, screenSize.height/3));
		add(pHorizontalEmpty1,BorderLayout.SOUTH);
		
		//add upper horizontal empty panel
		JPanel pHorizontalEmpty2 = new JPanel();
		pHorizontalEmpty2.setOpaque(false);
		pHorizontalEmpty2.setPreferredSize(new Dimension(1, screenSize.height/4));
		add(pHorizontalEmpty2,BorderLayout.NORTH);
		
		setVisible(true);
		setFocusable(true);
	}
}