(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('ProfileSettingsMySuffixDeleteController',ProfileSettingsMySuffixDeleteController);

    ProfileSettingsMySuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProfileSettings'];

    function ProfileSettingsMySuffixDeleteController($uibModalInstance, entity, ProfileSettings) {
        var vm = this;

        vm.profileSettings = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProfileSettings.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
