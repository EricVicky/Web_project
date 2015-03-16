'use strict';

describe('Key Pairs Controller Test', function(){
	var scope,$httpBackend;
    
    beforeEach(angular.mock.module('yao.compute'));
    
    beforeEach(angular.mock.inject(function($rootScope, $injector, $controller){
        scope = $rootScope.$new();
        $controller('KeyPairController', {$scope: scope});
        
        $httpBackend = $injector.get('$httpBackend');
        $httpBackend.when('POST', '/rest/os/compute/keypair/list').respond(200,{data:'200'});
    }));
    
    afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
    // test start from here
    it('should get keypairs successfully', function(done){
    	$httpBackend.flush();
        expect(scope.computeKeyPairData.data).toBe('200');
        done();
    });
    it('should get menu successfully', function(done){
    	$httpBackend.flush();
        expect(scope.menus).toEqual([ "Key Pair Name", "Fingerprint"]);
        done();
    });
    it('should loading is false', function(done){
    	$httpBackend.flush();
        expect(scope.loading).toBe(false);
        done();
    });
});


