angular.module('kvm').controller('ovmctr', function($scope,  $log, KVMService, monitorService, timezoneService, $state){
                        	
    $scope.installConfig ={
    		"vm_config":{
    			"ovm":{
    				"ipv6_address":"",
    				"ipv6_prefix":"",
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
    	$scope.temp_comTypeStore = data.OVMType;
		var comTypeStore=[];
		for(var index in $scope.temp_comTypeStore){
			if($scope.temp_comTypeStore[index].Name=='ATC'||$scope.temp_comTypeStore[index].Name=='HPSIM'){
				comTypeStore.push($scope.temp_comTypeStore[index]);
			}
		}
		$scope.comTypeStore = comTypeStore;
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
	
	$scope.installConfig = {
			"installConfig.comType":"ARS",
			"active_host_ip":"",
    		"vm_config":{
    			"db":{
    				"ip_address":""
    			}
    		},
	        "deployment_prefix":""
    };    
	
	$scope.reloadinstallconfig = function (){
		if($scope.com_instance != null){
        	$scope.installConfig.active_host_ip = $scope.com_instance.active_host_ip;
        	$scope.installConfig.vm_config.db.ip_address = $scope.com_instance.vm_config.db.nic[0].ip_v4.ipaddress;
        	$scope.installConfig.deployment_prefix = $scope.com_instance.deployment_prefix;
    	}
	};
	
	KVMService.getComInstance().then( function(data) {
		$scope.kvmcomInstance = [];
		for(var ci=0;ci<data.length;ci++){
			if(data[ci].comType == "OAM" ||  data[ci].comType == "CM" || data[ci].comType == "FCAPS"){
			    if(JSON3.parse(data[ci].comConfig).environment ==  "KVM"){
					$scope.kvmcomInstance.push(JSON3.parse(data[ci].comConfig));
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
}).controller('ovmqosacctr', function($scope,  $log, KVMService, monitorService, timezoneService, $state){
                        	
    $scope.installConfig ={
    		"vm_config":{
    			"ovm":{
    				"ipv6_address":"",
    				"ipv6_prefix":"",
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
     
     $scope.$watchGroup(['installConfig.root_password', 'installConfig.re_root_password','installConfig.axadmin_password','installConfig.re_axadmin_password'], function() {
     	if($scope.installConfig.root_password!=$scope.installConfig.re_root_password||$scope.installConfig.axadmin_password!=$scope.installConfig.re_axadmin_password){
     		$scope.disMatch = true;
     	}else{
     		$scope.disMatch = false;
     	}
     });

    
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
    	$log.info($scope.installConfig);
    	$scope.export = !$scope.export;
    	$scope.installConfig.app_install_options = {
    			BACKUP_SERVER_DISK_SPACE:'2000',
    			NTP_SERVER:'COM_LOCAL_CLOCK',
    			SEC_UNIX_ENABLE:'YES',
    			OMCCN_SUPPORT_SP_FM:'NO',
    			OMCCN_SUPPORT_SP_PM:'NO',
    			OMCCN_SUPPORT_SP_HVP:'NO',
    			BACKUP_SERVER_IS_LOCAL:'YES',
    			SOFTWARE_SERVER_IS_LOCAL:'YES',
    			SOFTWARE_SERVER_ADDRESS:$scope.installConfig.vm_config.ovm.ip_address,
    			BACKUP_SERVER_ADDRESS:$scope.installConfig.vm_config.ovm.ip_address
    	};
    };
    
})
