package com.alu.omc.oam.rest.config;

import java.io.IOException;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;

import org.openstack4j.api.OSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.ansible.validation.ValidationResult;
import com.alu.omc.oam.os.conf.OpenstackConfig;
import com.alu.omc.oam.rest.os.service.access.YaoOsClientService;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.util.InstallCert;
@RestController 
public class OSConfigController
{
    @Resource
    COMStackService cOMStackService;

	@Resource
	private YaoOsClientService yaoOsClientService;
	
	 private static Logger log = LoggerFactory.getLogger(OSConfigController.class);
    @RequestMapping(value="/os/rCred", method=RequestMethod.GET)
    public OpenstackConfig rCred() throws IOException, InterruptedException
    {
        return cOMStackService.getOpenstackConfig();
    }

    @RequestMapping(value="/os/uCred", method=RequestMethod.POST)
    public OpenstackConfig uCred(@RequestBody OpenstackConfig config) throws Exception
    {
        cOMStackService.addOpenstackConfig(config);
        if(config.HTTPs()){
            new InstallCert().importCert(config);
        }
        log.info("starting to download&export certification");
        yaoOsClientService.reset();
        return config;
    }
    
    @RequestMapping(value="/os/vCred", method=RequestMethod.GET)
    public ValidationResult vCred() throws IOException, InterruptedException
    {
    	ValidationResult res = new ValidationResult();
    	OpenstackConfig config = cOMStackService.getOpenstackConfig();
        if(config==null){
        	res.setSucceed(false);
        	res.setMessage("Openstack credentail not config");
        	return res;
        }else{
        	try {
				yaoOsClientService.getOsClient();
			} catch (Exception e) {
				log.error("Unable connect to openstack service", e);
				res.setSucceed(false);
				res.setMessage("Unable connect to openstack service");
				return res;
			}
			res.setSucceed(true);
        }
    	return res;
    }
}
