package com.alu.omc.oam.rest.os.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Limits;
import org.openstack4j.model.compute.SecGroupExtension;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.heat.Stack;
import org.openstack4j.model.image.Image;
import org.openstack4j.model.storage.block.BlockLimits;
import org.openstack4j.openstack.compute.domain.NovaFlavor;
import org.openstack4j.openstack.compute.domain.NovaKeypair;
import org.openstack4j.openstack.compute.domain.NovaServer;
import org.openstack4j.openstack.compute.domain.ext.ExtAvailabilityZone;
import org.openstack4j.openstack.heat.domain.HeatStack;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.rest.os.domain.compute.YaoFlavor;
import com.alu.omc.oam.rest.os.domain.compute.YaoInstance;
import com.alu.omc.oam.rest.os.domain.compute.YaoLimits;
import com.alu.omc.oam.rest.os.domain.compute.YaoSecurityGroup;
import com.alu.omc.oam.rest.os.service.access.YaoOsClientService;
import com.alu.omc.oam.rest.os.util.ListServiceLoader;

@Service
public class ComputeService {
	
	@Resource
	private YaoOsClientService yaoOsClientService;
	
	public void setYaoOsClientService(YaoOsClientService yaoOsClientService) {
		this.yaoOsClientService = yaoOsClientService;
	}

	@SuppressWarnings("unchecked")
	@Cacheable(value="OS_DYNAMIC_CACHE", key="#client.access.user.name+'getInstanceList1'")
    public List<YaoInstance> getInstanceListSample(OSClient client) 
    {
		List<NovaServer> servers = (List<NovaServer>)client.compute().servers().list();
        if(servers == null || servers.size() == 0)
        {
        	return Collections.<YaoInstance>emptyList();
        }
        List<HeatStack> stacks = (List<HeatStack>)client.heat().stacks().list();
        List<YaoInstance> instances = new ArrayList<YaoInstance>();
        Set<String> stackNameSet = new HashSet<String>();
        for(Stack stack : stacks)
        {
        	stackNameSet.add(stack.getName());
        }
        for (Server server : servers) 
        {
            YaoInstance instance = new YaoInstance(server);
            String instanceNamePref = instance.getName().split("-")[0];
            if(stackNameSet.contains(instanceNamePref))
            {
            	instance.setStackName(instanceNamePref);
            }
            instances.add(instance);
        }
        return instances;
    }
	
	public List<String> getKeyPairNames(OSClient client)
	{
		List<NovaKeypair> keypairList =  getKeyPairList(client);
		List<String> keypairNames = new ArrayList<String>();
		for(NovaKeypair keypair : keypairList)
		{
			keypairNames.add(keypair.getName());
		}
		return keypairNames;
	}
	
	public List<String> getFlavorNames(OSClient client)
	{
		List<YaoFlavor> flavorList = getFlavorList(client);
		List<String> flavorNames = new ArrayList<String>();
		for(YaoFlavor flavor : flavorList)
		{
			flavorNames.add(flavor.getName());
		}
		return flavorNames;
	}
	
	@SuppressWarnings("unchecked")
	public List<ExtAvailabilityZone> getAvaliabilityZoneList(OSClient client)
	{
		return (List<ExtAvailabilityZone>)client.compute().zones().list();
	}
	
