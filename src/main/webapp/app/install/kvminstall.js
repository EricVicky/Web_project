var app = angular.module('kvminstall', [ 'ui.router', 'ui.bootstrap', 'rcWizard',
		'rcForm', 'rest', 'websocket', 'ghiscoding.validation']);

app.controller('kvmctr', function($scope, $q, $timeout, $log, KVMService, websocketService, validationService) {
			var logviewer = $('#logviewer');
			$scope.user = {};
			//$scope.installConfig.vm_config.oam.ip_address = "";
			$scope.submitComtype = function(){
				$scope.loadimglist($scope.installConfig.active_host_ip.ip_address, $scope.installConfig.vm_img_dir);
			}
			$scope.imagelist= [];
			$scope.saveState = function() {
				var deferred = $q.defer();
				$timeout(function() { 
					deferred.resolve();
				}, 1);
				return deferred.promise;
			};
			$scope.completeWizard = function() {
				$scope.deploy();
				alert('Completed!');
			}
			$scope.support_ars = [ 'True', 'False' ];
            $scope.installConfig ={
            		deployment_prefix: "sun",
            		vm_img_dir:"/var/images",
            		vm_config: {
            		  oam:{
            		    ip_address: "10.223.0.50",
            		    netmask: "255.255.255.240",
            		    gateway: "10.223.0.62",
            		  },
            		  db:{
            		    ip_address: "10.223.0.54",
            		    netmask: "255.255.255.240",
            		    gateway: "10.223.0.62",
            		  },
            		  cm:{
            		    ip_address: "135.251.236.105",
            		    netmask: "255.255.255.240",
            		    gateway: "135.251.236.110",
            		  }
            		}

            };
            
            $scope.logtail = function(data){
        		$scope.socket = websocketService.connect("/oam", function(socket) {
        			socket.stomp.subscribe('/log/tail', $scope.showlog);
        		})
            }
            
            $scope.showlog= function(data){
            	$log.info(data);
            	logviewer.append(data.body + "\n");
                logviewer.css({ display: "block" });
            }
			$scope.deploy = function (){
            	KVMService.deploy(
                 		$scope.installConfig,
            			function(data){
                 			$scope.logtail(data);
            			}, 
            			function(response){
            					$log.info(response);
            			});
            };
            $scope.loadimglist = function(host, dir){
            	KVMService.imagelist(
            			{ "host":host, "dir":dir},
            			function(data) {
            				$log.info(data);
            				$scope.imagelist = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            };
			(function (){
            	KVMService.getFlavorStore(
            			function(data) {
            				$scope.flavorStore = data.Flavors;
            			}, 
            			function(response){
            				$log.error(response);
            			});
            })();
            (function (){
            	KVMService.getComTypeStore(
            			function(data) {
            				$scope.comTypeStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	KVMService.getTimezoneStore(
            			function(data) {
            				$scope.timezoneStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	KVMService.hostips(
            			function(data) {
            				$log.info(data);
            				$scope.hostIPs = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
} );

app.controller('upgradectr', function($scope, $q, $timeout, $log, KVMService, websocketService, validationService) {
	var logviewer = $('#logviewer');
	$scope.user = {};
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
    	installConfig = JSON3.parse($scope.com_instance.comConfig);
        $scope.vm_img_dir = installConfig.vm_img_dir;
    	$scope.loadimglist(installConfig.active_host_ip, $scope.vm_img_dir);
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
