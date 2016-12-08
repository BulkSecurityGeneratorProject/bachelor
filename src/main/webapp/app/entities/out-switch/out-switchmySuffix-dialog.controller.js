(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('OutSwitchMySuffixDialogController', OutSwitchMySuffixDialogController);

    OutSwitchMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OutSwitch'];

    function OutSwitchMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OutSwitch) {
        var vm = this;

        vm.outSwitch = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.outSwitch.id !== null) {
                OutSwitch.update(vm.outSwitch, onSaveSuccess, onSaveError);
            } else {
                OutSwitch.save(vm.outSwitch, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bachelorApp:outSwitchUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
