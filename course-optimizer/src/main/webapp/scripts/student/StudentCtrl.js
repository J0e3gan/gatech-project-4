angular.module('courseOpt').controller('StudentCtrl', function ($rootScope, $scope, $window, $http, AuthService, StudentService) {

	$scope.user = AuthService.getUser();

	StudentService.getDetails($scope.user.studentId).success(function (response) {
                $scope.studentDetails = response; 
    }).error(function (response) {
            $scope.error = "Not able to retrive student details."
            console.log("Error getting user details. " + response);
    });;

    
});