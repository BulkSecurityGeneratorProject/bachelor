(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('out-switchmySuffix', {
            parent: 'entity',
            url: '/out-switchmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.outSwitch.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/out-switch/out-switchesmySuffix.html',
                    controller: 'OutSwitchMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('outSwitch');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('out-switchmySuffix-detail', {
            parent: 'entity',
            url: '/out-switchmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.outSwitch.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/out-switch/out-switchmySuffix-detail.html',
                    controller: 'OutSwitchMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('outSwitch');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'OutSwitch', function($stateParams, OutSwitch) {
                    return OutSwitch.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'out-switchmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('out-switchmySuffix-detail.edit', {
            parent: 'out-switchmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/out-switch/out-switchmySuffix-dialog.html',
                    controller: 'OutSwitchMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OutSwitch', function(OutSwitch) {
                            return OutSwitch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('out-switchmySuffix.new', {
            parent: 'out-switchmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/out-switch/out-switchmySuffix-dialog.html',
                    controller: 'OutSwitchMySuffixDialogController',
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
                    $state.go('out-switchmySuffix', null, { reload: 'out-switchmySuffix' });
                }, function() {
                    $state.go('out-switchmySuffix');
                });
            }]
        })
        .state('out-switchmySuffix.edit', {
            parent: 'out-switchmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/out-switch/out-switchmySuffix-dialog.html',
                    controller: 'OutSwitchMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OutSwitch', function(OutSwitch) {
                            return OutSwitch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('out-switchmySuffix', null, { reload: 'out-switchmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('out-switchmySuffix.delete', {
            parent: 'out-switchmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/out-switch/out-switchmySuffix-delete-dialog.html',
                    controller: 'OutSwitchMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OutSwitch', function(OutSwitch) {
                            return OutSwitch.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('out-switchmySuffix', null, { reload: 'out-switchmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
