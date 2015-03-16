var baseUrl = angular.module('yao.restURL', []);

baseUrl.factory('RestService', function($location) {
    return {
        getRestUrl: function(url) {
            var parentUrl = parent.window.location.href;
            var baseUrl = parentUrl.split("#", 1)[0];
            var restURL = baseUrl + "rest/nfv" + url;
            return restURL;
        }
    };
});