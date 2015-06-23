'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */

angular.module('comoamApp')
  .directive('sidebar',['$location',function() {
    return {
      templateUrl:'app/directives/sidebar/sidebar.html',
      restrict: 'E',
      replace: true,
      scope: {
      },
      controller:function($scope, $modal, $state, KVMService, OSService){
        $scope.selectedMenu = 'dashboard';
        $scope.collapseVar = 0;
        $scope.multiCollapseVar = 0;
        
        $scope.check = function(x){
          
          if(x==$scope.collapseVar)
            $scope.collapseVar = 0;
          else
            $scope.collapseVar = x;
        };
        
        $scope.multiCheck = function(y){
          
          if(y==$scope.multiCollapseVar)
            $scope.multiCollapseVar = 0;
          else
            $scope.multiCollapseVar = y;
        };
        
        $scope.openInstallSelect = function($log){
        	$scope.check(1);
        	
        	var modalInstance = $modal.open({
        	      animation: true,
        	      templateUrl: 'app/directives/sidebar/chooseOperation.html',
        	      controller: 'chooseOperationCtrl',
        	      size: 'sm',
        	      resolve: {
//        	        items: function () {
//        	          return $scope.items;
//        	        }
        	      }
        	    });

        	modalInstance.result.then(function (selectedItems) {
        	  if(selectedItems.EnvItem.Name == 'KVM'){
        		  KVMService.VNFType = selectedItems.VNFItem.Name;
        	  } else if(selectedItems.EnvItem.Name == 'Openstack'){
        		  OSService.VNFType = selectedItems.VNFItem.Name;
        	  }
        	  
        	  $state.go('dashboard.'.concat(selectedItems.EnvItem.url).concat(selectedItems.VNFItem.url));
        	}, function () {
        	});
        };
        
        $scope.openUpInstallSelect = function($log){
        	$scope.check(2);
        	
        	var modalInstance = $modal.open({
        	      animation: true,
        	      templateUrl: 'app/directives/sidebar/chooseOperation.html',
        	      controller: 'chooseUpOperationCtrl',
        	      size: 'sm',
        	      resolve: {
//        	        items: function () {
//        	          return $scope.items;
//        	        }
        	      }
        	    });

        	modalInstance.result.then(function (selectedItems) {
        	  if(selectedItems.EnvItem.Name == 'KVM'){
        		  KVMService.VNFType = selectedItems.VNFItem.Name;
        	  } else if(selectedItems.EnvItem.Name == 'Openstack'){
        		  OSService.VNFType = selectedItems.VNFItem.Name;
        	  }
        	  
        	  $state.go('dashboard.'.concat(selectedItems.EnvItem.url).concat(selectedItems.VNFItem.upgradeUrl));
        	}, function () {
        	});
        };
      }
    }
  }])
  .controller('chooseOperationCtrl', function($scope, $modalInstance, KVMService) {
	  
	  KVMService.getComTypeStore().then(function(data){
			$scope.VNFItems = data.COMType;
			$scope.OVNFItems = data.OVMType;
		});
	  
	  $scope.EnvItems = [{'Name':'KVM','url':'kvm'},{'Name':'Openstack','url':'os'}];
	  
	  $scope.selected = [];
	  
	  $scope.ok = function(){
		$modalInstance.close($scope.selected);
	  };
	  
	  $scope.cancel = function () {
		$modalInstance.dismiss('cancel');
      };
      
  })
  .controller('chooseUpOperationCtrl', function($scope, $modalInstance, KVMService) {
	  
	  $scope.VNFItems = [{"Name": "COM","upgradeUrl":"upgrade"}];
	  $scope.OVNFItems = [];
	  
	  KVMService.getComTypeStore().then(function(data){
			for(var i in data.OVMType){
				if(data.OVMType[i].upgradeUrl != ""){
					$scope.OVNFItems = $scope.OVNFItems.concat(data.OVMType[i]);
				}
			}
		});
	  
	  $scope.EnvItems = [{'Name':'KVM','url':'kvm'},{'Name':'Openstack','url':'os'}];
	  
	  $scope.selected = [];
	  
	  $scope.ok = function(){
		$modalInstance.close($scope.selected);
	  };
	  
	  $scope.cancel = function () {
		$modalInstance.dismiss('cancel');
      };
      
  });
