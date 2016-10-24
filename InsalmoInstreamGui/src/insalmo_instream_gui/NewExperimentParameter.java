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

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/**
 *
 * @author critter
 */
public class NewExperimentParameter extends javax.swing.JFrame {
    InsalmoInstreamView parent;
    Project openProject;
    private javax.swing.JPanel parameterTypePanel;
    private javax.swing.ButtonGroup parameterTypeButtonGroup = new javax.swing.ButtonGroup();
    private javax.swing.JRadioButton parameterTypeInputButton;
    private javax.swing.JRadioButton parameterTypeOutputButton;
    private javax.swing.JRadioButton parameterTypeModelButton;
    private javax.swing.JRadioButton parameterTypeFishButton;
    private javax.swing.JRadioButton parameterTypeHabitatButton;
    private javax.swing.JComboBox parameterComboBox = new javax.swing.JComboBox();
    private javax.swing.JComboBox instanceComboBox = new javax.swing.JComboBox();
    private Hashtable<String,String[]> parameterLists = new Hashtable<String,String[]>();
    private JLabel parameterTypeLabel = new JLabel();
    private JLabel parameterLabel = new JLabel();
    private JLabel instanceLabel = new JLabel();
    private JButton submitButton = new JButton();
    private JButton cancelButton = new JButton();
    private String className = null; 
    private String rawInstanceLabel = null;

    /** Creates new form */
    public NewExperimentParameter(InsalmoInstreamView parent, Project openProject) {
        this.parent = parent;
        this.openProject = openProject;
        initComponents();
    }

