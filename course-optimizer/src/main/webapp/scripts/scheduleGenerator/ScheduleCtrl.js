angular.module('courseOpt').controller('ScheduleCtrl', function ($rootScope, $scope, $http) {

	$http.get('/courses').success(function(response){
		$scope.courses = response;
		$scope.courses.sort(compare);
	}).error(function(error){
		console.log("Error retrieving courses. " + error);
	});

	$scope.numDesiredCourses = 0;
	$scope.desiredCourses = [];
	$scope.courseRank = [];


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
