'use strict';

describe('Network Controller Test', function() {
	var scope, state, $httpBackend;

	beforeEach(angular.mock.module('yao.network', 'ui.router'));
	beforeEach(inject(function($state) {
		spyOn($state, 'go');
	}));

	beforeEach(angular.mock.inject(function($rootScope, $controller, $injector,
			$state) {
		scope = $rootScope.$new();
		$controller('NetworkController', {
			$scope : scope
		});

		state = $state;

		$httpBackend = $injector.get('$httpBackend');
        $httpBackend.when('POST', '/rest/os/neutron/network/list').respond(200,{data:'200'});

	}));

	afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
    // test start from here
    it('should get network data successfully', function(done){
    	$httpBackend.flush();
        expect(scope.networkData.data).toBe('200');
        done();
    });
    it('should get menu successfully', function(done){
    	$httpBackend.flush();
        expect(scope.menus).toEqual(["Name", "Subnet", "Status", "Admin State", "Shared"]);
        done();
    });
    it('should loading is false', function(done){
    	$httpBackend.flush();
        expect(scope.loading).toBe(false);
        done();
    });
});
