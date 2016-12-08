(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('GreenHouseMySuffixDetailController', GreenHouseMySuffixDetailController);

    GreenHouseMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GreenHouse', 'InSensor', 'OutSwitch', 'Plant'];

    function GreenHouseMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, GreenHouse, InSensor, OutSwitch, Plant) {
        var vm = this;

        vm.greenHouse = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bachelorApp:greenHouseUpdate', function(event, result) {
            vm.greenHouse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
