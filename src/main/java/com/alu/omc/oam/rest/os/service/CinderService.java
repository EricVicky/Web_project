package com.alu.omc.oam.rest.os.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.storage.block.Volume;
import org.openstack4j.model.storage.block.VolumeAttachment;
import org.openstack4j.openstack.storage.block.domain.CinderVolume;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.rest.os.domain.cinder.YaoVolume;
import com.alu.omc.oam.rest.os.domain.cinder.YaoVolumeAttachment;

@Service 
public class CinderService 
{
	private Map<String, String> instanceIdNameMap = null;
	
	@SuppressWarnings("unchecked")
	@Cacheable(value="OS_DYNAMIC_CACHE", key="#client.access.user.name+'getAllVolumes'")
	public List<YaoVolume> getAllVolumes(OSClient client)
	{
		List<CinderVolume> volumeList = (List<CinderVolume>)client.blockStorage().volumes().list();
		if(instanceIdNameMap == null)
		{
			instanceIdNameMap = new HashMap<String,String>();
			List<? extends Server> servers = client.compute().servers().list();
			for(Server server : servers)
			{
				instanceIdNameMap.put(server.getId(), server.getName());
			}
		}
		List<YaoVolume> yaoVolumeList = new ArrayList<YaoVolume>();
		for(Volume volume : volumeList)
		{
			yaoVolumeList.add(assembleVolume(volume));
		}
		return yaoVolumeList;
	}
	
	private YaoVolume assembleVolume(Volume volume)
	{
		YaoVolume yaoVolume = new YaoVolume();
		yaoVolume.setAvailabilityZone(volume.getZone());
		yaoVolume.setCreationDate(volume.getCreated());
		yaoVolume.setDescription(volume.getDescription());
		yaoVolume.setId(volume.getId());
		yaoVolume.setName(volume.getName());
		yaoVolume.setSize(volume.getSize());
		yaoVolume.setStatus(volume.getStatus().name());
		yaoVolume.setVolumeType(volume.getVolumeType());
		
		List<YaoVolumeAttachment> yaoVolAttachList = new ArrayList<YaoVolumeAttachment>();
		for(VolumeAttachment volAttach: volume.getAttachments())
		{
			yaoVolAttachList.add(assembleVolumeAttachment(volAttach));
		}
		
		yaoVolume.setAttachments(yaoVolAttachList);
		
		return yaoVolume;
		
	}
	
	private YaoVolumeAttachment assembleVolumeAttachment(VolumeAttachment attach)
	{
		YaoVolumeAttachment yaoVolAttach = new YaoVolumeAttachment();
		yaoVolAttach.setDevice(attach.getDevice());
		if(instanceIdNameMap != null)
			yaoVolAttach.setHostName(instanceIdNameMap.get(attach.getServerId()));
		else
			yaoVolAttach.setHostName(attach.getHostname());
		yaoVolAttach.setId(attach.getId());
		yaoVolAttach.setServerId(attach.getServerId());
		yaoVolAttach.setVolumeId(attach.getVolumeId());
		return yaoVolAttach;
	}
}
