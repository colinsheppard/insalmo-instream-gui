package insalmo;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author critter
 */
public class MetaParameter {
	String paramName;
	String paramTitle;
	String paramType;  // habSetup,habParam,speSetup,etc.
	DataType dataType;	// FLOAT, INTEGER, etc.
	String defaultValue;
	String rangeLow;
	String rangeHigh;
	Double dLow;
	Double dHigh;
	Integer iLow;
	Integer iHigh;
	Integer stringLow;
	Integer stringHigh;
	Integer displayOrder;

	public enum DataType{
		FLOAT, INTEGER, STRING, INFILENAME, OUTFILENAME, DATE, DAY, BOOL
	}
	public enum ValidationCode{
		VALID, UNTESTED, RANGE_LOW, RANGE_HIGH, LENGTH_LONG, LENGTH_SHORT, INVALID_FLOAT, 
		INVALID_INTEGER, INVALID_DATE, INVALID_DAY, INVALID_BOOL, MISSING_FILE, ILLEGAL_FILE,
		INCONSISTENT_SCENARIOS, CHOOSING
	}
	public enum ValidationType{
		VALID, WARNING, ERROR, CHOOSING 
	}

	public MetaParameter(String variableName,String defaultValue,String variableTitle,String dataType,String parameterType,String displayOrder, String rangeLow, String rangeHigh){
		this.paramName = variableName;
		this.defaultValue = defaultValue;
		this.paramTitle = variableTitle;
		this.paramType = parameterType;
		this.dataType = this.typeFromString(dataType);
		this.rangeLow = rangeLow;
		this.rangeHigh = rangeHigh;
		this.displayOrder = Integer.parseInt(displayOrder);
		switch(this.dataType){
		case FLOAT:
			this.dHigh = this.rangeHigh.equals("") ? Double.MAX_VALUE : Double.parseDouble(this.rangeHigh);
			this.dLow = this.rangeHigh.equals("") ? -Double.MAX_VALUE : Double.parseDouble(this.rangeLow);
			break;
		case INTEGER:
			this.iHigh 	= this.rangeHigh.equals("") ? Integer.MAX_VALUE : Integer.parseInt(this.rangeHigh);
			this.iLow 	= this.rangeLow.equals("") ? Integer.MIN_VALUE : Integer.parseInt(this.rangeLow);
			break;
		case INFILENAME:
		case OUTFILENAME:
			this.stringLow = this.rangeLow.equals("") ? 0 : Integer.parseInt(this.rangeLow);
			this.stringHigh = this.rangeHigh.equals("") ? 250 : Integer.parseInt(this.rangeHigh);
			break;
		case STRING:
			this.stringLow = this.rangeLow.equals("") ? 0 : Integer.parseInt(this.rangeLow);
			this.stringHigh = this.rangeHigh.equals("") ? Integer.MAX_VALUE : Integer.parseInt(this.rangeHigh);
			break;
		}
	}
	public ValidationCode validateParam(String value){
		ValidationCode validationCode = null;
		value = value.trim();
		switch(this.dataType){
		case FLOAT:
			if(value.matches("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?")){
				Double dvalue = Double.parseDouble(value);
				if(dvalue > this.dHigh){
					validationCode = ValidationCode.RANGE_HIGH;
					break;
				}else if(dvalue < this.dLow){
					validationCode = ValidationCode.RANGE_LOW;
					break;
				}
				validationCode = ValidationCode.VALID;
			}else{
				validationCode = ValidationCode.INVALID_FLOAT;
			}
			break;
		case INTEGER:
			if(value.matches("[-]?[0-9]+")){
				Integer ivalue = Integer.parseInt(value);
				if(ivalue > this.iHigh){
					validationCode = ValidationCode.RANGE_HIGH;
					break;
				}else if(ivalue < this.iLow){
					validationCode = ValidationCode.RANGE_LOW;
					break;
				}
				validationCode = ValidationCode.VALID;
			}else{
				validationCode = ValidationCode.INVALID_INTEGER;
			}
			break;
		case STRING:
			if(value.length() > this.stringHigh){
				validationCode = ValidationCode.LENGTH_LONG;
				break;
			}else if(value.length() < this.stringLow){
				validationCode = ValidationCode.LENGTH_SHORT;
				break;
			}
			validationCode = ValidationCode.VALID;
			break;
		case OUTFILENAME:
			if(value.length() > this.stringHigh){
				validationCode = ValidationCode.LENGTH_LONG;
				break;
			}else if(value.length() < this.stringLow){
				validationCode = ValidationCode.LENGTH_SHORT;
				break;
			}else{
				validationCode = ValidationCode.VALID;
			}
			break;
		case INFILENAME:
			if(value.length()>=2 && value.substring(0, 1).equals("<") && value.substring(value.length()-1,value.length()).equals(">")){
				validationCode = ValidationCode.CHOOSING;
				break;
			}
			if(value.length() > this.stringHigh){
				validationCode = ValidationCode.LENGTH_LONG;
				break;
			}else if(value.length() < this.stringLow){
				validationCode = ValidationCode.LENGTH_SHORT;
				break;
			}
			// Now test if the file exists
			File f = new File(MetaProject.getInstance().getOpenProject().getProjectDirectory().getPath()+"/"+value);
			if(!f.exists() || value.equals("")){
				validationCode = ValidationCode.MISSING_FILE;
			}else if(f.isDirectory()){
				validationCode = ValidationCode.ILLEGAL_FILE;
			}else{
				validationCode = ValidationCode.VALID;
			}
			break;
		case DATE:
			validationCode = ValidationCode.VALID;
			try {
				new SimpleDateFormat("MM/dd/yyyy").parse(value);
			} catch (ParseException e) {
				validationCode = ValidationCode.INVALID_DATE;
			}
			break;
		case DAY:
			validationCode = ValidationCode.VALID;
			try {
				new SimpleDateFormat("MM/dd").parse(value);
			} catch (ParseException e) {
				validationCode = ValidationCode.INVALID_DAY;
			}
			break;
		case BOOL:
			if(value.equals("1") || value.equals("0")){
				validationCode = ValidationCode.VALID;
			}else{
				validationCode = ValidationCode.INVALID_BOOL;
			}
			break;
		default:
			validationCode = ValidationCode.UNTESTED;
		}
		// There's special validation needed to make sure the experiment manager settings are consistent
		if(validationCode==ValidationCode.VALID && this.paramName.equals("numberOfScenarios")){
			validationCode = validateExperimentScenarios(value);
		}
		return validationCode;
	}
	public ValidationCode validateExperimentScenarios(String value){
		Integer intValue = Integer.parseInt(value);
		Project p = MetaProject.getInstance().getOpenProject();
		for(String expParamKey: p.getExperimentParamKeys()){
			ExperimentParameter expParam = p.getExperimentParameters(expParamKey);
			if(expParam.values.size() != intValue){
				return ValidationCode.INCONSISTENT_SCENARIOS;
			}
		}
		return ValidationCode.VALID;
	}
	public DataType typeFromString(String strType){
		if(strType.equals("FLOAT")){
			return DataType.FLOAT;
		}else if(strType.equals("STRING")){
			return DataType.STRING;
		}else if(strType.equals("INFILENAME")){
			return DataType.INFILENAME;
		}else if(strType.equals("OUTFILENAME")){
			return DataType.OUTFILENAME;
		}else if(strType.equals("INTEGER")){
			return DataType.INTEGER;
		}else if(strType.equals("DATE")){
			return DataType.DATE;
		}else if(strType.equals("DAY")){
			return DataType.DAY;
		}else if(strType.equals("BOOL")){
			return DataType.BOOL;
		}
		return null;
	}
	public String typeToString(DataType type){
		switch(type){
		case FLOAT:
			return "double";  // insalmo is looking for 'double' not 'float' in Experiment.Setup
							  // eventually, all references to FLOAT here and in the metaprojectdata should be replaced
		case STRING:
			return "string";
		case INFILENAME:
		case OUTFILENAME:
			return "filename";
		case BOOL:
			return "BOOL";
		case INTEGER:
			return "int";
		case DATE:
			return "date";
		case DAY:
			return "day";
		}
		return null;
	}
	public String getClassNameForExperimentManager(){
		if(this.paramType.equals("modSetup")){
			return "TroutModelSwarm";
		}else if(this.paramType.equals("habSetup") || this.paramType.equals("habParam")){
			return "HabitatSpace";
		}else if(this.paramType.equals("speSetup") || this.paramType.equals("speParam")){
			return "FishParams";
		}else{
			return null;
		} 
	}
	public String getParameterName(){
		return this.paramName;
	}
	public Integer getDisplayOrder(){
		return this.displayOrder;
	}
	public String getParameterTitle(){
		return this.paramTitle;
	}
	public String getParameterType() {
		return paramType;
	}
	public String getDataTypeString(){
		return this.typeToString(this.dataType);
	}
	public MetaParameter.DataType getDataType(){
		return dataType;
	}
	public void setParameterType(String parameterType) {
		this.paramType = parameterType;
	}
	public Integer getMinStringLength(){
		return this.stringLow;
	}
	public Integer getMaxStringLength(){
		return this.stringHigh;
	}
	public String getDefaultValue(){
		return this.defaultValue;
	}
	public String toString(){
		return "MetaParameter: "+this.paramName+" ("+this.paramType+")";
	}
}
