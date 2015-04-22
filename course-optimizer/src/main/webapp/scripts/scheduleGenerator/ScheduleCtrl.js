angular.module('courseOpt').controller('ScheduleCtrl', function ($rootScope, $scope, $http, AuthService) {

	$scope.user = AuthService.getUser();

	$http.get('/courses').success(function(response){
		$scope.courses = response;
		$scope.courses.sort(compare);
	}).error(function(error){
		console.log("Error retrieving courses. " + error);
	});

	$scope.numDesiredCourses = 0;
	$scope.desiredCourses = [];
	$scope.courseRank = [];

	$scope.updateStudent = function(){
		var id = $scope.user.studentId;

		var tempCourses = [];
            for(var i=0; i<$scope.desiredCourses.length; i++){
                tempCourses.push({
                	course:{id: $scope.desiredCourses[i]},
                	priority:$scope.courseRank[i]
            	});
        }


        var requestBody = {
        	'username' : $scope.user.username,
        	'password' : 'password',
        	'firstName': $scope.user.firstName,
        	'lastName' : $scope.user.lastName,
        	'desiredCourses': tempCourses,
        	'studentId': $scope.user.studentId
        }

        $http.post('/student/enroll', requestBody).success(function(response){
        	$scope.done = true;
        	$scope.err="";
        	$scope.message = "Student updated successfully!"

        }).error(function(error){
        	$scope.err = "Error adding student";
        });
	}

	function compare(a,b) {
	  if (a.number < b.number)
	     return -1;
	  if (a.number > b.number)
	    return 1;
	  return 0;
	}

	$scope.$watch('numDesiredCourses', function(){
		$scope.arr = getNumber($scope.numDesiredCourses);
	});

	var getNumber = function(num) {
		var arr = [];
		arr.length = num;
	    return arr;   
	}


});
