(function() {
    'use strict';
    angular
        .module('bachelorApp')
        .factory('InSensor', InSensor);

    InSensor.$inject = ['$resource'];

    function InSensor ($resource) {
        var resourceUrl =  'api/in-sensors/:id';

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
