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
				scope.$watch('installConfig.deployment_prefix',function(n){
							KVMService.uniqueDeploy({"name":ele.val()}).then(function(isUnique){
								if(isUnique.succeed){
									ctrl.$setValidity('notunique', true);
								}else{
									ctrl.$setValidity('notunique', false);
								}
							}).then(function(response){
								$log.info(response);
							});
				})
			
		}
	}
});
