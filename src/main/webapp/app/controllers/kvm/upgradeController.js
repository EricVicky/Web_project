angular.module('kvm').controller('upgradectr', function($scope,  $log, KVMService
		,  monitorService, $dialogs, $state) {
	
	$scope.loadimglist = function(host, dir){
           KVMService.imagelist({ "host":host, "dir":dir}).then(
            	function(data) {
            			$scope.imagelist = data;
            			$scope.installConfig.oam_cm_image = $scope.imagelist[0];
            			$scope.installConfig.db_image = $scope.imagelist[1];
            	}); 
    };
    $scope.reloadimglist = function(){
    	if($scope.com_instance != null){
        	$scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
    	}
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
    	$scope.loadimglist($scope.installConfig.active_host_ip, $scope.vm_img_dir);
    }
	$scope.doUpgrade = function (){
		var installConfig = JSON3.parse($scope.com_instance.comConfig);
		installConfig.oam_cm_image = $scope.oam_cm_image;
		installConfig.db_image = $scope.db_image;
		installConfig.vm_img_dir = $scope.vm_img_dir;
		KVMService.upgrade(
         		$scope.installConfig,
    			function(data){
            			monitorService.monitorKVMUpgrade($scope.installConfig.active_host_ip);
                 		$state.go("dashboard.monitor");
    			}, 
    			function(response){
    					$log.info(response);
    			});
    };
    KVMService.getComInstance().then( function(data) {
    				$log.info(data);
    				$scope.comInstance = data;
    });
    
    $scope.upgrade = function(){
    	            KVMService.isLockedHost($scope.installConfig.active_host_ip).then(function(response){
            		//if the host is locked, then ask
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
    
    $scope.deleteimglist = function(){
    	if($scope.del_com_instance != null){
        	$scope.deleteConfig = JSON3.parse($scope.del_com_instance.comConfig);
    	}
    }
    
    $scope.deletecom = function(){
    	var deleteConfig = JSON3.parse($scope.del_com_instance.comConfig);
		KVMService.deletecom(
         		$scope.deleteConfig,
    			function(data){
            			
    			}, 
    			function(response){
    					$log.info(response);
    			});
    }

} );


