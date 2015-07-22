'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('comoamApp').directive('importComconf',function(){
	return{
		restrict:'A',
		link:function(scope,ele,attrs,ctrl){
			ele.bind('drop',function(evt) {
		                evt.stopPropagation();
		                evt.preventDefault();
		                var files = evt.originalEvent.dataTransfer.files;
		                for (var i = 0, f; f = files[i]; i++) {
		                	var reader = new FileReader();
		                		reader.onload = (function(theFile) {
		                            return function(e) {
		                            	scope.$apply(function(){
		                            	    scope.installConfig = JSON3.parse(e.target.result);
		                            	    if(!scope.installConfig.key_name){
		                            	    	scope.networktraffic = scope.installConfig.vm_config['oam'].nic.length;
		                            	    }
		                            	});
		                            };
		                          })(f);
		                        reader.readAsBinaryString(f);
		                }
		              });
		      ele.bind("dragover", function (evt) {
		                evt.stopPropagation();
		                evt.preventDefault();
		                evt.originalEvent.dataTransfer.dropEffect = 'copy'; 
		      })
		}
	}
})
.directive('exportComconf',function(){
	return{
		restrict:'A',
		link:function(scope,ele,attrs,ctrl){
			scope.$watch('export', function() {
				if(scope.installConfig!=null){
					var data = "text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(scope.installConfig));
					ele.html('<a href="data:' + data + '" download="COM_config.json">Export Installation Config</a>');
				}
			})
		}
	}
});
