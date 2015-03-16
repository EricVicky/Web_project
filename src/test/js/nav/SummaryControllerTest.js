'use strict';

describe('summary Controller Test', function(){
    var scope,state,$stateProvider,$httpBackend;
    
    beforeEach(angular.mock.module('yao.summary','yao.compute.services', 'ui.router'));
    beforeEach(inject(function($state) {
        spyOn($state, 'go');
    }));

    beforeEach(angular.mock.inject(function($rootScope, $controller, $injector, $state){
        scope = $rootScope.$new();
        $controller('summaryController', {$scope: scope});
        
        state = $state;
        
        $httpBackend = $injector.get('$httpBackend');
        $httpBackend.when('POST', '/rest/os/compute/limits').respond(200,{statusCode:'200'});
    }));
    
    afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
    
    // test start from here
    it('should get summary data successfully', function(done){
    	$httpBackend.flush();
        expect(scope.summaryData).not.toBeNull();
        done();
    });
});


