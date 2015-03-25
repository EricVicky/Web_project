var app = angular.module('osinstall', [ 'ui.router', 'ui.bootstrap', 'rcWizard',
		'rcForm', 'rest' ]);

app.controller('osctr', function($scope, $q, $timeout, $log, OSService) {
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
            $scope.ahostIP="IPV4/IPV6";
            $scope.shostIP="IPV4/IPV6";
            $scope.com_types = [ 'FCAPS', 'QOSAC', 'CM' ,'OAM' ];
            $scope.gr_options = ['True' , 'False' ];
            $scope.deploy = function (){
            	OSService.deploy(
            			$scope.installConfig,
            			function(data){
            				$log.info(data);
            				}, 
            			function(response){
            					$log.info(response);
            					});
            };
            (function (comType, vm){
            	OSService.getFlavorStore(
            			function(data) {
            				$log.info(data);
            				$scope.flavorStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	OSService.getSecurityGroupStore(
            			function(data) {
            				$log.info(data);
            				$scope.securityGroup = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	OSService.getComTypeStore(
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
            	OSService.getTimezoneStore(
            			function(data) {
            				$log.info(data);
            				$scope.timezoneStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	OSService.getNetworkStore(
            			function(data) {
            				$log.info(data);
            				$scope.networkStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	OSService.getSubNetworkStore(
            			function(data) {
            				$log.info(data);
            				$scope.subNetworkStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	OSService.getComputeAvailZoneStore(
            			function(data) {
            				$log.info(data);
            				$scope.computeAvailZoneStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
} );
