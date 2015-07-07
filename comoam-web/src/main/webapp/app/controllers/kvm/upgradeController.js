angular.module('kvm').controller('upgradectr', function($scope, $filter,  $log, KVMService
		,  monitorService, DashboardService, $dialogs, $state) {
	
	$scope.loadimglist = function(host, dir){
        	KVMService.imagelist({ "host":host, "dir":dir}).then(
                	function(data) {
                			$scope.imagelist = data;
                	});
    };
    $scope.reloadimglist = function(){
    	if($scope.com_instance != null){
        	$scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
    	}
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
    	$scope.loadimglist($scope.installConfig.active_host_ip, $scope.vm_img_dir);
    	$scope.oamRowspan = $scope.installConfig.vm_config.oam.nic.length * 2 + 2;
    	$scope.dbRowspan = $scope.installConfig.vm_config.db.nic.length * 2 + 2;
    	$scope.cmRowspan = $scope.installConfig.vm_config.cm.nic.length * 2 + 2;
    }
    
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
        		   break;
        		}
        }
  
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
    	$scope.loadimglist($scope.installConfig.active_host_ip, $scope.vm_img_dir);
    }
   
	$scope.doUpgrade = function (){
		KVMService.upgrade($scope.installConfig).then( function(){
			monitorService.monitorKVMUpgrade($scope.installConfig.active_host_ip);
     		$state.go("dashboard.monitor");
		});
    };
    KVMService.getComInstance().then( function(data) {
		$log.info(data);
		$scope.comInstance = data;
		$scope.kvmcomInstance = [];
		for(var ci=0;ci<$scope.comInstance.length;ci++){
			if(JSON3.parse($scope.comInstance[ci].comConfig).environment ==  "KVM"){
				$scope.kvmcomInstance.push($scope.comInstance[ci]);
			}
		}
		$scope.setDefaultInstace();
		
    });
    $scope.upgrade = function(){
    	            KVMService.isLockedHost($scope.installConfig.active_host_ip).then(function(response){
            		if(response.succeed == true){
            			locked = true;
            			if(window.confirm("The installation proceed on selected Host, go to monitor?")){
            				KVMService.lockedHostStatus($scope.installConfig.active_host_ip).then(function(status){
            					if(status.lastAction == 'INSTALL'){
            						monitorService.monitorKVMInstall($scope.installConfig.active_host_ip);
            					}else if(status.lastAction  =="UPGRADE"){
            						monitorService.monitorKVMUpgrade($scope.installConfig.active_host_ip);
            					}
            					$state.go('dashboard.monitor');
            				})
            			}
            		}else{
            			$scope.doUpgrade();
            		}
            	});
    }
} );


