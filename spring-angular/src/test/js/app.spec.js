describe('Employee Controller', function() {

    beforeEach(module('employeeApp'));

    var $controller, EmployeeController, $rootScope, $scope, $http, $timeout;

    beforeEach(inject(function($injector) {
        $controller = $injector.get('$controller');
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        $http = $injector.get('$http');
        $timeout = $injector.get('$timeout');
        employeeController = $controller('employeeController', {
            '$scope': $scope,
            '$http': $http,
            '$timeout': $timeout
        });
    }));

    it('should init controller', function() {
        spyOn($scope, 'loadEmployees').and.callFake(function() {});
        spyOn($scope, 'watchStatusAndResetWithDelay').and.callFake(function() {});
        $scope.init();
        expect($scope.loadEmployees).toHaveBeenCalled();
        expect($scope.watchStatusAndResetWithDelay).toHaveBeenCalled();
    });
});