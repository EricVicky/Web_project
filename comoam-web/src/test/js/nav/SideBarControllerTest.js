'use strict';

describe('sideBar Controller Test', function() {
	var scope, state, $httpBackend;
	var timerCallback;

	beforeEach(angular.mock.module('yao.sideBar', 'ui.router'));
	beforeEach(inject(function($state) {
		spyOn($state, 'go');
	}));

	beforeEach(angular.mock.inject(function($rootScope, $controller, $injector,
			$state) {
		scope = $rootScope.$new();
		$controller('sidebarController', {
			$scope : scope
		});

		state = $state;

		$httpBackend = $injector.get('$httpBackend');
		$httpBackend.when('GET', '/rest/sidebar').respond(200, {
			statusCode : '200'
		});

		timerCallback = jasmine.createSpy("timerCallback");
		jasmine.clock().install();
	}));

	afterEach(function() {
		$httpBackend.verifyNoOutstandingExpectation();
		$httpBackend.verifyNoOutstandingRequest();
		jasmine.clock().uninstall();
	});

	// test start from here
	it('should transfer to main.dashboard successfully', function(done) {
		$httpBackend.flush();
		expect(state.go).toHaveBeenCalledWith("main.dashboard");
		done();
	});

	it('should get system menus', function(done) {
		$httpBackend.flush();
		expect(scope.sys_menu_urls[0].menu).toBe('Compute');
		expect(scope.sys_menu_urls[0].sref).toBe("main.compute({initPage: 'instance'})");
		done();
	});

	it('should get side bar menus from resutful API', function(done) {
		$httpBackend.flush();
		expect(scope.sb_data).not.toBeNull();
		done();
	});

	it('should get side bar menus', function(done) {
		$httpBackend.flush();
		expect(scope.menu_urls[0].menu).toBe('Compute');
		expect(scope.menu_urls[0].url).toBe('#/compute');
		expect(scope.menu_urls[0].sref).toBe('main.compute');
		done();
	});

	it('should causes a timeout to be called synchronously', function(done) {
		$httpBackend.flush();
		setTimeout(function() {
			timerCallback();
		}, 1000);
		expect(timerCallback).not.toHaveBeenCalled();
		jasmine.clock().tick(1001);
		expect(timerCallback).toHaveBeenCalled();
		done();
	});
});
