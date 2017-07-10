var exec = require('cordova/exec');

var PLUGIN_NAME = 'TangoPlugin';

var TangoPlugin = {
	initialize: function (apiKey, successCallback, errorCallback){
		exec(successCallback, errorCallback, PLUGIN_NAME, 'initialize', [apiKey]);
	},

	trigger: 	function (triggerPhrase, successCallback, errorCallback){
		exec(successCallback, errorCallback, PLUGIN_NAME, 'trigger', [triggerPhrase]);
	},

	addSegments: function (segment, successCallback, errorCallback) {
		exec(successCallback, errorCallback, PLUGIN_NAME, "addSegments", [segment]);
	},

	removeSegments: function (segment, successCallback, errorCallback){
		exec(successCallback, errorCallback, PLUGIN_NAME, "removeSegments", [segment]);
	},

	getSegments: function (successCallback, errorCallback){
		exec(successCallback, errorCallback, PLUGIN_NAME, "getSegments", []);
	},
}

module.exports = TangoPlugin;