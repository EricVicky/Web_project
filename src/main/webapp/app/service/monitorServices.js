'use strict';

angular.module('monitor').factory('monitorService', function($log) {
	var stepsDict= {
			"KVM" : {
				"install" :["Start", "Generate Config Driver", "Start VM instance", "Prepare Install Options",  "Finished"],
				 "upgrade": ["Start", "Data Backup", "Post Image Replacement", "Post Configuration", "Data Restore", "Finished"]
			},
			"Openstack" :{
				"install" : [],
				 "upgrade": []
			}
			
	};
	var environment;
	var action;
	var host;
	var topicPrefix= "/log/tail/" 
	return {
		monitorKVMInstall: function(h) {
			environment = "KVM";
			action = "install";
			host = h;
		},
		monitorKVMUpgrade: function(h) {
			environment = "KVM";
			action = "upgrade";
			host = h;
		},
		getChannel : function (){
			return (topicPrefix  + host);
		},
		getSteps: function(){
			return stepsDict[environment][action];
		}
	};
});