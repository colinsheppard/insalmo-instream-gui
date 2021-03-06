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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * newProjectWizard2.java
 *
 * Created on Jan 4, 2010, 2:57:50 PM
 */

package insalmo_instream_gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


/**
 *
 * @author critter
 */
public class NewProjectWizard extends javax.swing.JFrame {
	InsalmoInstreamView parent;
	File newProjectDir = null;
	File templateProjectFile = null;

	private ButtonGroup projectFromButtonGroup = new ButtonGroup();
	private JPanel projectFromButtons = new JPanel();
	private JRadioButton projectFromDefaultButton = new JRadioButton();
	private JRadioButton projectFromExistingButton= new JRadioButton();
	private JRadioButton projectFromScratchButton= new JRadioButton();
	private JButton projectWizardSubmitButton = new JButton();
	private JButton projectWizardCancelButton = new JButton();
	private JPanel projectWizardPanel = new JPanel();
	private JLabel projectWizardTitleLabel = new JLabel();
	private JLabel projectNewNameLabel = new JLabel();
	private JLabel projectNewMethodLabel = new JLabel();
	private JButton chooserExistingButton = new JButton();
	private JLabel chooserExistingLabel = new JLabel();
	private JButton chooserNewButton = new JButton();
	private JLabel chooserNewLabel = new JLabel();

	/** Creates new form newProjectWizard2 */
	public NewProjectWizard(InsalmoInstreamView parent) {
		this.parent = parent;
		initComponents();
	}

