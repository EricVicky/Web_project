angular.module('backup_restore', ['ui.router',
                                  'ui.bootstrap', 
                                  'dialogs',
                                  'rcWizard',
                                  'rcForm', 
                                  'ghiscoding.validation',
                                  'monitor',
                                  'ngResource']).controller('backup_resctr', function($scope,  $log, KVMService
		, Backup_ResService, monitorService, $dialogs, $state) {
	var backupConfig = {};
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
    	backupConfig.config = $scope.installConfig;
    	backupConfig.dir = $scope.backupConfig.directory;
    	backupConfig.filename = $scope.backupConfig.filename;
    	Backup_ResService.backup(
         		backupConfig,
    			function(data){
         			monitorService.monitorKVMBackup($scope.installConfig.active_host_ip);
         			$state.go("dashboard.monitor");
    			}, 
    			function(response){
    				$log.info(response);
    			});
    }
    
    $scope.restore = function(){
    	
    }


} );