	public List<String> getAvaliabilityZoneNames(OSClient client)
	{
		List<ExtAvailabilityZone> availZoneList = getAvaliabilityZoneList(client);
		List<String> availZoneNames = new ArrayList<String>();
		for(ExtAvailabilityZone zone : availZoneList)
		{
			availZoneNames.add(zone.getZoneName());
		}
		return availZoneNames;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Cacheable(value="OS_DYNAMIC_CACHE", key="#client.access.user.name+'getInstanceList'")
	public List<YaoInstance> getInstanceList(OSClient client)
	{
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Object serverService = client.compute().servers();
		Object stackService = client.heat().stacks();
		Object imageservice = client.images();
		Object flavorService = client.compute().flavors();
		
		ListServiceLoader serverServiceLoader = new ListServiceLoader(serverService, yaoOsClientService);
		ListServiceLoader stackServiceLoader = new ListServiceLoader(stackService, yaoOsClientService);
		ListServiceLoader imageserviceLoader = new ListServiceLoader(imageservice, yaoOsClientService);
		ListServiceLoader flavorServiceLoader = new ListServiceLoader(flavorService, yaoOsClientService);
		
		Future<List> stackServiceFuture = executor.submit(stackServiceLoader);
		Future<List> imageserviceFuture = executor.submit(imageserviceLoader);
		Future<List> flavorServiceFuture = executor.submit(flavorServiceLoader);
		Future<List> serverServiceFuture = executor.submit(serverServiceLoader);
		
		try {
			List<NovaServer> serverList = (List<NovaServer>)serverServiceFuture.get();
			List<HeatStack> stackList = (List<HeatStack>)stackServiceFuture.get();
			List<Image> imageList = (List<Image>)imageserviceFuture.get();
			List<Flavor> flavorList = (List<Flavor>)flavorServiceFuture.get();
			
			return assembleInstance(serverList, stackList, imageList, flavorList);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return null;
		}
		//return null;
	}
	
	@SuppressWarnings("rawtypes")
	private List<YaoInstance> assembleInstance(List<NovaServer> serverList, 
											   List<HeatStack> stackList, List<Image> imageList,
											   List<Flavor> flavorList)
	{
		 Set<String> stackNameSet = new HashSet<String>();
         for(Stack stack : stackList)
         {
        	 stackNameSet.add(stack.getName());
         }
         Map<String, Image> imageMap = new HashMap<String, Image>();
         for(Image image : imageList)
         {
        	 imageMap.put(image.getId(), image);
         }
         Map<String, Flavor> flavorMap = new HashMap<String, Flavor>();
         for(Flavor flavor : flavorList)
         {
        	 flavorMap.put(flavor.getId(), flavor);
         }
         List<YaoInstance> instanceList = new ArrayList<YaoInstance>();
         for(NovaServer server : serverList)
         {
        	 YaoInstance instance = new YaoInstance(server);
        	 String instanceNamePref = instance.getName().split("-")[0];
             if(stackNameSet.contains(instanceNamePref))
             {
             	instance.setStackName(instanceNamePref);
             }
             Flavor flavor = flavorMap.get(server.getFlavorId());
             if(flavor != null)
            	 instance.setFlavor(flavor.getName());
             
             if (server.image instanceof LinkedHashMap) {
     			LinkedHashMap map = (LinkedHashMap) server.image;
     			String imageId = (String) map.get("id");
     			Image image = imageMap.get(imageId);
     			if(image != null)
     				instance.setImage(image.getName());
             }
             instanceList.add(instance);
         }
         
         return instanceList;
	}
	
	@SuppressWarnings("unchecked")
	@Cacheable(value="OS_STATIC_CACHE", key="#client.access.user.name+'getFlavorList'")
	public List<YaoFlavor> getFlavorList(OSClient client)
	{
		List<NovaFlavor> flavors = (List<NovaFlavor>)client.compute().flavors().list();
		List<YaoFlavor> flavorlist = new ArrayList<YaoFlavor>();
		for (Flavor flavor : flavors){
			YaoFlavor yaoFlavor = new YaoFlavor(flavor);
			flavorlist.add(yaoFlavor);			
		}
		
		return flavorlist;
	}

    public YaoInstance getInstance(OSClient client, String serverId) {
        Server server = client.compute().servers().get(serverId);
        if (server != null)
            return new YaoInstance(server);
        else
            return null;
    }

    @SuppressWarnings("unchecked")
    public List<NovaKeypair> getKeyPairList(OSClient client) {
        return (List<NovaKeypair>) client.compute().keypairs().list();
    }

    public YaoLimits getDigestLimits(OSClient client) {
        Limits limits = client.compute().quotaSets().limits();
        YaoLimits lr = new YaoLimits(limits.getAbsolute());

        // Nova doesn't include the block storage limits
        BlockLimits blockLimits;
        if (limits.getAbsolute().getMaxTotalVolumes() == -1) {
            blockLimits = client.blockStorage().getLimits();
            lr.addStorageLimits(blockLimits.getAbsolute());
        }
        return lr;
    }

    public String getConsoleLogs(OSClient client, String serverId) {
       return client.compute().servers().getConsoleOutput(serverId, 0);
    }

    public List<YaoSecurityGroup> getSecurityGroupList(OSClient client) {
        List<? extends SecGroupExtension> secGroupList = client.compute()
                .securityGroups().list();
        List<YaoSecurityGroup> yaoSecGroupList = new ArrayList<YaoSecurityGroup>();
        for (SecGroupExtension secGroup : secGroupList) {
            yaoSecGroupList.add(new YaoSecurityGroup(secGroup));
        }
        return yaoSecGroupList;
    }

}
