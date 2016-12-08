(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('GreenHouseMySuffixDialogController', GreenHouseMySuffixDialogController);

    GreenHouseMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'GreenHouse', 'InSensor', 'OutSwitch', 'Plant'];

    function GreenHouseMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, GreenHouse, InSensor, OutSwitch, Plant) {
        var vm = this;

        vm.greenHouse = entity;
        vm.clear = clear;
        vm.save = save;
        vm.humidities = InSensor.query({filter: 'greenhouse-is-null'});
        $q.all([vm.greenHouse.$promise, vm.humidities.$promise]).then(function() {
            if (!vm.greenHouse.humidity || !vm.greenHouse.humidity.id) {
                return $q.reject();
            }
            return InSensor.get({id : vm.greenHouse.humidity.id}).$promise;
        }).then(function(humidity) {
            vm.humidities.push(humidity);
        });
        vm.temperatures = InSensor.query({filter: 'greenhouse-is-null'});
        $q.all([vm.greenHouse.$promise, vm.temperatures.$promise]).then(function() {
            if (!vm.greenHouse.temperature || !vm.greenHouse.temperature.id) {
                return $q.reject();
            }
            return InSensor.get({id : vm.greenHouse.temperature.id}).$promise;
        }).then(function(temperature) {
            vm.temperatures.push(temperature);
        });
        vm.humidifiers = OutSwitch.query({filter: 'greenhouse-is-null'});
        $q.all([vm.greenHouse.$promise, vm.humidifiers.$promise]).then(function() {
            if (!vm.greenHouse.humidifier || !vm.greenHouse.humidifier.id) {
                return $q.reject();
            }
            return OutSwitch.get({id : vm.greenHouse.humidifier.id}).$promise;
        }).then(function(humidifier) {
            vm.humidifiers.push(humidifier);
        });
        vm.plants = Plant.query();
        vm.outswitches = OutSwitch.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.greenHouse.id !== null) {
                GreenHouse.update(vm.greenHouse, onSaveSuccess, onSaveError);
            } else {
                GreenHouse.save(vm.greenHouse, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bachelorApp:greenHouseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
