angular.module('courseOpt').controller('MastheadCtrl', function ($rootScope, $scope, AuthService) {

	$scope.user = AuthService.getUser();
	var adminTabs = [{
							name:'Course Catalog',
							link:'courseCatalog',
							icon: 'fa-list-alt'
					},{
						name:'Students',
						link:'admin.students',
						icon: 'fa-list-alt'
					}];
	var studentTabs = [{
							name:'Course Catalog',
							link:'courseCatalog',
							icon: 'fa-list-alt'
						},
						{
							name:'Schedule Generator',
							link:'schedule',
							icon:'fa-calendar'
						}];

	switch($scope.user.role){
		case 'ADMIN':
			$scope.tabs = adminTabs;
			break;
		case 'STUDENT':
			$scope.tabs = studentTabs;
			break;
		default:
			$scope.tabs = studentTabs;
			break;
	}

});