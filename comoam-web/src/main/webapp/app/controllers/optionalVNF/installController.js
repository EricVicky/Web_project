angular.module('kvm').controller('ovmctr', function($scope,  $log, KVMService, monitorService, timezoneService, $state){
                        	
    $scope.installConfig ={
    		"vm_config":{
    			"ovm":{
    				"ipv6_address":"",
    				"ipv6_netmask":"",
    				"ipv6_gateway":""
    			}
    		}
    };    
    
    $scope.submitComtype = function(){
		$scope.loadimglist($scope.installConfig.active_host_ip, $scope.installConfig.vm_img_dir);
	};
	
	 $scope.loadimglist = function(host, dir){
     	KVMService.imagelist( { "host":host, "dir":dir}).then(
     			function(data) {
     				$log.info(data);
     				$scope.imagelist = data;
     			});   
     };

    
    KVMService.getFlavorStore().then( function(data) {
		$scope.flavorStore = data.Flavors;
	});
    
    KVMService.hostips().then(function(data) {
		$scope.hostIPs = data;
	});

    timezoneService.timezonelist().then( function(data) {
		$scope.timezoneStore = data;
	});
    
    KVMService.getComTypeStore().then(function(data){
		$scope.comTypeStore = data.OVMType;
	 	$scope.installConfig.comType = KVMService.VNFType;
	});
    
    $scope.deploy = function(){
    	/*check if it is locked: prevent multiple operation on same comstack
         * it may cause log chaos on monitor page
        */
        KVMService.comstackStatus($scope.installConfig.deployment_prefix).then(function(status){
            		var action_in_progress = 2;
            		if(status.state == action_in_progress){
            			if(window.confirm("some operation  proceed on selected vnf instance, go to monitor?")){
            				monitorService.monitor("KVM", status.lastaction, $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
            				$state.go('dashboard.monitor');
            			}
            		}else{
            			$scope.doDeploy();
            		}
        });
    };
    
    $scope.doDeploy = function (){
    	KVMService.deployOVM($scope.installConfig).then( function(){
            monitorService.monitor("KVM", "INSTALL", $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
            $state.go("dashboard.monitor");

		});
    };
    
    $scope.genExport = function (){
    	$scope.export = !$scope.export;
    };
    
}).controller('ovmarsctr', function($scope,  $log, KVMService, monitorService, timezoneService, $state){
	
	$scope.reloadinstallconfig = function (){
		if($scope.com_instance != null){
        	$scope.installConfig.active_host_ip = $scope.com_instance.comConfig.active_host_ip;
        	$scope.installConfig.vm_config.db.ip_address = $scope.com_instance.installConfig.vm_config.db.nic[0].ip_v4.ipaddress;
        	$scope.installConfig.deployment_prefix = $scope.com_instance.deployment_prefix;
    	}
	};
	
	KVMService.getComInstance().then( function(data) {
		$log.info(data);
	 	$scope.installConfig.comType = KVMService.VNFType;
		$scope.kvmcomInstance = [];
		for(var ci=0;ci<data.length;ci++){
			if(data[ci].comConfig.environment ==  "KVM"){
				if(data[ci].comConfig.comType == "OAM" ||  data[ci].comConfig.comType == "CM" || data[ci].comConfig.comType == "FCAPS"){
					$scope.kvmcomInstance.push(data[ci]);
				}
			}
		}
		
    });
    
    $scope.genExport = function (){
    	$scope.export = !$scope.export;
    };
    
    $scope.deploy = function(){
    	/*check if it is locked: prevent multiple operation on same comstack
         * it may cause log chaos on monitor page
        */
        KVMService.comstackStatus($scope.installConfig.deployment_prefix).then(function(status){
            		var action_in_progress = 2;
            		if(status.state == action_in_progress){
            			if(window.confirm("some operation  proceed on selected vnf instance, go to monitor?")){
            				monitorService.monitor("KVM", status.lastaction, $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
            				$state.go('dashboard.monitor');
            			}
            		}else{
            			$scope.doDeploy();
            		}
        });
    };
    
    $scope.doDeploy = function (){
    	KVMService.deployOVM($scope.installConfig).then( function(){
            monitorService.monitor("KVM", "INSTALL", $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
            $state.go("dashboard.monitor");

		});
    };
});