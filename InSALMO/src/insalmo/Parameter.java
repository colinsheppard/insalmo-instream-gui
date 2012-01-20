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

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author critter
 */
public class Parameter {
	String strValue;
    Double dblValue;
    Integer intValue;
    Boolean boolValue;
    Date dateValue;
    
	MetaParameter metaParam;
    MetaParameter.ValidationCode validationCode;
    MetaParameter.ValidationType validationType;
    String validationMessage;

    SetupParameters setupParamSource;
    ExperimentParameter expParamSource;
    String instance;
    
    public Parameter(String paramName,String value, Object source) throws RuntimeException{
    	this.metaParam = MetaProject.getInstance().getMetaParameter(paramName);
    	if(this.metaParam==null) throw new IllegalParameterException("Parameter name '"+paramName+"' is not a valid parameter");
    	this.setParameterValue(value);
    	if(source.getClass().equals(SetupParameters.class)){
    		this.setupParamSource = (SetupParameters)source;
    	}else if(source.getClass().equals(ExperimentParameter.class)){
    		this.expParamSource = (ExperimentParameter)source;
    	}else{
    		throw new RuntimeException("Argument 'source' passed to Parameter constructor must be of class SetupParameters or ExperimentParameter");
    	}
    }
    public void updateValidationCode(){
    	MetaParameter.ValidationType previousValidationType = this.validationType;
    	this.validationCode = this.metaParam.validateParam(this.strValue);
		switch (validationCode) {
		case UNTESTED:
			this.validationType = MetaParameter.ValidationType.WARNING;
			this.validationMessage = "Value not tested";
			break;
		case VALID:
			this.validationType = MetaParameter.ValidationType.VALID;
			this.validationMessage = "";
			break;
		case CHOOSING:
			this.validationType = MetaParameter.ValidationType.CHOOSING;
			this.validationMessage = "";
			break;
		case RANGE_LOW:
			this.validationType = MetaParameter.ValidationType.WARNING;
			this.validationMessage = "Value below recommended minimum.";
			break;
		case RANGE_HIGH:
			this.validationType = MetaParameter.ValidationType.WARNING;
			this.validationMessage = "Value above recommended maximum.";
			break;
		case LENGTH_LONG:
			this.validationType = MetaParameter.ValidationType.WARNING;
			this.validationMessage = "Too many characters in string or filename (max = "+this.getMetaParameter().getMaxStringLength()+").";
			break;
		case LENGTH_SHORT:
			this.validationType = MetaParameter.ValidationType.WARNING;
			this.validationMessage = "Too few characters in string or filename (min = "+this.getMetaParameter().getMinStringLength()+").";
			break;
		case MISSING_FILE:
			this.validationType = MetaParameter.ValidationType.ERROR;
			this.validationMessage = "File missing from project directory";
			break;
		case ILLEGAL_FILE:
			this.validationType = MetaParameter.ValidationType.ERROR;
			this.validationMessage = "Illegal file selected";
			break;
		case INVALID_FLOAT:
			this.validationType = MetaParameter.ValidationType.ERROR;
			this.validationMessage = "Invalid floating point expression";
			break;
		case INVALID_INTEGER:
			this.validationType = MetaParameter.ValidationType.ERROR;
			this.validationMessage = "Invalid integer expression";
			break;
		case INVALID_DATE:
			this.validationType = MetaParameter.ValidationType.ERROR;
			this.validationMessage = "Invalid date format (must be MM/DD/YYYY)";
			break;
		case INVALID_DAY:
			this.validationType = MetaParameter.ValidationType.ERROR;
			this.validationMessage = "Invalid day format (must be MM/DD)";
			break;
		case INVALID_BOOL:
			this.validationType = MetaParameter.ValidationType.ERROR;
			this.validationMessage = "Invalid boolean format (must be YES or NO)";
			break;
		}
		if(this.expParamSource != null && this.validationType == MetaParameter.ValidationType.VALID){
			MetaProject.getInstance().getOpenProject().setNumberOfScenarios();
			if(this.expParamSource.getValues().size() != MetaProject.getInstance().getOpenProject().getNumberOfScenarios()){
				this.validationCode = MetaParameter.ValidationCode.INCONSISTENT_SCENARIOS;
				this.validationType = MetaParameter.ValidationType.ERROR;
				this.validationMessage = "The number of values for this parameter does not match the number of values that the other experiment parameters contain";
				previousValidationType = MetaParameter.ValidationType.VALID;
			}else{
				this.validationCode = MetaParameter.ValidationCode.VALID;
				this.validationType = MetaParameter.ValidationType.VALID;
				this.validationMessage = "";
				previousValidationType = MetaParameter.ValidationType.ERROR;
			}
		}
		if(this.validationType == MetaParameter.ValidationType.ERROR){
			this.validationMessage = "Error: " + this.validationMessage;
		}else if(this.validationType == MetaParameter.ValidationType.WARNING){
			this.validationMessage = "Warning: " + this.validationMessage;
		}
    	if(this.validationType != previousValidationType){
    		MetaProject.getInstance().updateErrorsWarnings(this);
    	}
    }
    public MetaParameter.DataType getDataType(){
    	return this.metaParam.getDataType();
    }
    public MetaParameter.ValidationCode getValidationCode(){
        return this.validationCode;
    }
    public String getParameterName(){
        return this.metaParam.getParameterName();
    }
    public String getParameterTitle(){
        return this.metaParam.getParameterTitle();
    }
    public String getParameterType(){
    	return this.metaParam.getParameterType();
    }
    public void setParameterValue(String value){
    	this.setParameterValue(value, false);
    }
    public void setParameterValue(String value, Boolean skipValidation){
    	this.strValue = value.trim();
    	this.updateValidationCode();
    	if(this.getValidationCode() == MetaParameter.ValidationCode.VALID ||
    			this.getValidationCode() == MetaParameter.ValidationCode.RANGE_LOW ||
    			this.getValidationCode() == MetaParameter.ValidationCode.RANGE_HIGH || 
    			this.getValidationCode() == MetaParameter.ValidationCode.INCONSISTENT_SCENARIOS){
	        switch(this.getDataType()){
	            case INTEGER:
	            	this.intValue = Integer.parseInt(value);
	                break;
	            case FLOAT:
	            	this.dblValue = Double.parseDouble(value);
	                break;
	            case BOOL:
	            	if(value.equals("YES")){
						this.boolValue = true;
					}else if(value.equals("NO")){
						this.boolValue = false;
					}else{
						this.boolValue = null;
					}
	            	break;
	            case DATE:
	            	try {
	            		this.dateValue = new SimpleDateFormat("MM/dd/yyyy").parse(value);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					break;
				case DAY:
					try{
	            		this.dateValue = new SimpleDateFormat("MM/dd").parse(value);
					}catch(ParseException e2) {
						e2.printStackTrace();
					}
	            	break;
	        }
	    }
    }
    public String getParameterValue(){
    	return this.strValue;
    }
    public Integer getParameterIntegerValue(){
    	return this.intValue;
    }
    public MetaParameter.ValidationType getValidationType() {
		return validationType;
	}
	public void setValidationType(MetaParameter.ValidationType validationType) {
		this.validationType = validationType;
	}
    public MetaParameter getMetaParameter(){
    	return this.metaParam;
    }
    public Object getSource(){
    	if(this.setupParamSource==null){
	    	return this.expParamSource;
    	}else{
    		return this.setupParamSource;
    	}
    }
    public String getValidationMessage(){
    	return this.validationMessage;
    }
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public String toString(){
    	return this.getParameterName()+":"+this.getParameterValue();
    }
	public MetaParameter.DataType getParameterDataType(){
		return this.metaParam.getDataType();
	}
}
