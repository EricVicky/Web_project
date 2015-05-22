'use strict';

describe('Storage Controller Test', function(){
	var scope,$httpBackend;
    
    beforeEach(angular.mock.module('yao.compute', 'yao.storage'));
    
    beforeEach(angular.mock.inject(function($rootScope, $injector, $controller){
        scope = $rootScope.$new();
        $controller('StorageController', {$scope: scope});
        
        $httpBackend = $injector.get('$httpBackend');
        $httpBackend.when('POST', '/rest/os/cinder/volume/list').respond(200,{data:'200'});
    }));
    
    afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
    });
    // test start from here
    it('should get storage data successfully', function(done){
    	$httpBackend.flush();
        expect(scope.storageData.data).toBe('200');
        done();
    });
    it('should get menu successfully', function(done){
    	$httpBackend.flush();
        expect(scope.menus).toEqual(["Name", "Status", "Description", "Size", "Availability Zone", "Type", "Create At", "Attachments"]);
        done();
    });
    it('should loading is false', function(done){
    	$httpBackend.flush();
        expect(scope.loading).toBe(false);
        done();
    });

});


