'use strict';

describe('Image Controller Test', function(){
	var scope,$httpBackend;
    
    beforeEach(angular.mock.module('yao.compute'));
    
    beforeEach(angular.mock.inject(function($rootScope, $injector, $controller){
        scope = $rootScope.$new();
        $controller('ImageController', {$scope: scope});
        
        $httpBackend = $injector.get('$httpBackend');
        $httpBackend.when('POST', '/rest/os/glance/image/list').respond(200,{data:'200'});
    }));
    
    afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
    // test start from here
    it('should get image successfully', function(done){
    	$httpBackend.flush();
        expect(scope.images.data).toBe('200');
        done();
    });
    it('should get image menu successfully', function(done){
    	$httpBackend.flush();
        expect(scope.menus).toEqual([ "Image Name", "Type", "Status", "Public", "Protected", "Format"]);
        done();
    });
});


