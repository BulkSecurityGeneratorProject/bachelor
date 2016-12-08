(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('GreenHouseManagerMySuffixDialogController', GreenHouseManagerMySuffixDialogController);

    GreenHouseManagerMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'GreenHouseManager', 'ProfileSettings', 'GreenHouse'];

    function GreenHouseManagerMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, GreenHouseManager, ProfileSettings, GreenHouse) {
        var vm = this;

        vm.greenHouseManager = entity;
        vm.clear = clear;
        vm.save = save;
        vm.settings = ProfileSettings.query({filter: 'greenhousemanager-is-null'});
        $q.all([vm.greenHouseManager.$promise, vm.settings.$promise]).then(function() {
            if (!vm.greenHouseManager.settings || !vm.greenHouseManager.settings.id) {
                return $q.reject();
            }
            return ProfileSettings.get({id : vm.greenHouseManager.settings.id}).$promise;
        }).then(function(settings) {
            vm.settings.push(settings);
        });
        vm.greenhouses = GreenHouse.query({filter: 'greenhousemanager-is-null'});
        $q.all([vm.greenHouseManager.$promise, vm.greenhouses.$promise]).then(function() {
            if (!vm.greenHouseManager.greenHouse || !vm.greenHouseManager.greenHouse.id) {
                return $q.reject();
            }
            return GreenHouse.get({id : vm.greenHouseManager.greenHouse.id}).$promise;
        }).then(function(greenHouse) {
            vm.greenhouses.push(greenHouse);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.greenHouseManager.id !== null) {
                GreenHouseManager.update(vm.greenHouseManager, onSaveSuccess, onSaveError);
            } else {
                GreenHouseManager.save(vm.greenHouseManager, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bachelorApp:greenHouseManagerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
