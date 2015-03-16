'use strict';

describe('Instance Controller Test', function(){
	var scope,modal,state,$httpBackend;
	
	var fakeModal = {
	    result: {
	        then: function(confirmCallback, cancelCallback) {
	            //Store the callbacks for later when the user clicks on the OK or Cancel button of the dialog
	            this.confirmCallBack = confirmCallback;
	            this.cancelCallback = cancelCallback;
	        }
	    },
	    close: function(item) {
	        //The user clicked OK on the modal dialog, call the stored confirm callback with the selected item
	        this.result.confirmCallBack(item);
	    },
	    dismiss: function(type) {
	        //The user clicked cancel on the modal dialog, call the stored cancel callback
	        this.result.cancelCallback(type);
	    }
	};
    
    beforeEach(angular.mock.module('yao.compute'));
    
    beforeEach(inject(function($modal) {
        spyOn($modal, 'open').and.returnValue(fakeModal);
    }));
    
    beforeEach(angular.mock.inject(function($rootScope, $injector, $controller){
        scope = $rootScope.$new();
        modal = $injector.get('$modal');
        $controller('InstanceController', {$scope: scope, $modal: modal});
        $controller('InstanceLogController', {$scope: scope, $modalInstance: scope.modalInstance,instanceId: 'instanceId'});
        
        $httpBackend = $injector.get('$httpBackend');
        $httpBackend.when('POST', '/rest/os/compute/instances').respond(200,{data:'200'});
        
        $httpBackend = $injector.get('$httpBackend');
        $httpBackend.when('GET', '/rest/os/compute/instances/instanceId/consolelog').respond(200,'AAAAA\nBBBBB\nCCCCCC');
    }));
    
    afterEach(function() {
    	$httpBackend.verifyNoOutstandingExpectation();
    });
    // test start from here
    it('should get instance menus successfully', function(done){
    	$httpBackend.flush();
		expect(scope.menus[0]).toBe('Instance Name');
		expect(scope.menus[1]).toBe('Image');
		expect(scope.menus[2]).toBe('IP address');
		expect(scope.menus[3]).toBe('Flavor');
		expect(scope.menus[4]).toBe('Keypair');
		expect(scope.menus[5]).toBe('StackName');
		expect(scope.menus[6]).toBe('State');
		expect(scope.menus[7]).toBe('Avalibilty Zone');
		expect(scope.menus[8]).toBe('Actions');
        done();
    });
    
    it('should get instances data form server successfully', function(done){
    	$httpBackend.flush();
        expect(scope.instances.data).toEqual('200');
        done();
    });
    
    it('should cancel the dialog when dismiss is called, and $scope.canceled should be true', function(done){
    	$httpBackend.flush();
    	expect(scope.canceled).toBeUndefined();
        scope.open('instanceId'); // Open the modal
        scope.modalInstance.dismiss("cancel");
        expect(scope.canceled).toBe(true);
        done();
    });
    
    it('should get instance loading template successfully', function(done){
		expect(scope.instancelogs.$$unwrapTrustedValue()).toEqual('<span class="loading"><img src="images/loading_black.gif"/></span>');
        done();
    });
    
    it('should replace the /n to </br> successfully', function(done){
    	$httpBackend.flush();
        expect(scope.instancelogs.$$unwrapTrustedValue()).toBe('AAAAA<br/>BBBBB<br/>CCCCCC');
        done();
    });
});


