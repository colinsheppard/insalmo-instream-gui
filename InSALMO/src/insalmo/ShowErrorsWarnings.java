/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * newProjectWizard2.java
 *
 * Created on Jan 4, 2010, 2:57:50 PM
 */

package insalmo;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTableHeader;

/**
 *
 * @author critter
 */
public class ShowErrorsWarnings extends javax.swing.JFrame {
    InSALMOView parent;
    Project openProject;
    private javax.swing.JPanel showErrorWarningsPanel;
    private JButton closeButton = new JButton();
    private JXTable errorTable;
    private JLabel	errorLabel = new JLabel();

    /** Creates new form */
    public ShowErrorsWarnings(InSALMOView parent, Project openProject) {
        this.parent = parent;
        this.openProject = openProject;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(insalmo.InSTREAMConfigApp.class).getContext().getResourceMap(ShowErrorsWarnings.class);
        
        // CLOSE
        closeButton.setName("closeButton");
        closeButton.setText(resourceMap.getString("closeButton.text"));
        closeButton.addActionListener(new java.awt.event.ActionListener(){
        	public void actionPerformed(java.awt.event.ActionEvent evt){
        		closeButtonActionPerfomed(evt);
        	}
        });
        
        showErrorWarningsPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("showErrorsWarnings"); 

        // ERROR/WARNING TABLE
        errorTable = this.createXTable(); 
        errorTable.setName("errorTable"); 
        errorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        DefaultTableModel dataModel = new DefaultTableModel() {
            Class[] types = new Class[]{
           		java.lang.String.class,java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, Parameter.class
            };
            boolean[] canEdit = new boolean[]{
                false,false, false, false, false,false
            };
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        dataModel.setColumnIdentifiers(new String[]{"","Parameter","Value", "Location","Message","Parameter Object"});
        for(Parameter p: this.openProject.getErrors()){
        	String filename;
        	if(p.getSource().getClass().equals(SetupParameters.class)){
        		filename = ((SetupParameters)p.getSource()).getFileName();
        	}else{
        		filename = "Experiment.Setup";
        	}
	        dataModel.addRow(new Object[]{"Error",p.getParameterName(), p.getParameterValue(), filename,p.getValidationMessage(),p});
        }
        for(Parameter p: this.openProject.getWarnings()){
        	String filename;
        	if(p.getSource().getClass().equals(SetupParameters.class)){
        		filename = ((SetupParameters)p.getSource()).getFileName();
        	}else{
        		filename = "Experiment.Setup";
        	}
	        dataModel.addRow(new Object[]{"Warning",p.getParameterName(), p.getParameterValue(), filename,p.getValidationMessage(),p});
        }
        errorTable.setModel(dataModel);
        errorTable.getColumnExt("Parameter Object").setVisible(false);
        errorTable.addMouseListener(new MouseListener(){
	  public void mouseClicked(MouseEvent e) {
		  forwardEventToButton(e);
	  }
	  public void mouseEntered(MouseEvent e) {
	  }
	  public void mouseExited(MouseEvent e) {
	  }
	  public void mousePressed(MouseEvent e) {
	  }
	  public void mouseReleased(MouseEvent e) {
	  }

});
        JScrollPane scrollpane = new JScrollPane(errorTable); 
        
        // LABEL
        errorLabel.setText(resourceMap.getString("errorLabel.text"));
        
        javax.swing.GroupLayout showErrorWarningsTypeLayout = new javax.swing.GroupLayout(showErrorWarningsPanel);
        showErrorWarningsPanel.setLayout(showErrorWarningsTypeLayout);
        showErrorWarningsTypeLayout.setHorizontalGroup(
            showErrorWarningsTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showErrorWarningsTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(showErrorWarningsTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                	.addComponent(errorLabel)
	                .addComponent(scrollpane)
	           		.addComponent(closeButton))
                .addContainerGap())
        );
        showErrorWarningsTypeLayout.setVerticalGroup(
            showErrorWarningsTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(showErrorWarningsTypeLayout.createSequentialGroup()
                .addContainerGap()
               	.addComponent(errorLabel)
                .addComponent(scrollpane)
           		.addComponent(closeButton)
                .addContainerGap())
        );
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showErrorWarningsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showErrorWarningsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }
	  private void forwardEventToButton(MouseEvent e) {
	    if(e.getID()==e.MOUSE_CLICKED){
			JXTable table = ((JXTable)e.getSource());
			Integer row = table.rowAtPoint(e.getPoint());
			String location = (String) table.getValueAt(row, 3);
			table.getColumnExt("Parameter Object").setVisible(true);
		    this.parent.goToParameter((Parameter)table.getValueAt(row,5),location);
	    }
	    return;
	  }

    private void closeButtonActionPerfomed(java.awt.event.ActionEvent evt){
    	this.dispose();
    }

    private JXTable createXTable() { 
        JXTable table = new JXTable() { 
        	// Add this override to have a button in a cell.
        	@Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                return new DefaultTableCellRenderer() {
                	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
						Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
						String val = "";
						if (value.getClass().equals(String.class)) {
							val = (String) value;
						} else if (value.getClass().equals(MetaParameter.class)) {
							val = ((MetaParameter) value).getParameterName();
						}
						Parameter p = (Parameter)table.getModel().getValueAt(row, 5);
						if(p!=null){
							String msg = p.getValidationMessage();
							String toolTip = "<html>";
							if(!msg.equals(""))toolTip = toolTip + "<b>" + msg+"</b><br><br>";
							toolTip = toolTip + p.getParameterTitle() + "</html>";
							this.setToolTipText(toolTip);
						}
						return renderer;
                	}
                };
            }
 
            @Override 
            protected JTableHeader createDefaultTableHeader() { 
                return new JXTableHeader(columnModel) { 
                    @Override 
                    public void updateUI() { 
                        super.updateUI(); 
                        // need to do in updateUI to survive toggling of LAF 
                        if (getDefaultRenderer() instanceof JLabel) { 
                            ((JLabel) getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER); 
                             
                        } 
                    } 
                }; 
            } 
             
        }; 
        return table; 
    } 
}
