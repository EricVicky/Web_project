package com.alu.omc.oam.config;

public class HpsimCOMConfig extends OVMCOMConfig {

	@Override
	public COMType getCOMType() {
		return COMType.HPSIM;
	}

}
