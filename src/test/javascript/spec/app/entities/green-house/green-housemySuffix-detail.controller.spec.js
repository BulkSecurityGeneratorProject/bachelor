'use strict';

describe('Controller Tests', function() {

    describe('GreenHouse Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGreenHouse, MockInSensor, MockOutSwitch, MockPlant;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGreenHouse = jasmine.createSpy('MockGreenHouse');
            MockInSensor = jasmine.createSpy('MockInSensor');
            MockOutSwitch = jasmine.createSpy('MockOutSwitch');
            MockPlant = jasmine.createSpy('MockPlant');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'GreenHouse': MockGreenHouse,
                'InSensor': MockInSensor,
                'OutSwitch': MockOutSwitch,
                'Plant': MockPlant
            };
            createController = function() {
                $injector.get('$controller')("GreenHouseMySuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bachelorApp:greenHouseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
