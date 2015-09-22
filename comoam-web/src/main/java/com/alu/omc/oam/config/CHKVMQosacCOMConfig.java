package com.alu.omc.oam.config;

import java.util.Iterator;
import java.util.Map;

import com.alu.omc.oam.util.Json2Object;
import com.alu.omc.oam.util.JsonYamlConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CHKVMQosacCOMConfig extends QosacCOMConfig{
	
	private Map<String, OLDVMConfig> old_vm_config;
	   
	public Map<String, OLDVMConfig> getOld_vm_config() {
		return old_vm_config;
	}

	public void setOld_vm_config(Map<String, OLDVMConfig> old_vm_config) {
		this.old_vm_config = old_vm_config;
	}

	@Override
	@JsonIgnore 
	public String getVars() {
        Iterator<String> it = getVm_config().keySet().iterator(); 
	    while(it.hasNext()){
	        String name = it.next();
	        @SuppressWarnings("unchecked")
            Map<String, String> vmcfg = (Map<String, String>)getVm_config().get(name);
	       // VNFHostName.add(vmcfg, this.getCOMType(), name, getDeployment_prefix());
	        String istoption = "";
		    istoption = InstallOptions.get(COMType.QOSAC, COMType.QOSAC.name());
	        vmcfg.put("install_options", istoption );
	    }
	    Iterator<String> old_it = old_vm_config.keySet().iterator(); 
	   String json = Json2Object.object2Json(this);
       return JsonYamlConverter.convertJson2Yaml(json);
	}

}
