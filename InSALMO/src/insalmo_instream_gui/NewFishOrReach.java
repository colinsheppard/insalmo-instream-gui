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
 * NewFishOrReach
 */

package insalmo_instream_gui;

import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.layout.LayoutStyle;

/**
 *
 * @author critter
 */
public class NewFishOrReach extends javax.swing.JFrame {
	InsalmoInstreamView parent;
	Project openProject;
	private javax.swing.JPanel newFishReachPanel;
	private javax.swing.ButtonGroup sourceButtonGroup = new javax.swing.ButtonGroup();
	private javax.swing.JRadioButton sourceDefaultButton;
	private javax.swing.JRadioButton copyFromExistingButton;
	private javax.swing.JTextField newNameTextField = new javax.swing.JTextField();
	private JLabel instructionsLabel = new JLabel();
	private JLabel filenameLabel = new JLabel();
	private JLabel newNameLabel= new JLabel();
	private JButton submitButton = new JButton();
	private JButton cancelButton = new JButton();
	private String fishOrReach = null; 
	private File fishReachFile;
	private JComboBox fishOrReachComboBox = new JComboBox();

	/** Creates new form */
	public NewFishOrReach(InsalmoInstreamView parent, Project openProject, String fishOrReach) {
		this.parent = parent;
		this.openProject = openProject;
		this.fishOrReach = fishOrReach;
		initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(insalmo_instream_gui.InsalmoInstreamApp.class).getContext().getResourceMap(NewFishOrReach.class);

		copyFromExistingButton = new JRadioButton(resourceMap.getString("sourceFromFileButton.text"));
		sourceDefaultButton = new JRadioButton(resourceMap.getString("sourceDefaultButton.text"));

		// REACH/SEPCIES COMBO BOX
		String[] fishOrReachInstances = null;
		ArrayList<String> instanceArrayList = null;
		if(fishOrReach.equals("species")){
			instanceArrayList = MetaProject.getInstance().getOpenProject().getFish();
			fishOrReachInstances = new String[instanceArrayList.size()];
			instanceArrayList.toArray(fishOrReachInstances);
		}else{
			instanceArrayList = MetaProject.getInstance().getOpenProject().getHabs();
			fishOrReachInstances = new String[instanceArrayList.size()];
			instanceArrayList.toArray(fishOrReachInstances);
		}
		fishOrReachComboBox.setModel(new javax.swing.DefaultComboBoxModel(fishOrReachInstances));
		fishOrReachComboBox.setName("fishOrReachComboBox");
		if(fishOrReachInstances.length>0){
			fishOrReachComboBox.setVisible(true);
		}else{
			fishOrReachComboBox.setVisible(false);
		}
		fishOrReachComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				fishOrReachComboBoxActionPerformed(evt);
			}
		});

		//SOURCE FROM FILE
		sourceButtonGroup.add(copyFromExistingButton);
		copyFromExistingButton.setText(swapText(resourceMap.getString("sourceFromFileButton.text")));
		copyFromExistingButton.setName("sourceFromFileButton"); 
		copyFromExistingButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sourceFromFileButtonActionPerformed(evt);
			}
		});

		//OUTPUT PARAMS
		sourceButtonGroup.add(sourceDefaultButton);
		if(fishOrReachInstances.length>0){
		}else{
		}
		sourceDefaultButton.setText(swapText(resourceMap.getString("sourceDefaultButton.text")));
		sourceDefaultButton.setName("sourceDefaultButton"); 
		sourceDefaultButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sourceDefaultButtonActionPerformed(evt);
			}
		});

		if(fishOrReachInstances.length>0){
			copyFromExistingButton.setSelected(true);
			sourceDefaultButton.setSelected(false);
		}else{
			copyFromExistingButton.setSelected(false);
			copyFromExistingButton.setVisible(false);
			sourceDefaultButton.setSelected(true);
		}
		
		//NEW NAME TEXT FIELD
		newNameTextField.setName("newNameTextField");

		// LABELS
		instructionsLabel.setText(swapText(resourceMap.getString("instructionsLabel.text")));
		newNameLabel.setText(swapText(resourceMap.getString("newNameLabel.text")));
		filenameLabel.setText("");

		// SUBMIT/CANCEL
		submitButton.setName("submitButton");
		submitButton.setText(swapText(resourceMap.getString("submitButton.text")));
		submitButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				submitButtonActionPerfomed(evt);
			}
		});
		cancelButton.setName("cancelButton");
		cancelButton.setText(resourceMap.getString("cancelButton.text"));
		cancelButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				cancelButtonActionPerfomed(evt);
			}
		});

		newFishReachPanel = new javax.swing.JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setName("addExperimentParameter"); 

		GroupLayout parameterTypeLayout = new GroupLayout(newFishReachPanel);
		newFishReachPanel.setLayout(parameterTypeLayout);
		parameterTypeLayout.setHorizontalGroup(
				parameterTypeLayout.createParallelGroup(GroupLayout.LEADING)
				.add(parameterTypeLayout.createSequentialGroup()
						.addContainerGap()
						.add(parameterTypeLayout.createParallelGroup(GroupLayout.LEADING)
								.add(instructionsLabel)
								.add(copyFromExistingButton)
								.add(parameterTypeLayout.createSequentialGroup()
										.add(fishOrReachComboBox)
										.add(filenameLabel))
										.add(sourceDefaultButton)
										.add(newNameLabel)
										.add(newNameTextField)
										.add(parameterTypeLayout.createSequentialGroup()
												.add(submitButton)
												.addPreferredGap(LayoutStyle.RELATED)
												.add(cancelButton)))
												.addContainerGap())
		);
		parameterTypeLayout.setVerticalGroup(
				parameterTypeLayout.createParallelGroup(GroupLayout.LEADING)
				.add(parameterTypeLayout.createSequentialGroup()
						.addContainerGap()
						.add(instructionsLabel)
						.addPreferredGap(LayoutStyle.RELATED)
						.add(copyFromExistingButton)
						.add(parameterTypeLayout.createParallelGroup(GroupLayout.CENTER)
								.add(fishOrReachComboBox)
								.add(filenameLabel))
								.addPreferredGap(LayoutStyle.UNRELATED)
								.add(sourceDefaultButton)
								.add(10,15,30)
								.add(newNameLabel)
								.addPreferredGap(LayoutStyle.RELATED)
								.add(newNameTextField)
								.add(10,15,30)
								.add(parameterTypeLayout.createParallelGroup(GroupLayout.LEADING)
										.add(submitButton)
										.add(cancelButton))
										.addContainerGap())
		);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.LEADING)
				.add(layout.createSequentialGroup()
						.addContainerGap()
						.add(newFishReachPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.LEADING)
				.add(layout.createSequentialGroup()
						.addContainerGap()
						.add(newFishReachPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		pack();
	}

	private String swapText(String label){
		return label.replaceAll("<species/reach>", this.fishOrReach);
	}

	private void fishOrReachComboBoxActionPerformed(ItemEvent evt) {
	}
	private void sourceFromFileButtonActionPerformed(java.awt.event.ActionEvent evt) {
		fishOrReachComboBox.setVisible(true);
		filenameLabel.setVisible(true);
		this.newFishReachPanel.revalidate();
	}
	private void sourceDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {
		fishOrReachComboBox.setVisible(false);
		filenameLabel.setVisible(false);
		this.newFishReachPanel.revalidate();
	}

	private void submitButtonActionPerfomed(java.awt.event.ActionEvent evt) {
		if(newNameTextField.getText().trim().equals("")){
			JOptionPane.showMessageDialog(this, "Please enter a name for the "+fishOrReach);
			return;
		}
		if(fishOrReach.equals("species")){
			if(this.openProject.getFish().contains(newNameTextField.getText())){
				JOptionPane.showMessageDialog(this, "The species '"+newNameTextField.getText()+"' already exists in this model configuration.");
				return;
			}else{
				try{
					if(copyFromExistingButton.isSelected()){
						String selectedFishOrReachName = fishOrReachComboBox.getSelectedItem().toString();
						fishReachFile = new File(MetaProject.getInstance().getOpenProject().getProjectDirectory().getAbsolutePath()+"/"+MetaProject.getInstance().getOpenProject().getSetupParameters("speParam-"+selectedFishOrReachName).getFileName());
						openProject.addNewSpecies(selectedFishOrReachName, newNameTextField.getText());
					}else{
						openProject.addNewSpecies(null, newNameTextField.getText());
					}
					parent.setNewSpeciesName(newNameTextField.getText());
					parent.addSpeciesSubmitted();
				}catch (IOException e){
					JOptionPane.showMessageDialog(this,"<html><body><font size=+2><b>Species add operation cancelled</b></font><br/><br/>An error occurred reading the parameter file:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
					e.printStackTrace();
					return;
				}catch (RuntimeException e){
					JOptionPane.showMessageDialog(this,"<html><body><font size=+2><b>Species add operation cancelled</b></font><br/><br/>An error occurred reading the parameter file:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
					e.printStackTrace();
					return;
				}
			}
		}else if(fishOrReach.equals("reach")){
			if(this.openProject.getHabs().contains(newNameTextField.getText())){
				JOptionPane.showMessageDialog(this, "The reach '"+newNameTextField.getText()+"' already exists in this model configuration.");
				return;
			}else{
				try{
					if(copyFromExistingButton.isSelected()){
						String selectedFishOrReachName = fishOrReachComboBox.getSelectedItem().toString();
						fishReachFile = new File(MetaProject.getInstance().getOpenProject().getProjectDirectory().getAbsolutePath()+"/"+MetaProject.getInstance().getOpenProject().getSetupParameters("habParam-"+selectedFishOrReachName).getFileName());
						openProject.addNewReach(selectedFishOrReachName, newNameTextField.getText());
					}else{
						openProject.addNewReach(null, newNameTextField.getText());
					}
					parent.setNewReachName(newNameTextField.getText());
					parent.addReachSubmitted();
				}catch (IOException e){
					JOptionPane.showMessageDialog(this,"<html><body><font size=+2><b>Reach add operation cancelled</b></font><br/><br/>An error occurred reading the parameter file:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
					return;
				}catch (RuntimeException e){
					JOptionPane.showMessageDialog(this,"<html><body><font size=+2><b>Reach add operation cancelled</b></font><br/><br/>An error occurred reading the parameter file:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
					return;
				}
			}
		}
		this.dispose();
	}
	private void cancelButtonActionPerfomed(java.awt.event.ActionEvent evt){
		this.dispose();
	}


}
