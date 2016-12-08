(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('plantmySuffix', {
            parent: 'entity',
            url: '/plantmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.plant.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plant/plantsmySuffix.html',
                    controller: 'PlantMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('plant');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('plantmySuffix-detail', {
            parent: 'entity',
            url: '/plantmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.plant.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plant/plantmySuffix-detail.html',
                    controller: 'PlantMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('plant');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Plant', function($stateParams, Plant) {
                    return Plant.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'plantmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('plantmySuffix-detail.edit', {
            parent: 'plantmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plant/plantmySuffix-dialog.html',
                    controller: 'PlantMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Plant', function(Plant) {
                            return Plant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plantmySuffix.new', {
            parent: 'plantmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plant/plantmySuffix-dialog.html',
                    controller: 'PlantMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('plantmySuffix', null, { reload: 'plantmySuffix' });
                }, function() {
                    $state.go('plantmySuffix');
                });
            }]
        })
        .state('plantmySuffix.edit', {
            parent: 'plantmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plant/plantmySuffix-dialog.html',
                    controller: 'PlantMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Plant', function(Plant) {
                            return Plant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plantmySuffix', null, { reload: 'plantmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plantmySuffix.delete', {
            parent: 'plantmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plant/plantmySuffix-delete-dialog.html',
                    controller: 'PlantMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Plant', function(Plant) {
                            return Plant.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plantmySuffix', null, { reload: 'plantmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
