(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('PlantMySuffixDialogController', PlantMySuffixDialogController);

    PlantMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Plant', 'InSensor'];

    function PlantMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Plant, InSensor) {
        var vm = this;

        vm.plant = entity;
        vm.clear = clear;
        vm.save = save;
        vm.humidities = InSensor.query({filter: 'plant-is-null'});
        $q.all([vm.plant.$promise, vm.humidities.$promise]).then(function() {
            if (!vm.plant.humidity || !vm.plant.humidity.id) {
                return $q.reject();
            }
            return InSensor.get({id : vm.plant.humidity.id}).$promise;
        }).then(function(humidity) {
            vm.humidities.push(humidity);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.plant.id !== null) {
                Plant.update(vm.plant, onSaveSuccess, onSaveError);
            } else {
                Plant.save(vm.plant, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bachelorApp:plantUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
