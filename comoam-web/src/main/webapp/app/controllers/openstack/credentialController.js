angular.module('os').controller('credentialsController', function($scope, $q, $timeout, $log, OSService,
		$state,validationService) {
		$scope.crendential = {
				osUsername : "",
				osPassword : "",
				osTenant   : "",
				authURL    : "",
				osDomainName :"",
				osRegion:""
		};
		$scope.submit = function(){
			OSService.updateOSCred($scope.crendential).then(function (data){
				$log.info(data);
			});
			alert("Success!Openstack_config has been deployed!");
		};
		OSService.getUpdateOSCred().then(function(data) {
			$scope.crendential = data;
		});
} );
