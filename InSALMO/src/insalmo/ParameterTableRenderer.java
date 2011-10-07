/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package insalmo;

import insalmo.MetaParameter.ValidationCode;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author critter
 */
//public class ParameterTableRenderer extends DefaultTableCellRenderer implements TableCellRenderer{
public class ParameterTableRenderer extends DefaultTableCellRenderer {

	DateFormat formatter;

	public ParameterTableRenderer() {
		super();
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if(column>=4){
			return (Component)value;
		}
		Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		String val = "";
		if (value.getClass().equals(String.class)) {
			val = (String) value;
		} else if (value.getClass().equals(MetaParameter.class)) {
			val = ((MetaParameter) value).getParameterName();
		}
		Parameter p = (Parameter)table.getModel().getValueAt(row, 5);
		renderer.setBackground(Color.WHITE);
		renderer.setForeground(Color.BLACK);
		if(p!=null){
			String msg = p.getValidationMessage();
			switch (p.getValidationType()) {
			case VALID:
				break;
			case CHOOSING:
				renderer.setBackground(Color.GREEN);
				break;
			case WARNING:
				renderer.setBackground(Color.YELLOW);
				break;
			case ERROR:
				renderer.setBackground(Color.RED);
				break;
			}
			if(column==3){
				setValue(msg);
			}
			String toolTip = "<html>";
			if(!msg.equals(""))toolTip = toolTip + "<b>" + msg+"</b><br><br>";
			toolTip = toolTip + p.getParameterTitle() + "</html>";
			this.setToolTipText(toolTip);
		}
		return renderer;
	}

	@Override
	public void setValue(Object value) {
		if(value!=null)setText(value.toString());
	}
}
