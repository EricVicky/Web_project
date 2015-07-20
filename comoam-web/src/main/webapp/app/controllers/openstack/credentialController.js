angular.module('os').controller('credentialsController', function($scope, $q, $timeout, $log, OSService,
		$state,validationService,$modal, Upload) {
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
				var modalInstance = $modal.open({
					animation: true,
					templateUrl: 'views/os/confirm.html',
					controller: 'confirm'
				});
			}, function(reason){
				alert("Import of certification failed!");
			});
		};
		OSService.getUpdateOSCred().then(function(data) {
			$scope.crendential = data;
		});
		
		$scope.isHTTPS = function(){
			return $scope.crendential.authURL && $scope.crendential.authURL.toLowerCase().indexOf("https") != -1
		};
		
		$scope.certNotUpload = function(){
            return $scope.isHTTPS() && !$scope.crendential.cert
		};
		
	    $scope.$watch('files', function () {
	        $scope.upload($scope.files);
	    });

	    $scope.upload = function (files) {
	        if (files && files.length) {
	            for (var i = 0; i < files.length; i++) {
	                var file = files[i];
	                Upload.upload({
	                    url: 'rest/upload/cert',
	                    file: file
	                }).progress(function (evt) {
	                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
	                    console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
	                }).success(function (data, status, headers, config) {
	                    $scope.crendential.cert = true;
	                }).error(function (data, status, headers, config) {
	                    console.log('error status: ' + status);
	                })
	            }
	        }
	    };
} ).controller('confirm', function($scope, $modalInstance){
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
    };
});
