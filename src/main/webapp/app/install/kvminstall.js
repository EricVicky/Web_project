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
            $scope.oamcm_images = [ 'Redhat+orac_client' ,'Redhat+orac_server'];
            $scope.db_images = [ 'Redhat+orac_client', 'Redhat+orac_server'];
            $scope.installConfig ={};
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
