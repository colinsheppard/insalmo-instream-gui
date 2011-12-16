/*
 * InSALMO/InSTREAM Graphic User Interface, last revised October 2011.
 * Developed and maintained by Steve Railsback, Lang, Railsback & Associates,
 * Steve@LangRailsback.com and Colin Sheppard, critter@stanfordalumni.org.
 * Development sponsored by US Bureau of Reclamation under the
 * Central Valley Project Improvement Act, EPRI, USEPA, USFWS,
 * USDA Forest Service, and others.
 * Copyright (C) 2011 Lang, Railsback & Associates.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program (see file LICENSE); if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 * */
/*
 * InSTREAMConfigApp.java
 */

package insalmo;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import org.jdesktop.application.Application;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class InSALMO extends JFrame implements ActionListener{
	// Check that we are on Mac OS X.  This is crucial to loading and using the OSXAdapter class.
	public static boolean MAC_OS_X = (System.getProperty("os.name").toLowerCase().startsWith("mac os x"));
	protected JDialog aboutBox, prefs;
	private static InSALMOView view;

	public InSALMO(){
		super("InSALMO");

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.view = new InSALMOView(this);
		ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(insalmo.InSTREAMConfigApp.class).getContext().getResourceMap(InSALMO.class);
		this.setIconImage(resourceMap.getImageIcon("application.icon").getImage());
		this.setSize(1050, 850);

		if (MAC_OS_X) {
			// take the menu bar off the jframe
			//	        System.setProperty("apple.laf.useScreenMenuBar", "true");
			// set the name of the application menu item
			//	        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "WikiTeX");
			try {
				// Generate and register the OSXAdapter, passing it a hash of all the methods we wish to
				// use as delegates for various com.apple.eawt.ApplicationListener methods
				OSXAdapter.setQuitHandler(this, getClass().getDeclaredMethod("quit", (Class[])null));
				OSXAdapter.setAboutHandler(this, getClass().getDeclaredMethod("about", (Class[])null));
				OSXAdapter.setPreferencesHandler(this, getClass().getDeclaredMethod("preferences", (Class[])null));
				OSXAdapter.setFileHandler(this, getClass().getDeclaredMethod("loadImageFile", new Class[] { String.class }));
			} catch (Exception e) {
				System.err.println("Error while loading the OSXAdapter:");
				e.printStackTrace();
			}
		}

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				if(view.closeProject("Quit")){
					view.exitApplication();
				}
			}
		});

	}

	// General info dialog; fed to the OSXAdapter as the method to call when 
	// "About OSXAdapter" is selected from the application menu
	public void about() {
	}

	// General preferences dialog; fed to the OSXAdapter as the method to call when
	// "Preferences..." is selected from the application menu
	public void preferences() {
	}
	public void loadImageFile(String path) {
	}

	// General quit handler; fed to the OSXAdapter as the method to call when a system quit event occurs
	// A quit event is triggered by Cmd-Q, selecting Quit from the application or Dock menu, or logging out
	public boolean quit() {	
		return this.view.closeProject("Quit");
	}

	/**
	 * A convenient static getter for the application instance.
	 * @return the instance of InSTREAMConfigApp
	 */
	public static InSTREAMConfigApp getApplication() {
		return Application.getInstance(InSTREAMConfigApp.class);
	}

	/**
	 * Main method launching the application.
	 */
	public static void main(String[] args) {
		if (MAC_OS_X) {
			try{
				System.setProperty("apple.laf.useScreenMenuBar", "true");  
				System.setProperty("com.apple.mrj.application.apple.menu.about.name", MetaProject.getInstance().getAppTitle()); 
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(ClassNotFoundException e) {  
				System.out.println("ClassNotFoundException: " + e.getMessage());  
			}  
			catch(InstantiationException e) {  
				System.out.println("InstantiationException: " + e.getMessage());  
			}  
			catch(IllegalAccessException e) {  
				System.out.println("IllegalAccessException: " + e.getMessage());  
			}
			catch(UnsupportedLookAndFeelException e) {  
				System.out.println("UnsupportedLookAndFeelException: " + e.getMessage());  
			}  
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new InSALMO().setVisible(true);
			}
		});
		MetaProject.getInstance().setApplicationDir(new File(args[0]));
		MetaProject.getInstance().Initialize();
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}
