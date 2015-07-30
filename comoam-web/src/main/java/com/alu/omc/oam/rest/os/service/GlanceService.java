package com.alu.omc.oam.rest.os.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.image.Image;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.rest.os.domain.compute.YaoImage;

@Service
public class GlanceService 
{
	@Cacheable(value="OS_STATIC_CACHE", key="#client.token.id+'getImageList'")
	public List<YaoImage> getImageList(OSClient client)
	{	
		Map<String, String> params = new HashMap<String, String>();
		params.put("limit", Integer.MAX_VALUE+"");
		List<? extends Image> images = client.images().list(params);
		List<YaoImage> imageList = new ArrayList<YaoImage>();
		for(Image image : images)
		{
		    if( image.getStatus() != Image.Status.ACTIVE){
		        continue;
		    }
			YaoImage yaoImage = new YaoImage(image);
						
			imageList.add(yaoImage);
		}
		return  imageList;
	}
	
	public List<String> getImageNames(OSClient client)
	{
		List<YaoImage> imageList = getImageList(client);
		List<String> imageNames = new ArrayList<String>();
		for(YaoImage image : imageList)
		{
			imageNames.add(image.getName());
		}
		
		return imageNames;
	}
}
