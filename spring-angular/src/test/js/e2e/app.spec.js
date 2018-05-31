describe('Employee App', function() {
    var employees;

    beforeEach(function () {
        browser.get('http://localhost:8080/');
        employees = element.all(by.repeater('employee in employees'));
    });

    it('should display employees list on init', function () {
        expect(employees.count()).toEqual(3);
        expect(employees.get(0).getText()).toContain('bob 34 wroclaw');
        expect(employees.get(1).getText()).toContain('tom 22 warsaw');
        expect(employees.get(2).getText()).toContain('pat 22 warsaw');
    });

    it('should display employees list on load click', function () {
        var loadButton = element(by.id('load-button'));
        loadButton.click();
        expect(employees.count()).toEqual(3);
        expect(employees.get(0).getText()).toContain('bob 34 wroclaw');
        expect(employees.get(1).getText()).toContain('tom 22 warsaw');
        expect(employees.get(2).getText()).toContain('pat 22 warsaw');
    });

    it('should delete first employee and display updated employees list and success status message', function () {
        var deleteButton = element(by.id('delete-button0'));
        deleteButton.click();
        browser.ignoreSynchronization = true;
        var statusLabel = element(by.id('employee-list-status'));
        expect(statusLabel.getText()).toContain('Successfully deleted employee');
        expect(statusLabel.getAttribute('class')).toContain('success');
        browser.ignoreSynchronization = false;
        expect(employees.count()).toEqual(2);
        expect(employees.get(0).getText()).toContain('tom 22 warsaw');
        expect(employees.get(1).getText()).toContain('pat 22 warsaw');
    });

    it('should create new employee and display updated employees list and success status message', function () {
        var createButton = element(by.id('create-button'));
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
        expect(employees.get(0).getText()).toContain('tom 22 warsaw');
        expect(employees.get(1).getText()).toContain('pat 22 warsaw');
        expect(employees.get(2).getText()).toContain('max 55 warsaw');
    });

    it('should disable crate button if name is missing', function () {
        var createButton = element(by.id('create-button'));
        element(by.model('newEmployee.name')).sendKeys('');
        element(by.model('newEmployee.age')).sendKeys('33');
        element(by.model('newEmployee.address')).sendKeys('berlin');
        expect(createButton.isEnabled()).toBe(false);
    });

    it('should disable crate button if age is missing', function () {
        var createButton = element(by.id('create-button'));
        element(by.model('newEmployee.name')).sendKeys('sam');
        element(by.model('newEmployee.age')).sendKeys('');
        element(by.model('newEmployee.address')).sendKeys('berlin');
        expect(createButton.isEnabled()).toBe(false)
    });

    it('should disable crate button if address is missing', function () {
        var createButton = element(by.id('create-button'));
        element(by.model('newEmployee.name')).sendKeys('sam');
        element(by.model('newEmployee.age')).sendKeys('33');
        element(by.model('newEmployee.address')).sendKeys('');
        expect(createButton.isEnabled()).toBe(false)
    });
});
