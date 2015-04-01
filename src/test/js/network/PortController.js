'use strict';

describe('Port Controller Test', function() {
	var scope, state, stateParams;

	beforeEach(angular.mock.module('yao.network', 'ui.router','yao.network.services'));
	beforeEach(inject(function($state) {
		spyOn($state, 'go');
	}));

	beforeEach(angular.mock.inject(function($rootScope, networkDataService, $controller, $injector,
			$state) {
		scope = $rootScope.$new();
		stateParams = $rootScope.$new();
		stateParams.portId = 'portId001';
		networkDataService.savePort(eval('({id:\'portId001\'})'));
		$controller('PortController', {
			$scope : scope,
			$stateParams : stateParams
		});

		state = $state;
		

	}));

    // test start from here
    it('should get port data successfully', function(done){
        expect(scope.portObj.id).toBe('portId001');
        done();
    });
    it('should loading is false', function(done){
        expect(scope.loading).toBe(false);
        done();
    });
});
