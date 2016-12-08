(function() {
    'use strict';
    angular
        .module('bachelorApp')
        .factory('Plant', Plant);

    Plant.$inject = ['$resource'];

    function Plant ($resource) {
        var resourceUrl =  'api/plants/:id';

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
