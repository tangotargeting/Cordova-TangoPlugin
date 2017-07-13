# Tango targeting

Communication chanel for your existing customers

For more information please see [our website][1].

## Installation

1.  Open a terminal window, navigate to your Cordova project and type the following command:
```
cordova plugin add https://github.com/tangotargeting/Cordova-TangoPlugin.git
```
2.  If you don't have the iOS platform in your project, use the following line to add it:
```
cordova platform add ios
```
3.  Navigate to `yourCordovaProject/platforms/ios` and open the `.xcworkspace` file with Xcode.
4.  Select **yourCordovaProject** from Xcode Project Navigator. Then, select *Build Settings*, 
    search for **Always Embed Swift Standard Libraries** and set it to **YES**.
5.  You also need to code sign your app, for that please follow [this guide](https://developer.apple.com/support/code-signing/).
6.  Build and run.

### iOS 10 Rich Notifications

The library has support for iOS 10 notifications attachments, you can add images and animated GIFs in a notification. 
For using this functionality you will need to create a [notification service extension](https://developer.apple.com/reference/usernotifications/unnotificationserviceextension/). 

Create a new iOS target in Xcode (File -> New -> Target) and select the Notification Service Extension type
![NotificationServiceExtension image](https://github.com/tangotargeting/tango-ios/blob/master/Resources/NotificationServiceExtension.png?raw=true)

Go to `"yourCordovaProject"/platforms/ios` and open `Podfile` and add the new target for notification extension that you created in previous step. Add TangoRichNotification framework to this target, by typing following lines:
```
target 'Your-NotificationServiceExtesion-Target-Name' do
use_frameworks!
pod 'TangoRichNotification', '~> 1.0.1'
end
```

TangoRichNotification is developed for iOS 10.0 or higher, so you should update also the `platform :ios, '9.0'` to `10.0`.
After filling Podfile save it and run the following command in the Terminal:

```
$ pod install
```

iOS 10 Notification Framework installed.

### Add capabilities

For using push notifications you should add the following capabilities. Go to Xcode select the target’s Capabilities pane and enable push notifications: 

![PushNotificatioCapabilities image](https://github.com/tangotargeting/tango-ios/blob/master/Resources/PushNotifications.png?raw=true)

You should also enable Backround Modes an Remote notifications capabilities: 

![BackgroundModes image](https://github.com/tangotargeting/tango-ios/blob/master/Resources/BackgroundModes.png?raw=true)

### APNS Setup
After you setup the framework in order to use push notification you should create an APNS Certificate which require membership in the [iOS Developer Program](https://developer.apple.com/programs/).

Go to [Apple Developer Members Center](https://developer.apple.com/account/ios/certificate/) click on App IDs from Identifiers section.

![AppIds image](https://github.com/tangotargeting/tango-ios/blob/master/Resources/App%20IDs.png?raw=true)

1. Select your app id. If you don't have an app id created select +  button and fill out the form, be sure you check Push Notification checkbox.
2. Expand your app by selecting your app id and you will see a field named Push Notifications with yellow or green status icons for development or distribution: ![PushNotificationStatus image](https://github.com/tangotargeting/tango-ios/blob/master/Resources/Push%20Notifications%20Status.png?raw=true)
3. Click edit button, go to Push Notifications section and press the button create certificate for distribution or development.![CreateCertificate image](https://github.com/tangotargeting/tango-ios/blob/master/Resources/Create%20Certificate.png?raw=true) Follow up the instructions to create an Certificate Signing Request (CSR) file from your Mac. And press continue.
4. Upload the CSR and after that press download to get the Certificate.
5. Open the certificate. Opening the certificate will open Keychain Access.
6. Select your certificate from  Keychain Access in My Certificates section. If the certificate is not here try in Certificate section. Right clik on it and then Export "Apple iOS Development/Distribution Push Service: your_app_bundle". 
This command will export the certificate in a .p12 file with a password.

### Add Certificate to Tango
After generating the Push Notification certificate go [here](https://app.tangotargeting.com/app) and create a new iOS app:

![CreateiOSApp image](https://github.com/tangotargeting/tango-ios/blob/master/Resources/Create%20iOS%20App.png?raw=true
)

After that you should fill the form with your app data:
- insert app bundle id
- insert app name
- drag and drop  .p12 file from previous step

![AddCertificate image](https://github.com/tangotargeting/tango-ios/blob/master/Resources/Add%20Certificate.png?raw=true)

# How to use

**1. Tango framework**

Now the plugin is added but you don't use it yet. For start using it you should do this: 
*Go to `www/js/index.js` and in your `onDeviceReady` method, call the following method for initializing Tango:* 
``` 
tangoplugin.initialize('your-tango-api-key');
```
*If you are going to use a location campaign you need to add in your plist this key `NSLocationAlwaysUsageDescription`.*

# Additional methos

*There are also 2 methos in our plugin that you can use whenever you want:* 
1. If you want to register a new segment you will use:
``` 
tangoplugin.addSegment('your-segment-name');
```
2. If you want to trigger a campaign using a custom trigger you will use:
``` 
tangoplugin.trigger('your-custom-trigger-name');
```

**2. TangoRichNotification framework**

*1. After creating the Notification service extension, go to NotificationService class:*
``` objc
import TangoRichNotification
```

*2. In `didReceive(_ request: UNNotificationRequest, withContentHandler contentHandler: @escaping (UNNotificationContent)` method remove:*
``` objc
if let bestAttemptContent = bestAttemptContent {
	// Modify the notification content here...
	bestAttemptContent.title = "\(bestAttemptContent.title) [modified]"
	contentHandler(bestAttemptContent)
}
```
and add 
``` objc
if let bestAttemptContent = bestAttemptContent {
            TangoRichNotification.setupRichContent(content: bestAttemptContent,  apiKey: "your-api-key", completionHandler: { (content) in contentHandler(content)})
}
```

**3. Build and run :)**

## License

```
Copyright 2017 Tango Targeting, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


[1]: http://tangotargeting.com
