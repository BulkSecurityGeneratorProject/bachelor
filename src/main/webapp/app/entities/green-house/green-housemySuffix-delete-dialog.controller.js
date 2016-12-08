(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('GreenHouseMySuffixDeleteController',GreenHouseMySuffixDeleteController);

    GreenHouseMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'GreenHouse'];

    function GreenHouseMySuffixDeleteController($uibModalInstance, entity, GreenHouse) {
        var vm = this;

        vm.greenHouse = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GreenHouse.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
