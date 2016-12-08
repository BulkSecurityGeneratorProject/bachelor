(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .controller('GreenHouseMySuffixController', GreenHouseMySuffixController);

    GreenHouseMySuffixController.$inject = ['$scope', '$state', 'GreenHouse'];

    function GreenHouseMySuffixController ($scope, $state, GreenHouse) {
        var vm = this;
        
        vm.greenHouses = [];

        loadAll();

        function loadAll() {
            GreenHouse.query(function(result) {
                vm.greenHouses = result;
            });
        }
    }
})();
