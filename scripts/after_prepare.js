#!/usr/bin/env node
'use strict';

console.log("Executing after_prepare.js!");

var fs = require('fs');

const FCM_GRADLE_FILE_PATH =  "./plugins/cordova-plugin-tango/src/android/build.gradle";
const BUILD_GRADLE_FILE_PATH = "./platforms/android/build.gradle";

var destinFile = fs.readFileSync(BUILD_GRADLE_FILE_PATH, {encoding: "utf-8"});

if(destinFile.indexOf("apply plugin: 'com.google.gms.google-services'") < 0){
  var sourceFile = fs.readFileSync(FCM_GRADLE_FILE_PATH, {encoding: "utf-8"});
  fs.appendFile(BUILD_GRADLE_FILE_PATH, sourceFile, function(err){
    console.log("after_prepare: build.gradle updated!");
  });
}