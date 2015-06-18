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
      controller:function($scope, $modal, $state, KVMService){
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
        	      size: 'lg',
        	      resolve: {
//        	        items: function () {
//        	          return $scope.items;
//        	        }
        	      }
        	    });

        	modalInstance.result.then(function (selectedItems) {
        	  if(selectedItems.EnvItem.Name == 'KVM'){
        		  KVMService.VNFType = selectedItems.VNFitem.Name;
        	  }
        	  $state.go('dashboard.'.concat(selectedItems.EnvItem.url).concat(selectedItems.VNFitem.url));
        	}, function () {
        	});
        };
      }
    }
  }])
  .controller('chooseOperationCtrl', function($scope, $modalInstance, KVMService) {
	  
	  KVMService.getComTypeStore().then(function(data){
			$scope.VNFitems = data.COMType;
			$scope.OVNFitems = data.OVMType;
		});
	  
	  $scope.EnvItems = [{'Name':'KVM','url':'kvm'},{'Name':'Openstack','url':'os'}];
//	  $scope.VNFitems = [{'name':'OAM','url':'install'},{'name':'FCAPS','url':'install'}];
//	  $scope.OVNFitems = [{'name':'QOSAC','url':'ovminstall'}, {'name':'ATC','url':'ovminstall'}, {'name':'HPSIM','url':'ovminstall'}];
	  
	  $scope.selected = [];
	  
	  $scope.EnvItemVar = 'Environment';
	  $scope.VNFitemVar = 'VNF Type';
	  
	  $scope.ok = function(){
		$modalInstance.close($scope.selected);
	  };
	  
	  $scope.cancel = function () {
		$modalInstance.dismiss('cancel');
      };
      
      $scope.selectENVItem = function(data){
    	  $scope.selected.EnvItem = data;
    	  $scope.EnvItemVar = data.Name;
      };
      
      $scope.selectVNFItem = function(data){
    	  $scope.selected.VNFitem = data;
    	  $scope.VNFitemVar = data.Name;
      };
  });
