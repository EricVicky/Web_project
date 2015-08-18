angular.module('operationlog',[]).controller('operationlogctr', function($scope,operationlogService,DashboardService){
	
	$scope.selectedVNF = DashboardService.getSelectedInstance();
	if($scope.selectedVNF){
		$scope.selectedVNFName = $scope.selectedVNF.stackName;	
	}
	
	operationlogService.getOperationLog().then(function(data){
		$scope.operationlog = data[$scope.selectedVNFName];
		operationlogService.getAnsibleLog($scope.operationlog[0].dir).then(function(data){
			$scope.logview = data.log;
			$scope.allview = data.all;
			$scope.hostsview = data.host;
		});
	});
	
	$scope.showlog = function(ol){
		$scope.tabs[0].active = true;
		operationlogService.getAnsibleLog(ol.dir).then(function(data){
			$scope.logview = data.log;
			$scope.allview = data.all;
			$scope.hostsview = data.host;
		});
	};
	
	$scope.tabs = [{ title:'Ansible Log'},
	               { title:'Group_var/all', disabled: true },
	               { title:'Inventory/host', disabled: true }];
}); 