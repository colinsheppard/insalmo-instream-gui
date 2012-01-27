package insalmo_instream_gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;

public class InsalmoInstreamActions {
	private InsalmoInstreamView parent;
	
	public InsalmoInstreamActions(InsalmoInstreamView parent) {
		this.parent = parent;
	}

	public void saveActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		saveProject();
	}
	public void saveasActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		File oldProjectDir = parent.projectDir;
		if(saveProjectAs()){
			if(!saveProject())return;
		}
		// If the new directory is different from what the previous, non-null, location, copy all INFILES to the new directory 
		if(oldProjectDir != null && !parent.projectDir.getAbsolutePath().equals(oldProjectDir.getAbsolutePath())){
			ArrayList<String> alreadyCopiedInfiles = new ArrayList<String>();
			ArrayList<String> filesToCopy = new ArrayList<String>();
			filesToCopy.addAll(getOpenProject().getAllInfileNames());
			for(String infileName : filesToCopy){
				if(!infileName.trim().equals("")){
					File oldInfile = new File(oldProjectDir.getAbsolutePath() + "/" + infileName);
					if(oldInfile.exists()){
						File newInfile = new File(parent.projectDir.getAbsolutePath() + "/" + infileName);
						if(newInfile.exists()){
							if(alreadyCopiedInfiles.contains(infileName))continue;
							int result = JOptionPane.showOptionDialog(parent.parentFrame, "<html>Attempting to copy input file named: <i>\""+oldInfile.getName()+
									"\"</i><br>from previous project directory: <i>\""+ oldProjectDir.getName() + "\"</i><br>to the new project directory: <i>\""+ parent.projectDir.getName() + "\"</i>,<br> but a file " +
									"with that name already exists in the new project directory, should the file be overwritten?</html>","Warning",
									JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,new String[]{"Overwrite File in New Project Directory","Use Existing File"}, "blah"); 
							if(result!=0){
								alreadyCopiedInfiles.add(infileName);
								continue;
							}
						}
						try {
							MetaProject.getInstance().copy(oldInfile, newInfile);
							alreadyCopiedInfiles.add(infileName);
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println(infileName);
					}
				}
			}
		}
	}
	public void terminateExperiment(ActionEvent evt){
		LaunchInsamloExecutable launcher = parent.lftLaunchers.get(((JButton)evt.getSource()).getName());
		if(launcher!=null && launcher.getProcess()!=null)launcher.getProcess().destroy();
	}
	public void summaryActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		revealSummaryPane();
	}
	public void revealSummaryPane(){
		parent.modelSetupTabbedPane.setVisible(false);
		parent.modelSummaryScrollPane.setVisible(true);
		parent.experimentTabbedPane.setVisible(false);
		parent.lftTabbedPane.setVisible(false);
		updateModelSummary();
	}
	public void updateModelSummary(){
		if(getOpenProject()==null){
			parent.modelSummarySubtitle.setText(parent.resourceMap.getString("modelSummarySubtitle.text"));
		}else{
			String theText = "<html>";
			theText += "<b>Species:</b> ";
			if(getOpenProject().getFish().size()>0){
				for(String fish : getOpenProject().getFish()){
					theText += fish+", ";
				}
				theText = theText.substring(0, theText.length()-2)+"<br>";
			}else{
				theText += "<i>No species have been created</i><br>";				
			}
			theText += "<br><b>Reaches:</b> ";
			if(getOpenProject().getHabs().size()>0){
				theText += "<br><table border=0 cellpadding=2>";
				for(String hab : getOpenProject().getHabs()){
					theText += "<tr><td colspan=3>"+hab+"</td></tr>";
					SetupParameters habSetup = getOpenProject().getSetupParameters("habSetup-"+hab);
					theText += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>Flow File:</td><td><i>"+habSetup.getParameter("flowFile").getParameterValue()+"<i></td></tr>";
					theText += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>Temperature File:</td><td><i>"+habSetup.getParameter("temperatureFile").getParameterValue()+"<i></td></tr>";
					theText += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td><td>Turbidity File:</td><td><i>"+habSetup.getParameter("turbidityFile").getParameterValue()+"<i></td></tr>";
				}
				theText = theText.substring(0, theText.length()-2)+"</td></tr></table><br>";
			}else{
				theText += "<i>No reaches have been created</i><br>";				
			}
			SetupParameters modSetup = getOpenProject().getSetupParameters("modSetup-");
			String startDate = modSetup.getParameter("runStartDate").getParameterValue();
			String endDate = modSetup.getParameter("runEndDate").getParameterValue();
			theText += "<b>Run Start/End Dates:</b> start: "+startDate+" --- end: "+endDate;
			theText += "<br><br><b>Experiment Setup</b>";
			theText += "<br><table border=0 cellpadding=2>";
			SetupParameters expSetup = getOpenProject().getSetupParameters("expSetup-");
			theText += "<tr><td>Number of Scenarios:</td><td><i>"+getOpenProject().getNumberOfScenarios().toString()+"</i></td></tr>";
			theText += "<tr><td>Number of Replicates:</td><td><i>"+expSetup.getParameter("numberOfReplicates").getParameterValue()+"</i></td></tr>";
			if(parent.isINSTREAM){
				theText += "<tr><td>Number of Year Shuffler Replicates:</td><td><i>"+expSetup.getParameter("numberOfYearShufflerReplicates").getParameterValue()+"</i><br></td></tr>";
			}
			theText += "<tr><td colspan=2>Parameters varied in experiment and their values:</td></tr>";
			if(getOpenProject().exps.size()>0){
				for(String exp : getOpenProject().exps){
					theText += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<i>"+exp+":</i></td><td><i>";
					for(Parameter value : getOpenProject().getExperimentParameters(exp).getValues()){
						theText += value.getParameterValue()+", ";
					}
					theText = theText.substring(0,theText.length()-2);	
					theText += "</i></td></tr>";					
				}
			}else{
				theText += "<tr><td colspan=2>&nbsp;&nbsp;&nbsp;&nbsp;<i>no parameters varied in the experiment</i></td></tr>";
			}
			theText += "</table><br>";
			theText += "</html>";
			parent.modelSummarySubtitle.setText(theText);
		}
	}
	public boolean okToRunModel(){
		String osName = System.getProperty("os.name");
		return osName.contains("Windows");
	}
	public void runActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		runActionPerformed(evt, false);
	}
	public boolean cancelDueToErrors(){
		if(this.getOpenProject().errors.size()>0){
			String[] choices = {"Run with Errors","Cancel and show the errors","Cancel"};
			int result = JOptionPane.showOptionDialog(parent.parentFrame, "Errors exist in the project, run anyway?", "Warning", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
			if(result==1){
				errorWarningActionPerformed(new ActionEvent(this, 0,""));
				return true;
			}else if(result!=0){
				return true;
			}
		}
		return false;
	}
	public void runActionPerformed(java.awt.event.ActionEvent evt,boolean useGraphics) {
		if(cancelDueToErrors()){
			return;
		}
		if(MetaProject.getInstance().hasProjectChanged()){
			String[] choices = {"Save and Run", "CANCEL"};
			int result = JOptionPane.showOptionDialog(parent.parentFrame, "You have unsaved changes...", "Warning", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
			if(result==-1 || result==1){
				System.out.println("canceled run...");
				return;
			}
			if(result==0){
				System.out.println("saving first...");
				if(!saveProject())return;
			}
		}
		updateModelSummary();
		parent.modelSetupTabbedPane.setVisible(false);
		parent.modelSummaryScrollPane.setVisible(true);
		parent.experimentTabbedPane.setVisible(false);
		parent.runButton.setEnabled(false);
		parent.runMenuItem.setEnabled(false);
		parent.runWithGraphicsButton.setEnabled(false);
		parent.runWithGraphicsMenuItem.setEnabled(false);
		try {
			// launch EXE and grab stdout and stderr
			parent.modelLauncher = new LaunchInsamloExecutable(parent.projectDir.getAbsolutePath(),parent,useGraphics,false,parent.showConsoleTextArea,MetaProject.getInstance().getAppTitle()+" MODEL");
			StreamGobbler s1 = new StreamGobbler ("stdout", parent.modelLauncher.getProcess().getInputStream(),parent.showConsoleTextArea,parent.projectDir.getAbsolutePath()+"/Console_Output.Out",parent.newline);
			StreamGobbler s2 = new StreamGobbler ("stderr", parent.modelLauncher.getProcess().getErrorStream(),parent.showConsoleTextArea,parent.projectDir.getAbsolutePath()+"/Console_Output.Out",parent.newline);
			s1.start ();
			s2.start ();
			//			OutputStream killStream = modelLauncher.getProcess().getOutputStream();
		}catch (Exception err) {
			err.printStackTrace();
			System.exit(1);
		}		
	}
	public void modelRunCompleted(LaunchInsamloExecutable launcher){
		if(launcher!=null && launcher.getProcess().exitValue()>=0)launcher.textArea.append(parent.newline+parent.newline+"##############################"+parent.newline+launcher.getName()+" TERMINATED"+parent.newline+"##############################");
		if(launcher.isLFT){
			parent.lftLaunchers.remove(launcher.getName());
			if(parent.lftLaunchers.size()==0){
				parent.startLFTButton.setEnabled(true);
				if(!parent.lftTool.getTerminatedForcefully()){
					int result = JOptionPane.showOptionDialog(parent.parentFrame, "<html><h1>LFT Experiment Runs Complete</h1><br><br>" +
							"Process and view the results?  You can choose to do this later by clicking \"Process/View Results\" on the<br>" +
							"Liminting Factors Page )</html>","Confirm",
							JOptionPane.YES_NO_OPTION,JOptionPane.YES_NO_OPTION,null,new String[]{"Process and View Results Now","Not now."}, "blah"); 
					if(result==0)postProcessLFTActionPerformed();
				}
			}
		}else{
			if(okToRunModel()){
				parent.runButton.setEnabled(true);
				parent.runWithGraphicsButton.setEnabled(true);
				parent.runMenuItem.setEnabled(true);
				parent.runWithGraphicsMenuItem.setEnabled(true);
			}
			parent.viewResultsButton.setEnabled(true);
			parent.viewResultsMenuItem.setEnabled(true);
		}
	}
	public void postProcessLFTActionPerformed(){
		commitTables();
		try {
			String result = parent.lftTool.postProcessResults();
			if(!result.equals("")){
				int choice = JOptionPane.showOptionDialog(parent.parentFrame, "<html><body><h2>Post Processing Error Encountered</h2>" +
						"The following errors were encountered during post processing:<font color='red'>"+result+"</font><br>View results anyway?</body></html>","Confirm",
						JOptionPane.YES_NO_OPTION,JOptionPane.YES_NO_OPTION,null,new String[]{"View Results Now","Cancel"}, "blah");
				if(choice!=0)return;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parent.parentFrame,"<html><body><h2>Limiting Factors Tool Post Processing Cancelled</h2>" +
					"The following error was detected:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
			return;
		}
		try {
			File destinationFile = new File(parent.projectDir.getAbsolutePath()+"/LFT_Setup.xlsm");
			MetaProject.getInstance().copy(new File(MetaProject.getInstance().getApplicationDir()+"/DefaultProject/"+MetaProject.getInstance().getVersion()+"/LFT_Setup.xlsm"),destinationFile);
			BareBonesBrowserLaunch.openURL("file://"+destinationFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void showConsoleClearActionPerfomed(java.awt.event.ActionEvent evt){
		commitTables();
		parent.showConsoleTextArea.setText("");
	}
	public void terminateModelRunActionPerfomed(java.awt.event.ActionEvent evt){
		commitTables();
		if(parent.modelLauncher!=null && parent.modelLauncher.getProcess()!=null)parent.modelLauncher.getProcess().destroy();
	}
	public void runWithGraphicsActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		runActionPerformed(evt, true);
	}
	public void experimentActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		parent.modelSetupTabbedPane.setVisible(false);
		parent.modelSummaryScrollPane.setVisible(false);
		parent.experimentTabbedPane.setVisible(true);
		parent.lftTabbedPane.setVisible(false);
	}
	public void lftActionPerformed(){
		commitTables();
		parent.modelSetupTabbedPane.setVisible(false);
		parent.modelSummaryScrollPane.setVisible(false);
		parent.experimentTabbedPane.setVisible(false);
		parent.lftTabbedPane.setVisible(true);
	}
	public void addExperimentParamActionPerformed(java.awt.event.ActionEvent evt){
		commitTables();
		parent.newParamName = null;
		JFrame mainFrame = InsalmoInstreamApp.getApplication().getMainFrame();
		NewExperimentParameter expParamAdder = new NewExperimentParameter(parent, getOpenProject());
		expParamAdder.setLocationRelativeTo(mainFrame);
		InsalmoInstreamApp.getApplication().show(expParamAdder);
	}
	public void updateErrorWarningLinkButton(java.awt.event.ActionEvent evt){
		Integer numErr = 0; 
		Integer numWarn = 0; 
		if(evt.getSource().getClass().equals(Project.class)){
			numErr = ((Project)evt.getSource()).getNumErrors();
			numWarn = ((Project)evt.getSource()).getNumWarnings();
		}
		String errorStr = "Errors";
		if(numErr==1)errorStr="Error";
		String warnStr = "Warnings";
		if(numWarn==1)warnStr="Warning";
		parent.errorWarningButton.setText(numErr+" "+errorStr+" / "+numWarn+" "+warnStr);
		if(numErr+numWarn==0){
			parent.errorWarningButton.setEnabled(false);
		}else{
			parent.errorWarningButton.setEnabled(true);
		}
	}
	public void errorWarningActionPerformed(java.awt.event.ActionEvent evt){
		commitTables();
		parent.showErrorWarnings = new ShowErrorsWarnings(parent, getOpenProject());
		parent.showErrorWarnings.setLocationRelativeTo(parent.parentFrame);
		InsalmoInstreamApp.getApplication().show(parent.showErrorWarnings);
	}
	public void changeSpeParamActionPerformed(ActionEvent evt) {
		commitTables();
		Integer speIndex = Integer.parseInt(((JButton)evt.getSource()).getName().substring(20));
		ChangeSpeciesParameterFile changeParamFile = new ChangeSpeciesParameterFile(parent, getOpenProject(),speIndex);
		changeParamFile.setLocationRelativeTo(InsalmoInstreamApp.getApplication().getMainFrame());
		InsalmoInstreamApp.getApplication().show(changeParamFile);
	}
	public void removeHabitatActionPerformed(java.awt.event.ActionEvent evt){
		commitTables();
		Object[] habitatTitles = new Object[parent.habitatComboElements.toArray().length];
		for (int i = 0; i < parent.habitatComboElements.size(); i++) {
			habitatTitles[i] = parent.habitatComboElements.get(i)[0];
		}
		Object removeHabitatName = JOptionPane.showInputDialog(parent.parentFrame,"Select the name of reach to remove","Select the name of reach to remove",JOptionPane.QUESTION_MESSAGE,null,habitatTitles,habitatTitles[parent.habitatSetupComboBox.getSelectedIndex()]);
		if(removeHabitatName!=null){
			int foundIndex = -1;
			for (int i = 0; i < parent.habitatComboElements.size(); i++) {
				if(removeHabitatName.equals(parent.habitatComboElements.get(i)[0])){
					foundIndex = i;
				}
			}
			parent.habitatComboElements.remove(foundIndex);
			parent.habitatSetupComboBox.removeItemAt(foundIndex);
			parent.habitatParamComboBox.removeItemAt(foundIndex);
			parent.habitatSetupScrollPane.get(foundIndex).setVisible(false);
			parent.habitatSetupScrollPane.remove(foundIndex);
			parent.habitatParamFileLabel.get(foundIndex).setVisible(false);
			parent.habitatSetupFileLabel.remove(foundIndex);
			parent.habitatSetupTable.remove(foundIndex);
			parent.habitatParamTable.remove(foundIndex);
			parent.habitatSetupTab.revalidate();
			if(parent.habitatSetupComboBox.getItemCount()>0){
				parent.habitatSetupComboBox.setSelectedIndex(0);
				parent.habitatParamComboBox.setSelectedIndex(0);
				parent.habitatSetupScrollPane.get(0).setVisible(true);
			}
			this.getOpenProject().removeExperimentParameter((String)removeHabitatName);
			this.getOpenProject().removeHabitat((String)removeHabitatName);
			MetaProject.getInstance().setProjectChanged(true);
		}
	}
	public Project getOpenProject(){
		return parent.getOpenProject();
	}
	public void configureActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		parent.modelSummaryScrollPane.setVisible(false);
		parent.modelSetupTabbedPane.setVisible(true);
		parent.experimentTabbedPane.setVisible(false);
		parent.lftTabbedPane.setVisible(false);
	}
	public void exitActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		if(closeProject("Quit"))exitApplication();
	}
	public void exitApplication(){
		parent.parentFrame.quit();
		System.exit(0);
	}
	public void lftHideTabbedPane(java.awt.event.ContainerEvent evt) {
		parent.lftTabbedPane.setVisible(false);
	}
	public void lftHideExecutionTabbedPane(java.awt.event.ContainerEvent evt) {
		parent.lftExecutionTabbedPane.setVisible(false);
	}
	public void hideModelSetupTabbedPane(java.awt.event.ContainerEvent evt) {
		parent.modelSetupTabbedPane.setVisible(false);
	}
	public void openActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		openProject(null);
	}
	public void closeActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		closeProject("Close");
	}
	public void newActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		if(getOpenProject() != null){
			int response = JOptionPane.showConfirmDialog(parent.parentFrame, "You can only have one project open at a time, close the current project?","Project Already Open",JOptionPane.YES_NO_OPTION);
			if(response==0){
				closeProject("Close");
			}else{
				return;
			}
		}
		JFrame mainFrame = InsalmoInstreamApp.getApplication().getMainFrame();
		NewProjectWizard wizard = new NewProjectWizard(parent);
		wizard.setLocationRelativeTo(mainFrame);

		InsalmoInstreamApp.getApplication().show(wizard);
	}
	public void createNewProject(File projectFile, boolean fromScratch){
		System.out.println("Creating: " + projectFile.getName());
		parent.projectTitleLabel.setText("Project: "+projectFile.getName());
		parent.setOpenProject(new Project(projectFile));
		this.setProjectDir(projectFile);
		this.getOpenProject().createNewProject(fromScratch);
	}
	public void helpInterfaceActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		File helpFile = null;
		if(parent.isINSTREAM){
			helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/inSTREAM_5_0_GUI_Guide.chm");
		}else{
			helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/inSALMO_GUI_Guide.chm");
		}
		BareBonesBrowserLaunch.openURL("file://"+helpFile.getAbsolutePath());
	}
	public void helpModelActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		File helpFile = null;
		if(parent.isINSTREAM){
			helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/inSTREAM_5_0_Model_Description.chm");
		}else{
			helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/inSALMO_Model_Description.chm");
		}
		BareBonesBrowserLaunch.openURL("file://"+helpFile.getAbsolutePath());
	}
	public void helpSoftwareGuideActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		File helpFile = null;
		if(parent.isINSTREAM){
			helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/inSTREAM_5_0_Software_Documentation.chm");
		}else{
			helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/inSALMO_Software_Documentation.chm");
		}
		BareBonesBrowserLaunch.openURL("file://"+helpFile.getAbsolutePath());
	}
	public void helpLFTActionPerformed(java.awt.event.ActionEvent evt) {
		commitTables();
		File helpFile = null;
		if(parent.isINSTREAM){
			helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/inSTREAM_5_0_Limiting_Factors_Tool.chm");
		}else{
			helpFile = new File(MetaProject.getInstance().getApplicationDir()+"/../Help/inSALMO_Limiting_Factors_Tool.chm");
		}
		BareBonesBrowserLaunch.openURL("file://"+helpFile.getAbsolutePath());
	}
	public void commitTables(){
		if(parent.modelSetupTable.isEditing()){
			parent.modelSetupTable.getCellEditor().stopCellEditing();
		}else if(parent.observerSetupTable.isEditing()){
			parent.observerSetupTable.getCellEditor().stopCellEditing();
		}else if(parent.experimentControlTable.isEditing()){
			parent.experimentControlTable.getCellEditor().stopCellEditing();
		}else if(parent.lftSetupTable.isEditing()){
			parent.lftSetupTable.getCellEditor().stopCellEditing();
		}else{
			for(JTable tab : parent.speciesParamTable){
				if(tab.isEditing()){
					tab.getCellEditor().stopCellEditing();
					return;
				}
			}
			for(JTable tab : parent.speciesSetupTable){
				if(tab.isEditing()){
					tab.getCellEditor().stopCellEditing();
					return;
				}
			}
			for(JTable tab : parent.habitatParamTable){
				if(tab.isEditing()){
					tab.getCellEditor().stopCellEditing();
					return;
				}
			}
			for(JTable tab : parent.habitatSetupTable){
				if(tab.isEditing()){
					tab.getCellEditor().stopCellEditing();
					return;
				}
			}
			for(JTable tab : parent.experimentParamTable){
				if(tab.isEditing()){
					tab.getCellEditor().stopCellEditing();
					return;
				}
			}
		}
	}
	public void addHabitatActionPerformed(java.awt.event.ActionEvent evt){
		commitTables();
		parent.newReachName = null;
		JFrame mainFrame = InsalmoInstreamApp.getApplication().getMainFrame();
		NewFishOrReach fishOrReachAdder = new NewFishOrReach(parent, getOpenProject(),"reach");
		fishOrReachAdder.setLocationRelativeTo(mainFrame);
		InsalmoInstreamApp.getApplication().show(fishOrReachAdder);
	}
	public void addReachSubmitted(){
		if(parent.newReachName!=null){
			parent.habitatParamTable.add(ParameterTable.getInstance().makeTable("habParam",parent.newReachName));
			int i = parent.habitatParamTable.size()-1;
			parent.habitatParamTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"habParam",parent.newReachName));
			parent.habitatParamTable.get(i).removeColumn(parent.habitatParamTable.get(i).getColumnModel().getColumn(5));
			parent.habitatParamTable.get(i).removeColumn(parent.habitatParamTable.get(i).getColumnModel().getColumn(4));
			parent.habitatParamTable.get(i).setName("habitatTable"+parent.newReachName); 
			parent.habitatParamTable.get(i).getTableHeader().setReorderingAllowed(false);
			parent.habitatParamScrollPane.add(new javax.swing.JScrollPane());
			parent.habitatParamScrollPane.get(i).setName("habitatParamScrollPane"+i); 
			parent.habitatParamScrollPane.get(i).setViewportView(parent.habitatParamTable.get(i));
			parent.habitatParamScrollPane.get(i).setVisible(true);
			parent.habitatParamFileLabel.add(new javax.swing.JLabel());
			parent.habitatParamFileLabel.get(i).setText(parent.resourceMap.getString("configFileLabel.text")+" "+getOpenProject().getSetupParameters("habParam-"+parent.newReachName).getFileName()); 
			parent.habitatParamFileLabel.get(i).setName("habitatParamFileLabel"+i); 
			parent.habitatParamLayeredScrollPanesHGroup.addGroup(parent.habitatParamTabLayout.createParallelGroup()
					.addComponent(parent.habitatParamFileLabel.get(i))
					.addComponent(parent.habitatParamScrollPane.get(i),0, parent.resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE));
			parent.habitatParamLayeredScrollPanesVGroup.addGroup(parent.habitatParamTabLayout.createSequentialGroup()
					.addComponent(parent.habitatParamFileLabel.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(parent.habitatParamScrollPane.get(i),0,parent.resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE));
			parent.habitatSetupTable.add(ParameterTable.getInstance().makeTable("habSetup",parent.newReachName));
			parent.habitatSetupTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"habSetup",parent.newReachName));
			parent.habitatSetupTable.get(i).removeColumn(parent.habitatSetupTable.get(i).getColumnModel().getColumn(5));
			parent.habitatSetupTable.get(i).removeColumn(parent.habitatSetupTable.get(i).getColumnModel().getColumn(4));
			parent.habitatSetupTable.get(i).setName("habitatTable"+parent.newReachName); 
			parent.habitatSetupTable.get(i).getTableHeader().setReorderingAllowed(false);
			parent.habitatSetupScrollPane.add(new javax.swing.JScrollPane());
			parent.habitatSetupScrollPane.get(i).setName("habitatScrollPane"+i); 
			parent.habitatSetupScrollPane.get(i).setViewportView(parent.habitatSetupTable.get(i));
			parent.habitatSetupFileLabel.add(new javax.swing.JLabel());
			parent.habitatSetupFileLabel.get(i).setText(parent.resourceMap.getString("configFileLabel.text")+" Reach.Setup"); 
			parent.habitatSetupFileLabel.get(i).setName("habitatSetupFileLabel"+i); 
			parent.habitatSetupLayeredScrollPanesHGroup.addGroup(parent.habitatSetupTabLayout.createParallelGroup()
					.addComponent(parent.habitatSetupFileLabel.get(i))
					.addComponent(parent.habitatSetupScrollPane.get(i),0, parent.resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE));
			parent.habitatSetupLayeredScrollPanesVGroup.addGroup(parent.habitatSetupTabLayout.createSequentialGroup()
					.addComponent(parent.habitatSetupFileLabel.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(parent.habitatSetupScrollPane.get(i),0, parent.resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE));
			parent.habitatComboElements.add(new String[] {parent.newReachName,"habitatTable"+parent.newReachName});
			parent.habitatSetupComboBox.addItem(parent.newReachName);
			parent.habitatParamComboBox.addItem(parent.newReachName);
			parent.habitatSetupComboBox.setSelectedIndex(parent.habitatComboElements.size()-1);
			parent.habitatParamComboBox.setSelectedIndex(parent.habitatComboElements.size()-1);
			parent.habitatSetupTab.revalidate();
			parent.habitatParamTab.revalidate();
			MetaProject.getInstance().setProjectChanged(true);
		}
	}
	public void viewResultsActionPerformed(java.awt.event.ActionEvent evt){
		commitTables();
		File fishFile, reddsFile;
		SetupParameters modSetup = getOpenProject().getSetupParameters("modSetup-");
		String fishOutputFilename = null;
		if(parent.isINSTREAM){
			fishOutputFilename = modSetup.getParameter("fishOutputFile").getParameterValue();
		}else{
			fishOutputFilename = modSetup.getParameter("outmigrantOutputFile").getParameterValue();
		}
		String reddOutputFilename = modSetup.getParameter("reddOutputFile").getParameterValue();
		fishFile = new File(parent.projectDir.getAbsolutePath()+"/"+fishOutputFilename);
		reddsFile = new File(parent.projectDir.getAbsolutePath()+"/"+reddOutputFilename);
		if(!fishFile.exists()){
			JOptionPane.showMessageDialog(parent.parentFrame, "<html><body>Model run output not found, the output file named "+fishOutputFilename+" must be present <br>" +
			"in the project directory to view the results.  Either the model has not yet been run, or that output file was removed, please run the model again.</body></html>");
			return;
		}else if(!reddsFile.exists()){
			JOptionPane.showMessageDialog(parent.parentFrame, "<html><body>Model run output not found, the output file named "+reddOutputFilename+" must be present <br>" +
			"in the project directory to view the results.  Either the model has not yet been run, or that output file was removed, please run the model again.</body></html>");
			return;			
		}
		if((parent.isINSTREAM && !fishOutputFilename.equals("Live_Fish_Out.csv")) || 
				(!parent.isINSTREAM && !fishOutputFilename.equals("Outmigrants_Out.csv")) || 
				!reddOutputFilename.equals("Redds_Out.csv")){
			ArrayList<ArrayList> tab = new ArrayList<ArrayList>();
			ArrayList<String> col = new ArrayList<String>();
			if(parent.isINSTREAM){
				col.add("fishOutputFile");
			}else{
				col.add("outmigrantOutputFile");
			}
			col.add("reddOutputFile");
			tab.add(col);
			col.clear();
			col.add(fishOutputFilename);
			col.add(reddOutputFilename);
			try {
				parent.lftTool.writeTable(parent.projectDir.getAbsolutePath()+"/Analysis_Filenames.csv",tab);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			File destinationFile = new File(parent.projectDir.getAbsolutePath()+"/Analysis_Setup.xlsm");
			MetaProject.getInstance().copy(new File(MetaProject.getInstance().getApplicationDir()+"/DefaultProject/"+MetaProject.getInstance().getVersion()+"/Analysis_Setup.xlsm"),destinationFile);
			BareBonesBrowserLaunch.openURL("file://"+destinationFile.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void hideModelSummaryPane(java.awt.event.ContainerEvent evt) {
		parent.modelSummaryScrollPane.setVisible(false);
	}
	public void hideExperimentTabbedPane(java.awt.event.ContainerEvent evt) {
		parent.experimentTabbedPane.setVisible(false);
	}
	public void hideLftTabbedPane(java.awt.event.ContainerEvent evt) {
		parent.lftTabbedPane.setVisible(false);
	}
	public void experimentParamChangeHandler(java.awt.event.ItemEvent evt) {
		for(int i=0; i<parent.experimentParamComboElements.size(); i++){
			if(((String)evt.getItem()).equals(parent.experimentParamComboElements.get(i)[0])){
				if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED){
					parent.experimentParamScrollPane.get(i).setVisible(true);
					parent.experimentParamTab.revalidate();
				}else{
					parent.experimentParamScrollPane.get(i).setVisible(false);
				}
			}
		}
	}
	public void habitatChangeHandler(java.awt.event.ItemEvent evt) {
		if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED){
			for(int i=0; i<parent.habitatComboElements.size(); i++){
				if(((String)evt.getItem()).equals(parent.habitatComboElements.get(i)[0])){ 
					parent.habitatSetupScrollPane.get(i).setVisible(true);
					parent.habitatSetupFileLabel.get(i).setVisible(true);
					if(parent.habitatSetupComboBox.getItemCount()>0)parent.habitatSetupComboBox.setSelectedIndex(i);
					parent.habitatSetupTab.revalidate();

					parent.habitatParamScrollPane.get(i).setVisible(true);
					parent.habitatParamFileLabel.get(i).setVisible(true);
					if(parent.habitatParamComboBox.getItemCount()>0)parent.habitatParamComboBox.setSelectedIndex(i);
					parent.habitatParamTab.revalidate();
				}
			}
		}else{
			for(int i=0; i<parent.habitatSetupScrollPane.size(); i++){
				parent.habitatSetupScrollPane.get(i).setVisible(false);
				parent.habitatParamScrollPane.get(i).setVisible(false);
				parent.habitatSetupFileLabel.get(i).setVisible(false);
				parent.habitatParamFileLabel.get(i).setVisible(false);
			}
		}
	}
	public void speciesChangeHandler(java.awt.event.ItemEvent evt) {
		if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED){
			for(int i=0; i<parent.speciesComboElements.size(); i++){
				if(((String)evt.getItem()).equals(parent.speciesComboElements.get(i)[0])){ 
					parent.speciesSetupScrollPane.get(i).setVisible(true);
					parent.speciesSetupFileLabel.get(i).setVisible(true);
					parent.speciesSetupChangeParamButton.get(i).setVisible(true);
					if(parent.speciesSetupComboBox.getItemCount()>0)parent.speciesSetupComboBox.setSelectedIndex(i);
					parent.speciesSetupTab.revalidate();

					parent.speciesParamScrollPane.get(i).setVisible(true);
					parent.speciesParamFileLabel.get(i).setVisible(true);
					if(parent.speciesParamComboBox.getItemCount()>0)parent.speciesParamComboBox.setSelectedIndex(i);
					parent.speciesParamTab.revalidate();
				}
			}
		}else{
			for(int i=0; i<parent.speciesSetupScrollPane.size(); i++){
				parent.speciesSetupScrollPane.get(i).setVisible(false);
				parent.speciesSetupFileLabel.get(i).setVisible(false);
				parent.speciesSetupChangeParamButton.get(i).setVisible(false);
				parent.speciesParamScrollPane.get(i).setVisible(false);
				parent.speciesParamFileLabel.get(i).setVisible(false);
			}
		}
	}
	public void goToParameter(Parameter p){
		if(parent.showErrorWarnings!=null)parent.showErrorWarnings.dispose();
		parent.modelSetupTabbedPane.requestFocusInWindow();
		String paramType = p.getParameterType();
		if(p.getSource().getClass().equals(ExperimentParameter.class)){
			paramType = "expParam";
		}
		if(paramType.equals("lftSetup")){
			parent.modelSetupTabbedPane.setVisible(false);
			parent.modelSummaryScrollPane.setVisible(false);
			parent.experimentTabbedPane.setVisible(false);
			parent.lftTabbedPane.setVisible(true);
		}else if(paramType.equals("expSetup")){
			parent.modelSetupTabbedPane.setVisible(false);
			parent.modelSummaryScrollPane.setVisible(false);
			parent.lftTabbedPane.setVisible(false);
			parent.experimentTabbedPane.setVisible(true);
			parent.experimentTabbedPane.setSelectedIndex(0);
		}else if(paramType.equals("expParam")){
			parent.modelSetupTabbedPane.setVisible(false);
			parent.modelSummaryScrollPane.setVisible(false);
			parent.lftTabbedPane.setVisible(false);
			parent.experimentTabbedPane.setVisible(true);
			parent.experimentTabbedPane.setSelectedIndex(1);
			parent.experimentParamComboBox.setSelectedItem(((ExperimentParameter)p.getSource()).getParamKey());
		}else{
			String instance = ((SetupParameters)p.getSource()).getParamInstance();
			Integer tabIndex = 0;
			if(paramType.equals("obsSetup")){
				tabIndex = 1;
			}else if(paramType.equals("speSetup")){
				tabIndex = 2;
				parent.speciesSetupComboBox.setSelectedItem(instance);
			}else if(paramType.equals("speParam")){
				tabIndex = 3;
				parent.speciesParamComboBox.setSelectedItem(instance);
			}else if(paramType.equals("habSetup")){
				tabIndex = 4;
				parent.habitatSetupComboBox.setSelectedItem(instance);
			}else if(paramType.equals("habParam")){
				tabIndex = 5;
				parent.habitatParamComboBox.setSelectedItem(instance);
			}
			parent.modelSetupTabbedPane.setVisible(true);
			parent.modelSummaryScrollPane.setVisible(false);
			parent.experimentTabbedPane.setVisible(false);
			parent.lftTabbedPane.setVisible(false);
			parent.modelSetupTabbedPane.setSelectedIndex(tabIndex);
		}	
	}

	public void addExperimentParamSubmitted(String newName,String newInstanceName,String newClassName){
		parent.newParamName = newName;
		String newParamKey = null;
		if(newInstanceName.equals("NONE")){
			newParamKey = newName+" (ALL)";
		}else{
			newParamKey = newName+" ("+newInstanceName+")";
		}
		if(parent.newParamName!=null){
			// Error check the input
			if(this.getOpenProject().getExperimentParameters(newParamKey)!=null){
				JOptionPane.showMessageDialog(parent.experimentParamTab, "The parameter '"+newParamKey+"' already exists in this experiment.");
				return;
			}else if(MetaProject.getInstance().getMetaParameter(parent.newParamName)==null){
				JOptionPane.showMessageDialog(parent.experimentParamTab, "The parameter '"+parent.newParamName+"' is not a valid parameter that can be used in an experiment.");
				return;
			}
			ExperimentParameter expParam = new ExperimentParameter();
			expParam.setClassName(newClassName);
			expParam.setInstanceName(newInstanceName);
			expParam.setParamName(parent.newParamName);
			expParam.setValueType(MetaProject.getInstance().getMetaParameter(parent.newParamName).getDataTypeString());
			if(this.getOpenProject().getNumberOfScenarios()!=null){
				String defaultValue = MetaProject.getInstance().getMetaParameter(parent.newParamName).getDefaultValue();
				for(int i=0; i<this.getOpenProject().getNumberOfScenarios(); i++){
					expParam.addValue(defaultValue);
				}
			}
			this.getOpenProject().addExperimentParameter(expParam);
			parent.experimentParamTable.add(ParameterTable.getInstance().makeTable("expParam",newParamKey));
			int i = parent.experimentParamTable.size()-1;
			parent.experimentParamTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"expParam",newParamKey));
			parent.experimentParamTable.get(i).removeColumn(parent.experimentParamTable.get(i).getColumnModel().getColumn(5));
			parent.experimentParamTable.get(i).setName("expParam"+parent.experimentParamComboElements.size()); 
			parent.experimentParamTable.get(i).getTableHeader().setReorderingAllowed(false);
			parent.experimentParamScrollPane.add(new javax.swing.JScrollPane());
			parent.experimentParamScrollPane.get(i).setName("experimentParamScrollPane"+i); 
			parent.experimentParamScrollPane.get(i).setViewportView(parent.experimentParamTable.get(i));
			parent.experimentParamScrollPane.get(i).setVisible(true);
			parent.experimentParamLayeredScrollPanesHGroup.addComponent(parent.experimentParamScrollPane.get(i),0, parent.resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE);
			parent.experimentParamLayeredScrollPanesVGroup.addComponent(parent.experimentParamScrollPane.get(i),0, parent.resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE);
			parent.experimentParamComboElements.add(new String[] {newParamKey,"expParam"+parent.experimentParamComboElements.size()});
			parent.experimentParamComboBox.addItem(newParamKey);
			parent.experimentParamComboBox.setSelectedIndex(parent.experimentParamComboElements.size()-1);
			parent.experimentParamTab.revalidate();
			MetaProject.getInstance().setProjectChanged(true);
		}
	}
	public void setNewSpeciesName(String newName){
		parent.newSpeciesName = newName;
	}
	public void setNewReachName(String newName){
		parent.newReachName = newName;
	}
	public void startLFTActionPerformed(ActionEvent evt) {
		commitTables();
		String projDir = parent.projectDir.getAbsolutePath();
		try{
			parent.parentFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			parent.startLFTButton.setEnabled(false);
			Boolean doExecution = parent.lftTool.setupLFT();
			if(doExecution){
				parent.lftTabbedPane.setSelectedIndex(1);
				parent.lftTool.setTerminatedForcefully(false);
				parent.lftTool.executeLFT();
			}else{
				parent.startLFTButton.setEnabled(true);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(parent.parentFrame,"<html><body><font size=+2><b>Limiting Factors Tool Execution Cancelled</b></font><br/><br/>The following error was encountered:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
			e.printStackTrace();
			parent.startLFTButton.setEnabled(true);
		}finally{
			shutDownProject();
			revealSummaryPane();
			updateErrorWarningLinkButton(new ActionEvent(this,0,"Project closed"));
			openProject(new File(projDir));
			lftActionPerformed();
			parent.parentFrame.setCursor(Cursor.getDefaultCursor());
		}
	}
	public void killLFTActionPerformed(ActionEvent evt){
		commitTables();
		Enumeration launchers = parent.lftLaunchers.elements();
		while(launchers.hasMoreElements()){
			LaunchInsamloExecutable launcher = (LaunchInsamloExecutable)launchers.nextElement();
			if(launcher!=null && launcher.getProcess()!=null)launcher.getProcess().destroy();
		}
		parent.lftTool.setTerminatedForcefully(true);
	}
	public void clearExperimentActionPerformed(java.awt.event.ActionEvent evt){
		commitTables();
		int response = JOptionPane.showConfirmDialog(parent.parentFrame, "<html><body><b>Clear experiment?</b> This will remove all parameters " +
				"that are controlled by the experiment <br>manager and set the number of replicates to 1.</html></body>","Confirm Clear Experiment",JOptionPane.YES_OPTION);
		if(response==0){
			clearExperiment();
		}
	}
	public void clearExperiment(){
		ArrayList<String> expParamKeys = (ArrayList<String>) getOpenProject().getExperimentParamKeys().clone();
		for (String expParamKey : expParamKeys) {
			removeExperimentParam(expParamKey);
		}
		ParameterTable.getInstance().changeValueOfVariable(parent.experimentControlTable.getModel(), "numberOfReplicates", "1");
		ParameterTable.getInstance().changeValueOfVariable(parent.experimentControlTable.getModel(), "numberOfYearShufflerReplicates", "0");
	}
	public void removeExperimentParamActionPerformed(java.awt.event.ActionEvent evt){
		commitTables();
		if(parent.experimentParamComboElements.size()==0)return;
		Object[] paramTitles = new Object[parent.experimentParamComboElements.toArray().length];
		int selectedParamIndex;
		for (int i = 0; i < parent.experimentParamComboElements.size(); i++) {
			paramTitles[i] = parent.experimentParamComboElements.get(i)[0];
		}
		Object removeParamName = JOptionPane.showInputDialog(parent.parentFrame,"Select the name of parameter to remove","Select the name of parameter to remove",JOptionPane.QUESTION_MESSAGE,null,paramTitles,paramTitles[parent.experimentParamComboBox.getSelectedIndex()]);
		if(removeParamName!=null){
			removeExperimentParam((String)removeParamName);
		}
	}
	public void removeExperimentParam(String removeParamName){
		int foundIndex = -1;
		for (int i = 0; i < parent.experimentParamComboElements.size(); i++) {
			if(removeParamName.equals(parent.experimentParamComboElements.get(i)[0])){
				foundIndex = i;
			}
		}
		parent.experimentParamComboElements.remove(foundIndex);
		parent.experimentParamComboBox.removeItemAt(foundIndex);
		parent.experimentParamScrollPane.get(foundIndex).setVisible(false);
		parent.experimentParamScrollPane.remove(foundIndex);
		parent.experimentParamTable.remove(foundIndex);
		parent.experimentParamTab.revalidate();
		if(parent.experimentParamComboBox.getItemCount()>0){
			parent.experimentParamComboBox.setSelectedIndex(0);
			parent.experimentParamScrollPane.get(0).setVisible(true);
		}
		this.getOpenProject().removeExperimentParameter(removeParamName);
		MetaProject.getInstance().setProjectChanged(true);
	}
	public void addSpeciesActionPerformed(java.awt.event.ActionEvent evt){
		commitTables();
		parent.newSpeciesName = null;
		JFrame mainFrame = InsalmoInstreamApp.getApplication().getMainFrame();
		NewFishOrReach fishOrReachAdder = new NewFishOrReach(parent, getOpenProject(),"species");
		fishOrReachAdder.setLocationRelativeTo(mainFrame);
		InsalmoInstreamApp.getApplication().show(fishOrReachAdder);
	}
	public void addSpeciesSubmitted(){
		if(parent.newSpeciesName!=null){
			parent.speciesParamTable.add(ParameterTable.getInstance().makeTable("speParam",parent.newSpeciesName));
			int i = parent.speciesParamTable.size()-1;
			parent.speciesParamTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"speParam",parent.newSpeciesName));
			parent.speciesParamTable.get(i).removeColumn(parent.speciesParamTable.get(i).getColumnModel().getColumn(5));
			parent.speciesParamTable.get(i).removeColumn(parent.speciesParamTable.get(i).getColumnModel().getColumn(4));
			parent.speciesParamTable.get(i).setName("speciesTable"+parent.newSpeciesName); 
			parent.speciesParamTable.get(i).getTableHeader().setReorderingAllowed(false);
			parent.speciesParamScrollPane.add(new javax.swing.JScrollPane());
			parent.speciesParamScrollPane.get(i).setName("speciesParamScrollPane"+i); 
			parent.speciesParamScrollPane.get(i).setViewportView(parent.speciesParamTable.get(i));
			parent.speciesParamScrollPane.get(i).setVisible(true);
			parent.speciesParamFileLabel.add(new javax.swing.JLabel());
			parent.speciesParamFileLabel.get(i).setText(parent.resourceMap.getString("configFileLabel.text")+" "+getOpenProject().getSetupParameters("speParam-"+parent.newSpeciesName).getFileName()); 
			parent.speciesParamFileLabel.get(i).setName("speciesParamFileLabel"+i); 
			parent.speciesParamLayeredScrollPanesHGroup.addGroup(parent.speciesParamTabLayout.createParallelGroup()
					.addComponent(parent.speciesParamFileLabel.get(i))
					.addComponent(parent.speciesParamScrollPane.get(i),0, parent.resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE));
			parent.speciesParamLayeredScrollPanesVGroup.addGroup(parent.speciesParamTabLayout.createSequentialGroup()
					.addComponent(parent.speciesParamFileLabel.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(parent.speciesParamScrollPane.get(i),0, parent.resourceMap.getInteger("defaultContentSize.height.int"),  Short.MAX_VALUE));
			parent.speciesSetupTable.add(ParameterTable.getInstance().makeTable("speSetup",parent.newSpeciesName));
			parent.speciesSetupTable.get(i).setModel(ParameterTable.getInstance().getModel(getOpenProject(),"speSetup",parent.newSpeciesName));
			parent.speciesSetupTable.get(i).removeColumn(parent.speciesSetupTable.get(i).getColumnModel().getColumn(5));
			parent.speciesSetupTable.get(i).removeColumn(parent.speciesSetupTable.get(i).getColumnModel().getColumn(4));
			parent.speciesSetupTable.get(i).setName("speciesTable"+parent.newSpeciesName); 
			parent.speciesSetupTable.get(i).getTableHeader().setReorderingAllowed(false);
			parent.speciesSetupScrollPane.add(new javax.swing.JScrollPane());
			parent.speciesSetupScrollPane.get(i).setName("speciesScrollPane"+i); 
			parent.speciesSetupScrollPane.get(i).setViewportView(parent.speciesSetupTable.get(i));
			parent.speciesSetupFileLabel.add(new javax.swing.JLabel());
			parent.speciesSetupFileLabel.get(i).setText("<html>"+parent.resourceMap.getString("configFileLabel.text")+" Species.Setup<br>Parameter File: "+
					getOpenProject().getSetupParameters("speParam-"+parent.newSpeciesName).getFileName()+"</html>"); 
			parent.speciesSetupFileLabel.get(i).setName("speciesSetupFileLabel"+i); 
			parent.speciesSetupChangeParamButton.add(new JButton());
			parent.speciesSetupChangeParamButton.get(i).setText(parent.resourceMap.getString("changeSpeParamButton.text")); 
			parent.speciesSetupChangeParamButton.get(i).setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			parent.speciesSetupChangeParamButton.get(i).setName("changeSpeParamButton"+i);
			parent.speciesSetupChangeParamButton.get(i).setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
			if(parent.speciesSetupChangeParamButton.get(i).getActionListeners().length==0){
				parent.speciesSetupChangeParamButton.get(i).addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						changeSpeParamActionPerformed(evt);
					}
				});
			}
			parent.speciesSetupLayeredScrollPanesHGroup.addGroup(parent.speciesSetupTabLayout.createParallelGroup()
					.addComponent(parent.speciesSetupFileLabel.get(i))
					.addComponent(parent.speciesSetupChangeParamButton.get(i))
					.addComponent(parent.speciesSetupScrollPane.get(i),0, parent.resourceMap.getInteger("defaultContentSize.width.int"), Short.MAX_VALUE));
			parent.speciesSetupLayeredScrollPanesVGroup.addGroup(parent.speciesSetupTabLayout.createSequentialGroup()
					.addComponent(parent.speciesSetupFileLabel.get(i))
					.addComponent(parent.speciesSetupChangeParamButton.get(i))
					.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(parent.speciesSetupScrollPane.get(i),0, parent.resourceMap.getInteger("defaultContentSize.height.int"), Short.MAX_VALUE));
			parent.speciesComboElements.add(new String[] {parent.newSpeciesName,"speciesTable"+parent.newSpeciesName});
			parent.speciesSetupComboBox.addItem(parent.newSpeciesName);
			parent.speciesParamComboBox.addItem(parent.newSpeciesName);
			parent.speciesSetupComboBox.setSelectedIndex(i);
			parent.speciesParamComboBox.setSelectedIndex(i);
			parent.speciesSetupTab.revalidate();
			parent.speciesParamTab.revalidate();
			MetaProject.getInstance().setProjectChanged(true);
		}
	}
	public void removeSpeciesActionPerformed(java.awt.event.ActionEvent evt){
		commitTables();
		Object[] speciesTitles = new Object[parent.speciesComboElements.toArray().length];
		for (int i = 0; i < parent.speciesComboElements.size(); i++) {
			speciesTitles[i] = parent.speciesComboElements.get(i)[0];
		}
		Object removeSpeciesName = JOptionPane.showInputDialog(parent.parentFrame,"Select the name of species to remove","Select the name of species to remove",JOptionPane.QUESTION_MESSAGE,null,speciesTitles,speciesTitles[parent.speciesSetupComboBox.getSelectedIndex()]);
		if(removeSpeciesName!=null){
			int foundIndex = -1;
			for (int i = 0; i < parent.speciesComboElements.size(); i++) {
				if(removeSpeciesName.equals(parent.speciesComboElements.get(i)[0])){
					foundIndex = i;
				}
			}
			parent.speciesComboElements.remove(foundIndex);
			parent.speciesSetupComboBox.removeItemAt(foundIndex);
			parent.speciesParamComboBox.removeItemAt(foundIndex);
			parent.speciesSetupScrollPane.get(foundIndex).setVisible(false);
			parent.speciesSetupScrollPane.remove(foundIndex);
			parent.speciesSetupFileLabel.get(foundIndex).setVisible(false);
			parent.speciesSetupFileLabel.remove(foundIndex);
			parent.speciesSetupChangeParamButton.get(foundIndex).setVisible(false);
			parent.speciesSetupChangeParamButton.remove(foundIndex);
			parent.speciesSetupTable.remove(foundIndex);
			parent.speciesParamTable.remove(foundIndex);
			parent.speciesSetupTab.revalidate();
			if(parent.speciesSetupComboBox.getItemCount()>0){
				parent.speciesSetupComboBox.setSelectedIndex(0);
				parent.speciesParamComboBox.setSelectedIndex(0);
				parent.speciesSetupScrollPane.get(0).setVisible(true);
			}
			this.getOpenProject().removeSpecies((String)removeSpeciesName);
			this.getOpenProject().removeExperimentParameter((String)removeSpeciesName);
			MetaProject.getInstance().setProjectChanged(true);
		}
	}
	public File getProjectDir() {
		return parent.projectDir;
	}

	public void updateLFTRunsPerExperiment() {
		parent.lftRunsPerExpLabel.setText(parent.resourceMap.getString("lftSetupTab.runsPerExperiment.label")+parent.lftTool.getLFTRunsPerExperiment()); 
	}

	public boolean saveProject(){
		if(parent.projectDir==null && !saveProjectAs()){
			System.out.println("Project save cancelled by user");
			return false;
		}else if(parent.experimentValueErrorsExist()){
			JOptionPane.showMessageDialog(parent.parentFrame, 
					"<html>Errors exist in the experiment manager that prohibit saving.<br>" +
					"Click ok and you will be taken to the experiment manager so you can<br>" +
					"fix the errors.</html>", "Error: Cannot Save",JOptionPane.ERROR_MESSAGE);
			for(Parameter param : getOpenProject().errors){
				if(param.expParamSource!=null && param.validationCode == MetaParameter.ValidationCode.INCONSISTENT_SCENARIOS){
					goToParameter(param);
					return false;
				}
			}
			return false;
		}
		System.out.println("Saving project: "+parent.projectDir.getName());
		getOpenProject().save();
		MetaProject.getInstance().setProjectChanged(false);
		return true;
	}
	public boolean saveProjectAs(){
		final JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("."));
		fc.setDialogTitle("Select a directory to save the project to (the selected directory name will become the project name)");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false); // disable the "All files" option.
		File newDir = null;
		if(parent.projectDir==null){
			// Brand new project, suggest title
			newDir = new File("Specify_Project_Directory");
		}else{
			newDir = parent.projectDir;
		}
		fc.setSelectedFile( newDir );

		int returnVal = fc.showSaveDialog(parent.contentPanel);
		if( returnVal == JFileChooser.APPROVE_OPTION ){
			setProjectDir(fc.getSelectedFile());
			fc.getSelectedFile().mkdir();
			return(true);
		}else{
			System.out.println("Save command cancelled by user.");
			return(false);
		}
	}
	
	public void openProject(File projectFile){
		openProject(projectFile, true);
	}
	public void openProject(File projectFile, boolean showProject){
		if(this.getOpenProject() != null){
			int response = JOptionPane.showConfirmDialog(parent.parentFrame, "You can only have one project open at a time, close the current project?","Project Already Open",JOptionPane.YES_NO_OPTION);
			if(response==0){
				closeProject("Close");
			}else{
				return;
			}
		}
		if(projectFile==null){
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File("."));
			fc.setDialogTitle("Select a Project Directory");
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setAcceptAllFileFilterUsed(false); // disable the "All files" option.
			int returnVal = fc.showOpenDialog(parent.contentPanel);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				projectFile = fc.getSelectedFile();
			} else {
				System.out.println("Open command cancelled by user.");
				return;
			}
		}
		System.out.println("Opening: " + projectFile.getName());
		if(showProject)parent.projectTitleLabel.setText("Project: "+projectFile.getName());
		parent.setOpenProject(new Project(projectFile));
		try{
			// If the project directory is missing LimitingFactorsTool.Setup, copy the default version in
			File lftSetupFile = new File(projectFile.getAbsolutePath()+"/LimitingFactorsTool.Setup");
			if(!lftSetupFile.exists()){
				MetaProject.getInstance().copy(new File(MetaProject.getInstance().getApplicationDir()+"/DefaultProject/"+MetaProject.getInstance().getVersion()+"/LimitingFactorsTool.Setup"),lftSetupFile);
			}
			this.getOpenProject().loadProjectFiles();
		}catch (IOException e){
			JOptionPane.showMessageDialog(parent.parentFrame,"<html><body><font size=+2><b>Open operation cancelled</b></font><br/><br/>An error occurred reading a project file:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
			shutDownProject();
			updateErrorWarningLinkButton(new ActionEvent(this,0,"Project closed"));
			return;
		}catch (RuntimeException e){
			JOptionPane.showMessageDialog(parent.parentFrame,"<html><body><font size=+2><b>Open operation cancelled</b></font><br/><br/>An error occurred loading this project:<br/><br/><font color='red'> "+e.getMessage()+"</font></body></html>");
			shutDownProject();
			updateErrorWarningLinkButton(new ActionEvent(this,0,"Project closed"));
			return;
		}
		parent.projectDir = projectFile;
		if(showProject){
			parent.createParameterTabs();
			parent.initContentPanel();
			parent.enableModelButtons();
			MetaProject.getInstance().setContentPanel(parent.contentPanel);
			parent.modelSetupTabbedPane.setVisible(false);
			parent.modelSummaryScrollPane.setVisible(true);
			parent.experimentTabbedPane.setVisible(false);
			parent.lftTabbedPane.setVisible(false);
		}
		MetaProject.getInstance().setProjectChanged(false);

		// Test to make sure no illegal or missing parameters which we should warn the user about
		if(getOpenProject().isMissingParameters() | getOpenProject().containsIllegalParameters()){
			StringBuffer sb = new StringBuffer();
			sb.append( "<html><body><font color=RED size=+2>Warning</font><br><br>");
			if(getOpenProject().isMissingParameters()){
				sb.append("<b>Missing Parameters:</b> The following parameters were not found in the configuration files.<br>" +
						"These parameters will be added to the opened project with default values.<br><br>" );
				sb.append("<table border=1><tr><th align='left'>Parameter Name</th><th align='left'>File</th></tr>");
				for(String[] missing : getOpenProject().getMissingParameters()){
					sb.append("<tr><td align='left'>"+missing[0]+"</td><td align='left'>"+missing[1]+"</td></tr>");
				}
				sb.append("</table><br><br>");
			}
			if(getOpenProject().containsIllegalParameters()){
				sb.append("<b>Illegal Parameters:</b> The following illegal parameters were found in the configuration files.<br>" +
						"These parameters will be excluded from the opened project and will be eliminated from your<br>" +
						"configuration files upon save.<br><br>" );
				sb.append("<table border=1><tr><th align='left'>Parameter Name</th><th align='left'>Value</th><th align='left'>File</th><th align='left'>Error Message</th></tr>");
				for(String[] illegals : getOpenProject().getIllegalParameters()){
					sb.append("<tr><td align='left'>"+illegals[0]+"</td><td align='left'>"+illegals[1]+"</td><td align='left'>"+illegals[2]+"</td><td align='left'>"+illegals[3]+"</td></tr>");
				}
				sb.append("</table><br>");
			}
			sb.append( "</body></html>" );
			JLabel textarea = new JLabel( sb.toString());
			textarea.setVerticalAlignment(JLabel.TOP);
			JScrollPane scrollpane = new JScrollPane(textarea);
			scrollpane.setPreferredSize(new Dimension(600, 400));
			int result = JOptionPane.showOptionDialog(parent.parentFrame, scrollpane,"Warning",JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE,null,new String[]{"Continue","Close Without Save"},"Continue"); 
			if(result==1){
				shutDownProject();
			}else{
				MetaProject.getInstance().setProjectChanged(true);
			}
		}
		revealSummaryPane();
	}
	public boolean closeProject(String closeQuit){
		if(MetaProject.getInstance().hasProjectChanged()){
			String[] choices = {"Save and "+closeQuit, closeQuit+" without Save", "CANCEL"};
			int result = JOptionPane.showOptionDialog(parent.parentFrame, "You have unsaved changes...", "Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, choices, choices[0]);
			if(result==-1 || result==2){
				System.out.println("canceled close...");
				return false;
			}
			if(result==0){
				System.out.println("saving first...");
				if(!saveProject())return false;
			}
		}
		shutDownProject();
		revealSummaryPane();
		this.killLFTActionPerformed(new ActionEvent(this,0,""));
		updateErrorWarningLinkButton(new ActionEvent(this,0,"Project closed"));
		return true;
	}
	public void shutDownProject(){
		System.out.println("closing...");
		parent.removeProjectFromView();
		parent.setOpenProject(null);
		MetaProject.getInstance().setOpenProject(null);
		parent.disableModelButtons();
		updateModelSummary();
	}
	// HELPER METHODS
	public void setProjectDir(File dirFile){
		parent.projectDir = dirFile;
		parent.projectTitleLabel.setText(dirFile.getName());
		getOpenProject().setProjectDir(dirFile);
	}
}