	private void initComponents() {
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(insalmo_instream_gui.InsalmoInstreamApp.class).getContext().getResourceMap(NewProjectWizard.class);

		//NEW NAME TEXT FIELD
		projectNewNameLabel.setFont(resourceMap.getFont("projectNewNameLabel.font")); 
		projectNewNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		projectNewNameLabel.setText(resourceMap.getString("projectNewNameLabel.text")); 
		projectNewNameLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		projectNewNameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
		projectNewNameLabel.setName("projectNewNameLabel"); 
		
		projectNewMethodLabel.setFont(resourceMap.getFont("projectNewMethodLabel.font")); 
		projectNewMethodLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		projectNewMethodLabel.setText(resourceMap.getString("projectNewMethodLabel.text")); 
		projectNewMethodLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		projectNewMethodLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
		projectNewMethodLabel.setName("projectNewMethodLabel"); 

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setName("Form"); 

		projectWizardPanel.setName("projectWizardPanel"); 

		projectWizardTitleLabel.setFont(resourceMap.getFont("projectWizardTitleLabel.font")); 
		projectWizardTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		projectWizardTitleLabel.setText(resourceMap.getString("projectWizardTitleLabel.text")); 
		projectWizardTitleLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		projectWizardTitleLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
		projectWizardTitleLabel.setName("projectWizardTitleLabel"); 

		projectWizardSubmitButton.setText(resourceMap.getString("projectWizardSubmitButton.text")); 
		projectWizardSubmitButton.setName("projectWizardSubmitButton"); 
		projectWizardSubmitButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				projectWizardSubmitButtonActionPerformed(evt);
			}
		});
		projectWizardCancelButton.setText(resourceMap.getString("projectWizardCancelButton.text")); 
		projectWizardCancelButton.setName("projectWizardCancelButton"); 
		projectWizardCancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				projectWizardCancelButtonActionPerformed(evt);
			}
		});

		projectFromButtons.setName("projectFromButtons"); 
		projectFromButtons.setNextFocusableComponent(projectFromButtons);

		projectFromButtonGroup.add(projectFromExistingButton);
		projectFromExistingButton.setText(resourceMap.getString("projectFromExistingButton.text")); 
		projectFromExistingButton.setName("projectFromExistingButton"); 
		projectFromExistingButton.setSelected(false);
		projectFromExistingButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				projectFromExistingButtonActionPerformed(evt);
			}
		});

		projectFromButtonGroup.add(projectFromDefaultButton);
		projectFromDefaultButton.setText(resourceMap.getString("projectFromDefaultButton.text")); 
		projectFromDefaultButton.setName("projectFromDefaultButton"); 
		projectFromDefaultButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				projectFromDefaultButtonActionPerformed(evt);
			}
		});

		projectFromButtonGroup.add(projectFromScratchButton);
		projectFromScratchButton.setText(resourceMap.getString("projectFromScratchButton.text")); 
		projectFromScratchButton.setName("projectFromScratchButton"); 
		projectFromScratchButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				projectFromScratchButtonActionPerformed(evt);
			}
		});

		// CHOOSE NEW PROJECT DIR
		chooserNewButton.setName("chooserNewButton");
		chooserNewButton.setText(resourceMap.getString("chooserNewButton.text"));
		chooserNewButton.setVisible(true);
		chooserNewButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				chooserActionPerformed(evt,true);
			}
		});
		chooserNewLabel.setName("chooserNewLabel");
		chooserNewLabel.setText("<html><body><b>New Project Name:</b> <i>not yet specified<i></body></html>");

		// CHOOSE EXISTING PROJECT
		chooserExistingButton.setName("chooserExistingButton");
		chooserExistingButton.setText(resourceMap.getString("chooserExistingButton.text"));
		chooserExistingButton.setVisible(false);
		chooserExistingButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				chooserActionPerformed(evt,false);
			}
		});
		chooserExistingLabel.setName("chooserExistingLabel");
		chooserExistingLabel.setText("<html><body><b>selected:</b> <i>none<i></body></html>");
		chooserExistingLabel.setVisible(false);

		javax.swing.GroupLayout projectFromButtonsLayout = new javax.swing.GroupLayout(projectFromButtons);
		projectFromButtons.setLayout(projectFromButtonsLayout);
		projectFromButtonsLayout.setHorizontalGroup(
				projectFromButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(projectFromButtonsLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(projectFromButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(projectFromExistingButton)
								.addGroup(projectFromButtonsLayout.createSequentialGroup()
										.addComponent(chooserExistingButton)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(chooserExistingLabel))
										.addComponent(projectFromScratchButton)
										.addComponent(projectFromDefaultButton))
										.addContainerGap())
		);
		projectFromButtonsLayout.setVerticalGroup(
				projectFromButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(projectFromButtonsLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(projectFromExistingButton)
						.addGroup(projectFromButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
								.addComponent(chooserExistingButton)
								.addComponent(chooserExistingLabel))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(projectFromDefaultButton)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(projectFromScratchButton))
		);

		javax.swing.GroupLayout projectWizardPanelLayout = new javax.swing.GroupLayout(projectWizardPanel);
		projectWizardPanel.setLayout(projectWizardPanelLayout);
		projectWizardPanelLayout.setHorizontalGroup(
				projectWizardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(projectWizardPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(projectWizardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(projectNewNameLabel)
								.addGroup(projectWizardPanelLayout.createSequentialGroup()
												.addComponent(chooserNewButton)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(chooserNewLabel)
												.addContainerGap())
								.addComponent(projectNewMethodLabel)
								.addGroup(projectWizardPanelLayout.createSequentialGroup()
										.addGroup(projectWizardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(projectFromButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 543, Short.MAX_VALUE)
												.addComponent(projectWizardTitleLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE))
												.addContainerGap())
								.addGroup(javax.swing.GroupLayout.Alignment.LEADING, projectWizardPanelLayout.createSequentialGroup()
														.addComponent(projectWizardSubmitButton)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(projectWizardCancelButton)
														.addGap(56, 56, 56))))
		);
		projectWizardPanelLayout.setVerticalGroup(
				projectWizardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(projectWizardPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(projectWizardTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(projectWizardPanelLayout.createSequentialGroup()
								.addComponent(projectNewNameLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(projectWizardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
												.addComponent(chooserNewButton)
												.addComponent(chooserNewLabel))
								.addGap(20, 20, 20)
								.addComponent(projectNewMethodLabel)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(projectFromButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)								
								.addGap(20, 20, 20)
								.addGroup(projectWizardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
										.addComponent(projectWizardSubmitButton)
										.addComponent(projectWizardCancelButton))
								.addContainerGap())
		));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(projectWizardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(projectWizardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		pack();
	}

	private void projectWizardCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.dispose();
	}
	private void projectWizardSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {
		if(newProjectDir == null){
			JOptionPane.showMessageDialog(this, "Please choose a directory for the new project");
			return;
		}else if(!newProjectDir.exists()){
			JOptionPane.showMessageDialog(this, "<html><body>The selected directory for the new project does not exist, <br>please choose an existing directory</body></html>");
			return;
		}
		if(!projectFromExistingButton.isSelected() &&
				!projectFromDefaultButton.isSelected() &&
				!projectFromScratchButton.isSelected()){
			JOptionPane.showMessageDialog(this, "Please select a method for creating a new project");
			return;
		}
		if(projectFromExistingButton.isSelected() && templateProjectFile==null){
			JOptionPane.showMessageDialog(this, "Please select an existing project to use as a template");
			return;
		}
		// Error check the selected project directory
		ArrayList<String> existingProjectFiles = new ArrayList<String>();
		String[] fileNames = MetaProject.getInstance().getStaticFileNames();
		for(String fileName : fileNames){
			if((new File(newProjectDir.getAbsolutePath()+"/"+fileName)).exists()){
				existingProjectFiles.add(fileName);
			}
		}
		if((new File(newProjectDir.getAbsolutePath()+"/Experiment.Setup")).exists()){
			existingProjectFiles.add("Experiment.Setup");
		}
		if(existingProjectFiles.size()>0){
			String message = "<html><body><b>Existing setup files were detected in the new project directory</b><br><br>"+
				"Project directory:<br>&nbsp;&nbsp;&nbsp;&nbsp;"+newProjectDir.getAbsolutePath()+"<br><br>Setup files detected:<br>";
			for(String fileName : existingProjectFiles){
				message += "&nbsp;&nbsp;&nbsp;&nbsp;"+fileName+"<br>";
			}
			message += "<br>Other files detected:<br>";
			for(File fileFound : newProjectDir.listFiles()){
				if(!existingProjectFiles.contains(fileFound.getName()))message += "&nbsp;&nbsp;&nbsp;&nbsp;"+fileFound.getName()+"<br>";
			}
			message += "<br>"+"The setup files (and any species or habitat input files sharing the same name as an incoming file) <br>"+
			"will be overwritten by the new project, do you wish to continue?</body></html>";
			int choice = JOptionPane.showOptionDialog(this,message,"Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Continue","Cancel"},"Cancel");
			if(choice!=0){
				return;
			}
		}else if(newProjectDir.listFiles().length>0){
			String message = "<html><body><b>Existing files were detected in the new project directory</b><br><br>"+
			"Project directory:<br>&nbsp;&nbsp;&nbsp;&nbsp;"+newProjectDir.getAbsolutePath()+"<br><br>Files detected:<br>";
			for(File fileFound : newProjectDir.listFiles()){
				message += "&nbsp;&nbsp;&nbsp;&nbsp;"+fileFound.getName()+"<br>";
			}
			message += "<br>"+"Some or all of these files could be overwritten if they share the same name as an incoming <br>"+
			"species or habitat file from the new project, continue?</body></html>";
			int choice = JOptionPane.showOptionDialog(this,message,"Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"Continue","Cancel"},"Cancel");
			if(choice!=0){
				return;
			}
		}

		if(projectFromExistingButton.isSelected()){
			this.parent.actionHandler.openProject(templateProjectFile,false);
			if(this.parent.getProjectDir()==null)return;
			this.parent.actionHandler.setProjectDir(newProjectDir);
			this.parent.actionHandler.saveProject();
			this.parent.actionHandler.shutDownProject();
			this.parent.actionHandler.openProject(newProjectDir,true);
		}else{
			this.parent.actionHandler.createNewProject(newProjectDir,projectFromScratchButton.isSelected());
			this.parent.actionHandler.saveProject();
			this.parent.actionHandler.shutDownProject();
			this.parent.actionHandler.openProject(newProjectDir,true);
		}
		this.dispose();
	}

	private void projectFromExistingButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.chooserExistingButton.setVisible(true);
		this.chooserExistingLabel.setVisible(true);
	}

	private void projectFromDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.chooserExistingButton.setVisible(false);
		this.chooserExistingLabel.setVisible(false);
	}

	private void projectFromScratchButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.chooserExistingButton.setVisible(false);
		this.chooserExistingLabel.setVisible(false);
	}
	//    private void selectFileButtonActionPerformed(java.awt.event.ActionEvent evt){
	//		final JFileChooser fc = new JFileChooser();
	//		fc.setCurrentDirectory(new java.io.File("."));
	//		fc.setDialogTitle("Select a Project Directory");
	//		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	//		fc.setAcceptAllFileFilterUsed(false); // disable the "All files" option.
	//		int returnVal = fc.showOpenDialog(this.parent);
	//
	//		if (returnVal == JFileChooser.APPROVE_OPTION) {
	//			templateProjectFile = fc.getSelectedFile();
	//		} else {
	//			return;
	//		}
	//		filenameLabel.setText("<html><body><b>selected:</b> <i>"+templateProjectFile.getName()+"<i></body></html>");
	//    }
	private void chooserActionPerformed(java.awt.event.ActionEvent evt,boolean isNew){
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setDialogTitle("Select a Project Directory");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false); // disable the "All files" option.
		int returnVal = fc.showOpenDialog(this.parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if(isNew){
				newProjectDir = fc.getSelectedFile();
			}else{
				templateProjectFile = fc.getSelectedFile();
			}
		} else {
			return;
		}
		if(isNew){
			chooserNewLabel.setText("<html><body><b>New Project Name:</b> <i>"+newProjectDir.getName()+"<i></body></html>");
		}else{
			chooserExistingLabel.setText("<html><body><b>selected:</b> <i>"+templateProjectFile.getName()+"<i></body></html>");
		}
	}

}
