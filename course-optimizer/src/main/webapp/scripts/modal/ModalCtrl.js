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
        $scope.newProf={
            firstName:"",
            lastName:"",
            competencies:[]
        }

        $http.get('/courses').success(function(response){
            $scope.courses = response;
            $scope.courses.sort(compare);
        }).error(function(error){
            console.log("Error retrieving courses. " + error);
        });

        $http.get('/professors').success(function(response){
            $scope.professors = response;
        }).error(function(error){
            console.log("error fetching profs");
        });

        $scope.selected = {
            course:"",
            professor:"",
            capacity:0,
            crn:""
        }

        $scope.numCompetencies = 0;
		$scope.done = false;
		$scope.err = "";

        var modalType = "";

	    $scope.cancel = function() {
            $modalInstance.dismiss(modalType);
        };

        $scope.addStudent = function(){
            modalType = 'student';
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
            modalType = 'ta';

            if($scope.newTA.firstName=="" || $scope.newTA.lastName==""){
                $scope.err = "Please make sure all fields have data";
                return;
            }

            var requestBody = {
                'firstName' : $scope.newTA.firstName,
                'lastName' : $scope.newTA.lastName
            }

            $http.post('/ta/create', requestBody).success(function(response){
                $scope.done = true;
                $scope.err="";
                $scope.message = "TA added successfully!"

            }).error(function(error){
                $scope.err = "Error adding TA";
            });

        }

        $scope.addProf = function(){
            modalType = 'prof';

            if($scope.newProf.firstName=="" || $scope.newProf.lastName==""){
                $scope.err = "Please make sure all fields have data";
                return;
            }

            var tempComps = [];
            for(var i=0; i<$scope.newProf.competencies.length; i++){
                tempComps.push({id: $scope.newProf.competencies[i]});
            }

            var requestBody = {
                'firstName' : $scope.newProf.firstName,
                'lastName' : $scope.newProf.lastName,
                'competencies':tempComps
            }

            $http.post('/professor/create', requestBody).success(function(response){
                $scope.done = true;
                $scope.err="";
                $scope.message = "Professor added successfully!"

            }).error(function(error){
                $scope.err = "Error adding Professor";
            });

        }

        $scope.addOffering = function(){
            if($scope.selected.course==""){
                $scope.err = "Please select a course to offer."
                return;
            }

            var requestBody = {
                "crn": "",
                "studentCapacity":$scope.selected.capacity,
                "course":{id : $scope.selected.course },
                "semester":$rootScope.nextSemester,
                "professor":{id: $scope.selected.professor},
                "teacherAssistants":[],
                "enrolledStudents":[],
                "studentsOnWaitList":[]
            }

            $http.post('/offering/schedule', requestBody).success(function(response){
                $scope.done = true;
                $scope.err="";
                $scope.message = "Offering added successfully!"

            }).error(function(error){
                $scope.err = "Error adding course offering";
            });
        }

        $scope.$watch('numCompetencies', function(){
            $scope.getNumber($scope.numCompetencies);
        });

        $scope.getNumber = function(num) {
            var arr = [];
            arr.length = num;
            //return arr;
            $scope.emptyArr =  arr;  
        }

        function compare(a,b) {
          if (a.number < b.number)
             return -1;
          if (a.number > b.number)
            return 1;
          return 0;
        }


        
});
