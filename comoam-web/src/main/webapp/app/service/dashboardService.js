'use strict';

angular.module('dashboard', []).factory('DashboardService', function($log) {
	var slectedInstance;
	return {
		setUpgradeInstance:function(upgradeIns){
			slectedInstance = upgradeIns;
		},
		getUpgradeInstance:function(){
			return slectedInstance;
		}
	};
});