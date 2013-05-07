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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JPanel;

/*
 * This class stores all of the meta data associated with the different types of parameters.
 * 
 * Parameter Types (page references are to the InSTREAM Manual):
 * 	habSetup	habitat setup data (pp 184)
 * 	habParam	habitat parameter data (pp 187)
 * 	speSetup	species setup data (pp 183)
 * 	speParam	species parameter data (pp 187)
 *  modSetup	model setup data (pp 185)
 *  expSetup	experiemnt setup data (pp 216)
 *  obsSetup	observer setup data (pp 182)
 *  lftSetup	limiting factor tool configuration
 */

public class MetaProject {
    private static MetaProject instance = null;
    private static String[] paramTypes = {"modSetup","obsSetup","habSetup","speSetup","lftSetup","habParam","speParam","expSetup","expParam"};
    private static String[] staticFileNames = {"Model.Setup","Observer.Setup","Reach.Setup","Species.Setup","LimitingFactorsTool.Setup"};
    private static String[] variableFileNames = {"",""}; // These are set depending on the version 
    private static Hashtable<String,MetaParameter> metaParamByParamName = new Hashtable<String,MetaParameter>();
    private static Hashtable<String,ArrayList<String>>	paramNameListByParamType = new Hashtable<String,ArrayList<String>>();
    private Project openProject = null;
	private boolean projectChanged = false;
	private JPanel	contentPanel = new javax.swing.JPanel();
	private String applicationDirPath = null;
	private String version = "instream-sd";  // "insalmo", "instream", "instream-sd"
	private InsalmoInstreamView insalmoInstreamView;
	private Boolean isInstreamVar = false;
	private Boolean isInstreamSDVar = false;
	private Boolean isInsalmoVar = false;

    public static synchronized MetaProject getInstance() {
        if (instance == null) {
            instance = new MetaProject();
        }
        return (instance);
    }
	protected MetaProject(){
		if(version.equals("instream")){
			isInstreamVar = true;
			variableFileNames[0] = "ExampleSiteA-Hab.Params";
			variableFileNames[1] = "ExampleTrout.Params";
		}else if(version.equals("instream-sd")){
			isInstreamSDVar = true;
			variableFileNames[0] = "UpperSiteHabitat.Params";
			variableFileNames[1] = "DefaultRainbowTrout.Params";
		}else{
			isInsalmoVar = true;
			variableFileNames[0] = "ClearCreek3A-Hab.Params";
			variableFileNames[1] = "FallChinook.Params";
		}
	}
	public void Initialize(){
		System.out.println("Running "+this.version+" in dir: "+this.applicationDirPath);
		for(int i=0; i<paramTypes.length; i++){
			String paramType = paramTypes[i];
			ArrayList<String> nameList = new ArrayList<String>();
	        try {
	            FileInputStream fstream = new FileInputStream(applicationDirPath+"/metaprojectdata/" + version + "/" + paramType + ".txt");
	            DataInputStream in = new DataInputStream(fstream);
	            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	            String strLine;
	            boolean beginData = false;
	            while (!(strLine = br.readLine()).trim().equals("@end")) {
	                if (strLine.trim().equals("@begin")) {
	                    beginData = true;
	                } else if (beginData) {
	                    String[] splitStr = strLine.trim().split("\\t+");
	                    if(splitStr.length<1){
	                    	continue;
	                    }
	                    String paramName = splitStr[0].trim();
	                    MetaParameter metaP = null;
	                    if(splitStr.length<4){
	                    	System.err.println("Error: too few arguments specified in file "+paramType+".txt"+" \nline: "+strLine.trim());
	                    	System.exit(1);
	                    }else if(splitStr.length==4){
	                    	metaP = new MetaParameter(paramName, splitStr[1].trim(),
	                            splitStr[2].trim(), splitStr[3].trim(), paramType,"0","","");
	                    }else if(splitStr.length==5){
	                    	metaP = new MetaParameter(paramName, splitStr[1].trim(),
	                            splitStr[2].trim(), splitStr[3].trim(), paramType, splitStr[4].trim(),"","");
	                    }else if(splitStr.length==6){
	                    	metaP = new MetaParameter(paramName, splitStr[1].trim(),
	                            splitStr[2].trim(), splitStr[3].trim(), paramType, splitStr[4].trim(),splitStr[5].trim(),"");
	                    }else{
	                    	metaP = new MetaParameter(paramName, splitStr[1].trim(),
	                            splitStr[2].trim(), splitStr[3].trim(), paramType, splitStr[4].trim(), splitStr[5].trim(),splitStr[6].trim());
	                    }
	                    metaParamByParamName.put(paramName, metaP);
	                    nameList.add(paramName);
	                }
	            }
	            in.close();
	        } catch (Exception e) {//Catch exception if any
	            System.err.println("Error: " + e.getMessage());
	        }
	        MetaProject.paramNameListByParamType.put(paramType, nameList);
		}
	}
	
