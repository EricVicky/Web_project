package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.config.GRInstallConfig.GRIP;
import com.alu.omc.oam.util.Json2Object;
import com.alu.omc.oam.util.JsonYamlConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class GRUnInstallConfig<T extends COMConfig> extends COMConfig implements Serializable
{
	/**
     * @Fields serialVersionUID :
     */
private static final long serialVersionUID = 3890963920968307212L;
private T comConfig;
private boolean forced= false;

public boolean getForced()
{
    return forced;
}
public void setForced(boolean forced)
{
    this.forced = forced;
}
public T getComConfig() {
	return comConfig;
}
public void setComConfig(T comConfig) {
	this.comConfig = comConfig;
}
@JsonIgnore
@Override
public Inventory getInventory()
{
    Inventory  inv =comConfig.getInventory();
    inv.addNooamGroup();
    return inv;
}
@Override
@JsonIgnore 
public String getVars()
{
//    HashMap<String, Object> vars = new HashMap<String, Object>();
//    if(this.getEnvironment() == Environment.KVM){
//        vars.put("deployment_prefix", this.getStackName());
//    }else{
//        vars.put("stack_name", this.getStackName());
//    }
//    vars.put("forced","\"" + String.valueOf(this.getForced()) + "\"");
//    String json = Json2Object.object2Json(vars);
//    return JsonYamlConverter.convertJson2Yaml(json);
    
    StringBuffer sb = new StringBuffer();
    if(this.getEnvironment() == Environment.KVM){
    	sb.append("deployment_prefix:"+this.getStackName()+"\r\n");
    }else{
    	sb.append("stack_name:"+this.getStackName()+"\r\n");
    }
    sb.append("forced:"+"\'"+String.valueOf(this.getForced())+"\'");
    return sb.toString();
}


@Override
@JsonIgnore
public Environment getEnvironment()
{
   return comConfig.getEnvironment();
}

public String getVnf_type(){
    return this.getCOMType().name();
}
@Override
@JsonIgnore
public COMType getCOMType()
{
    return comConfig.getCOMType();
}

@Override
@JsonIgnore
public String getStackName()
{
    return comConfig.getStackName();
}



}
