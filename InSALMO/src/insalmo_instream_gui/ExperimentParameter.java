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

import java.util.ArrayList;

public class ExperimentParameter {

	Parameter classNameParam = null;
	Parameter instanceNameParam = null;
	Parameter paramNameParam = null;
	Parameter valueTypeParam = null;
	ArrayList<Parameter> values = new ArrayList<Parameter>();
	ArrayList<Parameter> allParameters = null;
	
	public ExperimentParameter() {
	}
	public void addValue(String val){
		if(paramNameParam==null){
			System.err.println("Error: Attempted to add a value to an experiment parameter before the parameter name has been specified");
			//TODO change all System.exit calls to a catchable exception that allows user to correct error.
			System.exit(1);
		}
		this.getValues().add(new Parameter(this.getParamName(),val,this));
	}
	public ArrayList<Parameter> getParameterArrayList(){
		if(allParameters==null){
			this.allParameters = new ArrayList<Parameter>(this.values.size()+4);
			this.allParameters.add(this.classNameParam);
			this.allParameters.add(this.instanceNameParam);
			this.allParameters.add(this.paramNameParam);
			this.allParameters.add(this.valueTypeParam);
		}else{
			for(Parameter val : values) {
				this.allParameters.remove(val);
			}
		}
		for(Parameter val : values) {
			this.allParameters.add(val);
		}
		return allParameters;
	}
	public String getClassName() {
		return classNameParam.getParameterValue();
	}
	public void setClassName(String className) {
		try{
			this.classNameParam = new Parameter("ClassName",className,this);
		}catch(IllegalParameterException e){
			e.printStackTrace();
		}
	}
	public String getInstanceName() {
		return instanceNameParam.getParameterValue();
	}
	public void setInstanceName(String instanceName) {
		try{
			this.instanceNameParam = new Parameter("InstanceName",instanceName,this);
		}catch(IllegalParameterException e){
			e.printStackTrace();
		}
	}
	public String getParamKey() {
		if(instanceNameParam.getParameterValue().equals("NONE")){
			return paramNameParam.getParameterValue()+" (ALL)";
		}else{
			return paramNameParam.getParameterValue()+" ("+instanceNameParam.getParameterValue()+")";
		}
	}
	public String getParamName() {
		return paramNameParam.getParameterValue();
	}
	public void setParamName(String paramName) {
		try{
			this.paramNameParam = new Parameter("ParamName",paramName,this);
		}catch(IllegalParameterException e){
			e.printStackTrace();
		}
	}
	public String getValueType() {
		return valueTypeParam.getParameterValue();
	}
	public void setValueType(String valueType) {
		try{
			this.valueTypeParam = new Parameter("ValueType",valueType,this);
		}catch(IllegalParameterException e){
			e.printStackTrace();
		}
	}
	public ArrayList<Parameter> getValues() {
		return values;
	}
	public void clearValues(){
		this.values.clear();
	}
	public static String getParamNameFromParamKey(String paramKey){
		return paramKey.split(" ")[0];
	}
	public static String getParamInstanceFromParamKey(String paramKey){
		String[] parts = paramKey.split("\\(");
		String instanceName = parts[1].split("\\)")[0];
		return (instanceName.equals("ALL")) ? "NONE" : instanceName;
	}
	public void updateParameterValueValidations(){
		for(Parameter param : this.values){
			param.updateValidationCode();
		}
	}
	
}
