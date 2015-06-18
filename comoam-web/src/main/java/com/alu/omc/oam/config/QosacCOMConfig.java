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
	
	@Override
	@JsonIgnore 
	public String getVars() {
        Iterator<String> it = getVm_config().keySet().iterator(); 
	    while(it.hasNext()){
	        String name = it.next();
	        @SuppressWarnings("unchecked")
            Map<String, String> vmcfg = (Map<String, String>)getVm_config().get(name);
	        vmcfg.put("hostname", this.getDeployment_prefix().concat("-").concat(this.getComType().toLowerCase()).concat("-1"));
	        String istoption = "";
		    istoption = InstallOptions.get(COMType.QOSAC, COMType.QOSAC.name());
	        vmcfg.put("install_options", istoption );
	    }
		Yaml yaml = new Yaml();
        return YamlFormatterUtil.format(yaml.dump(this));	
	}

}
