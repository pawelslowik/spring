app = angular.module("employeeApp", []);
app.controller("employeeController", ['$scope', '$http', '$timeout',
    function ($scope, $http, $timeout) {
        $scope.newEmployee = {};
        $scope.status = {};

        $scope.init = function () {
            $scope.loadEmployees();
            $scope.watchStatusAndResetWithDelay();
        }

        $scope.createEmployee = function () {
            $http.post("/employees", $scope.newEmployee).then(function (successResponse) {
                updateStatusOnResponse(successResponse, "Successfully created employee with id=" + successResponse.data.payload.id);
                $scope.newEmployee = {};
                $scope.loadEmployees();
            }, function (errorResponse) {
                updateStatusOnResponse(errorResponse, "Could not create new employee due to an error.");
            });
        };

        $scope.deleteEmployee = function (id) {
            $http.delete("/employees/" + id).then(function (successResponse) {
                updateStatusOnResponse(successResponse, "Successfully deleted employee with id=" + id);
                $scope.loadEmployees();
            }, function (errorResponse) {
                updateStatusOnResponse(errorResponse, "Could not delete employee due to an error.");
            });
        };

        $scope.loadEmployees = function () {
            $scope.newEmployee = {};
            $http.get("/employees").then(function (successResponse) {
                $scope.employees = successResponse.data.payload;
            }, function (errorResponse) {
                updateStatusOnResponse(errorResponse, "Could not load list of employees due to an error.");
            });
        };

        $scope.matchingInput = function (employee) {
            let nameMatching = !$scope.newEmployee.name || employee.name.indexOf($scope.newEmployee.name) >= 0;
            let ageMatching = !$scope.newEmployee.age || ('' + employee.age).indexOf($scope.newEmployee.age) >= 0;
            let addressMatching = !$scope.newEmployee.address || employee.address.indexOf($scope.newEmployee.address) >= 0;
            return nameMatching && ageMatching && addressMatching;
        };

        $scope.newEmployeeValid = function () {
            let nameValid = $scope.newEmployee.name && $scope.newEmployee.name.trim().length > 0;
            let ageValid = $scope.newEmployee.age && $scope.newEmployee.age.indexOf(".") < 0 && !isNaN($scope.newEmployee.age) && parseInt($scope.newEmployee.age) > 0;
            let addressValid = $scope.newEmployee.address && $scope.newEmployee.address.trim().length > 0;
            return nameValid && ageValid && addressValid;
        };

        $scope.watchStatusAndResetWithDelay = function () {
            let resetPromise = null;
            $scope.$watch('status.message', function (newValue) {
                if (newValue) {
                    if (resetPromise) {
                        $timeout.cancel(resetPromise);
                    }
                    resetPromise = $timeout(function () { $scope.status.message = ""; resetPromise = null; }, 10000);
                }
            });
        }

        function updateStatusOnResponse(response, message) {
            var success = response.status ? response.status.toString().startsWith(2) : false;
            if (!success) {
                let data = response.data;
                if (data) {
                    message += " Cause:" + data.error + (data.message ? " - " + data.message : "");
                }
            }
            $scope.status.message = message;
            $scope.status.success = success;
        }
    }]);
