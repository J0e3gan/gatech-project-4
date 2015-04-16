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
            $scope.offerings = parseOfferings(response);

        }).error(function(response){

        });
    }

    var parseOfferings = function(offerings){
        var parsed = {};
        var finalObj = {};

        angular.forEach(offerings, function(course){
            if(!parsed[course.semester.year.toString() + ' ' + course.semester.term.toString()]){
                parsed[course.semester.year.toString() + ' ' + course.semester.term.toString()] = [];
            }
            parsed[course.semester.year.toString() + ' ' + course.semester.term.toString()].push(course);
        });

        var keys = Object.keys(parsed);
        var i, len = keys.length;

        keys.sort();
        var m =0;
        for(i=len-1; i>=0; i--){
            k=keys[i];
            finalObj[m]=(parsed[k]);
            m++;
        }

        return finalObj;
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

    var getProfessors = function(){
        $http.get('/professors').success(function(response){
            $scope.professors = response;
        }).error(function(error){
            console.log("error fetching profs");
        })
    }

    $scope.deleteTA = function(taId){
        $http.delete('/ta/delete/'+taId).success(function(response){
            getTAs(); //re-populate
        }).error(function(error){
            console.log("Error deleting TA");
        });
    }
    
    $scope.deleteProf = function(profId){
        $http.delete('/professor/delete/'+profId).success(function(response){
            getProfessors(); //re-populate
        }).error(function(error){
            console.log("Error deleting prof");
        });
    }

    $scope.setCourse = function(course){

        $rootScope.selectedCourse = course;
    }

    var addNewOffering = function(){
        var requestBody = {
            'crn':'',
            'studentCapacity':'',
            'course':{
                'id':'',
                'name':'',
                'number':'',
                'prerequisites': ''
            },
            'semester':{
                'id':'',
                'term': '',
                'year': ''
            },
            'professor':'',
            'teacherAssistants':[],
            'enrolledStudents':[],
            'studentsOnWaitList':[]
        }

        $http.post('/offering/schedule', requestBody);
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
    else if($state.current.name=='professors'){
        getProfessors();
    }
    else{
        getCourseOfferings();
    }


}).filter('titleCase', function() {
        return function(input) {
          input = input || '';
          return input.replace(/\w\S*/g, function(txt){return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();});
        };
});