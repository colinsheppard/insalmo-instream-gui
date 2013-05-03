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

package insalmo_instream_gui;

import java.awt.Color;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.JToolBar.Separator;
import javax.swing.text.DefaultCaret;

/**
 * The application's main frame.
 */
public class InsalmoInstreamView extends JFrame{
	public File projectDir = null;
	org.jdesktop.application.ResourceMap resourceMap;
	public static String newline = System.getProperty("line.separator");
	public JPanel		mainPanel = new javax.swing.JPanel();
	public JToolBar	toolBar = new javax.swing.JToolBar();
	public JButton		newButton = new javax.swing.JButton();
	public JButton		openButton = new javax.swing.JButton();
	public JButton		saveButton = new javax.swing.JButton();
	public JButton		addExpParamButton = new javax.swing.JButton();
	public JButton		remExpParamButton = new javax.swing.JButton();
	public JButton		clearExpParamButton = new javax.swing.JButton();
	public JButton		addSpeButton = new javax.swing.JButton();
	public JButton		remSpeButton = new javax.swing.JButton();	
	public JButton		addHabButton = new javax.swing.JButton();
	public JButton		remHabButton = new javax.swing.JButton();
	public Separator	jSeparatorExperimentSummary = new javax.swing.JToolBar.Separator();
	public Separator	jSeparatorConfigureExperiment = new javax.swing.JToolBar.Separator();
	public JButton		summaryButton = new javax.swing.JButton();
	public JButton		experimentButton = new javax.swing.JButton();
	public JButton		lftButton = new javax.swing.JButton();
	public JButton		configureButton = new javax.swing.JButton();
	public JButton		runButton = new javax.swing.JButton();
	public JButton		runWithGraphicsButton = new javax.swing.JButton();
	public JButton		viewResultsButton = new javax.swing.JButton();
	public Separator	jSeparatorResultsHelp = new javax.swing.JToolBar.Separator();
	public JButton		helpInterfaceButton = new javax.swing.JButton();
	public JButton		helpModelButton = new javax.swing.JButton();
	public JButton		helpSoftwareGuideButton = new javax.swing.JButton();
	public JPanel		contentPanel = new javax.swing.JPanel();
	public JLabel		projectTitleLabel = new javax.swing.JLabel();
	public JPanel		modelSummaryPane = new javax.swing.JPanel();
	public JScrollPane	modelSummaryScrollPane = new javax.swing.JScrollPane();
	public JTabbedPane	experimentTabbedPane = new javax.swing.JTabbedPane();
	public JTabbedPane lftTabbedPane = new javax.swing.JTabbedPane();
	public JTabbedPane lftExecutionTabbedPane = new javax.swing.JTabbedPane();
	public JLabel 		lftRunsPerExpLabel = new javax.swing.JLabel();
	public JLabel		modelSummaryTitle = new javax.swing.JLabel();
	public JLabel		modelSummarySubtitle = new javax.swing.JLabel();
	public JTabbedPane	modelSetupTabbedPane = new javax.swing.JTabbedPane();
	public JPanel		modelSetupTab = new javax.swing.JPanel();
	public JScrollPane	modelSetupPanel = new javax.swing.JScrollPane();
	public  JTable		modelSetupTable = new javax.swing.JTable();
	public JPanel		observerSetupTab = new javax.swing.JPanel();
	public JTable		observerSetupTable = new javax.swing.JTable();
	public JScrollPane	observerSetupScrollPane = new javax.swing.JScrollPane();
	public JPanel		speciesSetupTab = new javax.swing.JPanel();
	public JPanel		speciesParamTab = new javax.swing.JPanel();
	public JScrollPane	speciesPanel = new javax.swing.JScrollPane();
	public JLabel		speciesSetupComboLabel = new javax.swing.JLabel();
	public JComboBox	speciesSetupComboBox = new javax.swing.JComboBox();
	public JComboBox	speciesParamComboBox = new javax.swing.JComboBox();
	public JLabel		speciesParamComboLabel = new javax.swing.JLabel();
	public JPanel		habitatSetupTab = new javax.swing.JPanel();
	public JPanel		habitatParamTab = new javax.swing.JPanel();
	public JPanel		experimentControlTab = new javax.swing.JPanel();
	public JPanel		experimentParamTab = new javax.swing.JPanel();
	public JPanel		lftSetupTab = new javax.swing.JPanel();
	public ArrayList<javax.swing.JScrollPane>	experimentParamScrollPane = new ArrayList<javax.swing.JScrollPane>();
	public JComboBox	experimentParamComboBox = new javax.swing.JComboBox();
	public JLabel		experimentParamComboLabel = new javax.swing.JLabel();
	public ArrayList<JTable>	experimentParamTable = new ArrayList<JTable>();
	public JComboBox	habitatSetupComboBox = new javax.swing.JComboBox();
	public JComboBox	habitatParamComboBox = new javax.swing.JComboBox();
	public JLabel		habitatSetupComboLabel = new javax.swing.JLabel();
	public JLabel		habitatParamComboLabel = new javax.swing.JLabel();
	public ArrayList<javax.swing.JScrollPane>	habitatSetupScrollPane = new ArrayList<javax.swing.JScrollPane>();
	public ArrayList<javax.swing.JScrollPane>	habitatParamScrollPane = new ArrayList<javax.swing.JScrollPane>();
	public ArrayList<javax.swing.JLabel>		habitatSetupFileLabel = new ArrayList<javax.swing.JLabel>();
	public ArrayList<javax.swing.JLabel>		habitatParamFileLabel = new ArrayList<javax.swing.JLabel>();
	public ArrayList<javax.swing.JScrollPane>	speciesSetupScrollPane = new ArrayList<javax.swing.JScrollPane>();
	public ArrayList<javax.swing.JScrollPane>	speciesParamScrollPane = new ArrayList<javax.swing.JScrollPane>();
	public ArrayList<javax.swing.JLabel>		speciesSetupFileLabel = new ArrayList<javax.swing.JLabel>();
	public ArrayList<javax.swing.JLabel>		speciesParamFileLabel = new ArrayList<javax.swing.JLabel>();
	public ArrayList<javax.swing.JButton>		speciesSetupChangeParamButton = new ArrayList<JButton>();
	public JScrollPane			modelSetupScrollPane = new javax.swing.JScrollPane();
	public JScrollPane			experimentControlScrollPane = new javax.swing.JScrollPane();
	public JScrollPane			lftSetupScrollPane = new javax.swing.JScrollPane();
	public ArrayList<JTable>	habitatSetupTable = new ArrayList<JTable>();
	public ArrayList<JTable>	habitatParamTable = new ArrayList<JTable>();
	public ArrayList<JTable>	speciesSetupTable = new ArrayList<JTable>();
	public ArrayList<JTable>	speciesParamTable = new ArrayList<JTable>();
	public JTable		experimentControlTable = new JTable();
	public JTable		lftSetupTable = new JTable();
	public JLayeredPane		habitatLayeredPane = new javax.swing.JLayeredPane();
	public ArrayList<String[]>	experimentParamComboElements = new ArrayList<String[]>();
	public ArrayList<String[]>	habitatComboElements = new ArrayList<String[]>();
	public ArrayList<String[]>	habitatParamComboElements = new ArrayList<String[]>();
	public Boolean		habitatSetupSelected = true;
	public Boolean		speciesSetupSelected = true;
	public ArrayList<String[]>	speciesComboElements = new ArrayList<String[]>();
	public ArrayList<String[]>	speciesParamComboElements = new ArrayList<String[]>();
	public JMenuBar	menuBar = new javax.swing.JMenuBar();
	public JMenu	 	fileMenu = new javax.swing.JMenu();
	public JMenuItem	newMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	openMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	closeMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	saveMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	saveasMenuItem = new javax.swing.JMenuItem();
	public JSeparator	jSeparatorSaveasExit = new javax.swing.JSeparator();
	public JMenuItem	exitMenuItem = new javax.swing.JMenuItem();
	public JMenu		modelMenu = new javax.swing.JMenu();
	public JMenu		experimentMenu = new javax.swing.JMenu();
	public JMenuItem	experimentMenuItem = new javax.swing.JMenuItem();
	public JMenuItem 	lftMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	summaryMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	configureMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	runMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	runWithGraphicsMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	viewResultsMenuItem = new javax.swing.JMenuItem();
	public JMenu		helpMenu = new javax.swing.JMenu();
	public JMenuItem	interfaceHelpMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	modelHelpMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	softwareGuideHelpMenuItem = new javax.swing.JMenuItem();
	public JMenuItem	lftHelpMenuItem = new javax.swing.JMenuItem();
	public JSeparator	jSeparatorModelhelpAbout = new javax.swing.JSeparator();
	public JMenuItem	aboutMenuItem = new javax.swing.JMenuItem();
	public String newParamName;
	public String newSpeciesName;
	public String newReachName;
	public String newClassName;
	public Timer messageTimer;
	public Timer busyIconTimer;
	public Icon idleIcon;
	public Icon[] busyIcons = new Icon[15];
	public int busyIconIndex = 0;
	public JProgressBar progressBar = new JProgressBar();
	public Project openProject;
	public ParallelGroup experimentParamLayeredScrollPanesHGroup;
	public ParallelGroup experimentParamLayeredScrollPanesVGroup;
	public ParallelGroup speciesSetupLayeredScrollPanesHGroup;
	public ParallelGroup speciesSetupLayeredScrollPanesVGroup;
	public ParallelGroup speciesParamLayeredScrollPanesHGroup;
	public ParallelGroup speciesParamLayeredScrollPanesVGroup;
	public ParallelGroup habitatSetupLayeredScrollPanesHGroup;
	public ParallelGroup habitatSetupLayeredScrollPanesVGroup;
	public ParallelGroup habitatParamLayeredScrollPanesHGroup;
	public ParallelGroup habitatParamLayeredScrollPanesVGroup;
	// Species Setup
	public javax.swing.GroupLayout speciesSetupTabLayout;
	public javax.swing.GroupLayout speciesParamTabLayout;
	// Species Parameter
	// Experiment Control 
	// Experiment Parameter
	// Habitat Setup
	public javax.swing.GroupLayout habitatSetupTabLayout;
	public javax.swing.GroupLayout habitatParamTabLayout;
	// Habitat Parameter
	public javax.swing.JScrollPane observerSetupPanel;
	public JDialog aboutBox;
	public JLinkButton errorWarningButton = new JLinkButton();
	public ShowErrorsWarnings showErrorWarnings;
	InsalmoInstreamGui parentFrame;
	public LaunchInsamloExecutable modelLauncher;
	// Model Summary
	public javax.swing.JScrollPane showConsoleScrollPane;
	public JExpandableTextArea showConsoleTextArea;
	public javax.swing.JLabel showConsoleLabel;
	public JButton showConsoleClearButton = new JButton();
	public JButton terminateModelRunButton = new JButton();
	public OutputStream killStream = null;
	// LFT 
	public Hashtable<String,JExpandableTextArea> lftTextAreas = new Hashtable<String,JExpandableTextArea>();
	public Hashtable<String,LaunchInsamloExecutable> lftLaunchers = new Hashtable<String,LaunchInsamloExecutable>();
	public LimitingFactorsTool lftTool = new LimitingFactorsTool(this);
	public JButton postProcessLFTButton = new JButton();
	public JButton	startLFTButton = new JButton();
	public JButton	killLFTButton = new JButton();
	public InsalmoInstreamActions actionHandler;

