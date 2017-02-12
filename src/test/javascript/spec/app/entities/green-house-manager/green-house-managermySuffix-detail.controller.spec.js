'use strict';

describe('Controller Tests', function() {

    describe('GreenHouseManager Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGreenHouseManager, MockProfileSettings, MockGreenHouse;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGreenHouseManager = jasmine.createSpy('MockGreenHouseManager');
            MockProfileSettings = jasmine.createSpy('MockProfileSettings');
            MockGreenHouse = jasmine.createSpy('MockGreenHouse');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'GreenHouseManager': MockGreenHouseManager,
                'ProfileSettings': MockProfileSettings,
                'GreenHouse': MockGreenHouse
            };
            createController = function() {
                $injector.get('$controller')("GreenHouseManagerMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bachelorApp:greenHouseManagerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
