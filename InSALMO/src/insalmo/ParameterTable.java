/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package insalmo;

import insalmo.MetaParameter.ValidationCode;

import java.io.*;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.jdesktop.application.ResourceMap;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author critter
 */
public class ParameterTable {

    private static ParameterTable instance = null;
    private static MetaProject metaProj = null;
    private static ParameterTableRenderer parTabRenderer = null;

    public static ParameterTable getInstance() {
        if (instance == null) {
            instance = new ParameterTable();
            parTabRenderer = new ParameterTableRenderer();
            metaProj = MetaProject.getInstance();
        }
        return (instance);
    }
    protected ParameterTable() {
    }

    public JTable makeTable(String paramType, String paramInstance) {
    	String tableCode = paramType+"-"+paramInstance;
        JTable jtab = new JTable() {
            public TableCellRenderer getCellRenderer(int row, int column) {
                    return parTabRenderer;
            }
        };
        jtab.addMouseListener(new ParameterTableMouseListener(jtab));
        jtab.setRowHeight(22);
        return jtab;
    }
    public JXTable makeJXTable(String paramType, String paramInstance) {
    	String tableCode = paramType+"-"+paramInstance;
        JXTable jtab = new JXTable() {
            public TableCellRenderer getCellRenderer(int row, int column) {
                    return parTabRenderer;
            }
        };
        jtab.addMouseListener(new ParameterTableMouseListener(jtab));
        jtab.setRowHeight(22);
        return jtab;
    }

    public DefaultTableModel getModel(Project proj, String paramType, String paramKey) {
    	String tableCode = paramType+"-"+paramKey;
    	DefaultTableModel model = null;
        if(paramType.equals("expParam")){
	    	model = new DefaultTableModel() {
	            Class[] types = new Class[]{
	                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,JButton.class,Parameter.class
	            };
	            boolean[] canEdit = new boolean[]{
	                false, true, false, false, false,false
	            };
	            public Class getColumnClass(int columnIndex) {
	                return types[columnIndex];
	            }
	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	            	if(this.getRowCount()-1==rowIndex)return false;
	                return canEdit[columnIndex];
	            }
	        };
	        model.setColumnIdentifiers(new String[]{"Parameter","Value (editable)", "Variable Name","Errors/Warnings","Add/Remove Value","Param Object"});
	        int i=0;
       		String paramName = null;
        	for(Parameter p : proj.getExperimentParameters(paramKey).getParameterArrayList()){
        		String paramTitle = p.getParameterTitle();
        		ExperimentTableButton removeValueButton = null;
        		paramName = p.getParameterName();
        		if(paramName.equals(paramKey.substring(0, paramName.length()))){
        			paramTitle = "Value";
	        		removeValueButton = new ExperimentTableButton("Remove Value",proj,paramName,paramKey,model,i);
        		}
		        model.addRow(new Object[]{paramTitle, p.getParameterValue(), p.getParameterName(),"",removeValueButton,p});
		        i++;
			}
       		ExperimentTableButton addValueButton = new ExperimentTableButton("Add Value",proj,paramName,paramKey,model,i);
	        model.addRow(new Object[]{"","","","",addValueButton,null});
        }else{
	    	model = new DefaultTableModel() {
	            Class[] types = new Class[]{
	                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,JButton.class,Parameter.class
	            };
	            boolean[] canEdit = new boolean[]{
	                false, true, false, false,false,false
	            };
	            public Class getColumnClass(int columnIndex) {
	                return types[columnIndex];
	            }
	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit[columnIndex];
	            }
	        };
	        model.setColumnIdentifiers(new String[]{"Parameter","Value (editable)", "Variable Name","Errors/Warnings","BlankColumn","Param Object"});
	        for(Parameter p: proj.getSetupParameters(tableCode).getSortedParameterCollection()){
		        model.addRow(new Object[]{p.getParameterTitle(), p.getParameterValue(), p.getParameterName(),"",null,p});
	        }
        }
        ParameterTableListener parTabListener = new ParameterTableListener();
        model.addTableModelListener(parTabListener);
        return model;
    }
    public void changeValueOfVariable(TableModel tableModel, String variablName, String newValue){
    	int targetRow=-1;
    	for(int i=0; i<tableModel.getRowCount(); i++){
    		if(tableModel.getValueAt(i, 2).equals(variablName)){
    			targetRow = i;
    			break;
    		}
    	}
    	if(targetRow>-1)tableModel.setValueAt(newValue, targetRow, 1);
    }
}
