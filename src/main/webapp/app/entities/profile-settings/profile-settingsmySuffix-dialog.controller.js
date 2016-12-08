(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('ProfileSettingsMySuffixDialogController', ProfileSettingsMySuffixDialogController);

    ProfileSettingsMySuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProfileSettings'];

    function ProfileSettingsMySuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProfileSettings) {
        var vm = this;

        vm.profileSettings = entity;
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
            if (vm.profileSettings.id !== null) {
                ProfileSettings.update(vm.profileSettings, onSaveSuccess, onSaveError);
            } else {
                ProfileSettings.save(vm.profileSettings, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bachelorApp:profileSettingsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
