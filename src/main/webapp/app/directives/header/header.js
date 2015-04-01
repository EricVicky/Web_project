'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('comoamApp')
	.directive('header',function(){
		return {
        templateUrl:'app/directives/header/header.html',
        restrict: 'E',
        replace: true,
    	}
	});


