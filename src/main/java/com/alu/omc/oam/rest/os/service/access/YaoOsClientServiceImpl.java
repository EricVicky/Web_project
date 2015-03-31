package com.alu.omc.oam.rest.os.service.access;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.types.Facing;
import org.openstack4j.openstack.OSFactory;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.os.conf.ConfigCache;
import com.alu.omc.oam.os.conf.OpenstackConfig;
import com.alu.omc.oam.rest.os.YaoOSFactory;

@Service
public class YaoOsClientServiceImpl implements YaoOsClientService {

    private static final Log log = LogFactory.getLog(YaoOsClientServiceImpl.class);

    private static YaoAccess access = null;
    
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
        OpenstackConfig osConfig = ConfigCache.getInstance().getOSParam();
        if (osConfig.getIdentityVersion() == 2) {
            return authenticateV2(osConfig);
        } else {
            return authenticateV3(osConfig);
        }
    }

    // V2
    private OSClient authenticateV2(OpenstackConfig osConfig) {
        return OSFactory
                .builder()
                .endpoint(osConfig.getAuthURL())
                .credentials(osConfig.getOsUsername(), osConfig.getOsPassword())
                .tenantName(osConfig.getOsTenant()).perspective(Facing.PUBLIC)
                .authenticate();
    }

    // V3
    private OSClient authenticateV3(OpenstackConfig osConfig) {
        return OSFactory
                .builderV3()
                .endpoint(osConfig.getAuthURL())
                .credentials(osConfig.getOsUsername(), osConfig.getOsPassword())
                .domainName(osConfig.getOsDomainName())
                .perspective(Facing.PUBLIC).authenticate();
    }
}
