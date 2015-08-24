'use strict';

angular.module('treadstoneApp').controller('MajorDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Major',
        function ($scope, $stateParams, $modalInstance, entity, Major) {

            $scope.major = entity;
            $scope.load = function (id) {
                Major.get({id: id}, function (result) {
                    $scope.major = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('treadstoneApp:majorUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.major.id != null) {
                    Major.update($scope.major, onSaveFinished);
                } else {
                    Major.save($scope.major, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
