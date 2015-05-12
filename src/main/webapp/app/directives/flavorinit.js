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
			scope.$watch('installConfig.vm_config',function(){
            	if(scope.installConfig!=null){
            		if(scope.installConfig.vm_config != null){
            			//oam flavor
            			if(scope.installConfig.vm_config.oam!=null&&scope.installConfig.vm_config.oam.flavor!=null){
            				if(scope.installConfig.vm_config.oam.flavor.label=="small(1*2*360)"){
                				scope.installConfig.vm_config.oam.flavor = scope.flavorStore[scope.installConfig.comType]['oam'][0];
                        	}else if(scope.installConfig.vm_config.oam.flavor.label=="medium(2*4*480)"){
                        		scope.installConfig.vm_config.oam.flavor = scope.flavorStore[scope.installConfig.comType]['oam'][1];
                        	}else{
                        		scope.installConfig.vm_config.oam.flavor = scope.flavorStore[scope.installConfig.comType]['oam'][2];
                        	}
            			}
            			
            			//db flavor
            			if(scope.installConfig.vm_config.db!=null&&scope.installConfig.vm_config.db.flavor!=null){
            				if(scope.installConfig.vm_config.db.flavor.label=="small(1*2*360)"){
                				scope.installConfig.vm_config.db.flavor = scope.flavorStore[scope.installConfig.comType]['db'][0];
                        	}else if(scope.installConfig.vm_config.db.flavor.label=="medium(2*4*480)"){
                        		scope.installConfig.vm_config.db.flavor = scope.flavorStore[scope.installConfig.comType]['db'][1];
                        	}else{
                        		scope.installConfig.vm_config.db.flavor = scope.flavorStore[scope.installConfig.comType]['db'][2];
                        	}
            			}
            			
            			//cm flavor
            			if(scope.installConfig.vm_config.cm!=null&&scope.installConfig.vm_config.cm.flavor!=null){
            				if(scope.installConfig.vm_config.cm.flavor.label=="small(1*2*360)"){
                				scope.installConfig.vm_config.cm.flavor = scope.flavorStore[scope.installConfig.comType]['cm'][0];
                        	}else if(scope.installConfig.vm_config.cm.flavor.label=="medium(2*4*480)"){
                        		scope.installConfig.vm_config.cm.flavor = scope.flavorStore[scope.installConfig.comType]['cm'][1];
                        	}else{
                        		scope.installConfig.vm_config.cm.flavor = scope.flavorStore[scope.installConfig.comType]['cm'][2];
                        	}
            			}
            			
            		}
            		
            	}
            });
		}
	}
});