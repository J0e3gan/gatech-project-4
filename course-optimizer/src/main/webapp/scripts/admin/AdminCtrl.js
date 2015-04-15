angular.module('courseOpt').controller('AdminCtrl', function ($rootScope, $scope, $window, $http, $modal,$state,$stateParams, AuthService) {


   $scope.user = AuthService.getUser();

   var getStudents = function(){
	   $http.get('/students').success(function(response){
	    	 $scope.students = response;
	    }).error(function(response){

	    });
	}

    var getCourseOfferings = function(){
        $http.get('/offerings').success(function(response){
            $scope.offerings = response;

        }).error(function(response){

        });
    }

    $scope.openModal = function(size, template){

    	var modalInstance = $modal.open({
    		templateUrl : template,
    		controller: 'ModalCtrl',
    		size: size
    	});

    	modalInstance.result.then(function () {
        }, function () {
            console.log("modal closed");
            getStudents();
        });
    }

    var getStudentDetails = function(studentId){
    	$http.get("/student/" + studentId).success(function(response){
    		$scope.studentDetails = response;
    	}).error(function(error){
    		console.log("error fetching student details for " + studentId);
    	});
    }

    var getTAs = function(){
    	 $http.get("/tas").success(function(response){
    	 	$scope.tas = response;
    	 }).error(function(error){
    	 	console.log("error fetching TAs");
    	 });
    }


    if($state.current.name=='studentDetails'){
    	var currentStudent = $stateParams.id;
    	getStudentDetails(currentStudent);
    }
    else if($state.current.name=='studentsList'){
    	getStudents();
    }
    else if($state.current.name=='taList'){
    	getTAs();
    }
    else{
        getCourseOfferings();
    }


});