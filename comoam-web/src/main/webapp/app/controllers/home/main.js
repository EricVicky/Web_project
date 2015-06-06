'use strict';
/**
 * @ngdoc function
 * @name comoamApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the comoamApp
 */
angular.module('comoamApp')
  .controller('MainCtrl', function($log, $scope,$position, KVMService, OSService, monitorService, DashboardService, $state, $modal) {
	  
	  $scope.goupgraqde = function(){
		  DashboardService.setUpgradeInstance($scope.selectedIns);
		  $state.go("dashboard.kvmupgrade");
	  }
	  
	  KVMService.getComInstance().then( function(data) {
			$log.info(data);
			$scope.delcomInstance = data;
	  });
	  $scope.deletecomlist = function(){
		  if($scope.del_com_instance != null){
			  $scope.Config = JSON3.parse($scope.del_com_instance.comConfig);
		  }
	  } 
	  $scope.animationsEnabled = true;

	  $scope.open = function (size) {

		  var modalInstance = $modal.open({
		      animation: $scope.animationsEnabled,
		      templateUrl: 'views/common/deleteComInsModal.html',
		      controller: 'deleteComController',
		      size: size,
		      backdrop: true,
		      resolve: {
		    	  selectedIns: function () {
		    		  return $scope.selectedIns;
		    	  }
		      }
		  });
	
	      modalInstance.result.then(function (selectedItem) {
	        $scope.selectedIns = selectedItem;
	      }, function () {
	        $log.info('Modal dismissed at: ' + new Date());
	      });
	  }
  })
  .controller('deleteComController', function($scope, $modalInstance, selectedIns, KVMService, OSService, monitorService, $state){
	  $scope.deletecom = function(){
		  if($scope.selectedIns.environment == "KVM"){
			  KVMService.deletecom($scope.selectedIns).then( function(){
				  monitorService.monitorKVMDelete($scope.selectedIns.active_host_ip);
	       		  $state.go("dashboard.monitor");
			  });
		  }else{
			  OSService.deletecom($scope.selectedIns).then( function(){
				  monitorService.monitorOSDelete($scope.selectedIns.stack_name);
	       		  $state.go("dashboard.monitor");
			  });
		  }  
	  }
	 $scope.selectedIns = selectedIns;
	 $scope.ok = function(){
		 $scope.deletecom();
		 $modalInstance.close($scope.selectedIns);
	 };
	 $scope.cancel = function () {
		 $modalInstance.dismiss('cancel');
     };
});
