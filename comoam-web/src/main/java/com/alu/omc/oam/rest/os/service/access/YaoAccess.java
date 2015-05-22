package com.alu.omc.oam.rest.os.service.access;

import java.util.Date;

import org.openstack4j.model.identity.Access;

import com.alu.omc.oam.util.TimeUtils;

public class YaoAccess {

    // TODO: should be configurable
    private final double OSReAuthPercent = 0.75;

    private Access access;
    private Date tokenGeneratedDate;
    private Date tokenExpiresDate;

    // Constructor
    public YaoAccess(Access access) {
        this.access = access;
        this.tokenExpiresDate = access.getToken().getExpires();
        this.tokenGeneratedDate = new Date();
    }

    // Check if the token is expired
    public boolean isExpired() {
        return tokenExpiresDate.compareTo(new Date()) <= 0;
    }

    public boolean needReAuthenticate() {
        return TimeUtils.elapsedOfPercent(this.tokenGeneratedDate,
                this.tokenExpiresDate, OSReAuthPercent);
    }
    
    public Access getAccess() {
        return access;
    }

}
