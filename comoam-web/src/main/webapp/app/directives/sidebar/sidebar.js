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
        	  if(selectedItems.EnvItem.name == 'KVM'){
        		  KVMService.VNFType = selectedItems.VNFitem.name;
        	  }
        	  $state.go('dashboard.'.concat(selectedItems.EnvItem.url).concat(selectedItems.VNFitem.url));
        	}, function () {
        	  $log.info('Modal dismissed at: ' + new Date());
        	});
        };
      }
    }
  }])
  .controller('chooseOperationCtrl', function($scope, $modalInstance) {
	  
	  $scope.EnvItems = [{'name':'KVM','url':'kvm'},{'name':'Openstack','url':'os'}];
	  $scope.VNFitems = [{'name':'OAM','url':'install'},{'name':'FCAPS','url':'install'}];
	  $scope.OVNFitems = [{'name':'QoSAC','url':''}, {'name':'ATC','url':'ovminstall'}, {'name':'HPsim','url':'ovminstall'}];
	  
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
    	  $scope.EnvItemVar = data.name;
      };
      
      $scope.selectVNFItem = function(data){
    	  $scope.selected.VNFitem = data;
    	  $scope.VNFitemVar = data.name;
      };
  });
