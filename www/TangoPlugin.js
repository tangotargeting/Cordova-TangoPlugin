var exec = require('cordova/exec');

exports.trigger = function(arg0, success, error) {
    exec(success, error, "TangoPlugin", "trigger", [arg0]);
};

exports.addSegment = function (arg0, success, error) {
	exec(success, error, "TangoPlugin", "addSegment", [arg0]);
};

exports.initializeTango = function(arg0, success, error) {
	exec(success, error, "TangoPlugin", "initializeTango", [arg0]);
};