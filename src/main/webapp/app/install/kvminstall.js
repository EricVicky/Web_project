var app = angular.module('kvminstall', [ 'ui.router', 'ui.bootstrap', 'rcWizard',
		'rcForm', 'rest', 'websocket' ]);

app.controller('kvmctr', function($scope, $q, $timeout, $log, KVMService, websocketService) {
			var logviewer = $('#logviewer');
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
			var count=0;

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
            
            $scope.logtail = function(data){
        		$scope.socket = websocketService.connect("/oam", function(socket) {
        			socket.stomp.subscribe('/log/tail', $scope.showlog);
        		})
            }
            
            $scope.showlog= function(data){
            	$log.info(data);
            	logviewer.append(data.body + "\n");
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
} );

