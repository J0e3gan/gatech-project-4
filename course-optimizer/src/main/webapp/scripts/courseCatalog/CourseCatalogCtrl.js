angular.module('courseOpt').controller('CourseCatalogCtrl', function ($rootScope, $scope, $http, $sce) {

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
		$scope.selectedCourse.description = $scope.parseDescription(course.description);
	};

	$scope.parseDescription = function(description){

		description = description.replace('"',"<div>");
		description = description.replace('"',"</div>");

		return description;

	}

	$scope.trust= function(description){
		return $sce.trustAsHtml(description);
	}

});
