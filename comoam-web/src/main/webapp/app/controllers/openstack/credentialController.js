angular.module('os').controller('credentialsController', function($scope, $q, $timeout, $log, OSService,
		$state,validationService,$modal) {
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
			var modalInstance = $modal.open({
      	      animation: true,
      	      templateUrl: 'views/os/confirm.html',
      	      controller: 'confirm',
			});
		};
		OSService.getUpdateOSCred().then(function(data) {
			$scope.crendential = data;
		});
} ).controller('confirm', function($scope, $modalInstance){
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
    };
});;
