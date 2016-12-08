(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('OutSwitchMySuffixController', OutSwitchMySuffixController);

    OutSwitchMySuffixController.$inject = ['$scope', '$state', 'OutSwitch'];

    function OutSwitchMySuffixController ($scope, $state, OutSwitch) {
        var vm = this;
        
        vm.outSwitches = [];

        loadAll();

        function loadAll() {
            OutSwitch.query(function(result) {
                vm.outSwitches = result;
            });
        }
    }
})();
