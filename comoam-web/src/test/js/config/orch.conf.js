// Karma configuration
// Generated on Mon Dec 08 2014 11:10:32 GMT+0800 (China Standard Time)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [
	  '../../../main/webapp/vendor/jquery/dist/jquery.js',
	  '../../../main/webapp/vendor/angularjs/angular.js',
	  '../../../main/webapp/vendor/angular-sanitize/*.js',
	  '../../../main/webapp/vendor/angular-sanitize/*.map',
	  '../../../main/webapp/vendor/angular-mocks/*.js',
	  '../../../main/webapp/vendor/angular-ui-router/release/*.js',
	  '../../../main/webapp/vendor/datatables/media/js/jquery.dataTables.min.js',
	  '../../../main/webapp/vendor/angularjs-datatables/angular-datatables.min.js',
	  '../../../main/webapp/vendor/angular-bootstrap/ui-bootstrap.js',
	  '../../../main/webapp/vendor/angular-bootstrap/ui-bootstrap-tpls.js',
	  '../../../main/webapp/vendor/jasmine/*',
	  '../../../main/webapp/app/*.js',
	  '../../../main/webapp/app/compute/*.js',
	  '../../../main/webapp/app/storage/*.js',
	  '../../../main/webapp/app/network/*.js',
	  '../../../main/webapp/app/login/*.js',
	  '../../../main/webapp/app/nav/*.js',
	  '../../../test/js/jasmine.html',
	  '../../../test/js/compute/*.js',
	  '../../../test/js/storage/*.js',
	  '../../../test/js/login/*.js',
	  '../../../test/js/nav/*.js',
	  '../../../test/js/network/*.js'
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false
  });
};
