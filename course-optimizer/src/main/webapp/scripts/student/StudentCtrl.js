angular.module('courseOpt').controller('StudentCtrl', function ($rootScope, $scope, $window, $http, AuthService, StudentService) {

	$scope.user = AuthService.getUser();

	StudentService.details = StudentService.getDetails($scope.user.studentId);
});