    private void initComponents() {
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(insalmo_instream_gui.InsalmoInstreamApp.class).getContext().getResourceMap(NewExperimentParameter.class);
        
	    parameterTypeInputButton = new JRadioButton(resourceMap.getString("parameterTypeInputButton.text"));
	    parameterTypeOutputButton = new JRadioButton(resourceMap.getString("parameterTypeOutputButton.text"));
	    parameterTypeModelButton = new JRadioButton(resourceMap.getString("parameterTypeModelButton.text"));
	    parameterTypeFishButton = new JRadioButton(resourceMap.getString("parameterTypeFishButton.text"));
	    parameterTypeHabitatButton = new JRadioButton(resourceMap.getString("parameterTypeHabitatButton"));
        
        //INPUT PARAMS
        parameterTypeButtonGroup.add(parameterTypeInputButton);
        parameterTypeInputButton.setSelected(true);
        parameterTypeInputButton.setText(resourceMap.getString("parameterTypeInputButton.text"));
        parameterTypeInputButton.setName("parameterTypeInputButton"); 
        parameterTypeInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	parameterTypeInputButtonActionPerformed(evt);
            }
        });
        String[] inputParams = new String[] {"cellHabVarsFile","flowFile","temperatureFile","turbidityFile","driftFoodFile"};
        this.parameterLists.put("input",inputParams);
        this.className = "HabitatSpace";
        
        // PARAM COMBO BOX
        parameterComboBox.setModel(new javax.swing.DefaultComboBoxModel(inputParams));
        parameterComboBox.setName("parameterComboBox"); 
        parameterComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				parameterComboBoxActionPerformed(evt);
			}
		});
        
        //OUTPUT PARAMS
        parameterTypeButtonGroup.add(parameterTypeOutputButton);
        parameterTypeOutputButton.setSelected(false);
        parameterTypeOutputButton.setText(resourceMap.getString("parameterTypeOutputButton.text"));
        parameterTypeOutputButton.setName("parameterTypeOutputButton"); 
        parameterTypeOutputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	parameterTypeOutputButtonActionPerformed(evt);
            }
        });
        String[] outputParams = new String[] {"fishOutputFile","fishMortalityFile","reddMortalityFile","reddOutputFile"};
        this.parameterLists.put("output",outputParams);
        
        //MODEL PARAMS
        parameterTypeButtonGroup.add(parameterTypeModelButton);
        parameterTypeModelButton.setSelected(false);
        parameterTypeModelButton.setText(resourceMap.getString("parameterTypeModelButton.text"));
        parameterTypeModelButton.setName("parameterTypeModelButton"); 
        parameterTypeModelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	parameterTypeModelButtonActionPerformed(evt);
            }
        });
        String[] modelParams = null;
        if(MetaProject.getInstance().isInsalmo()){
        	modelParams = new String[] {"randGenSeed","numberOfSpecies","runStartDate","runEndDate","popInitDate","appendFiles","tagFishColor","numSpawnerAdjuster"};
        }else{
        	modelParams = new String[] {"randGenSeed","numberOfSpecies","runStartDate","runEndDate","popInitDate","appendFiles","tagFishColor"};
        }
        this.parameterLists.put("model",modelParams);
        
        //FISH PARAMS
        parameterTypeButtonGroup.add(parameterTypeFishButton);
        parameterTypeFishButton.setSelected(false);
        parameterTypeFishButton.setText(resourceMap.getString("parameterTypeFishButton.text"));
        parameterTypeFishButton.setName("parameterTypeFishButton"); 
        parameterTypeFishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	parameterTypeFishButtonActionPerformed(evt);
            }
        });
        String[] fishParams = new String[MetaProject.getInstance().getParameterNameListFromType("speParam").size()];
        MetaProject.getInstance().getParameterNameListFromType("speParam").toArray(fishParams);
        this.parameterLists.put("fish",fishParams);
        
        //HAB PARAMS
        parameterTypeButtonGroup.add(parameterTypeHabitatButton);
        parameterTypeHabitatButton.setSelected(false);
        parameterTypeHabitatButton.setText(resourceMap.getString("parameterTypeHabitatButton.text"));
        parameterTypeHabitatButton.setName("parameterTypeHabitatButton"); 
        parameterTypeHabitatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	parameterTypeHabitatButtonActionPerformed(evt);
            }
        });
        String[] habParams = new String[MetaProject.getInstance().getParameterNameListFromType("habParam").size()];
        MetaProject.getInstance().getParameterNameListFromType("habParam").toArray(habParams);
        this.parameterLists.put("hab",habParams);
        
        // INSTANCE COMBO BOX
        instanceComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"ALL"}));
    	for(String habName : this.openProject.getHabs()){
    		this.instanceComboBox.addItem(habName);
    	}
        instanceComboBox.setName("instanceComboBox"); 
        instanceComboBox.setVisible(true);
        instanceComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				instanceComboBoxActionPerformed(evt);
			}
		});
        
        // LABELS
        rawInstanceLabel = resourceMap.getString("instanceLabel.text");
        parameterTypeLabel.setText(resourceMap.getString("parameterTypeLabel.text"));
        parameterLabel.setText(resourceMap.getString("parameterLabel.text"));
        instanceLabel.setText(rawInstanceLabel.replaceAll("<species-site>", "site"));
        instanceLabel.setVisible(true);
        
        // SUBMIT/CANCEL
        submitButton.setName("submitButton");
        submitButton.setText(resourceMap.getString("submitButton.text"));
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
        
        parameterTypePanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("addExperimentParameter"); 

        javax.swing.GroupLayout parameterTypeLayout = new javax.swing.GroupLayout(parameterTypePanel);
        parameterTypePanel.setLayout(parameterTypeLayout);
        parameterTypeLayout.setHorizontalGroup(
            parameterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parameterTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parameterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                	.addComponent(parameterTypeLabel)
                    .addComponent(parameterTypeInputButton)
                    .addComponent(parameterTypeOutputButton)
                    .addComponent(parameterTypeModelButton)
                    .addComponent(parameterTypeFishButton)
                    .addComponent(parameterTypeHabitatButton)
                    .addComponent(parameterLabel)
                    .addComponent(parameterComboBox)
                    .addComponent(instanceLabel)
                    .addComponent(instanceComboBox)
                    .addGroup(parameterTypeLayout.createSequentialGroup()
                    		.addComponent(submitButton)
                    		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    		.addComponent(cancelButton)))
                .addContainerGap())
        );
        parameterTypeLayout.setVerticalGroup(
            parameterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parameterTypeLayout.createSequentialGroup()
                .addContainerGap()
              	.addComponent(parameterTypeLabel)
                .addComponent(parameterTypeInputButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parameterTypeOutputButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parameterTypeModelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parameterTypeFishButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parameterTypeHabitatButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(parameterLabel)
                .addComponent(parameterComboBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(instanceLabel)
                .addComponent(instanceComboBox)
                .addGap(10,15,30)
                .addGroup(parameterTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    		.addComponent(submitButton)
                    		.addComponent(cancelButton))
                .addContainerGap())
        );
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(parameterTypePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(parameterTypePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void parameterTypeInputButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	this.parameterComboBox.removeAllItems();
    	for(String paramName : this.parameterLists.get("input")){
	    	this.parameterComboBox.addItem(paramName);
    	}
    	this.instanceComboBox.removeAllItems();
    	this.instanceComboBox.addItem("ALL");
    	for(String habName : this.openProject.getHabs()){
    		this.instanceComboBox.addItem(habName);
    	}
    	this.instanceComboBox.setVisible(true);
        this.instanceLabel.setText(rawInstanceLabel.replaceAll("<species-site>", "site"));
    	this.instanceLabel.setVisible(true);
    	this.className = "HabitatSpace";
    	this.parameterTypePanel.revalidate();
    }
    private void parameterTypeOutputButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	this.parameterComboBox.removeAllItems();
    	for(String paramName : this.parameterLists.get("output")){
	    	this.parameterComboBox.addItem(paramName);
    	}
    	this.instanceComboBox.removeAllItems();
    	this.instanceComboBox.addItem("ALL");
    	this.instanceComboBox.setVisible(false);
    	this.instanceLabel.setVisible(false);
    	this.className = "TroutModelSwarm";
    	this.parameterTypePanel.revalidate();
    }
    private void parameterTypeModelButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	this.parameterComboBox.removeAllItems();
    	for(String paramName : this.parameterLists.get("model")){
	    	this.parameterComboBox.addItem(paramName);
    	}
    	this.instanceComboBox.removeAllItems();
    	this.instanceComboBox.addItem("ALL");
    	this.instanceComboBox.setVisible(false);
    	this.instanceLabel.setVisible(false);
    	this.className = "TroutModelSwarm";
    	this.parameterTypePanel.revalidate();
    }
    private void parameterTypeFishButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	this.parameterComboBox.removeAllItems();
    	for(String paramName : this.parameterLists.get("fish")){
	    	this.parameterComboBox.addItem(paramName);
    	}
    	this.instanceComboBox.removeAllItems();
    	this.instanceComboBox.addItem("ALL");
    	for(String fishName : this.openProject.getFish()){
    		this.instanceComboBox.addItem(fishName);
    	}
    	this.instanceComboBox.setVisible(true);
        this.instanceLabel.setText(rawInstanceLabel.replaceAll("<species-site>", "species"));
        this.instanceLabel.setVisible(true);
    	this.className = "FishParams";
    	this.parameterTypePanel.revalidate();
    }
    private void parameterTypeHabitatButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	this.parameterComboBox.removeAllItems();
    	for(String paramName : this.parameterLists.get("hab")){
	    	this.parameterComboBox.addItem(paramName);
    	}
    	this.instanceComboBox.removeAllItems();
    	this.instanceComboBox.addItem("ALL");
    	for(String habName : this.openProject.getHabs()){
    		this.instanceComboBox.addItem(habName);
    	}
    	this.instanceComboBox.setVisible(true);
        this.instanceLabel.setText(rawInstanceLabel.replaceAll("<species-site>", "site"));
    	this.instanceLabel.setVisible(true);
    	this.className = "HabitatSpace";
    	this.parameterTypePanel.revalidate();
    }
    private void parameterComboBoxActionPerformed(ItemEvent evt) {
    }
    private void instanceComboBoxActionPerformed(ItemEvent evt) {
    }
    private void submitButtonActionPerfomed(java.awt.event.ActionEvent evt){
    	String newName = (String)this.parameterComboBox.getSelectedItem();
    	String newInstance = (String)this.instanceComboBox.getSelectedItem();
    	if(newInstance.equals("ALL"))newInstance = "NONE";
    	this.parent.actionHandler.addExperimentParamSubmitted(newName,newInstance,this.className);
    	this.dispose();
    }
    private void cancelButtonActionPerfomed(java.awt.event.ActionEvent evt){
    	this.dispose();
    }


}
