'use strict';

angular.module('dashboard', []).factory('DashboardService', function($log) {
	var slectedInstance;
	return {
		setSelectedInstance:function(upgradeIns){
			slectedInstance = upgradeIns;
		},
		getSelectedInstance:function(){
			return slectedInstance;
		}
	};
});