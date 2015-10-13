package com.alu.omc.oam.config;

import java.util.Iterator;
import java.util.Map;

import com.alu.omc.oam.util.Json2Object;
import com.alu.omc.oam.util.JsonYamlConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class QosacCOMConfig extends OVMCOMConfig implements EncryPassword {

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
	       // VNFHostName.add(vmcfg, this.getCOMType(), name, getDeployment_prefix());
	        String istoption = "";
		    istoption = InstallOptions.get(COMType.QOSAC, COMType.QOSAC.name());
	        vmcfg.put("install_options", istoption );
	    }
	   String json = Json2Object.object2Json(this);
       return JsonYamlConverter.convertJson2Yaml(json);
	}
	
	private String root_password;
    private String axadmin_password;
	public String getRoot_password() {
		return root_password;
	}

	public void setRoot_password(String root_password) {
		this.root_password = root_password;
	}

	public String getAxadmin_password() {
		return axadmin_password;
	}

	public void setAxadmin_password(String axadmin_password) {
		this.axadmin_password = axadmin_password;
	}

}
