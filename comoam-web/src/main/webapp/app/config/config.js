angular.module('comoamApp')
  .config(['$translateProvider', function ($translateProvider,$scope) {
	  $translateProvider.useStaticFilesLoader({
		  					prefix: 'locales/validation/',
		  					suffix: '.json'
						 });
	  $translateProvider.preferredLanguage('en');
	}])


angular.module("auth").config(function($httpProvider) {
	$httpProvider.interceptors.push('authInterceptor');
});
  
angular.module('comoamApp').config(['$httpProvider', function($httpProvider) {
    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};    
    }    
    $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
}]);


  
