(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('OutSwitchMySuffixDeleteController',OutSwitchMySuffixDeleteController);

    OutSwitchMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'OutSwitch'];

    function OutSwitchMySuffixDeleteController($uibModalInstance, entity, OutSwitch) {
        var vm = this;

        vm.outSwitch = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OutSwitch.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
