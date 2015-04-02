'use strict';

describe('Login Controller Test', function() {
	var scope, state, $stateProvider, $httpBackend;
	var promise, successCallback, errorCallback;

	beforeEach(angular.mock.module('yao.login', 'ui.router'));
	beforeEach(inject(function($state) {
		spyOn($state, 'go');
	}));

	beforeEach(angular.mock.inject(function($rootScope, $controller, $injector,
			$state) {
		scope = $rootScope.$new();
		$controller('loginController', {
			$scope : scope
		});

		state = $state;

		$httpBackend = $injector.get('$httpBackend');

		successCallback = jasmine.createSpy();
		errorCallback = jasmine.createSpy();

		scope.loginInfo = {
			username : "rex",
			password : "rex"
		};
	}));

	afterEach(function() {
		$httpBackend.verifyNoOutstandingExpectation();
		$httpBackend.verifyNoOutstandingRequest();
	});

	// test start from here
	it('should login successfully with the correct input', function(done) {
		
        var data = '{"statusCode":"200"}';
        $httpBackend.expectPOST('/rest/login').respond(200, data);
        promise = scope.onLogin(scope.loginInfo);
        promise.then(successCallback, errorCallback);

        $httpBackend.flush();

        expect(successCallback).toHaveBeenCalledWith(angular.fromJson(data));
        expect(errorCallback).not.toHaveBeenCalled();
        
        done();
	});
});
