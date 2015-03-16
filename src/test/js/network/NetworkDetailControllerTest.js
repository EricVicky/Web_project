'use strict';

describe('Network Detail Controller Test', function() {
	var scope, state, stateParams, $httpBackend;

	beforeEach(angular.mock.module('yao.network', 'ui.router','yao.network.services'));
	beforeEach(inject(function($state) {
		spyOn($state, 'go');
	}));

	beforeEach(angular.mock.inject(function($rootScope, networkDataService, $controller, $injector,	$state) {
		scope = $rootScope.$new();
		stateParams = $rootScope.$new();
		stateParams.netId = 'networkId001';
		networkDataService.saveNetwork(eval('({id:\'networkId001\'})'));
		$controller('NetworkDetailController', {
			$scope : scope,
			$stateParams : stateParams
		});

		state = $state;

		$httpBackend = $injector.get('$httpBackend');
        $httpBackend.when('POST', '/rest/os/neutron/network/networkId001/port/list/').respond(200,{data:'200'});

	}));

	afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
    // test start from here
    it('should get network data successfully', function(done){
    	$httpBackend.flush();
        expect(scope.portListData.data).toBe('200');
        done();
    });
    it('should get menu successfully', function(done){
    	$httpBackend.flush();
        expect(scope.menus).toEqual([ "Name", "Fixed IPs", "Attached Device", "Status", "Admin State" ]);
        expect(scope.subnetMenus).toEqual([ "Name", "CIDR", "IP Version", "Gateway IP" ]);
        done();
    });
    it('should loading is false', function(done){
    	$httpBackend.flush();
        expect(scope.loading).toBe(false);
        done();
    });
});
