exports.config = {
  framework: 'jasmine',
  seleniumAddress: 'http://localhost:4444/wd/hub',
  specs: ['src/test/js/e2e/app.spec.js'],
  capabilities: {
    browserName: 'firefox'
  }
}