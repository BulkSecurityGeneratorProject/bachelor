(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('in-sensormySuffix', {
            parent: 'entity',
            url: '/in-sensormySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.inSensor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/in-sensor/in-sensorsmySuffix.html',
                    controller: 'InSensorMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('inSensor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('in-sensormySuffix-detail', {
            parent: 'entity',
            url: '/in-sensormySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.inSensor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/in-sensor/in-sensormySuffix-detail.html',
                    controller: 'InSensorMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('inSensor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'InSensor', function($stateParams, InSensor) {
                    return InSensor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'in-sensormySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('in-sensormySuffix-detail.edit', {
            parent: 'in-sensormySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/in-sensor/in-sensormySuffix-dialog.html',
                    controller: 'InSensorMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InSensor', function(InSensor) {
                            return InSensor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('in-sensormySuffix.new', {
            parent: 'in-sensormySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/in-sensor/in-sensormySuffix-dialog.html',
                    controller: 'InSensorMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                pinNumber: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('in-sensormySuffix', null, { reload: 'in-sensormySuffix' });
                }, function() {
                    $state.go('in-sensormySuffix');
                });
            }]
        })
        .state('in-sensormySuffix.edit', {
            parent: 'in-sensormySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/in-sensor/in-sensormySuffix-dialog.html',
                    controller: 'InSensorMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InSensor', function(InSensor) {
                            return InSensor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('in-sensormySuffix', null, { reload: 'in-sensormySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('in-sensormySuffix.delete', {
            parent: 'in-sensormySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/in-sensor/in-sensormySuffix-delete-dialog.html',
                    controller: 'InSensorMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InSensor', function(InSensor) {
                            return InSensor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('in-sensormySuffix', null, { reload: 'in-sensormySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
