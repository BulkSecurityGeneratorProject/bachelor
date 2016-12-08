(function() {
    'use strict';
    angular
        .module('bachelorApp')
        .factory('ProfileSettings', ProfileSettings);

    ProfileSettings.$inject = ['$resource'];

    function ProfileSettings ($resource) {
        var resourceUrl =  'api/profile-settings/:id';

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
