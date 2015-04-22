angular.module('kvm').controller('upgradectr', function($scope, $q, $timeout, $log, KVMService
		, websocketService, validationService, WizardHandler) {
    $scope.ansibleSteps = ["Start", "Data Backup", "Post Image Replacement", "Post Configuration", "Data Restore", "Finished"];
	$scope.editing = true;
	$scope.loadingshow = true;
	$scope.saveState = function() {
		var deferred = $q.defer();
		$timeout(function() { 
			deferred.resolve();
		}, 1);
		return deferred.promise;
	};
	$scope.completeWizard = function() {
		$scope.upgrade();
	};
	
	$scope.loadimglist = function(host, dir){
           KVMService.imagelist({ "host":host, "dir":dir}).then(
            	function(data) {
            			$log.info(data);
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
    	$scope.loadimglist($scope.installConfig.active_host_ip.ip_address, $scope.vm_img_dir);
    }
	$scope.upgrade = function (){
		var installConfig = JSON3.parse($scope.com_instance.comConfig);
		installConfig.oam_cm_image = $scope.oam_cm_image;
		installConfig.db_image = $scope.db_image;
		installConfig.vm_img_dir = $scope.vm_img_dir;
		KVMService.upgrade(
         		$scope.installConfig,
    			function(data){
         			$scope.editing = false;
    			}, 
    			function(response){
    					$log.info(response);
    			});
    };
    KVMService.getComInstance().then( function(data) {
    				$log.info(data);
    				$scope.comInstance = data;
    });

} );


