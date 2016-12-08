(function() {
    'use strict';
    angular
        .module('bachelorApp')
        .factory('OutSwitch', OutSwitch);

    OutSwitch.$inject = ['$resource'];

    function OutSwitch ($resource) {
        var resourceUrl =  'api/out-switches/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
