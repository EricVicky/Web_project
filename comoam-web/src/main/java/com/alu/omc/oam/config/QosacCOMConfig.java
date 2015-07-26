package com.alu.omc.oam.config;

import java.util.Iterator;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.util.YamlFormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class QosacCOMConfig extends OVMCOMConfig {

	@Override
	public COMType getCOMType() {
		return COMType.QOSAC;
	}
	private Map<String, String> app_install_options;
	public Map<String, String> getApp_install_options() {
		return app_install_options;
	}
	public void setApp_install_options(Map<String, String> app_install_options) {
		this.app_install_options = app_install_options;
	}
	@Override
	@JsonIgnore 
	public String getVars() {
        Iterator<String> it = getVm_config().keySet().iterator(); 
	    while(it.hasNext()){
	        String name = it.next();
	        @SuppressWarnings("unchecked")
            Map<String, String> vmcfg = (Map<String, String>)getVm_config().get(name);
	        VNFHostName.add(vmcfg, this.getCOMType(), name, getDeployment_prefix());
	        String istoption = "";
		    istoption = InstallOptions.get(COMType.QOSAC, COMType.QOSAC.name());
	        vmcfg.put("install_options", istoption );
	    }
		Yaml yaml = new Yaml();
        return YamlFormatterUtil.format(yaml.dump(this));	
	}

}
