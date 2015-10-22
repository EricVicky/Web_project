angular.module('kvm').controller('ovmupgradectr', function($scope, $filter,  $log, KVMService,  
		monitorService, DashboardService, $dialogs, $state) {
	
	$scope.stepNo = 1;
	
	$scope.setDefaultInstace = function(){
    	var selectedKVMInstance = DashboardService.getSelectedInstance();
    	if(selectedKVMInstance == null){
    		return;
    	}
        $scope.installConfig = $scope.com_instance;
        for(var inst in $scope.kvmcomInstance){
        		var com_config = JSON3.parse($scope.kvmcomInstance[inst].comConfig);
        		if(angular.equals(com_config,selectedKVMInstance)){
        		   $scope.com_instance = $scope.kvmcomInstance[inst];	
        		   $scope.installConfig = com_config;
        		   return;
        		}
        }
  
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
    	$scope.loadimglist($scope.installConfig.active_host_ip, $scope.vm_img_dir);
    }
	
	KVMService.getComInstance().then( function(data) {
		$log.info(data);
		$scope.comInstance = data;
		$scope.kvmcomInstance = [];
		for(var ci=0;ci<$scope.comInstance.length;ci++){
			if(JSON3.parse($scope.comInstance[ci].comConfig).environment ==  "KVM"){
				if(JSON3.parse($scope.comInstance[ci].comConfig).comType == "QOSAC" ||
				     JSON3.parse($scope.comInstance[ci].comConfig).comType == "HPSIM"){
					$scope.kvmcomInstance.push($scope.comInstance[ci]);
				}
			}
		}
		$scope.setDefaultInstace();
		
    });
	
	$scope.reloadinstallconfig = function(){
    	if($scope.com_instance != null){
        	$scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
    	}
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
    };
	
    $scope.reloadimglist = function(){
    	$scope.loadimglist($scope.installConfig.active_host_ip, $scope.installConfig.vm_img_dir);
    };
    

	$scope.loadimglist = function(host, dir) {
		KVMService.imagelist({
			"host" : host,
			"dir" : dir
		}).then(function(data) {
			$scope.imagelist = data;
	    });
	};
	
	$scope.upgrade = function(){
		KVMService.comstackStatus($scope.installConfig.deployment_prefix).then(function(status){
            		var action_in_progress = 2;
            		if(status.state == action_in_progress){
            			if(window.confirm("some operation  proceed on selected vnf instance, go to monitor?")){
            				monitorService.monitor("KVM", status.lastaction, $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
            				$state.go('dashboard.monitor');
            			}
            		}else{
            			$scope.doUpgrade();
            		}
        });
		
    $scope.doUpgrade = function (){
        KVMService.upgradeOVM($scope.installConfig).then( function(){
            	monitorService.monitor("KVM", "UPGRADE", $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
             	$state.go("dashboard.monitor");
    	});
    };
        
        
}
	
}).controller('qosacupgradectr', function($scope, $filter,  $log, KVMService,  
		monitorService, DashboardService, $dialogs, $state) {
	
	$scope.stepNo = 1;
	
	$scope.setDefaultInstace = function(){
    	var selectedKVMInstance = DashboardService.getSelectedInstance();
    	if(selectedKVMInstance == null){
    		return;
    	}
        $scope.installConfig = $scope.com_instance;
        for(var inst in $scope.kvmcomInstance){
        		var com_config = JSON3.parse($scope.kvmcomInstance[inst].comConfig);
        		if(angular.equals(com_config,selectedKVMInstance)){
        		   $scope.com_instance = $scope.kvmcomInstance[inst];
        		   $scope.kvmcomInstance = [];
        		   $scope.kvmcomInstance.push($scope.com_instance);
        		   $scope.installConfig = com_config;
        		   break;
        		}
        }
  
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
        $scope.reloadinstallconfig();
    };
	
	KVMService.getComInstance().then( function(data) {
		$log.info(data);
		$scope.comInstance = data;
		$scope.kvmcomInstance = [];
		for(var ci=0;ci<$scope.comInstance.length;ci++){
			if(JSON3.parse($scope.comInstance[ci].comConfig).environment ==  "KVM"){
				if(JSON3.parse($scope.comInstance[ci].comConfig).comType == "QOSAC" ||
				     JSON3.parse($scope.comInstance[ci].comConfig).comType == "HPSIM"){
					$scope.kvmcomInstance.push($scope.comInstance[ci]);
				}
			}
		}
		$scope.setDefaultInstace();
		
    });
    var default_app_install_options = {
			BACKUP_SERVER_DISK_SPACE:'2000',
			NTP_SERVER:'COM_LOCAL_CLOCK',
			SEC_UNIX_ENABLE:'YES',
			OMCCN_SUPPORT_SP_FM:'YES',
			OMCCN_SUPPORT_SP_PM:'YES',
			OMCCN_SUPPORT_SP_HVP:'NO',
			BACKUP_SERVER_IS_LOCAL:'YES',
			SOFTWARE_SERVER_IS_LOCAL:'YES'
	};
    $scope.reloadinstallconfig = function(){
    	if($scope.com_instance != null){
        	$scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
    	}
    	if($scope.installConfig.app_install_options){
    		for(var attr in default_app_install_options){
        		if(!$scope.installConfig.app_install_options[attr]){
        			$scope.installConfig.app_install_options[attr] = default_app_install_options[attr];
        		}
    		}
    	}else{
    		$scope.installConfig.app_install_options = default_app_install_options;
    		$scope.installConfig.app_install_options.SOFTWARE_SERVER_ADDRESS = $scope.installConfig.vm_config.ovm.ip_address;
            $scope.installConfig.app_install_options.BACKUP_SERVER_ADDRESS = $scope.installConfig.vm_config.ovm.ip_address;
    	}
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
        $scope.loadimglist($scope.installConfig.active_host_ip, $scope.installConfig.vm_img_dir);
    }; 

	$scope.loadimglist = function(host, dir) {
		KVMService.imagelist({
			"host" : host,
			"dir" : dir
		}).then(function(data) {
			$scope.imagelist = data;
	    });
	};
	
	$scope.upgrade = function(){
		KVMService.comstackStatus($scope.installConfig.deployment_prefix).then(function(status){
            		var action_in_progress = 2;
            		if(status.state == action_in_progress){
            			if(window.confirm("some operation  proceed on selected vnf instance, go to monitor?")){
            				monitorService.monitor("KVM", status.lastaction, $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
            				$state.go('dashboard.monitor');
            			}
            		}else{
            			$scope.doUpgrade();
            		}
        });
		
    $scope.doUpgrade = function (){
        KVMService.upgradeOVM($scope.installConfig).then( function(){
            	monitorService.monitor("KVM", "UPGRADE", $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
             	$state.go("dashboard.monitor");
    	});
    };
        
        
}
	
});