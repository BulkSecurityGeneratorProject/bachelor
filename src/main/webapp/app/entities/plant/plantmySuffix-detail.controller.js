(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('PlantMySuffixDetailController', PlantMySuffixDetailController);

    PlantMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Plant', 'InSensor'];

    function PlantMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Plant, InSensor) {
        var vm = this;

        vm.plant = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bachelorApp:plantUpdate', function(event, result) {
            vm.plant = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
