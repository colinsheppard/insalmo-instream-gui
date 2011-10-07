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
			if(theValues.get(this.row-4).getValidationType()!=ValidationType.VALID){
				theValues.get(this.row-4).setParameterValue(theValues.get(this.row-4).getMetaParameter().getDefaultValue());
				this.proj.updateErrorsWarnings(theValues.get(this.row-4));
			}
			theValues.remove(this.row-4); 
			for(int i=this.row; i<lastRow-1; i++){
				this.model.setValueAt(this.model.getValueAt(i+1, 1),i,1);
				this.model.setValueAt(this.model.getValueAt(i+1, 4),i,4);
				((ExperimentTableButton)this.model.getValueAt(i,4)).setRow(i);
				((Parameter)this.model.getValueAt(i,5)).setParameterValue(((Parameter)this.model.getValueAt(i+1, 5)).getParameterValue());
			}
			this.model.setValueAt(null,lastRow-1,5);
			this.model.setValueAt("",lastRow-1,0);
			this.model.setValueAt("",lastRow-1,1);
			this.model.setValueAt("",lastRow-1,2);
			this.model.setValueAt("",lastRow-1,3);
			this.model.setValueAt(this.model.getValueAt(lastRow,4), lastRow-1, 4);
			this.model.removeRow(lastRow);
		}
		this.proj.getSetupParameters("expSetup-").getParameter("numberOfScenarios").updateValidationCode();
	}
	public void setRow(int row){
		this.row = row;
	}
	
	
}
