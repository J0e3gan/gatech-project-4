angular.module('courseOpt').controller('ModalCtrl', function ($rootScope, $scope, $http, $modalInstance) {

		$scope.newStudent={
			firstName : "",
			lastName : "",
			studentId : "",
		}
        $scope.newTA={
            firstName:"",
            lastName:""
        }
		$scope.done = false;
		$scope.err = "";

	    $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.addStudent = function(){

        	if($scope.newStudent.firstName=="" || $scope.newStudent.lastName=="" || $scope.newStudent.studentId==""){
        		$scope.err="Please make sure all fields have data.";
        		return;
        	}

        	var requestBody = {
        		'username' : $scope.newStudent.studentId,
        		'password' : 'password',
        		'firstName': $scope.newStudent.firstName,
        		'lastName' : $scope.newStudent.lastName,
        		'desiredCourses': [],
        		'seniority': 0,
        		'studentId': $scope.newStudent.studentId
        	}

        	$http.post('/student/enroll', requestBody).success(function(response){
        		$scope.done = true;
        		$scope.err="";
        		$scope.message = "Student added successfully!"

        	}).error(function(error){
        		$scope.err = "Error adding student";
        	});
        }

        $scope.addTA = function(){
            //TODO: add content
        }
});
