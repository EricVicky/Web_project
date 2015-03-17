package com.alu.omc.oam.rest.kvm.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.kvm.model.KvmFlavor;
@RestController
@RequestMapping("/rest/kvm/compute")
public class FlavorController
{
    @RequestMapping("/flavor/list")
    public List<KvmFlavor> getFlavorList()
    {
        List<KvmFlavor> flavorlist = new ArrayList<KvmFlavor>(10);
        flavorlist.add(new KvmFlavor(1,2,3));
        flavorlist.add(new KvmFlavor(6,5,9));
        return flavorlist;
    }
}
