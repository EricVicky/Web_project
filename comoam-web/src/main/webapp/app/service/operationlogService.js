'use strict';
angular.module('operationlog').factory('operationlogService', function($location, $q, $resource, $log, $http) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl;
	return {
		getOperationLog: function (name) {
			var operationLog = $resource(restUrl + "rest/operationlog");
			return operationLog.query({"name":name}).$promise;
		},
		getAnsibleLog:function(dir){
			var ansibleLog = $resource(restUrl + "rest/ansiblelog");
			return ansibleLog.get({"dir":dir}).$promise;
		}
	};
});

