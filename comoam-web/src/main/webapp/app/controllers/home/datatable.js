angular.module('datatable',['ui.grid', 'ui.grid.resizeColumns']).controller('datatablectr', function($scope,KVMService,$modal,DashboardService, $state){
	
	KVMService.getComInstance().then( function(data) {
		$scope.comInstance = data;
		for(var ci=0;ci<$scope.comInstance.length;ci++){
			$scope.comInstance[ci].comConfig = JSON3.parse($scope.comInstance[ci].comConfig);
		}
		
    });
	
	$scope.comGridOptions = {
			data: 'comInstance',
			columnDefs: [
			             {name: 'ComName',  field: 'name',
			            	 width:150
			             },
			             {name: 'VnfType',  field: 'comType',
			            	 enableColumnMenu: false,
			            	 width:100
			             },
                         {name: 'Environment',  field: 'comConfig.environment',
                        	 enableFiltering: false,
                        	 enableColumnMenu: false,
                        	 width:110
                         },
                         {name: 'Host Server',  field: 'comConfig.active_host_ip',
                        	 enableFiltering: false,
                        	 enableColumnMenu: false,
                        	 width:120
                         },
//                         {name: 'Oam Vm',  field: '',
//                        	 enableFiltering: false,
//                        	 enableColumnMenu: false,
//                        	 width:120
//                         },
//                         {name: 'Db Vm',  field: '',
//                        	 enableFiltering: false,
//                        	 enableColumnMenu: false,
//                        	 width:120
//                         },
//                         {name: 'Cm Vm',  field: '',
//                        	 enableFiltering: false,
//                        	 enableColumnMenu: false,
//                        	 width:120
//                         },
                         {name: 'Timezone',  field: 'comConfig.timezone',
                        	 enableFiltering: false,
                        	 enableColumnMenu: false,
                        	 width:120
                         },
                         {name: 'Status',  field: '',
                        	 cellTemplate: 'views/dashboard/status.html',
                        	 enableFiltering: false,
                        	 enableColumnMenu: false,
                        	 width:200
                         },
                         {name: 'operationMenu', field: '', 
                        	 cellTemplate: 'views/dashboard/operatebutton.html',
                        	 enableColumnMenu: false,
                        	 enableFiltering: false,
                        	 enableSorting: false,
                        	 enableColumnResizing: false
                         }
                        ],
            enableGridMenu: true,
            enableRowSelection: true,
            enableSelectAll: true,
            multiSelect: true,
            enableFiltering: true,
            onRegisterApi: function( gridApi ) { 
                $scope.gridApi = gridApi;
            }
	};
	
	$scope.details = function(row){
		  var selectedInsModal = "details" + row.entity.comConfig.environment + 'InsModal';
		  for (var index in $scope.comInstance){
			  if($scope.comInstance[index].name == row.entity.comConfig.stackName){
				  row.entity.comConfig['actionResult'] = $scope.comInstance[index].actionResult;
				  break;
			  }
		  }
		  $scope.selectedIns = row.entity.comConfig;
		  var selectComstack = row.entity;
		  var modalInstance = $modal.open({
		      animation: $scope.animationsEnabled,
		      templateUrl: 'views/common/' + selectedInsModal + '.html',
		      controller: 'detailController',
		      backdrop: true,
		      resolve: {
		    	  selectedIns: function () {
		    		  return $scope.selectedIns;
		    	  },
		    	  selectComstack: function () {
		    		  return selectComstack;
		    	  }
		      }
		  });
		  modalInstance.result.then(function (item) {
      	
      	  });
	};
	
	$scope.operationlog = function(row){
		DashboardService.setSelectedInstance(row.entity.comConfig);
		$state.go("dashboard.operationlog");
	};
	
	$scope.goupgraqde = function(row){
		  DashboardService.setSelectedInstance(row.entity.comConfig);
		  if(row.entity.comConfig.environment == "KVM"){
			  if (row.entity.comConfig.comType == "QOSAC") {
				  $state.go("dashboard.kvmovmupgrade");
			  }else{
				  $state.go("dashboard.kvmupgrade");	
			  }
		  }else{
			  if(row.entity.comConfig.comType == "QOSAC"){
				  $state.go("dashboard.osqosacupgrade");
			  }else{
				  $state.go("dashboard.osupgrade");
			  }
		  }
	  };
	  
	  $scope.gobackup = function(row){
		  DashboardService.setSelectedInstance(row.entity.comConfig);
		  $state.go("dashboard.backup");			  
	  };
	  
	  $scope.gorestore = function(row){
		  DashboardService.setSelectedInstance(row.entity.comConfig);
		  $state.go("dashboard.restore");			  
	  };
	  
	  $scope.gochhostname = function(row){
		  DashboardService.setSelectedInstance(row.entity.comConfig);
		  $state.go("dashboard.chhostname");			  
	  };
	  
	  $scope.godelete = function(row){
		  $scope.selectedIns = row.entity.comConfig;
		  var selectedInsModal = 'delete' + row.entity.comConfig.environment + 'InsModal';
		  var modalInstance = $modal.open({
		      animation: $scope.animationsEnabled,
		      templateUrl: 'views/common/' + selectedInsModal + '.html',
		      controller: 'deleteController',
		      backdrop: true,
		      resolve: {
		    	  selectedIns: function () {
		    		  return $scope.selectedIns;
		    	  }
		      }
		  });
		  modalInstance.result.then(function (item) {
		      	
      	  });
	  };
	
	
}).controller('detailController', function($scope, $modalInstance, selectedIns, selectComstack, KVMService, OSService, monitorService, $state){
	 $scope.selectedIns = selectedIns;
     $scope.selectComstack = selectComstack;
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
		 $modalInstance.close($scope.selectedIns);
	 };
	 $scope.cancel = function () {
		 $modalInstance.dismiss('cancel');
     };
}).controller('deleteController', function($scope, $modalInstance, selectedIns, KVMService, OSService, monitorService, $state){
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
	  };
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