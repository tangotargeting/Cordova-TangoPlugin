var exec = require('cordova/exec');

var PLUGIN_NAME = 'TangoPlugin';

var TangoPlugin = {
	initialize: function (apiKey, successCallback, errorCallback){
		exec(successCallback, errorCallback, PLUGIN_NAME, 'initializeTango', [apiKey]);
	},

	trigger: 	function (triggerPhrase, successCallback, errorCallback){
		exec(successCallback, errorCallback, PLUGIN_NAME, 'trigger', [triggerPhrase]);
	},

	addSegments: function (segments, successCallback, errorCallback) {
		exec(successCallback, errorCallback, PLUGIN_NAME, "addSegments", [segments]);
	}
}

module.exports = TangoPlugin;