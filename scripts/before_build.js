#!/usr/bin/env node
'use strict';

var xmldoc = require('xmldoc');
var fs = require('fs');
function fileExists(path) {
  try  {
    return fs.statSync(path).isFile();
  }
  catch (e) {
    return false;
  }
}

function directoryExists(path) {
  try  {
    return fs.statSync(path).isDirectory();
  }
  catch (e) {
    return false;
  }
}
var fileOptions = {encoding: "utf-8"};
var config = fs.readFileSync("config.xml", fileOptions);
var configXml = new xmldoc.XmlDocument(config);

if(directoryExists("platforms/android")){
  // check if tango_api_key is set
  var platforms = configXml.childrenNamed("platform");
  var android = platforms.find(function(platform){
    return platform.attr.name === "android";
  });

  if(android == null) throw new Error("cordova-plugin-tango: android platform is not added to config.xml");

  var configFiles = android.childrenNamed("config-file");

  var applicationConfigs = configFiles.filter(function(configFile){
    return configFile.attr.parent === "./application" 
          && configFile.attr.target === "AndroidManifest.xml"
  });

  var tangoApiKeyMissing = true;
  applicationConfigs.forEach(function(configFile){
    var metaDatas = configFile.childrenNamed("meta-data");
    metaDatas.forEach(function(metaData){
      if(true
        && metaData.attr['android:name'] === "tango_api_key" 
        && metaData.attr['android:value'] !== undefined  
        && metaData.attr['android:value'] !== null
        && metaData.attr['android:value'] !== ""){
        tangoApiKeyMissing = false;
      }
    })
  });

  if(tangoApiKeyMissing) throw new Error("cordova-plugin-tango: Could not find tango_api_key in config.xml for android platform");

  // check for google-services.json
  if(fileExists("google-services.json")){

    const PLUGIN_GRADLE_FILE_PATH =  "./plugins/cordova-plugin-tango/src/android/build.gradle";
    const BUILD_GRADLE_FILE_PATH = "./platforms/android/build.gradle";

    var buildGradle = fs.readFileSync(BUILD_GRADLE_FILE_PATH, fileOptions);
    var pluginGradle = fs.readFileSync(PLUGIN_GRADLE_FILE_PATH, fileOptions);

    if(buildGradle.indexOf(pluginGradle) < 0){
      
      fs.appendFile(BUILD_GRADLE_FILE_PATH, pluginGradle, function(err){
        console.log("cordova-plugin-tango: build.gradle updated!");
      });
    }
  }else{
    throw new Error("cordova-plugin-tango: google-services.json is missing.");
  }
}