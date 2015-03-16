'use strict';

describe('Subnet Controller Test', function() {
	var scope, state, stateParams;

	beforeEach(angular.mock.module('yao.network', 'ui.router','yao.network.services'));
	beforeEach(inject(function($state) {
		spyOn($state, 'go');
	}));

	beforeEach(angular.mock.inject(function($rootScope, networkDataService, $controller, $injector, $state) {
		scope = $rootScope.$new();
		stateParams = $rootScope.$new();
		stateParams.subnetId = 'subnetId001';
		networkDataService.saveSubnet(eval('({id:\'subnetId001\'})'));
		$controller('SubnetController', {
			$scope : scope,
			$stateParams : stateParams
		});

		state = $state;
		

	}));

    // test start from here
    it('should get subnet data successfully', function(done){
        expect(scope.subnetObj.id).toBe('subnetId001');
        done();
    });
    it('should loading is false', function(done){
        expect(scope.loading).toBe(false);
        done();
    });
});
