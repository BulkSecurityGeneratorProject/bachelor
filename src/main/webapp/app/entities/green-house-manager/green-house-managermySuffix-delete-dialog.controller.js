(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('GreenHouseManagerMySuffixDeleteController',GreenHouseManagerMySuffixDeleteController);

    GreenHouseManagerMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'GreenHouseManager'];

    function GreenHouseManagerMySuffixDeleteController($uibModalInstance, entity, GreenHouseManager) {
        var vm = this;

        vm.greenHouseManager = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GreenHouseManager.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
