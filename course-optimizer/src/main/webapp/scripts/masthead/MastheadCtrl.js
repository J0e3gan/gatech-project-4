angular.module('courseOpt').controller('MastheadCtrl', function ($rootScope, $scope, AuthService) {

	$scope.user = AuthService.getUser();
	var adminTabs = [{
							name:'Home',
							link:'admin',
							icon: 'fa-home'
					},
					{
							name:'Course Catalog',
							link:'courseCatalog',
							icon: 'fa-list-alt'
					},{
						name:'Students',
						link:'studentsList',
						icon: 'fa-group'
					},{
						name:'TAs',
						link:'taList',
						icon: 'fa-sitemap'
					},{
						name:'Professors',
						link:'professors',
						icon: 'fa-mortar-board'
					}];
	var studentTabs = [{
							name:'Home',
							link:'student',
							icon: 'fa-home'
						},{
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

	$scope.logout = function(){
		AuthService.logout();
	}

	$scope.status = {
    	isopen: false
  	};

});