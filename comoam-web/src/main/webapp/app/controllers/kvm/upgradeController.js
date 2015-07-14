angular.module('kvm').controller('upgradectr', function($scope, $filter,  $log, KVMService
		,  monitorService, DashboardService, $dialogs, $state) {
	
	$scope.loadimglist = function(host, dir){
        	KVMService.imagelist({ "host":host, "dir":dir}).then(
                	function(data) {
                			$scope.imagelist = data;
                	});
    };
    var default_app_install_options = {
			BACKUP_SERVER_DISK_SPACE:'2000',
			CALL_TRACE_DISK_SPACE:'1000',
			CODE_SERVER_DISK_SPACE:'2000',
			OMCCN_SUPPORT_WEBSSO_SANE:'false',
			NTP_SERVER:'COM_LOCAL_CLOCK',
			SEC_UNIX_ENABLE:'YES',
			OMCCN_SUPPORT_COM_GR:'false',
			OMCCN_SUPPORT_SP_FM:'YES',
			OMCCN_SUPPORT_SP_PM:'YES',
			OMCCN_SUPPORT_SP_HVP:'NO',
			BACKUP_SERVER_IS_LOCAL:'YES',
			SOFTWARE_SERVER_IS_LOCAL:'YES',
			OMCCN_SUPPORT_3GPP:'true',
			OMCCN_SUPPORT_SNMP_N_ITF:'true',
			OMCCN_SUPPORT_GSST:'false',
			OMCCN_SUPPORT_NETRA:'false',
			INSTALL_ETHEREAL:'NO'

	};
    $scope.reloadimglist = function(){
    	if($scope.com_instance != null){
    		$scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
        	$scope.oamRowspan = $scope.installConfig.vm_config.oam.nic.length * 2 + 2;
        	$scope.dbRowspan = $scope.installConfig.vm_config.db.nic.length * 2 + 2;
        	if($scope.installConfig.comType != "OAM"){
        		$scope.cmRowspan = $scope.installConfig.vm_config.cm.nic.length * 2 + 2;
        	}
        	//set default value if not set
        	for(var attr in default_app_install_options){
        		if(!$scope.installConfig.app_install_options[attr]){
        			$scope.installConfig.app_install_options[attr] = default_app_install_options[attr];
        		}
        	}
    	}
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
    	$scope.loadimglist($scope.installConfig.active_host_ip, $scope.vm_img_dir);
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
        $scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
    	$scope.oamRowspan = $scope.installConfig.vm_config.oam.nic.length * 2 + 2;
    	$scope.dbRowspan = $scope.installConfig.vm_config.db.nic.length * 2 + 2;
    	if($scope.installConfig.comType != "OAM"){
    		$scope.cmRowspan = $scope.installConfig.vm_config.cm.nic.length * 2 + 2;
    	}
    	
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
    	$scope.loadimglist($scope.installConfig.active_host_ip, $scope.vm_img_dir);
    }
   
	$scope.doUpgrade = function (){
		KVMService.upgrade($scope.installConfig).then( function(){
            monitorService.monitor("KVM", "UPGRADE", $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
     		$state.go("dashboard.monitor");
		});
    };
    
    KVMService.getComInstance().then( function(data) {
		$log.info(data);
		$scope.comInstance = data;
		$scope.kvmcomInstance = [];
		for(var ci=0;ci<$scope.comInstance.length;ci++){
			if(JSON3.parse($scope.comInstance[ci].comConfig).environment ==  'KVM'){
				if($scope.comInstance[ci].comType == 'OAM'||$scope.comInstance[ci].comType == 'FCAPS'||$scope.comInstance[ci].comType == 'CM'){
					$scope.kvmcomInstance.push($scope.comInstance[ci]);
				}
			}
		}
		$scope.setDefaultInstace();
		
    });
    
    $scope.upgrade = function(){
            	KVMService.comstackStatus($scope.installConfig.deployment_prefix).then(function(status){
            		var ACTION_IN_PROGRESS = 2;
            		if(status.state == ACTION_IN_PROGRESS){
            			if(window.confirm("some operation  proceed on selected VNF instance, go to monitor?")){
            				monitorService.monitor("KVM", status.lastaction, $scope.installConfig.comType, $scope.installConfig.deployment_prefix);
            				$state.go('dashboard.monitor');
            			}
            		}else{
            			$scope.doUpgrade();
            		}
            	});
    }
} );


