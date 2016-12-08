(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('InSensorMySuffixDetailController', InSensorMySuffixDetailController);

    InSensorMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InSensor'];

    function InSensorMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, InSensor) {
        var vm = this;

        vm.inSensor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bachelorApp:inSensorUpdate', function(event, result) {
            vm.inSensor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
