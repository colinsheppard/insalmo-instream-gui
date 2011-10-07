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
package insalmo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;

public class SetupParameters{
	String paramType;
	String paramInstance;
	String fileName;
	Hashtable<String,Parameter> params = new Hashtable<String,Parameter>();
	ArrayList<String[]>	missingParams	= new ArrayList<String[]>();
	
	public SetupParameters(String paramType,String fileName){
		this.paramType = paramType;
		this.fileName = fileName;
	}
	private void checkMissing(){
		this.missingParams.clear();
		ArrayList<String> nameList = MetaProject.getInstance().getParameterNameListFromType(paramType);
		for (String paramName : nameList) {
			if(!paramName.equals("habParamFile") 
					&& !paramName.equals("speciesParamFile")
					&& !paramName.equals("shuffleYearSeed")
					&& !paramName.equals("numberOfSpecies")
					&& !this.params.containsKey(paramName)){
				if(MetaProject.getInstance().getVersion().equals("insalmo") &&
						(paramName.equals("numberOfYearShufflerReplicates") ||
						 paramName.equals("firstShuffleYearSeed")	||
						 paramName.equals("shuffleYears")	||
						 paramName.equals("shuffleYearReplace")	||
						 paramName.equals("shuffleYearSeed"))){
					//do nothing
				}else{
					this.missingParams.add(new String[] {paramName,this.fileName});	
				}
			}
		}
	}
	public void addParameter(Parameter param){
		this.params.put(param.getParameterName(), param);
	}
	public void removeParameter(String paramName){
		this.params.remove(paramName);
	}
	public Parameter getParameter(String paramName){
		return this.params.get(paramName);
	}
	public Collection<Parameter> getParameterCollection(){
		return this.params.values();
	}
	public Collection<Parameter> getSortedParameterCollection(){
		ArrayList<Parameter> paramList = new ArrayList<Parameter>();
		paramList.addAll(this.params.values());
		Collections.sort(paramList, new byDisplayOrder());
		return paramList;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public boolean isMissingParams(){
		this.checkMissing();
		return(this.missingParams.size()>0);
	}
	public ArrayList<String[]> getMissingParams(){
		this.checkMissing();
		return this.missingParams;
	}
	public String getParamInstance() {
		return paramInstance;
	}
	public void setParamInstance(String paramInstance) {
		this.paramInstance = paramInstance;
	}
	public String getFileName(){
		return this.fileName;
	}
	public void setFileName(String fname){
		this.fileName = fname;
	}
	public class byDisplayOrder implements java.util.Comparator {
		public int compare(Object boy, Object girl) {
			Integer diff = ((Parameter)boy).getMetaParameter().getDisplayOrder() - ((Parameter)girl).getMetaParameter().getDisplayOrder();
			if(diff<0)return -1;
			if(diff>0)return 1;
			return (((Parameter)boy).getParameterName().compareTo(((Parameter)girl).getParameterName()));
		}
	} 
	public class byVariableName implements java.util.Comparator {
		public int compare(Object boy, Object girl) {
			return (((Parameter)boy).getParameterName().compareTo(((Parameter)girl).getParameterName()));
		}
	} 
}
