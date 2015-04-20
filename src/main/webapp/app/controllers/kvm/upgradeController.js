angular.module('kvm').controller('upgradectr', function($scope, $q, $timeout, $log, KVMService, websocketService, validationService) {
	var logviewer = $('#logviewer');
	$scope.user = {};
	$scope.editing = false;
	$scope.saveState = function() {
		var deferred = $q.defer();
		$timeout(function() { 
			deferred.resolve();
		}, 1);
		return deferred.promise;
	};
	$scope.completeWizard = function() {
		$scope.upgrade();
		alert('Completed!');
	};
    $scope.logtail = function(data){
		$scope.socket = websocketService.connect("/oam", function(socket) {
			socket.stomp.subscribe('/log/tail', $scope.showlog);
		})
    };
    $scope.showlog= function(data){
    	$log.info(data);
    	logviewer.append(data.body + "\n");
        logviewer.css({ display: "block" });
    };
	$scope.loadimglist = function(host, dir) {
		KVMService.imagelist({
			"host" : host,
			"dir" : dir
		}, function(data) {
			$log.info(data);
			$scope.imagelist = data;
		}, function(response) {
			$log.error(response);
		});

	};
    $scope.reloadimglist = function(){
    	$scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
    	$scope.loadimglist($scope.installConfig.active_host_ip.ip_address, $scope.vm_img_dir);
    }
	$scope.upgrade = function (){
		var installConfig = JSON3.parse($scope.com_instance.comConfig);
		installConfig.oam_cm_image = $scope.oam_cm_image;
		installConfig.db_image = $scope.db_image;
		installConfig.com_iso = $scope.com_iso;
		installConfig.vm_img_dir = $scope.vm_img_dir;
		KVMService.upgrade(
         		$scope.installConfig,
    			function(data){
         			$scope.logtail(data);
    			}, 
    			function(response){
    					$log.info(response);
    			});
    };
    (function (){
    	KVMService.getComInstance(
    			function(data) {
    				$log.info(data);
    				$scope.comInstance = data;
    			}, 
    			function(response){
    				$log.error(response);
    			}
    	);
    })();
} );


