var app = angular.module('kvminstall', [ 'ui.router', 'ui.bootstrap', 'rcWizard',
		'rcForm', 'rest' ]);

app.controller('kvmctr', function($scope, $q, $timeout, $log, KVMService) {
			$scope.user = {};
			$scope.saveState = function() {
				var deferred = $q.defer();
				$timeout(function() { 
					deferred.resolve();
				}, 1000);
				return deferred.promise;
			};
			$scope.completeWizard = function() {
				$scope.deploy();
				alert('Completed!');
			}
			$scope.support_ars = [ 'True', 'False' ];
            $scope.installConfig ={
            		deployment_prefix: "sun",
            		active_host_ip: "135.251.236.98",
            		vm_config: {
            		  oam:{
            		    ip_address: "10.223.0.50",
            		    flavor: {label: "medium(2*4*480)", vCpu: '2', memory: '3', disk: '480'},
            		    netmask: "255.255.255.240",
            		    gateway: "10.223.0.62",
            		  },
            		  db:{
            		    ip_address: "10.223.0.54",
            		    flavor: {label: "medium(2*4*480)", vCpu: '2', memory: '3', disk: '480'},
            		    netmask: "255.255.255.240",
            		    gateway: "10.223.0.62",
            		  },
            		  cm:{
            		    ip_address: "135.251.236.105",
            		    flavor: {label: "medium(2*4*480)", vCpu: '2', memory: '3', disk: '480'},
            		    netmask: "255.255.255.240",
            		    gateway: "135.251.236.110",
            		    hostname: "sun-cm-1"
            		  }
            		}

            };
			$scope.deploy = function (){
            	KVMService.deploy(
                 		$scope.installConfig,
            			function(data){
            				$log.info(data);
            			}, 
            			function(response){
            					$log.info(response);
            			});
            };
			(function (){
            	KVMService.getFlavorStore(
            			function(data) {
            				$log.info(data);
            				$scope.flavorStore = data.Flavors;
            			}, 
            			function(response){
            				$log.error(response);
            			});
            })();
            (function (){
            	KVMService.getComTypeStore(
            			function(data) {
            				$log.info(data);
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
            				$log.info(data);
            				$scope.timezoneStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
} );
