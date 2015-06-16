angular .module('comoamApp')
  .config(['$translateProvider', function ($translateProvider) {
	  $translateProvider.useStaticFilesLoader({
	    prefix: 'locales/validation/',
	    suffix: '.json'
		});
	    $translateProvider.preferredLanguage('en');
	}])
	.config(['$translateProvider', function ($translateProvider) {
	  $translateProvider.useStaticFilesLoader({
	    prefix: 'locales/login/',
	    suffix: '.json'
		});
		$translateProvider.preferredLanguage('en');
	}]);

angular.module("auth").config(function($httpProvider) {
	$httpProvider.interceptors.push('authInterceptor');
});
  



  
