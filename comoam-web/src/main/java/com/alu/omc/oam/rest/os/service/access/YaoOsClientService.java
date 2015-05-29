package com.alu.omc.oam.rest.os.service.access;

import org.openstack4j.api.OSClient;


public interface YaoOsClientService {
    OSClient getOsClient();
    void reset();
}
