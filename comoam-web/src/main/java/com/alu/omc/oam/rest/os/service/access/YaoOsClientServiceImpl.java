package com.alu.omc.oam.rest.os.service.access;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.types.Facing;
import org.openstack4j.core.transport.Config;
import org.openstack4j.openstack.OSFactory;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.os.conf.ConfigCache;
import com.alu.omc.oam.os.conf.OpenstackConfig;
import com.alu.omc.oam.rest.os.YaoOSFactory;

@Service
public class YaoOsClientServiceImpl implements YaoOsClientService {

    private static final Log log = LogFactory.getLog(YaoOsClientServiceImpl.class);

    private static YaoAccess access = null;
    @Resource 
    ConfigCache configCache;
    
    // TODO: Optimize for synchronized
    @Override
    public synchronized OSClient getOsClient() {
        if (access == null) {
            log.debug("accces is null");
            access = genOsAccess();
        } else {
            log.info("access isn't null and it is " + access);
            if (access.needReAuthenticate()) {
                log.debug("Re-Authenticate");
                access = genOsAccess();
            }
            log.debug("Using existing " + access);
        }
        return YaoOSFactory.clientFromAccess(access, Facing.PUBLIC);        
    }
    
    private YaoAccess genOsAccess() {
        return new YaoAccess(authenticate().getAccess());
    }
    
    // Interface for authenticate
    private  OSClient authenticate() {
        OpenstackConfig osConfig = configCache.getOSParam();
        if (osConfig.getIdentityVersion() == 2) {
            return authenticateV2(osConfig);
        } else {
            return authenticateV3(osConfig);
        }
    }

    // V2
    private OSClient authenticateV2(OpenstackConfig osConfig) {
        OSClient os=  OSFactory
                .builder()
                .endpoint(osConfig.getAuthURL())
                .credentials(osConfig.getOsUsername(), osConfig.getOsPassword())
                .tenantName(osConfig.getOsTenant()).perspective(Facing.PUBLIC).useNonStrictSSLClient(true)
                .authenticate();
        setRegion(os, osConfig);
        return os;
    }
    
    
    private Config getConfigSSL(){
        Config sslConfig = Config.newConfig();
     // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
            public X509Certificate[] getAcceptedIssuers(){return null;}
            public void checkClientTrusted(X509Certificate[] certs, String authType){}
            public void checkServerTrusted(X509Certificate[] certs, String authType){}
        }};

        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
             sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        sslConfig.withSSLContext(sc);
        return sslConfig;
    }

    // V3
    private OSClient authenticateV3(OpenstackConfig osConfig) {
        OSClient os=  OSFactory
                .builderV3()
                .endpoint(osConfig.getAuthURL())
                .credentials(osConfig.getOsUsername(), osConfig.getOsPassword())
                .domainName(osConfig.getOsDomainName())
                .perspective(Facing.PUBLIC).useNonStrictSSLClient(true).authenticate();
        setRegion(os, osConfig);
        return os;
    }
    
    private void setRegion(OSClient os, OpenstackConfig osConfig){
     if(osConfig.getOsRegion() != null && osConfig.getOsRegion() != ""){
            os.useRegion(osConfig.getOsRegion());
        }
        
    }

    @Override
    public void reset()
    {
       this.access = null; 
    }
}
