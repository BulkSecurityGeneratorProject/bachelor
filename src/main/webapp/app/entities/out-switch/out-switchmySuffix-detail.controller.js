(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('OutSwitchMySuffixDetailController', OutSwitchMySuffixDetailController);

    OutSwitchMySuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OutSwitch'];

    function OutSwitchMySuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, OutSwitch) {
        var vm = this;

        vm.outSwitch = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bachelorApp:outSwitchUpdate', function(event, result) {
            vm.outSwitch = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
