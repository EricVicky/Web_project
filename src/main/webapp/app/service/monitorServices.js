'use strict';

angular.module('monitor').factory('monitorService', function($log) {
	var stepsDict= {
			"KVM" : {
				"install" :["Start", "Generate Config Driver", "Start VM instance", "Prepare Install Options",  "Finished"],
				 "upgrade": ["Start", "Data Backup", "Post Image Replacement", "Post Configuration", "Data Restore", "Finished"],
				 "backup":["Start","Data Backup","Finished"]
			},
			"Openstack" :{
				"install" : ["Start", "valiadtion key", "Generate Heat Templates",  "check Presence of Heat stack", "Cloud Init",  "Start COM"],
				 "upgrade": [],
				 "backup":[]
			}
			
	};
	var environment;
	var action;
	var channel;
	var topicPrefix= "/log/tail/" 
	return {
		monitorKVMInstall: function(ch) {
			environment = "KVM";
			action = "install";
			channel = ch;
		},
		monitorKVMUpgrade: function(ch) {
			environment = "KVM";
			action = "upgrade";
			channel = ch;
		},
		monitorKVMBackup: function(ch) {
			environment = "KVM";
			action = "backup";
			channel = ch;
		},
		monitorOSInstall: function(ch) {
			environment = "Openstack";
			action = "install";
			channel = ch;
		},
		getChannel : function (){
			return (topicPrefix  + channel);
		},
		getSteps: function(){
			return stepsDict[environment][action];
		}
	};
});