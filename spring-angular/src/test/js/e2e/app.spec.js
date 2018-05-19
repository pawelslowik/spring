describe('Employee App', function() {
    var employees = element.all(by.repeater('employee in employees'));

    it('should display employees list on init', function () {
        browser.get('http://localhost:8080/');
        expect(employees.count()).toEqual(3);
        expect(employees.get(0).getText()).toContain('bob');
        expect(employees.get(1).getText()).toContain('tom');
        expect(employees.get(2).getText()).toContain('pat');
    });

    it('should delete first employee and display updated employees list and success status message', function () {
        var deleteButton = element(by.id('deleteButton0'));
        deleteButton.click();
        browser.ignoreSynchronization = true;
        var statusLabel = element(by.id('employee-list-status'));
        expect(statusLabel.getText()).toContain('Successfully deleted employee');
        expect(statusLabel.getAttribute('class')).toContain('success');
        browser.ignoreSynchronization = false;
        expect(employees.count()).toEqual(2);
        expect(employees.get(0).getText()).toContain('tom');
        expect(employees.get(1).getText()).toContain  ('pat');
    });

    it('should create new employee and display updated employees list and success status message', function () {
        var createButton = element(by.id('createButton'));
        element(by.model('newEmployee.name')).sendKeys('max');
        element(by.model('newEmployee.age')).sendKeys(55);
        element(by.model('newEmployee.address')).sendKeys('warsaw');
        createButton.click();
        browser.ignoreSynchronization = true;
        var statusLabel = element(by.id('employee-list-status'));
        expect(statusLabel.getText()).toContain('Successfully created employee');
        expect(statusLabel.getAttribute('class')).toContain('success');
        browser.ignoreSynchronization = false;
        expect(employees.count()).toEqual(3);
        expect(employees.get(0).getText()).toContain('tom');
        expect(employees.get(1).getText()).toContain  ('pat');
        expect(employees.get(2).getText()).toContain  ('max 55 warsaw');
    });
});