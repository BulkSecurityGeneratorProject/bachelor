(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('ProfileSettingsMySuffixDetailController', ProfileSettingsMySuffixDetailController);

    ProfileSettingsMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProfileSettings'];

    function ProfileSettingsMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, ProfileSettings) {
        var vm = this;

        vm.profileSettings = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bachelorApp:profileSettingsUpdate', function(event, result) {
            vm.profileSettings = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
