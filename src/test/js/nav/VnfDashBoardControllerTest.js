'use strict';

describe('vnf dashboard Controller Test', function(){
    var scope,$httpBackend,$DTLoadingTemplate;
    
    beforeEach(angular.mock.module('yao.dashBoard','datatables'));

    beforeEach(angular.mock.inject(function($rootScope, $controller, $injector,DTLoadingTemplate){
        scope = $rootScope.$new();
        $controller('vnfDashBoardController', {$scope: scope});
        
        $DTLoadingTemplate = DTLoadingTemplate;
        
        $httpBackend = $injector.get('$httpBackend');
        $httpBackend.when('GET', '/rest/os/heat/stack-list').respond(200,{statusCode:'200'});
    }));
    
    afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
    
    // test start from here
    it('should get dashboard data successfully', function(done){
    	$httpBackend.flush();
        expect(scope.stacks).not.toBeNull();
        done();
    });
    
    it('should return loading template successfully', function(done){
    	$httpBackend.flush();
    	expect($DTLoadingTemplate.html).toBe('<img src="images/loading.gif">');
    	done();
    });
});


