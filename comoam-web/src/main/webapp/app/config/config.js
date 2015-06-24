angular .module('comoamApp')
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
  



  
