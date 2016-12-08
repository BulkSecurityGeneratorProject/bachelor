(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('InSensorMySuffixDeleteController',InSensorMySuffixDeleteController);

    InSensorMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'InSensor'];

    function InSensorMySuffixDeleteController($uibModalInstance, entity, InSensor) {
        var vm = this;

        vm.inSensor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InSensor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
