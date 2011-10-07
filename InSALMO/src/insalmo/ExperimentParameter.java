package insalmo;

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
	
}
