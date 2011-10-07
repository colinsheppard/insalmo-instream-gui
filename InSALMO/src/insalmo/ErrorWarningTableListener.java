package insalmo;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class ErrorWarningTableListener implements TableModelListener{
    public ErrorWarningTableListener() {
        
    }

	public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        DefaultTableModel model = (DefaultTableModel)e.getSource();
        if(column==-1 || row >= model.getRowCount())return;
        String parName = (String)model.getValueAt(row,2);
        String data = ((String)model.getValueAt(row, 1)).trim();
        if(model.getValueAt(row,5)!=null)((Parameter)model.getValueAt(row,5)).setParameterValue(data);
        MetaProject.getInstance().setProjectChanged(true);
	}

}