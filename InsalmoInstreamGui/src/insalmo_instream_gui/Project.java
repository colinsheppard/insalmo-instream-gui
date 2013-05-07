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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Project {
	public static String newline = System.getProperty("line.separator");
	private String[][] beginEndFiles = { {"modSetup","Model.Setup"},{"obsSetup","Observer.Setup"},{"lftSetup","LimitingFactorsTool.Setup"} };  // files that use @begin and @end to mark the content
	private Hashtable<String,SetupParameters> setupParams = new Hashtable<String,SetupParameters>();
	private Hashtable<String,ExperimentParameter> expParams = new Hashtable<String,ExperimentParameter>();
	ArrayList<String> habs = new ArrayList<String>();
	ArrayList<String> fish = new ArrayList<String>();
	ArrayList<String> exps = new ArrayList<String>();
	ArrayList<String[]> illegals 	= new ArrayList<String[]>(); // contains: 0 param name, 1 param value, 2 filename, 3 error message
	ArrayList<String[]> missing 	= new ArrayList<String[]>(); // contains: 0 param name, 1 filename
	ArrayList<Parameter> errors 	= new ArrayList<Parameter>();
	ArrayList<Parameter> warnings	= new ArrayList<Parameter>();
	File projectDir;
	private Boolean projectChanged; 
	private ActionListener errorWarningUpdateListener;
	Integer numberOfScenarios;

	public Project(File projectDir){
		MetaProject.getInstance().setOpenProject(this);
		this.projectDir = projectDir;
	}
	public void createNewProject(boolean fromScratch){
		String[] paramTypes = MetaProject.getInstance().getParamTypes();
		String[] fileNames  = MetaProject.getInstance().getAllFileNames();
		String pValue = "";
		for (int i = 0; i < paramTypes.length-2; i++) {
			String paramType = paramTypes[i];
			String fileName = fileNames[i];
			SetupParameters sParams =  new SetupParameters(paramType,fileName);
			ArrayList<String> paramNameList = MetaProject.getInstance().getParameterNameListFromType(paramType);
			for (String paramName: paramNameList) {
				if(fromScratch){
					switch(MetaProject.getInstance().getMetaParameter(paramName).getDataType()){
					case FLOAT:
						pValue = "-999.0";
						break;
					case STRING:
					case INFILENAME:
					case OUTFILENAME:
						pValue = "---";
						break;
					case BOOL:
					case INTEGER:
						pValue = "-999";
						break;
					case DATE:
						pValue = "99/99/9999";
						break;
					case DAY:
						pValue = "-99/-99";
						break;
					}
					if(paramName.equals("reachName") || paramName.equals("speciesName")){
						pValue = MetaProject.getInstance().getMetaParameter(paramName).getDefaultValue();
					}
				}else{
					pValue = MetaProject.getInstance().getMetaParameter(paramName).getDefaultValue();
				}
				if(!paramName.equals("habParamFile") && !paramName.equals("speParamFile")){
					Parameter newParam = new Parameter(paramName,pValue,sParams);
					sParams.addParameter(newParam);				

					if(!fromScratch && newParam.getParameterDataType() == MetaParameter.DataType.INFILENAME){
						// Look for and copy the default version of the infile to the project directory, do not overwrite
						String defaultFileName = MetaProject.getInstance().getMetaParameter(newParam.getParameterName()).getDefaultValue();
						File defaultFile = new File(MetaProject.getInstance().getApplicationDir()+"/DefaultProject/"+MetaProject.getInstance().getVersion()+"/"+defaultFileName);
						if(defaultFile.exists()){
							try {
								MetaProject.getInstance().copy(defaultFile, new File(projectDir.getAbsolutePath()+"/"+defaultFileName));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}else{
							System.out.println("Warning: Cannot locate file "+defaultFile.getAbsolutePath());
						}
						newParam.updateValidationCode();
					}
				}
			}
			if(paramType.substring(0,3).equals("hab")){
				if(MetaProject.getInstance().isInstream()){
					sParams.setParamInstance("ExampleSiteA");
				}else if(MetaProject.getInstance().isInstreamSD()){
					sParams.setParamInstance("UpperSite");
				}else{
					sParams.setParamInstance("ClearCreek-3A");
				}
				if(paramType.equals("habParam")){
					this.addHabitat(this.setupParams.get("habSetup-"+sParams.getParamInstance()), sParams);
				}
			}
			if(paramType.substring(0,3).equals("spe")){
				if(MetaProject.getInstance().isInstream()){
					sParams.setParamInstance("ExampleTrout");
				}else if(MetaProject.getInstance().isInstreamSD()){
					sParams.setParamInstance("Rainbow");
				}else{
					sParams.setParamInstance("FallChinook");
				}
				if(paramType.equals("speParam")){
					this.addSpecies(this.setupParams.get("speSetup-"+sParams.getParamInstance()), sParams);
				}
			}
			this.setupParams.put(paramType+"-"+((sParams.getParamInstance()==null)?"":sParams.getParamInstance()), sParams);
			if(paramType.equals("habSetup")){
				File defaultFile = new File(MetaProject.getInstance().getApplicationDir()+"/DefaultProject/"+MetaProject.getInstance().getVersion()+"/"+fileName);
				if(defaultFile.exists()){
					File destinationFile = new File(projectDir+"/"+fileName);
					if(!destinationFile.exists())
						try {
							MetaProject.getInstance().copy(defaultFile, destinationFile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}else{
					System.out.println("Warning: Cannot locate file "+defaultFile.getAbsolutePath());
				}
			}
		}
		SetupParameters expSetup = new SetupParameters("expSetup","Experiment.Setup");
		expSetup.addParameter(new Parameter("numberOfReplicates","1",expSetup));
		if(!MetaProject.getInstance().isInsalmo()){
			expSetup.addParameter(new Parameter("numberOfYearShufflerReplicates","0",expSetup));
			expSetup.addParameter(new Parameter("firstShuffleYearSeed","1",expSetup));
		}else{
			this.setupParams.get("modSetup-").removeParameter("shuffleYears");
			this.setupParams.get("modSetup-").removeParameter("shuffleYearReplace");
			this.setupParams.get("modSetup-").removeParameter("shuffleYearSeed");
		}
		this.setupParams.put("expSetup-", expSetup);
	}
	public void loadProjectFiles() throws RuntimeException,IOException {
		FileInputStream fstream = null;
		int j=1;
		boolean beginData = false;
		for (int i = 0; i < beginEndFiles.length; i++) {
			SetupParameters sParams =  new SetupParameters(beginEndFiles[i][0],beginEndFiles[i][1]);
			try {
				fstream = new FileInputStream(projectDir.getPath()+"/"+beginEndFiles[i][1]);
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				beginData = false;
				int lineCount = 0;
				while ((strLine = br.readLine())!=null && !strLine.trim().equals("@end")) {
					lineCount++;
					if (strLine.trim().equals("@begin")) {
						beginData = true;
					} else if (beginData && strLine.length()>0 && !strLine.substring(0, 1).equals("#")) {
						if(strLine.trim().equals(""))continue;
						String[] splitStr = strLine.trim().split("\\s+");
						if(splitStr.length==1)throw new IOException(beginEndFiles[i][1] + "(line "+lineCount+") -- expecting a variable name and value with space between, instead found '"+splitStr[0]+"'");
						try{
							sParams.addParameter(new Parameter(splitStr[0],splitStr[1],sParams));
						}catch(IllegalParameterException e){
							this.illegals.add(new String[] {splitStr[0],splitStr[1],beginEndFiles[i][1],e.getMessage()});
						}
					}
				}
				in.close();
			} catch (IOException e) {//Catch exception if any
				fstream.close();
				throw e;
			} catch (RuntimeException e) {//Catch exception if any
				fstream.close();
				throw e;
			} catch (Exception e) {//Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}	
			if(!beginData)throw new IOException(beginEndFiles[i][1]+" -- token '@begin' never found.");
			this.setupParams.put(beginEndFiles[i][0]+"-", sParams);
		}
		this.setupParams.get("modSetup-").removeParameter("numberOfSpecies");
		try {
			fstream = new FileInputStream(projectDir.getPath()+"/Reach.Setup");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int lineCount = 0;
			beginData = false;
			SetupParameters sParams =  new SetupParameters("habSetup","Reach.Setup");
			while ((strLine = br.readLine())!=null) {
				lineCount++;
				if (strLine.trim().equals("REACHBEGIN")) {
					beginData = true;
				} else if (beginData) {
					if(strLine.trim().equals("REACHEND")){
						beginData = false;
						this.setupParams.put("habSetup-"+sParams.getParamInstance(), sParams);
						this.loadHabitatParams(sParams,projectDir.getPath()+"/"+sParams.getParameter("habParamFile").getParameterValue());
						sParams = new SetupParameters("habSetup","Reach.Setup");
						continue;
					}
					if(strLine.trim().equals(""))continue;
					String[] splitStr = strLine.trim().split("\\s+");
					if(splitStr.length==1)throw new IOException("Reach.Setup (line "+lineCount+") -- expecting a variable name and value with space between, instead found '"+splitStr[0]+"'");
					if(splitStr[0].equals("reachName")){
						if(habs.contains(splitStr[1])){
							System.err.println("Error: Habiatat Reach name: "+splitStr[1]+" already defined.");
							System.exit(1);
						}
						habs.add(splitStr[1]);
						sParams.setParamInstance(splitStr[1]);
					}
					if(sParams.getParameter(splitStr[0])!=null)throw new IOException("Reach.Setup (line "+lineCount+") -- variable name "+splitStr[0]+" specified twice in one Reach block.");
					try{
						sParams.addParameter(new Parameter(splitStr[0],splitStr[1],sParams));
					}catch(IllegalParameterException e){
						e.printStackTrace();
						this.illegals.add(new String[] {splitStr[0],splitStr[1],"Reach.Setup",e.getMessage()});
					}
				}
			}
			if(!beginData && habs.size()==0)throw new IOException("Reach.Setup -- expecting token 'REACHBEGIN' but never found.");
			in.close();
		} catch (IOException e) {//Catch exception if any
			fstream.close();
			throw e; 
		} catch (RuntimeException e) {//Catch exception if any
			fstream.close();
			throw e;
		} catch (Exception e) {//Catch exception if any
			fstream.close();
			System.err.println("Error: " + e.getMessage());
		}	
		try {
			fstream = new FileInputStream(projectDir.getPath()+"/Species.Setup");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int k = 1;
			int beginDataInt = 0;
			int lineCount = 0;
			SetupParameters sParams =  new SetupParameters("speSetup","Species.Setup");
			while ((strLine = br.readLine())!=null) {
				lineCount++;
				if(MetaProject.getInstance().isInstreamSD()){
					if((lineCount-4)%6==0 && !strLine.trim().equals(""))throw new IOException("Species.Setup (line "+lineCount+") -- expecting a blank line");
				}else{
					if((lineCount+1)%5==0 && !strLine.trim().equals(""))throw new IOException("Species.Setup (line "+lineCount+") -- expecting a blank line");
				}
				if (strLine.trim().equals("")) {
					beginDataInt = 1;
				} else if (beginDataInt>0) {
					String[] splitStr = strLine.trim().split("\\s+");
					try{
						switch(beginDataInt){
						case 1:
							if(fish.contains(splitStr[0])){
								throw new IllegalParameterException("Species name: "+splitStr[0]+" already defined.");
							}
							fish.add(splitStr[0]);
							sParams.setParamInstance(splitStr[0]);
							sParams.addParameter(new Parameter("speciesName",splitStr[0],sParams));
							break;
						case 2:
							sParams.addParameter(new Parameter("speciesParamFile",splitStr[0],sParams));
							break;
						case 3:
							sParams.addParameter(new Parameter("speciesInitPopFile",splitStr[0],sParams));
							break;
						case 4:
							sParams.addParameter(new Parameter("speciesColor",splitStr[0],sParams));
							if(!MetaProject.getInstance().isInstreamSD()){
								this.setupParams.put("speSetup-"+sParams.getParamInstance(), sParams);
								this.loadSpeciesParams(sParams,projectDir.getPath()+"/"+sParams.getParameter("speciesParamFile").getParameterValue());
								sParams =  new SetupParameters("speSetup","Species.Setup");
								beginDataInt=-1;
							}
							break;
						case 5:
							sParams.addParameter(new Parameter("speciesStockingFile",splitStr[0],sParams));
							this.setupParams.put("speSetup-"+sParams.getParamInstance(), sParams);
							this.loadSpeciesParams(sParams,projectDir.getPath()+"/"+sParams.getParameter("speciesParamFile").getParameterValue());
							sParams =  new SetupParameters("speSetup","Species.Setup");
							beginDataInt=-1;
							break;
						}
					}catch(IllegalParameterException e){
						this.illegals.add(new String[] {"",splitStr[0],"Species.Setup",e.getMessage()});
					}
					beginDataInt++;
				}
			}
			in.close();
		} catch (IOException e) {//Catch exception if any
			fstream.close();
			throw e;
		} catch (RuntimeException e) {//Catch exception if any
			fstream.close();
			throw e;
		} catch (Exception e) {//Catch exception if any
			fstream.close();
			System.err.println("Error: " + e.getMessage());
		}	
		resolveParamFilenameConflicts();

		try {
			fstream = new FileInputStream(projectDir.getPath()+"/Experiment.Setup");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			int lineCount=0;
			int k = 1;
			int beginDataInt = 0;
			int numValues = 0;
			SetupParameters expSetup =  new SetupParameters("expSetup","Experiment.Setup");
			ExperimentParameter expParam = null;
			while ((strLine = br.readLine())!=null) {
				lineCount++;
				if(strLine.length()>0 && strLine.charAt(0)=='#'){
					lineCount--; // pretend nothing happened
				}else if (k==4){
					beginDataInt = 1;
					k++;
				}else if (beginDataInt>0) {
					String[] splitStr = strLine.trim().split("\\s+");
					try{
						switch(beginDataInt){
						case 1:
							if(!splitStr[0].equals("numberOfScenarios")){
								throw new IOException("Experiment.Setup (line:"+lineCount+") contains unexpected input on 5th uncommented line: '"+splitStr[0]+"' found when 'numberOfScenarios' expected.");
							}
							break;
						case 2:
							if(!splitStr[0].equals("numberOfReplicates")){
								throw new IOException("Experiment.Setup (line:"+lineCount+") contains unexpected input on 6th uncommented line: '"+splitStr[0]+"' found when 'numberOfReplicates' expected.");
							}
							expSetup.addParameter(new Parameter("numberOfReplicates",splitStr[1],expSetup));
							break;
						case 10:
							if(!splitStr[0].equals("ClassName")){
								throw new IOException("Experiment.Setup (line:"+lineCount+") contains unexpected input in variable declaration: '"+splitStr[0]+"' found when 'ClassName' expected.");
							}
							expParam = new ExperimentParameter();
							expParam.setClassName(splitStr[1]);
							break;
						case 11:
							if(!splitStr[0].equals("InstanceName")){
								throw new IOException("Experiment.Setup (line:"+lineCount+") contains unexpected input in variable declaration: '"+splitStr[0]+"' found when 'InstanceName' expected.");
							}
							expParam.setInstanceName(splitStr[1]);
							break;
						case 12:
							if(!splitStr[0].equals("ParamName")){
								throw new IOException("Experiment.Setup (line:"+lineCount+") contains unexpected input in variable declaration: '"+splitStr[0]+"' found when 'ParamName' expected.");
							}
							expParam.setParamName(splitStr[1]);
							this.exps.add(expParam.getParamKey());
							break;
						case 13:
							if(!splitStr[0].equals("ValueType")){
								throw new IOException("Experiment.Setup (line:"+lineCount+") contains unexpected input in variable declaration: '"+splitStr[0]+"' found when 'ValueType' expected.");
							}
							expParam.setValueType(splitStr[1]);
							break;
						case 14:
							if(splitStr[0].trim().equals("")){
								beginDataInt = 10;
								numValues = 0;
								continue;
							}else if(!splitStr[0].equals("Value")){
								throw new IOException("Experiment.Setup (line:"+lineCount+") contains unexpected input in variable declaration: '"+splitStr[0]+"' found when 'Value' expected.");
							}
							if(numValues==0){
								if(this.expParams.containsKey(expParam.getParamKey())){
									throw new IOException("Experiment.Setup (line:"+lineCount+") contains a duplicate of the combination of experiment parameter <br>" +
											"name and instance name \""+expParam.getParamKey()+"\", the GUI will only accept a single combination, <br>" +
											"please remove redundant entries in Experiment.Setup.");
								}
								this.expParams.put(expParam.getParamKey(),expParam);
							}
							expParam.addValue(splitStr[1]);
							numValues++;
							beginDataInt--;
							break;
						}
					}catch(IllegalParameterException e){
						if(splitStr.length<2){
							this.illegals.add(new String[] {splitStr[0],"","Experiment.Setup",e.getMessage()});
						}else{
							this.illegals.add(new String[] {splitStr[0],splitStr[1],"Experiment.Setup",e.getMessage()});
						}
						e.printStackTrace();
					}catch(IOException e){
						throw e;
					}
					beginDataInt++;
				}else{
					k++;
				}
			}
			setNumberOfScenarios();
			this.setupParams.put("expSetup-", expSetup);
			in.close();
		} catch (IOException e){
			fstream.close();
			throw e;
		} catch (Exception e) {//Catch exception if any
			fstream.close();
			e.printStackTrace();
		}
		if(MetaProject.getInstance().getVersion().equals("insalmo")){
			if(this.exps.contains("shuffleYearSeed (ALL)")){
				this.removeExperimentParameter("shuffleYearSeed (ALL)");
			}
			if(this.exps.contains("shuffleYears (ALL)")){
				this.removeExperimentParameter("shuffleYears (ALL)");
			}
			this.setupParams.get("modSetup-").removeParameter("shuffleYears");
			this.setupParams.get("modSetup-").removeParameter("shuffleYearReplace");
			this.setupParams.get("modSetup-").removeParameter("shuffleYearSeed");
		}else{
			// Now post-process and adjust values if the year shuffler is included in this experiment setup
			if(this.exps.contains("shuffleYearSeed (ALL)")){
				collapseYearShuffler(true);
			}else{
				this.setupParams.get("expSetup-").addParameter(new Parameter("numberOfYearShufflerReplicates","0",this.setupParams.get("expSetup-")));
				this.setupParams.get("expSetup-").addParameter(new Parameter("firstShuffleYearSeed","1",this.setupParams.get("expSetup-")));
			}
		}
		this.updateExperimentParamValidations();
		
		checkForMissing();
	}
	private void resolveParamFilenameConflicts() {
		ArrayList<String> usedFilenames = new ArrayList<String>();
		for(String fish : this.fish){
			String currentFilename = this.getSetupParameters("speParam-"+fish).getFileName();
			if(usedFilenames.contains(currentFilename)){
				String newFilename = getFilenameWithoutExtension(currentFilename)+"-copy.Params";
				File newFile = new File(this.projectDir+"/"+newFilename);
				while(newFile.exists()){
					newFilename = getFilenameWithoutExtension(newFilename)+"-copy.Params";
					newFile = new File(this.projectDir+"/"+newFilename);
				}
				this.getSetupParameters("speParam-"+fish).setFileName(newFilename);
				usedFilenames.add(newFilename);
			}else{
				usedFilenames.add(currentFilename);
			}
		}
		usedFilenames.clear();
		for(String reach : this.habs){
			String currentFilename = this.getSetupParameters("habParam-"+reach).getFileName();
			if(usedFilenames.contains(currentFilename)){
				String newFilename = getFilenameWithoutExtension(currentFilename)+"-copy.Params";
				File newFile = new File(this.projectDir+"/"+newFilename);
				while(newFile.exists()){
					newFilename = getFilenameWithoutExtension(newFilename)+"-copy.Params";
					newFile = new File(this.projectDir+"/"+newFilename);
				}
				this.getSetupParameters("habParam-"+reach).setFileName(newFilename);
				usedFilenames.add(newFilename);
			}else{
				usedFilenames.add(currentFilename);
			}
		}
	}
	private String getFilenameWithoutExtension(String name){
		int index = name.lastIndexOf('.');
		if (index>0&& index <= name.length() - 2 ) {
			return name.substring(0, index);
		}else{
			return name;
		}
	}
	public void checkForMissing(){
		Enumeration<SetupParameters> enumer = this.setupParams.elements(); 
		while(enumer.hasMoreElements()){
			SetupParameters setupParam = (SetupParameters) enumer.nextElement();
			if(setupParam.isMissingParams()){
				ArrayList<String[]> missingP = setupParam.getMissingParams();
				this.missing.addAll(missingP);
				for(String[] miss : missingP){
					setupParam.addParameter(new Parameter(miss[0],MetaProject.getInstance().getMetaParameter(miss[0]).getDefaultValue(),setupParam));
				}
			}
		}
	}
	Integer getNumberOfScenarios(){
		if(numberOfScenarios==null)setNumberOfScenarios(null);
		return numberOfScenarios;
	}
	void setNumberOfScenarios(){
		setNumberOfScenarios(null);
	}
	void setNumberOfScenarios(Integer numScen){
		if(numScen == null){
			if(exps.size()==0){
				numScen = 1;
			}else{
				numScen = Integer.MAX_VALUE;
				for(String exp : exps){
					if(expParams.get(exp).getValues().size() < numScen) numScen = expParams.get(exp).getValues().size();
				}
			}
		}
		numberOfScenarios = numScen;
	}
	public void collapseYearShuffler(Boolean onFileRead){
		ArrayList<Parameter> shuffleValueParameters = this.expParams.get("shuffleYearSeed (ALL)").getValues();
		ArrayList<Integer> shuffleSeeds       = new ArrayList<Integer>(shuffleValueParameters.size());
		ArrayList<Integer> uniqueShuffleSeeds = new ArrayList<Integer>(shuffleValueParameters.size());
		ArrayList<Integer> uniqueYearIndependentIndices   = new ArrayList<Integer>(shuffleValueParameters.size());
		Integer minSeed = Integer.MAX_VALUE;
		// Find the unique shuffle seeds as well as the smallest seed value (assumed to be the starting point for the shuffle)
		for(Parameter p : shuffleValueParameters){
			Integer val =  p.getParameterIntegerValue();
			shuffleSeeds.add(val);
			if(!uniqueShuffleSeeds.contains(val)){
				uniqueShuffleSeeds.add(val);
				if(val<minSeed)minSeed=val;
			}
		}
		// Collapse the number of scenarios by the number of unique year shuffler seeds there are
		Integer newNumScenarios = new Double(getNumberOfScenarios().doubleValue() / uniqueShuffleSeeds.size()).intValue();
		setNumberOfScenarios(newNumScenarios);
		if(onFileRead){
			// Set year shuffler params in expSetup
			this.setupParams.get("expSetup-").addParameter(new Parameter("numberOfYearShufflerReplicates",(new Integer(uniqueShuffleSeeds.size()).toString()),this.setupParams.get("expSetup-")));
			this.setupParams.get("expSetup-").addParameter(new Parameter("firstShuffleYearSeed",minSeed.toString(),this.setupParams.get("expSetup-")));
		}else{
			this.setupParams.get("expSetup-").getParameter("numberOfYearShufflerReplicates").setParameterValue(new Integer(uniqueShuffleSeeds.size()).toString());
			this.setupParams.get("expSetup-").getParameter("firstShuffleYearSeed").setParameterValue(minSeed.toString());
		}
		// Find the row indices were the minSeed occurs in order to collapse all of the value lists
		for (int i = 0; i < shuffleSeeds.size(); i++) {
			if(shuffleSeeds.get(i).intValue() == minSeed.intValue()){
				uniqueYearIndependentIndices.add(i);
			}
		}
		// Collapse all of the value lists for each experiment parameter except shuffleYearSeed and shuffleYears which are removed
		this.expParams.remove("shuffleYears (ALL)");
		this.exps.remove("shuffleYears (ALL)");
		this.expParams.remove("shuffleYearSeed (ALL)");
		this.exps.remove("shuffleYearSeed (ALL)");
		Enumeration enumer = this.expParams.elements(); 
		while(enumer.hasMoreElements()){
			ExperimentParameter expParam1 = (ExperimentParameter) enumer.nextElement();
			ArrayList<String> newVals = new ArrayList<String>(uniqueShuffleSeeds.size());
			for(Integer i : uniqueYearIndependentIndices){
				newVals.add(expParam1.getValues().get(i).getParameterValue());
			}
			expParam1.clearValues();
			for (String newVal: newVals) {
				expParam1.addValue(newVal);
			}
		}
		this.updateExperimentParamValidations();
	}
	public void expandYearShuffler(){
		Integer firstSeed = this.setupParams.get("expSetup-").getParameter("firstShuffleYearSeed").getParameterIntegerValue();
		Integer numReplicates = this.setupParams.get("expSetup-").getParameter("numberOfYearShufflerReplicates").getParameterIntegerValue();

		// We need to add 2 parameters to the experiment data structure, shuffleYearSeed and shuffleYears
		ExperimentParameter shuffSeedExpParam = new ExperimentParameter();
		shuffSeedExpParam.setClassName("TroutModelSwarm");
		shuffSeedExpParam.setInstanceName("NONE");
		shuffSeedExpParam.setParamName("shuffleYearSeed");
		shuffSeedExpParam.setValueType("int");
		
		ExperimentParameter shuffExpParam = new ExperimentParameter();
		shuffExpParam.setClassName("TroutModelSwarm");
		shuffExpParam.setInstanceName("NONE");
		shuffExpParam.setParamName("shuffleYears");
		shuffExpParam.setValueType("BOOL");
		
		this.expParams.put(shuffSeedExpParam.getParamKey(), shuffSeedExpParam);
		this.exps.add(shuffSeedExpParam.getParamKey());
		this.expParams.put(shuffExpParam.getParamKey(), shuffExpParam);
		this.exps.add(shuffExpParam.getParamKey());
		for(int j=0; j<getNumberOfScenarios(); j++){
			shuffSeedExpParam.addValue(firstSeed.toString());
			shuffExpParam.addValue("1");
		}
		for(int i=1; i<numReplicates; i++){
			Integer shuffleSeed = firstSeed+i;
			Enumeration enumer = this.expParams.elements(); 
			while(enumer.hasMoreElements()){
				ExperimentParameter expParam = (ExperimentParameter) enumer.nextElement();
				if(expParam.getParamKey().equals("shuffleYearSeed (ALL)")){
					for(int j=0; j<getNumberOfScenarios(); j++){
						expParam.addValue(new Integer(i+firstSeed).toString());
					}
				}else{
					for(int j=0; j<getNumberOfScenarios(); j++){
						expParam.addValue(expParam.getValues().get(j).getParameterValue());
					}
				}
			}
		}
	}
	public void loadHabitatParams(SetupParameters sParams, String filePath) throws RuntimeException,IOException{
		SetupParameters habParams = new SetupParameters("habParam",sParams.getParameter("habParamFile").getParameterValue());
		habParams.setParamInstance(sParams.getParameter("reachName").getParameterValue());
		try {
			File f = new File(filePath);
			FileInputStream fstream = new FileInputStream(filePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			boolean beginData = false;
			int lineCount = 0;
			while ((strLine = br.readLine())!=null && !strLine.trim().equals("@end")) {
				lineCount++;
				if (strLine.trim().equals("@begin")) {
					beginData = true;
				} else if (beginData && strLine.length()>0 && !strLine.substring(0, 1).equals("#")) {
					if(strLine.trim().equals(""))continue;
					String[] splitStr = strLine.trim().split("\\s+");
					if(splitStr.length==1)throw new IOException(f.getName() + " (line "+lineCount+") -- expecting a variable name and value with space between, instead found '"+splitStr[0]+"'");
					try{
						habParams.addParameter(new Parameter(splitStr[0],splitStr[1],habParams));
					}catch(IllegalParameterException e){
						this.illegals.add(new String[] {splitStr[0],splitStr[1],sParams.getParameter("habParamFile").getParameterValue(),e.getMessage()});
						e.printStackTrace();
					}
				}
			}
			in.close();
			if(!beginData)throw new IOException(f.getName()+" -- token '@begin' never found.");
		} catch (IOException e) {//Catch exception if any
			throw e;
		} catch (RuntimeException e) {//Catch exception if any
			throw e;
		} catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}	
		this.setupParams.put("habParam-"+sParams.getParameter("reachName").getParameterValue(),habParams);
		habParams.setFileName(sParams.getParameter("habParamFile").getParameterValue());
		sParams.removeParameter("habParamFile");
	}

	public void loadSpeciesParams(SetupParameters sParams, String filePath) throws RuntimeException,IOException{
		SetupParameters speParams = new SetupParameters("speParam",sParams.getParameter("speciesParamFile").getParameterValue());
		speParams.setParamInstance(sParams.getParameter("speciesName").getParameterValue());
		try {
			File f = new File(filePath);
			FileInputStream fstream = new FileInputStream(filePath);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			boolean beginData = false;
			int lineCount = 0;
			while ((strLine = br.readLine())!=null && !strLine.trim().equals("@end")) {
				lineCount++;
				if (strLine.trim().equals("@begin")) {
					beginData = true;
				} else if (beginData && strLine.length()>0 && !strLine.substring(0, 1).equals("#")) {
					if(strLine.trim().equals(""))continue;
					String[] splitStr = strLine.trim().split("\\s+");
					if(splitStr.length==1)throw new IOException(f.getName() + " (line "+lineCount+") -- expecting a variable name and value with space between, instead found '"+splitStr[0]+"'");
					try{
						speParams.addParameter(new Parameter(splitStr[0],splitStr[1],speParams));
					}catch(IllegalParameterException e){
						this.illegals.add(new String[] {splitStr[0],splitStr[1],sParams.getParameter("speciesParamFile").getParameterValue(),e.getMessage()});
						e.printStackTrace();
					}
				}
			}
			in.close();
			if(!beginData)throw new IOException(f.getName()+" -- token '@begin' never found.");
		} catch (IOException e) {//Catch exception if any
			throw e;
		} catch (RuntimeException e) {//Catch exception if any
			throw e;
		} catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}	
		this.setupParams.put("speParam-"+sParams.getParameter("speciesName").getParameterValue(),speParams);
		speParams.setFileName(sParams.getParameter("speciesParamFile").getParameterValue());
		sParams.removeParameter("speciesParamFile");
		// the following is before I allowed the .Params filename to be specified from inside Species.Setup
		//		sParams.removeParameter("speciesParamFile");
		//		speParams.setFileName(sParams.getParamInstance()+".Params");
	}
	
	public void changeSpeciesParameterFile(String speciesName, File newParameterFile) throws Exception{
		SetupParameters speSetup = setupParams.get("speSetup-"+speciesName);
		SetupParameters prevSpeParam = setupParams.get("speParam-"+speciesName);
		speSetup.addParameter(new Parameter("speciesParamFile",newParameterFile.getName(),speSetup));
		setupParams.remove("speParam-"+speciesName);
		try{
			this.loadSpeciesParams(speSetup,newParameterFile.getAbsolutePath());
		}catch (Exception e){
			speSetup.removeParameter("speciesParamFile");
			setupParams.put("speParam-"+speciesName,prevSpeParam);
			throw e;
		}
		for(Parameter param : prevSpeParam.getParameterCollection()){
			this.removeFromErrorsWarnings(param);
		}
	}

	public void addNewSpecies(String existingSpeciesName,String newSpeciesName)throws IOException,RuntimeException{
		if(existingSpeciesName==null){
			SetupParameters speSetup =  new SetupParameters("speSetup","Species.Setup");
			speSetup.setParamInstance(newSpeciesName);
			try{
				speSetup.addParameter(new Parameter("speciesName",newSpeciesName,speSetup));
				speSetup.addParameter(new Parameter("speciesColor",MetaProject.getInstance().getMetaParameter("speciesColor").getDefaultValue(),speSetup));

				// For Init Pop File, we use the default
				String defaultFileName = MetaProject.getInstance().getMetaParameter("speciesInitPopFile").getDefaultValue();
				// Look for and copy the default version of the infile to the project directory, do not overwrite
				File defaultFile = new File(MetaProject.getInstance().getApplicationDir()+"/DefaultProject/"+MetaProject.getInstance().getVersion()+"/"+defaultFileName);
				if(defaultFile.exists()){
					File destinationFile = new File(projectDir+"/"+defaultFileName);
					if(!destinationFile.exists()) MetaProject.getInstance().copy(defaultFile, destinationFile);
				}else{
					System.out.println("Warning: Cannot locate file "+defaultFile.getAbsolutePath());
				}
				speSetup.addParameter(new Parameter("speciesInitPopFile",defaultFileName,speSetup));
			}catch(IllegalParameterException e){
				e.printStackTrace();
			}
			SetupParameters speParams = new SetupParameters("speParam",newSpeciesName+".Params");
			speParams.setParamInstance(speSetup.getParameter("speciesName").getParameterValue());
			// Initialize the fish with default values
			for(String speParamName : MetaProject.getInstance().getParameterNameListFromType("speParam")){
				Parameter newParam = new Parameter(speParamName,MetaProject.getInstance().getMetaParameter(speParamName).getDefaultValue(),speParams);
				speParams.addParameter(newParam);
			}
			speParams.setFileName(speSetup.getParamInstance()+".Params");
			this.addSpecies(speSetup, speParams);
		}else{
			SetupParameters existingSpeSetup = getSetupParameters("speSetup-"+existingSpeciesName);
			String existingSpeParamsFilename = getSetupParameters("speParam-"+existingSpeciesName).getFileName();
			SetupParameters speSetup = new SetupParameters("speSetup","Species.Setup");
			speSetup.setParamInstance(newSpeciesName);
			speSetup.addParameter(new Parameter("speciesName",newSpeciesName,speSetup));
			speSetup.addParameter(new Parameter("speciesInitPopFile",existingSpeSetup.getParameter("speciesInitPopFile").getParameterValue(),speSetup));
			speSetup.addParameter(new Parameter("speciesColor",existingSpeSetup.getParameter("speciesColor").getParameterValue(),speSetup));
			SetupParameters existingSpeParams= getSetupParameters("speParam-"+existingSpeciesName);
			SetupParameters speParams = new SetupParameters("speParam",newSpeciesName+".Params");
			speParams.setParamInstance(speSetup.getParameter("speciesName").getParameterValue());
			for(String speParamParamName : MetaProject.getInstance().getParameterNameListFromType("speParam")){
				speParams.addParameter(new Parameter(speParamParamName,existingSpeParams.getParameter(speParamParamName).getParameterValue(),speParams));
			}
			this.addSpecies(speSetup, speParams);
		}
	}

	public void addNewReach(String existingReachName,String newReachName) throws IOException,RuntimeException{
		if(existingReachName==null){
			SetupParameters habSetup =  new SetupParameters("habSetup","Reach.Setup");
			habSetup.setParamInstance(newReachName);
			for(String habSetupParamName : MetaProject.getInstance().getParameterNameListFromType("habSetup")){
				if(!habSetupParamName.equals("habParamFile")){ 
					Parameter newParam = new Parameter(habSetupParamName,MetaProject.getInstance().getMetaParameter(habSetupParamName).getDefaultValue(),habSetup);
					habSetup.addParameter(newParam);
					if(newParam.getParameterDataType() == MetaParameter.DataType.INFILENAME){
						// Look for and copy the default version of the infile to the project directory, do not overwrite
						String defaultFileName = MetaProject.getInstance().getMetaParameter(newParam.getParameterName()).getDefaultValue();
						File defaultFile = new File(MetaProject.getInstance().getApplicationDir()+"/DefaultProject/"+MetaProject.getInstance().getVersion()+"/"+defaultFileName);
						if(defaultFile.exists()){
							File destinationFile = new File(projectDir+"/"+defaultFileName);
							if(!destinationFile.exists()) MetaProject.getInstance().copy(defaultFile, destinationFile);
						}else{
							System.out.println("Warning: Cannot locate file "+defaultFile.getAbsolutePath());
						}
					}
					newParam.updateValidationCode();
				}
			}
			habSetup.getParameter("reachName").setParameterValue(newReachName);

			SetupParameters habParams = new SetupParameters("habParam",newReachName+".Params");
			habParams.setParamInstance(habSetup.getParameter("reachName").getParameterValue());
			for(String habParamName : MetaProject.getInstance().getParameterNameListFromType("habParam")){
				habParams.addParameter(new Parameter(habParamName,MetaProject.getInstance().getMetaParameter(habParamName).getDefaultValue(),habSetup));
			}
			this.addHabitat(habSetup, habParams);
		}else{
			SetupParameters existingSetupParams = getSetupParameters("habSetup-"+existingReachName);
			SetupParameters habSetup =  new SetupParameters("habSetup","Reach.Setup");
			habSetup.setParamInstance(newReachName);
			habSetup.setFileName(existingSetupParams.getFileName());
			for(String habSetupParamName : MetaProject.getInstance().getParameterNameListFromType("habSetup")){
				if(!habSetupParamName.equals("habParamFile")){ 
					Parameter newParam = new Parameter(habSetupParamName,existingSetupParams.getParameter(habSetupParamName).getParameterValue(),habSetup);
					habSetup.addParameter(newParam);
				}
			}
			habSetup.getParameter("reachName").setParameterValue(newReachName);
			SetupParameters existingHabParam = getSetupParameters("habParam-"+existingReachName);
			SetupParameters habParam = new SetupParameters("habParam",newReachName+".Params");
			habParam.setParamInstance(habSetup.getParameter("reachName").getParameterValue());
			for(String habParamParamName : MetaProject.getInstance().getParameterNameListFromType("habParam")){
				habParam.addParameter(new Parameter(habParamParamName,existingHabParam.getParameter(habParamParamName).getParameterValue(),habParam));
			}
			this.addHabitat(habSetup, habParam);
		}
	}

	public void save(){
		if(MetaProject.getInstance().isInsalmo()){
			SetupParameters modSetup = this.setupParams.get("modSetup-");
			modSetup.addParameter(new Parameter("shuffleYears","0",modSetup));
			modSetup.addParameter(new Parameter("shuffleYearReplace","1",modSetup));
			modSetup.addParameter(new Parameter("shuffleYearSeed","0",modSetup));
		}
		int j=1;
		try {
			for(int i=0; i<this.beginEndFiles.length; i++){
				SetupParameters sParams =  this.setupParams.get(this.beginEndFiles[i][0]+"-");
				FileOutputStream fstream = new FileOutputStream(projectDir.getPath()+"/"+sParams.getFileName());
				DataOutputStream out = new DataOutputStream(fstream);
				BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out));
				br.write("# This config file automatically generated by "+MetaProject.getInstance().getAppTitle()+" GUI"+newline+newline);
				br.write("@begin"+newline);
				if(this.beginEndFiles[i][0].equals("modSetup") && !MetaProject.getInstance().isInstream()){
					br.write("numberOfSpecies\t\t"+this.fish.size()+newline);
				}
				for (Parameter param : sParams.getParameterCollection()) {
					br.write(param.getParameterName()+"\t\t"+param.getParameterValue()+newline);
				}
				br.write("@end"+newline);
				br.flush();
				out.close();
			}
			for (String speName : this.fish) {
				SetupParameters sParams = this.setupParams.get("speParam-"+speName);
				FileOutputStream fstream = new FileOutputStream(projectDir.getPath()+"/"+sParams.getFileName());
				DataOutputStream out = new DataOutputStream(fstream);
				BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out));
				br.write("# This config file automatically generated by "+MetaProject.getInstance().getAppTitle()+" GUI"+newline+newline);
				br.write("@begin"+newline);
				for (Parameter param : sParams.getParameterCollection()) {
					br.write(param.getParameterName()+"\t\t"+param.getParameterValue()+newline);
				}
				br.write("@end"+newline);
				br.flush();
				out.close();
			}
		} catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}	
		try {
			FileOutputStream fstream = new FileOutputStream(projectDir.getPath()+"/Reach.Setup");
			DataOutputStream out = new DataOutputStream(fstream);
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out));
			br.write("Reach.Setup file. Automatically generated by "+MetaProject.getInstance().getAppTitle()+" GUI"+newline+newline+newline);
			for (String habName : this.habs) {
				br.write("REACHBEGIN"+newline+"habParamFile\t\t"+this.setupParams.get("habParam-"+habName).getFileName()+newline);
				SetupParameters sParams =  this.setupParams.get("habSetup-"+habName);
				for (Parameter param : sParams.getParameterCollection()) {
					br.write(param.getParameterName()+"\t\t"+param.getParameterValue()+newline);
				}
				br.write("REACHEND"+newline+newline);
			}
			br.flush();
			out.close();
			for (String habName : this.habs) {
				SetupParameters sParams = this.setupParams.get("habParam-"+habName);
				fstream = new FileOutputStream(projectDir.getPath()+"/"+sParams.getFileName());
				out = new DataOutputStream(fstream);
				br = new BufferedWriter(new OutputStreamWriter(out));
				br.write("# This config file automatically generated by "+MetaProject.getInstance().getAppTitle()+" GUI"+newline+newline);
				br.write("@begin"+newline);
				for (Parameter param : sParams.getParameterCollection()) {
					br.write(param.getParameterName()+"\t\t"+param.getParameterValue()+newline);
				}
				br.write("@end"+newline);
				br.flush();
				out.close();
			}

		} catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}	
		try {
			FileOutputStream fstream = new FileOutputStream(projectDir.getPath()+"/Species.Setup");
			DataOutputStream out = new DataOutputStream(fstream);
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out));
			br.write("Species.Setup file, Automatically generated by "+MetaProject.getInstance().getAppTitle()+" GUI."+newline+
					"For each species, provide species class name, parameter file name,"+newline+"" +
							"initial population file name, and raster display color."+newline+newline);
			for (String speName : this.fish) {
				SetupParameters sParams =  this.setupParams.get("speSetup-"+speName);
				br.write(speName+newline);
				br.write(this.setupParams.get("speParam-"+speName).getFileName()+newline);
				br.write(sParams.getParameter("speciesInitPopFile").getParameterValue()+newline);
				br.write(sParams.getParameter("speciesColor").getParameterValue()+newline);
				if(MetaProject.getInstance().isInstreamSD())br.write(sParams.getParameter("speciesStockingFile").getParameterValue()+newline);
				br.write(newline);
			}
			br.flush();
			out.close();
		} catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}	
		if(!MetaProject.getInstance().isInsalmo()){
			if(this.setupParams.get("expSetup-").getParameter("numberOfYearShufflerReplicates").getParameterIntegerValue()>0){
				expandYearShuffler();
				setNumberOfScenarios(getNumberOfScenarios() * this.setupParams.get("expSetup-").getParameter("numberOfYearShufflerReplicates").getParameterIntegerValue());
			}
		}
		try {
			FileOutputStream fstream = new FileOutputStream(projectDir.getPath()+"/Experiment.Setup");
			DataOutputStream out = new DataOutputStream(fstream);
			BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out));
			br.write("Experiment setup file, Automatically generated by "+MetaProject.getInstance().getAppTitle()+" GUI."+
					newline+"The first 3 lines of this file are for header info and are"+newline+"ignored by "+MetaProject.getInstance().getAppTitle()+", the next line must be blank."+newline+newline);
			br.write("numberOfScenarios\t\t"+getNumberOfScenarios().toString()+newline);
			br.write("numberOfReplicates\t\t"+this.setupParams.get("expSetup-").getParameter("numberOfReplicates").getParameterValue()+newline+newline);
			br.write("sendScenarioCountToParam:\t\tscenario"+newline+"inClass:\t\tTroutModelSwarm"+newline+newline+
					"sendReplicateCountToParam:\t\treplicate"+newline+"inClass:\t\tTroutModelSwarm"+newline+newline);

			for (String expParamKey : this.exps) {
				ExperimentParameter expParam =  this.expParams.get(expParamKey);
				br.write("ClassName\t\t"+expParam.getClassName()+newline);
				br.write("InstanceName\t\t"+expParam.getInstanceName()+newline);
				br.write("ParamName\t\t"+expParam.getParamName()+newline);
				br.write("ValueType\t\t"+expParam.getValueType()+newline);
				for(Parameter p : expParam.getValues()){
					br.write("Value\t\t"+p.getParameterValue()+newline);
				}
				br.write(newline);
			}
			br.flush();
			out.close();
		} catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}	
		if(!MetaProject.getInstance().isInsalmo()){
			if(Integer.parseInt(this.setupParams.get("expSetup-").getParameter("numberOfYearShufflerReplicates").getParameterValue())>0){
				collapseYearShuffler(false);
			}
		}else{
			this.setupParams.get("modSetup-").removeParameter("shuffleYears");
			this.setupParams.get("modSetup-").removeParameter("shuffleYearReplace");
			this.setupParams.get("modSetup-").removeParameter("shuffleYearSeed");
		}
	}
	public void updateErrorsWarnings(Parameter param){
		if(param.getValidationType()==MetaParameter.ValidationType.VALID){
			if(this.errors.contains(param)){
				this.errors.remove(param);
			}else if(this.warnings.contains(param)){
				this.warnings.remove(param);
			}
		}else if(param.getValidationType()==MetaParameter.ValidationType.ERROR){
			if(this.warnings.contains(param)){
				this.warnings.remove(param);
			}
			if(!this.errors.contains(param))this.errors.add(param);
		}else if(param.getValidationType()==MetaParameter.ValidationType.WARNING){
			if(this.errors.contains(param)){
				this.errors.remove(param);
			}
			if(!this.warnings.contains(param))this.warnings.add(param);
		}
		this.errorWarningUpdateListener.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
	}
	public void removeFromErrorsWarnings(Parameter param){
		if(this.errors.contains(param)){
			this.errors.remove(param);
			this.errorWarningUpdateListener.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
		}
		if(this.warnings.contains(param)){
			this.warnings.remove(param);
			this.errorWarningUpdateListener.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
		}
	}
	public void addActionListener(java.awt.event.ActionListener actionListener){
		this.errorWarningUpdateListener = actionListener;
	}
	public Integer getNumErrors(){
		return this.errors.size();
	}
	public ArrayList<Parameter> getErrors(){
		return this.errors;
	}
	public ArrayList<Parameter> getWarnings(){
		return this.warnings;
	}
	public Integer getNumWarnings(){
		return this.warnings.size();
	}
	public ArrayList<String> getHabs() {
		return habs;
	}
	public ArrayList<String> getFish() {
		return fish;
	}
	public ArrayList<String> getExperimentParamKeys() {
		return exps;
	}
	public ExperimentParameter getExperimentParameters(String expParamKey){
		return this.expParams.get(expParamKey);
	}
	public void addExperimentParameter(ExperimentParameter expParam){
		this.expParams.put(expParam.getParamKey(), expParam);
		this.exps.add(expParam.getParamKey());
		this.setNumberOfScenarios();
	}
	public void addHabitat(SetupParameters habSetup, SetupParameters habParams){
		this.habs.add(habSetup.getParameter("reachName").getParameterValue());
		this.setupParams.put("habSetup-"+habSetup.getParamInstance(), habSetup);
		this.setupParams.put("habParam-"+habSetup.getParameter("reachName").getParameterValue(),habParams);
	}
	public void addSpecies(SetupParameters speSetup, SetupParameters speParams){
		this.fish.add(speSetup.getParameter("speciesName").getParameterValue());
		this.setupParams.put("speSetup-"+speSetup.getParamInstance(), speSetup);
		this.setupParams.put("speParam-"+speSetup.getParameter("speciesName").getParameterValue(),speParams);
	}
	public void removeExperimentParameter(String paramKey){
		if(this.expParams.containsKey(paramKey)){
			for(Parameter param : this.expParams.get(paramKey).getParameterArrayList()){
				this.removeFromErrorsWarnings(param);
			}
			this.expParams.remove(paramKey);
			this.exps.remove(paramKey);
			this.setNumberOfScenarios();
		}
	}
	public void removeHabitat(String habitatName){
		this.setupParams.remove("habSetup-"+habitatName);
		this.setupParams.remove("habParam-"+habitatName);
		this.habs.remove(habitatName);
	}
	public void removeSpecies(String speciesName){
		this.setupParams.remove("speSetup-"+speciesName);
		this.setupParams.remove("speParam-"+speciesName);
		this.fish.remove(speciesName);
	}
	public SetupParameters getSetupParameters(String setupName){
		return setupParams.get(setupName);
	}
	public ArrayList<String> getAllInfileNames(){
		ArrayList<String> infileNames = new ArrayList<String>();
		Enumeration<String> setupParamNames = setupParams.keys();
		while(setupParamNames.hasMoreElements()){
			String name = setupParamNames.nextElement();
			SetupParameters sParams = getSetupParameters(name);
			ArrayList<Parameter> params = new ArrayList(sParams.getParameterCollection());
			for(Parameter param : params){
				if(param.getParameterDataType() == MetaParameter.DataType.INFILENAME){
					infileNames.add(param.getParameterValue());
				}
			}
		}
		return infileNames;
	}
	public void updateExperimentParamValidations(){
		for(String exp : this.exps){
			this.expParams.get(exp).updateParameterValueValidations();
		}
	}
	public void setProjectDir(File dir){
		this.projectDir = dir;
	}
	public Boolean isMissingParameters(){
		return this.missing.size()>0;
	}
	public Boolean containsIllegalParameters(){
		return this.illegals.size()>0;
	}
	public ArrayList<String[]> getMissingParameters(){
		return this.missing;
	}
	public ArrayList<String[]> getIllegalParameters(){
		return this.illegals;
	}
	public Boolean hasProjectChanged() {
		return projectChanged;
	}
	public void setProjectChanged(Boolean projectChanged) {
		this.projectChanged = projectChanged;
	}
	public File getProjectDirectory(){
		return this.projectDir;
	}
}
