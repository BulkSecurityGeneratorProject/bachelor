(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('ProfileSettingsMySuffixController', ProfileSettingsMySuffixController);

    ProfileSettingsMySuffixController.$inject = ['$scope', '$state', 'ProfileSettings'];

    function ProfileSettingsMySuffixController ($scope, $state, ProfileSettings) {
        var vm = this;
        
        vm.profileSettings = [];

        loadAll();

        function loadAll() {
            ProfileSettings.query(function(result) {
                vm.profileSettings = result;
            });
        }
    }
})();
