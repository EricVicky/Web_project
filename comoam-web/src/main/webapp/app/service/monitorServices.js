'use strict';

angular.module('monitor').factory('monitorService', function($log, $location, $resource) {
	var stepsDict= {
			"KVM" : {
				"install" :["Start", "Generate Config Driver", "Start VM Instance", "Prepare Install Options",  "Finished"],
				"upgrade": ["Start", "Data Backup", "Prepare Virtual Machines", "Post Image Replacement", "Data Restore", "Finished"],
				 "backup":["Start","Data Backup","Finished"],
				 "delete":["Start","Destroy Virtual Machine","Undefine Virtual Machine","Finished"],
				 "restore":["Start","Data Restore","Finished"],
				 "gr_pri_install":["Start","Pri GR Install","Finished"],
				 "gr_sec_install":["Start","Sec GR Install","Finished"],
				 "gr_uninstall":["Start","GR Uninstall","Finished"]
			},
			"Openstack" :{
				"install" : ["Start", "Valiadtion Key", "Generate Heat Templates",  "Check Presence of Heat Stack", "Cloud Init",  "Start COM", "Finished"],
				 "upgrade": ["Start", "Data Backup", "Post Image Replacement", "Post Configuration", "Data Restore", "Finished"],
				 "backup":["Start","Data Backup","Finished"],
				 "delete":["Start","Check Presence of stack","Destroy stack","Finished"],
				 "restore":["Start","Data Restore","Finished"],
				 "gr_pri_install":["Start","Pri GR Install","Finished"],
				 "gr_sec_install":["Start","Sec GR Install","Finished"],
				 "gr_uninstall":["Start","GR Uninstall","Finished"]
			},
			"KVM_OVM" :{
				"install" : ["Start", "Generate Config Driver", "Start VM Instance", "Post Configuration", "Finished"],
				"upgrade":["Start","Data Backup","Start Virtual Machines","Finished"],
				"delete":["Start","Destroy Virtual Machines","Undefine Virtual Machines","Delete Virtual Machine Files","Finished"]
			},
			"KVM_QOSAC":{
				"install":["Start", "Generate Config Driver", "Start VM Instance", "Post Configuration", "Finished"],
				"upgrade":["Start","Data Backup","Prepare Virtual Machines","Post Image Replacement","Data Restore","Finished"],
				"delete":["Start","Destroy Virtual Machines","Undefine Virtual Machines","Delete Virtual Machine Files","Finished"],
				"backup":["Start","Data Backup","Finished"],
				"restore":["Start","Data Restore","Finished"]
			},
			"KVM_ARS":{
				"install":["Start", "Prepare Environment", "Install", "Finished"]			
			},
			"Openstack_QOSAC":{
				"install":["Start", "Generate Config Driver", "Start VM Instance", "Post Configuration", "Finished"]
			},
	};
	
	var endMsg = {
			"install": {
				"success" : "Installation Completed",
				"error": "Installation Failed"
			},
			"upgrade":{
				"success" : "Upgrade Completed",
				"error": "Upgrade Failed"
			},
			"backup":{
				"success" : "Backup Completed",
				"error": "Backup Failed"
			},
			"delete":{
				"success" : "Destroyed",
				"error": "Deletion failed"
			},
			"restore":{
				"success" : "Restore completed!",
				"error": "Restore failed"
			},
			"gr_pri_install":{
				"success" : "Installation of Primary completed!",
				"error": "Installation of Primary failed"
			},
			"gr_sec_install":{
				"success" : "Installation of GR on secondary completed!",
				"error": "Installation of GR on secondary  failed"
			},
			"gr_uninstall":{
				"success" : "Uninstallation of GR completed!",
				"error": "Uninstallation  of GR failed"
			}
	}
	var environment;
	var action;
	var channel;
	var topicPrefix= "/log/tail/";
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl;
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
		monitorKVMBackup: function(ch,comType) {
			if(comType=='QOSAC'){
				environment = "KVM_QOSAC";
			}
			environment = "KVM";
			action = "backup";
			channel = ch;
		},
		monitorKVMRestore:function(ch,comType) {
			if(comType=='QOSAC'){
				environment = "KVM_QOSAC";
			}
			environment = "KVM";
			action = "restore";
			channel = ch;
		},
		monitorKVMDelete: function(ch,comType) {
			if(comType=='FCAPS'||comType=='CM'||comType=='OAM'){
				environment = "KVM";
			}else if(comType=='QOSAC'||comType=='ARS'){
				environment = "KVM"+"_"+comType;
			}else{
				environment = "KVM_OVM";
			}
			action = "delete";
			channel = ch;
		},
		monitorKVMGR_Pri_Install: function(ch) {
			environment = "KVM";
			action = "gr_pri_install";
			channel = ch;
		},
		monitorKVMGR_Sec_Install: function(ch) {
			environment = "KVM";
			action = "gr_sec_install";
			channel = ch;
		},
		monitorKVMGR_UnInstall: function(ch) {
			environment = "KVM";
			action = "gr_uninstall";
			channel = ch;
		},
		monitorOSInstall: function(ch) {
			environment = "Openstack";
			action = "install";
			channel = ch;
		},
		monitorOSUpgrade: function(ch) {
			environment = "Openstack";
			action = "upgrade";
			channel = ch;
		},
		monitorOSBackup: function(ch) {
			environment = "Openstack";
			action = "backup";
			channel = ch;
		},
		monitorOSRestore: function(ch) {
			environment = "Openstack";
			action = "restore";
			channel = ch;
		},
		monitorOSDelete: function(ch) {
			environment = "Openstack";
			action = "delete";
			channel = ch;
		},
		monitorKVMOVMInstall: function(ch) {
			environment = "KVM_OVM";
			action = "install";
			channel = ch;
		},
		monitorKVMOVMUpgrade: function(ch) {
			environment = "KVM_OVM";
			action = "upgrade";
			channel = ch;
		},
		monitorKVMQOSACUpgrade: function(ch) {
			environment = "KVM_QOSAC";
			action = "upgrade";
			channel = ch;
		},
		monitorKVMQOSACInstall:function(ch) {
			environment = "KVM_QOSAC";
			action = "install";
			channel = ch;
		},
		monitor: function(env, act, comType, ch){
			environment = env;
			action = act.toLowerCase();
			channel  = ch;
			if(comType == "ARS" || comType == "QOSAC"){
				environment = env + "_" + comType;
			}else if(comType == "ATC" || comType == "HPSIM"){
				environment = env + "_OVM";
			}
		},
		runAnsibleTask : function(){
			var runAnsibleRes = $resource(restUrl + "rest/ansible/task");
			return runAnsibleRes.get({"comStack" : channel}).$promise;
		},
		monitorKVMQOSACDelete: function(ch) {
			environment = "KVM_QOSAC";
			action = "delete";
			channel = ch;
		},
		monitorKVMARSInstall: function(ch) {
			environment = "KVM_ARS";
			action = "install";
			channel = ch;
		},
		getChannel : function (){
			return (topicPrefix  + channel);
		},
		getSteps: function(){
			return stepsDict[environment][action];
		},
		getEndMsg: function(res){
			return endMsg[act][res];
		}
	};
});