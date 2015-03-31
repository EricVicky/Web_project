package com.alu.omc.oam.os.conf;

public class OpenstackConfig {
    private String osUsername;
    private String osPassword;
    private String osTenant;
    private String osDomainName;
    private String authURL;
    private int identityVersion;
    private double clientReAuthTimeRatio = 0.75;

    public OpenstackConfig(String authURL, String osUsername,
            String osPassword, String osTenantOrDomainName) {
        if (authURL.endsWith("v2.0")) {
            setOsTenant(osTenantOrDomainName);
            setIdentityVersion(2);
        } else if (authURL.endsWith("v3.0")) {
            setOsDomainName(osTenantOrDomainName);
            setIdentityVersion(3);
        }
        this.osPassword = osPassword;
        this.osUsername = osUsername;
        this.authURL = authURL;
    }

    // Getter and Setter methods
    public String getOsUsername() {
        return osUsername;
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

    public String getOsDomainName() {
        return osDomainName;
    }

    public void setOsDomainName(String osDomainName) {
        this.osDomainName = osDomainName;
    }

    public int getIdentityVersion() {
        return identityVersion;
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
}
