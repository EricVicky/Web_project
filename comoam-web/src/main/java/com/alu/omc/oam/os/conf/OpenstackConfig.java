package com.alu.omc.oam.os.conf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OpenstackConfig implements Serializable {
    /**
      * @Fields serialVersionUID : TODO
      */
    private static final long serialVersionUID = -8498782124537722281L;
    private String osUsername;
    private String osPassword;
    private String osTenant;
    private String osDomainName;
    private String authURL;
    private String osRegion;
    private int identityVersion;
    private double clientReAuthTimeRatio = 0.75;
    private final int VERSION_3 = 3;
    private final int VERSION_2 = 2;

    public OpenstackConfig(String authURL, String osUsername,
            String osPassword, String osTenantOrDomainName) {
        this.osPassword = osPassword;
        this.osUsername = osUsername;
        this.authURL = authURL;
        if (this.isVersion2()) {
            setOsTenant(osTenantOrDomainName);
            setIdentityVersion(2);
        } else if (this.isVersion3()) {
            setOsDomainName(osTenantOrDomainName);
            setIdentityVersion(3);
        }
    }
    public OpenstackConfig (){
        
    }
    
    public String getOsUsername() {
        return osUsername;
    }

	public String getOsRegion() {
		return osRegion;
	}
	public void setOsRegion(String osRegion) {
		this.osRegion = osRegion;
	}
	public void setOsUsername(String osUsername) {
        this.osUsername = osUsername;
    }

    public String getOsPassword() {
        return osPassword;
    }

    public void setOsPassword(String osPassword) {
        this.osPassword = osPassword;
    }

    public String getOsTenant() {
        return osTenant;
    }

    public void setOsTenant(String osTenant) {
        this.osTenant = osTenant;
    }

    public String getAuthURL() {
        return authURL;
    }

    public void setAuthURL(String authURL) {
        this.authURL = authURL;
    }

	@JsonIgnore 
    public String getOsDomainName() {
       if(!this.isVersion2()){
           return this.osTenant;
       }else{
           return null;
       }
    }

    public void setOsDomainName(String osDomainName) {
        this.osDomainName = osDomainName;
    }

	@JsonIgnore 
    public int getIdentityVersion() {
       if(this.isVersion2()){
           return VERSION_2;
       }else{
           return VERSION_3;
       }
    }

    public void setIdentityVersion(int identityVersion) {
        this.identityVersion = identityVersion;
    }

    public double getClientReAuthTimeRatio() {
        return clientReAuthTimeRatio;
    }

    public void setClientReAuthTimeRatio(double clientReAuthTimeRatio) {
        this.clientReAuthTimeRatio = clientReAuthTimeRatio;
    }
    
    
	@JsonIgnore 
    public boolean isVersion2(){
        return authURL.contains("v2.0");
    }
	
	
	@JsonIgnore 
    public boolean isVersion3(){
        return authURL.contains("v3.0");
    }
	
	
	public String asExportEnvironment(){
	   StringBuffer vars = new StringBuffer();
	   vars.append("export OS_AUTH_URL=").append(this.getAuthURL()).append("\n");
	   vars.append("export OS_TENANT_NAME=\"").append(this.getOsTenant()).append("\"\n");
	   vars.append("export OS_USERNAME=\"").append(this.osUsername).append("\"\n");
	   vars.append("export OS_PASSWORD=\"").append(this.getOsPassword()).append("\"\n");
	   vars.append("export OS_REGION_NAME=\"").append(this.osRegion).append("\"\n");
	   return vars.toString();
	}
	
	public Map<String, String> asEnvironmentMap(){
	   Map<String, String> envs = new HashMap<String, String>();
	   envs.put("OS_AUTH_URL", this.getAuthURL());
	   envs.put("OS_TENANT_NAME", this.getOsTenant());
	   envs.put("OS_USERNAME", this.osUsername);
	   envs.put("OS_PASSWORD",this.getOsPassword());
	   envs.put("OS_REGION_NAME", this.osRegion);
	   return envs;
	}
}
