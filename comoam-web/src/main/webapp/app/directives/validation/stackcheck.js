'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('comoamApp').directive('uniqueStack',function($log,OSService){
	return{
		restrict:'A',
		require:'ngModel',
		link:function(scope,ele,attrs,ctrl){
			scope.$watch('installConfig.stack_name',function(n){
						OSService.uniqueDeploy({"name":ele.val()}).then(function(isUnique){
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
