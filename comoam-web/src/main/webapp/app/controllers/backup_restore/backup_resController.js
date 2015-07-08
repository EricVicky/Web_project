angular.module('backup_restore', ['ui.router',
                                  'ui.bootstrap', 
                                  'dialogs',
                                  'rcWizard',
                                  'rcForm', 
                                  'ghiscoding.validation',
                                  'monitor',
                                  'ngResource']).controller('backup_resctr', function($scope,  $log, KVMService
		, Backup_ResService, monitorService,DashboardService, $dialogs, $state,$translate) {
    $scope.reloadimglist = function(){
    	if($scope.com_instance != null){
        	$scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
        	$scope.oamRowspan = $scope.installConfig.vm_config.oam.nic.length * 2 + 2;
        	$scope.dbRowspan = $scope.installConfig.vm_config.db.nic.length * 2 + 2;
        	$scope.cmRowspan = $scope.installConfig.vm_config.cm.nic.length * 2 + 2;
    	}
    }
    
    $scope.setDefaultInstace = function(){
    	var selectedInstance = DashboardService.getSelectedInstance();
    	if(selectedInstance == null){
    		return;
    	}
        $scope.installConfig = $scope.com_instance;
        for(var inst in $scope.comInstance){
    		var com_config = JSON3.parse($scope.comInstance[inst].comConfig);
    		if(angular.equals(com_config,selectedInstance)){
    		   $scope.com_instance = $scope.comInstance[inst];
    		   $scope.installConfig = com_config;
    		   return;
    		}
        }
    }
    
    Backup_ResService.getComInstance().then( function(data) {
		$log.info(data);
		$scope.comInstance = data;
		$scope.setDefaultInstace();
    });
    $scope.backup = function(){
    	$scope.backupConfig.config = $scope.installConfig;
    	if($scope.backupConfig.config.environment=='KVM'){
    		Backup_ResService.kvmbackup($scope.backupConfig).then( function(){
    			monitorService.monitorKVMBackup($scope.installConfig.active_host_ip);
             	$state.go("dashboard.monitor");
    		});
    	}else{
    		Backup_ResService.osbackup($scope.backupConfig).then( function(){
    			monitorService.monitorOSBackup($scope.installConfig.stack_name);
     			$state.go("dashboard.monitor");
    		});
    	}
    }
    $scope.restore = function(){
    	$scope.backupConfig.config = $scope.installConfig;
    	if($scope.backupConfig.config.environment=='KVM'){
    		Backup_ResService.kvmrestore($scope.backupConfig).then( function(){
    			monitorService.monitorKVMRestore($scope.installConfig.active_host_ip);
             	$state.go("dashboard.monitor");
    		});
    	}else{
    		Backup_ResService.osrestore($scope.backupConfig).then( function(){
    			monitorService.monitorOSRestore($scope.installConfig.stack_name);
     			$state.go("dashboard.monitor");
    		});
    	}
    }
} );


