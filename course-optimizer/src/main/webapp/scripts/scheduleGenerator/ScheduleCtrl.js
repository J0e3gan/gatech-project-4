angular.module('courseOpt').controller('ScheduleCtrl', function ($rootScope, $scope, $http, AuthService, StudentService) {

	$scope.user = AuthService.getUser();

	$http.get('/courses').success(function(response){
		$scope.courses = response;
		$scope.courses.sort(compare);
	}).error(function(error){
		console.log("Error retrieving courses. " + error);
	});

	var getDetails = function(){
		StudentService.getDetails($scope.user.studentId).success(function (response) {
	        $scope.studentDetails = response; 
	    }).error(function (response) {
	            $scope.error = "Not able to retrieve student details."
	            console.log("Error getting user details. " + response);
	    });;
	}
	getDetails();


	$scope.numDesiredCourses = 0;
	$scope.desiredCourses = [];
	$scope.courseRank = [];

	$scope.setCourse = function(course){

        $rootScope.selectedCourse = course;
    }

	$scope.updateStudent = function(){
		var id = $scope.user.studentId;

		var tempCourses = [];
            for(var i=0; i<$scope.desiredCourses.length; i++){
                tempCourses.push({
                	courseId: $scope.desiredCourses[i],
                	priority:$scope.courseRank[i]
            	});
        }


        var requestBody = {
        	'desiredCourses': tempCourses,
        	'studentId': $scope.user.studentId
        }

        $http.post('/student/update', requestBody).success(function(response){
        	$scope.done = true;
        	$scope.err="";
        	$scope.message = "Student updated successfully!"
        	getDetails();

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
