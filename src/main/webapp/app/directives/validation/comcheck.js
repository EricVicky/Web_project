'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('comoamApp').directive('uniqueCom',function($log,KVMService){
	return{
		restrict:'A',
		require:'ngModel',
		link:function(scope,ele,attrs,ctrl){
			ele.bind('change',function(n){
				if(n){
					scope.$apply(function(){
						KVMService.uniqueDeploy({"comname":ele.val()}).then(function(isUnique){
							if(isUnique){
								ctrl.$setValidity('installConfig.deployment_prefix', true);
							}else{
								ctrl.$setValidity('installConfig.deployment_prefix', false);
							}
						}).then(function(response){
							$log.info(response);
						});
					});
				}
			})
		}
	}
});
