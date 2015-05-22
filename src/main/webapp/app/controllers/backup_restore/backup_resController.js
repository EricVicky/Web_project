angular.module('backup_restore', ['ui.router',
                                  'ui.bootstrap', 
                                  'dialogs',
                                  'rcWizard',
                                  'rcForm', 
                                  'ghiscoding.validation',
                                  'monitor',
                                  'ngResource']).controller('backup_resctr', function($scope,  $log, KVMService
		, Backup_ResService, monitorService, $dialogs, $state) {
    $scope.reloadimglist = function(){
    	if($scope.com_instance != null){
        	$scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
    	}
    }
    Backup_ResService.getComInstance().then( function(data) {
    				$log.info(data);
    				$scope.comInstance = data;
    });
    
    $scope.backup = function(){
    	$scope.backupConfig.config = $scope.installConfig;
    	if($scope.backupConfig.config.environment=='KVM'){
    		Backup_ResService.kvmbackup(
             		$scope.backupConfig,
        			function(data){
             			monitorService.monitorKVMBackup($scope.installConfig.active_host_ip);
             			$state.go("dashboard.monitor");
        			}, 
        			function(response){
        				$log.info(response);
        			});
    	}else{
    		Backup_ResService.osbackup(
             		$scope.backupConfig,
        			function(data){
             			monitorService.monitorOSBackup($scope.installConfig.stack_name);
             			$state.go("dashboard.monitor");
        			}, 
        			function(response){
        				$log.info(response);
        			});
    	}
    }
    
    $scope.restore = function(){
    	
    }


} );


