describe('Employee Controller', function () {

    beforeEach(module('employeeApp'));

    var $controller, employeeController, $rootScope, $scope, $http, $timeout, $httpBackend;

    beforeEach(inject(function ($injector) {
        $controller = $injector.get('$controller');
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        $http = $injector.get('$http');
        $timeout = $injector.get('$timeout');
        $httpBackend = $injector.get('$httpBackend');
        employeeController = $controller('employeeController', {
            '$scope': $scope,
            '$http': $http,
            '$timeout': $timeout
        });
        $scope.newEmployee = {
            name: 'bob',
            age: '44',
            address: 'warsaw'
        };
    }));

    it('should init controller', function () {
        spyOn($scope, 'loadEmployees').and.callFake(function () { });
        spyOn($scope, 'watchStatusAndResetWithDelay').and.callFake(function () { });
        $scope.init();
        expect($scope.loadEmployees).toHaveBeenCalled();
        expect($scope.watchStatusAndResetWithDelay).toHaveBeenCalled();
    });

    it('should create employee and update status', function () {
        let expectedResponse = { payload: { id: 1 } };
        spyOn($scope, 'loadEmployees').and.callFake(function () { });
        $httpBackend.expectPOST('/employees', $scope.newEmployee).respond(200, expectedResponse);
        $scope.createEmployee();
        $httpBackend.flush();
        expect($scope.status.message).toEqual("Successfully created employee with id=" + expectedResponse.payload.id);
        expect($scope.status.success).toEqual(true);
        expect($scope.newEmployee).toEqual({});
        expect($scope.loadEmployees).toHaveBeenCalled();
    });

    it('should fail to create employee and update status with generic error message', function () {
        spyOn($scope, 'loadEmployees').and.callFake(function () { });
        $httpBackend.expectPOST('/employees', $scope.newEmployee).respond(500);
        $scope.createEmployee();
        $httpBackend.flush();
        expect($scope.status.message).toEqual("Could not create new employee due to an error.");
        expect($scope.status.success).toEqual(false);
        expect($scope.newEmployee).toEqual({
            name: 'bob',
            age: '44',
            address: 'warsaw'
        });
        expect($scope.loadEmployees).not.toHaveBeenCalled();
    });

    it('should fail to create employee and update status with specific error', function () {
        let expectedResponse = {
            error: 'Internal Server Error'
        };
        spyOn($scope, 'loadEmployees').and.callFake(function () { });
        $httpBackend.expectPOST('/employees', $scope.newEmployee).respond(500, expectedResponse);
        $scope.createEmployee();
        $httpBackend.flush();
        expect($scope.status.message).toEqual("Could not create new employee due to an error. Cause:" + expectedResponse.error);
        expect($scope.status.success).toEqual(false);
        expect($scope.newEmployee).toEqual({
            name: 'bob',
            age: '44',
            address: 'warsaw'
        });
        expect($scope.loadEmployees).not.toHaveBeenCalled();
    });

    it('should fail to create employee and update status with specific error and message', function () {
        let expectedResponse = {
            error: 'Internal Server Error',
            message: 'Something really bad happened'
        };
        spyOn($scope, 'loadEmployees').and.callFake(function () { });
        $httpBackend.expectPOST('/employees', $scope.newEmployee).respond(500, expectedResponse);
        $scope.createEmployee();
        $httpBackend.flush();
        expect($scope.status.message).toEqual("Could not create new employee due to an error. Cause:" + expectedResponse.error + " - " + expectedResponse.message);
        expect($scope.status.success).toEqual(false);
        expect($scope.newEmployee).toEqual({
            name: 'bob',
            age: '44',
            address: 'warsaw'
        });
        expect($scope.loadEmployees).not.toHaveBeenCalled();
    });

    it('should delete employee and update status', function () {
        let employeeId = 1;
        spyOn($scope, 'loadEmployees').and.callFake(function () { });
        $httpBackend.expectDELETE('/employees/' + employeeId).respond(200);
        $scope.deleteEmployee(employeeId);
        $httpBackend.flush();
        expect($scope.status.message).toEqual("Successfully deleted employee with id=" + employeeId);
        expect($scope.status.success).toEqual(true);
        expect($scope.newEmployee).toEqual({
            name: 'bob',
            age: '44',
            address: 'warsaw'
        });
        expect($scope.loadEmployees).toHaveBeenCalled();
    });

    it('should fail to delete employee and update status with specific error and message', function () {
        let expectedResponse = {
            error: 'Internal Server Error',
            message: 'Something really bad happened'
        };
        let employeeId = 1;
        spyOn($scope, 'loadEmployees').and.callFake(function () { });
        $httpBackend.expectDELETE('/employees/' + employeeId).respond(500, expectedResponse);
        $scope.deleteEmployee(employeeId);
        $httpBackend.flush();
        expect($scope.status.message).toEqual("Could not delete employee due to an error. Cause:" + expectedResponse.error + " - " + expectedResponse.message);
        expect($scope.status.success).toEqual(false);
        expect($scope.newEmployee).toEqual({
            name: 'bob',
            age: '44',
            address: 'warsaw'
        });
        expect($scope.loadEmployees).not.toHaveBeenCalled();
    });

    it('should load employees and not update status', function () {
        let expectedResponse = { payload: [{ id: 1 }] };
        $httpBackend.expectGET('/employees').respond(200, expectedResponse);
        $scope.loadEmployees();
        $httpBackend.flush();
        expect($scope.status).toEqual({});
        expect($scope.newEmployee).toEqual({});
        expect($scope.employees).toEqual([{ id: 1 }]);
    });

    it('should load employees and update status with generic error message', function () {
        $httpBackend.expectGET('/employees').respond(500);
        $scope.loadEmployees();
        $httpBackend.flush();
        expect($scope.status.message).toEqual("Could not load list of employees due to an error.");
        expect($scope.status.success).toEqual(false);
        expect($scope.newEmployee).toEqual({});
    });

    it('should mark new valid employee as valid', function() {
        expect($scope.newEmployeeValid()).toEqual(true);
    });
});