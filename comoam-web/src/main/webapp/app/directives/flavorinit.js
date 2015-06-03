'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('comoamApp').directive('flavorinit',function(){
	return{
		restrict:'A',
		link:function(scope,ele,attrs,ctrl){
			for(var init in scope.flavorStore[scope.installConfig.comType]['oam']){
				if(angular.equals(scope.installConfig.vm_config.oam.flavor,scope.flavorStore[scope.installConfig.comType]['oam'][init])){
					scope.installConfig.vm_config.oam.flavor = scope.flavorStore[scope.installConfig.comType]['oam'][init];
				}
			}
			for(var init in scope.flavorStore[scope.installConfig.comType]['cm']){
				if(angular.equals(scope.installConfig.vm_config.cm.flavor,scope.flavorStore[scope.installConfig.comType]['cm'][init])){
					scope.installConfig.vm_config.cm.flavor = scope.flavorStore[scope.installConfig.comType]['cm'][init];
				}
			}
			for(var init in scope.flavorStore[scope.installConfig.comType]['db']){
				if(angular.equals(scope.installConfig.vm_config.db.flavor,scope.flavorStore[scope.installConfig.comType]['db'][init])){
					scope.installConfig.vm_config.db.flavor = scope.flavorStore[scope.installConfig.comType]['db'][init];
				}
			}
		}
	}
});