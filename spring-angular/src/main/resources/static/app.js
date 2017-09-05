app = angular.module("employeeApp", []);
app.controller("employeeController", function($scope, $http){
    $scope.newEmployee = {};
    $scope.createEmployee = function(){
        $http.post("/employees", JSON.stringify($scope.newEmployee)).then(function handleSuccess(response){
            console.log("Created:" + JSON.stringify(response.data.payload));
            $scope.newEmployee = {};
            $scope.validateNewEmployee();
            $scope.loadEmployees();
        }, function handleError(response){
            alert("An error occurred:" + response.data.message);
            $scope.loadEmployees();
        });
    };

    $scope.matchingInput = function(employee){
            let nameMatching = $scope.newEmployee.name == null || $scope.newEmployee.name == undefined
            || employee.name.indexOf($scope.newEmployee.name) >= 0;

            let ageMatching = $scope.newEmployee.age == null || $scope.newEmployee.age == undefined
            || ('' + employee.age).indexOf($scope.newEmployee.age) >= 0;

            let addressMatching = $scope.newEmployee.address == null || $scope.newEmployee.address == undefined
            || employee.address.indexOf($scope.newEmployee.address) >= 0;

            return nameMatching && ageMatching && addressMatching;
        };

    $scope.deleteEmployee = function(id){
         $http.delete("/employees/" + id).then(function handleSuccess(response){
            console.log("Deleted:" + JSON.stringify(response.data.payload));
            $scope.loadEmployees();
        }, function handleError(response){
            alert("An error occurred:" + response.data.message);
            $scope.loadEmployees();
        });
    };

    $scope.loadEmployees = function(){
        $http.get("/employees").then(function handleSuccess(response){
            $scope.employees = response.data.payload;
        }, function handleError(response){
            alert("An error occurred:" + response.data.message);
        });
    };

    $scope.validateNewEmployee = function(){
        let nameValid = $scope.newEmployee.name != null && $scope.newEmployee.name != undefined
        && $scope.newEmployee.name.replace(" ", "").length > 0;

        let ageValid = $scope.newEmployee.age != null && $scope.newEmployee.age != undefined
        && $scope.newEmployee.age.indexOf(".") < 0 && parseInt($scope.newEmployee.age) > 0;

        let addressValid = $scope.newEmployee.address != null && $scope.newEmployee.address != undefined
        && $scope.newEmployee.address.replace(" ", "").length > 0;

        $scope.newEmployeeValid = nameValid && ageValid && addressValid;
    };

    $scope.loadEmployees();
});