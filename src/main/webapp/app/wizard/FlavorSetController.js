'use strict';

wizard.controller('FlavorSetController', function($scope, FlavorService) {

    FlavorService.initFlavors();
    FlavorService.getFlavors().then(function(result) {
        $scope.flavors = result.data;
    });
    
    var isReduce = false;
    
    $scope.changeMrfcFlavor =  function(){
    	var index = document.getElementById("mrfc_flavor").selectedIndex;
    	if(!isReduce){
    		document.getElementById("mrfp_flavor").selectedIndex = index + 1;
    	}
    	if(isReduce){
    		document.getElementById("mrfp_flavor").selectedIndex = index;
    	}
    }
    
    $scope.changeMrfpFlavor =  function(){
    	isReduce = true;
    }
});