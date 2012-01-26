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
package insalmo_instream_gui;

import insalmo_instream_gui.MetaParameter.ValidationCode;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
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
