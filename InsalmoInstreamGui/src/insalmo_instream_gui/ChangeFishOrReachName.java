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
import javax.swing.table.TableModel;

/**
 *
 * @author critter
 */
public class ChangeFishOrReachName extends javax.swing.JFrame {
	InsalmoInstreamView parent;
	Project openProject;
	String speciesOrReachName;
	Integer speciesOrReachIndex;
	Boolean isSpecies;

	private JButton submitButton = new JButton();
	private JButton cancelButton = new JButton();
	private JTextField newNameTextField = new JTextField();
	private JLabel changeNameTitle = new JLabel();
//	private JLabel projectNewMethodLabel = new JLabel();

	/** Creates new form newProjectWizard2 */
	public ChangeFishOrReachName(InsalmoInstreamView parent, Project openProject, Integer speciesOrReachIndex, Boolean isSpecies) {
		this.parent = parent;
		this.openProject = openProject;
		this.isSpecies = isSpecies;
		this.speciesOrReachIndex = speciesOrReachIndex;
		this.speciesOrReachName = (isSpecies) ? openProject.getFish().get(speciesOrReachIndex) : openProject.getHabs().get(speciesOrReachIndex);
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 */
	private void initComponents() {
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(insalmo_instream_gui.InsalmoInstreamApp.class).getContext().getResourceMap(ChangeSpeciesParameterFile.class);

		//NEW NAME TEXT FIELD
		changeNameTitle.setFont(resourceMap.getFont("changeParameterTitle.font")); 
		changeNameTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		changeNameTitle.setText("Rename " + ((isSpecies) ? "Species" : "Reach") + " " + speciesOrReachName); 
		changeNameTitle.setVerticalAlignment(javax.swing.SwingConstants.TOP);
		changeNameTitle.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
		changeNameTitle.setName("changeNameTitle");
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setName("Form"); 

		newNameTextField.setName("newNameTextField");
		newNameTextField.setText(this.speciesOrReachName);
		newNameTextField.setVisible(true);

		// SUBMIT AND CANCEL
		submitButton.setName("submitButton");
		submitButton.setText(resourceMap.getString("submitButton.text"));
		submitButton.setVisible(true);
		submitButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				submitButtonActionPerformed(evt);
			}
		});
		cancelButton.setName("cancelButton");
		cancelButton.setText(resourceMap.getString("cancelButton.text"));
		cancelButton.setVisible(true);
		cancelButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				cancelButtonActionPerformed(evt);
			}
		});
		
		javax.swing.GroupLayout changeParameterLayout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(changeParameterLayout);
		changeParameterLayout.setHorizontalGroup(
				changeParameterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(changeParameterLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(changeParameterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(changeNameTitle)
								.addComponent(newNameTextField)
								.addGroup(changeParameterLayout.createSequentialGroup()
									.addComponent(submitButton)
									.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
									.addComponent(cancelButton)))
								.addContainerGap())
		);
		changeParameterLayout.setVerticalGroup(
				changeParameterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(changeParameterLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(changeNameTitle)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(newNameTextField)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(changeParameterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
								.addComponent(submitButton)
								.addComponent(cancelButton)))
		);

		pack();
	}

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.dispose();
	}
	private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String newSpeciesOrReachName = newNameTextField.getText();
		if(newSpeciesOrReachName.equals(speciesOrReachName)){
			JOptionPane.showMessageDialog(this, "Please type a new name for the " + (isSpecies ? "species" : "reach"));
			return;
		}else if(this.isSpecies & this.openProject.fish.contains(newSpeciesOrReachName)){
			JOptionPane.showMessageDialog(this, "The species name '" + newSpeciesOrReachName + "' already exists in the project, please type another.");
			return;
		}else if(!this.isSpecies & this.openProject.habs.contains(newSpeciesOrReachName)){
			JOptionPane.showMessageDialog(this, "The reach name '" + newSpeciesOrReachName + "' already exists in the project, please type another.");
			return;
		}else{
			if(this.isSpecies){
				this.openProject.changeSpeciesName(this.speciesOrReachName,newSpeciesOrReachName);
				this.parent.changeSpeciesName(this.speciesOrReachName,speciesOrReachIndex,newSpeciesOrReachName);
			}else{
				this.openProject.changeReachName(this.speciesOrReachName,newSpeciesOrReachName);
				this.parent.changeReachName(this.speciesOrReachName,speciesOrReachIndex,newSpeciesOrReachName);
			}
		}
		this.dispose();
	}

}
