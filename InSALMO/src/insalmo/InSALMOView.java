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
 * InSALMOView.java
 */

package insalmo;

import insalmo.LimitingFactorsTool.LFTExperiment;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JToolBar.Separator;
import javax.swing.text.DefaultCaret;

import org.jdesktop.application.Action;

/**
 * The application's main frame.
 */
public class InSALMOView extends JFrame{
	private File projectDir = null;
	org.jdesktop.application.ResourceMap resourceMap;
	public static String newline = System.getProperty("line.separator");
	private JPanel		mainPanel = new javax.swing.JPanel();
	private JToolBar	toolBar = new javax.swing.JToolBar();
	private JButton		newButton = new javax.swing.JButton();
	private JButton		openButton = new javax.swing.JButton();
	private JButton		saveButton = new javax.swing.JButton();
	private JButton		addExpParamButton = new javax.swing.JButton();
	private JButton		remExpParamButton = new javax.swing.JButton();
	private JButton		clearExpParamButton = new javax.swing.JButton();
	private JButton		addSpeButton = new javax.swing.JButton();
	private JButton		remSpeButton = new javax.swing.JButton();	
	private JButton		addHabButton = new javax.swing.JButton();
	private JButton		remHabButton = new javax.swing.JButton();
	private Separator	jSeparatorExperimentSummary = new javax.swing.JToolBar.Separator();
	private Separator	jSeparatorConfigureExperiment = new javax.swing.JToolBar.Separator();
	private JButton		summaryButton = new javax.swing.JButton();
	private JButton		experimentButton = new javax.swing.JButton();
	private JButton		lftButton = new javax.swing.JButton();
	private JButton		configureButton = new javax.swing.JButton();
	private JButton		runButton = new javax.swing.JButton();
	private JButton		runWithGraphicsButton = new javax.swing.JButton();
	private JButton		viewResultsButton = new javax.swing.JButton();
	private Separator	jSeparatorResultsHelp = new javax.swing.JToolBar.Separator();
	private JButton		helpInterfaceButton = new javax.swing.JButton();
	private JButton		helpModelButton = new javax.swing.JButton();
	private JButton		helpSoftwareGuideButton = new javax.swing.JButton();
	private JPanel		contentPanel = new javax.swing.JPanel();
	private JLabel		projectTitleLabel = new javax.swing.JLabel();
	private JPanel		modelSummaryPane = new javax.swing.JPanel();
	private JScrollPane	modelSummaryScrollPane = new javax.swing.JScrollPane();
	private JTabbedPane	experimentTabbedPane = new javax.swing.JTabbedPane();
	private JTabbedPane lftTabbedPane = new javax.swing.JTabbedPane();
	private JTabbedPane lftExecutionTabbedPane = new javax.swing.JTabbedPane();
	private JLabel 		lftRunsPerExpLabel = new javax.swing.JLabel();
	private JLabel		modelSummaryTitle = new javax.swing.JLabel();
	private JLabel		modelSummarySubtitle = new javax.swing.JLabel();
	private JTabbedPane	modelSetupTabbedPane = new javax.swing.JTabbedPane();
	private JPanel		modelSetupTab = new javax.swing.JPanel();
	private JScrollPane	modelSetupPanel = new javax.swing.JScrollPane();
	private JTable		modelSetupTable = new javax.swing.JTable();
	private JPanel		observerSetupTab = new javax.swing.JPanel();
	private JTable		observerSetupTable = new javax.swing.JTable();
	private JScrollPane	observerSetupScrollPane = new javax.swing.JScrollPane();
	private JPanel		speciesSetupTab = new javax.swing.JPanel();
	private JPanel		speciesParamTab = new javax.swing.JPanel();
	private JScrollPane	speciesPanel = new javax.swing.JScrollPane();
	private JLabel		speciesSetupComboLabel = new javax.swing.JLabel();
	private JComboBox	speciesSetupComboBox = new javax.swing.JComboBox();
	private JComboBox	speciesParamComboBox = new javax.swing.JComboBox();
	private JLabel		speciesParamComboLabel = new javax.swing.JLabel();
	private JPanel		habitatSetupTab = new javax.swing.JPanel();
	private JPanel		habitatParamTab = new javax.swing.JPanel();
	private JPanel		experimentControlTab = new javax.swing.JPanel();
	private JPanel		experimentParamTab = new javax.swing.JPanel();
	private JPanel		lftSetupTab = new javax.swing.JPanel();
	private ArrayList<javax.swing.JScrollPane>	experimentParamScrollPane = new ArrayList<javax.swing.JScrollPane>();
	private JComboBox	experimentParamComboBox = new javax.swing.JComboBox();
	private JLabel		experimentParamComboLabel = new javax.swing.JLabel();
	private ArrayList<JTable>	experimentParamTable = new ArrayList<JTable>();
	private JComboBox	habitatSetupComboBox = new javax.swing.JComboBox();
	private JComboBox	habitatParamComboBox = new javax.swing.JComboBox();
	private JLabel		habitatSetupComboLabel = new javax.swing.JLabel();
	private JLabel		habitatParamComboLabel = new javax.swing.JLabel();
	private ArrayList<javax.swing.JScrollPane>	habitatSetupScrollPane = new ArrayList<javax.swing.JScrollPane>();
	private ArrayList<javax.swing.JScrollPane>	habitatParamScrollPane = new ArrayList<javax.swing.JScrollPane>();
	private ArrayList<javax.swing.JLabel>		habitatSetupFileLabel = new ArrayList<javax.swing.JLabel>();
	private ArrayList<javax.swing.JLabel>		habitatParamFileLabel = new ArrayList<javax.swing.JLabel>();
	private ArrayList<javax.swing.JScrollPane>	speciesSetupScrollPane = new ArrayList<javax.swing.JScrollPane>();
	private ArrayList<javax.swing.JScrollPane>	speciesParamScrollPane = new ArrayList<javax.swing.JScrollPane>();
	public ArrayList<javax.swing.JLabel>		speciesSetupFileLabel = new ArrayList<javax.swing.JLabel>();
	public ArrayList<javax.swing.JLabel>		speciesParamFileLabel = new ArrayList<javax.swing.JLabel>();
	private ArrayList<javax.swing.JButton>		speciesSetupChangeParamButton = new ArrayList<JButton>();
	private JScrollPane			modelSetupScrollPane = new javax.swing.JScrollPane();
	private JScrollPane			experimentControlScrollPane = new javax.swing.JScrollPane();
	private JScrollPane			lftSetupScrollPane = new javax.swing.JScrollPane();
	private ArrayList<JTable>	habitatSetupTable = new ArrayList<JTable>();
	private ArrayList<JTable>	habitatParamTable = new ArrayList<JTable>();
	private ArrayList<JTable>	speciesSetupTable = new ArrayList<JTable>();
	public ArrayList<JTable>	speciesParamTable = new ArrayList<JTable>();
	private JTable		experimentControlTable = new JTable();
	private JTable		lftSetupTable = new JTable();
	private JLayeredPane		habitatLayeredPane = new javax.swing.JLayeredPane();
	private ArrayList<String[]>	experimentParamComboElements = new ArrayList<String[]>();
	private ArrayList<String[]>	habitatComboElements = new ArrayList<String[]>();
	private ArrayList<String[]>	habitatParamComboElements = new ArrayList<String[]>();
	private Boolean		habitatSetupSelected = true;
	private Boolean		speciesSetupSelected = true;
	private ArrayList<String[]>	speciesComboElements = new ArrayList<String[]>();
	private ArrayList<String[]>	speciesParamComboElements = new ArrayList<String[]>();
	private JMenuBar	menuBar = new javax.swing.JMenuBar();
	private JMenu	 	fileMenu = new javax.swing.JMenu();
	private JMenuItem	newMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	openMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	closeMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	saveMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	saveasMenuItem = new javax.swing.JMenuItem();
	private JSeparator	jSeparatorSaveasExit = new javax.swing.JSeparator();
	private JMenuItem	exitMenuItem = new javax.swing.JMenuItem();
	private JMenu		modelMenu = new javax.swing.JMenu();
	private JMenu		experimentMenu = new javax.swing.JMenu();
	private JMenuItem	experimentMenuItem = new javax.swing.JMenuItem();
	private JMenuItem 	lftMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	summaryMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	configureMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	runMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	runWithGraphicsMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	viewResultsMenuItem = new javax.swing.JMenuItem();
	private JMenu		helpMenu = new javax.swing.JMenu();
	private JMenuItem	interfaceHelpMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	modelHelpMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	softwareGuideHelpMenuItem = new javax.swing.JMenuItem();
	private JMenuItem	lftHelpMenuItem = new javax.swing.JMenuItem();
	private JSeparator	jSeparatorModelhelpAbout = new javax.swing.JSeparator();
	private JMenuItem	aboutMenuItem = new javax.swing.JMenuItem();
	private JPanel		statusPanel = new javax.swing.JPanel();
	private JSeparator 	statusPanelSeparator = new javax.swing.JSeparator();
	private JLabel		statusMessageLabel = new javax.swing.JLabel();
	private JLabel		statusAnimationLabel = new javax.swing.JLabel();
	private String newParamName;
	private String newSpeciesName;
	private String newReachName;
	private String newClassName;
	private Timer messageTimer;
	private Timer busyIconTimer;
	private Icon idleIcon;
	private Icon[] busyIcons = new Icon[15];
	private int busyIconIndex = 0;
	private JProgressBar progressBar = new JProgressBar();
	private Project openProject;
	private ParallelGroup experimentParamLayeredScrollPanesHGroup;
	private ParallelGroup experimentParamLayeredScrollPanesVGroup;
	private ParallelGroup speciesSetupLayeredScrollPanesHGroup;
	private ParallelGroup speciesSetupLayeredScrollPanesVGroup;
	private ParallelGroup speciesParamLayeredScrollPanesHGroup;
	private ParallelGroup speciesParamLayeredScrollPanesVGroup;
	private ParallelGroup habitatSetupLayeredScrollPanesHGroup;
	private ParallelGroup habitatSetupLayeredScrollPanesVGroup;
	private ParallelGroup habitatParamLayeredScrollPanesHGroup;
	private ParallelGroup habitatParamLayeredScrollPanesVGroup;
	// Species Setup
	private javax.swing.GroupLayout speciesSetupTabLayout;
	private javax.swing.GroupLayout speciesParamTabLayout;
	// Species Parameter
	// Experiment Control 
	// Experiment Parameter
	// Habitat Setup
	private javax.swing.GroupLayout habitatSetupTabLayout;
	private javax.swing.GroupLayout habitatParamTabLayout;
	// Habitat Parameter
	private javax.swing.JScrollPane observerSetupPanel;
	private JDialog aboutBox;
	private JLinkButton errorWarningButton = new JLinkButton();
	private ShowErrorsWarnings showErrorWarnings;
	InSALMO parentFrame;
	private LaunchInsamloExecutable modelLauncher;
	// Model Summary
	private javax.swing.JScrollPane showConsoleScrollPane;
	public JExpandableTextArea showConsoleTextArea;
	private javax.swing.JLabel showConsoleLabel;
	private JButton showConsoleClearButton = new JButton();
	private JButton terminateModelRunButton = new JButton();
	private OutputStream killStream = null;
	// LFT 
	public Hashtable<String,JExpandableTextArea> lftTextAreas = new Hashtable<String,JExpandableTextArea>();
	public Hashtable<String,LaunchInsamloExecutable> lftLaunchers = new Hashtable<String,LaunchInsamloExecutable>();
	public LimitingFactorsTool lftTool = new LimitingFactorsTool(this);
	private JButton postProcessLFTButton = new JButton();
	private JButton	startLFTButton = new JButton();
	private JButton	killLFTButton = new JButton();
	
	public InSALMOView(InSALMO inSALMO) {
		parentFrame = inSALMO;
		MetaProject.getInstance().setInSALMOView(this);
		initComponents();
	}

