/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package insalmo;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author critter
 */
public class ParameterTableListener implements TableModelListener{
    public ParameterTableListener() {
        
    }

    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        DefaultTableModel model = (DefaultTableModel)e.getSource();
        if(column==-1 || row >= model.getRowCount())return;
        String parName = (String)model.getValueAt(row,2);
        String data = ((String)model.getValueAt(row, 1)).trim();
        if(model.getValueAt(row,5)!=null)((Parameter)model.getValueAt(row,5)).setParameterValue(data);
        MetaProject.getInstance().setProjectChanged(true,parName);
    }
    
}
