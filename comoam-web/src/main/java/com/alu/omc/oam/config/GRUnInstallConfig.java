package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.util.Json2Object;
import com.alu.omc.oam.util.JsonYamlConverter;
import com.alu.omc.oam.util.YamlFormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml;

public class GRUnInstallConfig<T extends COMConfig> extends COMConfig implements Serializable
{
/**
      */
private static final long serialVersionUID = 3890963920968307212L;
private T comConfig;

public T getConfig()
{
    return comConfig;
}
public void setConfig(T config)
{
    this.comConfig = config;
}
@JsonIgnore
@Override
public Inventory getInventory()
{
    Inventory  inv =comConfig .getInventory();
    inv.addNooamGroup();
    return inv;
}
@Override
public String getVars()
{
 return "";
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
    return null;
}


}
