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
		  DashboardService.setSelectedInstance($scope.selectedIns);
		  if($scope.selectedIns.environment == "KVM"){
			  if ($scope.selectedIns.comType == "QOSAC") {
				  $state.go("dashboard.kvmovmupgrade");
			  }else{
				  $state.go("dashboard.kvmupgrade");	
			  }
		  }else{
			  if($scope.selectedIns.comType == "QOSAC"){
				  $state.go("dashboard.osqosacupgrade");
			  }else{
				  $state.go("dashboard.osupgrade");
			  }
		  }
	  };
	  
	  $scope.operationlog = function(){
		  DashboardService.setSelectedInstance($scope.selectedIns);
		  $state.go("dashboard.operationlog");
	  };
	  
	  $scope.gobackup = function(){
		  DashboardService.setSelectedInstance($scope.selectedIns);
		  $state.go("dashboard.backup");			  
	  };
	  
	  $scope.gorestore = function(){
		  DashboardService.setSelectedInstance($scope.selectedIns);
		  $state.go("dashboard.restore");			  
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

	  $scope.open = function (action) {
		  var selectedInsModal = action + $scope.selectedIns.environment + 'InsModal';
		  var modalInstance = $modal.open({
		      animation: $scope.animationsEnabled,
		      templateUrl: 'views/common/' + selectedInsModal + '.html',
		      controller: 'deleteComController',
		      backdrop: true,
		      resolve: {
		    	  selectedIns: function () {
		    		  return $scope.selectedIns;
		    	  }
		      }
		  }); 
	  }
  })
  .controller('deleteComController', function($scope, $modalInstance, selectedIns, KVMService, OSService, monitorService, $state){
	  $scope.deletecom = function(){
		  if($scope.selectedIns.environment == "KVM"){
			  KVMService.deletecom($scope.selectedIns).then( function(){
				  monitorService.monitorKVMDelete($scope.selectedIns.deployment_prefix, $scope.selectedIns.comType);
	       		  $state.go("dashboard.monitor");
			  });
		  }else{
			  if($scope.selectedIns.comType=='QOSAC' || $scope.selectedIns.comType =='HPSIM' || $scope.selectedIns.comType=='ATC'){
				  OSService.deleteovm($scope.selectedIns).then( function(){
					  monitorService.monitor("Openstack","DELETE",$scope.selectedIns.comType,$scope.selectedIns.stack_name);
					  $state.go("dashboard.monitor");
				  });
			  }else{
				  OSService.deletecom($scope.selectedIns).then( function(){
					  monitorService.monitor("Openstack","DELETE",$scope.selectedIns.comType,$scope.selectedIns.stack_name);
					  $state.go("dashboard.monitor");
				  });
			  }
		  }  
	  }
	 $scope.selectedIns = selectedIns;
	 if($scope.selectedIns.environment == "KVM"){
		 if($scope.selectedIns.comType=='FCAPS'||$scope.selectedIns.comType=='OAM'||$scope.selectedIns.comType=='CM'){
			 $scope.oamRowspan = $scope.selectedIns.vm_config.oam.nic.length * 2 + 2;
		 	 $scope.dbRowspan = $scope.selectedIns.vm_config.db.nic.length * 2 + 2;
		 	 if($scope.selectedIns.comType != "OAM"){
		 		 $scope.cmRowspan = $scope.selectedIns.vm_config.cm.nic.length * 2 + 2;                		
		 	 }
		 }
	 }
	 
	 $scope.ok = function(){
		 $scope.deletecom();
		 $modalInstance.close($scope.selectedIns);
	 };
	 $scope.cancel = function () {
		 $modalInstance.dismiss('cancel');
     };
});

