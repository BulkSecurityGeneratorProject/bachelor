(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('GreenHouseManagerMySuffixController', GreenHouseManagerMySuffixController);

    GreenHouseManagerMySuffixController.$inject = ['$scope', '$state', 'GreenHouseManager'];

    function GreenHouseManagerMySuffixController ($scope, $state, GreenHouseManager) {
        var vm = this;
        
        vm.greenHouseManagers = [];

        loadAll();

        function loadAll() {
            GreenHouseManager.query(function(result) {
                vm.greenHouseManagers = result;
            });
        }
    }
})();
