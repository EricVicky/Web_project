'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */


angular.module('comoamApp').directive('ping', function($log, KVMService) {
	return {
		restrict : 'A',
		require : 'ngModel',
		link: function(scope, ele, attrs, ctrl){
			ele.bind('change', function(n){
				if(n){
					scope.$apply(function (){
						KVMService.pingcheck({host:"135.251.236.98"}).then(function (data){
							$log.info(data);
						}).then(function(response){
							$log.info(response);
						});
					});
				}
			})
		}
	}
});

