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
package insalmo_instream_gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ParameterTableMouseListener implements MouseListener {
	private JTable table;

	public ParameterTableMouseListener(JTable table) {
		this.table = table;
	}

	private void forwardEventToButton(MouseEvent e) {
		TableColumnModel columnModel = table.getColumnModel();
		int column = columnModel.getColumnIndexAtX(e.getX());
		if(column!=4)return;
		int row    = e.getY() / table.getRowHeight();
		Object value;
		JButton button;
		MouseEvent buttonEvent;

		if(row >= table.getRowCount() || row < 0 ||
				column >= table.getColumnCount() || column < 0)
			return;

		value = table.getValueAt(row, column);

		if(!(value instanceof JButton))
			return;

		button = (JButton)value;
		if(e.getID()==e.MOUSE_CLICKED){
			button.doClick();
		}
		// This is necessary so that when a button is pressed and released
		// it gets rendered properly.  Otherwise, the button may still appear
		// pressed down when it has been released.
		table.repaint();
	}

	private void maybeOpenFileChooserOrInstancePicker(MouseEvent e){
		TableColumnModel columnModel = table.getColumnModel();
		int column = columnModel.getColumnIndexAtX(e.getX());
		if(column!=1)return;
		int row    = e.getY() / table.getRowHeight();

		if(row >= table.getRowCount() || row < 0 ||
				column >= table.getColumnCount() || column < 0)
			return;

		Parameter p;
		JButton button;
		MouseEvent buttonEvent;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		p = (Parameter)model.getValueAt(row, 5);
		if(p==null)return;
		if(p.getDataType() == MetaParameter.DataType.INFILENAME){
			String previousValue = p.getParameterValue();
			table.setValueAt("<"+previousValue+">", row, column);
			table.repaint();
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(MetaProject.getInstance().getOpenProject().getProjectDirectory());
//			fc.setCurrentDirectory(new File("."));
			fc.setDialogTitle("Select a File");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showOpenDialog(MetaProject.getInstance().getContentPanel());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// We have 3 cases to handle:
				//	1 - if the file chosen is already in the project directory, we do nothing but update the value in the cell
				//	2 - if the file chosen is not from the project directory but has the same name as an existing file, prompt to make sure they want an overwrite
				//	3 - if the file chosen is not from the project directory and does not share a name with an existing file, copy to project directory 
				if(!fc.getSelectedFile().getParent().equals(MetaProject.getInstance().getOpenProject().getProjectDirectory().getPath())){
					File testFile = new File(MetaProject.getInstance().getOpenProject().getProjectDirectory().getPath() + "/" + fc.getSelectedFile().getName());
					int result = 0;
					if(testFile.exists()){
						result = JOptionPane.showOptionDialog( MetaProject.getInstance().getContentPanel(), "<html>A file with the same filename <i>\""+fc.getSelectedFile().getName()+
								"\"</i> already exists in the <br>project directory, should the file be overwritten by the selected file?</html>","Warning",
								JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null,new String[]{"Overwrite Existing with Selected","Use Existing File","Cancel"}, "blah"); 
						if(result==2){
							return;
						}
					}
					if(result==0){
						try {
							MetaProject.getInstance().copy(fc.getSelectedFile(),testFile);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				table.setValueAt(fc.getSelectedFile().getName(), row, 1);
				table.repaint();
			}else{
				table.setValueAt(previousValue, row, column);
				table.repaint();
			}
		}else if(p.getParameterName().equals("InstanceName") && p.getParameterType().equals("expParam")){
			ArrayList<String> choices = new ArrayList<String>();
			String className = ((ExperimentParameter)p.getSource()).getClassName();
			Integer chosenIndex = 0;
			
			if(className.equals("TroutModelSwarm")){
				choices.add("NONE");
			}else if(className.equals("HabitatSpace")){
				choices.add("NONE");
				for(String hab : MetaProject.getInstance().getOpenProject().getHabs()){
					choices.add(hab);
				}
			}else if(className.equals("FishParams")){
				choices.add("NONE");
				for(String fish : MetaProject.getInstance().getOpenProject().getFish()){
					choices.add(fish);
				}
			}else{
				choices.add("");
			}
			for (String choice : choices) {
				if(choice.equals(p.getParameterValue())){
					chosenIndex = choices.indexOf(choice);
				}
			}
//			String[] choiceArray = (String[]) choices.toArray();
		    String userChoice = (String) JOptionPane.showInputDialog(MetaProject.getInstance().getContentPanel(), "Select an instance...",
		        "Select an instance", JOptionPane.QUESTION_MESSAGE, null, 
		        choices.toArray(), // Array of choices
		        choices.get(chosenIndex));
		    if(userChoice!=null)table.setValueAt(userChoice, row, 1);
		}
	}

	public void mouseClicked(MouseEvent e) {
		forwardEventToButton(e);
		maybeOpenFileChooserOrInstancePicker(e);
	}

	public void mouseEntered(MouseEvent e) {
		//	    forwardEventToButton(e);
	}

	public void mouseExited(MouseEvent e) {
		//	    forwardEventToButton(e);
	}

	public void mousePressed(MouseEvent e) {
		//	    forwardEventToButton(e);
	}

	public void mouseReleased(MouseEvent e) {
		//	    forwardEventToButton(e);
	}
}
