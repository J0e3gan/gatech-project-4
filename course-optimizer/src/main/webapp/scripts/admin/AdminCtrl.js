angular.module('courseOpt').controller('AdminCtrl', function ($rootScope, $scope, $window, $http, $modal, AuthService) {


   $scope.user = AuthService.getUser();

   var getStudents = function(){
	   $http.get('/students').success(function(response){
	    	 $scope.students = response;
	    }).error(function(response){

	    });
	}

    $scope.openModal = function(size){

    	var modalInstance = $modal.open({
    		templateUrl : 'scripts/modal/addStudentModal.html',
    		controller: 'ModalCtrl',
    		size: size
    	});

    	modalInstance.result.then(function () {
        }, function () {
            console.log("modal closed");
            getStudents();
        });
    }

    getStudents();


});