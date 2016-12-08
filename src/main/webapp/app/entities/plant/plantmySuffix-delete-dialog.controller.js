(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('PlantMySuffixDeleteController',PlantMySuffixDeleteController);

    PlantMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Plant'];

    function PlantMySuffixDeleteController($uibModalInstance, entity, Plant) {
        var vm = this;

        vm.plant = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Plant.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
