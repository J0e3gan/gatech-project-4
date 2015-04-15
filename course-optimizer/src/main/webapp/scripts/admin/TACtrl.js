angular.module('courseOpt').controller('TACtrl', function ($rootScope, $scope, $window, $http, $modal,$state,$stateParams, AuthService) {


   $scope.user = AuthService.getUser();

   var getTAs = function(){
	   $http.get('/tas').success(function(response){
	    	 $scope.taList = response;
	    }).error(function(response){

	    });
	}

    $scope.openModal = function(size){

    	var modalInstance = $modal.open({
    		templateUrl : 'scripts/modal/addTAModal.html',
    		controller: 'ModalCtrl',
    		size: size
    	});

    	modalInstance.result.then(function () {
        }, function () {
            console.log("modal closed");
            getTAs();
        });
    }

    var getTADetails = function(taId){
    	$http.get("/ta/" + taId).success(function(response){
    		$scope.taDetails = response;
    	}).error(function(error){
    		console.log("error fetching TA details for " + taId);
    	});
    }

    getTAs();

    if($state.current.name=='taDetails'){
    	
    	var currentTA = $stateParams.id;
    	getTADetails(currentTA);
    }


});