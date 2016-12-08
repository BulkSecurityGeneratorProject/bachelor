(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('InSensorMySuffixController', InSensorMySuffixController);

    InSensorMySuffixController.$inject = ['$scope', '$state', 'InSensor'];

    function InSensorMySuffixController ($scope, $state, InSensor) {
        var vm = this;
        
        vm.inSensors = [];

        loadAll();

        function loadAll() {
            InSensor.query(function(result) {
                vm.inSensors = result;
            });
        }
    }
})();
