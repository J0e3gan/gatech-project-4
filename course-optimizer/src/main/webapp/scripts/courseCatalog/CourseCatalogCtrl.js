angular.module('courseOpt').controller('CourseCatalogCtrl', function ($rootScope, $scope, $http) {

	$http.get('/courses').success(function(response){
		$scope.courses = response;
		$scope.courses.sort(compare);
		$scope.setSelectedCourse($scope.courses[0]);
	}).error(function(error){
		console.log("Error retrieving courses. " + error);
	});

	function compare(a,b) {
	  if (a.number < b.number)
	     return -1;
	  if (a.number > b.number)
	    return 1;
	  return 0;
	}

	$scope.setSelectedCourse = function(course){
		if(course.prerequisites.length == 0){
			course.prerequisites = 'No required prerequisites';
		}
		$scope.selectedCourse = course;
	};


});