	public MetaParameter getMetaParameter(String paramName){
		if(parameterExists(paramName)){
			return(metaParamByParamName.get(paramName));
		}else{
			return null;
		}
	}
	public String getParameterType(String paramName){
		return(metaParamByParamName.get(paramName).getParameterType());
	}
	public MetaParameter.DataType getDataType(String paramName){
		return(metaParamByParamName.get(paramName).getDataType());
	}
	/*
	 * Verify whether a parameter name is a valid name from among any possible parameter in the project
	 */
	public boolean parameterExists(String paramName){
		return(metaParamByParamName.containsKey(paramName));
	}
	/*
	 * Verify whether a parameter name is a valid name for a specific type of parameter 
	 */
	public boolean parameterExists(String paramName, String paramType){
		return(metaParamByParamName.containsKey(paramName) && metaParamByParamName.get(paramName).getParameterType().equals(paramType));
	}
	
	public ArrayList<String> getParameterNameListFromType(String paramType){
		return (this.paramNameListByParamType.get(paramType));
	}
	
	public Enumeration getAllParameterNames(){
		return this.metaParamByParamName.keys();
	}
	public Integer getParameterCount(){
		return this.metaParamByParamName.size();
	}
	public boolean hasProjectChanged() {
		return (this.openProject!=null && this.openProject.hasProjectChanged());
	}
	public void setProjectChanged(boolean projectChanged) {
		setProjectChanged(projectChanged,null);
	}
	public void setProjectChanged(boolean projectChanged, String paramName) {
		if(this.openProject!=null){ 
			this.openProject.setProjectChanged(projectChanged);
			if(paramName != null && 
					(paramName.equals("numUncertaintyScenarios") || 
					paramName.equals("numScenarios") ||
					paramName.equals("numUncertaintyParams"))){
				this.insalmoInstreamView.actionHandler.updateLFTRunsPerExperiment();
			}
		}
	}
	public Project getOpenProject() {
		return openProject;
	}
	public void setOpenProject(Project openProject) {
		this.openProject = openProject;
	}
	public void updateErrorsWarnings(Parameter param){
		if(this.openProject!=null){	this.openProject.updateErrorsWarnings(param); }
	}
	public JPanel getContentPanel() {
		return contentPanel;
	}
	public void setContentPanel(JPanel contentPanel) {
		this.contentPanel = contentPanel;
	}
	public String[] getParamTypes(){
		return this.paramTypes;
	}
	public String[] getStaticFileNames(){
		return this.staticFileNames;
	}
	public String[] getVariableFileNames(){
		return this.variableFileNames;
	}
	public String[] getAllFileNames(){
		String[] allFilenames = new String[this.staticFileNames.length + this.variableFileNames.length];
		int i=0;
		for(String fName : this.staticFileNames){
			allFilenames[i++] = fName;
		}
		for(String fName : this.variableFileNames){
			allFilenames[i++] = fName;
		}
		return allFilenames;
	}
	// Copies src file to dst file. 
	// If the dst file does not exist, it is created
	public void copy(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst); 

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		} 
		in.close();
		out.close();
	}

	public void setApplicationDir(File file) {
		this.applicationDirPath  = file.getAbsolutePath();
	} 
	public String getApplicationDir(){
		return this.applicationDirPath;
	}
	public String getVersion(){
		return this.version;
	}
	public Boolean isInsalmo(){
		return isInsalmoVar;
	}
	public Boolean isInstreamSD(){
		return isInstreamSDVar;
	}
	public Boolean isInstream(){
		return isInstreamVar;
	}
	public void setInSALMOView(InsalmoInstreamView inSALMOView) {
		this.insalmoInstreamView = inSALMOView;
	}
	public InsalmoInstreamView getInSALMOView(){
		return this.insalmoInstreamView;
	}
	public String getAppTitle(){
		return (this.version.equals("instream") ? "InSTREAM" : (this.version.equals("instream-sd") ? "InSTREAM-6" : "InSALMO"));
	}
}