	public InsalmoInstreamView(InsalmoInstreamGui inSALMO) {
		parentFrame = inSALMO;
		MetaProject.getInstance().setInSALMOView(this);
		actionHandler = new InsalmoInstreamActions(this);
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 */
	private void initComponents() {
		mainPanel.setName("mainPanel"); 

		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		toolBar.setName("toolBar"); 

		this.resourceMap = org.jdesktop.application.Application.getInstance(insalmo_instream_gui.InsalmoInstreamApp.class).getContext().getResourceMap(InsalmoInstreamView.class);
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
					actionHandler.newActionPerformed(evt);
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
					actionHandler.openActionPerformed(evt);
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
					actionHandler.saveActionPerformed(evt);
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
					actionHandler.configureActionPerformed(evt);
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
					actionHandler.experimentActionPerformed(evt);
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
					actionHandler.lftActionPerformed();
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
					actionHandler.summaryActionPerformed(evt);
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
					actionHandler.runActionPerformed(evt);
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
					actionHandler.runWithGraphicsActionPerformed(evt);
				}
			});
		}
		toolBar.add(runWithGraphicsButton);

		if(!actionHandler.okToRunModel()){
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
					actionHandler.viewResultsActionPerformed(evt);
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
					actionHandler.errorWarningActionPerformed(evt);
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
					actionHandler.helpInterfaceActionPerformed(evt);
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
					actionHandler.helpModelActionPerformed(evt);
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
					actionHandler.helpSoftwareGuideActionPerformed(evt);
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
					actionHandler.newActionPerformed(evt);
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
					actionHandler.openActionPerformed(evt);
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
					actionHandler.saveActionPerformed(evt);
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
					actionHandler.saveasActionPerformed(evt);
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
					actionHandler.closeActionPerformed(evt);
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
					actionHandler.configureActionPerformed(evt);
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
					actionHandler.exitActionPerformed(evt);
				}
			});
		}
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);
		experimentMenu.setText(resourceMap.getString("experimentMenu.text")); 
		experimentMenu.setName("experimentMenu"); 

		experimentMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, getMenuShortcutKeyMask()));
		experimentMenuItem.setText(resourceMap.getString("experimentMenuItem.text")); 
		experimentMenuItem.setName("experimentMenuItem"); 
		experimentMenuItem.setEnabled(false);
		if(experimentMenuItem.getActionListeners().length==0){
			experimentMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					actionHandler.experimentActionPerformed(evt);
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
					actionHandler.lftActionPerformed();
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
					actionHandler.summaryActionPerformed(evt);
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
					actionHandler.runActionPerformed(evt);
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
					actionHandler.runWithGraphicsActionPerformed(evt);
				}
			});
		}
		modelMenu.add(runWithGraphicsMenuItem);

		viewResultsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, getMenuShortcutKeyMask()));
		viewResultsMenuItem.setText(resourceMap.getString("viewResultsMenuItem.text")); 
		viewResultsMenuItem.setName("viewResultsMenuItem"); 
		viewResultsMenuItem.setEnabled(false);
		if(viewResultsMenuItem.getActionListeners().length==0){
			viewResultsMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					actionHandler.viewResultsActionPerformed(evt);
				}
			});
		}
		modelMenu.add(viewResultsMenuItem);

		menuBar.add(modelMenu);

		helpMenu.setText(resourceMap.getString("helpMenu.text")); 
		helpMenu.setName("helpMenu"); 

		interfaceHelpMenuItem.setText(resourceMap.getString("interfaceHelpMenuItem.text")); 
		interfaceHelpMenuItem.setName("interfaceHelpMenuItem"); 
		if(interfaceHelpMenuItem.getActionListeners().length==0){
			interfaceHelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					actionHandler.helpInterfaceActionPerformed(evt);
				}
			});
		}
		helpMenu.add(interfaceHelpMenuItem);

		modelHelpMenuItem.setText(resourceMap.getString("modelHelpMenuItem.text")); 
		modelHelpMenuItem.setName("modelHelpMenuItem"); 
		if(modelHelpMenuItem.getActionListeners().length==0){
			modelHelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					actionHandler.helpModelActionPerformed(evt);
				}
			});
		}
		helpMenu.add(modelHelpMenuItem);

		softwareGuideHelpMenuItem.setText(resourceMap.getString("softwareGuideHelpMenuItem.text")); 
		softwareGuideHelpMenuItem.setName("softwareGuideHelpMenuItem"); 
		if(softwareGuideHelpMenuItem.getActionListeners().length==0){
			softwareGuideHelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					actionHandler.helpSoftwareGuideActionPerformed(evt);
				}
			});
		}
		helpMenu.add(softwareGuideHelpMenuItem);

		lftHelpMenuItem.setText(resourceMap.getString("lftHelpMenuItem.text")); 
		lftHelpMenuItem.setName("lftHelpMenuItem"); 
		if(lftHelpMenuItem.getActionListeners().length==0){
			lftHelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					actionHandler.helpLFTActionPerformed(evt);
				}
			});
		}
		helpMenu.add(lftHelpMenuItem);

		jSeparatorModelhelpAbout.setName("jSeparatorModelhelpAbout"); 
		helpMenu.add(jSeparatorModelhelpAbout);

		menuBar.add(helpMenu);

		progressBar.setName("progressBar"); 

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
	}

	/*
	 * initContentPanel
	 */
	public void initContentPanel(){

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
				actionHandler.hideModelSummaryPane(evt);
			}
		});

		experimentTabbedPane.setName("experimentTabbedPane"); 
		experimentTabbedPane.setOpaque(false);
		experimentTabbedPane.addContainerListener(new java.awt.event.ContainerAdapter() {
			public void componentAdded(java.awt.event.ContainerEvent evt) {
				actionHandler.hideExperimentTabbedPane(evt);
			}
		});

		lftTabbedPane.setName("TabbedPane"); 
		lftTabbedPane.setOpaque(false);
		lftTabbedPane.addContainerListener(new java.awt.event.ContainerAdapter() {
			public void componentAdded(java.awt.event.ContainerEvent evt) {
				actionHandler.lftHideTabbedPane(evt);
			}
		});

		lftExecutionTabbedPane.setName("TabbedPane"); 
		lftExecutionTabbedPane.setOpaque(false);
		lftExecutionTabbedPane.addContainerListener(new java.awt.event.ContainerAdapter() {
			public void componentAdded(java.awt.event.ContainerEvent evt) {
				actionHandler.lftHideExecutionTabbedPane(evt);
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
					actionHandler.showConsoleClearActionPerfomed(evt);
				}
			});
		}
		terminateModelRunButton.setName("terminateModelRunButton");
		terminateModelRunButton.setText(resourceMap.getString("terminateModelRunButton.text"));
		if(terminateModelRunButton.getActionListeners().length==0){
			terminateModelRunButton.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					actionHandler.terminateModelRunActionPerfomed(evt);
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
				actionHandler.hideModelSetupTabbedPane(evt);
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

	public void enableModelButtons(){
		summaryButton.setEnabled(true);
		saveButton.setEnabled(true);
		configureButton.setEnabled(true);
		if(actionHandler.okToRunModel()){
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

	public void removeProjectFromView(){
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
			experimentParamScrollPane.get(i).setVisible(false);
			experimentParamScrollPane.remove(i);
		}
		experimentTabbedPane.remove(experimentParamTab);
		experimentTabbedPane.remove(experimentControlTab);

		experimentTabbedPane.revalidate();
	}

	public boolean experimentValueErrorsExist(){
		if(!MetaProject.getInstance().isInsalmo() && this.openProject.getSetupParameters("expSetup-").getParameter("numberOfYearShufflerReplicates").getParameterIntegerValue()>0){
			for(String exp : this.openProject.exps){
				if(this.openProject.getExperimentParameters(exp).getValues().size() != this.openProject.getNumberOfScenarios()){
					return true;
				}
			}
		}
		return false;
	}

	public void createParameterTabs(){
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
		modelSetupTabbedPane.addTab(resourceMap.getString("modelSetupTab.TabConstraints.tabTitle"), null, modelSetupTab, resourceMap.getString("modelSetupTab.TabConstraints.tabToolTip")); 

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

		modelSetupTabbedPane.addTab(resourceMap.getString("observerSetupTab.TabConstraints.tabTitle"), null, observerSetupTab, resourceMap.getString("observerSetupTab.TabConstraints.tabToolTip")); 

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
				actionHandler.speciesChangeHandler(evt);
			}
		});
		speciesParamComboBox.setModel(new javax.swing.DefaultComboBoxModel(speciesTitles));
		speciesParamComboBox.setName("speciesParamComboBox"); 
		speciesParamComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				actionHandler.speciesChangeHandler(evt);
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
					actionHandler.addSpeciesActionPerformed(evt);
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
					actionHandler.removeSpeciesActionPerformed(evt);
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
						actionHandler.changeSpeParamActionPerformed(evt);
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
				actionHandler.habitatChangeHandler(evt);
			}
		});
		habitatSetupComboLabel.setText(resourceMap.getString("habitatComboLabel.text")); 
		habitatSetupComboLabel.setName("habitatParamComboLabel"); 
		habitatParamComboBox.setModel(new javax.swing.DefaultComboBoxModel(habitatTitles));
		habitatParamComboBox.setName("habitatSetupComboBox"); 
		habitatParamComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				actionHandler.habitatChangeHandler(evt);
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
					actionHandler.addHabitatActionPerformed(evt);
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
					actionHandler.removeHabitatActionPerformed(evt);
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
		modelSetupTabbedPane.addTab(resourceMap.getString("habitatSetupTab.TabConstraints.tabTitle"), null, habitatSetupTab, resourceMap.getString("habitatSetupTab.TabConstraints.tabToolTip")); 

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
		modelSetupTabbedPane.addTab(resourceMap.getString("habitatParamTab.TabConstraints.tabTitle"), null, habitatParamTab, resourceMap.getString("habitatParamTab.TabConstraints.tabToolTip")); 

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
					actionHandler.clearExperimentActionPerformed(evt);
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
		experimentTabbedPane.addTab(resourceMap.getString("experimentControlTab.TabConstraints.tabTitle"), null, experimentControlTab, resourceMap.getString("experimentControlTab.TabConstraints.tabToolTip")); 

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
				actionHandler.experimentParamChangeHandler(evt);
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
					actionHandler.addExperimentParamActionPerformed(evt);
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
					actionHandler.removeExperimentParamActionPerformed(evt);
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
		experimentTabbedPane.addTab(resourceMap.getString("experimentParamTab.TabConstraints.tabTitle"), null, experimentParamTab, resourceMap.getString("experimentParamTab.TabConstraints.tabToolTip")); 

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
					actionHandler.startLFTActionPerformed(evt);
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
					actionHandler.killLFTActionPerformed(evt);
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
					actionHandler.postProcessLFTActionPerformed();
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
		lftTabbedPane.addTab(resourceMap.getString("lftSetupTab.TabConstraints.tabTitle"), null, lftSetupTab, resourceMap.getString("lftSetupTab.TabConstraints.tabToolTip")); 

		//GroupLayout lftExecutionTabLayout = new javax.swing.GroupLayout(lftExecutionTabbedPane);
		//lftExecutionTabbedPane.setLayout(lftExecutionTabLayout);

		lftTabbedPane.addTab(resourceMap.getString("lftExecutionTab.TabConstraints.tabTitle"), null, lftExecutionTabbedPane, resourceMap.getString("lftExecutionTab.TabConstraints.tabToolTip")); 
		if(lftTextAreas.size()==0){
			lftTool.createLFTOutputTabs(lftExecutionTabbedPane);
		}
	}

	// MENU ITEM ACTIONS
	public void setOpenProject(Project openProject) {
		this.openProject = openProject;
		if(openProject!=null){
			this.openProject.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent evt) {
					actionHandler.updateErrorWarningLinkButton(evt);
				}
			});
		}
	}
	public Project getOpenProject() {
		return openProject;
	}
	public int getMenuShortcutKeyMask(){
		return java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
	}
	public File getProjectDir() {
		return projectDir;
	}
}
