(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('InSensorMySuffixDialogController', InSensorMySuffixDialogController);

    InSensorMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InSensor'];

    function InSensorMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InSensor) {
        var vm = this;

        vm.inSensor = entity;
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
            if (vm.inSensor.id !== null) {
                InSensor.update(vm.inSensor, onSaveSuccess, onSaveError);
            } else {
                InSensor.save(vm.inSensor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bachelorApp:inSensorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
