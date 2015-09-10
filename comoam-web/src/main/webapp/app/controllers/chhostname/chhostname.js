angular.module('chhostname',[]).controller('chhostnamectr', function($scope,KVMService,OSService,monitorService,$state){
	
    KVMService.getComInstance().then( function(data) {
		$scope.Instance = data;
		$scope.comInstance = [];
		for(var ci=0;ci<$scope.Instance.length;ci++){
			$scope.Instance[ci].comConfig = JSON3.parse($scope.Instance[ci].comConfig);
			if($scope.Instance[ci].comType == 'HPSIM'||$scope.Instance[ci].comType == 'ATC'){
				continue;
			}else{
				$scope.comInstance.push($scope.Instance[ci]);
			}
		}
		//$scope.setDefaultInstace();
		
    });
    
    $scope.initconfig = function(){
    	$scope.installConfig = $scope.com_instance.comConfig;
    	if($scope.installConfig.comType == 'FCAPS'||$scope.installConfig.comType == 'CM'){
    		$scope.installConfig.old_vm_config = {
    				"oam": { "old_hostname": $scope.installConfig.vm_config.oam.hostname},
    				"cm" : { "old_hostname": $scope.installConfig.vm_config.cm.hostname},
    				"db" : { "old_hostname": $scope.installConfig.vm_config.db.hostname}
    		};
    		$scope.new_oamhostname = $scope.installConfig.vm_config.oam.hostname;
    		$scope.new_dbhostname = $scope.installConfig.vm_config.db.hostname;
    		$scope.new_cmhostname = $scope.installConfig.vm_config.cm.hostname;
    	}else if($scope.installConfig.comType == 'OAM'){
    		$scope.installConfig.old_vm_config = {
    				"oam": { "old_hostname": $scope.installConfig.vm_config.oam.hostname},
    				"db" : { "old_hostname": $scope.installConfig.vm_config.db.hostname}
    		};
    		$scope.new_oamhostname = $scope.installConfig.vm_config.oam.hostname;
    		$scope.new_dbhostname = $scope.installConfig.vm_config.db.hostname;
    	}else{
    		$scope.installConfig.old_vm_config = {
    				"ovm": { "old_hostname": $scope.installConfig.vm_config.ovm.hostname}
    		};
    		$scope.new_ovmhostname = $scope.installConfig.vm_config.ovm.hostname;
    	}
    }; 
    
    $scope.changehostname = function(){
    	$scope.swapHostname();
    	if($scope.installConfig.environment == 'KVM'){
    		KVMService.chHostname($scope.installConfig).then( function(){
    			monitorService.monitor("KVM", "CHHOSTNAME", $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
         		$state.go("dashboard.monitor");
    		});
    	}else{
    		OSService.chHostname($scope.installConfig).then( function(){
                
    		});
    	}

    };
    
    $scope.swapHostname = function(){
    	if($scope.installConfig.comType == 'FCAPS'||$scope.installConfig.comType == 'CM'){
    		$scope.installConfig.vm_config.oam.hostname = $scope.new_oamhostname;
        	$scope.installConfig.vm_config.db.hostname = $scope.new_dbhostname;
        	$scope.installConfig.vm_config.cm.hostname = $scope.new_cmhostname;    		
    	}else if($scope.installConfig.comType == 'OAM'){
    		$scope.installConfig.vm_config.oam.hostname = $scope.new_oamhostname;
        	$scope.installConfig.vm_config.db.hostname = $scope.new_dbhostname;
    	}else{
    		$scope.installConfig.vm_config.ovm.hostname = $scope.new_ovmhostname;
    	}
    };
	

}); 