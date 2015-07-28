angular.module('os').controller('osupgradectr', function($scope, $filter,  $log 
		,  OSService, monitorService, $dialogs, $state , DashboardService) {
    OSService.getImages().then(function(data){
            	$scope.oam_cm_images = data;
            	$scope.db_images = data;
            });
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
			INSTALL_ETHEREAL:'YES'

	};
    $scope.setDefaultInstace = function(){
    	var selectedOSInstance = DashboardService.getSelectedInstance();
    	if(selectedOSInstance == null){
    		return;
    	}
        $scope.installConfig = $scope.com_instance;
        for(var inst in $scope.oscomInstance){
    		var com_config = JSON3.parse($scope.oscomInstance[inst].comConfig);
    		if(angular.equals(com_config,selectedOSInstance)){
    		   $scope.com_instance = $scope.oscomInstance[inst];
    		   $scope.installConfig = com_config;
    		   return;
    		}
        }
    }
    
    OSService.getComInstance().then( function(data) {
		$log.info(data);
		$scope.comInstance = data;
		$scope.oscomInstance = [];
		for(var ci=0;ci<$scope.comInstance.length;ci++){
			if(JSON3.parse($scope.comInstance[ci].comConfig).environment ==  "OPENSTACK"){
				$scope.oscomInstance.push($scope.comInstance[ci]);
			}
		}
		$scope.setDefaultInstace();
    });
    $scope.showInstance = function(){
           $scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
		   for(var attr in default_app_install_options){
          		if(!$scope.installConfig.app_install_options[attr]){
          			$scope.installConfig.app_install_options[attr] = default_app_install_options[attr];
          		}
          	}
    }
    $scope.doUpgrade = function (){
    	OSService.upgrade($scope.installConfig).then( function(){
    		monitorService.monitorKVMUpgrade($scope.installConfig.stack_name);
     		$state.go("dashboard.monitor");
		});
    };

    $scope.upgrade = function(){
         $scope.doUpgrade();
    };

} );



