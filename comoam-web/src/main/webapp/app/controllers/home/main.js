'use strict';
/**
 * @ngdoc function
 * @name comoamApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the comoamApp
 */
angular.module('comoamApp')
  .controller('MainCtrl', function($log, $scope,$position, KVMService, OSService, monitorService, DashboardService, $state) {
	  
	  $scope.goupgraqde = function(){
		  DashboardService.setUpgradeInstance($scope.selectedIns);
		  //$state.go("dashboard.kvmupgrade");
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
	    
	  $scope.deletecom = function(){
		  //var Config = JSON3.parse($scope.del_com_instance.comConfig);
		  if($scope.Config.environment == "KVM"){
			  KVMService.deletecom(
				$scope.Config,
				function(data){
					monitorService.monitorKVMDelete($scope.Config.active_host_ip);
             		$state.go("dashboard.monitor");
				},
				function(response){
					$log.info(response);
				}
			  );
		  }else{
			  OSService.deletecom(
				$scope.Config,
				function(data){
					monitorService.monitorOSDelete($scope.Config.stack_name);
             		$state.go("dashboard.monitor");
				},
				function(response){
					$log.info(response);
				}
			  );
		  }
		  
	  }
	  
  });
