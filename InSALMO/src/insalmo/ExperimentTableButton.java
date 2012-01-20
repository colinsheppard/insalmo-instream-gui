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
package insalmo;

import insalmo.MetaParameter.ValidationType;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class ExperimentTableButton extends JButton{
	boolean isAddButton;
	Project proj;
	String paramName;
	String paramKey;
	DefaultTableModel model;
	int row;
	
	public ExperimentTableButton(String buttonType, Project proj,String paramName,String paramKey,DefaultTableModel model, int row) {
		super(buttonType);
		this.proj = proj;
		this.model = model;
		this.paramName = paramName;
		this.paramKey = paramKey;
		this.row = row;
		if(buttonType.equals("Add Value")){
			this.isAddButton = true;
//			this.setIcon(new ImageIcon("icons/addValue.gif"));
		}else{
			this.isAddButton = false;
//			this.setIcon(new ImageIcon("icons/removeValue.gif"));
		}
		this.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				((ExperimentTableButton) evt.getSource()).doAddRemove();
			}
		});       
//		this.setOpaque(true);
//		this.setBackground(Color.WHITE);
//		this.setSize(20, 20);
	}
	 
	public void doAddRemove(){
		int lastRow = this.model.getRowCount()-1;
		ArrayList<Parameter> theValues = this.proj.getExperimentParameters(this.paramKey).getValues();
		if(isAddButton){
			this.proj.getExperimentParameters(this.paramKey).addValue(MetaProject.getInstance().getMetaParameter(this.paramName).getDefaultValue());
			this.model.setValueAt("Value",lastRow,0);
			this.model.setValueAt(MetaProject.getInstance().getMetaParameter(this.paramName).getDefaultValue(),lastRow,1);
			this.model.setValueAt(this.paramName,lastRow,2);
			this.model.setValueAt(new ExperimentTableButton("Remove Value",this.proj,this.paramName,this.paramKey,this.model,lastRow), lastRow, 4);
			this.model.setValueAt(theValues.get(theValues.size()-1), lastRow, 5);
			this.model.addRow(new Object[]{"","","","",this,null});
		}else{
//			if(theValues.get(this.row-4).getValidationType()!=ValidationType.VALID){
//				theValues.get(this.row-4).setParameterValue(theValues.get(this.row-4).getMetaParameter().getDefaultValue());
//			}
			this.proj.removeFromErrorsWarnings(theValues.get(this.row-4));
			theValues.remove(this.row-4); 
			this.model.setValueAt(null,this.row,5);
			for(int i=this.row; i<lastRow-1; i++){
				this.model.setValueAt(this.model.getValueAt(i+1, 1),i,1);
				this.model.setValueAt(this.model.getValueAt(i+1, 4),i,4);
				((ExperimentTableButton)this.model.getValueAt(i,4)).setRow(i);
				this.model.setValueAt(this.model.getValueAt(i+1,5),i,5);
			}
			this.model.setValueAt(null,lastRow-1,5);
			this.model.setValueAt("",lastRow-1,0);
			this.model.setValueAt("",lastRow-1,1);
			this.model.setValueAt("",lastRow-1,2);
			this.model.setValueAt("",lastRow-1,3);
			this.model.setValueAt(this.model.getValueAt(lastRow,4), lastRow-1, 4);
			this.model.removeRow(lastRow);
		}
		this.proj.updateExperimentParamValidations();
	}
	public void setRow(int row){
		this.row = row;
	}
	
	
}