	@Action
	public void showAboutBox() {
		if (aboutBox == null) {
			JFrame mainFrame = InSTREAMConfigApp.getApplication().getMainFrame();
			aboutBox = new InSTREAMConfigAboutBox(mainFrame);
			aboutBox.setLocationRelativeTo(mainFrame);
		}
		InSTREAMConfigApp.getApplication().show(aboutBox);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 */
	@SuppressWarnings("unchecked")
	private void initComponents() {
		mainPanel.setName("mainPanel"); 

		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		toolBar.setName("toolBar"); 

		this.resourceMap = org.jdesktop.application.Application.getInstance(insalmo.InSTREAMConfigApp.class).getContext().getResourceMap(InSALMOView.class);
		newButton.setIcon(resourceMap.getIcon("newButton.icon")); 
		newButton.setText(resourceMap.getString("newButton.text")); 
		newButton.setFocusable(false);
		newButton.setEnabled(true);
		newButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		newButton.setName("newButton"); 
		newButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(newButton.getActionListeners().length==0){
			newButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					newActionPerformed(evt);
				}
			});
		}
		toolBar.add(newButton);

		openButton.setIcon(resourceMap.getIcon("openButton.icon")); 
		openButton.setText(resourceMap.getString("openButton.text")); 
		openButton.setFocusable(false);
		openButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		openButton.setName("openButton"); 
		openButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(openButton.getActionListeners().length==0){
			openButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					openButtonActionPerformed(evt);
				}
			});
		}
		toolBar.add(openButton);

		saveButton.setIcon(resourceMap.getIcon("saveButton.icon")); 
		saveButton.setText(resourceMap.getString("saveButton.text")); 
		saveButton.setEnabled(false);
		saveButton.setFocusable(false);
		saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		saveButton.setName("saveButton"); 
		saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(saveButton.getActionListeners().length==0){
			saveButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					saveButtonActionPerformed(evt);
				}
			});
		}
		toolBar.add(saveButton);

		configureButton.setIcon(resourceMap.getIcon("configureButton.icon")); 
		configureButton.setText(resourceMap.getString("configureButton.text")); 
		configureButton.setEnabled(false);
		configureButton.setFocusable(false);
		configureButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		configureButton.setName("configureButton"); 
		configureButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(configureButton.getActionListeners().length==0){
			configureButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					configureButtonActionPerformed(evt);
				}
			});
		}
		toolBar.add(configureButton);

		jSeparatorConfigureExperiment.setName("jSeparatorConfigureExperiment"); 
		toolBar.add(jSeparatorConfigureExperiment);

		experimentButton.setIcon(resourceMap.getIcon("experimentButton.icon")); 
		experimentButton.setText(resourceMap.getString("experimentButton.text")); 
		experimentButton.setEnabled(false);
		experimentButton.setFocusable(false);
		experimentButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		experimentButton.setName("experimentButton"); 
		experimentButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(experimentButton.getActionListeners().length==0){
			experimentButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					experimentActionPerformed(evt);
				}
			});
		}
		toolBar.add(experimentButton);

		lftButton.setIcon(resourceMap.getIcon("lftButton.icon")); 
		lftButton.setText(resourceMap.getString("lftButton.text")); 
		lftButton.setEnabled(false);
		lftButton.setFocusable(false);
		lftButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		lftButton.setName("lftButton"); 
		lftButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(lftButton.getActionListeners().length==0){
			lftButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					lftActionPerformed();
				}
			});
		}
		toolBar.add(lftButton);

		jSeparatorExperimentSummary.setName("jSeparatorExperimentSummary"); 
		toolBar.add(jSeparatorExperimentSummary);

		summaryButton.setIcon(resourceMap.getIcon("summaryButton.icon")); 
		summaryButton.setText(resourceMap.getString("summaryButton.text")); 
		summaryButton.setEnabled(false);
		summaryButton.setFocusable(false);
		summaryButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		summaryButton.setName("summaryButton"); 
		summaryButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(summaryButton.getActionListeners().length==0){
			summaryButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					summaryActionPerformed(evt);
				}
			});
		}
		toolBar.add(summaryButton);

		runButton.setIcon(resourceMap.getIcon("runButton.icon")); 
		runButton.setText(resourceMap.getString("runButton.text")); 
		runButton.setEnabled(false);
		runButton.setFocusable(false);
		runButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		runButton.setName("runButton"); 
		runButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(runButton.getActionListeners().length==0){
			runButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					runButtonActionPerformed(evt);
				}
			});
		}
		toolBar.add(runButton);

		runWithGraphicsButton.setIcon(resourceMap.getIcon("runWithGraphicsButton.icon")); 
		runWithGraphicsButton.setText(resourceMap.getString("runWithGraphicsButton.text")); 
		runWithGraphicsButton.setEnabled(false);
		runWithGraphicsButton.setFocusable(false);
		runWithGraphicsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		runWithGraphicsButton.setName("runWithGraphicsButton"); 
		runWithGraphicsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(runWithGraphicsButton.getActionListeners().length==0){
			runWithGraphicsButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					runWithGraphicsButtonActionPerformed(evt);
				}
			});
		}
		toolBar.add(runWithGraphicsButton);

		if(!okToRunModel()){
			runButton.setToolTipText("The run feature is only compatible with Windows");
			runWithGraphicsButton.setToolTipText("The run feature is only compatible with Windows");
		}

		viewResultsButton.setIcon(resourceMap.getIcon("viewResultsButton.icon")); 
		viewResultsButton.setText(resourceMap.getString("viewResultsButton.text")); 
		viewResultsButton.setEnabled(false);
		viewResultsButton.setFocusable(false);
		viewResultsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		viewResultsButton.setName("viewResultsButton"); 
		viewResultsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		toolBar.add(viewResultsButton);
		if(viewResultsButton.getActionListeners().length==0){
			viewResultsButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					viewResultsActionPerformed(evt);
				}
			});
		}

		errorWarningButton.setText(resourceMap.getString("errorWarningButton.text"));
		errorWarningButton.setFocusable(false);
		errorWarningButton.setName("errorWarningButton"); 
		errorWarningButton.setEnabled(false);
		errorWarningButton.setLinkColor(Color.RED);
		if(errorWarningButton.getActionListeners().length==0){
			errorWarningButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					errorWarningButtonActionPerformed(evt);
				}
			});
		}

		jSeparatorResultsHelp.setName("jSeparatorResultsHelp"); 
		toolBar.add(jSeparatorResultsHelp);

		helpInterfaceButton.setIcon(resourceMap.getIcon("helpInterfaceButton.icon")); 
		helpInterfaceButton.setText(resourceMap.getString("helpInterfaceButton.text")); 
		helpInterfaceButton.setFocusable(false);
		helpInterfaceButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		helpInterfaceButton.setName("helpInterfaceButton"); 
		helpInterfaceButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(helpInterfaceButton.getActionListeners().length==0){
			helpInterfaceButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					helpInterfaceButtonActionPerformed(evt);
				}
			});
		}
		//toolBar.add(helpInterfaceButton);

		helpModelButton.setIcon(resourceMap.getIcon("helpModelButton.icon")); 
		helpModelButton.setText(resourceMap.getString("helpModelButton.text")); 
		helpModelButton.setFocusable(false);
		helpModelButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		helpModelButton.setName("helpModelButton"); 
		helpModelButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(helpModelButton.getActionListeners().length==0){
			helpModelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					helpModelButtonActionPerformed(evt);
				}
			});
		}
		//toolBar.add(helpModelButton);

		helpSoftwareGuideButton.setIcon(resourceMap.getIcon("helpSoftwareGuideButton.icon")); 
		helpSoftwareGuideButton.setText(resourceMap.getString("helpSoftwareGuideButton.text")); 
		helpSoftwareGuideButton.setFocusable(false);
		helpSoftwareGuideButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		helpSoftwareGuideButton.setName("helpSoftwareGuideButton"); 
		helpSoftwareGuideButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(helpSoftwareGuideButton.getActionListeners().length==0){
			helpSoftwareGuideButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					helpSoftwareGuideButtonActionPerformed(evt);
				}
			});
		}
		//toolBar.add(helpSoftwareGuideButton);

		menuBar.setName("menuBar"); 

		fileMenu.setLabel(resourceMap.getString("fileMenu.label")); 
		fileMenu.setName("fileMenu"); 

		newMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, getMenuShortcutKeyMask()));
		newMenuItem.setText(resourceMap.getString("newMenuItem.text")); 
		newMenuItem.setName("newMenuItem"); 
		newMenuItem.setEnabled(true);
		if(newMenuItem.getActionListeners().length==0){
			newMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					newActionPerformed(evt);
				}
			});
		}
		fileMenu.add(newMenuItem);

		openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,getMenuShortcutKeyMask()));
		openMenuItem.setText(resourceMap.getString("openMenuItem.text")); 
		openMenuItem.setName("openMenuItem"); 
		if(openMenuItem.getActionListeners().length==0){
			openMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					openMenuItemActionPerformed(evt);
				}
			});
		}
		fileMenu.add(openMenuItem);

		saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, getMenuShortcutKeyMask()));
		saveMenuItem.setText(resourceMap.getString("saveMenuItem.text")); 
		saveMenuItem.setName("saveMenuItem"); 
		saveMenuItem.setEnabled(false);
		if(saveMenuItem.getActionListeners().length==0){
			saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					saveMenuItemActionPerformed(evt);
				}
			});
		}
		fileMenu.add(saveMenuItem);

		saveasMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | getMenuShortcutKeyMask()));
		saveasMenuItem.setText(resourceMap.getString("saveasMenuItem.text")); 
		saveasMenuItem.setName("saveasMenuItem"); 
		saveasMenuItem.setEnabled(false);
		if(saveasMenuItem.getActionListeners().length==0){
			saveasMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					saveasMenuItemActionPerformed(evt);
				}
			});
		}
		fileMenu.add(saveasMenuItem);

		closeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, getMenuShortcutKeyMask()));
		closeMenuItem.setText(resourceMap.getString("closeMenuItem.text")); 
		closeMenuItem.setName("closeMenuItem"); 
		closeMenuItem.setEnabled(false);
		if(closeMenuItem.getActionListeners().length==0){
			closeMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					closeActionPerformed(evt);
				}
			});
		}
		fileMenu.add(closeMenuItem);

		jSeparatorSaveasExit.setName("jSeparatorSaveasExit"); 
		fileMenu.add(jSeparatorSaveasExit);

		configureMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, getMenuShortcutKeyMask()));
		configureMenuItem.setText(resourceMap.getString("configureMenuItem.text")); 
		configureMenuItem.setName("configureMenuItem"); 
		configureMenuItem.setEnabled(false);
		if(configureMenuItem.getActionListeners().length==0){
			configureMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					configureMenuItemActionPerformed(evt);
				}
			});
		}
		fileMenu.add(configureMenuItem);

		jSeparatorSaveasExit.setName("jSeparatorSaveasExit"); 
		fileMenu.add(jSeparatorSaveasExit);

		exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, getMenuShortcutKeyMask()));
		exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); 
		exitMenuItem.setName("exitMenuItem"); 
		exitMenuItem.setEnabled(true);
		if(exitMenuItem.getActionListeners().length==0){
			exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					exitMenuItemActionPerformed(evt);
				}
			});
		}
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);
		experimentMenu.setText(resourceMap.getString("experimentMenu.text")); 
		experimentMenu.setName("experimentMenu"); 

		//TODO assign accelerator to experiment menu options
		experimentMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, getMenuShortcutKeyMask()));
		experimentMenuItem.setText(resourceMap.getString("experimentMenuItem.text")); 
		experimentMenuItem.setName("experimentMenuItem"); 
		experimentMenuItem.setEnabled(false);
		if(experimentMenuItem.getActionListeners().length==0){
			experimentMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					experimentActionPerformed(evt);
				}
			});
		}
		experimentMenu.add(experimentMenuItem);

		lftMenuItem.setText(resourceMap.getString("lftMenuItem.text")); 
		lftMenuItem.setName("lftMenuItem"); 
		lftMenuItem.setEnabled(false);
		if(lftMenuItem.getActionListeners().length==0){
			lftMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					lftActionPerformed();
				}
			});
		}
		experimentMenu.add(lftMenuItem);

		menuBar.add(experimentMenu);
		modelMenu.setText(resourceMap.getString("modelMenu.text")); 
		modelMenu.setName("modelMenu"); 

		summaryMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, getMenuShortcutKeyMask()));
		summaryMenuItem.setText(resourceMap.getString("summaryMenuItem.text")); 
		summaryMenuItem.setName("summaryMenuItem"); 
		summaryMenuItem.setEnabled(false);
		if(summaryMenuItem.getActionListeners().length==0){
			summaryMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					summaryActionPerformed(evt);
				}
			});
		}
		modelMenu.add(summaryMenuItem);

		runMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, getMenuShortcutKeyMask()));
		runMenuItem.setText(resourceMap.getString("runMenuItem.text")); 
		runMenuItem.setName("runMenuItem"); 
		runMenuItem.setEnabled(false);
		if(runMenuItem.getActionListeners().length==0){
			runMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					runMenuItemActionPerformed(evt);
				}
			});
		}
		modelMenu.add(runMenuItem);

		runWithGraphicsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_MASK | getMenuShortcutKeyMask()));
		runWithGraphicsMenuItem.setText(resourceMap.getString("runWithGraphicsMenuItem.text")); 
		runWithGraphicsMenuItem.setName("runWithGraphicsMenuItem"); 
		runWithGraphicsMenuItem.setEnabled(false);
		if(runWithGraphicsMenuItem.getActionListeners().length==0){
			runWithGraphicsMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					runWithGraphicsMenuItemActionPerformed(evt);
				}
			});
		}
		modelMenu.add(runWithGraphicsMenuItem);

		viewResultsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, getMenuShortcutKeyMask()));
		viewResultsMenuItem.setText(resourceMap.getString("viewResultsMenuItem.text")); 
		viewResultsMenuItem.setName("viewResultsMenuItem"); 
		viewResultsMenuItem.setEnabled(false);
		modelMenu.add(viewResultsMenuItem);

		menuBar.add(modelMenu);

		helpMenu.setText(resourceMap.getString("helpMenu.text")); 
		helpMenu.setName("helpMenu"); 

		interfaceHelpMenuItem.setText(resourceMap.getString("interfaceHelpMenuItem.text")); 
		interfaceHelpMenuItem.setName("interfaceHelpMenuItem"); 
		if(interfaceHelpMenuItem.getActionListeners().length==0){
			interfaceHelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					helpInterfaceButtonActionPerformed(evt);
				}
			});
		}
		helpMenu.add(interfaceHelpMenuItem);

		modelHelpMenuItem.setText(resourceMap.getString("modelHelpMenuItem.text")); 
		modelHelpMenuItem.setName("modelHelpMenuItem"); 
		if(modelHelpMenuItem.getActionListeners().length==0){
			modelHelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					helpModelButtonActionPerformed(evt);
				}
			});
		}
		helpMenu.add(modelHelpMenuItem);

		softwareGuideHelpMenuItem.setText(resourceMap.getString("softwareGuideHelpMenuItem.text")); 
		softwareGuideHelpMenuItem.setName("softwareGuideHelpMenuItem"); 
		if(softwareGuideHelpMenuItem.getActionListeners().length==0){
			softwareGuideHelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					helpSoftwareGuideButtonActionPerformed(evt);
				}
			});
		}
		helpMenu.add(softwareGuideHelpMenuItem);
		
		lftHelpMenuItem.setText(resourceMap.getString("lftHelpMenuItem.text")); 
		lftHelpMenuItem.setName("lftHelpMenuItem"); 
		if(lftHelpMenuItem.getActionListeners().length==0){
			lftHelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					helpLFTButtonActionPerformed(evt);
				}
			});
		}
		helpMenu.add(lftHelpMenuItem);

		jSeparatorModelhelpAbout.setName("jSeparatorModelhelpAbout"); 
		helpMenu.add(jSeparatorModelhelpAbout);

		menuBar.add(helpMenu);

		statusPanel.setName("statusPanel"); 
		statusPanelSeparator.setName("statusPanelSeparator"); 
		statusMessageLabel.setName("statusMessageLabel"); 
		statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		statusAnimationLabel.setName("statusAnimationLabel"); 

		progressBar.setName("progressBar"); 

		javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
		statusPanel.setLayout(statusPanelLayout);
		statusPanelLayout.setHorizontalGroup(
				statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1452, Short.MAX_VALUE)
				.addGroup(statusPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(statusMessageLabel)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1288, Short.MAX_VALUE)
						.addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(statusAnimationLabel)
						.addContainerGap())
		);
		statusPanelLayout.setVerticalGroup(
				statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(statusPanelLayout.createSequentialGroup()
						.addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(statusMessageLabel)
								.addComponent(statusAnimationLabel)
								.addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(3, 3, 3))
		);

		javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout.setHorizontalGroup(
				mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(mainPanelLayout.createSequentialGroup()
						.addGap(34, 34, 34)
						.addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
						.addGroup(mainPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(contentPanel, 0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE)
								.addGap(2, 2, 2))
		);
		mainPanelLayout.setVerticalGroup(
				mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(mainPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(contentPanel, 0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE)
						.addGap(2, 2, 2))
		);
		parentFrame.setJMenuBar(menuBar);
		parentFrame.getContentPane().add(mainPanel);
		//		setStatusBar(statusPanel);
	}

	/*
	 * initContentPanel
	 */
	private void initContentPanel(){

		contentPanel.setName("contentPanel"); 

		projectTitleLabel.setFont(resourceMap.getFont("projectTitleLabel.font")); 
		projectTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		projectTitleLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		projectTitleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
		projectTitleLabel.setName("projectTitleLabel"); 
		projectTitleLabel.setVisible(true);

		modelSummaryPane.setName("modelSummaryPane"); 
		modelSummaryPane.setOpaque(true);
		modelSummaryPane.addContainerListener(new java.awt.event.ContainerAdapter() {
			public void componentAdded(java.awt.event.ContainerEvent evt) {
				hideModelSummaryPane(evt);
			}
		});

		experimentTabbedPane.setName("experimentTabbedPane"); 
		experimentTabbedPane.setOpaque(false);
		experimentTabbedPane.addContainerListener(new java.awt.event.ContainerAdapter() {
			public void componentAdded(java.awt.event.ContainerEvent evt) {
				hideExperimentTabbedPane(evt);
			}
		});

		lftTabbedPane.setName("TabbedPane"); 
		lftTabbedPane.setOpaque(false);
		lftTabbedPane.addContainerListener(new java.awt.event.ContainerAdapter() {
			public void componentAdded(java.awt.event.ContainerEvent evt) {
				lftHideTabbedPane(evt);
			}
		});
		
		lftExecutionTabbedPane.setName("TabbedPane"); 
		lftExecutionTabbedPane.setOpaque(false);
		lftExecutionTabbedPane.addContainerListener(new java.awt.event.ContainerAdapter() {
			public void componentAdded(java.awt.event.ContainerEvent evt) {
				lftHideExecutionTabbedPane(evt);
			}
		});

		modelSummaryTitle.setText(resourceMap.getString("modelSummaryTitle.text")); 
		modelSummaryTitle.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		modelSummaryTitle.setName("modelSummaryTitle"); 
		modelSummaryTitle.setFont(resourceMap.getFont("modelSummaryTitle.font"));
		modelSummarySubtitle.setText(resourceMap.getString("modelSummarySubtitle.text")); 
		modelSummarySubtitle.setName("modelSummarySubtitle"); 
		modelSummarySubtitle.setFont(resourceMap.getFont("modelSummarySubtitle.font"));
		showConsoleTextArea = new JExpandableTextArea(10,80);
		((DefaultCaret)showConsoleTextArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		showConsoleTextArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		showConsoleTextArea.setEditable(false);
		showConsoleScrollPane = new javax.swing.JScrollPane(showConsoleTextArea);
		showConsoleScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		showConsoleScrollPane.setEnabled(true);
		showConsoleLabel = new javax.swing.JLabel();
		showConsoleLabel.setText(resourceMap.getString("consoleLabel.text"));
		showConsoleClearButton.setName("showConsoleClearButton");
		showConsoleClearButton.setText(resourceMap.getString("clearButton.text"));
		if(showConsoleClearButton.getActionListeners().length==0){
			showConsoleClearButton.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					showConsoleClearButtonActionPerfomed(evt);
				}
			});
		}
		terminateModelRunButton.setName("terminateModelRunButton");
		terminateModelRunButton.setText(resourceMap.getString("terminateModelRunButton.text"));
		if(terminateModelRunButton.getActionListeners().length==0){
			terminateModelRunButton.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					terminateModelRunButtonActionPerfomed(evt);
				}
			});
		}

		javax.swing.GroupLayout modelSummaryPaneLayout = new javax.swing.GroupLayout(modelSummaryPane);
		modelSummaryPane.setLayout(modelSummaryPaneLayout);
		modelSummaryPaneLayout.setHorizontalGroup(
				modelSummaryPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(modelSummaryPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(modelSummaryPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(modelSummarySubtitle)
								.addComponent(modelSummaryTitle)
								.addComponent(showConsoleLabel)
								.addComponent(showConsoleScrollPane)
								.addGroup(modelSummaryPaneLayout.createSequentialGroup()
										.addComponent(showConsoleClearButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(terminateModelRunButton)))
										.addContainerGap(resourceMap.getInteger("containerGap.rightParamTable.int"),Short.MAX_VALUE))
		);
		modelSummaryPaneLayout.setVerticalGroup(
				modelSummaryPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(modelSummaryPaneLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(modelSummaryTitle)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(modelSummarySubtitle)						
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(showConsoleLabel)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(showConsoleScrollPane)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(modelSummaryPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
								.addComponent(showConsoleClearButton)
								.addComponent(terminateModelRunButton))
								.addContainerGap(resourceMap.getInteger("containerGap.bottomParamTable.int"),Short.MAX_VALUE))
		);
		modelSummaryScrollPane.add(modelSummaryPane);
		modelSummaryScrollPane.setVisible(true);
		modelSummaryScrollPane.setViewportView(modelSummaryPane);

		modelSetupTabbedPane.setName("modelSetupTabbedPane"); 
		modelSetupTabbedPane.setFocusable(true);
		modelSetupTabbedPane.addContainerListener(new java.awt.event.ContainerAdapter() {
			public void componentAdded(java.awt.event.ContainerEvent evt) {
				hideModelSetupTabbedPane(evt);
			}
		});

		javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
		contentPanel.setLayout(contentPanelLayout);
		contentPanelLayout.setHorizontalGroup(
				contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.CENTER,contentPanelLayout.createSequentialGroup()
						.addComponent(projectTitleLabel)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(errorWarningButton))
						.addComponent(modelSetupTabbedPane, 0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE)
						.addComponent(modelSummaryScrollPane, 0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE)
						.addComponent(experimentTabbedPane, 0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE)
						.addComponent(lftTabbedPane, 0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE)
		);
		contentPanelLayout.setVerticalGroup(
				contentPanelLayout.createSequentialGroup()
				.addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(projectTitleLabel)
						.addComponent(errorWarningButton))
						.addGap(14, 14, 14)
						.addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(modelSetupTabbedPane, 0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE)
								.addComponent(modelSummaryScrollPane, 0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE)
								.addComponent(experimentTabbedPane, 0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE)
								.addComponent(lftTabbedPane, 0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE))
		);
		experimentTabbedPane.getAccessibleContext().setAccessibleName(resourceMap.getString("experiment.AccessibleContext.accessibleName")); 
		lftTabbedPane.getAccessibleContext().setAccessibleName(resourceMap.getString("lft.AccessibleContext.accessibleName")); 
		modelSetupTabbedPane.getAccessibleContext().setAccessibleName(resourceMap.getString("modelSetup.AccessibleContext.accessibleName")); 

	}

	private void configureButtonActionPerformed(java.awt.event.ActionEvent evt) {
		modelSummaryScrollPane.setVisible(false);
		modelSetupTabbedPane.setVisible(true);
		experimentTabbedPane.setVisible(false);
		lftTabbedPane.setVisible(false);
	}
	private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		if(closeProject("Quit"))exitApplication();
	}
	public void exitApplication(){
		this.parentFrame.quit();
		System.exit(0);
	}
	private void lftHideTabbedPane(java.awt.event.ContainerEvent evt) {
		lftTabbedPane.setVisible(false);
	}
	private void lftHideExecutionTabbedPane(java.awt.event.ContainerEvent evt) {
		lftExecutionTabbedPane.setVisible(false);
	}
	private void hideModelSetupTabbedPane(java.awt.event.ContainerEvent evt) {
		modelSetupTabbedPane.setVisible(false);
	}
	private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {
		openProject(null);
	}
	private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		openProject(null);
	}
	private void closeActionPerformed(java.awt.event.ActionEvent evt) {
		closeProject("Close");
	}
	private void newActionPerformed(java.awt.event.ActionEvent evt) {
		if(this.getOpenProject() != null){
			int response = JOptionPane.showConfirmDialog(this.parentFrame, "You can only have one project open at a time, close the current project?","Project Already Open",JOptionPane.YES_NO_OPTION);
			if(response==0){
				closeProject("Close");
			}else{
				return;
			}
		}
		JFrame mainFrame = InSTREAMConfigApp.getApplication().getMainFrame();
		NewProjectWizard wizard = new NewProjectWizard(this);
		wizard.setLocationRelativeTo(mainFrame);

		InSTREAMConfigApp.getApplication().show(wizard);
	}
	public void createNewProject(File projectFile, boolean fromScratch){
		System.out.println("Creating: " + projectFile.getName());
		this.projectTitleLabel.setText("Project: "+projectFile.getName());
		this.setOpenProject(new Project(projectFile));
		this.setProjectDir(projectFile);
		this.getOpenProject().createNewProject(fromScratch);
	}
	private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {
		saveProject();
	}
	private void helpInterfaceButtonActionPerformed(java.awt.event.ActionEvent evt) {
		File helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/inSALMO_GUI_Guide.chm");
		BareBonesBrowserLaunch.openURL("file://"+helpFile.getAbsolutePath());
	}
	private void helpModelButtonActionPerformed(java.awt.event.ActionEvent evt) {
		File helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/inSALMO_Model_Description.chm");
		BareBonesBrowserLaunch.openURL("file://"+helpFile.getAbsolutePath());
	}
	private void helpSoftwareGuideButtonActionPerformed(java.awt.event.ActionEvent evt) {
		File helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/inSALMO_Software_Documentation.chm");
		BareBonesBrowserLaunch.openURL("file://"+helpFile.getAbsolutePath());
	}
	private void helpLFTButtonActionPerformed(java.awt.event.ActionEvent evt) {
		File helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/Limiting_Factors_Tool.chm");
		BareBonesBrowserLaunch.openURL("file://"+helpFile.getAbsolutePath());
	}
	public void enableModelButtons(){
		summaryButton.setEnabled(true);
		saveButton.setEnabled(true);
		configureButton.setEnabled(true);
		if(okToRunModel()){
			runButton.setEnabled(true);
			runWithGraphicsButton.setEnabled(true);
			runMenuItem.setEnabled(true);
			runWithGraphicsMenuItem.setEnabled(true);
		}
		viewResultsButton.setEnabled(true);
		viewResultsMenuItem.setEnabled(true);
		experimentButton.setEnabled(true);
		lftButton.setEnabled(true);
		summaryMenuItem.setEnabled(true);
		saveMenuItem.setEnabled(true);
		saveasMenuItem.setEnabled(true);
		closeMenuItem.setEnabled(true);
		configureMenuItem.setEnabled(true);
		experimentMenuItem.setEnabled(true);
		lftMenuItem.setEnabled(true);
		errorWarningButton.setEnabled(true);
		projectTitleLabel.setVisible(true);
		showConsoleLabel.setVisible(true);
		showConsoleScrollPane.setVisible(true);
		showConsoleTextArea.setVisible(true);
		showConsoleClearButton.setVisible(true);
		terminateModelRunButton.setVisible(true);
	}
	public void disableModelButtons(){
		summaryButton.setEnabled(false);
		saveButton.setEnabled(false);
		configureButton.setEnabled(false);
		runButton.setEnabled(false);
		runWithGraphicsButton.setEnabled(false);
		experimentButton.setEnabled(false);
		lftButton.setEnabled(false);
		summaryMenuItem.setEnabled(false);
		saveMenuItem.setEnabled(false);
		saveasMenuItem.setEnabled(false);
		closeMenuItem.setEnabled(false);
		configureMenuItem.setEnabled(false);
		runMenuItem.setEnabled(false);
		runWithGraphicsMenuItem.setEnabled(false);
		experimentMenuItem.setEnabled(false);
		lftMenuItem.setEnabled(false);
		errorWarningButton.setEnabled(false);
		projectTitleLabel.setVisible(false);
		viewResultsButton.setEnabled(false);
		viewResultsMenuItem.setEnabled(false);
		if(showConsoleLabel!=null){
			showConsoleLabel.setVisible(false);
			showConsoleScrollPane.setVisible(false);
			showConsoleTextArea.setVisible(false);
			showConsoleClearButton.setVisible(false);
			terminateModelRunButton.setVisible(false);
		}
	}
	public void openProject(File projectFile){
		openProject(projectFile, true);
	}
	public void openProject(File projectFile, boolean showProject){
		if(this.getOpenProject() != null){
			int response = JOptionPane.showConfirmDialog(this.parentFrame, "You can only have one project open at a time, close the current project?","Project Already Open",JOptionPane.YES_NO_OPTION);
			if(response==0){
				closeProject("Close");
			}else{
				return;
			}
		}
		if(projectFile==null){
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File("."));
			fc.setDialogTitle("Select a Project Directory");
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setAcceptAllFileFilterUsed(false); // disable the "All files" option.
			int returnVal = fc.showOpenDialog(contentPanel);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				projectFile = fc.getSelectedFile();
			} else {
				System.out.println("Open command cancelled by user.");
				return;
			}
		}
		System.out.println("Opening: " + projectFile.getName());
		if(showProject)this.projectTitleLabel.setText("Project: "+projectFile.getName());
		this.setOpenProject(new Project(projectFile));
		try{
			this.getOpenProject().loadProjectFiles();
		}catch (IOException e){
			JOptionPane.showMessageDialog(this.parentFrame,"<html><body><font size=+2><b>Open operation cancelled</b></font><br/><br/>An error occurred reading a project file:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
			shutDownProject();
			updateErrorWarningLinkButton(new ActionEvent(this,0,"Project closed"));
			return;
		}catch (RuntimeException e){
			JOptionPane.showMessageDialog(this.parentFrame,"<html><body><font size=+2><b>Open operation cancelled</b></font><br/><br/>An error occurred loading this project:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
			shutDownProject();
			updateErrorWarningLinkButton(new ActionEvent(this,0,"Project closed"));
			return;
		}
		this.projectDir = projectFile;
		if(showProject){
			createParameterTabs();
			initContentPanel();
			enableModelButtons();
			MetaProject.getInstance().setContentPanel(contentPanel);
			modelSetupTabbedPane.setVisible(false);
			modelSummaryScrollPane.setVisible(true);
			experimentTabbedPane.setVisible(false);
			lftTabbedPane.setVisible(false);
		}
		MetaProject.getInstance().setProjectChanged(false);

		// Test to make sure no illegal or missing parameters which we should warn the user about
		if(getOpenProject().isMissingParameters() | getOpenProject().containsIllegalParameters()){
			StringBuffer sb = new StringBuffer();
			sb.append( "<html><body><font color=RED size=+2>Warning</font><br><br>");
			if(getOpenProject().isMissingParameters()){
				sb.append("<b>Missing Parameters:</b> The following parameters were not found in the configuration files.<br>" +
				"These parameters will be added to the opened project with default values.<br><br>" );
				sb.append("<table border=1><tr><th align='left'>Parameter Name</th><th align='left'>File</th></tr>");
				for(String[] missing : getOpenProject().getMissingParameters()){
					sb.append("<tr><td align='left'>"+missing[0]+"</td><td align='left'>"+missing[1]+"</td></tr>");
				}
				sb.append("</table><br><br>");
			}
			if(getOpenProject().containsIllegalParameters()){
				sb.append("<b>Illegal Parameters:</b> The following illegal parameters were found in the configuration files.<br>" +
						"These parameters will be excluded from the opened project and will be eliminated from your<br>" +
				"configuration files upon save.<br><br>" );
				sb.append("<table border=1><tr><th align='left'>Parameter Name</th><th align='left'>Value</th><th align='left'>File</th><th align='left'>Error Message</th></tr>");
				for(String[] illegals : getOpenProject().getIllegalParameters()){
					sb.append("<tr><td align='left'>"+illegals[0]+"</td><td align='left'>"+illegals[1]+"</td><td align='left'>"+illegals[2]+"</td><td align='left'>"+illegals[3]+"</td></tr>");
				}
				sb.append("</table><br>");
			}
			sb.append( "</body></html>" );
			JLabel textarea = new JLabel( sb.toString() );
			int result = JOptionPane.showOptionDialog(this.parentFrame, textarea ,"Warning",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null,new String[]{"Continue","Close Without Save"},"Continue"); 
			if(result==1){
				shutDownProject();
			}else{
				MetaProject.getInstance().setProjectChanged(true);
			}
		}
		revealSummaryPane();
	}
	public boolean closeProject(String closeQuit){
		if(MetaProject.getInstance().hasProjectChanged()){
			String[] choices = {"Save and "+closeQuit, closeQuit+" without Save", "CANCEL"};
			int result = JOptionPane.showOptionDialog(this.parentFrame, "You have unsaved changes...", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
			if(result==-1 || result==2){
				System.out.println("canceled close...");
				return false;
			}
			if(result==0){
				System.out.println("saving first...");
				if(!saveProject())return false;
			}
		}
		shutDownProject();
		revealSummaryPane();
		updateErrorWarningLinkButton(new ActionEvent(this,0,"Project closed"));
		return true;
	}
	public void shutDownProject(){
		System.out.println("closing...");
		removeProjectFromView();
		this.setOpenProject(null);
		MetaProject.getInstance().setOpenProject(null);
		disableModelButtons();
		updateModelSummary();
	}

	private void removeProjectFromView(){
		modelSetupTabbedPane.remove(modelSetupTab);
		modelSetupTabbedPane.remove(observerSetupTab);

		habitatComboElements.clear();
		habitatSetupComboBox.removeAllItems();
		habitatParamComboBox.removeAllItems();
		for(int i=0; i<habitatSetupTable.size(); i++){
			habitatSetupTable.remove(i);
			habitatSetupScrollPane.remove(i);
		}
		for(int i=0; i<habitatParamTable.size(); i++){
			habitatParamTable.remove(i);
			habitatParamScrollPane.remove(i);
		}
		modelSetupTabbedPane.remove(habitatSetupTab);
		modelSetupTabbedPane.remove(habitatParamTab);

		speciesComboElements.clear();
		speciesSetupComboBox.removeAllItems();
		speciesParamComboBox.removeAllItems();
		for(int i=0; i<speciesSetupTable.size(); i++){
			speciesSetupTable.remove(i);
			speciesSetupScrollPane.remove(i);
		}
		for(int i=0; i<speciesParamTable.size(); i++){
			speciesParamTable.remove(i);
			speciesParamScrollPane.remove(i);
		}
		modelSetupTabbedPane.remove(speciesSetupTab);
		modelSetupTabbedPane.remove(speciesParamTab);

		modelSetupTabbedPane.revalidate();


		experimentParamComboElements.clear();
		experimentParamComboBox.removeAllItems();
		for(int i=0; i<experimentParamTable.size(); i++){
			experimentParamTable.remove(i);
			experimentParamScrollPane.remove(i);
		}
		experimentTabbedPane.remove(experimentParamTab);
		experimentTabbedPane.remove(experimentControlTab);

		experimentTabbedPane.revalidate();
	}

	public boolean saveProject(){
		if(projectDir==null && !saveProjectAs()){
			System.out.println("Project save cancelled by user");
			return false;
		}
		System.out.println("Saving project: "+projectDir.getName());
		getOpenProject().save();
		MetaProject.getInstance().setProjectChanged(false);
		return true;
	}


	private boolean saveProjectAs(){
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setDialogTitle("Select a directory to save the project to (the selected directory name will become the project name)");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false); // disable the "All files" option.
		File newDir = null;
		if(projectDir==null){
			// Brand new project, suggest title
			newDir = new File("Specify_Project_Directory");
		}else{
			newDir = projectDir;
		}
		fc.setSelectedFile( newDir );

		int returnVal = fc.showSaveDialog(contentPanel);
		if( returnVal == JFileChooser.APPROVE_OPTION ){
			setProjectDir(fc.getSelectedFile());
			fc.getSelectedFile().mkdir();
			return(true);
		}else{
			System.out.println("Save command cancelled by user.");
			return(false);
		}
	}

	public void setProjectDir(File dirFile){
		projectDir = dirFile;
		projectTitleLabel.setText(dirFile.getName());
		getOpenProject().setProjectDir(dirFile);
	}
	private void createParameterTabs(){

		/*
		 * Model setup tab 
		 */
		modelSetupTab.setName("modelSetupTab"); 

		javax.swing.GroupLayout modelSetupTabLayout = new javax.swing.GroupLayout(modelSetupTab);
		modelSetupTab.setLayout(modelSetupTabLayout);

		ParallelGroup modelSetupLayeredScrollPanesHGroup = modelSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		ParallelGroup modelSetupLayeredScrollPanesVGroup = modelSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);

		modelSetupTable = ParameterTable.getInstance().makeTable("modSetup","");
		modelSetupTable.setModel(ParameterTable.getInstance().getModel(getOpenProject(),"modSetup",""));
		modelSetupTable.removeColumn(modelSetupTable.getColumnModel().getColumn(5));
		modelSetupTable.removeColumn(modelSetupTable.getColumnModel().getColumn(4));
		modelSetupTable.setName("modelSetupTable");
		modelSetupTable.getTableHeader().setReorderingAllowed(false);
		modelSetupScrollPane.setName("modelSetupScrollPane"); 
		modelSetupScrollPane.setViewportView(modelSetupTable);
		modelSetupScrollPane.setVisible(true);

		javax.swing.JLabel modelSetupFileLabel = new javax.swing.JLabel();
		modelSetupFileLabel.setText(resourceMap.getString("configFileLabel.text")+" Model.Setup"); 
		modelSetupFileLabel.setName("modelSetupFileLabel"); 

		modelSetupLayeredScrollPanesHGroup.addComponent(modelSetupScrollPane,0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE);
		modelSetupLayeredScrollPanesVGroup.addComponent(modelSetupScrollPane,0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE);

		modelSetupTabLayout.setHorizontalGroup(
				modelSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(modelSetupTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(modelSetupTabLayout.createParallelGroup()
								.addComponent(modelSetupFileLabel)
								.addGroup(modelSetupLayeredScrollPanesHGroup))
								.addContainerGap())
		);
		modelSetupTabLayout.setVerticalGroup(
				modelSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(modelSetupTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(modelSetupTabLayout.createSequentialGroup()
								.addComponent(modelSetupFileLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(modelSetupLayeredScrollPanesVGroup))
								.addContainerGap())
		);

		modelSetupTabbedPane.addTab(resourceMap.getString("modelSetupTab.TabConstraints.tabTitle"), modelSetupTab); 

		/*
		 * Observer setup tab
		 */
		observerSetupTab.setName("observerSetupTab"); 

		javax.swing.GroupLayout observerSetupTabLayout = new javax.swing.GroupLayout(observerSetupTab);
		observerSetupTab.setLayout(observerSetupTabLayout);

		ParallelGroup observerSetupLayeredScrollPanesHGroup = observerSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		ParallelGroup observerSetupLayeredScrollPanesVGroup = observerSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);

		observerSetupTable = ParameterTable.getInstance().makeTable("obsSetup","");
		observerSetupTable.setModel(ParameterTable.getInstance().getModel(getOpenProject(),"obsSetup",""));
		observerSetupTable.removeColumn(observerSetupTable.getColumnModel().getColumn(5));
		observerSetupTable.removeColumn(observerSetupTable.getColumnModel().getColumn(4));
		observerSetupTable.setName("observerSetupTable");
		observerSetupTable.getTableHeader().setReorderingAllowed(false);
		observerSetupScrollPane.setName("observerSetupScrollPane"); 
		observerSetupScrollPane.setViewportView(observerSetupTable);
		observerSetupScrollPane.setVisible(true);

		javax.swing.JLabel observerSetupFileLabel = new javax.swing.JLabel();
		observerSetupFileLabel.setText(resourceMap.getString("configFileLabel.text")+" Observer.Setup"); 
		observerSetupFileLabel.setName("observerSetupFileLabel"); 

		observerSetupLayeredScrollPanesHGroup.addComponent(observerSetupScrollPane,0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE);
		observerSetupLayeredScrollPanesVGroup.addComponent(observerSetupScrollPane,0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE);

		observerSetupTabLayout.setHorizontalGroup(
				observerSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(observerSetupTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(observerSetupTabLayout.createParallelGroup()
								.addComponent(observerSetupFileLabel)
								.addGroup(observerSetupLayeredScrollPanesHGroup))
								.addContainerGap())
		);
		observerSetupTabLayout.setVerticalGroup(
				observerSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(observerSetupTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(observerSetupTabLayout.createSequentialGroup()
								.addComponent(observerSetupFileLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(observerSetupLayeredScrollPanesVGroup))
								.addContainerGap())
		);

		modelSetupTabbedPane.addTab(resourceMap.getString("observerSetupTab.TabConstraints.tabTitle"), observerSetupTab); 

		/*
		 * Species Setup and Parameter Tabs
		 */
		speciesSetupTab.setName("speciesSetupTab"); 
		speciesParamTab.setName("speciesParamTab"); 
		String[] speciesTitles = new String[getOpenProject().getFish().size()];
		for(int i=0; i<getOpenProject().getFish().size(); i++){
			speciesComboElements.add(new String[] {getOpenProject().getFish().get(i),"speciesTable"+getOpenProject().getFish().get(i)});
			speciesTitles[i] = getOpenProject().getFish().get(i);
		}
		speciesSetupComboBox.setModel(new javax.swing.DefaultComboBoxModel(speciesTitles));
		speciesSetupComboBox.setName("speciesSetupComboBox"); 
		speciesSetupComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				speciesChangeHandler(evt);
			}
		});
		speciesParamComboBox.setModel(new javax.swing.DefaultComboBoxModel(speciesTitles));
		speciesParamComboBox.setName("speciesParamComboBox"); 
		speciesParamComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				speciesChangeHandler(evt);
			}
		});
		speciesSetupComboLabel.setText(resourceMap.getString("speciesSetupComboLabel.text")); 
		speciesSetupComboLabel.setName("speciesSetupComboLabel"); 
		speciesParamComboLabel.setText(resourceMap.getString("speciesParamComboLabel.text")); 
		speciesParamComboLabel.setName("speciesParamComboLabel"); 

		addSpeButton.setText(resourceMap.getString("addSpeButton.text")); 
		addSpeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		addSpeButton.setName("addSpeButton");
		addSpeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(addSpeButton.getActionListeners().length==0){
			addSpeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					addSpeciesButtonActionPerformed(evt);
				}
			});
		}
		remSpeButton.setText(resourceMap.getString("remSpeButton.text")); 
		remSpeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		remSpeButton.setName("remSpeButton");
		remSpeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(remSpeButton.getActionListeners().length==0){
			remSpeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					removeSpeciesButtonActionPerformed(evt);
				}
			});
		}
		

		speciesSetupTabLayout = new javax.swing.GroupLayout(speciesSetupTab);
		speciesSetupTab.setLayout(speciesSetupTabLayout);
		speciesParamTabLayout = new javax.swing.GroupLayout(speciesParamTab);
		speciesParamTab.setLayout(speciesParamTabLayout);

		speciesSetupLayeredScrollPanesHGroup = speciesSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		speciesSetupLayeredScrollPanesVGroup = speciesSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
		speciesParamLayeredScrollPanesHGroup = speciesParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		speciesParamLayeredScrollPanesVGroup = speciesParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);


		for(int i=0; i<speciesTitles.length; i++){
			// Species Setup Table - speSetup
			speciesSetupTable.add(ParameterTable.getInstance().makeTable("speSetup",speciesTitles[i]));
			speciesSetupTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"speSetup",speciesTitles[i]));
			speciesSetupTable.get(i).removeColumn(speciesSetupTable.get(i).getColumnModel().getColumn(5));
			speciesSetupTable.get(i).removeColumn(speciesSetupTable.get(i).getColumnModel().getColumn(4));
			speciesSetupTable.get(i).setName(speciesComboElements.get(i)[1]); 
			speciesSetupTable.get(i).getTableHeader().setReorderingAllowed(false);
			speciesSetupScrollPane.add(new javax.swing.JScrollPane());
			speciesSetupScrollPane.get(i).setName("speciesScrollPane"+i); 
			speciesSetupScrollPane.get(i).setViewportView(speciesSetupTable.get(i));
			speciesSetupFileLabel.add(new javax.swing.JLabel());
			speciesSetupFileLabel.get(i).setText("<html>"+resourceMap.getString("configFileLabel.text")+" Species.Setup<br>Parameter File: "+
					openProject.getSetupParameters("speParam-"+speciesComboElements.get(i)[0]).getFileName()+"</html>"); 
			speciesSetupFileLabel.get(i).setName("speciesSetupFileLabel"+i); 
			speciesSetupChangeParamButton.add(new JButton());
			speciesSetupChangeParamButton.get(i).setText(resourceMap.getString("changeSpeParamButton.text")); 
			speciesSetupChangeParamButton.get(i).setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			speciesSetupChangeParamButton.get(i).setName("changeSpeParamButton"+i);
			speciesSetupChangeParamButton.get(i).setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			if(speciesSetupChangeParamButton.get(i).getActionListeners().length==0){
				speciesSetupChangeParamButton.get(i).addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						changeSpeParamButtonActionPerformed(evt);
					}
				});
			}
			if(i==0){
				speciesSetupScrollPane.get(i).setVisible(true);
				speciesSetupFileLabel.get(i).setVisible(true);
				speciesSetupChangeParamButton.get(i).setVisible(true);
			}else{
				speciesSetupScrollPane.get(i).setVisible(false);
				speciesSetupFileLabel.get(i).setVisible(false);
				speciesSetupChangeParamButton.get(i).setVisible(false);
			}
			speciesSetupLayeredScrollPanesHGroup.addGroup(speciesSetupTabLayout.createParallelGroup()
					.addComponent(speciesSetupFileLabel.get(i))
					.addComponent(speciesSetupChangeParamButton.get(i))
					.addComponent(speciesSetupScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE));
			speciesSetupLayeredScrollPanesVGroup.addGroup(speciesSetupTabLayout.createSequentialGroup()
					.addComponent(speciesSetupFileLabel.get(i))
					.addComponent(speciesSetupChangeParamButton.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(speciesSetupScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE));

			// Species Param Table - speParam
			speciesParamTable.add(ParameterTable.getInstance().makeTable("speParam",speciesTitles[i]));
			speciesParamTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"speParam",speciesTitles[i]));
			speciesParamTable.get(i).removeColumn(speciesParamTable.get(i).getColumnModel().getColumn(5));
			speciesParamTable.get(i).removeColumn(speciesParamTable.get(i).getColumnModel().getColumn(4));
			speciesParamTable.get(i).setName(speciesComboElements.get(i)[1]); 
			speciesParamTable.get(i).getTableHeader().setReorderingAllowed(false);
			speciesParamScrollPane.add(new javax.swing.JScrollPane());
			speciesParamScrollPane.get(i).setName("speciesParamScrollPane"+i); 
			speciesParamScrollPane.get(i).setViewportView(speciesParamTable.get(i));
			speciesParamFileLabel.add(new javax.swing.JLabel());
			speciesParamFileLabel.get(i).setText(resourceMap.getString("configFileLabel.text")+" "+openProject.getSetupParameters("speParam-"+speciesComboElements.get(i)[0]).getFileName()); 
			speciesParamFileLabel.get(i).setName("speciesParamFileLabel"+i); 
			if(i==0){
				speciesParamScrollPane.get(i).setVisible(true);
				speciesParamFileLabel.get(i).setVisible(true);
			}else{
				speciesParamScrollPane.get(i).setVisible(false);
				speciesParamFileLabel.get(i).setVisible(false);
			}
			speciesParamLayeredScrollPanesHGroup.addGroup(speciesParamTabLayout.createParallelGroup()
					.addComponent(speciesParamFileLabel.get(i))
					.addComponent(speciesParamScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE));
			speciesParamLayeredScrollPanesVGroup.addGroup(speciesParamTabLayout.createSequentialGroup()
					.addComponent(speciesParamFileLabel.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(speciesParamScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE));
		}

		speciesSetupTabLayout.setHorizontalGroup(
				speciesSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(speciesSetupTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(speciesSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(speciesSetupLayeredScrollPanesHGroup)
								.addGroup(speciesSetupTabLayout.createSequentialGroup()
										.addGroup(speciesSetupTabLayout.createParallelGroup()
												.addGroup(speciesSetupTabLayout.createSequentialGroup()
														.addComponent(speciesSetupComboLabel)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(speciesSetupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addComponent(addSpeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(remSpeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addContainerGap())
		);
		speciesSetupTabLayout.setVerticalGroup(
				speciesSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(speciesSetupTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(speciesSetupTabLayout.createParallelGroup().addGroup(
								speciesSetupTabLayout.createSequentialGroup()
								.addGroup(speciesSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(speciesSetupComboLabel)
										.addComponent(speciesSetupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(addSpeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(remSpeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addGroup(speciesSetupTabLayout.createSequentialGroup()
												.addGap(15, 15, 15)
												.addGroup(speciesSetupLayeredScrollPanesVGroup)
												.addContainerGap()))
		);
		modelSetupTabbedPane.addTab(resourceMap.getString("speciesSetupTab.TabConstraints.tabTitle"), null, speciesSetupTab, resourceMap.getString("speciesSetupTab.TabConstraints.tabToolTip")); 

		speciesParamTabLayout.setHorizontalGroup(
				speciesParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(speciesParamTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(speciesParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(speciesParamLayeredScrollPanesHGroup)
								.addGroup(speciesParamTabLayout.createSequentialGroup()
										.addGroup(speciesParamTabLayout.createParallelGroup()
												.addGroup(speciesParamTabLayout.createSequentialGroup()
														.addComponent(speciesParamComboLabel)))
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(speciesParamComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addContainerGap())
		);
		speciesParamTabLayout.setVerticalGroup(
				speciesParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(speciesParamTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(speciesParamTabLayout.createParallelGroup().addGroup(
								speciesParamTabLayout.createSequentialGroup()
								.addGroup(speciesParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(speciesParamComboLabel)
										.addComponent(speciesParamComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addGroup(speciesParamTabLayout.createSequentialGroup()
												.addGap(15, 15, 15)
												.addGroup(speciesParamLayeredScrollPanesVGroup)
												.addContainerGap()))
		);
		modelSetupTabbedPane.addTab(resourceMap.getString("speciesParamTab.TabConstraints.tabTitle"), null, speciesParamTab, resourceMap.getString("speciesParamTab.TabConstraints.tabToolTip")); 

		/*
		 * Habitat Configuration Tab
		 */
		habitatSetupTab.setName("habitatSetupTab"); 
		habitatParamTab.setName("habitatParamTab"); 
		for(int i=0; i<getOpenProject().getHabs().size(); i++){
			habitatComboElements.add(new String[] {getOpenProject().getHabs().get(i),"habitatTable"+getOpenProject().getHabs().get(i)});
		}
		String[] habitatTitles = new String[habitatComboElements.size()];
		for(int i=0; i<habitatComboElements.size(); i++){
			habitatTitles[i] = habitatComboElements.get(i)[0];
		}
		habitatSetupComboBox.setModel(new javax.swing.DefaultComboBoxModel(habitatTitles));
		habitatSetupComboBox.setName("habitatSetupComboBox"); 
		habitatSetupComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				habitatChangeHandler(evt);
			}
		});
		habitatSetupComboLabel.setText(resourceMap.getString("habitatComboLabel.text")); 
		habitatSetupComboLabel.setName("habitatParamComboLabel"); 
		habitatParamComboBox.setModel(new javax.swing.DefaultComboBoxModel(habitatTitles));
		habitatParamComboBox.setName("habitatSetupComboBox"); 
		habitatParamComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				habitatChangeHandler(evt);
			}
		});
		habitatParamComboLabel.setText(resourceMap.getString("habitatComboLabel.text")); 
		habitatParamComboLabel.setName("habitatParamComboLabel"); 

		addHabButton.setText(resourceMap.getString("addHabButton.text")); 
		addHabButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		addHabButton.setName("addHabButton");
		addHabButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(addHabButton.getActionListeners().length==0){
			addHabButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					addHabitatButtonActionPerformed(evt);
				}
			});
		}
		remHabButton.setText(resourceMap.getString("remHabButton.text")); 
		remHabButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		remHabButton.setName("remHabButton");
		remHabButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(remHabButton.getActionListeners().length==0){
			remHabButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					removeHabitatButtonActionPerformed(evt);
				}
			});
		}
		habitatSetupTabLayout = new javax.swing.GroupLayout(habitatSetupTab);
		habitatSetupTab.setLayout(habitatSetupTabLayout);
		habitatParamTabLayout = new javax.swing.GroupLayout(habitatParamTab);
		habitatParamTab.setLayout(habitatParamTabLayout);

		habitatSetupLayeredScrollPanesHGroup = habitatSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		habitatSetupLayeredScrollPanesVGroup = habitatSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
		habitatParamLayeredScrollPanesHGroup = habitatParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		habitatParamLayeredScrollPanesVGroup = habitatParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);

		for(int i=0; i<habitatTitles.length; i++){
			// Habitat Setup Table - habSetup
			habitatSetupTable.add(ParameterTable.getInstance().makeTable("habSetup",habitatTitles[i]));
			habitatSetupTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"habSetup",habitatTitles[i]));
			habitatSetupTable.get(i).removeColumn(habitatSetupTable.get(i).getColumnModel().getColumn(5));
			habitatSetupTable.get(i).removeColumn(habitatSetupTable.get(i).getColumnModel().getColumn(4));
			habitatSetupTable.get(i).setName(habitatComboElements.get(i)[1]); 
			habitatSetupTable.get(i).getTableHeader().setReorderingAllowed(false);
			habitatSetupScrollPane.add(new javax.swing.JScrollPane());
			habitatSetupScrollPane.get(i).setName("habitatScrollPane"+i); 
			habitatSetupScrollPane.get(i).setViewportView(habitatSetupTable.get(i));
			habitatSetupFileLabel.add(new javax.swing.JLabel());
			habitatSetupFileLabel.get(i).setText(resourceMap.getString("configFileLabel.text")+" Reach.Setup"); 
			habitatSetupFileLabel.get(i).setName("habitatSetupFileLabel"+i); 
			if(i==0){
				habitatSetupScrollPane.get(i).setVisible(true);
				habitatSetupFileLabel.get(i).setVisible(true);
			}else{
				habitatSetupScrollPane.get(i).setVisible(false);
				habitatSetupFileLabel.get(i).setVisible(false);
			}
			habitatSetupLayeredScrollPanesHGroup.addGroup(habitatSetupTabLayout.createParallelGroup()
					.addComponent(habitatSetupFileLabel.get(i))
					.addComponent(habitatSetupScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE));
			habitatSetupLayeredScrollPanesVGroup.addGroup(habitatSetupTabLayout.createSequentialGroup()
					.addComponent(habitatSetupFileLabel.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(habitatSetupScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE));

			// Habitat Param Table - habParam
			habitatParamTable.add(ParameterTable.getInstance().makeTable("habParam",habitatTitles[i]));
			habitatParamTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"habParam",habitatTitles[i]));
			habitatParamTable.get(i).removeColumn(habitatParamTable.get(i).getColumnModel().getColumn(5));
			habitatParamTable.get(i).removeColumn(habitatParamTable.get(i).getColumnModel().getColumn(4));
			habitatParamTable.get(i).setName(habitatComboElements.get(i)[1]); 
			habitatParamTable.get(i).getTableHeader().setReorderingAllowed(false);
			habitatParamScrollPane.add(new javax.swing.JScrollPane());
			habitatParamScrollPane.get(i).setName("habitatParamScrollPane"+i); 
			habitatParamScrollPane.get(i).setViewportView(habitatParamTable.get(i));
			habitatParamFileLabel.add(new javax.swing.JLabel());
			habitatParamFileLabel.get(i).setText(resourceMap.getString("configFileLabel.text")+" "+openProject.getSetupParameters("habParam-"+habitatComboElements.get(i)[0]).getFileName()); 
			habitatParamFileLabel.get(i).setName("habitatParamFileLabel"+i); 
			if(i==0){
				habitatParamScrollPane.get(i).setVisible(true);
				habitatParamFileLabel.get(i).setVisible(true);
			}else{
				habitatParamScrollPane.get(i).setVisible(false);
				habitatParamFileLabel.get(i).setVisible(false);
			}
			habitatParamLayeredScrollPanesHGroup.addGroup(habitatParamTabLayout.createParallelGroup()
					.addComponent(habitatParamFileLabel.get(i))
					.addComponent(habitatParamScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE)); 
			habitatParamLayeredScrollPanesVGroup.addGroup(habitatParamTabLayout.createSequentialGroup()
					.addComponent(habitatParamFileLabel.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(habitatParamScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.height.int"),  Short.MAX_VALUE));
		}

		habitatSetupTabLayout.setHorizontalGroup(
				habitatSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(habitatSetupTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(habitatSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(habitatSetupLayeredScrollPanesHGroup)
								.addGroup(habitatSetupTabLayout.createSequentialGroup()
										.addGroup(habitatSetupTabLayout.createParallelGroup()
												.addGroup(habitatSetupTabLayout.createSequentialGroup()
														.addComponent(habitatSetupComboLabel)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(habitatSetupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addComponent(addHabButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(remHabButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addContainerGap())
		);
		habitatSetupTabLayout.setVerticalGroup(
				habitatSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(habitatSetupTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(habitatSetupTabLayout.createSequentialGroup()
								.addGroup(habitatSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(habitatSetupComboLabel)
										.addComponent(habitatSetupComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(addHabButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(remHabButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(15, 15, 15)
										.addGroup(habitatSetupLayeredScrollPanesVGroup)
										.addContainerGap())
		);
		modelSetupTabbedPane.addTab(resourceMap.getString("habitatSetupTab.TabConstraints.tabTitle"), habitatSetupTab); 

		habitatParamTabLayout.setHorizontalGroup(
				habitatParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(habitatParamTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(habitatParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(habitatParamLayeredScrollPanesHGroup)
								.addGroup(habitatParamTabLayout.createSequentialGroup()
										.addGroup(habitatParamTabLayout.createParallelGroup()
												.addGroup(habitatParamTabLayout.createSequentialGroup()
														.addComponent(habitatParamComboLabel)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(habitatParamComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
														.addContainerGap())
		);
		habitatParamTabLayout.setVerticalGroup(
				habitatParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(habitatParamTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(habitatParamTabLayout.createSequentialGroup()
								.addGroup(habitatParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(habitatParamComboLabel)
										.addComponent(habitatParamComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(15, 15, 15)
										.addGroup(habitatParamLayeredScrollPanesVGroup)
										.addContainerGap())
		);
		modelSetupTabbedPane.addTab(resourceMap.getString("habitatParamTab.TabConstraints.tabTitle"), habitatParamTab); 

		/*
		 * Experiment Control Configuration Tab
		 */
		experimentControlTab.setName("experimentControlTab"); 

		javax.swing.GroupLayout experimentControlTabLayout = new javax.swing.GroupLayout(experimentControlTab);
		experimentControlTab.setLayout(experimentControlTabLayout);

		clearExpParamButton.setText(resourceMap.getString("clearExpParamButton.text")); 
		clearExpParamButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		clearExpParamButton.setName("clearExpParamButton");
		clearExpParamButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(clearExpParamButton.getActionListeners().length==0){
			clearExpParamButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					clearExperimentButtonActionPerformed(evt);
				}
			});
		}


		ParallelGroup experimentControlLayeredScrollPanesHGroup = experimentControlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		ParallelGroup experimentControlScrollPanesVGroup = experimentControlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
		// Experiment Control Setup Table - expSetup
		experimentControlTable = ParameterTable.getInstance().makeTable("expSetup","");
		experimentControlTable.setModel(ParameterTable.getInstance().getModel(getOpenProject(),"expSetup",""));
		experimentControlTable.removeColumn(experimentControlTable.getColumnModel().getColumn(5));
		experimentControlTable.removeColumn(experimentControlTable.getColumnModel().getColumn(4));
		experimentControlTable.setName("expSetupTable");
		experimentControlTable.getTableHeader().setReorderingAllowed(false);

		javax.swing.JLabel experimentControlFileLabel = new javax.swing.JLabel();
		experimentControlFileLabel.setText(resourceMap.getString("configFileLabel.text")+" Experiment.Setup"); 
		experimentControlFileLabel.setName("experimentControlFileLabel"); 

		experimentControlScrollPane.setName("experimentControlScrollPane"); 
		experimentControlScrollPane.setViewportView(experimentControlTable);
		experimentControlScrollPane.setVisible(true);

		experimentControlTabLayout.setHorizontalGroup(
				experimentControlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(experimentControlTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(experimentControlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(experimentControlTabLayout.createSequentialGroup()
										.addComponent(experimentControlFileLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(clearExpParamButton))
										.addComponent(experimentControlScrollPane,0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE))
										.addContainerGap())
		);
		experimentControlTabLayout.setVerticalGroup(
				experimentControlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(experimentControlTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(experimentControlTabLayout.createSequentialGroup()
								.addGroup(experimentControlTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
										.addComponent(experimentControlFileLabel)
										.addComponent(clearExpParamButton))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(experimentControlScrollPane,0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE))
										.addContainerGap())
		);
		experimentTabbedPane.addTab(resourceMap.getString("experimentControlTab.TabConstraints.tabTitle"), experimentControlTab); 

		/*
		 * Experiment Parameters Configuration Tab
		 */
		experimentParamTab.setName("experimentParamTab"); 
		for(String expParamKey : getOpenProject().getExperimentParamKeys()){
			experimentParamComboElements.add(new String[] {expParamKey,"experimentParamTable"+expParamKey});
		}
		String[] experimentParamTitles = new String[experimentParamComboElements.size()];
		for(int i=0; i<experimentParamComboElements.size(); i++){
			experimentParamTitles[i] = experimentParamComboElements.get(i)[0];
		}
		experimentParamComboBox.setModel(new javax.swing.DefaultComboBoxModel(experimentParamTitles));
		experimentParamComboBox.setName("experimentParamComboBox"); 
		experimentParamComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				experimentParamChangeHandler(evt);
			}
		});
		experimentParamComboLabel.setText(resourceMap.getString("experimentParamComboLabel.text")); 
		experimentParamComboLabel.setName("experimentParamParamComboLabel"); 

		addExpParamButton.setText(resourceMap.getString("addExpParamButton.text")); 
		addExpParamButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		addExpParamButton.setName("addExpParamButton");
		addExpParamButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(addExpParamButton.getActionListeners().length==0){
			addExpParamButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					addExperimentParamButtonActionPerformed(evt);
				}
			});
		}
		remExpParamButton.setText(resourceMap.getString("remExpParamButton.text")); 
		remExpParamButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		remExpParamButton.setName("remExpParamButton");
		remExpParamButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(remExpParamButton.getActionListeners().length==0){
			remExpParamButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					removeExperimentParamButtonActionPerformed(evt);
				}
			});
		}

		javax.swing.GroupLayout experimentParamTabLayout = new javax.swing.GroupLayout(experimentParamTab);
		experimentParamTab.setLayout(experimentParamTabLayout);

		experimentParamLayeredScrollPanesHGroup = experimentParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
		experimentParamLayeredScrollPanesVGroup = experimentParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
		// Experiment Parameter Setup Tables - expParam
		for(int i=0; i<experimentParamTitles.length; i++){
			// Experiment Param Table - expParam
			experimentParamTable.add(ParameterTable.getInstance().makeTable("expParam",experimentParamTitles[i]));
			experimentParamTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"expParam",experimentParamTitles[i]));
			experimentParamTable.get(i).removeColumn(experimentParamTable.get(i).getColumnModel().getColumn(5));
			experimentParamTable.get(i).setName(experimentParamComboElements.get(i)[1]); 
			experimentParamTable.get(i).getTableHeader().setReorderingAllowed(false);
			experimentParamScrollPane.add(new javax.swing.JScrollPane());
			experimentParamScrollPane.get(i).setName("experimentParamScrollPane"+i); 
			experimentParamScrollPane.get(i).setViewportView(experimentParamTable.get(i));
			if(i==0){
				experimentParamScrollPane.get(i).setVisible(true);
			}else{
				experimentParamScrollPane.get(i).setVisible(false);
			}
			experimentParamLayeredScrollPanesHGroup.addComponent(experimentParamScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE);
			experimentParamLayeredScrollPanesVGroup.addComponent(experimentParamScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE);
		}
		experimentParamTabLayout.setHorizontalGroup(
				experimentParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(experimentParamTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(experimentParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(experimentParamLayeredScrollPanesHGroup)
								.addGroup(experimentParamTabLayout.createSequentialGroup()
										.addComponent(experimentParamComboLabel)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(experimentParamComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(addExpParamButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(remExpParamButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap())
		);
		experimentParamTabLayout.setVerticalGroup(
				experimentParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(experimentParamTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(experimentParamTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(experimentParamComboLabel)
								.addComponent(experimentParamComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(addExpParamButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(remExpParamButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(15, 15, 15)
								.addGroup(experimentParamLayeredScrollPanesVGroup)
								.addContainerGap())
		);
		experimentTabbedPane.addTab(resourceMap.getString("experimentParamTab.TabConstraints.tabTitle"), experimentParamTab); 

		/*
		 * Limiting Factors Tool Setup Tab
		 */
		lftSetupTab.setName("lftControlTab"); 

		javax.swing.GroupLayout lftSetupTabLayout = new javax.swing.GroupLayout(lftSetupTab);
		lftSetupTab.setLayout(lftSetupTabLayout);

		// LFT Setup Table - lftSetup
		lftSetupTable = ParameterTable.getInstance().makeTable("lftSetup","");
		lftSetupTable.setModel(ParameterTable.getInstance().getModel(getOpenProject(),"lftSetup",""));
		lftSetupTable.removeColumn(lftSetupTable.getColumnModel().getColumn(5));
		lftSetupTable.removeColumn(lftSetupTable.getColumnModel().getColumn(4));
		lftSetupTable.setName("lftSetupTable");
		lftSetupTable.getTableHeader().setReorderingAllowed(false);

		javax.swing.JLabel lftSetupFileLabel = new javax.swing.JLabel();
		lftSetupFileLabel.setText(resourceMap.getString("configFileLabel.text")+" LimitingFactorsTool.Setup"); 
		lftSetupFileLabel.setName("lftSetupFileLabel"); 
		
		lftRunsPerExpLabel.setText(resourceMap.getString("lftSetupTab.runsPerExperiment.label")+this.lftTool.getLFTRunsPerExperiment()); 
		lftRunsPerExpLabel.setName("lftRunsPerExpLabel"); 

		startLFTButton.setText(resourceMap.getString("startLFTButton.text")); 
		startLFTButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		startLFTButton.setName("startLFTButton");
		startLFTButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(startLFTButton.getActionListeners().length==0){
			startLFTButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					startLFTActionPerformed(evt);
				}
			});
		}
		killLFTButton.setText(resourceMap.getString("killLFTButton.text")); 
		killLFTButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		killLFTButton.setName("killLFTButton");
		killLFTButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(killLFTButton.getActionListeners().length==0){
			killLFTButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					killLFTActionPerformed(evt);
				}
			});
		}
		postProcessLFTButton.setText(resourceMap.getString("postProcessLFTButton.text")); 
		postProcessLFTButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		postProcessLFTButton.setName("postProcessLFTButton");
		postProcessLFTButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		if(postProcessLFTButton.getActionListeners().length==0){
			postProcessLFTButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					postProcessLFTActionPerformed();
				}
			});
		}
		lftSetupScrollPane.setName("lftSetupScrollPane"); 
		lftSetupScrollPane.setViewportView(lftSetupTable);
		lftSetupScrollPane.setVisible(true);
		lftSetupTabLayout.setHorizontalGroup(
				lftSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(lftSetupTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(lftSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(lftRunsPerExpLabel)
								.addGroup(
										lftSetupTabLayout.createSequentialGroup()
											.addComponent(lftSetupFileLabel)
											.addComponent(startLFTButton)
											.addComponent(killLFTButton)
											.addComponent(postProcessLFTButton))
								.addComponent(lftSetupScrollPane,0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE))
								.addContainerGap())
		);
		lftSetupTabLayout.setVerticalGroup(
				lftSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(lftSetupTabLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(lftSetupTabLayout.createSequentialGroup()
								.addComponent(lftRunsPerExpLabel)
								.addGroup(lftSetupTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
										.addComponent(lftSetupFileLabel)
										.addComponent(startLFTButton)
										.addComponent(killLFTButton)
										.addComponent(postProcessLFTButton))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(lftSetupScrollPane,0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE))
						.addContainerGap())
		);
		lftTabbedPane.addTab(resourceMap.getString("lftSetupTab.TabConstraints.tabTitle"), lftSetupTab); 
		
		//GroupLayout lftExecutionTabLayout = new javax.swing.GroupLayout(lftExecutionTabbedPane);
		//lftExecutionTabbedPane.setLayout(lftExecutionTabLayout);

		lftTabbedPane.addTab(resourceMap.getString("lftExecutionTab.TabConstraints.tabTitle"), lftExecutionTabbedPane); 
		if(lftTextAreas.size()==0){
			lftTool.createLFTOutputTabs(lftExecutionTabbedPane);
		}
	}
	protected void changeSpeParamButtonActionPerformed(ActionEvent evt) {
		Integer speIndex = Integer.parseInt(((JButton)evt.getSource()).getName().substring(20));
		ChangeSpeciesParameterFile changeParamFile = new ChangeSpeciesParameterFile(this, getOpenProject(),speIndex);
		changeParamFile.setLocationRelativeTo(InSTREAMConfigApp.getApplication().getMainFrame());
		InSTREAMConfigApp.getApplication().show(changeParamFile);
	}

	// MENU ITEM ACTIONS
	private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		saveProject();
	}
	private void saveasMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		File oldProjectDir = projectDir;
		if(saveProjectAs()){
			saveProject();
		}
		// If the new directory is different from what the previous, non-null, location, copy all INFILES to the new directory 
		if(oldProjectDir != null && !projectDir.getAbsolutePath().equals(oldProjectDir.getAbsolutePath())){
			ArrayList<String> alreadyCopiedInfiles = new ArrayList<String>();
			ArrayList<String> filesToCopy = new ArrayList<String>();
			filesToCopy.addAll(this.openProject.getAllInfileNames());
			for(String infileName : filesToCopy){
				if(!infileName.trim().equals("")){
					File oldInfile = new File(oldProjectDir.getAbsolutePath() + "/" + infileName);
					if(oldInfile.exists()){
						File newInfile = new File(projectDir.getAbsolutePath() + "/" + infileName);
						if(newInfile.exists()){
							if(alreadyCopiedInfiles.contains(infileName))continue;
							int result = JOptionPane.showOptionDialog(this.parentFrame, "<html>Attempting to copy input file named: <i>\""+oldInfile.getName()+
									"\"</i><br>from previous project directory: <i>\""+ oldProjectDir.getName() + "\"</i><br>to the new project directory: <i>\""+ projectDir.getName() + "\"</i>,<br> but a file " +
									"with that name already exists in the new project directory, should the file be overwritten?</html>","Warning",
									JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,new String[]{"Overwrite File in New Project Directory","Use Existing File"}, "blah"); 
							if(result!=0){
								alreadyCopiedInfiles.add(infileName);
								continue;
							}
						}
						try {
							MetaProject.getInstance().copy(oldInfile, newInfile);
							alreadyCopiedInfiles.add(infileName);
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println(infileName);
					}
				}
			}
		}
	}
	public void terminateExperiment(ActionEvent evt){
		LaunchInsamloExecutable launcher = lftLaunchers.get(((JButton)evt.getSource()).getName());
		if(launcher!=null && launcher.getProcess()!=null)launcher.getProcess().destroy();
	}
	private void configureMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		modelSetupTabbedPane.setVisible(true);
		modelSummaryScrollPane.setVisible(false);
		experimentTabbedPane.setVisible(false);
	}
	private void summaryActionPerformed(java.awt.event.ActionEvent evt) {
		revealSummaryPane();
	}
	private void revealSummaryPane(){
		modelSetupTabbedPane.setVisible(false);
		modelSummaryScrollPane.setVisible(true);
		experimentTabbedPane.setVisible(false);
		lftTabbedPane.setVisible(false);
		updateModelSummary();
	}
	public void updateModelSummary(){
		if(this.openProject==null){
			modelSummarySubtitle.setText(resourceMap.getString("modelSummarySubtitle.text"));
		}else{
			String theText = "<html>";
			theText += "<b>Species:</b> ";
			if(this.openProject.getFish().size()>0){
				for(String fish : this.openProject.getFish()){
					theText += fish+", ";
				}
				theText = theText.substring(0, theText.length()-2)+"<br>";
			}else{
				theText += "<i>No species have been created</i><br>";				
			}
			theText += "<br><b>Reaches:</b> ";
			if(this.openProject.getHabs().size()>0){
				theText += "<br><table border=0 cellpadding=2>";
				for(String hab : this.openProject.getHabs()){
					theText += "<tr><td colspan=3>"+hab+"</td></tr>";
					SetupParameters habSetup = openProject.getSetupParameters("habSetup-"+hab);
					theText += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>Flow File:</td><td><i>"+habSetup.getParameter("flowFile").getParameterValue()+"<i></td></tr>";
					theText += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>Temperature File:</td><td><i>"+habSetup.getParameter("temperatureFile").getParameterValue()+"<i></td></tr>";
					theText += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>Turbidity File:</td><td><i>"+habSetup.getParameter("turbidityFile").getParameterValue()+"<i></td></tr>";
				}
				theText = theText.substring(0, theText.length()-2)+"</td></tr></table><br>";
			}else{
				theText += "<i>No reaches have been created</i><br>";				
			}
			SetupParameters modSetup = openProject.getSetupParameters("modSetup-");
			String startDate = modSetup.getParameter("runStartDate").getParameterValue();
			String endDate = modSetup.getParameter("runEndDate").getParameterValue();
			theText += "<b>Run Start/End Dates:</b> start: "+startDate+" --- end: "+endDate;
			theText += "<br><br><b>Experiment Setup</b>";
			theText += "<br><table border=0 cellpadding=2>";
			SetupParameters expSetup = openProject.getSetupParameters("expSetup-");
			theText += "<tr><td>Number of Scenarios:</td><td>"+expSetup.getParameter("numberOfScenarios").getParameterValue()+"</td></tr>";
			theText += "<tr><td>Number of Replicates:</td><td>"+expSetup.getParameter("numberOfReplicates").getParameterValue()+"</td></tr>";
			if(MetaProject.getInstance().getVersion().equals("instream")){
				theText += "<tr><td>Number of Year Shuffler Replicates:</td><td>"+expSetup.getParameter("numberOfYearShufflerReplicates").getParameterValue()+"<br></td></tr>";
			}
			theText += "<tr><td colspan=2>Parameters varied in experiment and <i>their values</i>:</td></tr>";
			if(openProject.exps.size()>0){
				for(String exp : openProject.exps){
					theText += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;"+exp+":</td><td><i>";
					for(Parameter value : openProject.getExperimentParameters(exp).getValues()){
						theText += value.getParameterValue()+", ";
					}
					theText = theText.substring(0,theText.length()-2);	
					theText += "</i></td></tr>";					
				}
			}else{
				theText += "<tr><td colspan=2>&nbsp;&nbsp;&nbsp;&nbsp;<i>no parameters varied in the experiment</td></tr>";
			}
			theText += "</table><br>";
			theText += "</html>";
			modelSummarySubtitle.setText(theText);
		}
	}
	private boolean okToRunModel(){
		String osName = System.getProperty("os.name");
		return osName.contains("Windows");
	}
	private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {
		runMenuItemActionPerformed(evt);
	}
	private void runMenuItemActionPerformed(java.awt.event.ActionEvent evt){
		runMenuItemActionPerformed(evt, false);
	}
	private boolean cancelDueToErrors(){
		if(this.openProject.errors.size()>0){
			String[] choices = {"Run with Errors","Cancel and show the errors","Cancel"};
			int result = JOptionPane.showOptionDialog(this.parentFrame, "Errors exist in the project, run anyway?", "Warning", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
			if(result==1){
				errorWarningButtonActionPerformed(new ActionEvent(this, 0,""));
				return true;
			}else if(result!=0){
				return true;
			}
		}
		return false;
	}
	private void runMenuItemActionPerformed(java.awt.event.ActionEvent evt,boolean useGraphics) {
		if(cancelDueToErrors()){
			return;
		}
		if(MetaProject.getInstance().hasProjectChanged()){
			String[] choices = {"Save and Run", "CANCEL"};
			int result = JOptionPane.showOptionDialog(this.parentFrame, "You have unsaved changes...", "Warning", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
			if(result==-1 || result==1){
				System.out.println("canceled run...");
				return;
			}
			if(result==0){
				System.out.println("saving first...");
				if(!saveProject())return;
			}
		}
		updateModelSummary();
		modelSetupTabbedPane.setVisible(false);
		modelSummaryScrollPane.setVisible(true);
		experimentTabbedPane.setVisible(false);
		runButton.setEnabled(false);
		runMenuItem.setEnabled(false);
		runWithGraphicsButton.setEnabled(false);
		runWithGraphicsMenuItem.setEnabled(false);
		try {
			// launch EXE and grab stdout and stderr
			modelLauncher = new LaunchInsamloExecutable(projectDir.getAbsolutePath(),this,useGraphics,false,showConsoleTextArea,MetaProject.getInstance().getAppTitle()+" MODEL");
			StreamGobbler s1 = new StreamGobbler ("stdout", modelLauncher.getProcess().getInputStream(),showConsoleTextArea,projectDir.getAbsolutePath()+"/Console_Output.Out",newline);
			StreamGobbler s2 = new StreamGobbler ("stderr", modelLauncher.getProcess().getErrorStream(),showConsoleTextArea,projectDir.getAbsolutePath()+"/Console_Output.Out",newline);
			s1.start ();
			s2.start ();
			//			OutputStream killStream = modelLauncher.getProcess().getOutputStream();
		}catch (Exception err) {
			err.printStackTrace();
			System.exit(ERROR);
		}		
	}
	public void modelRunCompleted(LaunchInsamloExecutable launcher){
		//JOptionPane.showMessageDialog(null, "The inSALMO model has terminated.");
		if(launcher!=null && launcher.getProcess().exitValue()>=0)launcher.textArea.append(newline+newline+"##############################"+newline+launcher.getName()+" TERMINATED"+newline+"##############################");
		if(launcher.isLFT){
			this.lftLaunchers.remove(launcher.getName());
			if(this.lftLaunchers.size()==0){
				startLFTButton.setEnabled(true);
				if(!lftTool.getTerminatedForcefully()){
					int result = JOptionPane.showOptionDialog(this.parentFrame, "<html><h1>LFT Experiment Runs Complete</h1><br><br>" +
							"Process and view the results?  You can choose to do this later by clicking \"Process/View Results\" on the<br>" +
							"Liminting Factors Page )</html>","Confirm",
							JOptionPane.YES_NO_OPTION,JOptionPane.YES_NO_OPTION,null,new String[]{"Process and View Results Now","Not now."}, "blah"); 
					if(result==0)postProcessLFTActionPerformed();
				}
			}
		}else{
			if(okToRunModel()){
				runButton.setEnabled(true);
				runWithGraphicsButton.setEnabled(true);
				runMenuItem.setEnabled(true);
				runWithGraphicsMenuItem.setEnabled(true);
			}
			viewResultsButton.setEnabled(true);
			viewResultsMenuItem.setEnabled(true);
		}
	}
	private void postProcessLFTActionPerformed(){
		try {
			String result = lftTool.postProcessResults();
			if(!result.equals("")){
				int choice = JOptionPane.showOptionDialog(this.parentFrame, "<html><body><h2>Post Processing Error Encountered</h2>" +
						"The following errors were encountered during post processing:<font color='red'>"+result+"</font><br>View results anyway?</body></html>","Confirm",
						JOptionPane.YES_NO_OPTION,JOptionPane.YES_NO_OPTION,null,new String[]{"View Results Now","Cancel"}, "blah");
				if(choice!=0)return;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.parentFrame,"<html><body><h2>Limiting Factors Tool Post Processing Cancelled</h2>" +
					"The following error was detected:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
			return;
		}
		try {
			File destinationFile = new File(projectDir.getAbsolutePath()+"/LFT_Setup.xlsm");
			MetaProject.getInstance().copy(new File(MetaProject.getInstance().getApplicationDir()+"/DefaultProject/LFT_Setup.xlsm"),destinationFile);
			BareBonesBrowserLaunch.openURL("file://"+destinationFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void showConsoleClearButtonActionPerfomed(java.awt.event.ActionEvent evt){
		showConsoleTextArea.setText("");
	}
	private void terminateModelRunButtonActionPerfomed(java.awt.event.ActionEvent evt){
		if(modelLauncher!=null && modelLauncher.getProcess()!=null)modelLauncher.getProcess().destroy();
	}
	private void runWithGraphicsButtonActionPerformed(java.awt.event.ActionEvent evt) {
		runMenuItemActionPerformed(evt, true);
	}
	private void runWithGraphicsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
		runMenuItemActionPerformed(evt, true);
	}
	private void experimentActionPerformed(java.awt.event.ActionEvent evt) {
		modelSetupTabbedPane.setVisible(false);
		modelSummaryScrollPane.setVisible(false);
		experimentTabbedPane.setVisible(true);
		lftTabbedPane.setVisible(false);
	}
	private void lftActionPerformed(){
		modelSetupTabbedPane.setVisible(false);
		modelSummaryScrollPane.setVisible(false);
		experimentTabbedPane.setVisible(false);
		lftTabbedPane.setVisible(true);
	}
	private void addExperimentParamButtonActionPerformed(java.awt.event.ActionEvent evt){
		newParamName = null;
		JFrame mainFrame = InSTREAMConfigApp.getApplication().getMainFrame();
		NewExperimentParameter expParamAdder = new NewExperimentParameter(this, getOpenProject());
		expParamAdder.setLocationRelativeTo(mainFrame);
		InSTREAMConfigApp.getApplication().show(expParamAdder);
	}
	private void updateErrorWarningLinkButton(java.awt.event.ActionEvent evt){
		Integer numErr = 0; 
		Integer numWarn = 0; 
		if(evt.getSource().getClass().equals(Project.class)){
			numErr = ((Project)evt.getSource()).getNumErrors();
			numWarn = ((Project)evt.getSource()).getNumWarnings();
		}
		String errorStr = "Errors";
		if(numErr==1)errorStr="Error";
		String warnStr = "Warnings";
		if(numWarn==1)warnStr="Warning";
		errorWarningButton.setText(numErr+" "+errorStr+" / "+numWarn+" "+warnStr);
		if(numErr+numWarn==0){
			errorWarningButton.setEnabled(false);
		}else{
			errorWarningButton.setEnabled(true);
		}
	}
	private void errorWarningButtonActionPerformed(java.awt.event.ActionEvent evt){
		JFrame mainFrame = InSTREAMConfigApp.getApplication().getMainFrame();
		showErrorWarnings = new ShowErrorsWarnings(this, getOpenProject());
		showErrorWarnings.setLocationRelativeTo(this.parentFrame);
		InSTREAMConfigApp.getApplication().show(showErrorWarnings);
	}
	public void addExperimentParamSubmitted(String newName,String newInstanceName,String newClassName){
		this.newParamName = newName;
		String newParamKey = null;
		if(newInstanceName.equals("NONE")){
			newParamKey = newName+" (ALL)";
		}else{
			newParamKey = newName+" ("+newInstanceName+")";
		}
		if(newParamName!=null){
			// Error check the input
			if(this.getOpenProject().getExperimentParameters(newParamKey)!=null){
				JOptionPane.showMessageDialog(experimentParamTab, "The parameter '"+newParamKey+"' already exists in this experiment.");
				return;
			}else if(MetaProject.getInstance().getMetaParameter(newParamName)==null){
				JOptionPane.showMessageDialog(experimentParamTab, "The parameter '"+newParamName+"' is not a valid parameter that can be used in an experiment.");
				return;
			}
			ExperimentParameter expParam = new ExperimentParameter();
			expParam.setClassName(newClassName);
			expParam.setInstanceName(newInstanceName);
			expParam.setParamName(newParamName);
			expParam.setValueType(MetaProject.getInstance().getMetaParameter(newParamName).getDataTypeString());
			Integer numScenarios = this.getOpenProject().getSetupParameters("expSetup-").getParameter("numberOfScenarios").getParameterIntegerValue();
			if(numScenarios!=null){
				String defaultValue = MetaProject.getInstance().getMetaParameter(newParamName).getDefaultValue();
				for(int i=0; i<numScenarios; i++){
					expParam.addValue(defaultValue);
				}
			}
			this.getOpenProject().addExperimentParameter(expParam);
			experimentParamTable.add(ParameterTable.getInstance().makeTable("expParam",newParamKey));
			int i = experimentParamTable.size()-1;
			experimentParamTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"expParam",newParamKey));
			experimentParamTable.get(i).removeColumn(experimentParamTable.get(i).getColumnModel().getColumn(5));
			experimentParamTable.get(i).setName("expParam"+experimentParamComboElements.size()); 
			experimentParamTable.get(i).getTableHeader().setReorderingAllowed(false);
			experimentParamScrollPane.add(new javax.swing.JScrollPane());
			experimentParamScrollPane.get(i).setName("experimentParamScrollPane"+i); 
			experimentParamScrollPane.get(i).setViewportView(experimentParamTable.get(i));
			experimentParamScrollPane.get(i).setVisible(true);
			experimentParamLayeredScrollPanesHGroup.addComponent(experimentParamScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE);
			experimentParamLayeredScrollPanesVGroup.addComponent(experimentParamScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE);
			experimentParamComboElements.add(new String[] {newParamKey,"expParam"+experimentParamComboElements.size()});
			experimentParamComboBox.addItem(newParamKey);
			experimentParamComboBox.setSelectedIndex(experimentParamComboElements.size()-1);
			experimentParamTab.revalidate();
			MetaProject.getInstance().setProjectChanged(true);
		}
	}
	public void setNewSpeciesName(String newName){
		this.newSpeciesName = newName;
	}
	public void setNewReachName(String newName){
		this.newReachName = newName;
	}
	private void startLFTActionPerformed(ActionEvent evt) {
		String projDir = this.projectDir.getAbsolutePath();
		try{
			this.parentFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			startLFTButton.setEnabled(false);
			lftTool.setupLFT();
			lftTabbedPane.setSelectedIndex(1);
			lftTool.setTerminatedForcefully(false);
			lftTool.executeLFT();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.parentFrame,"<html><body><font size=+2><b>Limiting Factors Tool Execution Cancelled</b></font><br/><br/>The following error was encountered:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
			e.printStackTrace();
			startLFTButton.setEnabled(true);
		}finally{
			closeProject("Close");
			openProject(new File(projDir));
			lftActionPerformed();
			this.parentFrame.setCursor(Cursor.getDefaultCursor());
		}
	}
	private void killLFTActionPerformed(ActionEvent evt){
		Enumeration launchers = this.lftLaunchers.elements();
		while(launchers.hasMoreElements()){
			LaunchInsamloExecutable launcher = (LaunchInsamloExecutable)launchers.nextElement();
			if(launcher!=null && launcher.getProcess()!=null)launcher.getProcess().destroy();
		}
		lftTool.setTerminatedForcefully(true);
	}
	private void clearExperimentButtonActionPerformed(java.awt.event.ActionEvent evt){
		int response = JOptionPane.showConfirmDialog(this.parentFrame, "<html><body><b>Clear experiment?</b> This will remove all parameters " +
					"that are controlled by the experiment <br>manager and set the number of replicates to 1.</html></body>","Confirm Clear Experiment",JOptionPane.YES_OPTION);
		if(response==0){
			clearExperiment();
		}
	}
	public void clearExperiment(){
		ArrayList<String> expParamKeys = (ArrayList<String>) getOpenProject().getExperimentParamKeys().clone();
		for (String expParamKey : expParamKeys) {
			removeExperimentParam(expParamKey);
		}
		ParameterTable.getInstance().changeValueOfVariable(experimentControlTable.getModel(), "numberOfReplicates", "1");
		ParameterTable.getInstance().changeValueOfVariable(experimentControlTable.getModel(), "numberOfScenarios", "1");
		ParameterTable.getInstance().changeValueOfVariable(experimentControlTable.getModel(), "numberOfYearShufflerReplicates", "0");
	}
	private void removeExperimentParamButtonActionPerformed(java.awt.event.ActionEvent evt){
		if(experimentParamComboElements.size()==0)return;
		Object[] paramTitles = new Object[experimentParamComboElements.toArray().length];
		int selectedParamIndex;
		for (int i = 0; i < experimentParamComboElements.size(); i++) {
			paramTitles[i] = experimentParamComboElements.get(i)[0];
		}
		Object removeParamName = JOptionPane.showInputDialog(this.parentFrame,"Select the name of parameter to remove","Select the name of parameter to remove",JOptionPane.QUESTION_MESSAGE,null,paramTitles,paramTitles[experimentParamComboBox.getSelectedIndex()]);
		if(removeParamName!=null){
			removeExperimentParam((String)removeParamName);
		}
	}
	private void removeExperimentParam(String removeParamName){
		int foundIndex = -1;
		for (int i = 0; i < experimentParamComboElements.size(); i++) {
			if(removeParamName.equals(experimentParamComboElements.get(i)[0])){
				foundIndex = i;
			}
		}
		experimentParamComboElements.remove(foundIndex);
		experimentParamComboBox.removeItemAt(foundIndex);
		experimentParamScrollPane.get(foundIndex).setVisible(false);
		experimentParamScrollPane.remove(foundIndex);
		experimentParamTable.remove(foundIndex);
		experimentParamTab.revalidate();
		if(experimentParamComboBox.getItemCount()>0){
			experimentParamComboBox.setSelectedIndex(0);
			experimentParamScrollPane.get(0).setVisible(true);
		}
		this.getOpenProject().removeExperimentParameter(removeParamName);
		MetaProject.getInstance().setProjectChanged(true);
	}
	private void addSpeciesButtonActionPerformed(java.awt.event.ActionEvent evt){
		newSpeciesName = null;
		JFrame mainFrame = InSTREAMConfigApp.getApplication().getMainFrame();
		NewFishOrReach fishOrReachAdder = new NewFishOrReach(this, getOpenProject(),"species");
		fishOrReachAdder.setLocationRelativeTo(mainFrame);
		InSTREAMConfigApp.getApplication().show(fishOrReachAdder);
	}
	public void addSpeciesSubmitted(){
		if(newSpeciesName!=null){
			speciesParamTable.add(ParameterTable.getInstance().makeTable("speParam",newSpeciesName));
			int i = speciesParamTable.size()-1;
			speciesParamTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"speParam",newSpeciesName));
			speciesParamTable.get(i).removeColumn(speciesParamTable.get(i).getColumnModel().getColumn(5));
			speciesParamTable.get(i).removeColumn(speciesParamTable.get(i).getColumnModel().getColumn(4));
			speciesParamTable.get(i).setName("speciesTable"+newSpeciesName); 
			speciesParamTable.get(i).getTableHeader().setReorderingAllowed(false);
			speciesParamScrollPane.add(new javax.swing.JScrollPane());
			speciesParamScrollPane.get(i).setName("speciesParamScrollPane"+i); 
			speciesParamScrollPane.get(i).setViewportView(speciesParamTable.get(i));
			speciesParamScrollPane.get(i).setVisible(true);
			speciesParamFileLabel.add(new javax.swing.JLabel());
			speciesParamFileLabel.get(i).setText(resourceMap.getString("configFileLabel.text")+" "+this.openProject.getSetupParameters("speParam-"+newSpeciesName).getFileName()); 
			speciesParamFileLabel.get(i).setName("speciesParamFileLabel"+i); 
			speciesParamLayeredScrollPanesHGroup.addGroup(speciesParamTabLayout.createParallelGroup()
					.addComponent(speciesParamFileLabel.get(i))
					.addComponent(speciesParamScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE));
			speciesParamLayeredScrollPanesVGroup.addGroup(speciesParamTabLayout.createSequentialGroup()
					.addComponent(speciesParamFileLabel.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(speciesParamScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.height.int"),  Short.MAX_VALUE));
			speciesSetupTable.add(ParameterTable.getInstance().makeTable("speSetup",newSpeciesName));
			speciesSetupTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"speSetup",newSpeciesName));
			speciesSetupTable.get(i).removeColumn(speciesSetupTable.get(i).getColumnModel().getColumn(5));
			speciesSetupTable.get(i).removeColumn(speciesSetupTable.get(i).getColumnModel().getColumn(4));
			speciesSetupTable.get(i).setName("speciesTable"+newSpeciesName); 
			speciesSetupTable.get(i).getTableHeader().setReorderingAllowed(false);
			speciesSetupScrollPane.add(new javax.swing.JScrollPane());
			speciesSetupScrollPane.get(i).setName("speciesScrollPane"+i); 
			speciesSetupScrollPane.get(i).setViewportView(speciesSetupTable.get(i));
			speciesSetupFileLabel.add(new javax.swing.JLabel());
			speciesSetupFileLabel.get(i).setText("<html>"+resourceMap.getString("configFileLabel.text")+" Species.Setup<br>Parameter File: "+
					openProject.getSetupParameters("speParam-"+newSpeciesName).getFileName()+"</html>"); 
			speciesSetupFileLabel.get(i).setName("speciesSetupFileLabel"+i); 
			speciesSetupChangeParamButton.add(new JButton());
			speciesSetupChangeParamButton.get(i).setText(resourceMap.getString("changeSpeParamButton.text")); 
			speciesSetupChangeParamButton.get(i).setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			speciesSetupChangeParamButton.get(i).setName("changeSpeParamButton"+i);
			speciesSetupChangeParamButton.get(i).setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			if(speciesSetupChangeParamButton.get(i).getActionListeners().length==0){
				speciesSetupChangeParamButton.get(i).addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						changeSpeParamButtonActionPerformed(evt);
					}
				});
			}
			speciesSetupLayeredScrollPanesHGroup.addGroup(speciesSetupTabLayout.createParallelGroup()
					.addComponent(speciesSetupFileLabel.get(i))
					.addComponent(speciesSetupChangeParamButton.get(i))
					.addComponent(speciesSetupScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE));
			speciesSetupLayeredScrollPanesVGroup.addGroup(speciesSetupTabLayout.createSequentialGroup()
					.addComponent(speciesSetupFileLabel.get(i))
					.addComponent(speciesSetupChangeParamButton.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(speciesSetupScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE));
			speciesComboElements.add(new String[] {newSpeciesName,"speciesTable"+newSpeciesName});
			speciesSetupComboBox.addItem(newSpeciesName);
			speciesParamComboBox.addItem(newSpeciesName);
			speciesSetupComboBox.setSelectedIndex(i);
			speciesParamComboBox.setSelectedIndex(i);
			speciesSetupTab.revalidate();
			speciesParamTab.revalidate();
			MetaProject.getInstance().setProjectChanged(true);
		}
	}
	private void removeSpeciesButtonActionPerformed(java.awt.event.ActionEvent evt){
		Object[] speciesTitles = new Object[speciesComboElements.toArray().length];
		for (int i = 0; i < speciesComboElements.size(); i++) {
			speciesTitles[i] = speciesComboElements.get(i)[0];
		}
		Object removeSpeciesName = JOptionPane.showInputDialog(this.parentFrame,"Select the name of species to remove","Select the name of species to remove",JOptionPane.QUESTION_MESSAGE,null,speciesTitles,speciesTitles[speciesSetupComboBox.getSelectedIndex()]);
		if(removeSpeciesName!=null){
			int foundIndex = -1;
			for (int i = 0; i < speciesComboElements.size(); i++) {
				if(removeSpeciesName.equals(speciesComboElements.get(i)[0])){
					foundIndex = i;
				}
			}
			speciesComboElements.remove(foundIndex);
			speciesSetupComboBox.removeItemAt(foundIndex);
			speciesParamComboBox.removeItemAt(foundIndex);
			speciesSetupScrollPane.get(foundIndex).setVisible(false);
			speciesSetupScrollPane.remove(foundIndex);
			speciesSetupFileLabel.get(foundIndex).setVisible(false);
			speciesSetupFileLabel.remove(foundIndex);
			speciesSetupChangeParamButton.get(foundIndex).setVisible(false);
			speciesSetupChangeParamButton.remove(foundIndex);
			speciesSetupTable.remove(foundIndex);
			speciesParamTable.remove(foundIndex);
			speciesSetupTab.revalidate();
			if(speciesSetupComboBox.getItemCount()>0){
				speciesSetupComboBox.setSelectedIndex(0);
				speciesParamComboBox.setSelectedIndex(0);
				speciesSetupScrollPane.get(0).setVisible(true);
			}
			this.getOpenProject().removeSpecies((String)removeSpeciesName);
			this.getOpenProject().removeExperimentParameter((String)removeSpeciesName);
			MetaProject.getInstance().setProjectChanged(true);
		}
	}
	private void addHabitatButtonActionPerformed(java.awt.event.ActionEvent evt){
		newReachName = null;
		JFrame mainFrame = InSTREAMConfigApp.getApplication().getMainFrame();
		NewFishOrReach fishOrReachAdder = new NewFishOrReach(this, getOpenProject(),"reach");
		fishOrReachAdder.setLocationRelativeTo(mainFrame);
		InSTREAMConfigApp.getApplication().show(fishOrReachAdder);
	}
	public void addReachSubmitted(){
		if(newReachName!=null){
			habitatParamTable.add(ParameterTable.getInstance().makeTable("habParam",newReachName));
			int i = habitatParamTable.size()-1;
			habitatParamTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"habParam",newReachName));
			habitatParamTable.get(i).removeColumn(habitatParamTable.get(i).getColumnModel().getColumn(5));
			habitatParamTable.get(i).removeColumn(habitatParamTable.get(i).getColumnModel().getColumn(4));
			habitatParamTable.get(i).setName("habitatTable"+newReachName); 
			habitatParamTable.get(i).getTableHeader().setReorderingAllowed(false);
			habitatParamScrollPane.add(new javax.swing.JScrollPane());
			habitatParamScrollPane.get(i).setName("habitatParamScrollPane"+i); 
			habitatParamScrollPane.get(i).setViewportView(habitatParamTable.get(i));
			habitatParamScrollPane.get(i).setVisible(true);
			habitatParamFileLabel.add(new javax.swing.JLabel());
			habitatParamFileLabel.get(i).setText(resourceMap.getString("configFileLabel.text")+" "+openProject.getSetupParameters("habParam-"+newReachName).getFileName()); 
			habitatParamFileLabel.get(i).setName("habitatParamFileLabel"+i); 
			habitatParamLayeredScrollPanesHGroup.addGroup(habitatParamTabLayout.createParallelGroup()
					.addComponent(habitatParamFileLabel.get(i))
					.addComponent(habitatParamScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE));
			habitatParamLayeredScrollPanesVGroup.addGroup(habitatParamTabLayout.createSequentialGroup()
					.addComponent(habitatParamFileLabel.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(habitatParamScrollPane.get(i),0,resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE));
			habitatSetupTable.add(ParameterTable.getInstance().makeTable("habSetup",newReachName));
			habitatSetupTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"habSetup",newReachName));
			habitatSetupTable.get(i).removeColumn(habitatSetupTable.get(i).getColumnModel().getColumn(5));
			habitatSetupTable.get(i).removeColumn(habitatSetupTable.get(i).getColumnModel().getColumn(4));
			habitatSetupTable.get(i).setName("habitatTable"+newReachName); 
			habitatSetupTable.get(i).getTableHeader().setReorderingAllowed(false);
			habitatSetupScrollPane.add(new javax.swing.JScrollPane());
			habitatSetupScrollPane.get(i).setName("habitatScrollPane"+i); 
			habitatSetupScrollPane.get(i).setViewportView(habitatSetupTable.get(i));
			habitatSetupFileLabel.add(new javax.swing.JLabel());
			habitatSetupFileLabel.get(i).setText(resourceMap.getString("configFileLabel.text")+" Reach.Setup"); 
			habitatSetupFileLabel.get(i).setName("habitatSetupFileLabel"+i); 
			habitatSetupLayeredScrollPanesHGroup.addGroup(habitatSetupTabLayout.createParallelGroup()
					.addComponent(habitatSetupFileLabel.get(i))
					.addComponent(habitatSetupScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE));
			habitatSetupLayeredScrollPanesVGroup.addGroup(habitatSetupTabLayout.createSequentialGroup()
					.addComponent(habitatSetupFileLabel.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(habitatSetupScrollPane.get(i),0, resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE));
			habitatComboElements.add(new String[] {newReachName,"habitatTable"+newReachName});
			habitatSetupComboBox.addItem(newReachName);
			habitatParamComboBox.addItem(newReachName);
			habitatSetupComboBox.setSelectedIndex(habitatComboElements.size()-1);
			habitatParamComboBox.setSelectedIndex(habitatComboElements.size()-1);
			habitatSetupTab.revalidate();
			habitatParamTab.revalidate();
			MetaProject.getInstance().setProjectChanged(true);
		}
	}
	private void viewResultsActionPerformed(java.awt.event.ActionEvent evt){
		File outmigrantsFile, reddsFile;
		SetupParameters modSetup = this.openProject.getSetupParameters("modSetup-");
		String outmigrantOutputFilename = modSetup.getParameter("outmigrantOutputFile").getParameterValue();
		String reddOutputFilename = modSetup.getParameter("reddOutputFile").getParameterValue();
		outmigrantsFile = new File(projectDir.getAbsolutePath()+"/"+outmigrantOutputFilename);
		reddsFile = new File(projectDir.getAbsolutePath()+"/"+reddOutputFilename);
		if(!outmigrantsFile.exists()){
			JOptionPane.showMessageDialog(this.parentFrame, "<html><body>Model run output not found, the outmigrant output file named "+outmigrantOutputFilename+" must be present <br>" +
			"in the project directory to view the results.  Either the model has not yet been run, or that output file was removed, please run the model again.</body></html>");
			return;
		}else if(!reddsFile.exists()){
			JOptionPane.showMessageDialog(this.parentFrame, "<html><body>Model run output not found, the redd output file named "+reddOutputFilename+" must be present <br>" +
			"in the project directory to view the results.  Either the model has not yet been run, or that output file was removed, please run the model again.</body></html>");
			return;			
		}
		if(!outmigrantOutputFilename.equals("Outmigrants_Out.csv") || !reddOutputFilename.equals("Redds_Out.csv")){
			ArrayList<ArrayList> tab = new ArrayList<ArrayList>();
			ArrayList<String> col = new ArrayList<String>();
			col.add("outmigrantOutputFile");
			col.add("reddOutputFile");
			tab.add(col);
			col.clear();
			col.add(outmigrantOutputFilename);
			col.add(reddOutputFilename);
			try {
				lftTool.writeTable(projectDir.getAbsolutePath()+"/Analysis_Filenames.csv",tab);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			File destinationFile = new File(projectDir.getAbsolutePath()+"/Analysis_Setup.xlsm");
			MetaProject.getInstance().copy(new File(MetaProject.getInstance().getApplicationDir()+"/DefaultProject/Analysis_Setup.xlsm"),destinationFile);
			BareBonesBrowserLaunch.openURL("file://"+destinationFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void removeHabitatButtonActionPerformed(java.awt.event.ActionEvent evt){
		Object[] habitatTitles = new Object[habitatComboElements.toArray().length];
		for (int i = 0; i < habitatComboElements.size(); i++) {
			habitatTitles[i] = habitatComboElements.get(i)[0];
		}
		Object removeHabitatName = JOptionPane.showInputDialog(this.parentFrame,"Select the name of reach to remove","Select the name of reach to remove",JOptionPane.QUESTION_MESSAGE,null,habitatTitles,habitatTitles[habitatSetupComboBox.getSelectedIndex()]);
		if(removeHabitatName!=null){
			int foundIndex = -1;
			for (int i = 0; i < habitatComboElements.size(); i++) {
				if(removeHabitatName.equals(habitatComboElements.get(i)[0])){
					foundIndex = i;
				}
			}
			habitatComboElements.remove(foundIndex);
			habitatSetupComboBox.removeItemAt(foundIndex);
			habitatParamComboBox.removeItemAt(foundIndex);
			habitatSetupScrollPane.get(foundIndex).setVisible(false);
			habitatSetupScrollPane.remove(foundIndex);
			habitatParamFileLabel.get(foundIndex).setVisible(false);
			habitatSetupFileLabel.remove(foundIndex);
			habitatSetupTable.remove(foundIndex);
			habitatParamTable.remove(foundIndex);
			habitatSetupTab.revalidate();
			if(habitatSetupComboBox.getItemCount()>0){
				habitatSetupComboBox.setSelectedIndex(0);
				habitatParamComboBox.setSelectedIndex(0);
				habitatSetupScrollPane.get(0).setVisible(true);
			}
			this.getOpenProject().removeExperimentParameter((String)removeHabitatName);
			this.getOpenProject().removeHabitat((String)removeHabitatName);
			MetaProject.getInstance().setProjectChanged(true);
		}
	}
	private void hideModelSummaryPane(java.awt.event.ContainerEvent evt) {
		modelSummaryScrollPane.setVisible(false);
	}
	private void hideExperimentTabbedPane(java.awt.event.ContainerEvent evt) {
		experimentTabbedPane.setVisible(false);
	}
	private void hideLftTabbedPane(java.awt.event.ContainerEvent evt) {
		lftTabbedPane.setVisible(false);
	}
	private void experimentParamChangeHandler(java.awt.event.ItemEvent evt) {
		for(int i=0; i<experimentParamComboElements.size(); i++){
			if(((String)evt.getItem()).equals(experimentParamComboElements.get(i)[0])){
				if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED){
					experimentParamScrollPane.get(i).setVisible(true);
					experimentParamTab.revalidate();
				}else{
					experimentParamScrollPane.get(i).setVisible(false);
				}
			}
		}
	}
	private void habitatChangeHandler(java.awt.event.ItemEvent evt) {
		if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED){
			for(int i=0; i<habitatComboElements.size(); i++){
				if(((String)evt.getItem()).equals(habitatComboElements.get(i)[0])){ 
					habitatSetupScrollPane.get(i).setVisible(true);
					habitatSetupFileLabel.get(i).setVisible(true);
					if(habitatSetupComboBox.getItemCount()>0)habitatSetupComboBox.setSelectedIndex(i);
					habitatSetupTab.revalidate();

					habitatParamScrollPane.get(i).setVisible(true);
					habitatParamFileLabel.get(i).setVisible(true);
					if(habitatParamComboBox.getItemCount()>0)habitatParamComboBox.setSelectedIndex(i);
					habitatParamTab.revalidate();
				}
			}
		}else{
			for(int i=0; i<habitatSetupScrollPane.size(); i++){
				habitatSetupScrollPane.get(i).setVisible(false);
				habitatParamScrollPane.get(i).setVisible(false);
				habitatSetupFileLabel.get(i).setVisible(false);
				habitatParamFileLabel.get(i).setVisible(false);
			}
		}
	}
	private void speciesChangeHandler(java.awt.event.ItemEvent evt) {
		if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED){
			for(int i=0; i<speciesComboElements.size(); i++){
				if(((String)evt.getItem()).equals(speciesComboElements.get(i)[0])){ 
					speciesSetupScrollPane.get(i).setVisible(true);
					speciesSetupFileLabel.get(i).setVisible(true);
					speciesSetupChangeParamButton.get(i).setVisible(true);
					if(speciesSetupComboBox.getItemCount()>0)speciesSetupComboBox.setSelectedIndex(i);
					speciesSetupTab.revalidate();

					speciesParamScrollPane.get(i).setVisible(true);
					speciesParamFileLabel.get(i).setVisible(true);
					if(speciesParamComboBox.getItemCount()>0)speciesParamComboBox.setSelectedIndex(i);
					speciesParamTab.revalidate();
				}
			}
		}else{
			for(int i=0; i<speciesSetupScrollPane.size(); i++){
				speciesSetupScrollPane.get(i).setVisible(false);
				speciesSetupFileLabel.get(i).setVisible(false);
				speciesSetupChangeParamButton.get(i).setVisible(false);
				speciesParamScrollPane.get(i).setVisible(false);
				speciesParamFileLabel.get(i).setVisible(false);
			}
		}
	}
	public void goToParameter(Parameter p,String location){
		showErrorWarnings.dispose();
		modelSetupTabbedPane.requestFocusInWindow();
		String paramType = p.getParameterType();
		if(p.getSource().getClass().equals(ExperimentParameter.class)){
			paramType = "expParam";
		}
		if(paramType.equals("lftSetup")){
			modelSetupTabbedPane.setVisible(false);
			modelSummaryScrollPane.setVisible(false);
			experimentTabbedPane.setVisible(false);
			lftTabbedPane.setVisible(true);
		}else if(paramType.equals("expSetup")){
			modelSetupTabbedPane.setVisible(false);
			modelSummaryScrollPane.setVisible(false);
			lftTabbedPane.setVisible(false);
			experimentTabbedPane.setVisible(true);
			experimentTabbedPane.setSelectedIndex(0);
		}else if(paramType.equals("expParam")){
			modelSetupTabbedPane.setVisible(false);
			modelSummaryScrollPane.setVisible(false);
			lftTabbedPane.setVisible(false);
			experimentTabbedPane.setVisible(true);
			experimentTabbedPane.setSelectedIndex(1);
			experimentParamComboBox.setSelectedItem(p.getParameterName());
		}else{
			String instance = ((SetupParameters)p.getSource()).getParamInstance();
			Integer tabIndex = 0;
			if(paramType.equals("obsSetup")){
				tabIndex = 1;
			}else if(paramType.equals("speSetup")){
				tabIndex = 2;
				speciesSetupComboBox.setSelectedItem(instance);
			}else if(paramType.equals("speParam")){
				tabIndex = 3;
				speciesParamComboBox.setSelectedItem(instance);
			}else if(paramType.equals("habSetup")){
				tabIndex = 4;
				habitatSetupComboBox.setSelectedItem(instance);
			}else if(paramType.equals("habParam")){
				tabIndex = 5;
				habitatParamComboBox.setSelectedItem(instance);
			}
			modelSetupTabbedPane.setVisible(true);
			modelSummaryScrollPane.setVisible(false);
			experimentTabbedPane.setVisible(false);
			lftTabbedPane.setVisible(false);
			modelSetupTabbedPane.setSelectedIndex(tabIndex);
		}	
	}

	public void setOpenProject(Project openProject) {
		this.openProject = openProject;
		if(openProject!=null){
			this.openProject.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					updateErrorWarningLinkButton(evt);
				}
			});
		}
	}

	public Project getOpenProject() {
		return openProject;
	}

	public File getProjectDir() {
		return projectDir;
	}
	private int getMenuShortcutKeyMask(){
		return java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
	}

	public void updateLFTRunsPerExperiment() {
		lftRunsPerExpLabel.setText(resourceMap.getString("lftSetupTab.runsPerExperiment.label")+this.lftTool.getLFTRunsPerExperiment()); 
	}
}
